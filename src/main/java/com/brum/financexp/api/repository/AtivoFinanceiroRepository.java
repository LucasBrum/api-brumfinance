package com.brum.financexp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.brum.financexp.api.entity.AtivoFinanceiro;

public interface AtivoFinanceiroRepository extends JpaRepository<AtivoFinanceiro, Long>, JpaSpecificationExecutor<AtivoFinanceiro> {

	List<AtivoFinanceiro> findByOrderByCodigoAsc();
}
