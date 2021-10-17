insert into usr(id, username, password, active, email)
    values (DEFAULT,'okabe','wider',true,'test@mail.com');
insert into user_role (user_id, roles)
    values (1,'USER'), (1,'ADMIN');

insert into usr(id, username, password, active, email)
values (DEFAULT,'tester','wider',true,'test@mail.com');

insert into user_role (user_id, roles)
values (2,'USER');

insert into usr(id, username, password, active, email)
values (DEFAULT,'DELETED','qweqgfqW124qassdqgnmuui876/_)0',true,'SYSTEM@SYSTEM.com');

