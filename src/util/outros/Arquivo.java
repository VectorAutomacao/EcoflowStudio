package util.outros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Arquivo {
    
    // METODO PARA APRESENTAR O MENU DE ESCOLHA DE ARQUIVOS
    // 1 - PARA LEITURA
    // 2 - PARA GRAVACAO
    //Retorna uma string com caminho e nome do arquivo ou nulo caso não seleciona nada
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
    
    
    public static String escolherXML(int operacao) {
        int retorno;
        String caminhoArquivo;
        JFileChooser arquivo;

        retorno = 0;
        arquivo = new JFileChooser(new File("."));
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
        arquivo.setFileFilter(filter);

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
    
    public static void salvar(String caminho,String texto) throws FileNotFoundException{
        
        File file = new File(caminho);
        PrintWriter print;
        print = new PrintWriter(file);

        print.write(texto);
        print.flush();
        print.close();
        
    }
    
}
