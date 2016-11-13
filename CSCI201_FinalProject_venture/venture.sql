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

select * from Venture.Users;