CREATE TABLE historico_aporte (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_compra DATE NOT NULL,
	quantidade INT NOT NULL,
	custo DECIMAL(10,2) NOT NULL,
	preco_atual DECIMAL(10,2) NOT NULL,
	ganho_valor DECIMAL(10,2) NOT NULL,
	ganho_porcentagem DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	id_categoria BIGINT(20) NOT NULL,
	id_ativo BIGINT(20) NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria_ativo(id),
	FOREIGN KEY (id_ativo) REFERENCES ativo_financeiro(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
