package com.brum.financexp.api.controller;

import javax.servlet.http.HttpServletResponse;
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

import com.brum.financexp.api.model.Venda;
import com.brum.financexp.api.repository.VendaRepository;
import com.brum.financexp.api.service.VendaService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda, HttpServletResponse response) {

		vendaService.calcularValores(venda);
		
		Venda vendaSalva = vendaRepository.save(venda);
		log.info("Venda de Ativo {} efetuada com sucesso.", venda.getAtivo());
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CrossOrigin(origins = "http://localhost:4200")
	public void excluir(@PathVariable Long id) {
		vendaRepository.deleteById(id);
		log.info("Venda excluída com sucesso.");

	}
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public Page<Venda> listar(Pageable pageable) {
		
		log.info("Listando Vendas.");
		return vendaRepository.findByOrderByDataVendaAsc(pageable);
	}

}
