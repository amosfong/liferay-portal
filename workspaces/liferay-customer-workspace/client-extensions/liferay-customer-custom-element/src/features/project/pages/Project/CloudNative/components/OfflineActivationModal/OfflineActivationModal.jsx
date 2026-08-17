/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useState} from 'react';
import Button from '~/components/Button';
import i18n from '~/utils/I18n';

// TODO Replace with the real command once it is defined

const ACTIVATION_CLI_COMMAND = '[activation CLI command]';

const OfflineActivationModal = ({
	environmentType,
	errorMessageKey,
	isActivating,
	observer,
	onActivate,
	onClose,
}) => {
	const [token, setToken] = useState('');

	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">
							{i18n.translate('offline-activation')}
						</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>

					<p className="mb-4 mt-5 text-neutral-10">
						{i18n.sub(
							'this-environment-s-cloud-native-cluster-doesn-t-have-a-live-connection-to-liferay-s-provisioning-service-run-x-in-your-cloud-native-environment-to-generate-a-signed-activation-token-then-paste-it-below-to-activate-this-x-environment',
							[ACTIVATION_CLI_COMMAND, environmentType]
						)}
					</p>

					<label htmlFor="cpOfflineActivationToken">
						{i18n.translate('activation-token')}
					</label>

					<textarea
						className="form-control"
						disabled={isActivating}
						id="cpOfflineActivationToken"
						onChange={(event) => setToken(event.target.value)}
						placeholder={i18n.translate(
							'paste-your-activation-token-here'
						)}
						rows={6}
						value={token}
					/>
				</div>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						{i18n.translate('cancel')}
					</Button>

					<Button
						className="d-flex ml-2"
						disabled={isActivating || !token.trim()}
						onClick={() => onActivate(token.trim())}
					>
						{isActivating ? (
							<>
								<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm"></span>
								{i18n.translate('activating')}...
							</>
						) : (
							i18n.translate('ok')
						)}
					</Button>
				</div>
			</div>

			{!isActivating && !!errorMessageKey && (
				<div className="allign cp-error-alert d-flex px-4 py-3">
					<ClayIcon
						className="mr-2 mt-1 text-danger"
						symbol="info-circle"
					/>

					<p className="m-0 text-danger text-paragraph">
						{i18n.translate(errorMessageKey)}
					</p>
				</div>
			)}
		</ClayModal>
	);
};

export default OfflineActivationModal;
