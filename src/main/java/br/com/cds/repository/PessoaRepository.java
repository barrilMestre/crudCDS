package br.com.cds.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.cds.model.PessoaModel;
import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.entity.PessoaEntity;
import br.com.cds.repository.entity.UsuarioEntity;
import br.com.cds.uteis.Uteis;

public class PessoaRepository {

	@Inject
	PessoaEntity pessoaEntity;
	
	EntityManager entityManager;
	
	@Transactional
	public void SalvarNovoRegistro(PessoaModel pessoaModel){
		
		entityManager =  Uteis.JpaEntityManager();
		
		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());
		
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo()); 
		
		pessoaEntity.setUsuarioEntity(usuarioEntity);
				
		entityManager.persist(pessoaEntity);
		
	}
	
	public List<PessoaModel> GetPessoas(){
		
		entityManager =  Uteis.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");
		
		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>)query.getResultList();
	
		return carregaPessoaModel(pessoasEntity);
	}
	
	public List<PessoaModel> getPessoas(String textoPesquisa){
		
		entityManager =  Uteis.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PessoaEntity.findPesquisa")
				.setParameter(1, "%"+textoPesquisa+"%")
				.setParameter(2,  "%"+textoPesquisa+"%")
				.setParameter(3,  "%"+textoPesquisa+"%");
		
		
		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>)query.getResultList();
	
		return carregaPessoaModel(pessoasEntity);
	}
	
/*
	@PersistenceContext EntityManager em;
	public List<PessoaModel> GetPessoas(String string) throws ParseException{
		
		org.apache.lucene.search.Query luceneQuery = getLuceneQuery(string);
		FullTextEntityManager ftEm = (FullTextEntityManager) em;
		javax.persistence.Query query = ftEm.createFullTextQuery(luceneQuery, PessoaEntity.class);
		
	    @SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>)query.getResultList();
				
		return carregaPessoaModel(pessoasEntity);
	}

	private org.apache.lucene.search.Query getLuceneQuery(String string) throws ParseException {
		 	String[] stopWords = {"de","do","da","dos","das","a","o","na","no","em"};    
		    QueryParser parser = new QueryParser("nome",new StopAnalyzer(stopWords));
		    org.apache.lucene.search.Query LuceneQuery = parser.parse(string);
		return LuceneQuery;
	}
*/
	private List<PessoaModel> carregaPessoaModel(Collection<PessoaEntity> pessoasEntity) {
		PessoaModel pessoaModel = null;
		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();
		for (PessoaEntity pessoaEntity : pessoasEntity) {
		
			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());
			
			if(pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			else
				pessoaModel.setSexo("Feminino");
						
			UsuarioEntity usuarioEntity =  pessoaEntity.getUsuarioEntity();			
			
			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());
			
			pessoaModel.setUsuarioModel(usuarioModel);
					
			pessoasModel.add(pessoaModel);
		}
		return pessoasModel;
	}
	
	/***
	 * CONSULTA UMA PESSOA CADASTRADA PELO CÃ“DIGO
	 * @param codigo
	 * @return
	 */
	private PessoaEntity GetPessoa(int codigo){
		
		entityManager =  Uteis.JpaEntityManager();
		
		return entityManager.find(PessoaEntity.class, codigo);
	}
	
	/***
	 * ALTERA UM REGISTRO CADASTRADO NO BANCO DE DADOS
	 * @param pessoaModel
	 */
	@Transactional
	public void AlterarRegistro(PessoaModel pessoaModel){
		
		entityManager =  Uteis.JpaEntityManager();
		
		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());
		
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSexo(pessoaModel.getSexo());
	
		entityManager.merge(pessoaEntity);
	}
	/***
	 * EXCLUI UM REGISTRO DO BANCO DE DADOS
	 * @param codigo
	 */
	@Transactional
	public void ExcluirRegistro(int codigo){
		
		entityManager =  Uteis.JpaEntityManager();		
	
		PessoaEntity pessoaEntity = this.GetPessoa(codigo);
		
		entityManager.remove(pessoaEntity);
	}
	
}
