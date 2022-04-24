CREATE TABLE album (
     id bigint NOT NULL AUTO_INCREMENT,
     fotografo_id bigint DEFAULT NULL,
     nome varchar(255) DEFAULT NULL,
     valor_foto decimal(10,2) NOT NULL,
     qualidade int NOT NULL,
     token char(36) DEFAULT NULL,
     data_alteracao datetime(6) DEFAULT NULL,
     data_criacao datetime(6) DEFAULT NULL,
     PRIMARY KEY (`id`),
     CONSTRAINT `fk_album_fotografo` FOREIGN KEY (`fotografo_id`) REFERENCES `fotografo` (`id`)
) ENGINE=InnoDB;

insert into album values (1, 1, 'Ensaio', 10, 50, '123456789', now(), now());
commit;