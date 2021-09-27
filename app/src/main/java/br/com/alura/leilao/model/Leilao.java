package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    // private double maiorLance = Double.NEGATIVE_INFINITY;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public void propoe(Lance lance) {
        if (lanceNaoValido(lance)) return;
        lances.add(lance);
        double valorLance = lance.getValor();
        if (defineMaiorEMenorLanceParaPrimeiroLance(valorLance)) return;
        Collections.sort(lances);
        calculaMaiorLance(valorLance);
        calculaMenorLance(valorLance);
    }

    private boolean defineMaiorEMenorLanceParaPrimeiroLance(double valorLance) {
        if (lances.size() == 1) {
            menorLance = valorLance;
            maiorLance = valorLance;
            return true;
        }
        return false;
    }

    private boolean lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();
        if (lanceMenorQueOUltimoLance(valorLance)) return true;
        if (temLances()) {
            Usuario usuarioNovo = lance.getUsuario();
            if (usuarioForOMesmoDoUltimoLance(usuarioNovo)) return true;
            if (mesmoUsuarioDeuCincoLances(usuarioNovo)) return true;

        }
        return false;
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean mesmoUsuarioDeuCincoLances(Usuario usuarioNovo) {
        int contadorLances = 0;
        for (Lance l : lances) {
            Usuario usuarioExistente = l.getUsuario();

            if (usuarioExistente.equals(usuarioNovo)) {
                contadorLances++;
                if (contadorLances == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForOMesmoDoUltimoLance(Usuario usuarioNovo) {
        //o ultimo lance vai ser sempre o primeiro lance da lista
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if (usuarioNovo.equals(ultimoUsuario)) {
            return true;
        }
        return false;
    }

    private boolean lanceMenorQueOUltimoLance(double valorLance) {
        if (valorLance < maiorLance) {
            return true;
        }
        return false;
    }

    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance) {
            menorLance = valorLance;
        }
    }

    private void calculaMaiorLance(double valorLance) {
        if (valorLance > maiorLance) {
            maiorLance = valorLance;
        }
    }

    public List<Lance> tresMaioresLances() {
        int quantidadeMaximaLances = lances.size();
        if (quantidadeMaximaLances > 3) {
            quantidadeMaximaLances = 3;
        }
        return lances.subList(0, quantidadeMaximaLances);
    }

    public int quantidadeLances() {
        return lances.size();
    }


}
