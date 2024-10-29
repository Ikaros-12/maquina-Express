INSERT INTO users (username, password,mail, celular,activo) VALUES
  ( 'admin', 'test123','abc@gmail.com','123',true),
  ( 'adminmaquina', 'marcopolo','yyy@gmail.com','123',true),
  ( 'ifts17', 'ACB789','zzz@gmail.com','123',false);

INSERT INTO roles (name) VALUES
  ('ROLE_ROOT'),
  ('ROLE_ADMIN'),
  ('ROLE_OPERARIO');

INSERT INTO user_roles (user_id,rol_id) VALUES
  (1,1),
  (2,1),
  (3,1);