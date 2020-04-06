package com.brum.financexp.api.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.repository.AtivoFinanceiroRepository;
import com.brum.financexp.api.service.AtivoFinanceiroService;

@RestController
@RequestMapping("/ativos")
public class AtivoFinanceiroController {

	@Autowired
	private AtivoFinanceiroRepository ativoFinanceiroRepository;

	@Autowired
	private AtivoFinanceiroService ativoFinanceiroService;

	@PostMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<AtivoFinanceiro> criar(@Valid @RequestBody AtivoFinanceiro ativoFinanceiro,
			HttpServletResponse response) {
		AtivoFinanceiro ativoFinanceiroSalvo = ativoFinanceiroRepository.save(ativoFinanceiro);

		return ResponseEntity.status(HttpStatus.CREATED).body(ativoFinanceiroSalvo);
	}
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public List<AtivoFinanceiro> listar() {
		return ativoFinanceiroRepository.findAll();
	}
	
	@GetMapping("/preco-atual")
	public HashMap<String, BigDecimal> buscaPrecoAtualDosAtivos() throws IOException, GeneralSecurityException {
		
		HashMap<String, BigDecimal> ativos = ativoFinanceiroService.atualizarAtivosViaGoogleSheets();
		
		return ativos;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AtivoFinanceiro> findById(@PathVariable Long id) {
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroRepository.findById(id);

		return ativoFinanceiro.isPresent() ? ResponseEntity.ok(ativoFinanceiro.get())
				: ResponseEntity.notFound().build();
	}

	@PutMapping
	public String atualizarAtivos() {
		List<AtivoFinanceiro> ativosFinanceirosLista = ativoFinanceiroRepository.findAll();

		try {
			ativoFinanceiroService.getInformacoesAtivoFromBovespaWebService(ativosFinanceirosLista);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}
	
	@PutMapping("/google-sheets")
	public void atualizarAtivosViaGoogleSheets() {
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CrossOrigin(origins = "http://localhost:4200")
	public void remover(@PathVariable Long id) {
		ativoFinanceiroRepository.deleteById(id);
	}
	
	

}
