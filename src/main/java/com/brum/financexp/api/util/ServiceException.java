package com.brum.financexp.api.util;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5274625641608417401L;

	private final String chave;
	private final String campo;
	private final Serializable valor;
	private final HttpStatus status;
}
