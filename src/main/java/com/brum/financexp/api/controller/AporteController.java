package com.brum.financexp.api.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.brum.financexp.api.dto.AporteDto;
import com.brum.financexp.api.entity.Aporte;
import com.brum.financexp.api.entity.AtivoFinanceiro;
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


	@PostMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Aporte> criar(@Valid @RequestBody AporteDto aporte) {

		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroService.findById(aporte.getAtivoFinanceiro().getId());

		if (ativoFinanceiro.isPresent()) {

			BigDecimal valorJaInvestido = aporteService.getValorJaInvestidoNoAtivo(ativoFinanceiro);
			aporteService.atualizaQuantidadeDoAtivo(aporte, ativoFinanceiro);
			aporteService.atualizaTotalInvestidoNoAtivo(aporte, ativoFinanceiro, valorJaInvestido);

			ativoFinanceiroService.atualizarAtivoFinanceiro(aporte.getAtivoFinanceiro().getId(), ativoFinanceiro.get());

		}
		
		return new ResponseEntity<>(aporteService.criar(aporte), HttpStatus.CREATED);

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

}
