CREATE TABLE ativo_financeiro (
	
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	codigo VARCHAR(20) NOT NULL,
    preco_atual DECIMAL(10,2),
	quantidade INT(20),
    data_atualizacao DATE,
	id_categoria BIGINT(20) NOT NULL,
	descricao VARCHAR(1000) NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria_ativo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('BTG Pactual', 'BCFF11', 0, 2, 'BCFF11 é um fundo imobiliário do tipo papel FOF – Fundo de fundos. Seus investimentos são destinados primeiramente a aquisição de cotas de outros fundos imobiliários.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('RBR Alpha', 'RBRF11', 0, 2, 'RBRF11 é um fundo imobiliário do tipo FOF – Fundo de fundos. Seus investimentos são destinados com prioridade nas aplicações em outros FIIs.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('KINEA RENDA IMOBILIÁRIA', 'KNRI11', 0, 2, 'KNRI11 é um fundo imobiliário do tipo tijolo. Seus investimentos são destinados em sua maioria nos empreendimentos imobiliários físicos.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('GGR COVEPI RENDA', 'GGRC11', 0, 2, 'GGRC11 é um fundo imobiliário do tipo tijolo. Seus investimentos são direcionados com prioridade em empreendimentos de galpões logísticos.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('HEDGE BRASIL SHOPPING', 'HGBS11', 0, 2, 'HGBS11 é um fundo imobiliário do tipo tijolo. Seus investimentos são focados em propriedades de shoppings centers.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('HOTEL MAXINVEST', 'HTMX11', 0, 2, 'HTMX11 é um fundo imobiliário do tipo tijolo, ou seja, seus investimentos tem como base principal, empreendimentos imobiliários físicos. Fundos de tijolo como o HTMX11 tem o objetivo de comprar ou construir imóveis para alugar e gerar uma renda mensal. Fundos desse tipo, geralmente buscam uma renda constante com potencial de valorização e reajustes de aluguéis.');
INSERT INTO ativo_financeiro (nome, codigo, quantidade, id_categoria, descricao) VALUES ('CONTINENTAL SQUARE FARIA LIMA', 'FLMA11', 0, 2, 'FLMA11 é um fundo imobiliário do tipo tijolo. Seus investimentos são direcionados em propriedades comercias do segmento escritórios. Fundos de tijolo como o FLMA11 possuem essa característica pelo motivo de obterem imóveis físicos, podendo o fundo rentabilizar através da compra ou construção para gerar aluguéis ou também ganhar com a venda dos imóveis.');
