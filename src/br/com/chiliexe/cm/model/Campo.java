package br.com.chiliexe.cm.model;

import br.com.chiliexe.cm.excecao.ExplosaoException;

import java.util.ArrayList;

public class Campo {
    private final int linha;
    private final int coluna;

    private boolean minado = false;
    private boolean aberto = false;
    private boolean marcado = false;

    private ArrayList<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho)
    {
        boolean isDiagonal = vizinho.linha != linha && vizinho.coluna != coluna;
        int deltaVizinho = Math.abs(linha - vizinho.linha) + Math.abs(coluna - vizinho.coluna);

        if( (!isDiagonal && deltaVizinho == 1) || (isDiagonal && deltaVizinho == 2))
        {
            vizinhos.add(vizinho);
            return true;
        }

        return false;

    }

    void alternarMarcado()
    {
        if(!aberto)
        {
            marcado = !marcado;
        }
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isMinado() {
        return minado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean abrirCampo()
    {
        if(!aberto && !marcado)
        {
            aberto = true;
            if(minado)
            {
                throw new ExplosaoException();
            }

            if(vizinhosSeguro())
            {
                vizinhos.forEach(vizinho -> vizinho.abrirCampo());
            }

            return true;
        }

        return false;
    }

    void minarCampo()
    {
        minado = true;
    }
    boolean vizinhosSeguro()
    {
        return vizinhos.stream().noneMatch(e -> e.minado);
    }

    boolean objetivoConcluido()
    {
        boolean concluido = !minado && aberto;
        boolean protegido = minado && marcado;

        return concluido || protegido;
    }

    long minasVizinhas()
    {
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar()
    {
        aberto = false;
        minado = false;
        marcado = false;
    }

    @Override
    public String toString() {
        if(marcado) return "X";
        else if(minado && aberto) return "*";
        else if(aberto && minasVizinhas() > 0) return Long.toString(minasVizinhas());
        else if(aberto) return "_";
        else return "?";
    }
}
