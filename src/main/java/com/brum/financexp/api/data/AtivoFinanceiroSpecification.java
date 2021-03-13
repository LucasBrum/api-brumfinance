package com.brum.financexp.api.data;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import com.brum.financexp.api.entity.AtivoFinanceiro;
import com.brum.financexp.api.vo.AtivoFinanceiroRequestVO;

public class AtivoFinanceiroSpecification {

	public static Specification<AtivoFinanceiro> byFilter(AtivoFinanceiroRequestVO filter) {
		
		Specification<AtivoFinanceiro> specification = Specification.where(Specifications.truePredicate());
		
		if(Strings.isNotBlank(filter.getCodigo())) {
			specification = specification.and(Specifications.codigoLike(filter.getCodigo()));
		}
		
		return specification;
		
	}
}
