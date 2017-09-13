package util.modbus;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteMultipleRegistersRequest;
import net.wimpi.modbus.msg.WriteMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;

public class Registro {
    
    public static boolean escrever(TCPMasterConnection con, int ref, int valor){
        /* Variáveis ​​para armazenar os parâmetros 
        ref = A referência; Desloca onde começar a ler
        valor = dados a ser escrito no registro*/
        
        try {
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction        trans = null; //A transação
            WriteSingleRegisterRequest  req = null; //o pedido
            WriteSingleRegisterResponse res = null; //a resposta
            
            //Prepare o pedido
            req = new WriteSingleRegisterRequest(ref, new SimpleRegister(valor) );

            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Verifica se escreveu com sucesso
            res = (WriteSingleRegisterResponse) trans.getResponse();
            if( res.getReference() == ref && res.getRegisterValue() == valor ){
                return true;
            }else{
                System.out.println("Erro ao escrever no unico registro! referencia ou valor não corresponde.");
                return false; 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao escrever no unico registro!");
            
            return false;
        }
        
    }
    
    public static boolean escrever(TCPMasterConnection con, int ref, int[] valores){
            /* Variáveis ​​para armazenar os parâmetros 
            ref = A referência; Desloca onde começar a ler*/
        
        try {
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction            trans = null; //A transação
            WriteMultipleRegistersRequest   req = null; //o pedido
            WriteMultipleRegistersResponse  res = null; //a resposta
            
            //Prepare o pedido
            int sizeValores = valores.length;            
            Register [] reg = new Register[sizeValores];
            for(int i = 0; i < sizeValores; i++){
                reg[i] = new SimpleRegister( valores[i] );
            }
            req = new WriteMultipleRegistersRequest(ref, reg);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Verifica se escreveu com sucesso
            res = (WriteMultipleRegistersResponse) trans.getResponse();
            if( res.getReference() == ref && res.getWordCount() == sizeValores ){
                return true;
            }else{
                System.out.println("Erro ao escrever nos multiplos registro! referencia ou quantidade valores não corresponde.");
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao escrever nos multiplos registro!");
            
            return false;
        }
        
    }
    
    public static int[] ler(TCPMasterConnection con, int ref, int count){
            /* Variáveis ​​para armazenar os parâmetros 
            ref = A referência; Desloca onde começar a ler
            count = O número de DI's para ler
            e numero de loop para repetir a transação*/
            
            //Vetor de resposta
            int[] valores = new int[count];
        
        try {            
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction            trans = null; //A transação
            ReadMultipleRegistersRequest    req = null; //o pedido
            ReadMultipleRegistersResponse   res = null; //a resposta
            
            //Prepare o pedido
            req = new ReadMultipleRegistersRequest(ref, count);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Retorno dos valores solicitados
            int k = 0;
            res = (ReadMultipleRegistersResponse) trans.getResponse();
            do {
                valores[k] = res.getRegisterValue(k);
                k++;
            } while (k < count);
            
            return valores;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao ler multiplos registro!");
            
            return null;
        }
        
    }
    
}