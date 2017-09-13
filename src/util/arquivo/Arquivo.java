package util.arquivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Arquivo {
    
    // METODO PARA APRESENTAR O MENU DE ESCOLHA DE ARQUIVOS
    // 1 - PARA LEITURA
    // 2 - PARA GRAVACAO
    public static String escolher(int operacao) {
        int retorno;
        String caminhoArquivo;
        JFileChooser arquivo;

        retorno = 0;
        arquivo = new JFileChooser(new File("."));

        // TIPO DE OPERACAO A SER REALIZADA
        switch (operacao) {
            case 1:
                retorno = arquivo.showOpenDialog(null);
                break;

            case 2:
                retorno = arquivo.showSaveDialog(null);
        }

        // OPERACAO
        caminhoArquivo = null;

        if (retorno == JFileChooser.APPROVE_OPTION) {
            try {
                caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("erro: " + e);
            }
        }

        return (caminhoArquivo);
    }
    
    public static void salvarXML(String xml){
        
        try {
            String caminho = escolher(2);
            File file = new File(caminho + ".xml");
            PrintWriter print;
            print = new PrintWriter(file);
            
            print.write(xml);
            print.flush();
            print.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
