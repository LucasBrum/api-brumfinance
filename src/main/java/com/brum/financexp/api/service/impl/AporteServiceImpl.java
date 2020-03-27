package com.brum.financexp.api.service.impl;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brum.financexp.api.service.AporteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AporteServiceImpl implements AporteService{

	public String buscaInformacoesSobreAtivoNoWebService() throws JsonProcessingException, JsonMappingException {
		RestTemplate restTemplate = new RestTemplate();
		String stock  = "sp.itsa4";
		String url = "http://webservices.infoinvest.com.br/cotacoes/cotacoes_handler.asp?&quotes="+stock;
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String responseBody = response.getBody();
		
		ObjectNode node = new ObjectMapper().readValue(responseBody, ObjectNode.class);
		
		String valorAcao = node.get(stock.toUpperCase()).get("valor").textValue();
		System.out.println("Valor: " + valorAcao);
		return valorAcao;
	}

	@Override
	public BigDecimal calculaValorTotalDoAporte(BigDecimal custo, Integer quantidade) {
		
		BigDecimal valorTotal = custo.multiply(new BigDecimal(quantidade));
		
		return valorTotal;
	}
}
