package com.brum.financexp.api.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.brum.financexp.api.data.AtivoFinanceiroSpecification;
import com.brum.financexp.api.model.AtivoFinanceiro;
import com.brum.financexp.api.repository.AtivoFinanceiroRepository;
import com.brum.financexp.api.service.AtivoFinanceiroService;
import com.brum.financexp.api.vo.AtivoFinanceiroRequestVO;
import com.brum.financexp.api.vo.IndiceBovespaVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AtivoFinanceiroServiceImpl implements AtivoFinanceiroService {

	@Autowired
	private AtivoFinanceiroRepository ativoFinanceiroRepository;

	@Override
	public Optional<AtivoFinanceiro> findById(Long id) {
		Optional<AtivoFinanceiro> ativoFinanceiro = ativoFinanceiroRepository.findById(id);
		if (ativoFinanceiro.isPresent()) {

		}
		return ativoFinanceiro;
	}

	@Override
	public AtivoFinanceiro atualizarAtivoFinanceiro(Long id, AtivoFinanceiro ativoFinanceiro) {
		AtivoFinanceiro ativoFinanceiroEncontrado = ativoFinanceiroRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));

		BeanUtils.copyProperties(ativoFinanceiro, ativoFinanceiroEncontrado);

		return ativoFinanceiroRepository.save(ativoFinanceiroEncontrado);

	}

	@Override
	public IndiceBovespaVO getInfoIndiceBovespaOnline() throws IOException {
		
		String url = "https://www.infomoney.com.br/cotacoes/ibovespa";
		Document document = Jsoup.connect(url).get();
		String pontos = document.body().select("div.value > p" /*css selector*/).get(0).text() + " Pontos";
		String porcentagem = document.body().select("div.percentage > p" /*css selector*/).get(0).text();
		String minimo = document.body().select("div.minimo > p" /*css selector*/).get(0).text();
		String maximo = document.body().select("div.maximo > p" /*css selector*/).get(0).text();
		
		IndiceBovespaVO indiceBovespaVO = new IndiceBovespaVO();
		indiceBovespaVO.setPontos(pontos);
		indiceBovespaVO.setVariacaoDia(porcentagem);
		indiceBovespaVO.setMinDia(minimo);
		indiceBovespaVO.setMaxDia(maximo);
		
		return indiceBovespaVO;
	}

	@Override
	public List<AtivoFinanceiro> pesquisar(AtivoFinanceiroRequestVO ativoFinanceiroRequestVO) {
		Specification<AtivoFinanceiro> filter = AtivoFinanceiroSpecification.byFilter(ativoFinanceiroRequestVO);

		return this.ativoFinanceiroRepository.findAll(filter);
	}

	@Override
	public List<AtivoFinanceiro> getListaComCotacaoAtualDosAtivos(List<AtivoFinanceiro> ativosFinanceiros) throws IOException {

 		for (AtivoFinanceiro ativoFinanceiro : ativosFinanceiros) {
 			log.info("Buscando informações do Ativo: {}", ativoFinanceiro.getCodigo());
			if (ativoFinanceiro.getCategoriaAtivo().getNome().equals("FIIs")) {
				getCotacaoAtualFundosImobiliarios(ativoFinanceiro);
				
				BigDecimal porcentagem = calcularTotalEmPorcentagem(ativoFinanceiro);
				
				ativoFinanceiro.setTotalPorcentagem(porcentagem);
				
			}
			
			if (ativoFinanceiro.getCategoriaAtivo().getNome().equals("Ações")) {
				getCotacaoAtualAcoes(ativoFinanceiro);
				
				BigDecimal porcentagem = calcularTotalEmPorcentagem(ativoFinanceiro);
				
				ativoFinanceiro.setTotalPorcentagem(porcentagem);
				
				ativoFinanceiro.setTotalPorcentagem(porcentagem);
				
			}
		}

		return ativosFinanceiros;
	}

	private BigDecimal calcularTotalEmPorcentagem(AtivoFinanceiro ativoFinanceiro) {
		BigDecimal totalAplicadoEmDinheiroBruto = ativoFinanceiro.getPrecoAtual().multiply(new BigDecimal(ativoFinanceiro.getQuantidade()));
		
		BigDecimal diferencaEntreValorBrutoEValorAplicado = totalAplicadoEmDinheiroBruto.subtract(ativoFinanceiro.getTotalDinheiro());
		BigDecimal porcentagem = diferencaEntreValorBrutoEValorAplicado
				.divide(ativoFinanceiro.getTotalDinheiro(), 4, RoundingMode.HALF_UP)
				.multiply(new BigDecimal(100)).setScale(2);
		
		
		return porcentagem;
	}

	private void getCotacaoAtualAcoes(AtivoFinanceiro ativoFinanceiro) throws IOException {
		String[] nomeDoAtivoSeparado = ativoFinanceiro.getNome().split(" ");
		String primeiroNomeDoAtivo = nomeDoAtivoSeparado[0].toLowerCase();
		
		String url = "https://www.infomoney.com.br/cotacoes/" + primeiroNomeDoAtivo + "-" + ativoFinanceiro.getCodigo().toLowerCase() + "";
		Document document = Jsoup.connect(url).get();
		String cotacaoAtual = document.body().select("div.value > p" /*css selector*/).get(0).text();
		
		converteCotacaoAtualToBigDecimal(ativoFinanceiro, cotacaoAtual);
	}

	private void getCotacaoAtualFundosImobiliarios(AtivoFinanceiro ativoFinanceiro) throws IOException {
		String url = "https://www.infomoney.com.br/cotacoes/fundos-imobiliarios-" + ativoFinanceiro.getCodigo() + "/grafico/";
		Document document = Jsoup.connect(url).get();
		String cotacaoAtual = document.body().select("div.value > p" /*css selector*/).get(0).text();
		
		converteCotacaoAtualToBigDecimal(ativoFinanceiro, cotacaoAtual);
	}

	private void converteCotacaoAtualToBigDecimal(AtivoFinanceiro ativoFinanceiro, String cotacaoAtual) {
		cotacaoAtual = cotacaoAtual.replace(",", ".");
		ativoFinanceiro.setPrecoAtual(new BigDecimal(cotacaoAtual));
	}

	
}
