INSERT INTO role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO role (role_name) VALUES ('ROLE_JOURNALIST');
INSERT INTO role (role_name) VALUES ('ROLE_SUBSCRIBER');
INSERT INTO app_user (username, password) VALUES ('root', '$2a$12$bG26jPcfWQEh6sOjfCnraOplnwcn.v0kZ9ca3bfOJK0.U4AWABk2a');
INSERT INTO app_user (username, password) VALUES ('user', '$2a$12$pZBiqwxqLV47L/cTEwBy.eogOF/Xmt.NZWAs.qm/ck2Z7m8bzBiwq');
INSERT INTO app_user (username, password) VALUES ('sub', '$2a$12$GE35cq0gvtai6eITiDvNc.BZ8HpL.OyvB.Rdeur3Sh6ca8WmNFP3.');
INSERT INTO app_user_roles (app_user_id, roles_id) VALUES (1, 1);
INSERT INTO app_user_roles (app_user_id, roles_id) VALUES (2, 2);
INSERT INTO app_user_roles (app_user_id, roles_id) VALUES (3, 3);