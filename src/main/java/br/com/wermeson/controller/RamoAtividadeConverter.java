package br.com.wermeson.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.wermeson.model.RamoAtividade;

public class RamoAtividadeConverter implements Converter {

	private List<RamoAtividade> listRamoAtividades;
	
	public RamoAtividadeConverter(List<RamoAtividade> listRamoAtividades) {
		this.listRamoAtividades = listRamoAtividades;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		Long id = Long.valueOf(value);
		for(RamoAtividade ramoAtividade: listRamoAtividades) {
			if(id.equals(ramoAtividade.getId())) {
				return ramoAtividade;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}
		RamoAtividade ramoAtividade = (RamoAtividade) value;
		return ramoAtividade.getId().toString();
	}

}
