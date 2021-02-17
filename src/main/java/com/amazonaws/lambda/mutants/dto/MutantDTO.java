package com.amazonaws.lambda.mutants.dto;

public class MutantDTO {
	
	private String cadena;
	private int resultado;
	
	
	public MutantDTO() {
		
	}
	
	public MutantDTO(String cadena, int resultado) {
		this.cadena = cadena;
		this.resultado = resultado;
	}
	public String getCadena() {
		return cadena;
	}
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	

}
