package util.modbus;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.wimpi.modbus.net.TCPMasterConnection;

public class ModbusConexao {
    
    private static TCPMasterConnection con = null;
        
    public TCPMasterConnection configurar(String ip, int porta, int timeOut) throws UnknownHostException, Exception{
               
        /* Variáveis ​​para armazenar os parâmetros */
        InetAddress addr = InetAddress.getByName(ip); //O endereço do escravo

        //2. Abra a conexão
        if(con == null){
            con = new TCPMasterConnection(addr);
            con.setPort(porta);
            con.setTimeout(timeOut);
            con.connect();
        }else{
            if( !testeConexaoRegistro() ){
                con = new TCPMasterConnection(addr);
                con.setPort(porta);
                con.setTimeout(timeOut);
                con.connect();
            }
        }
        
        
        return con;
    }
    
    public void setTCPMasterConnection(TCPMasterConnection conexao){
        this.con = conexao;    
    }
    
    public Boolean testeConexaoRegistro(){
        
        try{
            int[] resp = ModbusRegistro.ler(con, 0, 1);
            return true;
        }catch(Exception e){
            return false;
        } 
        
    }
    
}
