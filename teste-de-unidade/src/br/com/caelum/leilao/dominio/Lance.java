package br.com.caelum.leilao.dominio;

public class Lance {

	private Usuario usuario;
	private double valor;
	
	public Lance(Usuario usuario, double valor) {
		this.usuario = usuario;
		this.valor = validaValorLance(valor);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = validaValorLance(valor);
	}
	
	private double validaValorLance(double valor) {
		if(valor <= 0) {
			throw new IllegalArgumentException("O valor não pode ser menor ou igual a 0");
		}else {
			return valor;
		}
	}
	
}
