package br.com.cds.filters;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cds.model.UsuarioFacebook;
import br.com.cds.model.UsuarioModel;
import br.com.cds.repository.UsuarioRepository;
import br.com.cds.usuario.autenticacao.LoginFacebook;

@WebFilter("/sistema/*")
public class AutenticacaoFilter implements Filter {
	
	@Inject
	UsuarioRepository usuarioRepository;
	
	public AutenticacaoFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession httpSession 				= ((HttpServletRequest) request).getSession(); 

		HttpServletRequest httpServletRequest   = (HttpServletRequest) request;

		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		UsuarioModel usuarioModel =(UsuarioModel) httpSession.getAttribute("usuarioAutenticado");

		if(httpServletRequest.getRequestURI().equals("/crudCDS/sistema/redirectFace") ){
			
			UsuarioFacebook uf = logarComFacebook(request.getParameter("code").toString(),httpSession);
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/sistema/home.xhtml");
			//usuarioRepository.salvarUsuario(uf);
		}else{
		
			if(httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1){
	
				if(usuarioModel == null){
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/index.xhtml");
				}else{
					chain.doFilter(request, response);
				}
			} else{
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
	public UsuarioFacebook logarComFacebook(String code,HttpSession httpSession ) throws MalformedURLException, IOException{
		LoginFacebook loginFacebook =new LoginFacebook();
		UsuarioFacebook uf = loginFacebook.obterUsuarioFacebook(code);
		UsuarioModel usuarioModel = new UsuarioModel();
		usuarioModel.setUsuario(uf.getName());
		usuarioModel.setSenha(uf.getId().toString());

		httpSession.setAttribute("usuarioAutenticado", usuarioModel);
		
		return uf;
	}
}
