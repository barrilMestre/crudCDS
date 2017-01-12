package br.com.cds.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;*/

@Entity
@Table(name="tb_pessoa")
//@Indexed
@NamedQueries({
	@NamedQuery(name = "PessoaEntity.findAll",query= "SELECT p FROM PessoaEntity p"),
	@NamedQuery(name = "PessoaEntity.findPesquisa",query= "SELECT p FROM PessoaEntity p where p.nome like ? or p.email like ? or p.endereco like ?")
	
})
public class PessoaEntity {

	@Id
	@GeneratedValue
	@Column(name = "id_pessoa")
	private Integer 		codigo;
	
//	@Field(index=Index.YES,store=Store.YES)
	@Column(name = "nm_pessoa")
	private String  		nome;
	
	@Column(name = "fl_sexo")
	private String  		sexo;
	
	@Column(name = "dt_cadastro")
	private LocalDateTime	dataCadastro;
	
//	@Field(index=Index.YES)
	@Column(name = "ds_email")
	private String  		email;
	
//	@Field(index=Index.YES)
	@Column(name = "ds_endereco")
	private String  		endereco;
	
	@Column(name = "ds_telefone")
	private String telefone;
	
	@OneToOne
	@JoinColumn(name="id_usuario_cadastro")
	private UsuarioEntity	usuarioEntity;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}
	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
		
}
