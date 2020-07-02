package br.com.api.restaurante.repository.produto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.api.restaurante.model.Categoria;
import br.com.api.restaurante.model.Produto;
import br.com.api.restaurante.model.Produto_;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;	
	
	@Override
	public Page<Produto> filtrar(ProdutoFilter filter, Pageable pageable ) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		Join<Produto, Categoria> join = root.join(Produto_.categoria);

		Predicate[] predicates = criarRestricoes(filter, builder, root , join);
		
		criteria.where(predicates);

		TypedQuery<Produto> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		List<Produto> result = query.getResultList();		
		
		return new PageImpl<>(result, pageable,total(filter));
	}

	private Long total(ProdutoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Produto> root = criteria.from(Produto.class);
		Join<Produto, Categoria> join = root.join(Produto_.categoria);

		Predicate[] predicates = criarRestricoes(filter, builder, root , join);
		
		criteria.where(predicates);
		criteria.select(builder.count(root));
		Long result = manager.createQuery(criteria).getSingleResult();
		//System.out.println("Total: " + result);
		return result;
	}


	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina =pageable.getPageSize();
		int primeiroRegistroPagina =  paginaAtual * totalRegistroPorPagina;
		//System.out.println("paginaAtual" +paginaAtual) ;
		//System.out.println("totalRegistroPorPagina" +totalRegistroPorPagina) ;
		//System.out.println("primeiroRegistroPagina" +primeiroRegistroPagina) ;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistroPorPagina);
		
	}


	private Predicate[] criarRestricoes(ProdutoFilter filter, CriteriaBuilder builder, 
				Root<Produto> root, Join<Produto, Categoria> join) {
		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isEmpty(filter.getNome())) {						
			predicates.add(
					builder.like(builder.lower(root.get(Produto_.nome)), "%" + filter.getNome().toLowerCase() + "%"));
		}

		if (filter.getCategoria() != null) {			
			predicates.add(builder.equal(root.get(Produto_.categoria), filter.getCategoria()) );					
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
