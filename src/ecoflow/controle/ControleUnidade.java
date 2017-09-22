/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Conexao;
import ecoflow.modelo.Unidade;
import java.util.ArrayList;
import java.util.List;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.modbus.ModbusConexao;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleUnidade {
    
    private int CONTADOR = 100; //Quantidade de leituras
    private int REFERENCIA = 0; //Inicio da leitura
    private int FATORMULTIPLICATIVO = 10000;
    
    TCPMasterConnection tcpMasterConnection;
    
    public ControleUnidade(){
        
    }
    
    public ControleUnidade(Conexao c){
        tcpMasterConnection = ModbusConexao.configurar(c.getIp(), c.getPorta() );
        tcpMasterConnection.setTimeout(c.getTimeOut() );
    }
    
    public void setTcpMasterConnection(Conexao c){
        tcpMasterConnection = ModbusConexao.configurar(c.getIp(), c.getPorta() );
        tcpMasterConnection.setTimeout(c.getTimeOut() );
    }
    
    public List<Unidade> getUnidades(){
        List<Unidade> unidades = new ArrayList<>();
        int[] respostas = new int[CONTADOR];
        
        respostas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIA, CONTADOR);
        
        for(int i = 0; i < CONTADOR; i += 2){
            Unidade unidade = new Unidade();
            unidade.setLeitura( respostas[i] + respostas[i + 1] * FATORMULTIPLICATIVO );
            unidades.add(unidade);
        }
        
        return unidades;
    }
    
}
