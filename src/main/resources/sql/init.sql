insert into role(id,name) VALUES (0,'超级管理员');
insert into role(id,name) VALUES (1,'系统管理员');
insert into role(id,name) VALUES (2,'普通用户');

insert into user(username,password,role_id) values('admin','admin',(select id from role where name='超级管理员'));

insert into menu (image, level, name, parent, status, url) values ('fa-diamond', 1, '仪表盘', 0, 1, '/dashboard');
insert into menu (image, level, name, parent, status, url) values ('fa-shopping-cart', 1, '服务管理', 0, 1, null);
insert into menu (image, level, name, parent, status, url) values (null, 2, '主机管理', (select id from menu where name= '服务管理'),1,  '/host/list');
insert into menu (image, level, name, parent, status, url) values (null, 2, '告警管理', (select id from menu where name= '服务管理'),1,  '/event/list');

insert into menu (image, level, name, parent, status, url) values ('fa-shopping-cart', 1, '系统设置', 0, 1, null);
insert into menu (image, level, name, parent, status, url) values (null, 2, '用户管理', (select id from menu where name= '系统设置'),1,  '/user/list');
insert into menu (image, level, name, parent, status, url) values (null, 2, '角色管理', (select id from menu where name= '系统设置'),1,  '/role/list');
insert into menu (image, level, name, parent, status, url) values (null, 2, '权限管理', (select id from menu where name= '系统设置'),1,  '/privilege/view');
insert into menu (image, level, name, parent, status, url) values (null, 2, '菜单管理', (select id from menu where name= '系统设置'),1, '/menu/list');
