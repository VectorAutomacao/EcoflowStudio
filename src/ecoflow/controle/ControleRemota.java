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
    
    public void getRemotas(List<Remota> remotas){
        getRemotasLeituras(remotas);
        getRemotasServico(remotas);
        getRemotasMatriculaHidrometro(remotas);
        getRemotasNumeroHidrometro(remotas);
        getRemotasNome(remotas);
    }
    
    public void getRemotasLeituras(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesLeituras(i, remotas.get(i).getUnidades() );
        }
    }
    
    public void getRemotasServico(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesServicos(i, remotas.get(i).getUnidades() );
        }
    }
    
    public void getRemotasMatriculaHidrometro(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesMatriculaHidrometro(i, remotas.get(i).getUnidades() );
        }
    }
    
    public void getRemotasNumeroHidrometro(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNumeroHidrometro(i, remotas.get(i).getUnidades() );
        }
    }
    
    public void getRemotasNome(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNome(i, remotas.get(i).getUnidades() );
        }
    }
       
    public void addRemota(List<Remota> remotas, String nome, int servico){
        Remota r = new Remota();
        int qtd;
        
        //Quantidade de remotas
        qtd = remotas.size() + 1;
        
       //Escrever na central a quantidade de remotas
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIAQTDREMOTA, qtd);
        
        //Configura id da nova remota
        r.setId(qtd);
        //Cria uma lista de 16 unidades para remota
        addUnidades(r, nome, servico);
        
        //Escrever na central
        setUnidadesServicos(remotas.size(), r.getUnidades() );
        setUnidadesMatriculaHidrometro(remotas.size(), r.getUnidades() );
        
        //Adiciona nova remota
        remotas.add(r);
        
    }

    public void criarListaRemota(List<Remota> remotas){
        int[] qtdRemotas = new int[1];
        
        //Leitura da quantidade de remotas
        qtdRemotas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIAQTDREMOTA, 1);
        
        //Cria Lista de remotas com as unidades
        for(int i = 0; i < qtdRemotas[0]; i++){
            Remota remota = new Remota();
            remota.setId(i + 1);
            criarListaUnidade( remota.getUnidades() );
            remotas.add(remota);
        }
    }
    
}
