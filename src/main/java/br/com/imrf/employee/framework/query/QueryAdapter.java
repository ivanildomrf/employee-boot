package br.com.imrf.employee.framework.query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class QueryAdapter {

	private static final String FINAL = "Final";
	private static final String INICIAL = "Inicial";
	private final EntityManager entityManager;
	private List<Restricoes> listaRestriction;
	private StringBuilder select;

	private int posicaoPrimeiroRegistro = 0;
	private int quantidadeMaximaDeResultados = Integer.MAX_VALUE;

	public QueryAdapter(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setFirstResult(Integer posicao) {
		this.posicaoPrimeiroRegistro = posicao;
	}

	public void setMaxResults(Integer maximo) {
		this.quantidadeMaximaDeResultados = maximo;
	}

	public void setParameters(List<Restricoes> listaRestriction) {
		this.listaRestriction = listaRestriction;
	}

	public QueryAdapter comSelect(StringBuilder select) {
		this.select = select;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> getResultList() {
		return (List<E>) TratadorLancamentoExcecao.tratarExcecaoRelancandoComMensagemSeNecessario(new Command() {
			@Override
			public Object execute() {
				final Query query = entityManager.createQuery(select.toString());
				adicionarValoresNaQuery(query);
				query.setFirstResult(posicaoPrimeiroRegistro);
				query.setMaxResults(quantidadeMaximaDeResultados);
				return query.getResultList();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <E> E getSingleResult() {
		return (E) TratadorLancamentoExcecao.tratarExcecaoRelancandoComMensagemSeNecessario(new Command() {
			@Override
			public Object execute() {
				Query query = entityManager.createQuery(select.toString());
				adicionarValoresNaQuery(query);
				return (E) query.getSingleResult();
			}
		});
	}

	private void adicionarValoresNaQuery(Query query) {
		for (Restricoes restricao : listaRestriction) {
			restricao.seAtributoCompostoRetiraPonto();
			if (restricao.isOperadorEhBetween()) {
				query.setParameter(restricao.getNome() + INICIAL, ((Intervalo) restricao.getValor()).getValorInicial());
				query.setParameter(restricao.getNome() + FINAL, ((Intervalo) restricao.getValor()).getValorFinal());
			} else if (restricao.isOperadorNaoEhIsNullNemIsNotNull()) {
				query.setParameter(restricao.getNome(), restricao.getValor());
			}
		}
	}

	private static class TratadorLancamentoExcecao {

		public static Object tratarExcecaoRelancandoComMensagemSeNecessario(Command command) {
			try {
				return command.execute();
			} catch (IllegalArgumentException e) {
				gravarLog(e);
				throw new RuntimeException(e.getMessage());
			} catch (NoResultException e) {
				gravarLog(e);
				throw e;
			} catch (Exception e) {
				gravarLog(e);
				throw new RuntimeException(e.getMessage());
			}
		}

		private static void gravarLog(Exception e) {
			Logger logger = Logger.getLogger("br.gov.al.saude.framework.core.consulta.QueryAdapter");
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	interface Command {

		Object execute();
	}

}
