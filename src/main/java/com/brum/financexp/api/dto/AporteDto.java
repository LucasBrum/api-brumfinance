package com.brum.financexp.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.brum.financexp.api.entity.AtivoFinanceiro;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AporteDto {

	private LocalDate dataCompra;
	
	private AtivoFinanceiro ativoFinanceiro;
	
	private Integer quantidade;
	
	private BigDecimal custo;
	
	@JsonIgnore
	private BigDecimal valorTotal;
}
