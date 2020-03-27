package com.brum.financexp.api.util.google;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;

public class GoogleSheetsLiveTest {

	private static final String SPREADSHEET_ID = "1FUpj4vi4N2s2xMPP1Q3GZTyUQUVd-QIScqb0XFxZONQ";
	private static Sheets sheetsService;

	@BeforeAll
	public static void setup() throws GeneralSecurityException, IOException {
		sheetsService = SheetsServiceUtil.getSheetsService();
	}

	@Test
	public void testGetValoresDosFundosImobiliarios() throws IOException {

		HashMap<String, BigDecimal> fiiHashMap = new HashMap<String, BigDecimal>();

		List<String> codigosFIIsLista = Arrays.asList("A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");
		BatchGetValuesResponse readResultCodigosAtivosLista = sheetsService.spreadsheets().values()
				.batchGet(SPREADSHEET_ID).setRanges(codigosFIIsLista).execute();

		List<String> valoresAtualizadosLista = Arrays.asList("B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10");
		BatchGetValuesResponse readResultValoresAtualizados = sheetsService.spreadsheets().values()
				.batchGet(SPREADSHEET_ID).setRanges(valoresAtualizadosLista).execute();

		for (int i = 0; i < readResultValoresAtualizados.getValueRanges().size(); i++) {
			Object codigoAtivo = readResultCodigosAtivosLista.getValueRanges().get(i).getValues().get(0).get(0);
			Object precoAtual = readResultValoresAtualizados.getValueRanges().get(i).getValues().get(0).get(0);
			
			String precoAtualizadoFormatado = precoAtual.toString().replace(",", "."); 
			
			fiiHashMap.put(codigoAtivo.toString(), new BigDecimal(precoAtualizadoFormatado));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(fiiHashMap);
		
		System.out.println(json);

	}
}
