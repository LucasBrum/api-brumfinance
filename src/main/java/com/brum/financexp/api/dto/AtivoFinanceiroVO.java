package com.brum.financexp.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtivoFinanceiroVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String valor;
}
