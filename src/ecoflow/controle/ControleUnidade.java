/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Unidade;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.wimpi.modbus.net.TCPMasterConnection;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.modbus.ModbusRegistro;
import util.outros.Arquivo;

/**
 *
 * @author vinicius
 */
public class ControleUnidade {
    
    private final int CONTADOR = 32; //Quantidade de leituras
    private final int REFERENCIA = 2; //Inicio da leitura
    private final int FATORMULTIPLICATIVO = 10000; //fator multiplicativo para o segundo registro
    
    TCPMasterConnection tcpMasterConnection;
   
    public void setTcpMasterConnection(TCPMasterConnection tcp ){
        this.tcpMasterConnection = tcp;
    }
    
    public List<Unidade> getUnidades(){
        List<Unidade> unidades = new ArrayList<>();
        int[] remotas = new int[1];
        int[] leituras = new int[CONTADOR];
        int referencia = REFERENCIA;
        
        remotas = ModbusRegistro.ler(tcpMasterConnection, 1, 1);
        
        //Leitura por quantidade de remotas
        for(int j = 0; j < remotas[0]; j++){
            
            //Leitura de uma remota
            leituras = ModbusRegistro.ler(tcpMasterConnection, referencia, CONTADOR);
            
            //Converte o double word
            for(int i = 0; i < CONTADOR; i += 2){
                Unidade unidade = new Unidade();
                unidade.setLeitura( leituras[i] + leituras[i + 1] * FATORMULTIPLICATIVO );
                unidades.add(unidade);
            }
            referencia += CONTADOR;
        }
        
        return unidades;
    }
    
    public void saveXLS(List<Unidade> unidades) throws FileNotFoundException, IOException{
        int contador = 1;
        
        //Ecolhe local do arquivo para salvar
        String url = Arquivo.escolher(2);
        
        if(url != null){
            // arquivo do excel
            HSSFWorkbook wb = new HSSFWorkbook();
            // cria a planilha
            HSSFSheet plan = wb.createSheet("Leitura");
            
            // cria a linha na planilha o parametro da função create row é a linha
            HSSFRow rowTitle = plan.createRow(0);
            // cria a célula na planilha
            rowTitle.createCell((short) 0).setCellValue("Nome");
            rowTitle.createCell((short) 1).setCellValue("Leitura");
            
            for(Unidade un: unidades){
                // cria a linha na planilha o parametro da função create row é a linha
                HSSFRow row = plan.createRow(contador);
                // cria a célula na planilha
                row.createCell((short) 0).setCellValue(un.getNome() );
                row.createCell((short) 1).setCellValue(un.getLeitura() );
                
                contador++;
            }
            
            // cria o arquivo do excel
            FileOutputStream stream = new FileOutputStream(url + ".xls");
            // escreve os dados na planilha
            wb.write(stream);
            //Fechar arquivo
            stream.flush();
            stream.close();
            wb.close();
        }        
    }
    
}
