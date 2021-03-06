create database test;
use test;
create table users(
id bigint(20) NOT NULL AUTO_INCREMENT,
user_name varchar(50) not null,
password varchar(50) not null,
email varchar(50) not null,
mobile_number varchar(25) not null,
admin char(1),
PRIMARY KEY (id)
);

create table Temperature_Note(
id bigint(20) NOT NULL AUTO_INCREMENT,
creation_date Date,
note VARCHAR(500),
user_id bigint(20),
PRIMARY KEY (id)
);

create table Default_Temperature_Note(
id bigint(20) NOT NULL AUTO_INCREMENT,
from_temp Int,
to_temp Int,
note VARCHAR(500),
PRIMARY KEY (id)
);