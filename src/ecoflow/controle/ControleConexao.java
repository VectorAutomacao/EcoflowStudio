/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Conexao;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.modbus.ModbusConexao;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleConexao {
    private final String NOMEARQUIVO = "./properties/conexao.properties";
    private final String NOMEIP = "prop.ip";
    private final String NOMEPORTA = "prop.porta";
    private final String NOMETIMEOUT = "prop.timeout";
    
    private final int CONTADOR = 1;
    private final int REFERENCIA = 0;
    
    //Leitura do arquivo properties de conexão
    public Conexao getConexao(){
        Conexao conexao = new Conexao();
        Properties props = new Properties();
        
        //Le arquivo properties
        try {
            FileInputStream file = new FileInputStream(NOMEARQUIVO);
            props.load(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo de configurações não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControleConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Alterando o objeto conexão
        conexao.setIp(props.getProperty(NOMEIP) );
        conexao.setPorta(Integer.parseInt(props.getProperty(NOMEPORTA) ) );
        conexao.setTimeOut( Integer.parseInt(props.getProperty(NOMETIMEOUT) ) );
        
        return conexao;
    }
    
    //Edita arquivo properties de conexão
    public void setConexao(Conexao conexao) throws IOException{
        Properties props = new Properties();
        
        //Escrever no arquivo properties
        FileOutputStream fileOut = new FileOutputStream(NOMEARQUIVO);
        props.setProperty(NOMEIP, conexao.getIp() );
        props.setProperty(NOMEPORTA, Integer.toString(conexao.getPorta() ) );
        props.setProperty(NOMETIMEOUT, Integer.toString(conexao.getTimeOut() ) );
        props.store(fileOut, null);
         
    }
    
    //Retorna conexão TCP master configurada
    public TCPMasterConnection getTcpMasterConnection(){
        TCPMasterConnection tcpMasterConnection;
        ModbusConexao mc = new ModbusConexao();
        Conexao c = getConexao();
        
        //Configura conexão TCP master
        tcpMasterConnection = mc.configurar(c.getIp(), c.getPorta() );
        tcpMasterConnection.setTimeout(c.getTimeOut() );
        
        return tcpMasterConnection;
    }
    
    //Testa conexão TCP
    public Boolean testarConexao(){
        int[] respostas = new int[CONTADOR];
        TCPMasterConnection tcp = getTcpMasterConnection();
        
        //le um registro da central
        respostas = ModbusRegistro.ler(tcp, REFERENCIA, CONTADOR);
        
        tcp.close();
        
        if(respostas != null){
            return true;
        }else{
            return false;
        }        
    }
       
}
