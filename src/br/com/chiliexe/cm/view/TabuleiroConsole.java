package br.com.chiliexe.cm.view;

import br.com.chiliexe.cm.excecao.ExplosaoException;
import br.com.chiliexe.cm.excecao.SairException;
import br.com.chiliexe.cm.model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

public class TabuleiroConsole {
    Tabuleiro tabuleiro;
    Scanner scanner = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try{
            boolean continuar = true;

            loopJogo();
            System.out.println("Outra partida? (S/n) ");
            String resposta = scanner.nextLine();
            if("n".equalsIgnoreCase(resposta))
            {
                continuar = false;
                throw new SairException();
            }else
            {
                tabuleiro.reiniciar();
                executarJogo();
            }

        }catch (SairException e)
        {
            System.out.println("Tchauu!!");
        }finally {
            scanner.close();
        }
    }

    private void loopJogo() {
        try{
            while (!tabuleiro.objetivoConcluido())
            {
                System.out.println(tabuleiro);
                String digitado = capturarValorDigitado("Digite (x, y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim().replace(" ", "")))
                        .iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - Marcar (desmarcar): ");
                if("1".equalsIgnoreCase(digitado))
                {
                    tabuleiro.abrir(xy.next(), xy.next());
                }else if ("2".equalsIgnoreCase(digitado))
                {
                    tabuleiro.marcar(xy.next(), xy.next());
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Você ganhou !!!");
        }catch (ExplosaoException e)
        {
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!!!");
        }
    }

    private String capturarValorDigitado(String texto)
    {
        System.out.print(texto);
        String resposta = scanner.nextLine();
        if("sair".equalsIgnoreCase(resposta))
        {
            throw new SairException();
        }

        return resposta;
    }
}
