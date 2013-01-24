package org.domain.workshop.entity;

// Generated Jan 2, 2012 3:00:03 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

/**
 * Customer generated by hbm2java
 */
@Entity
@Table(name = "customer")
@Name("customer")
public class Customer implements java.io.Serializable {

	private int customerId;
	private String title;
	private String fname;
	private String lname;
	private String addressline;
	private String town;
	private String zipcode;
	private String phone;
	private char gold;
	private char silver;
	private int edad;
	private char genero;
	private String Tipo_Vehiculo;
	
	private String Modelo;
	private String Marca;
	private String Zona_circulacion;
	
	public Customer() {
	}

	public Customer(int customerId, String lname, String zipcode, char gold,
			char silver) {
		this.customerId = customerId;
		this.lname = lname;
		this.zipcode = zipcode;
		this.gold = gold;
		this.silver = silver;
	}

	public Customer(int customerId, String title, String fname, String lname,
			String addressline, String town, String zipcode, String phone,
			char gold, char silver) {
		this.customerId = customerId;
		this.title = title;
		this.fname = fname;
		this.lname = lname;
		this.addressline = addressline;
		this.town = town;
		this.zipcode = zipcode;
		this.phone = phone;
		this.gold = gold;
		this.silver = silver;
	}

	public Customer(int customerId, String title, String fname, String lname,
			String addressline, String town, String zipcode, String phone,
			char gold, char silver,int edad,char genero) {
		this.customerId = customerId;
		this.title = title;
		this.fname = fname;
		this.lname = lname;
		this.addressline = addressline;
		this.town = town;
		this.zipcode = zipcode;
		this.phone = phone;
		this.gold = gold;
		this.silver = silver;
		this.edad = edad;
		this.genero = genero;
	}
	
	@Id
	@Column(name = "customer_id", unique = true, nullable = false)
	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Column(name = "title", length = 4)
	@Length(max = 4)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "fname", length = 32)
	@Length(max = 32)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "lname", nullable = false, length = 32)
	@NotNull
	@Length(max = 32)
	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	@Column(name = "addressline", length = 64)
	@Length(max = 64)
	public String getAddressline() {
		return this.addressline;
	}

	public void setAddressline(String addressline) {
		this.addressline = addressline;
	}

	@Column(name = "town", length = 32)
	@Length(max = 32)
	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(name = "zipcode", nullable = false, length = 10)
	@NotNull
	@Length(max = 10)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "phone", length = 16)
	@Length(max = 16)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "gold", nullable = false, length = 1)
	public char getGold() {
		return this.gold;
	}

	public void setGold(char gold) {
		this.gold = gold;
	}

	@Column(name = "silver", nullable = false, length = 1)
	public char getSilver() {
		return this.silver;
	}

	public void setSilver(char silver) {
		this.silver = silver;
	}

	@Column(name = "edad")
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Column(name = "genero", length = 32)
	@Length(max = 32)
	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	@Column(name = "tipovh", length = 32)
	@Length(max = 32)
	public String getTipo_Vehiculo() {
		return Tipo_Vehiculo;
	}

	public void setTipo_Vehiculo(String tipo_Vehiculo) {
		Tipo_Vehiculo = tipo_Vehiculo;
	}

	
	
	@Column(name = "modelo")
	public String getModelo() {
		return Modelo;
	}

	public void setModelo(String modelo) {
		Modelo = modelo;
	}

	@Column(name = "marca")
	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	@Column(name = "zona")
	public String getZona_circulacion() {
		return Zona_circulacion;
	}

	public void setZona_circulacion(String zona_circulacion) {
		Zona_circulacion = zona_circulacion;
	}

}
