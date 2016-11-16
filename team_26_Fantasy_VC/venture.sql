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

insert into Companies (imagePath, companyName, description, startingPrice, tierLevel)
	values ('image', 
			'Active Recovery', 
            'Active Recovery Inc. is a medium sized biomedical engineering company, specializing in rehabilitative technology for athletes and soldiers.  They are looking to sell equity to fuel new development. ',
            25000,
            3);


select * from Venture.Users;
select * from Venture.Companies;




