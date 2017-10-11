/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.modelo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author vinicius
 */
@XStreamAlias("unidade")
public class Unidade {
    
    private int porta;
    private String nome;
    private int servico;
    private int matriculaHidrometro;
    private String numeroHidrometro;
    private int leitura;
    private Boolean habilitado;

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLeitura() {
        return leitura;
    }

    public void setLeitura(int leitura) {
        this.leitura = leitura;
    }

    public int getServico() {
        return servico;
    }

    public void setServico(int servico) {
        this.servico = servico;
    }

    public int getMatriculaHidrometro() {
        return matriculaHidrometro;
    }

    public void setMatriculaHidrometro(int matriculaHidrometro) {
        this.matriculaHidrometro = matriculaHidrometro;
    }

    public String getNumeroHidrometro() {
        return numeroHidrometro;
    }

    public void setNumeroHidrometro(String numeroHidrometro) {
        this.numeroHidrometro = numeroHidrometro;
    }
    
    
    
}
