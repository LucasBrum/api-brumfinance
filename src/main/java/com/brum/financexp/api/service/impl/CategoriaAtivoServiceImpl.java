package com.brum.financexp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brum.financexp.api.entity.CategoriaAtivo;
import com.brum.financexp.api.repository.CategoriaAtivoRepository;
import com.brum.financexp.api.service.CategoriaAtivoService;

@Service
public class CategoriaAtivoServiceImpl implements CategoriaAtivoService {
	
	@Autowired
	private CategoriaAtivoRepository categoriaAtivoRepository;

	@Override
	public List<CategoriaAtivo> listarCategorias() {
		
		return categoriaAtivoRepository.findAll();
	}
}
