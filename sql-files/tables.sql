CREATE TABLE users
(
	[id] int primary key identity(1, 1),
	[user] varchar(100) not null,
	[pw] varchar(512) not null
)

CREATE TABLE accounts
(
	[id] int primary key identity(1, 1),
	[user] varchar(100) not null,
	[pw] varchar(512) not null,
	[host] varchar(50) not null,
	[protocol] char(4) not null,
	[port] int not null,
	[owner_id] int not null
)