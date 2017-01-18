package br.com.cds.pessoa.controller;


import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.cds.model.PessoaModel;
import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.PessoaRepository;
import br.com.cds.repository.UsuarioRepository;
import br.com.cds.repository.entity.UsuarioEntity;
import br.com.cds.usuario.controller.UsuarioController;
import br.com.cds.uteis.Uteis;

@Named(value="cadastrarPessoaController")
@RequestScoped
public class CadastrarPessoaController {

	@Inject
	PessoaModel pessoaModel;
	
	@Inject
	UsuarioController usuarioController;
	
	@Inject
	PessoaRepository pessoaRepository;

	@Inject
	UsuarioRepository usuarioRepository;
	
	private UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
		
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}
	
	
	public void salvarNovaPessoa(){
		
		pessoaModel.setUsuarioModel(verificaUsuarioFace());
		pessoaRepository.salvarNovoRegistro(this.pessoaModel);
		this.pessoaModel = null;
		Uteis.MensagemInfo("Registro cadastrado com sucesso");
		
	}
	
    private UsuarioModel verificaUsuarioFace() {
		UsuarioModel um = this.usuarioController.getUsuarioSession();
		
		if(um.getCodigo()==null){
		
			UsuarioEntity ue= usuarioRepository.ValidaUsuario(um);
			
			if(ue==null){
				ue= usuarioRepository.salvarUsuario(um);
			}
			um.setCodigo(ue.getCodigo());
			
		}
		return um;
	}

	public void handleFileUpload(FileUploadEvent event) {
    	
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
