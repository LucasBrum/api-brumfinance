package com.brum.financexp.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.brum.financexp.api.model.Aporte;
import com.brum.financexp.api.model.AtivoFinanceiro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AporteService {

	String buscaInformacoesSobreAtivoNoWebService() throws JsonProcessingException, JsonMappingException;

	BigDecimal calculaValorTotalDoAporte(BigDecimal custo, Integer quantidade);
	BigDecimal getValorJaInvestidoNoAtivo(Optional<AtivoFinanceiro> ativoFinanceiro);
	
	void atualizaTotalInvestidoNoAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro, BigDecimal valorJaInvestido);
	void atualizaQuantidadeDoAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro);
}
