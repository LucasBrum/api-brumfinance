package com.brum.financexp.api.service;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AporteService {

	String buscaInformacoesSobreAtivoNoWebService() throws JsonProcessingException, JsonMappingException;

	BigDecimal calculaValorTotalDoAporte(BigDecimal custo, Integer quantidade);
}
