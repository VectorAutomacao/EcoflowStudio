package util.modbus;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusSlaveException;
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
    
    public static void escrever(TCPMasterConnection con, int ref, boolean valor) throws ModbusSlaveException, ModbusException{
                           
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
    }
    
    public static void escrever(TCPMasterConnection con, int ref, BitVector valor) throws ModbusSlaveException, ModbusException{
        
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
                
    }
    
    public static BitVector ler(TCPMasterConnection con, int ref, int count) throws ModbusSlaveException, ModbusException{
        
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
    }
    
}
