package com.example.projetolojaunit.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItensVendaKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_venda", nullable = false)
	public int id_venda;

	@Column(name = "id_produto", nullable = false)
	public int id_produto;
	
	public ItensVendaKey() {
		super();
	}

	public ItensVendaKey(int id_venda) {
		super();
		this.id_venda = id_venda;
	}

	public ItensVendaKey(int id_venda, int id_produto) {
		super();
		this.id_venda = id_venda;
		this.id_produto = id_produto;
	}

	public int getId_venda() {
		return id_venda;
	}

	public void setId_venda(int id_venda) {
		this.id_venda = id_venda;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getItensVendaKey(int id_venda, int id_produto) {
		return String.valueOf(id_venda) + String.valueOf(id_produto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_produto;
		result = prime * result + id_venda;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItensVendaKey other = (ItensVendaKey) obj;
		if (id_produto != other.id_produto)
			return false;
		if (id_venda != other.id_venda)
			return false;
		return true;
	}



}
