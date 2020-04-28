package com.brum.financexp.api.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brum.financexp.api.model.Aporte;
import com.brum.financexp.api.model.AtivoFinanceiro;
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
	
	public BigDecimal getValorJaInvestidoNoAtivo(Optional<AtivoFinanceiro> ativoFinanceiro) {
		BigDecimal valorJaInvestido = ativoFinanceiro.get().getTotalDinheiro();
		
		if(valorJaInvestido == null) {
			valorJaInvestido = BigDecimal.ZERO;
		}
		return valorJaInvestido;
	}
	
	public void atualizaTotalInvestidoNoAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro, BigDecimal valorJaInvestido) {
		BigDecimal valorTotalDoAporte = calculaValorTotalDoAporte(aporte.getCusto(), aporte.getQuantidade());
		aporte.setValorTotal(valorTotalDoAporte);

		BigDecimal valorInvestidoAtualizado = valorJaInvestido.add(valorTotalDoAporte);
		
		ativoFinanceiro.get().setTotalDinheiro(valorInvestidoAtualizado);
	}
	
	public void atualizaQuantidadeDoAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro) {
		if(ativoFinanceiro.get().getQuantidade() == null) {
			ativoFinanceiro.get().setQuantidade(0);
		}
		
		Integer quantidadeDoAtivo = ativoFinanceiro.get().getQuantidade();
		quantidadeDoAtivo = quantidadeDoAtivo + aporte.getQuantidade();
		ativoFinanceiro.get().setQuantidade(quantidadeDoAtivo);
	}
}
