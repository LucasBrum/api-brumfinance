CREATE TABLE aporte (
	
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_compra DATE NOT NULL,
	id_ativo_financeiro BIGINT(20) NOT NULL,
	quantidade INT(20),
	custo DECIMAL(10,2) NOT NULL,	
	valor_total DECIMAL(10,2),
	FOREIGN KEY (id_ativo_financeiro) REFERENCES ativo_financeiro(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;