CREATE TABLE app_user (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   username VARCHAR(255),
   password VARCHAR(255),
   CONSTRAINT pk_appuser PRIMARY KEY (id)
);

CREATE TABLE app_user_roles (
  app_user_id BIGINT NOT NULL,
   roles_id BIGINT NOT NULL
);

ALTER TABLE app_user_roles ADD CONSTRAINT fk_appuserol_on_app_user FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE app_user_roles ADD CONSTRAINT fk_appuserol_on_role FOREIGN KEY (roles_id) REFERENCES role (id);