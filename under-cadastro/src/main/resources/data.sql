insert into fotografo (id, nome, email, data_criacao, data_alteracao) values (1, 'Guilherme', 'guilherme@email.com', now(), now());

insert into album (id, fotografo_id, nome, qualidade, token, data_criacao, data_alteracao) values (1, 1, 'Ensaio', 50, '123456789', now(), now());

