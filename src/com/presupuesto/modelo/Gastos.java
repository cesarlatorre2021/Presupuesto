package com.presupuesto.modelo;

import java.util.Date;

public class Gastos {
	
	private Date   fecha;
	private String categoria;
	private String descripcion;
	private Long   valor;
	
	public Gastos(Date fecha, String categoria, String descripcion, Long valor) {
		setFecha(fecha);
		setCategoria(categoria);
		setDescripcion(descripcion);
		setValor(valor);
	}
		
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Long getValor() {
		return valor;
	}
	
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
}
