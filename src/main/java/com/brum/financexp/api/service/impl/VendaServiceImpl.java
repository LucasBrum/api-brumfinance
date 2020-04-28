package com.brum.financexp.api.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.brum.financexp.api.model.Venda;
import com.brum.financexp.api.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService{

	public void calcularValores(Venda venda) {
		BigDecimal totalAplicado = getValorTotalAplicado(venda);
		BigDecimal totalVendido = getValorTotalVendido(venda);
		
		BigDecimal lucro = calcularLucro(totalAplicado, totalVendido);
		BigDecimal porcentagem = calcularLucroPorcentagem(totalAplicado, lucro);
	
		venda.setLucro(lucro);
		venda.setPorcentagem(porcentagem);
	}

	private BigDecimal calcularLucroPorcentagem(BigDecimal totalAplicado, BigDecimal lucro) {
		BigDecimal porcentagem = lucro.divide(totalAplicado, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
		return porcentagem;
	}

	private BigDecimal calcularLucro(BigDecimal totalAplicado, BigDecimal totalVendido) {
		BigDecimal lucro = totalVendido.subtract(totalAplicado).setScale(2);
		return lucro;
	}

	private BigDecimal getValorTotalVendido(Venda venda) {
		BigDecimal totalVendido = venda.getPrecoVenda().multiply(new BigDecimal(venda.getQuantidade()));
		return totalVendido;
	}

	private BigDecimal getValorTotalAplicado(Venda venda) {
		BigDecimal totalAplicado = venda.getPrecoCompra().multiply(new BigDecimal(venda.getQuantidade()));
		return totalAplicado;
	}
}
