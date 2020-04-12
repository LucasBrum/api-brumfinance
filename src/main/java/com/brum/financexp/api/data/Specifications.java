package com.brum.financexp.api.data;

import org.springframework.data.jpa.domain.Specification;

import com.brum.financexp.api.model.AtivoFinanceiro;

public interface Specifications {
	
	public static <T> Specification<T> truePredicate() {
		return (entity, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
	}

	public static <T> Specification<T> falsePredicate() {
		return (entity, criteriaQuery, criteriaBuilder) -> criteriaBuilder.disjunction();
	}

	public static Specification<AtivoFinanceiro> codigoLike(String codigo) {
		return (root, query, builder) -> builder.like(root.get("codigo"), "%" + codigo + "%");
	}
}
