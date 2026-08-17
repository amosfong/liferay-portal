/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import * as OAuth2 from '@liferay/oauth2-provider-web/client';
import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import Button from '~/components/Button';
import PopoverIcon from '~/features/project/containers/ActivationStatus/DXPCloud/components/PopoverIcon';
import {useAppContext} from '~/features/project/context';
import DeveloperKeysLayouts from '~/features/project/layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '~/features/project/utils/constants';
import {useGetCloudNativeEnvironments} from '~/services/liferay/graphql/cloud-native-environments';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';
import downloadFromBlob from '~/utils/downloadFromBlob';
import i18n from '~/utils/I18n';

import OfflineActivationBundleModal from './components/OfflineActivationBundleModal';
import OfflineActivationModal from './components/OfflineActivationModal';

import './CloudNative.css';

const ACTIVATION_ERROR_MESSAGE_KEYS = {
	INVALID_TOKEN:
		'the-activation-token-is-not-valid-please-generate-a-new-token-in-your-cloud-native-environment-and-try-again',
};

const BUNDLE_ERROR_MESSAGE_KEYS = {
	ADD_ONS_UNAVAILABLE:
		'no-add-ons-are-available-for-the-selected-dxp-version-please-select-a-different-version',
};

const ENVIRONMENT_TYPE_ORDER = ['production', 'uat', 'non-production'];

const UNEXPECTED_ACTIVATION_ERROR_MESSAGE_KEY =
	'there-was-an-unexpected-error-while-attempting-to-activate-your-environment-please-try-again-in-a-few-moments';

const UNEXPECTED_BUNDLE_ERROR_MESSAGE_KEY =
	'there-was-an-unexpected-error-while-attempting-to-download-your-offline-activation-bundle-please-try-again-in-a-few-moments';

const getErrorMessageKey = async (
	error,
	errorMessageKeys,
	unexpectedErrorMessageKey
) => {
	if (typeof error?.text !== 'function') {
		return unexpectedErrorMessageKey;
	}

	try {
		const code = (await error.text()).trim();

		return errorMessageKeys[code] ?? unexpectedErrorMessageKey;
	}
	catch (readError) {
		return unexpectedErrorMessageKey;
	}
};

const sortByEnvironmentType = (a, b) =>
	ENVIRONMENT_TYPE_ORDER.indexOf(a.environmentType) -
	ENVIRONMENT_TYPE_ORDER.indexOf(b.environmentType);

const CloudNative = () => {
	const [{project, subscriptionGroups}] = useAppContext();

	const [activationErrorMessageKey, setActivationErrorMessageKey] =
		useState('');
	const [bundleEnvironmentId, setBundleEnvironmentId] = useState('');
	const [bundleErrorMessageKey, setBundleErrorMessageKey] = useState('');
	const [copiedActivationCode, setCopiedActivationCode] = useState('');
	const [isActivating, setIsActivating] = useState(false);
	const [isDownloadingBundle, setIsDownloadingBundle] = useState(false);
	const [oAuthToken, setOAuthToken] = useState();
	const [offlineActivationCode, setOfflineActivationCode] = useState('');
	const [
		offlineActivationEnvironmentType,
		setOfflineActivationEnvironmentType,
	] = useState('');
	const {setHasSideMenu} = useOutletContext();

	const {observer, onClose} = useModal({
		onClose: () => {
			setActivationErrorMessageKey('');
			setOfflineActivationCode('');
			setOfflineActivationEnvironmentType('');
		},
	});

	const {observer: bundleObserver, onClose: onBundleClose} = useModal({
		onClose: () => {
			setBundleEnvironmentId('');
			setBundleErrorMessageKey('');
		},
	});

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	const {data, refetch} = useGetCloudNativeEnvironments({
		filter: `accountKey eq '${project?.accountKey}'`,
	});

	const handleOfflineActivate = async (token) => {
		setActivationErrorMessageKey('');
		setIsActivating(true);

		try {
			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			await oauth2Client.fetch(
				'/cloud-native-environments/offline-activation',
				{
					body: JSON.stringify({
						activationCode: offlineActivationCode,
						token,
					}),
					method: 'POST',
				}
			);

			setOfflineActivationCode('');
			setOfflineActivationEnvironmentType('');

			await refetch();
		}
		catch (error) {
			setActivationErrorMessageKey(
				await getErrorMessageKey(
					error,
					ACTIVATION_ERROR_MESSAGE_KEYS,
					UNEXPECTED_ACTIVATION_ERROR_MESSAGE_KEY
				)
			);
		}
		finally {
			setIsActivating(false);
		}
	};

	const handleDownloadBundle = async (dxpVersion) => {
		setBundleErrorMessageKey('');
		setIsDownloadingBundle(true);

		try {
			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			const response = await oauth2Client.fetch(
				'/cloud-native-environments/offline-activation-bundle',
				{
					body: JSON.stringify({
						dxpVersion,
						environmentId: bundleEnvironmentId,
					}),
					method: 'POST',
				}
			);

			downloadFromBlob(
				await response.blob(),
				`${bundleEnvironmentId}-${dxpVersion}-offline-activation-bundle.zip`
			);

			setBundleEnvironmentId('');
		}
		catch (error) {
			setBundleErrorMessageKey(
				await getErrorMessageKey(
					error,
					BUNDLE_ERROR_MESSAGE_KEYS,
					UNEXPECTED_BUNDLE_ERROR_MESSAGE_KEY
				)
			);
		}
		finally {
			setIsDownloadingBundle(false);
		}
	};

	if (!project || !subscriptionGroups) {
		return <span> {i18n.translate('loading')}...</span>;
	}

	const cloudNativeEnvironments =
		data?.c?.cloudNativeEnvironments?.items || [];

	const environments = [...cloudNativeEnvironments]
		.filter(({environmentId}) => !!environmentId)
		.sort(sortByEnvironmentType);

	const hasOfflineEnvironments = environments.some(
		({activationMethod}) => activationMethod === 'offline'
	);

	const activationCodes = [...cloudNativeEnvironments]
		.filter(
			({activationCode, environmentId}) =>
				activationCode && !environmentId
		)
		.sort(sortByEnvironmentType);

	const handleCopyToClipboard = async (activationCode) => {
		await navigator.clipboard.writeText(activationCode);

		setCopiedActivationCode(activationCode);
	};

	return (
		<>
			{!!bundleEnvironmentId && (
				<OfflineActivationBundleModal
					errorMessageKey={bundleErrorMessageKey}
					isDownloading={isDownloadingBundle}
					observer={bundleObserver}
					onClose={onBundleClose}
					onDownload={handleDownloadBundle}
				/>
			)}

			{!!offlineActivationCode && (
				<OfflineActivationModal
					environmentType={offlineActivationEnvironmentType}
					errorMessageKey={activationErrorMessageKey}
					isActivating={isActivating}
					observer={observer}
					onActivate={handleOfflineActivate}
					onClose={onClose}
				/>
			)}

			<h1>{i18n.translate('cloud-native-environments')}</h1>

			<div className="mt-4">
				<ClayTable striped={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								{i18n.translate('type')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('environment-id')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('environment-name')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('maximum-cluster-nodes')}

								<PopoverIcon
									symbol="question-circle-full"
									title="maximum-number-of-active-nodes-available-for-this-environment-this-does-not-include-expired-or-future-nodes"
								/>
							</ClayTable.Cell>

							{hasOfflineEnvironments && (
								<ClayTable.Cell headingCell></ClayTable.Cell>
							)}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{environments.length ? (
							environments.map((cloudNativeEnvironment) => (
								<ClayTable.Row
									key={
										cloudNativeEnvironment.cloudNativeEnvironmentId
									}
								>
									<ClayTable.Cell>
										{i18n.translate(
											cloudNativeEnvironment.environmentType
										)}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{cloudNativeEnvironment.environmentId}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{cloudNativeEnvironment.environmentName}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{cloudNativeEnvironment.maxClusterNodes}
									</ClayTable.Cell>

									{hasOfflineEnvironments && (
										<ClayTable.Cell className="text-right">
											{cloudNativeEnvironment.activationMethod ===
												'offline' && (
												<Button
													displayType="link"
													onClick={() =>
														setBundleEnvironmentId(
															cloudNativeEnvironment.environmentId
														)
													}
												>
													{i18n.translate(
														'download-offline-activation-bundle'
													)}
												</Button>
											)}
										</ClayTable.Cell>
									)}
								</ClayTable.Row>
							))
						) : (
							<ClayTable.Row>
								<ClayTable.Cell colSpan={4}>
									{i18n.translate(
										'no-cloud-native-environments-were-found'
									)}
								</ClayTable.Cell>
							</ClayTable.Row>
						)}
					</ClayTable.Body>
				</ClayTable>
			</div>

			{!!activationCodes.length && (
				<div className="mt-5">
					<h2>{i18n.translate('activation-codes')}</h2>

					<div className="mt-4">
						<ClayTable striped={false}>
							<ClayTable.Head>
								<ClayTable.Row>
									<ClayTable.Cell headingCell>
										{i18n.translate('type')}
									</ClayTable.Cell>

									<ClayTable.Cell headingCell>
										{i18n.translate('activation-code')}

										<PopoverIcon
											symbol="question-circle-full"
											title="please-copy-and-paste-this-activation-code-to-your-cloud-native-instance"
										/>
									</ClayTable.Cell>

									<ClayTable.Cell headingCell>
										{i18n.translate(
											'maximum-cluster-nodes'
										)}

										<PopoverIcon
											symbol="question-circle-full"
											title="maximum-number-of-active-nodes-available-for-this-environment-this-does-not-include-expired-or-future-nodes"
										/>
									</ClayTable.Cell>

									<ClayTable.Cell
										headingCell
									></ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								{activationCodes.map(
									(cloudNativeEnvironment) => (
										<ClayTable.Row
											key={
												cloudNativeEnvironment.cloudNativeEnvironmentId
											}
										>
											<ClayTable.Cell>
												{i18n.translate(
													cloudNativeEnvironment.environmentType
												)}
											</ClayTable.Cell>

											<ClayTable.Cell>
												{
													cloudNativeEnvironment.activationCode
												}

												<ClayIcon
													className="cp-copy-clipboard-icon ml-3 text-neutral-5"
													onClick={() =>
														handleCopyToClipboard(
															cloudNativeEnvironment.activationCode
														)
													}
													symbol="copy"
													title={i18n.translate(
														'copy-to-clipboard'
													)}
												/>

												{copiedActivationCode ===
													cloudNativeEnvironment.activationCode && (
													<span className="ml-2 text-neutral-7">
														{i18n.translate(
															'copied-to-clipboard'
														)}
													</span>
												)}
											</ClayTable.Cell>

											<ClayTable.Cell>
												{
													cloudNativeEnvironment.maxClusterNodes
												}
											</ClayTable.Cell>

											<ClayTable.Cell className="text-right">
												<Button
													displayType="secondary"
													onClick={() => {
														setOfflineActivationCode(
															cloudNativeEnvironment.activationCode
														);
														setOfflineActivationEnvironmentType(
															cloudNativeEnvironment.environmentType
														);
													}}
												>
													{i18n.translate(
														'offline-activation'
													)}
												</Button>
											</ClayTable.Cell>
										</ClayTable.Row>
									)
								)}
							</ClayTable.Body>
						</ClayTable>
					</div>
				</div>
			)}

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'to-activate-a-local-instance-of-liferay-dxp-download-a-developer-key-for-your-liferay-dxp-version'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.dxpMajorVersion}
					oAuthToken={oAuthToken}
					productName="DXP"
					projectName={project.name}
				/>
			</DeveloperKeysLayouts>
		</>
	);
};

export default CloudNative;
