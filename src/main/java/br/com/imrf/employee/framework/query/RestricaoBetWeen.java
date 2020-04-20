package br.com.imrf.employee.framework.query;

import static br.com.imrf.employee.framework.query.Consulta.DOIS_PONTOS;
import static br.com.imrf.employee.framework.query.Consulta.ESPACO;
import static br.com.imrf.employee.framework.query.Consulta.FINAL;
import static br.com.imrf.employee.framework.query.Consulta.INICIAL;

public class RestricaoBetWeen extends AbstractRestricao {

	@Override
	public void montarRetricaoParticular(Restricoes restricoes) {
		restricoes.seAtributoCompostoRetiraPonto();
		getSelect().append(DOIS_PONTOS).append(restricoes.getNome() + INICIAL).append(ESPACO)
				.append(TipoConectivo.AND.toString()).append(ESPACO).append(DOIS_PONTOS)
				.append(restricoes.getNome() + FINAL);
	}
}