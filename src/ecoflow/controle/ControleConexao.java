/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Conexao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinicius
 */
public class ControleConexao {
    private static Conexao conexao = new Conexao();
    private static String NOMEARQUIVO = "./properties/conexao.properties";
    
    public static Conexao getConexao(){
        Properties props = new Properties();
        
        try {
            FileInputStream file = new FileInputStream(NOMEARQUIVO);
            props.load(file);
        } catch (IOException ex) {
            Logger.getLogger(ControleConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conexao.setIp(props.getProperty("prop.ip") );
        conexao.setPorta(Integer.parseInt(props.getProperty("prop.porta") ) );
        return conexao;
    }

    public static void setConexao(Conexao conexao){
        //Alterando properties
        Properties props = new Properties();
        
        try {
            FileOutputStream fileOut = new FileOutputStream(NOMEARQUIVO);
            props.setProperty("prop.ip", conexao.getIp() );
            props.setProperty("prop.porta", Integer.toString(conexao.getPorta() ) );
            props.store(fileOut, null);
        } catch (IOException ex) {
            Logger.getLogger(ControleConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ControleConexao.conexao = conexao;
    }
    
    public static Properties getProp(String arquivo) throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(arquivo);
        props.load(file);
        return props;

    }
    
}
