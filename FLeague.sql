use userNP;


create table ranks (
num tinyint unsigned primary key
);

create table UserD (
id tinyint unsigned auto_increment not null primary key,
Team varchar (20),
win int default 0,
draw int default 0,
lose int default 0,
points int default 0,
foreign key (id) references ranks (num)
);

Update UserD set lose = lose + 1  where Team = 'bekasi fc';

Update UserD set points = points + 1  where Team = 'bogor united';

insert into ranks values(12);

insert into UserD 
set Team = 'bekasi fc', 
id= (select num from ranks where num = 1 );

delete from UserD where id;
delete from ranks where num;

update UserD set win = 'bogor united' where id = 1;

update UserD set win = 1, draw = 2, lose = 2, points = 7 where team = 'bekasi fc' ;

drop table Score;

SELECT * FROM Score WHERE Team1 = 'demit';

SELECT *
FROM UserD
ORDER BY points DESC
LIMIT 1;

UPDATE UserD SET win = 0 , lose = 0 , draw = 0 , points = 0 WHERE Team = 'Munyuk' ;

SELECT team FROM UserD WHERE  points = max(points);

select * from UserD order by points desc;
select * from ranks;


create table Score (
id tinyint unsigned auto_increment not null primary key,
Team1 varchar (20),
score1 int,
stripe char(1) default '-',
score2 int,
Team2 varchar (20)
);

insert into Score (Team1, score1, score2, Team2) values ('Bogor united', 2, 3, 'Depok FC');


select * from Score;

SELECT * FROM Score WHERE Team1 = 'demit' AND Team2 = 'Bali United' and score1 = 5 and score2 = 8;
