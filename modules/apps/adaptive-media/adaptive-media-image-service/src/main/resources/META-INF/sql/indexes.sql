create index IX_607564C4 on AdaptiveMediaImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$]);
create unique index IX_3AFA259 on AdaptiveMediaImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId);
create index IX_72197D9C on AdaptiveMediaImageEntry (fileVersionId);
create index IX_F0B3611F on AdaptiveMediaImageEntry (groupId);
create index IX_AEC6C91F on AdaptiveMediaImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_99FBCF09 on AdaptiveMediaImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId);