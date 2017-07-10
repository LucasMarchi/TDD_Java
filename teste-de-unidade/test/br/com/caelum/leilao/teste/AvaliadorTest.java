package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		// cenario: 3 lances em ordem crescente
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(maria, 250.0)
				.lance(joao, 300.0)
				.lance(jose, 400.0)
				.constroi();

		// executando a acao
		leiloeiro.avalia(leilao);

		// comparando a saida com o esperado
		assertEquals(400, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(250, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {
		// cenario: 3 lances em ordem crescente
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(maria, 400.0)
				.lance(joao, 300.0)
				.lance(jose, 250.0)
				.constroi();

		// executando a acao
		leiloeiro.avalia(leilao);

		// comparando a saida com o esperado
		assertEquals(400, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(250, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEntenderLancesEmOrdemAleatoria() {
		// cenario: 3 lances em ordem crescente
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(jose, 250.0)
				.lance(maria, 400.0)
				.lance(joao, 300.0)
				.constroi();

		// executando a acao
		leiloeiro.avalia(leilao);

		// comparando a saida com o esperado
		assertEquals(400, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(250, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {

		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 1000.0)
				.constroi();

		leiloeiro.avalia(leilao);

		assertEquals(1000, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {

		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
			.lance(joao, 100.0)
			.lance(maria, 200.0)
			.lance(joao, 300.0)
			.lance(maria, 400.0)
			.constroi();

		leiloeiro.avalia(leilao);
		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
	}

	@Test
	public void deveEncontrarOsDoisMaioresLances() {

		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.constroi();

		leiloeiro.avalia(leilao);
		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(2, maiores.size());
	}

	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		
		leiloeiro.avalia(leilao);
		
	}

}
