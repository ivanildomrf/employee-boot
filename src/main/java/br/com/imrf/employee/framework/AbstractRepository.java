package br.com.imrf.employee.framework;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.imrf.employee.core.Identidade;
import br.com.imrf.employee.core.Ordenacao;
import br.com.imrf.employee.framework.query.ConsultaJPA;
import br.com.imrf.employee.framework.query.Order;
import br.com.imrf.employee.framework.query.Restricoes;
import br.com.imrf.employee.repository.command.CommandSingleResult;
import br.com.imrf.employee.repository.command.ValidadorSingleResult;

public abstract class AbstractRepository<E> implements Repository<E> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void cadastrar(E entidade) {
		if (CheckerUtil.isNull(((Identidade) entidade).getId())) {
			getEntityManager().persist(entidade);
		} else {
			getEntityManager().merge(entidade);
		}
	}

	@Override
	public void alterar(E entidade) {
		getEntityManager().merge(entidade);
	}

	@Override
	public void excluir(E entidade) {
		E itemParaExcluir = consultarPorId(entidade);
		getEntityManager().remove(itemParaExcluir);
		getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public E consultarPorId(E entidade) {
		return (E) getEntityManager().find(entidade.getClass(), ((Identidade) entidade).getId());
	}

	public E consultarPorId(Integer id) {
		return (E) getEntityManager().find(getClassePeloTipoParametrizado(), id);
	}

	@Override
	public List<E> consultarTodos(E entidade) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName()).resultList();
	}

	@Override
	public List<E> consultarTodos(E entidade, String campoOrdenacao) {
		List<E> resultList = new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade))).resultList();
		return resultList;
	}

	@Override
	public E consultarEntidade(final E entidade, final List<Restricoes> listaRestricoes) {
		return new ValidadorSingleResult<E>().getSingleResult(new CommandSingleResult<E>() {
			@Override
			public E execute() {
				return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
						.addParametrosQuery(listaRestricoes).singleResult();
			}
		});
	}

	@Override
	public List<E> consultarTodosDesc(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		List<E> resultList = new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes)
				.addOrderByColuna(Order.desc(getOrdenacao(campoOrdenacao, entidade))).resultList();
		return resultList;
	}

	@Override
	public List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		List<E> resultList = new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes).addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade)))
				.resultList();
		return resultList;
	}

	public List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes,
			String campoOrdenacao) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes).setFirstResult(first).setMaxResults(pageSize)
				.addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade))).resultList();
	}

	public Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes).setFirstResult(0).setMaxResults(Integer.MAX_VALUE)
				.getQuantidadeRegistro();
	}

	@Override
	public List<E> filtro(List<Restricoes> listaRestricoes, E entidade, String campoOrdenacao) {
		if (CheckerUtil.isCollectionNotNullNotEmpty(listaRestricoes)) {
			return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
					.addParametrosQuery(listaRestricoes)
					.addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade))).resultList();
		} else {
			return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName()).resultList();
		}
	}

	@Override
	public List<E> filtro(E entidade) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName()).resultList();
	}

	@SuppressWarnings("unchecked")
	private Class<E> getClassePeloTipoParametrizado() {
		Class<E> classe = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return classe;
	}

	private String getOrdenacao(String campoOrdenacao, E entidade) {
		return CheckerUtil.isNullOrEmpty(campoOrdenacao) ? ((Ordenacao) entidade).getCampoOrderBy() : campoOrdenacao;
	}

}
