/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import PopoverIcon from '~/features/project/containers/ActivationStatus/DXPCloud/components/PopoverIcon';
import {useAppContext} from '~/features/project/context';
import DeveloperKeysLayouts from '~/features/project/layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '~/features/project/utils/constants';
import {useGetCloudNativeEnvironments} from '~/services/liferay/graphql/cloud-native-environments';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';
import i18n from '~/utils/I18n';

import './CloudNative.css';

const ENVIRONMENT_TYPE_ORDER = ['production', 'uat', 'non-production'];

const sortByEnvironmentType = (a, b) =>
	ENVIRONMENT_TYPE_ORDER.indexOf(a.environmentType) -
	ENVIRONMENT_TYPE_ORDER.indexOf(b.environmentType);

const CloudNative = () => {
	const [{project, subscriptionGroups}] = useAppContext();

	const [copiedActivationCode, setCopiedActivationCode] = useState('');
	const [oAuthToken, setOAuthToken] = useState();
	const {setHasSideMenu} = useOutletContext();

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

	const {data} = useGetCloudNativeEnvironments({
		filter: `accountKey eq '${project?.accountKey}'`,
	});

	if (!project || !subscriptionGroups) {
		return <span> {i18n.translate('loading')}...</span>;
	}

	const cloudNativeEnvironments =
		data?.c?.cloudNativeEnvironments?.items || [];

	const environments = [...cloudNativeEnvironments]
		.filter(({environmentId}) => !!environmentId)
		.sort(sortByEnvironmentType);

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
