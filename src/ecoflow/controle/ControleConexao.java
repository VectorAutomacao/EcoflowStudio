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

/**
 *
 * @author vinicius
 */
public class ControleConexao {
    private static String NOMEARQUIVO = "./properties/conexao.properties";
    private static String NOMEIP = "prop.ip";
    private static String NOMEPORTA = "prop.porta";
    private static String NOMETIMEOUT = "prop.timeout";
    
    public Conexao getConexao() throws IOException{
        Conexao conexao = new Conexao();
        Properties props = new Properties();
        
        //Le arquivo properties
        FileInputStream file = new FileInputStream(NOMEARQUIVO);
        props.load(file);
        
        //Alterando o objeto conexão
        conexao.setIp(props.getProperty(NOMEIP) );
        conexao.setPorta(Integer.parseInt(props.getProperty(NOMEPORTA) ) );
        conexao.setTimeOut( Integer.parseInt(props.getProperty(NOMETIMEOUT) ) );
        
        return conexao;
    }

    public void setConexao(Conexao conexao) throws IOException{
        Properties props = new Properties();
        
        //Escrever no arquivo properties
        FileOutputStream fileOut = new FileOutputStream(NOMEARQUIVO);
        props.setProperty(NOMEIP, conexao.getIp() );
        props.setProperty(NOMEPORTA, Integer.toString(conexao.getPorta() ) );
        props.setProperty(NOMETIMEOUT, Integer.toString(conexao.getTimeOut() ) );
        props.store(fileOut, null);
         
    }
       
}
