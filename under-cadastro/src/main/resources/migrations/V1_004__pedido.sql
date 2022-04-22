CREATE TABLE pedido (
    id bigint NOT NULL AUTO_INCREMENT,
    album_id bigint NOT NULL,
    nome varchar(255) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    numero_cartao varchar(255) DEFAULT NULL,
    valor_total decimal(10,2) DEFAULT NULL,
    data_alteracao datetime(6) DEFAULT NULL,
    data_criacao datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_pedido_ambum` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) ENGINE=InnoDB