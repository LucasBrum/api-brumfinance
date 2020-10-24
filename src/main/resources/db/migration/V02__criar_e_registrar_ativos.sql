CREATE TABLE ativo_financeiro (
	
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	codigo VARCHAR(40) NOT NULL,
    preco_atual DECIMAL(10,2),
    total_dinheiro DECIMAL(10,2),
    total_porcentagem DECIMAL(10,2),
	quantidade INT(20),
    data_atualizacao DATE,
	id_categoria BIGINT(20) NOT NULL,
	descricao VARCHAR(1000) NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria_ativo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

