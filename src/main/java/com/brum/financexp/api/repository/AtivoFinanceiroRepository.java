package com.brum.financexp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.brum.financexp.api.model.AtivoFinanceiro;

public interface AtivoFinanceiroRepository extends JpaRepository<AtivoFinanceiro, Long>, JpaSpecificationExecutor<AtivoFinanceiro> {

	//TODO: Criar metodo que irá fazer update na base com os precos e datas atualizados
	//void updatePrecoAtualAtivos();
	
	List<AtivoFinanceiro> findByOrderByCodigoAsc();
}
