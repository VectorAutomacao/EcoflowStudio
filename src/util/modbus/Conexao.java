package util.modbus;

import java.net.InetAddress;
import net.wimpi.modbus.net.TCPMasterConnection;

public class Conexao {
    
    public static TCPMasterConnection configurar(String ip, int porta) throws Exception{
                    
        try {    
            /* As instâncias importantes das classes mencionadas anteriormente */
            TCPMasterConnection con = null; //a conexão

            /* Variáveis ​​para armazenar os parâmetros */
            InetAddress addr = InetAddress.getByName(ip); //O endereço do escravo
            
            //2. Abra a conexão
            con = new TCPMasterConnection(addr);
            con.setPort(porta);
            con.connect();
            
            return con;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro ao criar a conexão!");
            
            return null;
        }
        
    }
    
}
