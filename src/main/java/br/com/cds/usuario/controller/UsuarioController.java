package br.com.cds.usuario.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.StringUtils;

import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.UsuarioRepository;
import br.com.cds.repository.entity.UsuarioEntity;
import br.com.cds.usuario.autenticacao.LoginFacebook;
//import br.com.cds.usuario.autenticacao.LoginFacebook;
import br.com.cds.uteis.Uteis;

@Named(value="usuarioController")
@SessionScoped
@WebListener
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//@Inject
	//private LoginFacebook loginFacebook;
	
	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private UsuarioEntity usuarioEntity;
	
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
	
	public UsuarioModel GetUsuarioSession(){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		return (UsuarioModel)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}
	
	public String Logout(){
						
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "/index.xhtml?faces-redirect=true";
	}
	public String EfetuarLogin(){
			
		if(StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())){
			
			Uteis.Mensagem("Favor informar o login!");
			return null;
		}
		else if(StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())){
			
			Uteis.Mensagem("Favor informara senha!");
			return null;
		}
		else{	

			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);
			
			if(usuarioEntity!= null){
							
				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());
				
				
				FacesContext facesContext = FacesContext.getCurrentInstance();
				
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);
				
				
				return "sistema/home?faces-redirect=true";
			}
			else{
				
				Uteis.Mensagem("N√£o foi poss√≠vel efetuar o login com esse usu√°rio e senha!");
				return null;
			}
		}
		
	}
	

	/**
	 * MÈtodo que chama URL do facebook onde o usu·rio poder· autorizar a aplicaÁ„o
	 * a acessar seus recursos privados.
	 * @return
	 */
	public String logarComFacebook(){
		LoginFacebook loginFacebook =new LoginFacebook();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(loginFacebook.getLoginRedirectURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}