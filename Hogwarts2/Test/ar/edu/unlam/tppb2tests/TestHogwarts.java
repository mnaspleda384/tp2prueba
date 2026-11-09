package ar.edu.unlam.tppb2tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.tppb2.Alumno;
import ar.edu.unlam.tppb2.Casa;
import ar.edu.unlam.tppb2.Gestion;
import ar.edu.unlam.tppb2.Personalidad;
import ar.edu.unlam.tppb2.Mago;

public class TestHogwarts {

	@Before
	public void setUp() {
		Gestion hogwarts = new Gestion();
		
		Casa gryffindor = new Casa("Gryffindor");
		
		Casa slytherin = new Casa("Slytherin");
		
		Casa hufflepuff = new Casa("Hufflepluff");
		
		Casa ravenclaw = new Casa("Ravenclaw");
		
		Mago alumno1 = new Alumno("harry", "potter", 01, Personalidad.VALIENTE);
		
	}
	
	@Test
	public void sePuedeInscribirAUnAlumno() {
		
		
		
	}
	
	@Test
	public void noSePuedeInscribirAUnAlumnoConElMismoID() {
		fail("Not yet implemented");
	}
	
	@Test
	public void sePuedeAsignarUnAlumnoALaCasa() {
		fail("Not yet implemented");
	}
	
	@Test
	public void sePuedeAsignarMasDeUnAlumnoAUnaCasa() {
		fail("Not yet implemented");
	}
	
	@Test
	public void unAlumnoDaUnaEvaluacionQueEsteAprobada() {
		fail("Not yet implemented");
	}
	
	@Test
	public void unAlumnoDaUnaEvaluacionQueEsteDesaprobada() {
		fail("Not yet implemented");
	}
	
	@Test
	public void seMuestranLosPuntosDeCadaAlumno() {
		fail("Not yet implemented");
	}
	
	@Test
	public void seOrdenanLosAlumnosDeLaCasaGryffindorPorPuntos() {
		fail("Not yet implemented");
	}
	
	@Test
	public void seOrdenanTodosLosAlumnosPorID() {
		fail("Not yet implemented");
	}
	
	@Test
	public void seDeterminaLaCasaGanadoraAlTerminarElAño() {
		fail("Not yet implemented");
	}

}
