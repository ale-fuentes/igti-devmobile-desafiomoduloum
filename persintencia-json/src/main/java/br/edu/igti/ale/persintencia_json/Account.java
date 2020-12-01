package br.edu.igti.ale.persintencia_json;

// Class ussing for create list from JSON
class Account {
	Integer agencia;
	Integer conta;
	String name;
	int balance;

	public Account() {
		super();
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		String str = String.format(
				"\n\nPARKING TICKET" + "============\n" + "Dados\n" + "Conta: %d\n" + "Agencia: %d\n" + "name: %s\n"
						+ "Bala: %d\n" + "--------------------------------\n",
				this.conta, this.agencia, this.name, this.balance);
		return str;
	}

}