package br.com.api.restaurante.repository.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.api.restaurante.dto.RankingProduto;
import br.com.api.restaurante.model.Pedido;
import br.com.api.restaurante.model.Pedido_;
import br.com.api.restaurante.model.StatusPedido;

public class PedidoRepositoryImpl implements PedidoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Pedido> filtrar(PedidoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(PedidoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Pedido> root = query.from(Pedido.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		query.where(predicates);

		query.select(builder.count(root));
		return manager.createQuery(query).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);

	}

	private Predicate[] criarRestricoes(PedidoFilter filter, CriteriaBuilder builder, Root<Pedido> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (filter.getDataInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Pedido_.dataCriacao), filter.getDataInicio()));
		}

		if (filter.getDataFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Pedido_.dataCriacao), filter.getDataFim()));
		}

		if (!filter.getStatusPedido().isEmpty()) {
			In<StatusPedido> inClause = builder.in(root.get(Pedido_.STATUS_PEDIDO));
			filter.getStatusPedido().forEach(s -> inClause.value(s));
			predicates.add(inClause);
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public BigDecimal vendaMediaMesAtual() {
		
		var query = manager.createQuery(
				"SELECT AVG(total) FROM Pedido p WHERE EXTRACT (YEAR_MONTH FROM p.dataCriacao ) = EXTRACT (YEAR_MONTH FROM :data)",
				Double.class).setParameter("data", LocalDate.now());

		Optional<Double> media = Optional.ofNullable(query.getSingleResult());
		if (media.isPresent()) {
			return BigDecimal.valueOf(media.get());
		}			
				
		return BigDecimal.ZERO;
	}

	@Override
	public List<RankingProduto> rankingProdutosMaisVendidosMesAtual() {

		var query = manager.createQuery(
				"SELECT new br.com.api.restaurante.dto.RankingProduto(pro.nome, SUM(i.quantidade) as qtd) "
				+ "FROM Pedido ped "
				+ "JOIN ped.itens i "
				+ "JOIN i.produto pro "
				+ "WHERE EXTRACT (YEAR_MONTH FROM ped.dataCriacao ) = EXTRACT (YEAR_MONTH FROM :data) "
				+ "GROUP BY i.produto "
				+ "ORDER BY qtd DESC",
				RankingProduto.class)
				.setMaxResults(5)				
				.setParameter("data", LocalDate.now());
		var result = query.getResultList();
		return result;
	}

}
