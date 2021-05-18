package br.com.chiliexe.cm.model;

import br.com.chiliexe.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTest {
    private Campo campo = new Campo(3, 3);

    @Test
    void vizinhoRealHorizontal()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(3, 4)); // dereita
        boolean result2 = campo.adicionarVizinho(new Campo(3, 2)); // esquera

        assertTrue(result1);
        assertTrue(result2);

    }
    @Test
    void vizinhoFalsoHorizontal()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(3, 5)); // direita
        boolean result2 = campo.adicionarVizinho(new Campo(3, 1)); // esquerda

        assertFalse(result1);
        assertFalse(result2);

    }
    @Test
    void vizinhoRealDiagonal()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(2, 4)); // dereita
        boolean result2 = campo.adicionarVizinho(new Campo(4, 2)); // esquera

        assertTrue(result1);
        assertTrue(result2);

    }
    @Test
    void vizinhoFalsoDiagonal()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(2, 5)); // dereita
        boolean result2 = campo.adicionarVizinho(new Campo(5, 2)); // esquera

        assertFalse(result1);
        assertFalse(result2);

    }
    @Test
    void vizinhoRealVertical()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(2, 3)); // cima
        boolean result2 = campo.adicionarVizinho(new Campo(4, 3)); // baixo

        assertTrue(result1);
        assertTrue(result2);

    }
    @Test
    void vizinhoFalsoVertical()
    {

        boolean result1 = campo.adicionarVizinho(new Campo(1, 3)); // cima
        boolean result2 = campo.adicionarVizinho(new Campo(5, 3)); // baixo

        assertFalse(result1);
        assertFalse(result2);

    }
    @Test
    void vizinhoDoVizinho()
    {

        Campo c1 = new Campo(2,2);
        Campo c2 = new Campo(1,1);

        c1.adicionarVizinho(c2);
        campo.adicionarVizinho(c1);
        campo.abrirCampo();

        assertTrue(c1.isAberto());
        assertTrue(c2.isAberto());
        assertTrue(campo.isAberto());

    }

    @Test
    void alternarMarcacaoTest()
    {

        assertFalse(campo.isMarcado());
        campo.alternarMarcado();
        assertTrue(campo.isMarcado());
    }

    /* TESTE ABRIR CAMPO =====================================*/
    @Test
    void abrirCampoTest()
    {
        assertTrue(campo.abrirCampo());
    }

    @Test
    void abrirCampoMarcadoTest()
    {
        campo.alternarMarcado();
        assertFalse(campo.abrirCampo());
    }
    @Test
    void abrirCampoMarcadoMinadoTest()
    {
        campo.alternarMarcado();
        campo.minarCampo();
        assertFalse(campo.abrirCampo());
    }
    @Test
    void abrirCampoMinadoTest()
    {
        campo.minarCampo();
        assertThrows(ExplosaoException.class, () -> campo.abrirCampo());

    }

    /* TESTE OBJETIVO CONCLUIDO =====================================*/
    @Test
    void objetivoConcluidoTest()
    {
        Campo c1 = new Campo(3,3);
        Campo c2 = new Campo(6,6);

        c1.abrirCampo();

        c2.minarCampo();
        c2.alternarMarcado();

        assertTrue(c1.objetivoConcluido());
        assertTrue(c2.objetivoConcluido());
    }
    @Test
    void objetivoNaoConcluidoTest()
    {
        campo.minarCampo();
        assertFalse(campo.objetivoConcluido());
    }
    @Test
    void minasVizinhasTest()
    {
        Campo c1 = new Campo(3,4);
        Campo c2 = new Campo(3,2);
        Campo c3 = new Campo(2,3);
        Campo c4 = new Campo(4,3);

        c1.minarCampo();
        c2.minarCampo();
        c3.minarCampo();
        c4.minarCampo();

        campo.adicionarVizinho(c1);
        campo.adicionarVizinho(c2);
        campo.adicionarVizinho(c3);
        campo.adicionarVizinho(c4);

        assertEquals(4L, campo.minasVizinhas());
    }

    @Test
    void reiniciarTest()
    {
        campo.reiniciar();
        assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
    }
}
