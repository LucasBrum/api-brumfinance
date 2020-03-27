package com.brum.financexp.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brum.financexp.api.model.Aporte;
import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.repository.AporteRepository;
import com.brum.financexp.api.service.AporteService;
import com.brum.financexp.api.service.AtivoFinanceiroService;

@RestController
@RequestMapping("/aportes")
public class AporteController {

	@Autowired
	private AporteService aporteService;
	
	@Autowired
	private AporteRepository aporteRepository;
	
	@Autowired
	private AtivoFinanceiroService ativoFinanceiroService;
	
	private String buscaPrecoAtualDoAtivo(String codigoAtivo) throws Exception {
		String valorAcao = aporteService.buscaInformacoesSobreAtivoNoWebService();

		return valorAcao;
	}
	
	@PostMapping
	public ResponseEntity<Aporte> criar(@Valid @RequestBody Aporte aporte, HttpServletResponse response) {
		
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroService.findById(aporte.getAtivoFinanceiro().getId());
		
		if(ativoFinanceiro.isPresent()) {
			Integer quantidadeDoAtivo = ativoFinanceiro.get().getQuantidade();
			quantidadeDoAtivo = quantidadeDoAtivo + aporte.getQuantidade();
			ativoFinanceiro.get().setQuantidade(quantidadeDoAtivo);
			ativoFinanceiroService.atualizarQuantidadeAtivo(aporte.getAtivoFinanceiro().getId(), ativoFinanceiro.get());
		}
		
		BigDecimal valorTotal = aporteService.calculaValorTotalDoAporte(aporte.getCusto(), aporte.getQuantidade());
		
		aporte.setValorTotal(valorTotal);
		
		Aporte aporteSalvo = aporteRepository.save(aporte);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(aporteSalvo);
		
	}
	
	@GetMapping
	public List<Aporte> listar() {
		return aporteRepository.findAll();
	}

//	public static void main(String[] args) throws Exception {
//		RestTemplate restTemplate = new RestTemplate();
//		String stock  = "sp.itsa4";
//		String url = "http://webservices.infoinvest.com.br/cotacoes/cotacoes_handler.asp?&quotes="+stock;
//		
//		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//		
//		String responseBody = response.getBody();
//		
//		ObjectNode node = new ObjectMapper().readValue(responseBody, ObjectNode.class);
//		
//		System.out.println("Valor: " + node.get(stock.toUpperCase()).get("valor").textValue());
//		
//	}

}
