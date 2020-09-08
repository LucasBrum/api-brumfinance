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
@Table(name = "emprestimo")
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valorPago;
	
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	private String pessoa;
	
}
