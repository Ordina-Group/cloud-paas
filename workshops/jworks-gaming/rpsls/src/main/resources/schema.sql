CREATE TABLE UserConnection (
	userId varchar(255) NOT NULL,
	providerId varchar(255) NOT NULL,
	providerUserId varchar(255),
	rank int NOT NULL,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(255) NOT NULL,
	secret varchar(255),
	refreshToken varchar(255),
	expireTime bigint,
	PRIMARY KEY (userId, providerId, providerUserId));
CREATE UNIQUE INDEX UserConnectionRank ON UserConnection(userId, providerId, rank);
