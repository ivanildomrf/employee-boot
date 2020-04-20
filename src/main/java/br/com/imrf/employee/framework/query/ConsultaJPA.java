package br.com.imrf.employee.framework.query;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.persistence.EntityManager;

public class ConsultaJPA<E> implements Consulta<E> {

	private EntityManager entityManager;
	private String nomeEntidade;

	private QueryAdapter query;
	private List<Restricoes> listaRestriction;
	private StringBuilder select = new StringBuilder();
	private Map<TipoOperador, MontadorRestricoes> mapRestrictionBuilder;

	private ConsultaJPA(EntityManager entityManager) {
		this.entityManager = entityManager;
		query = new QueryAdapter(entityManager);
		listaRestriction = new ArrayList<Restricoes>();
		mapRestrictionBuilder = configurarMapTipoRestriction();
	}

	public ConsultaJPA(String sql, EntityManager entityManager) {
		this(entityManager);
		select = new StringBuilder(sql);
	}

	public ConsultaJPA(EntityManager entityManager, String nomeEntidade) {
		this(entityManager);
		this.nomeEntidade = nomeEntidade;
		select = new StringBuilder(MessageFormat.format(SELECT_ALL, nomeEntidade));
	}

	public Consulta<E> addInnerJoin(String nomeEntidadeJoin, String alias) {
		select.append(ESPACO).append(INNER_JOIN_FETCH).append(ESPACO).append(ALIAS).append(PONTO)
				.append(nomeEntidadeJoin).append(ESPACO).append(alias);
		return this;
	}

	/**
	 * Este método adiciona um inner join na consulta. É esperado que a entidade
	 * associada seja uma collection. Ex: public class Usuario { private
	 * List<Permissao> permissoes = new ArrayList<Permissao>(); }
	 * 
	 * nomeEntidadeJoin = permissoes; aliasEntidadePai = alias da entidade usuário.
	 * Normalmente é o alias padrão 'o'; aliasCollection = alias da collection. No
	 * caso o alias de permissão.
	 */
	public Consulta<E> addInnerJoinCollection(String nomeEntidadeJoin, String aliasEntidadePai,
			String aliasCollection) {
		select.append(ESPACO).append(INNER_JOIN_FETCH).append(ESPACO).append(aliasEntidadePai).append(PONTO)
				.append(nomeEntidadeJoin).append(ESPACO).append(aliasCollection);
		return this;
	}

	public Consulta<E> addLeftJoin(String nomeEntidadeJoin, String alias) {
		select.append(ESPACO).append(LEFT_JOIN_FETCH).append(ESPACO).append(ALIAS).append(PONTO)
				.append(nomeEntidadeJoin).append(ESPACO).append(alias);
		return this;
	}

	public List<E> consultarTodos() {
		return entityManager.createQuery(select.toString()).getResultList();
	}

	public Consulta<E> addParametroQuery(Restricoes restricoes) {
		addParametro(restricoes);
		return this;
	}

	public Consulta<E> addParametrosQuery(List<Restricoes> listaRestriction) {
		for (Restricoes restricoes : listaRestriction) {
			Restricoes restricaoClonada = restricoes.cloneRestricao();
			addParametro(restricaoClonada);
		}
		return this;
	}

	public Consulta<E> setFirstResult(Integer posicao) {
		query.setFirstResult(posicao);
		return this;
	}

	public Consulta<E> setMaxResults(Integer maximo) {
		query.setMaxResults(maximo);
		return this;
	}

	public Consulta<E> addOrderByColuna(Order orderBy) {
		if (queryPossuiOrderBy()) {
			addColunaOrderBy(orderBy);
		} else {
			addClausulaOrderByComColunaOrdenadora(orderBy);
		}
		return this;
	}

	@Override
	public Integer getQuantidadeRegistro() {
		addParametrosNaQueryJpa(listaRestriction);
		select = PATTERN_WHERE.matcher(select).find() ? substituirSelectPorCount(select.toString())
				: new StringBuilder(MessageFormat.format(SELECT_COUNT_ALL, this.nomeEntidade));
		Long quantidadeRegistros = query.comSelect(select).getSingleResult();
		limparListaDeParametros(listaRestriction);
		return quantidadeRegistros.intValue();
	}

	public List<E> resultList() {
		addParametrosNaQueryJpa(listaRestriction);
		List<E> listaResultado = query.comSelect(select).getResultList();
		limparListaDeParametros(listaRestriction);
		return listaResultado;
	}

	public E singleResult() {
		addParametrosNaQueryJpa(listaRestriction);
		Object resultado = query.comSelect(select).getSingleResult();
		limparListaDeParametros(listaRestriction);
		return (E) resultado;
	}

	private boolean queryPossuiOrderBy() {
		Matcher matcher = PATTERN_ORDER.matcher(select);
		return matcher.find();
	}

	private Consulta<E> addParametro(Restricoes restricoes) {
		if (queryPossuiClausulaWhere()) {
			select.append(ESPACO).append(restricoes.getTipoConectivo());
			adicionaRestriction(restricoes);
		} else {
			adicionaClausulaWhere();
			adicionaRestriction(restricoes);
		}
		addParametroNaListaDeRestriction(restricoes);
		return this;
	}

	private boolean queryPossuiClausulaWhere() {
		Matcher matcher = PATTERN_WHERE.matcher(select);
		return matcher.find();
	}

	private void adicionaClausulaWhere() {
		select.append(ESPACO).append(WHERE);
	}

	private void addClausulaOrderByComColunaOrdenadora(Order orderBy) {
		select.append(ESPACO).append(ORDER).append(ESPACO).append(BY).append(ESPACO)
				.append(retornarAliasCorretoOrderBy(orderBy)).append(PONTO).append(orderBy.getColunaOrdernadora())
				.append(ESPACO).append(orderBy.getTipoOrderBy());
	}

	private void addColunaOrderBy(Order orderBy) {
		select.append(VIRGULA).append(ESPACO).append(retornarAliasCorretoOrderBy(orderBy)).append(PONTO)
				.append(orderBy.getColunaOrdernadora()).append(ESPACO).append(orderBy.getTipoOrderBy());
	}

	private void adicionaRestriction(Restricoes restricoes) {
		String restricao = mapRestrictionBuilder.get(restricoes.getTipoOperador()).montarRestricao(restricoes);
		select.append(restricao);
	}

	private String retornarAliasCorretoOrderBy(Order order) {
		return order.getAlias() != null ? order.getAlias() : ALIAS;
	}

	private void addParametroNaListaDeRestriction(Restricoes restricoes) {
		listaRestriction.add(restricoes);
	}

	private void addParametrosNaQueryJpa(List<Restricoes> listaRestriction) {
		query.setParameters(listaRestriction);
	}

	private void limparListaDeParametros(List<Restricoes> listaRestriction) {
		listaRestriction.clear();
	}

	private Map<TipoOperador, MontadorRestricoes> configurarMapTipoRestriction() {
		Map<TipoOperador, MontadorRestricoes> mapRestriction = new HashMap<TipoOperador, MontadorRestricoes>();
		mapRestriction.put(TipoOperador.BETWEEN, new RestricaoBetWeen());
		mapRestriction.put(TipoOperador.ISNULL, new RestricaoSemParametro());
		mapRestriction.put(TipoOperador.ISNOTNULL, new RestricaoSemParametro());
		mapRestriction.put(TipoOperador.DIFERENTEDE, new RestricaoComParametro());
		mapRestriction.put(TipoOperador.IGUAL, new RestricaoComParametroLowerCase());
		mapRestriction.put(TipoOperador.LIKE, new RestricaoComParametroLowerCase());
		mapRestriction.put(TipoOperador.MAIORIGUAL, new RestricaoComParametro());
		mapRestriction.put(TipoOperador.MENORIGUAL, new RestricaoComParametro());
		return mapRestriction;
	}

	private StringBuilder substituirSelectPorCount(String sql) {
		sql = sql.replaceFirst(ALIAS, "count(o)");
		return new StringBuilder(sql);
	}
}