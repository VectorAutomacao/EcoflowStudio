/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.outros;

/**
 *
 * @author vinicius
 */
public class Converte {
    
    public static int[] stringInt(String palavra){
        byte[]  caracteres = palavra.getBytes();
        int[]   numeros = new int[ (int) Math.round((double) caracteres.length / 2)];
        int contar = 0;
        
        for(int i = 0; i < numeros.length; i++){
            numeros[i] = (int)caracteres[contar] + (int) caracteres[contar + 1];
            contar += 2;
        }
        
        return numeros;
    }
    
    public static String intString(int[] numeros){
        String palavra = "";
        
        for(int n: numeros){
            char c = (char) n;
            palavra = palavra + String.valueOf(c);
        }
        
        return palavra;
    }
    
    public static int doubleWordInt(int a, int b, int fator){        
        return a + b * fator;
    }
    
    public static int intWordMenos(int a, int fator){
        return a % fator;
    }
    
    public static int intWordMais(int a, int fator){
        return a / fator;
    }
    
}
