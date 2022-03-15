-- CREATE SCHEMA
create schema document_analyzer;


-- CREATE TABLE USER
create table document_analyzer.user (
	email varchar(150) primary key,
	created_date date not null default CURRENT_DATE
);

-- CREATE TABLE TEAM
create table document_analyzer.team (
	name varchar(150) primary key
);

-- CREATE TABLE USER_TEAM
create table document_analyzer.user_team (
	user_email varchar(150) references document_analyzer.user (email) on update CASCADE on delete cascade,
	team_name varchar(150) references document_analyzer.team (name) on update cascade,
	constraint user_team_pkey primary key (user_email, team_name)
);

-- CREATE TABLE DOCUMENT
create table document_analyzer.document (
	id serial PRIMARY KEY,
	name varchar(50) not null,
	word_count integer not null,
	data bytea not null,
	created_date date not null default CURRENT_DATE,
	user_email varchar(150) not null,
	foreign key (user_email)
		references document_analyzer.user (email),
    UNIQUE (name, user_email)	
);


-- INSERTS

-- USER
INSERT INTO document_analyzer.user (email)
VALUES('user1@test.com');
INSERT INTO document_analyzer.user (email, created_date)
VALUES('user2@test.com', '2022-03-10');
INSERT INTO document_analyzer.user (email, created_date)
VALUES('user3@test.com', '2022-02-10');

-- TEAM
INSERT INTO document_analyzer.team
VALUES('team one');
INSERT INTO document_analyzer.team
VALUES('team two');
INSERT INTO document_analyzer.team
VALUES('team three');

-- USER_TEAM
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user1@test.com', 'team one');
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user2@test.com', 'team one');
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user2@test.com', 'team two');
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user3@test.com', 'team one');
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user3@test.com', 'team two');
INSERT INTO document_analyzer.user_team (user_email, team_name)
VALUES('user3@test.com', 'team three');

CREATE SEQUENCE document_analyzer.hibernate_sequence START 1;