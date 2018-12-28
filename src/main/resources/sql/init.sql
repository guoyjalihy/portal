insert into menus (image, level, name, parent, status, url,id) values (null, 0, 'root', null, 1, null,0);
insert into menus (image, level, name, parent, status, url) values ('fa-diamond', 1, '仪表盘', 0, 1, '/dashboard');

insert into menus (image, level, name, parent, status, url) values ('fa-desktop', 1, '内容管理', 0, 1, null);
insert into menus (image, level, name, parent, status, url) values (null, 2, '文章管理', (select id from menus where name= '内容管理'), 1, '/newsManage_0_0_0');


insert into menus (image, level, name, parent, status, url) values ('fa-shopping-cart', 1, '系统设置', 0, 1, null);
insert into menus (image, level, name, parent, status, url) values (null, 2, '用户管理', (select id from menus where name= '系统设置'),1,  '/user');
insert into menus (image, level, name, parent, status, url) values (null, 2, '权限管理', (select id from menus where name= '系统设置'),1,  '/privilege');
insert into menus (image, level, name, parent, status, url) values (null, 2, '菜单管理', (select id from menus where name= '系统设置'),1, '/menus');
