CREATE TABLE categoria_ativo ( 
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	tipo varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria_ativo(nome, tipo) VALUES ('Ações', 'Renda Variável');
INSERT INTO categoria_ativo(nome, tipo) VALUES ('FIIs', 'Renda Variável');
INSERT INTO categoria_ativo(nome, tipo) VALUES ('Tesouro Direto', 'Renda Fixa');
INSERT INTO categoria_ativo(nome, tipo) VALUES ('ETF', 'Renda Variável');
INSERT INTO categoria_ativo(nome, tipo) VALUES ('Renda Fixa', 'Renda Fixa');
INSERT INTO categoria_ativo(nome, tipo) VALUES ('Bitcoin', 'Renda Variável');
