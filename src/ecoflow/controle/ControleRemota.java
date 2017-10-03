/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Remota;
import java.util.ArrayList;
import java.util.List;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleRemota extends ControleUnidade{
    
    
    public List<Remota> getRemotas(){
        int[] qtdRemotas = new int[1];
        List<Remota> remotas = new ArrayList<>();
        
        //Leitura da quantidade de remotas
        qtdRemotas = ModbusRegistro.ler(tcpMasterConnection, 1, 1);
        
        for(int i = 0; i < qtdRemotas[0]; i++){
            //Cria uma nova remota
            Remota remota = new Remota();
            
            //Configura remota
            remota.setId(i);
            remota.setUnidades(getUnidades(i) );
            
            //Adiciona nova remota a lista
            remotas.add(remota);
        }
                
        return remotas;
    }
    
}
