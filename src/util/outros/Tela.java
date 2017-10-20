/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.outros;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author vinicius
 */
public class Tela {
    
    public static void chamarInternalFrame(JDesktopPane desktopPane, JInternalFrame jiFrame, Boolean maximizado){
        for(JInternalFrame frame: desktopPane.getAllFrames() ){
            /*Metodo para abrir a mesma janela uma unica vez
            if(frame.getClass().toString().equalsIgnoreCase(jiFrame.getClass().toString() ) ){
                return;
            }*/
            
            //Fecha todas as janelas
            frame.dispose();
        }
        
        //Resolução da DesktopPane
        desktopPane.add(jiFrame);
        jiFrame.setVisible(true);
        if(maximizado){
            try {
                jiFrame.setSelected(true);
                //diz que a janela interna é maximizável   
                //jiFrame.setMaximizable(true);   
                //set o tamanho máximo dela, que depende da janela pai   
                jiFrame.setMaximum(true); 
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
}
