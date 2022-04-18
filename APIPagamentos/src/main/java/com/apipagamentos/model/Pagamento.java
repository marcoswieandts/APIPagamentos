package com.apipagamentos.model;

public class Pagamento {
	
	private Transacao transacao;
	
	public Pagamento() {}

	public Pagamento(Transacao transacao) {
		this.transacao = transacao;
	}
	
	public Transacao getTransacao() {
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
	}
}