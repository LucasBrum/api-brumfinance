package com.brum.financexp.api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "aporte")
@AllArgsConstructor
@NoArgsConstructor
public class Aporte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "data_compra")
	private LocalDate dataCompra;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "id_ativo_financeiro")
	private AtivoFinanceiro ativoFinanceiro;
	
	@NotNull
	private Integer quantidade;
	
	@NotNull
	private BigDecimal custo;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
}
