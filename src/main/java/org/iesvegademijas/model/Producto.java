package org.iesvegademijas.model;

import java.util.Objects;

public class Producto {
	
	private int codigo;
	private String nombre;
	private double precio;
	private int codigofabricante;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCodigofabricante() {
		return codigofabricante;
	}
	public void setCodigofabricante(int codigofabricante) {
		this.codigofabricante = codigofabricante;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, codigofabricante, nombre, precio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return codigo == other.codigo && codigofabricante == other.codigofabricante
				&& Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", codigofabricante="
				+ codigofabricante + "]";
	}	
	
	
	
}
