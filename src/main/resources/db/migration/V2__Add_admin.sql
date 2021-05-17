insert into usr(id, username, password, active, email)
    values (2,'okabe','wider',true,'test@mail.com');

insert into user_role (user_id, roles)
    values (2,'USER'), (2,'ADMIN');