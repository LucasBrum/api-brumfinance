package com.brum.financexp.api.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brum.financexp.api.model.Aporte;
import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.repository.AporteRepository;
import com.brum.financexp.api.service.AporteService;
import com.brum.financexp.api.service.AtivoFinanceiroService;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Aporte> criar(@Valid @RequestBody Aporte aporte, HttpServletResponse response) {
		
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroService.findById(aporte.getAtivoFinanceiro().getId());
		
		if(ativoFinanceiro.isPresent()) {
			
			BigDecimal valorJaInvestido = getValorJaInvestidoNoAtivo(ativoFinanceiro);
			
			atualizaQuantidadeAtivo(aporte, ativoFinanceiro);

			atualizaTotalInvestidoNoAtivo(aporte, ativoFinanceiro, valorJaInvestido);
			
			ativoFinanceiroService.atualizarAtivoFinanceiro(aporte.getAtivoFinanceiro().getId(), ativoFinanceiro.get());
			
		}
		
		Aporte aporteSalvo = aporteRepository.save(aporte);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(aporteSalvo);
		
	}

	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public Page<Aporte> listar(Pageable pageable) {
		
		log.info("Listando Aportes.");
		
		return aporteRepository.findByOrderByDataCompraDesc(pageable);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CrossOrigin(origins = "http://localhost:4200")
	public void excluir(@PathVariable Long id) {
		aporteRepository.deleteById(id);
		
		log.info("Aporte excluído com sucesso.");

	}
	
	
	private void atualizaTotalInvestidoNoAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro, BigDecimal valorJaInvestido) {
		BigDecimal valorTotalDoAporte = aporteService.calculaValorTotalDoAporte(aporte.getCusto(), aporte.getQuantidade());
		aporte.setValorTotal(valorTotalDoAporte);

		BigDecimal valorInvestidoAtualizado = valorJaInvestido.add(valorTotalDoAporte);
		
		ativoFinanceiro.get().setTotalDinheiro(valorInvestidoAtualizado);
	}

	private void atualizaQuantidadeAtivo(Aporte aporte, Optional<AtivoFinanceiro> ativoFinanceiro) {
		Integer quantidadeDoAtivo = ativoFinanceiro.get().getQuantidade();
		quantidadeDoAtivo = quantidadeDoAtivo + aporte.getQuantidade();
		ativoFinanceiro.get().setQuantidade(quantidadeDoAtivo);
	}

	private BigDecimal getValorJaInvestidoNoAtivo(Optional<AtivoFinanceiro> ativoFinanceiro) {
		BigDecimal valorJaInvestido = ativoFinanceiro.get().getTotalDinheiro();
		
		if(valorJaInvestido == null) {
			valorJaInvestido = BigDecimal.ZERO;
		}
		return valorJaInvestido;
	}
	

}
