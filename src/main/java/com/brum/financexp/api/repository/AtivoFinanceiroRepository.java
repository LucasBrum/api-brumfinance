package com.brum.financexp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brum.financexp.api.model.AtivoFinanceiro;

public interface AtivoFinanceiroRepository extends JpaRepository<AtivoFinanceiro, Long> {

	//TODO: Criar metodo que irá fazer update na base com os precos e datas atualizados
	//void updatePrecoAtualAtivos();
}
