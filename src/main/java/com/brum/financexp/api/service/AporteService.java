package com.brum.financexp.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.brum.financexp.api.dto.AporteDto;
import com.brum.financexp.api.entity.Aporte;
import com.brum.financexp.api.entity.AtivoFinanceiro;

public interface AporteService {
	
	Aporte criar(AporteDto aporte);
	
	BigDecimal calculaValorTotalDoAporte(BigDecimal custo, Integer quantidade);
	BigDecimal getValorJaInvestidoNoAtivo(Optional<AtivoFinanceiro> ativoFinanceiro);
	
	void atualizaTotalInvestidoNoAtivo(AporteDto aporte, Optional<AtivoFinanceiro> ativoFinanceiro, BigDecimal valorJaInvestido);
	void atualizaQuantidadeDoAtivo(AporteDto aporte, Optional<AtivoFinanceiro> ativoFinanceiro);
	
	
}
