package com.brum.financexp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brum.financexp.api.model.CategoriaAtivo;
import com.brum.financexp.api.service.CategoriaAtivoService;

@RestController
@RequestMapping("/categorias")
public class CategoriaAtivoController {
	
	@Autowired
	private CategoriaAtivoService categoriaAtivoService;

	@GetMapping
	public List<CategoriaAtivo> listar() {
		
		return categoriaAtivoService.listarCategorias();
	}
}
