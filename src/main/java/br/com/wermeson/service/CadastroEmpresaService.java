package br.com.wermeson.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.wermeson.model.Empresa;
import br.com.wermeson.repository.Empresas;
import br.com.wermeson.util.Transacional;

public class CadastroEmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	@Transacional
	public void salvar(Empresa empresa) {
		empresas.guardar(empresa);
	}
	
	@Transacional
	public void excluir(Empresa empresa) {
		empresas.remover(empresa);
	}

}
