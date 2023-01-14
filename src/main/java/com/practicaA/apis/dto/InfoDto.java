package com.practicaA.apis.dto;

import java.util.ArrayList;

public class InfoDto {

	private ArrayList<ResultsBody> Coincidencias;
	
	public ArrayList<ResultsBody> getResults() {
		return Coincidencias;
	}
	public void setResults(ArrayList<ResultsBody> results) {
		this.Coincidencias = results;
	}
	
	public void combineResults(ArrayList<ResultsBody> results) {
		this.Coincidencias.addAll(results);
	}
	
}
