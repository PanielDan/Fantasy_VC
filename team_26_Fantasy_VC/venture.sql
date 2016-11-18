-- Use this script to initialize the database. --
-- Author: Alan Coon --

drop database Venture;
create database Venture;
use Venture;

create table Users (
	userID 		int(11) 	primary key not null auto_increment,
	username 	varchar(30) not null,
    passcode 	varchar(30) not null,
    biography	varchar(144) not null,
    gamesPlayed int(11) 	not null default 0,
    gamesWon 	int(11) 	not null default 0,
    totalProfit double	 	not null default 0
);

create table Companies (
	-- 	Companies are uniquely identified by an ID. --
    companyID		int(11)		primary key not null auto_increment,
    --  Each company has a filepath/URL to their logo. --
    imagePath		varchar(100) not null,
	--  Each company has a name. --
    companyName		varchar(45) not null,
    --  Each company has a text description. --
    description 	longtext not null,
    --  Each company has a starting price. --
    startingPrice 	double 		not null,
	--  Each company has a tier level. ---
    tierLevel  		int(11)		not null
);



