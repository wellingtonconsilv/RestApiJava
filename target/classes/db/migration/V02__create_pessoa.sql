CREATE TABLE pessoa(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL,
	ativo BOOLEAN NOT NULL,
	logradouro VARCHAR(30),
	numero VARCHAR(10),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(2)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa(nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Wellington', 'Travessa Coronel', '132', 'Barraca do andré', 'Sitio dos gansos', '25570-391', 'São joao de meriti', 'RJ', true);
INSERT INTO pessoa(nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Rosa', 'Travessa Coronel', '132', 'Barraca do andré', 'Sitio dos gansos', '25570-391', 'São joao de meriti', 'RJ', true);
INSERT INTO pessoa(nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Jessica', 'Rua Bolonha', '10', 'Casa 4', 'Acari', '21535-040', 'Rio de Janeiro', 'RJ', true);
INSERT INTO pessoa(nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Matheus', 'Estrada linha verde', '311', 'Barraca do carlão', 'Pavuno', '25570-391', 'Rio de Janeiro', 'RJ', true);

