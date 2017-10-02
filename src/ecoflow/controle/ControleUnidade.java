/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Unidade;
import java.util.ArrayList;
import java.util.List;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleUnidade {
    
    private final int CONTADOR = 32; //Quantidade de leituras
    private final int REFERENCIA = 2; //Inicio da leitura
    private final int FATORMULTIPLICATIVO = 10000; //fator multiplicativo para o segundo registro
    
    TCPMasterConnection tcpMasterConnection;
   
    public void setTcpMasterConnection(TCPMasterConnection tcp ){
        this.tcpMasterConnection = tcp;
    }
    
    public List<Unidade> getUnidades(){
        List<Unidade> unidades = new ArrayList<>();
        int[] remotas = new int[1];
        int[] leituras = new int[CONTADOR];
        int referencia = REFERENCIA;
        
        remotas = ModbusRegistro.ler(tcpMasterConnection, 1, 1);
        
        //Leitura por quantidade de remotas
        for(int j = 0; j < remotas[0]; j++){
            
            //Leitura de uma remota
            leituras = ModbusRegistro.ler(tcpMasterConnection, referencia, CONTADOR);
            
            //Converte o double word
            for(int i = 0; i < CONTADOR; i += 2){
                Unidade unidade = new Unidade();
                unidade.setLeitura( leituras[i] + leituras[i + 1] * FATORMULTIPLICATIVO );
                unidades.add(unidade);
            }
            referencia += CONTADOR;
        }
        
        return unidades;
    }
    
}
