/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinicius
 */
public class Central {
    
    private int id;
    private String nome;
    List<Remota> remotas = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Remota> getRemotas() {
        return remotas;
    }

    public void setRemotas(List<Remota> remotas) {
        this.remotas = remotas;
    }
    
    public void addRemota(Remota re){
        remotas.add(re);
    }
    
    public void removeRemota(int index){
        remotas.remove(index);
    }
    
    public Remota getRemota(int index){
        return remotas.get(index);
    }
    
    public void setRemota(int index, Remota re){
        remotas.set(index, re);
    }    
    
}
