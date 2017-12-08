/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Remota;
import ecoflow.modelo.Unidade;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.net.TCPMasterConnection;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.modbus.ModbusRegistro;
import util.outros.Arquivo;
import util.outros.Converte;

/**
 *
 * @author vinicius
 */
public class ControleUnidade {
    
    final int CONTADOR = 16;                            //Quantidade de unidades por remota
    final int REFERENCIAQTDREMOTA = 0;                  //Referencia para quantidade de remota
    final int REFERENCIALEITURA = 1;                    //Inicio da leitura 1 a 640 (2 words)
    final int REFERENCIASERVICO = 641;                  //Referencia tipo de leitura 641 a 961 (1-agua fria 2-Agua quente 3-gas)
    final int REFERENCIAMATRICULAHIDROMETRO = 962;      //Referencia matricula 962 a 1601 (2 words)
    final int REFERENCIANUMEROHIDROMETRO = 1602;        //Referencia Numero de hidrometro 1602 a 3521 (6 words)
    final int REFERENCIANOME = 3522;                    //Referencia nome das unidades 3522 a 5122 (5 words)
    final int REFERENCIAIDCENTRAL = 5123;               //Referencia para quantidade de remota
    final int REFERENCIANUMEROREMOTA = 8000;            //Referencia numero da remota
    final int REFERENCIATRIGGER = 8001;                 //Referencia TRIGGER
    final int REFERENCIANUMEROUNIDADE = 8002;           //Referencia posição da unidade
    final int REFERENCIAEDITARLEITURA = 8003;           //Referencia da leitura
    final int FATORMULTIPLICATIVO = 65536;              //fator multiplicativo para o segundo registro
    
    TCPMasterConnection tcpMasterConnection;
   
    public void setTcpMasterConnection(TCPMasterConnection tcp ){
        this.tcpMasterConnection = tcp;
    }
    
    public void getUnidades(Remota remota) throws ModbusException{
        //getUnidadesLeituras(remota);
        getUnidadesServicos(remota);
        getUnidadesMatriculaHidrometro(remota);
        getUnidadesNumeroHidrometro(remota);
        getUnidadesNome(remota);
    }
    
    public void setUnidades(Remota remota) throws ModbusException{
        //setUnidadesLeituras(remota);
        setUnidadesServicos(remota);
        setUnidadesMatriculaHidrometro(remota);
        setUnidadesNumeroHidrometro(remota);
        setUnidadesNome(remota);
    }
    
    //Le na central todos as leituras de uma central
    public void getUnidadesLeituras(List<Unidade> unidades) throws ModbusException{
        int contador = CONTADOR * 2; // 2 words
        int referencia = REFERENCIALEITURA;
        int contar = 0;
        
        int[] remotas;
        
        remotas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIAQTDREMOTA, 1);

        //Leitura por quantidade de remotas
        for(int j = 0; j < remotas[0]; j++){

            //Leitura de uma remota
            int[] leituras = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

            //Converte o double word
            for(int i = 0; i < contador; i += 2){
                Unidade un = unidades.get(contar);
                un.setLeitura( Converte.doubleWordIntMais(leituras[i], leituras[i + 1], FATORMULTIPLICATIVO) );
                contar++;
            }
            referencia += contador;
        }
            
    }
    
    //Le na central as leituras de uma remota
    public void getUnidadesLeituras(Remota remota) throws ModbusException{
        int contador = CONTADOR * 2; // 2 words
        int[] leituras = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();
        
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIALEITURA + contador * remota.getId();

        //Leitura de uma remota
        leituras = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

        //Converte o double word
        for(int i = 0; i < contador; i += 2){
            Unidade un = unidades.get(contar);
            un.setLeitura( Converte.doubleWordIntMais(leituras[i], leituras[i + 1], FATORMULTIPLICATIVO) );
            contar++;
        }
        
    }
    
    //Escreve na central leitura de uma remota
    public void setUnidadeLeitura(Unidade unidade, int idRemota) throws ModbusException, Exception{
        int[] vetor = new int[5];
        
        //Numero da remota
        vetor[0] = idRemota + 1;
        
        //Trigger
        vetor[1] = 1;
        
        //Numero da unidade
        vetor[2] = unidade.getPorta() + 1;
        
        //Valor da leitura
        vetor[3] = Converte.intWordMais(unidade.getLeitura(), FATORMULTIPLICATIVO);
        vetor[4] = Converte.intWordMenos(unidade.getLeitura(), FATORMULTIPLICATIVO);
                
        /*
        Escreve edição da leitura
        Numero da remota %MW8000
        Trigger %MW8001
        Numero da unidade %MW8002
        Leitura da unidade HI-%MW8003 LO-%MW8004
        */        
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIANUMEROREMOTA, vetor);
        
        //Tempo de espera
        Thread.sleep(10);
        
        /*
        0 - Leitura
        1 - Escrita
        Trigger modo de leitura
        */
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIATRIGGER, 0);
        
    }
        
    //Le da central os servicos de uma remota
    public void getUnidadesServicos(Remota remota) throws ModbusException{
        int contador = CONTADOR; // 16 por remota 1 word
        int[] servico = new int[contador];
        int referencia;
        List<Unidade> unidades = remota.getUnidades();
        
        //Calculo para inicio do registro de servicos
        referencia = REFERENCIASERVICO + contador * remota.getId();

        //Leitura de uma remota
        servico = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

        //Altera lista unidades
        for(int i = 0; i < contador; i++){
            Unidade un = unidades.get(i);
            un.setServico( servico[i] );
        }
        
    }
    
    //Escreve na central os servicos de uma remota
    public void setUnidadesServicos(Remota remota) throws ModbusException{
        int contador = CONTADOR; // 16 por remota 1 word
        int[] servico = new int[contador];
        int referencia;
        List<Unidade> unidades = remota.getUnidades();

        //Altera vetor servico
        for(int i = 0; i < contador; i++){
            Unidade un = unidades.get(i);
            servico[i] = un.getServico();
        }
        
        //Calculo para inicio do registro de servicos
        referencia = REFERENCIASERVICO + contador * remota.getId();

        //Escrita de uma remota
        ModbusRegistro.escrever(tcpMasterConnection, referencia, servico);
    }
    
    //Le na central matriculas dos hidrometros de uma remota
    public void getUnidadesMatriculaHidrometro(Remota remota) throws ModbusException{
        int contador = CONTADOR * 2; // 2 word
        int[] matriculas = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();
        
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIAMATRICULAHIDROMETRO + contador * remota.getId();

        //Leitura de uma remota
        matriculas = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

        //Converte o double word
        for(int i = 0; i < contador; i += 2){
            Unidade un = new Unidade();

            un = unidades.get(contar);
            un.setMatriculaHidrometro(Converte.doubleWordIntMenos(matriculas[i], matriculas[i + 1], FATORMULTIPLICATIVO) );
            contar++;
        }
    }
    
    //Escreve na central matriculas dos hidrometros de uma remota
    public void setUnidadesMatriculaHidrometro(Remota remota) throws ModbusException{
        int contador = CONTADOR * 2; // 2 word
        int[] matriculas = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();

        //Converte em double word
        for(Unidade un: unidades){
            int a, b;
            
            a = Converte.intWordMenos(un.getMatriculaHidrometro(), FATORMULTIPLICATIVO);
            b = Converte.intWordMais(un.getMatriculaHidrometro(), FATORMULTIPLICATIVO);
            matriculas[contar] = a;
            contar++;
            matriculas[contar] = b;
            contar++;
        }
        
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIAMATRICULAHIDROMETRO + contador * remota.getId();

        //Escreve em uma remota
        ModbusRegistro.escrever(tcpMasterConnection, referencia, matriculas);
    }
    
    //Le na central numero do hidrometro
    public void getUnidadesNumeroHidrometro(Remota remota) throws ModbusException{
        int contador = CONTADOR * 6; // 6 word
        int[] numeros = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();
        
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIANUMEROHIDROMETRO + contador * remota.getId();
        
        //Leitura de uma remota
        numeros = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

        //Converte o double word
        for(int i = 0; i < contador; i += 6){
            Unidade un = new Unidade();
            int[] numero = new int[6];

            numero[0] = numeros[i];
            numero[1] = numeros[i + 1];
            numero[2] = numeros[i + 2];
            numero[3] = numeros[i + 3];
            numero[4] = numeros[i + 4];
            numero[5] = numeros[i + 5];

            un = unidades.get(contar);
            un.setNumeroHidrometro(Converte.wordString(numero) );
            contar++;
        }
        
    }
    
    //Escreve na central numero de hidrometro
    public void setUnidadesNumeroHidrometro(Remota remota) throws ModbusException{
        int contador = CONTADOR * 6; // 6 word
        int[] numeros = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();

        //Converte em word
        for(Unidade un: unidades){
            int[] numero = Converte.stringWord(un.getNumeroHidrometro() );
            
            for(int i = 0; i < 6; i++){
                if( i < numero.length){
                    numeros[contar] = numero[i];
                }else{
                    numeros[contar] = 0;
                }
                contar++;                    
            }
        }
                
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIANUMEROHIDROMETRO + contador * remota.getId();

        //Escreve em uma remota
        ModbusRegistro.escrever(tcpMasterConnection, referencia, numeros);
    }
    
    //Le na central nomes
    public void getUnidadesNome(Remota remota) throws ModbusException{
        int contador = CONTADOR * 5; // 5 word
        int[] nomes = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();
        
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIANOME + contador * remota.getId();

        //Leitura de uma remota
        nomes = ModbusRegistro.ler(tcpMasterConnection, referencia, contador);

        //Converte o double word
        for(int i = 0; i < contador; i += 5){
            Unidade un = new Unidade();
            int[] nome = new int[5];

            nome[0] = nomes[i];
            nome[1] = nomes[i + 1];
            nome[2] = nomes[i + 2];
            nome[3] = nomes[i + 3];
            nome[4] = nomes[i + 4];

            un = unidades.get(contar);
            un.setNome(Converte.wordString(nome) );
            contar++;
        }
    }
    
    //Escreve na central nomes
    public void setUnidadesNome(Remota remota) throws ModbusException{
        int contador = CONTADOR * 5; // 5 word
        int[] nomes = new int[contador];
        int referencia;
        int contar = 0;
        List<Unidade> unidades = remota.getUnidades();

        //Converte em word
        for(Unidade un: unidades){
            int[] nome = Converte.stringWord(un.getNome() );
            
            for(int i = 0; i < 5; i++){
                if( i < nome.length){
                    nomes[contar] = nome[i];
                }else{
                    nomes[contar] = 0;
                }
                contar++;                    
            }
        }
                
        //Calculo para inicio da leitura do registro
        referencia = REFERENCIANOME + contador * remota.getId();

        //Escreve em uma remota
        ModbusRegistro.escrever(tcpMasterConnection, referencia, nomes);
    }
    
    //Limpa o registros
    public void limparRegistros(Remota remota, int tipo) throws ModbusException{
        int contador = CONTADOR;
        int referencia = REFERENCIALEITURA;
        
        if(tipo == 1){ 
            contador = CONTADOR * 2;
            referencia = REFERENCIALEITURA;
        }
        if(tipo == 2){ 
            contador = CONTADOR;
            referencia = REFERENCIASERVICO;
        }
        if(tipo == 3){ 
            contador = CONTADOR * 2;
            referencia = REFERENCIAMATRICULAHIDROMETRO;
        }
        if(tipo == 4){ 
            contador = CONTADOR * 6;
            referencia = REFERENCIANUMEROHIDROMETRO;
        }
        if(tipo == 5){ 
            contador = CONTADOR * 5;
            referencia = REFERENCIANOME;
        }
        
        
        int[] valor = new int[contador];

        //Altera vetor
        for(int i = 0; i < contador; i++){
            valor[i] = 0;
        }
        
        //Calculo para inicio do registro de servicos
        referencia = referencia + contador * remota.getId();

        //Escrita de uma remota
        ModbusRegistro.escrever(tcpMasterConnection, referencia, valor);
    }
    
    //Cria uma lista de unidades para uma remota
    public void addUnidades(Remota remota, int servico) throws ModbusException{
        List<Unidade> unidades = remota.getUnidades();

       for(int i = 0; i < CONTADOR; i++ ){           
           Unidade un = new Unidade();
           
           //Altera nome e as configurações da unidade
           un.setPorta(i);
           un.setNome("");
           un.setServico(servico);
           un.setMatriculaHidrometro(0);
           un.setNumeroHidrometro("0");
           un.setLeitura(0);
           
           //Adiciona unidade na lista
           unidades.add(un);
        }
       
        setUnidadesServicos(remota);
        setUnidadesMatriculaHidrometro(remota);
        setUnidadesNumeroHidrometro(remota);
        setUnidadesNome(remota);
       
    }
    
    //Cria uma lista de 16 unidades nulas
    public void criarListaUnidade(List<Unidade> unidades){
        for(int i = 0; i < CONTADOR; i++){
            Unidade un = new Unidade();
            un.setPorta(i);
            unidades.add(un);
        }
    }
    
    //Salva as unidades em um arquivo xls(Excel)
    public void saveUnidadesXLS(List<Unidade> unidades) throws FileNotFoundException, IOException{
        int contador = 1;
        
        //Ecolhe local do arquivo para salvar
        String url = Arquivo.escolher(2);
        
        if(url != null){
            // arquivo do excel
            HSSFWorkbook wb = new HSSFWorkbook();
            // cria a planilha
            HSSFSheet plan = wb.createSheet("Leitura");
            
            // cria a linha de titulos
            HSSFRow rowTitle = plan.createRow(0);
            // cria a célula com nomes
            rowTitle.createCell((short) 0).setCellValue("Nome");
            rowTitle.createCell((short) 1).setCellValue("Serviço");
            rowTitle.createCell((short) 2).setCellValue("Matricula Hidrometro");
            rowTitle.createCell((short) 3).setCellValue("Número Hidrometro");
            rowTitle.createCell((short) 4).setCellValue("Leitura");
            
            for(Unidade un: unidades){
                // cria a linha na planilha o parametro da função create row é a linha
                HSSFRow row = plan.createRow(contador);
                // cria a célula na planilha
                row.createCell((short) 0).setCellValue(un.getNome() );
                row.createCell((short) 1).setCellValue(un.getServico() );
                row.createCell((short) 2).setCellValue(un.getMatriculaHidrometro() );
                row.createCell((short) 3).setCellValue(un.getNumeroHidrometro() );
                row.createCell((short) 4).setCellValue(un.getLeitura() );
                
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
