package util.modbus;

import java.net.InetAddress;
import javax.swing.JOptionPane;
import net.wimpi.modbus.net.TCPMasterConnection;

public class ModbusConexao {
    
    private static TCPMasterConnection con = null;
        
    public TCPMasterConnection configurar(String ip, int porta, int timeOut){
                    
        try {
            /* Variáveis ​​para armazenar os parâmetros */
            InetAddress addr = InetAddress.getByName(ip); //O endereço do escravo
            
            //2. Abra a conexão
            if(con == null || !con.isConnected() ){
                con = new TCPMasterConnection(addr);
                con.setPort(porta);
                con.setTimeout(timeOut);
                con.connect();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a conexão!", "Alerta", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao criar a conexão!");
            ex.printStackTrace();
        }
        
        return con;
        
    }
    
    public void setTCPMasterConnection(TCPMasterConnection conexao){
        this.con = conexao;    
    }
    
}
