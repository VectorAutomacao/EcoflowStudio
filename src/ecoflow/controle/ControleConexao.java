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

/**
 *
 * @author vinicius
 */
public class ControleConexao {
    private static String NOMEARQUIVO = "./properties/conexao.properties";
    private static String NOMEIP = "prop.ip";
    private static String NOMEPORTA = "prop.porta";
    private static String NOMETIMEOUT = "prop.timeout";
    
    public Conexao getConexao(){
        Conexao conexao = new Conexao();
        Properties props = new Properties();
        
        //Le arquivo properties
        try {
            FileInputStream file = new FileInputStream(NOMEARQUIVO);
            props.load(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo properties", "Alerta", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControleConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Alterando o objeto conexão
        conexao.setIp(props.getProperty(NOMEIP) );
        conexao.setPorta(Integer.parseInt(props.getProperty(NOMEPORTA) ) );
        conexao.setTimeOut( Integer.parseInt(props.getProperty(NOMETIMEOUT) ) );
        
        return conexao;
    }

    public void setConexao(Conexao conexao){
        Properties props = new Properties();
        
        //Escrever no arquivo properties
        try {
            FileOutputStream fileOut = new FileOutputStream(NOMEARQUIVO);
            props.setProperty(NOMEIP, conexao.getIp() );
            props.setProperty(NOMEPORTA, Integer.toString(conexao.getPorta() ) );
            props.setProperty(NOMETIMEOUT, Integer.toString(conexao.getTimeOut() ) );
            props.store(fileOut, null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao escrever arquivo properties", "Alerta", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControleConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
}
