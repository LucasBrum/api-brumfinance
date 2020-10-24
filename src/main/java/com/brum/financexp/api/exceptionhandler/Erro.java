package com.brum.financexp.api.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erro {

	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	
	public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
}
