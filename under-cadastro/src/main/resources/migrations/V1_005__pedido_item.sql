CREATE TABLE pedido_item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    foto_id BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_pedido_item_pedido` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
    CONSTRAINT `fk_pedido_item_foto` FOREIGN KEY (`foto_id`) REFERENCES `foto` (`id`)
) ENGINE=InnoDB