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
import util.outros.Arquivo;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleCentral extends ControleRemota{
    
    private final String NOMEARQUIVO = "./arquivos/listaCentral.xml";
    private final String LOCALARQUIVO = "./arquivos/";
        
    //Busca id na central
    public int getIdCentral(){
        int[] respostas = new int[1];
        
        respostas = ModbusRegistro.ler(tcpMasterConnection, 0, 1);
        
        return respostas[0];
    }
    
    //Configura id na central
    public Boolean setIdCentral(Central c){
        return ModbusRegistro.escrever(tcpMasterConnection, 0, c.getId() );
    }
    
    //Cria o arquivo central senão existir
    public void criarCentral(Central central){
        File file = new File(LOCALARQUIVO + central.getId() + ".xml");
        
        //Verifica se arquivo já existe
        if(!file.exists() ){
            //Inicia driver do xstream
            XStream xstream = new XStream( new DomDriver() );
            // Auto detectar alias das classes
            xstream.autodetectAnnotations(true);

            //Cria string xml
            String xml = xstream.toXML(central);
        
            try {
                Arquivo.salvar(LOCALARQUIVO + central.getId() + ".xml", xml);                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Problema ao criar xml da central", "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Salva central no xml
    public void saveCentral(Central central){
        //Inicia driver do xstream
        XStream xstream = new XStream( new DomDriver() );
        // Auto detectar alias das classes
        xstream.autodetectAnnotations(true);
        
        //Cria string xml
        String xml = xstream.toXML(central);
        
        try {
            Arquivo.salvar(LOCALARQUIVO + central.getId() + ".xml", xml);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void setCentralLista(int index, Central c, List<Central> listaCentral){
        listaCentral.set(index, c);
    }
        
}
