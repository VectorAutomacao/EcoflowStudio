package util.modbus;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.InputRegister;

public class EntradaAnalogica {
    
    public static InputRegister[] ler(TCPMasterConnection con, int ref, int count){
        
        try {            
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction        trans = null; //A transação
            ReadInputRegistersRequest   req = null; //o pedido
            ReadInputRegistersResponse  res = null; //a resposta
            
            //Prepare o pedido
            req = new ReadInputRegistersRequest(ref, count);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute a transação
            trans.execute();
            
            //Retorno dos valores solicitados
            int k = 0;
            res = (ReadInputRegistersResponse) trans.getResponse();
            
            return res.getRegisters();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao ler multiplas entradas analógicas!");
            
            return null;
        }
        
    }
    
}
