package com.brum.financexp.api.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.vo.AtivoFinanceiroRequestVO;

public interface AtivoFinanceiroService {

	Optional<AtivoFinanceiro> findById(Long id);

	void atualizarQuantidadeAtivo(Long id, AtivoFinanceiro ativoFinanceiro);
	HashMap<String, BigDecimal> atualizarAtivosViaGoogleSheets() throws IOException, GeneralSecurityException;
	
	String getInformacoesAtivoFromBovespaWebService(List<AtivoFinanceiro> ativosFinanceiros) throws IOException;

	List<AtivoFinanceiro> pesquisar(AtivoFinanceiroRequestVO filter);
}
