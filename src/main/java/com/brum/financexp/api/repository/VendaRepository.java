package com.brum.financexp.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brum.financexp.api.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	Page<Venda> findByOrderByDataVendaAsc(Pageable pageable);
}
