package com.brum.financexp.api.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.brum.financexp.api.data.AtivoFinanceiroSpecification;
import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.repository.AtivoFinanceiroRepository;
import com.brum.financexp.api.service.AtivoFinanceiroService;
import com.brum.financexp.api.util.google.SheetsServiceUtil;
import com.brum.financexp.api.vo.AtivoFinanceiroRequestVO;
import com.brum.financexp.api.vo.AtivoFinanceiroVO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;

@Service
public class AtivoFinanceiroServiceImpl implements AtivoFinanceiroService {

	private static final String SPREADSHEET_ID = "1FUpj4vi4N2s2xMPP1Q3GZTyUQUVd-QIScqb0XFxZONQ";
	private static final String PIPE_SEPARATOR = "|";
	
	@Autowired
	private AtivoFinanceiroRepository ativoFinanceiroRepository;
	
	private static Sheets sheetsService;
	
	@Override
	public Optional<AtivoFinanceiro> findById(Long id) {
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroRepository.findById(id);
		if(ativoFinanceiro.isPresent()) {
			
		}
		return ativoFinanceiro;
	}

	@Override
	public void atualizarQuantidadeAtivo(Long id, AtivoFinanceiro ativoFinanceiro) {
		AtivoFinanceiro ativoFinanceiroEncontrado = ativoFinanceiroRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(ativoFinanceiroEncontrado, ativoFinanceiro );
		
		ativoFinanceiroRepository.save(ativoFinanceiroEncontrado);
		
	}
	
	public String getInformacoesAtivoFromBovespaWebService(List<AtivoFinanceiro> ativosFinanceiros) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		
		
		//Monta Lista de Ativos para URL
		
		String pipe = PIPE_SEPARATOR;
		StringBuilder stringBuild = new StringBuilder(pipe);
		for (AtivoFinanceiro ativoFinanceiro : ativosFinanceiros) {
			stringBuild.append(ativoFinanceiro.getCodigo());
			stringBuild.append(pipe);
		}
		
		System.out.println(stringBuild);
		
		
		StringBuffer sb = new StringBuffer();
		
		URL url = new URL("http://bvmf.bmfbovespa.com.br/cotacoes2000/FormConsultaCotacoes.asp?strListaCodigos=BCFF11");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		String str;
		
		while((str = in.readLine()) != null) {
			sb.append(str);
		}
		
		AtivoFinanceiroVO papelFinanceiroVO = xmlMapper.readValue(sb.toString(), AtivoFinanceiroVO.class);
		
		System.out.println("Papel Financeiro VO: " + papelFinanceiroVO.getNome());
		
		return null;
	}

	@Override
	public HashMap<String, BigDecimal> atualizarAtivosViaGoogleSheets() throws IOException, GeneralSecurityException {
		sheetsService = SheetsServiceUtil.getSheetsService();
		List<AtivoFinanceiro> ativosFinanceirosLista = ativoFinanceiroRepository.findAll();
		
		HashMap<String, BigDecimal> fiiHashMap = new HashMap<String, BigDecimal>();

		List<String> codigosFIIsLista = Arrays.asList("A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");
		BatchGetValuesResponse readResultCodigosAtivosLista = sheetsService.spreadsheets().values()
				.batchGet(SPREADSHEET_ID).setRanges(codigosFIIsLista).execute();

		List<String> codigosAcoesLista = Arrays.asList("B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10");
		BatchGetValuesResponse readResultValoresAtualizados = sheetsService.spreadsheets().values()
				.batchGet(SPREADSHEET_ID).setRanges(codigosAcoesLista).execute();

		for (int i = 0; i < readResultValoresAtualizados.getValueRanges().size(); i++) {
			
			Object codigoAtivo = readResultCodigosAtivosLista.getValueRanges().get(i).getValues().get(0).get(0);
			Object precoAtual = readResultValoresAtualizados.getValueRanges().get(i).getValues().get(0).get(0);
			
			for (AtivoFinanceiro ativoFinanceiro : ativosFinanceirosLista) {
				if(ativoFinanceiro.getCodigo().equals(codigoAtivo)) {
					Long idAtivoFinanceiro = ativoFinanceiro.getId();
				}
			}
			
			
			String precoAtualizadoFormatado = precoAtual.toString().replace(",", "."); 
			
			fiiHashMap.put(codigoAtivo.toString(), new BigDecimal(precoAtualizadoFormatado));
		}
		
		return fiiHashMap;
		
	}

	@Override
	public List<AtivoFinanceiro> pesquisar(AtivoFinanceiroRequestVO ativoFinanceiroRequestVO) {
		Specification<AtivoFinanceiro> filter = AtivoFinanceiroSpecification.byFilter(ativoFinanceiroRequestVO);
		
		return this.ativoFinanceiroRepository.findAll(filter);
	}
	
}
