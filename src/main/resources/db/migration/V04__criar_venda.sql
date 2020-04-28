CREATE TABLE venda (
	
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_venda DATE NOT NULL,
	id_ativo_financeiro BIGINT(20) NOT NULL,
	quantidade INT(20),
	preco_compra DECIMAL(10,2) NOT NULL,	
	preco_venda DECIMAL(10,2),
    lucro DECIMAL(10,2),
    porcentagem DECIMAL(10,2),
	FOREIGN KEY (id_ativo_financeiro) REFERENCES ativo_financeiro(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
