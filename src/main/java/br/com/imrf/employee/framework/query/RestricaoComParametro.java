package br.com.imrf.employee.framework.query;

import static br.com.imrf.employee.framework.query.Consulta.DOIS_PONTOS;

public class RestricaoComParametro extends AbstractRestricao {

	@Override
	public void montarRetricaoParticular(Restricoes restricoes) {
		restricoes.seAtributoCompostoRetiraPonto();
		getSelect().append(DOIS_PONTOS).append(restricoes.getNome());
	}

}
