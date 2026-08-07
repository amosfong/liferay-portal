/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useState} from 'react';
import Button from '~/components/Button';
import {LIST_TYPES} from '~/features/project/utils/constants';
import SearchBuilder from '~/lib/SearchBuilder';
import {useGetListTypeDefinitions} from '~/services/liferay/graphql/list-type-definitions';
import i18n from '~/utils/I18n';

const QUARTERLY_VERSION_REGEX = /^DXP (\d{4})\.Q([1-4])\.(\d+)/;

const MINIMUM_QUARTERLY_VERSION = [2024, 1, 0];

const compareQuarterlyVersions = (quarterlyVersion1, quarterlyVersion2) => {
	for (let i = 0; i < quarterlyVersion1.length; i++) {
		if (quarterlyVersion1[i] !== quarterlyVersion2[i]) {
			return quarterlyVersion1[i] - quarterlyVersion2[i];
		}
	}

	return 0;
};

const parseQuarterlyVersion = (name) => {
	const matches = name.match(QUARTERLY_VERSION_REGEX);

	return matches
		? [Number(matches[1]), Number(matches[2]), Number(matches[3])]
		: null;
};

const OfflineActivationBundleModal = ({
	errorMessageKey,
	isDownloading,
	observer,
	onClose,
	onDownload,
}) => {
	const [dxpVersion, setDXPVersion] = useState('');

	const {data} = useGetListTypeDefinitions({
		filter: SearchBuilder.eq('name', LIST_TYPES.dxpMinorVersion),
	});

	const dxpVersions = (
		data?.listTypeDefinitions?.items?.[0]?.listTypeEntries ?? []
	)
		.map((entry) => ({
			...entry,
			quarterlyVersion: parseQuarterlyVersion(entry.name),
		}))
		.filter(
			({quarterlyVersion}) =>
				quarterlyVersion &&
				compareQuarterlyVersions(
					quarterlyVersion,
					MINIMUM_QUARTERLY_VERSION
				) >= 0
		)
		.sort((a, b) =>
			compareQuarterlyVersions(b.quarterlyVersion, a.quarterlyVersion)
		);

	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">
							{i18n.translate(
								'download-offline-activation-bundle'
							)}
						</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>

					<label className="mt-5" htmlFor="cpOfflineBundleDXPVersion">
						{i18n.translate('dxp-version')}
					</label>

					<select
						className="form-control"
						disabled={isDownloading}
						id="cpOfflineBundleDXPVersion"
						onChange={(event) => setDXPVersion(event.target.value)}
						value={dxpVersion}
					>
						<option disabled value="">
							{i18n.translate('select-a-dxp-version')}
						</option>

						{dxpVersions.map(({key, name}) => (
							<option key={key} value={name}>
								{name}
							</option>
						))}
					</select>
				</div>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						{i18n.translate('cancel')}
					</Button>

					<Button
						className="d-flex ml-2"
						disabled={isDownloading || !dxpVersion}
						onClick={() => onDownload(dxpVersion)}
					>
						{isDownloading ? (
							<>
								<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm"></span>
								{i18n.translate('download-in-progress')}...
							</>
						) : (
							i18n.translate('download')
						)}
					</Button>
				</div>
			</div>

			{!isDownloading && !!errorMessageKey && (
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

export default OfflineActivationBundleModal;
