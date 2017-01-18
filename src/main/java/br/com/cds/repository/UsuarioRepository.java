package br.com.cds.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.cds.model.UsuarioFacebook;
import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.entity.UsuarioEntity;
import br.com.cds.uteis.Uteis;


public class UsuarioRepository implements Serializable {
	
	@Inject
	UsuarioEntity usuarioEntity;
	
	EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel){
		
		try {

			//QUERY QUE VAI SER EXECUTADA (UsuarioEntity.findUser) 	
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			
			//PARÃ‚METROS DA QUERY
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
			
			//RETORNA O USUÃ�RIO SE FOR LOCALIZADO
			return (UsuarioEntity)query.getSingleResult();
			
		} catch (Exception e) {
			
			return null;
		}

	}

	@Transactional
	public UsuarioEntity salvarUsuario(UsuarioModel um){
		
		entityManager =  Uteis.JpaEntityManager();
		usuarioEntity = new UsuarioEntity();
		usuarioEntity.setSenha(um.getSenha());
		usuarioEntity.setUsuario(um.getUsuario());
		
				
		entityManager.persist(usuarioEntity);
		
		return usuarioEntity;
		
	}

	
}
