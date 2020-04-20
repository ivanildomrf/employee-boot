package br.com.imrf.employee.framework.query;

import static br.com.imrf.employee.framework.query.Consulta.ALIAS;
import static br.com.imrf.employee.framework.query.Consulta.ESPACO;
import static br.com.imrf.employee.framework.query.Consulta.PONTO;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractRestricao implements MontadorRestricoes {

	private StringBuilder select;

	public StringBuilder getSelect() {
		return select;
	}

	protected void montarRetricaoParticular(Restricoes restricoes) {
	}

	protected void montarRestricaoPadrao(Restricoes restricoes) {
		getSelect().append(ESPACO).append(retornarAliasCorreto(restricoes)).append(PONTO).append(restricoes.getNome())
				.append(ESPACO).append(restricoes.getOperador()).append(ESPACO);
	}

	protected String retornarAliasCorreto(Restricoes restricoes) {
		return restricoes.getAlias() != null ? restricoes.getAlias() : ALIAS;
	}

	protected String seAtributoCompostoRetiraPonto(String nome) {
		if (StringUtils.contains(nome, ".")) {
			return StringUtils.remove(nome, ".");
		}
		return nome;
	}

	@Override
	public String montarRestricao(Restricoes restricoes) {
		select = new StringBuilder();
		montarRestricaoPadrao(restricoes);
		montarRetricaoParticular(restricoes);
		return getSelect().toString();
	}

}