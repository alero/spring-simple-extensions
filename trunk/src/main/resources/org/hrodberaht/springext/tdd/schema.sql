drop table Person if exists;
drop table Account if exists;
drop table AccountPerson if exists;


create table Person (
    ID integer identity primary key,
    name varchar(255),
    email varchar(255),
    createdate Date
);

create table Account (
    ID integer identity primary key,
    accountname varchar(255),
    balance double
);

create table AccountPerson (
    personid integer,
    accountid integer    
);

alter table AccountPerson add constraint FK_AccountPerson_Person foreign key (personid) references Person(ID) on delete cascade;
alter table AccountPerson add constraint FK_AccountPerson_Account foreign key (accountid) references Account(ID) on delete cascade;

