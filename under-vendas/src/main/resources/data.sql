insert into fotografo (id, nome, email, data_criacao, data_alteracao)
values (1, 'Guilherme', 'guilherme@email.com', now(), now());

insert into album (id, fotografo_id, nome, valor_foto, qualidade, token, data_criacao, data_alteracao)
values (1, 1, 'Ensaio', 10, 50, '123456789', now(), now());

insert into foto (id, album_id, token, filename, foto_original, foto_baixa_qualidade, data_criacao, data_alteracao)
values (1, 1, '123456789', 'teste.jpg', 7465737465, 7465737465,  now(), now());

insert into pedido (id, album_id, nome, email, numero_cartao, valor_total, data_criacao, data_alteracao)
values (1, 1, 'Guilherme', 'guilherme@email.com', '1234123412341234', 10, now(), now())