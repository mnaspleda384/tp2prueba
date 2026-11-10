package ar.edu.unlam.tppb2tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.tppb2.Alumno;
import ar.edu.unlam.tppb2.Examen;
import ar.edu.unlam.tppb2.Gestion;
import ar.edu.unlam.tppb2.Personalidad;
import ar.edu.unlam.tppb2.Profesor;
import ar.edu.unlam.tppb2.SancionInvalidaException;
import ar.edu.unlam.tppb2.Mago;
import ar.edu.unlam.tppb2.MagoDuplicadoException;
import ar.edu.unlam.tppb2.MagoEncontradoPeroEsProfesorException;
import ar.edu.unlam.tppb2.MagoNoEncontradoException;

public class TestHogwarts {
	
	private Gestion hogwarts;
	private Mago alumno1;
	private Mago alumno2;
	private Mago profesor1;

	@Before
	public void setUp() {
		hogwarts = new Gestion();
		
		alumno1 = new Alumno("A-1", "harry", "potter", Personalidad.VALIENTE, 100);
		
		alumno2 = new Alumno("A-2", "ron", "weasley", Personalidad.VALIENTE, 20);
		
		profesor1 = new Profesor("P-1", "severus", "snape");
	}
	
	@Test
	public void queMePermitaInscribirUnAlumno() {
		hogwarts.inscribirMago(alumno1);
		assertEquals(1, hogwarts.getMagos().size());		
	}
	
	@Test(expected = MagoDuplicadoException.class)
	public void queNoMePermitaInscribirAUnAlumnoConElMismoID() {
		hogwarts.inscribirMago(alumno1);

		Mago alumno3 = new Alumno("A-1", "hermione", "granger", Personalidad.VALIENTE, 20);
		
		hogwarts.inscribirMago(alumno3);
	}
	
	@Test
	public void queMePermitaInscribirUnProfesor() {
		hogwarts.inscribirMago(profesor1);
		
		assertTrue(hogwarts.getMagos().contains(profesor1));
		assertEquals(1, hogwarts.getMagos().size());
	}
	
	@Test(expected = MagoDuplicadoException.class)
	public void queNoMePermitaInscribirAUnProfesorConElMismoIDYLanceProfesorDuplicadoException() {
		hogwarts.inscribirMago(profesor1);
		
		Mago profesor2 = new Profesor("P-1", "remus", "lupin");
		hogwarts.inscribirMago(profesor2);
	}
	
	@Test
	public void quePermitaMostrarLosPuntosQueTieneUnAlumno() {
		assertEquals(100, ((Alumno)alumno1).getPuntos(), 0);
	}
	
	@Test
	public void quePermitaMostrarLosPuntosQueTienenLosAlumnosDeUnaMismaCasa() {
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);
		
		Mago alumno3 = new Alumno("A-3", "draco", "malfoy", Personalidad.PROBLEMATICO, 30);
		
		hogwarts.inscribirMago(alumno3);
		
		assertEquals(120, hogwarts.calcularPuntosDeUnaCasa("Gryffindor"), 0);
	}
	
	@Test
	public void quePermitaMostrarLosPuntosQueTienenLosAlumnos() {
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);

		Mago alumno3 = new Alumno("A-3", "draco", "malfoy", Personalidad.PROBLEMATICO, 30);
		
		hogwarts.inscribirMago(alumno3);
		
		assertEquals(150, hogwarts.calcularPuntosDeTodoElColegio(), 0);
	}
	
	@Test
	public void queMePermitaObtenerLosAlumnosSancionados() throws MagoNoEncontradoException, SancionInvalidaException, MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);
		hogwarts.aplicarSancion("A-1", 3);
		List<Alumno> alumnosSancionados = hogwarts.obtenerAlumnosSancionados();
		
		assertEquals(1, alumnosSancionados.size());
		assertEquals("A-1", alumnosSancionados.get(0).getCodigo());
	}
	
	@Test
	public void queMePermitaObtenerElPromedioDeDiasSancionados() throws MagoNoEncontradoException, SancionInvalidaException, MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);
		
		hogwarts.aplicarSancion("A-1", 6);
		hogwarts.aplicarSancion("A-2", 7);
		
		assertEquals(6.5, hogwarts.obtenerPromedioDiasSanciones(), 0);
	}

	@Test (expected = MagoNoEncontradoException.class)
	public void queNoMePermitaAplicarleSancionAUnAlumnoConCodigoInexistente() throws MagoNoEncontradoException, SancionInvalidaException, MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno1);
		
		hogwarts.aplicarSancion("A-3", 3);
		}

	
	@Test
	public void quePermitaMostrarLosPuntosQueTieneUnAlumnoSancionado() throws MagoNoEncontradoException, SancionInvalidaException, MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno2);
		hogwarts.aplicarSancion("A-2", 5);
		
		assertEquals(10, ((Alumno)alumno2).getPuntos(), 0);
	}
	
	@Test
	public void queNoPermitaAplicarleSancionAOtroMagoQueNoSeaAlumno() {
		hogwarts.inscribirMago(profesor1);
		
		try {
			hogwarts.aplicarSancion("P-1", 3);
			fail("Debió lanzar MagoEncontradoPeroEsProfesorException");
		}catch(MagoEncontradoPeroEsProfesorException e) {
			assertEquals("Se encontró al mago pero al ser profesor no se puede aplicarle sanción", e.getMessage());			
		}catch(MagoNoEncontradoException e) {
			fail("No debía lanzar esta excepción");
		}catch(SancionInvalidaException e) {
			fail("No debía lanzar esta excepción");
		}
	}
	
	@Test
	public void unAlumnoDaUnaEvaluacionQueEsteAprobada() throws MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno1);
		hogwarts.alumnoDaExamen("A-1", Examen.APRUEBA);
		
		assertEquals(110, ((Alumno)alumno1).getPuntos(), 0);
	}
	
	@Test
	public void unAlumnoDaUnaEvaluacionQueEsteDesaprobada() throws MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(alumno1);
		hogwarts.alumnoDaExamen("A-1", Examen.DESAPRUEBA);
		
		assertEquals(100, ((Alumno)alumno1).getPuntos(), 0);
	}
	
	@Test(expected= MagoEncontradoPeroEsProfesorException.class)
	public void noPermiteQueUnProfesorDeUnExamen() throws MagoEncontradoPeroEsProfesorException {
		hogwarts.inscribirMago(profesor1);
		hogwarts.alumnoDaExamen("P-1", Examen.APRUEBA);
	}
	
	@Test
	public void seOrdenanLosProfesoresPorCodigoAscendenteYPorApellido() {
		Mago profesor2 = new Profesor("P-2","remus", "lupin");
		Mago profesor3 = new Profesor("P-4", "minerva", "mcgonagall");
		Mago profesor4 = new Profesor("P-3", "alastor", "moody");

		hogwarts.inscribirMago(profesor1);
		hogwarts.inscribirMago(profesor2);
		hogwarts.inscribirMago(profesor3);
		hogwarts.inscribirMago(profesor4);
		
		TreeSet<Profesor> profesoresOrdenadosPorCodigo = hogwarts.seOrdenanLosProfesoresPorCodigoAscendente();
		
		assertEquals(profesor1.getCodigo(), profesoresOrdenadosPorCodigo.getFirst().getCodigo());
		assertEquals(profesor3.getCodigo(), profesoresOrdenadosPorCodigo.getLast().getCodigo());
	}

	@Test
	public void seOrdenanLosAlumnosPorPuntosDescendenteYPorCasaAscendente() {
		Mago alumno2 = new Alumno("A-2", "luna", "lovegood", Personalidad.INGENIOSO, 110);
		Mago alumno3 = new Alumno("A-3", "cedric", "diggory", Personalidad.AMABLE, 110);
		Mago alumno4 = new Alumno("A-4", "draco", "malfoy", Personalidad.PROBLEMATICO, 30);
		
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);
		hogwarts.inscribirMago(alumno3);
		hogwarts.inscribirMago(alumno4);
		
		TreeSet<Alumno> alumnosOrdenadosPorCasas = hogwarts.seOrdenanLosAlumnosPorPuntosDescendenteYPorCasaAscendente();
		
		assertEquals(((Alumno)alumno3).getPuntos(), alumnosOrdenadosPorCasas.getFirst().getPuntos());
		assertEquals(((Alumno)alumno3).getCasa(), alumnosOrdenadosPorCasas.getFirst().getCasa());
		assertEquals(((Alumno)alumno4).getPuntos(), alumnosOrdenadosPorCasas.getLast().getPuntos());
		assertEquals(((Alumno)alumno4).getCasa(), alumnosOrdenadosPorCasas.getLast().getCasa());
	}
	
	@Test
	public void quePermitaObtenerUnReporteConTodosLosMagosPorCodigoYApellido() {
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(profesor1);
		
		Map<String, String> reporteMagos = hogwarts.obtenerReporteTodosLosMagos();
		
		for(Map.Entry<String, String> entry : reporteMagos.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			
			switch(key) {
			case "A-1":
				assertEquals(alumno1.getApellido(), val);
				break;
			case "P-1":
				assertEquals(profesor1.getApellido(), val);
				break;
				
			}
		}
	}
	
	@Test
	public void seDeterminaLaCasaGanadoraAlTerminarElAño() throws MagoEncontradoPeroEsProfesorException {
		Mago alumno2 = new Alumno("A-2", "luna", "lovegood", Personalidad.INGENIOSO, 120);
		Mago alumno3 = new Alumno("A-3", "lourdes", "godoy lotta", Personalidad.AMABLE, 110);
		Mago alumno4 = new Alumno("A-4", "draco", "malfoy", Personalidad.PROBLEMATICO, 30);
		
		hogwarts.inscribirMago(alumno1);
		hogwarts.inscribirMago(alumno2);
		hogwarts.inscribirMago(alumno3);
		hogwarts.inscribirMago(alumno4);
		
		hogwarts.alumnoDaExamen(alumno1.getCodigo(), Examen.APRUEBA);
		
		hogwarts.alumnoDaExamen(alumno2.getCodigo(), Examen.APRUEBA);
		
		hogwarts.alumnoDaExamen(alumno3.getCodigo(), Examen.APRUEBA);
		hogwarts.alumnoDaExamen(alumno3.getCodigo(), Examen.APRUEBA);
		hogwarts.alumnoDaExamen(alumno3.getCodigo(), Examen.APRUEBA);
		
		hogwarts.alumnoDaExamen(alumno4.getCodigo(), Examen.APRUEBA);
		hogwarts.alumnoDaExamen(alumno4.getCodigo(), Examen.DESAPRUEBA);

		assertEquals(((Alumno)alumno3).getCasa(), hogwarts.determinarCasaGanadora());
	}

}
