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
import util.arquivo.Arquivo;

/**
 *
 * @author vinicius
 */
public class ControleCentral {
    
    private final String NOMEARQUIVO = "./arquivos/listaCentral.xml";
    
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
    
    public void setListaCentralNome(int index, String nome, List<Central> listaCentral){
        Central c = listaCentral.get(index);
        c.setNome(nome);
        listaCentral.set(index, c);
    }
    
}
