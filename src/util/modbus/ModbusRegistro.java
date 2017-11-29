package util.modbus;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusSlaveException;
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

public class ModbusRegistro {
    
    public static void escrever(TCPMasterConnection con, int ref, int valor) throws ModbusSlaveException, ModbusException{
        /* Variáveis ​​para armazenar os parâmetros 
        ref = A referência; Desloca onde começar a ler
        valor = dados a ser escrito no registro*/
        
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
        
    }
    
    public static void escrever(TCPMasterConnection con, int ref, int[] valores) throws ModbusSlaveException, ModbusException{
        /* Variáveis ​​para armazenar os parâmetros 
        ref = A referência; Desloca onde começar a ler*/

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
                
    }
    
    public static int[] ler(TCPMasterConnection con, int ref, int count) throws ModbusSlaveException, ModbusException{
        /* Variáveis ​​para armazenar os parâmetros 
        ref = A referência; Desloca onde começar a ler
        count = O número de DI's para ler
        e numero de loop para repetir a transação*/

        //Vetor de resposta
        int[] valores = new int[count];

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
    }
    
}