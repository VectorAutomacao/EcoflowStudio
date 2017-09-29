/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ecoflow.modelo.Central;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.arquivo.Arquivo;
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
    
    public void setTcpMasterConnection(TCPMasterConnection tcp ){
        this.tcpMasterConnection = tcp;
    }
    
    //Busca id na central
    public int getIdCentral(){
        int[] respostas = new int[CONTADOR];
        
        respostas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIA, CONTADOR);
        
        return respostas[0];
    }
    
    //Configura id na central
    public void setIdCentral(Central c){
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIA, c.getId() );
    }
    
    //Cria o arquivo listaCentral.xml senão existir
    public void criarLista(List<Central> listaCentral){
        File file = new File(NOMEARQUIVO);
        
        //Verifica se arquivo já existe
        if(!file.exists() ){
            //Inicia driver do xstream
            XStream xstream = new XStream( new DomDriver() );
            // Auto detectar alias das classes
            xstream.autodetectAnnotations(true);

            //Cria string xml
            String xml = xstream.toXML(listaCentral);
        
            try {
                Arquivo.salvar(NOMEARQUIVO, xml);
                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Problema ao criar listaCentral.xml", "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Salva a listaCentral.xml
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
    
    //Leitura do arquvo listaCentral.xml
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
    
    //Verifica por id se central já existe na List
    public Boolean igual(Central central, List<Central> listaCentral){
        
        for(Central c: listaCentral ){
            if(c.getId() == central.getId() ) 
                return true;
        }
        
        return false;
    }
    
    //Alterar Central na List
    public void setListaCentralNome(int index, Central c, List<Central> listaCentral){
        listaCentral.set(index, c);
    }
        
}
