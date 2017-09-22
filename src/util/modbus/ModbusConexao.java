package util.modbus;

import java.net.InetAddress;
import javax.swing.JOptionPane;
import net.wimpi.modbus.net.TCPMasterConnection;

public class ModbusConexao {
    
    public static TCPMasterConnection configurar(String ip, int porta){
                    
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
            JOptionPane.showMessageDialog(null, "Erro ao criar a conexão!", "Alerta", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao criar a conexão!");
            ex.printStackTrace();
            
            return null;
        }
        
    }
    
}
