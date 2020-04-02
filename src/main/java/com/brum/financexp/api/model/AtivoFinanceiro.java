package com.brum.financexp.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "ativo_financeiro")
public class AtivoFinanceiro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String codigo;
	
	@NotEmpty
	private String descricao;
	
	@Column(name = "preco_atual")
	private BigDecimal precoAtual;
	
	@Column(name = "total_dinheiro")
	private BigDecimal totalDinheiro;
	
	@Column(name = "total_porcentagem")
	private BigDecimal totalPorcentagem;
	
	@Column(name = "data_atualizacao")
	private LocalDate dataAtualizacao;
	
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaAtivo categoriaAtivo;

}
