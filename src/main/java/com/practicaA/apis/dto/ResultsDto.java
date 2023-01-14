package com.practicaA.apis.dto;

import java.util.ArrayList;

public class ResultsDto {

	private ArrayList<ResponseBody> Coincidencias;
	
	public ArrayList<ResponseBody> getResults() {
		return Coincidencias;
	}
	public void setResults(ArrayList<ResponseBody> results) {
		this.Coincidencias = results;
	}
	
	public void combineResults(ArrayList<ResponseBody> results) {
		this.Coincidencias.addAll(results);
	}
	
}
