package com.bernardpaula.vendas.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardpaula.vendas.domain.entity.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

	//@Query(value = " select c from Cliente c where c.nome like :nome ")
	@Query(value= "select * from cliente c where c.nome like '%:nome%' ", nativeQuery=true )  
	List<Cliente> encontrarPorNome(@Param("nome")String nome);
	
	// select c from Cliente c where c.nome like :nome
	List<Cliente> findByNomeLike(String nome);
	
	@Query("delete from Cliente c where c.nome =:nome")
	@Modifying
	void deleteByNome(String nome);
	
	@Query("select c from Cliente c left join fetch c.pedidos where c.id = :id ")
	Cliente findClienteFatchPedidos(@Param("id") Integer id);
	
	
	List<Cliente> findByNomeOrId(String nome, Integer id);
	
	
	List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
	
	
	Cliente findOneByNome(String nome);
	
	boolean existsByNome(String nome);
	
	/*
	@Autowired
	private EntityManager entityManager;
	
	
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		entityManager.persist(cliente);
		return cliente;
	}

	@Transactional
	public Cliente atualizar(Cliente cliente) {
		entityManager.merge(cliente);
		return cliente;
	}
	
	@Transactional
	public void deletar(Cliente cliente) {
		if(!entityManager.contains(cliente)) {
			cliente = entityManager.merge(cliente);
		}
		entityManager.remove(cliente);
	}
	
	@Transactional
	public void deletar(Integer id) {
		Cliente cliente = entityManager.find(Cliente.class, id);
		deletar(cliente);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNome(String nome){
		String jpql = " select c from Cliente c where c.nome like :nome ";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> obterTodos(){
		return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
	}
	
	*/

}
