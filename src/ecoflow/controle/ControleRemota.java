/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Central;
import ecoflow.modelo.Remota;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.wimpi.modbus.ModbusException;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleRemota extends ControleUnidade{
    
    public void getRemotasLeituras(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesLeituras(remotas.get(i) );
        }
    }
    
    public void getRemotasServico(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesServicos(remotas.get(i) );
        }
    }
    
    public void getRemotasMatriculaHidrometro(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesMatriculaHidrometro(remotas.get(i) );
        }
    }
    
    public void getRemotasNumeroHidrometro(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNumeroHidrometro(remotas.get(i) );
        }
    }
    
    public void getRemotasNome(List<Remota> remotas){
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNome(remotas.get(i) );
        }
    }
       
    public void addRemota(Central central, int servico){
        Remota r = new Remota();
        int qtd;
        List<Remota> remotas = central.getRemotas();
        
        try {

            //Quantidade de remotas
            qtd = remotas.size() + 1;
            central.setQtdRemotas(qtd);

            //Escrever na central a quantidade de remotas
            ModbusRegistro.escrever(tcpMasterConnection, REFERENCIAQTDREMOTA, qtd);

            //Configura id da nova remota
            r.setId(remotas.size() );
            //Cria uma lista de 16 unidades para remota
            addUnidades(r, servico);

            //Escrever na central
            setUnidades(r);

            //Adiciona nova remota
            remotas.add(r);
            
        } catch (ModbusException ex) {
            Logger.getLogger(ControleRemota.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao ler e escrever os multiplos registro! "
                    , "Erro", JOptionPane.ERROR_MESSAGE);                
        }
        
    }

    /*public void criarListaRemota(List<Remota> remotas){
        int qtdRemotas;
        
        //Leitura da quantidade de remotas
        qtdRemotas = getQtdRemota();
        
        //Cria Lista de remotas com as unidades
        for(int i = 0; i < qtdRemotas; i++){
            Remota remota = new Remota();
            remota.setId(i);
            criarListaUnidade( remota.getUnidades() );
            remotas.add(remota);
        }
    }*/
    
    public int getQtdRemota(){
        int[] qtdRemotas;
        
        try {
            qtdRemotas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIAQTDREMOTA, 1);
            return qtdRemotas[0];
        } catch (ModbusException ex) {
            Logger.getLogger(ControleRemota.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao ler multiplos registro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
}
