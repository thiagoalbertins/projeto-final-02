package com.example.projetolojaunit.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "itens_venda")
public class ItensVenda {

	@EmbeddedId
	ItensVendaKey itensVendaKey;

	public ItensVenda() {
		super();
	}
	
	public ItensVenda(ItensVendaKey itens_venda_key, Integer quantidade, Double valor_unitario) {
		super();
		this.itensVendaKey = itens_venda_key;
		this.quantidade = quantidade;
		this.valor_unitario = valor_unitario;
	}


	Integer quantidade;
	
	Double valor_unitario;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public ItensVendaKey getItens_venda_key() {
		return itensVendaKey;
	}

	public void setItens_venda_key(ItensVendaKey itens_venda_key) {
		this.itensVendaKey = itens_venda_key;
	}
	
	public void setId_venda(int id_venda) {
		this.itensVendaKey.setId_venda(id_venda);
	}
	
	public void setId_produto(int id_produto) {
		this.itensVendaKey.setId_produto(id_produto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itensVendaKey == null) ? 0 : itensVendaKey.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((valor_unitario == null) ? 0 : valor_unitario.hashCode());
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
		ItensVenda other = (ItensVenda) obj;
		if (itensVendaKey == null) {
			if (other.itensVendaKey != null)
				return false;
		} else if (!itensVendaKey.equals(other.itensVendaKey))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (valor_unitario == null) {
			if (other.valor_unitario != null)
				return false;
		} else if (!valor_unitario.equals(other.valor_unitario))
			return false;
		return true;
	}
	
	
}
