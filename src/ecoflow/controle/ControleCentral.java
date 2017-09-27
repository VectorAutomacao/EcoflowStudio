/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ecoflow.modelo.Central;
import ecoflow.modelo.Conexao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.arquivo.Arquivo;
import util.modbus.ModbusConexao;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleCentral {
    
    private final int CONTADOR = 1;
    private final int REFERENCIA = 0;
    private final String NOMEARQUIVO = "./arquivos/listaCentral.xml";
    
    TCPMasterConnection tcpMasterConnection;
    
    public void setTcpMasterConnection(Conexao c){
        tcpMasterConnection = ModbusConexao.configurar(c.getIp(), c.getPorta() );
        tcpMasterConnection.setTimeout(c.getTimeOut() );
    }
    
    public int getIdCentral(){
        int[] respostas = new int[CONTADOR];
        
        respostas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIA, CONTADOR);
        
        return respostas[0];
    }
    
    public void setIdCentral(Central c){
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIA, c.getId() );
    }
    
    public void criarLista(List<Central> listaCentral){
        File file = new File(NOMEARQUIVO);
        
        //Inicia driver do xstream
        XStream xstream = new XStream( new DomDriver() );
        // Auto detectar alias das classes
        xstream.autodetectAnnotations(true);
        
        //Cria string xml
        String xml = xstream.toXML(listaCentral);
        
        if(!file.exists() ){
            try {
                Arquivo.salvar(NOMEARQUIVO, xml);
                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Problema ao criar listaCentral.xml", "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void saveLista(List<Central> listaCentral){        
        //Inicia driver do xstream
        XStream xstream = new XStream( new DomDriver() );
        // Auto detectar alias das classes
        xstream.autodetectAnnotations(true);
        
        //Cria string xml
        String xml = xstream.toXML(listaCentral );
        
        try {
            Arquivo.salvar(NOMEARQUIVO, xml);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List getLista(){
        try {
            //Recupera o arquivo de leitura
            FileReader file = new FileReader(NOMEARQUIVO);
            
            //Inicializa driver xstream
            XStream xstream = new XStream( new DomDriver() );
            
            //Cria alias para as classes
            xstream.alias("central", Central.class);
            
            //Le arquivo xml e retornar um objeto
            return (List) xstream.fromXML(file);
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao ler arquivo listaCentral.xml", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
                       
    }
    
    public Boolean igual(Central central, List<Central> listaCentral){
        
        for(Central c: listaCentral ){
            if(c.getId() == central.getId() ) 
                return true;
        }
        
        return false;
    }
    
    public void setListaCentralNome(int index, Central c, List<Central> listaCentral){
        listaCentral.set(index, c);
    }
        
}
