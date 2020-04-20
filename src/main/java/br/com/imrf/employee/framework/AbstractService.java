package br.com.imrf.employee.framework;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import br.com.imrf.employee.framework.contexto.Alterar;
import br.com.imrf.employee.framework.contexto.Cadastrar;
import br.com.imrf.employee.framework.query.Restricoes;

public abstract class AbstractService<E> implements Service<E> {

	private Validador validador;

	@Autowired
	private MessageSource messageSource;

	protected abstract Repository<E> getRepository();

	public AbstractService(Validador validador) {
		this.validador = validador;
	}

	@Override
	public void cadastrar(E entidade) {
		validarEntidadeNoContextoInformado(entidade, Cadastrar.class);
		regrasNegocioCadastrar(entidade);
		getRepository().cadastrar(entidade);
	}

	@Override
	public void alterar(E entidade) {
		validarEntidadeNoContextoInformado(entidade, Alterar.class);
		regrasNegocioAlterar(entidade);
		getRepository().alterar(entidade);
	}

	@Override
	public void excluir(E entidade) {
		regrasNegocioExcluir(entidade);
		excluirEntidadeOuLancarExcecaoCasoPossuaAssociacao(entidade);
	}

	@Override
	public E consultarPorId(E entidade) {
		return getRepository().consultarPorId(entidade);
	}

	@Override
	public E consultarPorId(Integer id) {
		return getRepository().consultarPorId(id);
	}

	@Override
	public E consultarEntidade(E entidade, List<Restricoes> listaRestricoes) {
		return getRepository().consultarEntidade(entidade, listaRestricoes);
	}

	@Override
	public List<E> consultarTodos(E entidade, String campoOrdenacao) {
		return getRepository().consultarTodos(entidade, campoOrdenacao);
	}

	@Override
	public List<E> consultarTodos(E entidade) {
		return getRepository().consultarTodos(entidade);
	}

	@Override
	public List<E> consultarTodosDesc(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarTodosDesc(entidade, listaRestricoes, campoOrdenacao);
	}

	@Override
	public List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarTodos(entidade, listaRestricoes, campoOrdenacao);
	}

	public List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes,
			String campoOrdenacao) {
		return getRepository().consultarTodosPaginado(entidade, first, pageSize, listaRestricoes, campoOrdenacao);
	}

	@Override
	public Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarQuantidadeRegistros(entidade, listaRestricoes, campoOrdenacao);
	}

	@Override
	public List<E> filtro(E entidade) {
		return getRepository().filtro(entidade);
	}

	@Override
	public List<E> filtro(List<Restricoes> listaRestricoes, E entidade, String campoOrdenacao) {
		return getRepository().filtro(listaRestricoes, entidade, campoOrdenacao);
	}

	protected void validarEntidadeNoContextoInformado(E entidade, Class<?> contexto) {
		validador.validar(entidade, contexto);
	}

	protected void excluirEntidadeOuLancarExcecaoCasoPossuaAssociacao(E entidade) {
		lancarExcecaoCasoEntidadePossuaAssociacao(entidade);
		getRepository().excluir(entidade);
	}

	public StringBuffer verificarSeCampoEstaNulo(Object entidade, String mensagem) {
		StringBuffer msg = new StringBuffer();

		if (entidade instanceof BigDecimal) {
			if (entidade.equals(BigDecimal.ZERO)) {
				msg.append(mensagem);
			}
		}
		if (entidade == null || entidade.equals("")) {
			msg.append(mensagem);
		}
		return new StringBuffer(
				this.messageSource.getMessage(String.valueOf(msg), null, LocaleContextHolder.getLocale()));
	}

	protected void lancarExcecaoCasoEntidadePossuaAssociacao(E entidade) {
	}

	protected void regrasNegocioCadastrar(E entidade) {
	}

	protected void regrasNegocioAlterar(E entidade) {
	}

	protected void regrasNegocioExcluir(E entidade) {
	}

}
