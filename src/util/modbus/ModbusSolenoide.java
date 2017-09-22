package util.modbus;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteCoilResponse;
import net.wimpi.modbus.msg.WriteMultipleCoilsRequest;
import net.wimpi.modbus.msg.WriteMultipleCoilsResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.util.BitVector;

public class ModbusSolenoide {
    
    public static boolean escrever(TCPMasterConnection con, int ref, boolean valor){
        
        try {
            
             /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction    trans = null; //A transação
            WriteCoilRequest        req = null; //o pedido
            WriteCoilResponse       res = null; //a resposta
            
            //Prepare o pedido
            req = new WriteCoilRequest(ref, valor );

            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Verifica se escreveu com sucesso
            res = (WriteCoilResponse) trans.getResponse();
            if( res.getReference() == ref && res.getCoil() == valor ){
                return true;
            }else{
                System.out.println("Erro ao escrever no unica saída discreta! referencia ou valor não corresponde.");
                return false; 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao escrever no unica saída discreta!");
            
            return false;
        }
        
    }
    
    public static boolean escrever(TCPMasterConnection con, int ref, BitVector valor){
        
        try {
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction        trans = null; //A transação
            WriteMultipleCoilsRequest   req = null; //o pedido
            WriteMultipleCoilsResponse  res = null; //a resposta
            
            //Prepare o pedido
            req = new WriteMultipleCoilsRequest(ref, valor);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Verifica se escreveu com sucesso
            res = (WriteMultipleCoilsResponse) trans.getResponse();
            if( res.getReference() == ref && res.getBitCount() == valor.size() ){
                return true;
            }else{
                System.out.println("Erro ao escrever nos multiplas saídas discretas! referencia ou quantidade valores não corresponde.");
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao escrever nos multiplas saídas discretas!");
            
            return false;
        }
        
    }
    
    public static BitVector ler(TCPMasterConnection con, int ref, int count){
        
        try {
            
            /* As instâncias importantes das classes mencionadas anteriormente */
            ModbusTCPTransaction    trans = null; //A transação
            ReadCoilsRequest        req = null; //o pedido
            ReadCoilsResponse       res = null; //a resposta
            
            //Prepare o pedido
            req = new ReadCoilsRequest(ref, count);
            
            //Prepare a transação
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            
            //Execute os tempos de repetição da transação
            trans.execute();
            
            //Retorno dos valores solicitados
            res = (ReadCoilsResponse) trans.getResponse();
            
            return res.getCoils();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao ler saídas discretas!");
            
            return null;
        }
    }
    
}
