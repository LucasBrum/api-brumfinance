package com.brum.financexp.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brum.financexp.api.model.Aporte;

public interface AporteRepository extends JpaRepository<Aporte, Long> {

	Page<Aporte>findByOrderByDataCompraDesc(Pageable pageable);
}
