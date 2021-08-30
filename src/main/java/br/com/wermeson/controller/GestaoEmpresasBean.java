package br.com.wermeson.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.wermeson.model.Empresa;
import br.com.wermeson.model.RamoAtividade;
import br.com.wermeson.model.TipoEmpresa;
import br.com.wermeson.repository.Empresas;
import br.com.wermeson.repository.RamoAtividades;
import br.com.wermeson.service.CadastroEmpresaService;
import br.com.wermeson.util.FacesMessages;

@Named
//@RequestScoped //A cada requisição é criada a instância
@ViewScoped // A cada visualização é criada a instância
//@SessionScoped //A cada sessão é criada a instância
//@ApplicationScoped //É criada apenas UMA instância
public class GestaoEmpresasBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Empresa> listaEmpresas;

	private String termoPesquisa;

	@Inject
	private RamoAtividades ramoAtividades;

	@Inject
	private Empresas empresas;

	@Inject
	private CadastroEmpresaService cadastroEmpresaService;

	@Inject
	private FacesMessages messages;

	private RamoAtividadeConverter ramoAtividadeConverter;

	private Empresa empresa;

	public void prepararNovaEmpresa() {
		empresa = new Empresa();
	}

	public void prepararEdicao() {
		ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
	}

	public void salvar() {
		cadastroEmpresaService.salvar(empresa);
		atualizarRegistros();
		messages.info("Empresa salva com sucesso!");
		PrimeFaces.Ajax currentAjax = PrimeFaces.current().ajax();
		currentAjax.update(Arrays.asList("frm:empresasDataTable", "frm:messages"));
	}

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todasEmpresas();
		}

	}

	public void excluir() {
		cadastroEmpresaService.excluir(empresa);
		empresa = null;
		atualizarRegistros();
		messages.info("Empresa excluída com sucesso!");
	}

	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}

	public void pesquisar() {
		listaEmpresas = empresas.pesquisar(termoPesquisa);

		if (listaEmpresas.isEmpty()) {
			messages.info("Sua consulta não retornou registros");
		}
	}

	public void todasEmpresas() {
		listaEmpresas = empresas.todas();
	}

	public List<RamoAtividade> completarRamoAtividade(String termo) {
		List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(termo);

		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);

		return listaRamoAtividades;
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public TipoEmpresa[] getTiposEmpresa() {
		return TipoEmpresa.values();
	}

	public RamoAtividadeConverter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isEmpresaSelecionada() {
		return empresa != null && empresa.getId() != null;
	}
}
