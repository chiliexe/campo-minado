package br.com.chiliexe.cm;

import br.com.chiliexe.cm.model.Tabuleiro;
import br.com.chiliexe.cm.view.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(4, 4, 1);
        new TabuleiroConsole(tabuleiro);
    }
}
