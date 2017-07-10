package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {

	private Usuario steveJobs;
	private Usuario billGates;
	private Usuario steveWozniak;
	
	@Before
	public void criaLeilao() {
		steveJobs = new Usuario("Steve Jobs");
		billGates = new Usuario("Bill Gates");
		steveWozniak = new Usuario("Steve Wozniak");
	}

	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(steveJobs, 2000)
				.constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}

	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(steveJobs, 2000)
				.lance(steveWozniak, 3000)
				.constroi();
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveDobrarLances() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(steveJobs, 2000)
				.lance(billGates, 3000)
				.dobraLance(steveJobs)
				.constroi();

		assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(steveJobs, 2000)
				.lance(steveJobs, 3000)
				.constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
		.lance(steveJobs, 2000)
		.lance(billGates, 3000)
		.lance(steveJobs, 4000)
		.lance(billGates, 5000)
		.lance(steveJobs, 6000)
		.lance(billGates, 7000)
		.lance(steveJobs, 8000)
		.lance(billGates, 9000)
		.lance(steveJobs, 10000)
		.lance(billGates, 11000)
		.constroi();
		
		// deve ser ignorado
		leilao.propoe(new Lance(steveJobs, 12000));
		assertEquals(10, leilao.getLances().size());
		
		int ultimo = leilao.getLances().size() - 1;
		Lance ultimoLance = leilao.getLances().get(ultimo);
		assertEquals(11000.0, ultimoLance.getValor(), 0.00001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveAceitarLanceIgualOuAbaixoDeZero() {
		
		@SuppressWarnings("unused")
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(new Usuario("Bill Gates"), 0)
				.constroi();
		
	}

}
