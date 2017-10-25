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
    
    public static int[] stringWord(String palavra){
        byte[]  caracteres = palavra.getBytes();
        int tamCaracteres = caracteres.length;
        int[]   numeros = new int[tamCaracteres]; //6 words
        int contar = 0;
        
        for(int i = 0; i < tamCaracteres; i+=2){
            if( i != tamCaracteres - 1){
                numeros[contar] = (int)caracteres[i] * 256 + (int)caracteres[i + 1] ;
                contar++;
            }else{
                numeros[contar] = (int)caracteres[i];
                contar++;
            }
        }
        
        return numeros;
    }
    
    public static String wordString(int[] numeros){
        String palavra = "";
        
        for(int n: numeros){
            if(n > 0){
                if( n > 256){
                    char a = (char) (n / 256);
                    char b = (char) (n % 256);

                    palavra = palavra + String.valueOf(a);
                    palavra = palavra + String.valueOf(b);
                }else{
                    char a = (char) n;

                    palavra = palavra + String.valueOf(a);
                }
            }
        }
        
        return palavra;
    }
    
    public static int doubleWordInt(int a, int b, int fator){        
        return a * fator + b;
    }
    
    public static int intWordMenos(int num, int fator){
        return num % fator;
    }
    
    public static int intWordMais(int num, int fator){
        return num / fator;
    }
    
}
