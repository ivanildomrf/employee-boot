package br.com.imrf.employee.framework.query;

import static br.com.imrf.employee.framework.query.Consulta.ESPACO;
import static br.com.imrf.employee.framework.query.Consulta.PONTO;

public class RestricaoComParametroLowerCase extends RestricaoComParametro {

	private static final String ABRE_PARENTESES = "(";
	private static final String FECHA_PARENTESES = ")";
	private static final String LOWER = "lower";

	@Override
	protected void montarRestricaoPadrao(Restricoes restricoes) {
		if (restricoes.getValor() instanceof String) {
			getSelect().append(ESPACO).append(LOWER).append(ABRE_PARENTESES).append(retornarAliasCorreto(restricoes))
					.append(PONTO).append(restricoes.getNome()).append(FECHA_PARENTESES).append(ESPACO)
					.append(restricoes.getOperador()).append(ESPACO);
		} else {
			super.montarRestricaoPadrao(restricoes);
		}
	}

}
