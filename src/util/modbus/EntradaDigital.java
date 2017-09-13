package util.modbus;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.util.BitVector;

public class EntradaDigital {
    
    public static BitVector ler(TCPMasterConnection con, int ref, int count){
                
        try {            
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction        trans = null; //A transação
            ReadInputDiscretesRequest   req = null; //o pedido
            ReadInputDiscretesResponse  res = null; //a resposta
            
            //Prepare o pedido
            req = new ReadInputDiscretesRequest(ref, count);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Retorno dos valores solicitados
            res = (ReadInputDiscretesResponse) trans.getResponse();
            
            return res.getDiscretes();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao entradas discretas!");
            
            return null;
        }
        
    }
    
}
