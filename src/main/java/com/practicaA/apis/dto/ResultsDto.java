package com.practicaA.apis.dto;

import java.util.ArrayList;

public class ResultsDto {

	// Arraylist para almacenar todas las coincidencias
	private ArrayList<ResponseBody> Coincidencias;
	
	// Getters. setters y método para combinar coincidencias

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
