package br.com.chiliexe.cm.model;

import br.com.chiliexe.cm.excecao.ExplosaoException;

import java.util.ArrayList;

public class Tabuleiro {
    private int qtdLinhas;
    private int qtdColunas;
    private int qtdMinas;

    private final ArrayList<Campo> campos = new ArrayList<>();

    public Tabuleiro(int qtdLinhas, int qtdColunas, int qtdMinas) {
        this.qtdLinhas = qtdLinhas;
        this.qtdColunas = qtdColunas;
        this.qtdMinas = qtdMinas;

        gerarCampos();
        adicionarOsVizinhos();
        adicionarMinas();
    }

    public void abrir(int linha, int coluna)
    {
        try {
            campos.stream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrirCampo());
        } catch (ExplosaoException e) {
            campos.stream()
                    .filter(Campo::isMinado)
                    .forEach(x -> x.setAberto(true));

            throw e;
        }
    }
    public void marcar(int linha, int coluna)
    {
        campos.stream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcado());
    }

    private void gerarCampos() {
        for (int linha = 0; linha < qtdLinhas; linha++)
        {
            for (int coluna =0; coluna < qtdColunas; coluna++)
            {
                campos.add(new Campo(linha + 1, coluna + 1));
            }
        }
    }

    private void adicionarOsVizinhos() {
        for(Campo c1 : campos)
        {
            for(Campo c2 : campos)
            {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void adicionarMinas() {
        long minasArmadas = 0;
        do{
            int aleatorio = (int) (Math.random() * campos.size() );
            campos.get(aleatorio).minarCampo();
            minasArmadas = campos.stream().filter(Campo::isMinado).count();
        }while (minasArmadas < qtdMinas);
    }

    public boolean objetivoConcluido()
    {
        return campos.stream().allMatch(Campo::objetivoConcluido);
    }

    public void reiniciar()
    {
        campos.forEach(c -> c.reiniciar());
        adicionarMinas();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for(int c = 0; c < qtdColunas; c++)
        {
            sb.append(" ");
            sb.append(c + 1);
            sb.append(" ");
        }
        sb.append("\n");

        int index = 0;
        for(int l=0; l < qtdLinhas; l++)
        {
            sb.append(l + 1);
            sb.append(" ");

            for(int c=0; c < qtdColunas; c++)
            {
                sb.append(" ");
                sb.append(campos.get(index));
                sb.append(" ");
                index++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
