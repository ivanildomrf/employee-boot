package br.com.imrf.employee.framework;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.imrf.employee.framework.query.Restricoes;

public interface Repository<E> {

	EntityManager getEntityManager();

	void cadastrar(E entidade);

	void alterar(E entidade);

	void excluir(E entidade);

	List<E> consultarTodos(E entidade);

	List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao);

	E consultarPorId(E entidade);

	E consultarPorId(Integer id);

	E consultarEntidade(final E entidade, final List<Restricoes> listaRestricoes);

	List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes,
			String campoOrdenacao);

	Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao);

	List<E> filtro(List<Restricoes> listaRestricoes, E entidade, String campoOrdenacao);

	List<E> filtro(E entidade);

	List<E> consultarTodosDesc(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao);

	List<E> consultarTodos(E entidade, String campoOrdenacao);

}
