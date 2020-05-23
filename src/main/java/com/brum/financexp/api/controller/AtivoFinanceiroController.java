package com.brum.financexp.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.brum.financexp.api.vo.AtivoFinanceiroRequestVO;
import com.brum.financexp.api.vo.IndiceBovespaVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
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

		log.info("Ativo Financeiro cadastrado com sucesso. Nome do Ativo: {}", ativoFinanceiroSalvo.getNome());
		return ResponseEntity.status(HttpStatus.CREATED).body(ativoFinanceiroSalvo);
	}
	
	@PutMapping("/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<AtivoFinanceiro> atualizar(@PathVariable Long id, @Valid @RequestBody AtivoFinanceiro ativoFinanceiro) {
		AtivoFinanceiro ativoSalvo = ativoFinanceiroService.atualizarAtivoFinanceiro(id, ativoFinanceiro);
		
		return ResponseEntity.ok(ativoSalvo);
	}
	
	@GetMapping("cotacao/ibovespa")
	@CrossOrigin(origins = "http://localhost:4200")
	public IndiceBovespaVO getInfoIndiceBovespa() throws IOException {
		
		return ativoFinanceiroService.getInfoIndiceBovespaOnline();
	}
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public List<AtivoFinanceiro> listar(Pageable pageable) {

		log.info("Listando Ativos Financeiros.");
		List<AtivoFinanceiro> ativosFinanceirosList = ativoFinanceiroRepository.findByOrderByCodigoAsc();
		
		try {
			ativosFinanceirosList = ativoFinanceiroService.getListaComCotacaoAtualDosAtivos(ativosFinanceirosList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ativosFinanceirosList;
	}

	@GetMapping("/listar")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<AtivoFinanceiro> listarAtivos() {

		log.info("Listando todos os ativos financeiros existentes no sistema.");
		List<AtivoFinanceiro> ativosFinanceirosList = ativoFinanceiroRepository.findByOrderByCodigoAsc();
		
		
		
		return ativosFinanceirosList;
	}

	@GetMapping("/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<AtivoFinanceiro> findById(@PathVariable Long id) {
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroRepository.findById(id);

		return ativoFinanceiro.isPresent() ? ResponseEntity.ok(ativoFinanceiro.get())
				: ResponseEntity.notFound().build();
	}

	@PutMapping("/google-sheets")
	public void atualizarAtivosViaGoogleSheets() {

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CrossOrigin(origins = "http://localhost:4200")
	public void excluir(@PathVariable Long id) {
		ativoFinanceiroRepository.deleteById(id);

		log.info("Ativo financeiro excluído com sucesso. Id do Ativo: {}", id);

	}

	@PostMapping("/pesquisar")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<AtivoFinanceiro>> pesquisarAtivos(@RequestBody AtivoFinanceiroRequestVO filter) {

		List<AtivoFinanceiro> ativoFinanceiroList = ativoFinanceiroService.pesquisar(filter);

		return new ResponseEntity<>(ativoFinanceiroList, HttpStatus.OK);

	}

}
