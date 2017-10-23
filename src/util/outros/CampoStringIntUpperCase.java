/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.outros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author vinicius
 */
public class CampoStringIntUpperCase extends PlainDocument{
    private int qtdMaxima;
    
    public CampoStringIntUpperCase(int maxLen){
        super();
        if(maxLen <= 0)
         throw new IllegalArgumentException("Especifique o tamanho!");
        
        qtdMaxima = maxLen;
        
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(str == null || getLength() == qtdMaxima){
            return;
        }
        
        int totalqtd = (getLength() + str.length() );
        
        if(totalqtd <= qtdMaxima){
            super.insertString(offset, str.toUpperCase().replaceAll("[^a-z|^A-Z|^0-9]",""), attr);
            return;
        }
        
        String nova = str.substring(0, getLength() - qtdMaxima);
        super.insertString(offset, nova, attr);
    }
    
}
