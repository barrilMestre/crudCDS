package br.com.cds.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.entity.UsuarioEntity;
import br.com.cds.uteis.Uteis;


public class UsuarioRepository implements Serializable {
	
	
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
}
