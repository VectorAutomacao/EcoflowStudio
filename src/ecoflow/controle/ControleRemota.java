/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Remota;
import java.util.List;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleRemota extends ControleUnidade{
    
    
    public void getRemotasLeituras(List<Remota> remotas){
        int[] qtdRemotas = new int[1];
        int contar;
        
        //Leitura da quantidade de remotas
        qtdRemotas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIAQTDREMOTA, 1);
        
        for(int i = 0; i < qtdRemotas[0]; i++){
            contar = i + 1;
            
            //Cria uma nova remota
            Remota remota = new Remota();
            
            //Configura remota
            remota.setId(contar);
            getUnidadesLeituras(i, remota.getUnidades() );
            
            //Adiciona nova remota a lista
            remotas.add(remota);
        }
    }
    
    public void addRemota(List<Remota> remotas, String nome, int servico){
        Remota r = new Remota();
        int qtd;
        
        //Quantidade de remotas
        qtd = remotas.size() + 1;
        
       //Escrever da quantidade de remotas
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIAQTDREMOTA, qtd);
        
        //Configura id da nova remota
        r.setId(qtd);
        //Adiciona nova remota
        remotas.add(r);
                
        //Configura nomes e configurações da unidades
        addUnidades(remotas.get(qtd - 1), nome, servico);
    }
        
}
