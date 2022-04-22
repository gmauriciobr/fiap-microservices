CREATE TABLE foto (
    id bigint NOT NULL AUTO_INCREMENT,
    album_id bigint NOT NULL,
    token char(36) NOT NULL,
    filename varchar(255) DEFAULT NULL,
    foto_original longblob DEFAULT NULL,
    foto_baixa_qualidade longblob DEFAULT NULL,
    data_alteracao datetime(6) NOT NULL,
    data_criacao datetime(6) NOT NULL ,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_foto_ambum` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) ENGINE=InnoDB