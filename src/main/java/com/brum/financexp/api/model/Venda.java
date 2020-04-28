package com.brum.financexp.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "venda")
@AllArgsConstructor
@NoArgsConstructor
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "data_venda")
	private LocalDate dataVenda;
	
	@NotNull
	private String ativo;
	
	@NotNull
	private Integer quantidade;
	
	@NotNull
	@Column(name = "preco_compra")
	private BigDecimal precoCompra;
	
	@NotNull
	@Column(name = "preco_venda")
	private BigDecimal precoVenda;
	
	private BigDecimal lucro;
	
	private BigDecimal porcentagem;
}
