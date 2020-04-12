package com.brum.financexp.api.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoFinanceiroVO {

	private String codigo;
	private String nome;
	
	private LocalDate data;
	
	private BigDecimal precoAbertura;
	private BigDecimal precoMinimo;
	private BigDecimal precoMaximo;
	private BigDecimal precoMedio;
	private BigDecimal precoAtual;
	
}
