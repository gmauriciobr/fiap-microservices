CREATE TABLE fotografo (
     id bigint NOT NULL AUTO_INCREMENT,
     nome varchar(255) DEFAULT NULL,
     email varchar(255) DEFAULT NULL,
     data_alteracao datetime(6) DEFAULT NULL,
     data_criacao datetime(6) DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB;

insert into fotografo values (1, 'Guilherme', 'guilherme@email.com', now(), now());
commit;