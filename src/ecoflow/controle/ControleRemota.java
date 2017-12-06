/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.controle;

import ecoflow.modelo.Central;
import ecoflow.modelo.Remota;
import java.util.List;
import net.wimpi.modbus.ModbusException;
import util.modbus.ModbusRegistro;

/**
 *
 * @author vinicius
 */
public class ControleRemota extends ControleUnidade{
    
    public void getRemotasLeituras(List<Remota> remotas) throws ModbusException{
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesLeituras(remotas.get(i) );
        }
    }
    
    public void getRemotasServico(List<Remota> remotas) throws ModbusException{
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesServicos(remotas.get(i) );
        }
    }
    
    public void getRemotasMatriculaHidrometro(List<Remota> remotas) throws ModbusException{
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesMatriculaHidrometro(remotas.get(i) );
        }
    }
    
    public void getRemotasNumeroHidrometro(List<Remota> remotas) throws ModbusException{
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNumeroHidrometro(remotas.get(i) );
        }
    }
    
    public void getRemotasNome(List<Remota> remotas) throws ModbusException{
        
        for(int i = 0; i < remotas.size(); i++){
            getUnidadesNome(remotas.get(i) );
        }
    }
    
    
    public void removeRemota(Central central) throws ModbusException{
        int qtd;
        List<Remota> remotas = central.getRemotas();
        Remota re = remotas.get(remotas.size() - 1);
        
        //Limpar ultima remota
        limparRegistros(re, 2); //serviço
        limparRegistros(re, 3); //matricula
        limparRegistros(re, 4); //numero
        limparRegistros(re, 5); //nome   
        
        //Excluir da central
        remotas.remove(re);
        
        //Quantidade de remotas
        qtd = remotas.size();
        central.setQtdRemotas(qtd);
        
        //Escrever na central a quantidade de remotas
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIAQTDREMOTA, qtd);
                
    }
    
    public void addRemota(Central central, int servico) throws ModbusException{
        Remota r = new Remota();
        int qtd;
        List<Remota> remotas = central.getRemotas();
        

        //Quantidade de remotas
        qtd = remotas.size() + 1;
        central.setQtdRemotas(qtd);

        //Escrever na central a quantidade de remotas
        ModbusRegistro.escrever(tcpMasterConnection, REFERENCIAQTDREMOTA, qtd);

        //Configura id da nova remota
        r.setId(remotas.size() );
        //Cria uma lista de 16 unidades para remota
        addUnidades(r, servico);

        //Escrever na central
        setUnidades(r);

        //Adiciona nova remota
        remotas.add(r);
        
    }
    
    public int getQtdRemota() throws ModbusException{
        int[] qtdRemotas;
        
        qtdRemotas = ModbusRegistro.ler(tcpMasterConnection, REFERENCIAQTDREMOTA, 1);
        return qtdRemotas[0];
        
    }
    
}
