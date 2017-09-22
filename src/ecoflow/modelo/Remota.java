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
public class Remota {
    
    private int id;
    List<Unidade> unidades = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }
    
    public void addUnidade(Unidade un){
        unidades.add(un);
    }
    
    public void removeUnidade(int index){
        unidades.remove(index);
    }
    
    public Unidade getUnidade(int index){
        return unidades.get(index);
    }
    
    public void setUnidade(int index, Unidade un){
        unidades.set(index, un);
    }
    
}
