-- Use this script to initialize the database. --
-- Author: Alan Coon --

drop database Venture;
create database Venture;
use Venture;

create table Users (
	userID 		int(11) 	primary key not null auto_increment,
	username 	varchar(30) not null,
    passcode 	varchar(30) not null,
    gamesPlayed int(11) 	not null default 0,
    gamesWon 	int(11) 	not null default 0,
    totalProfit bigint	 	not null default 0
);

create table Companies (
	-- 	Companies are uniquely identified by an ID. --
    companyID		int(11)		primary key not null auto_increment,
    --  Each company has a filepath/URL to their logo. --
    imagePath		varchar(60) not null,
	--  Each company has a name. --
    companyName		varchar(45) not null,
    --  Each company has a text description. --
    description 	varchar(300) not null,
    --  Each company has a starting price. --
    startingPrice 	bigint 		not null,
	--  Each company has a tier level. ---
    tierLevel  		int(11)		not null
);

select * from Venture.Users;
select * from Venture.Companies;




