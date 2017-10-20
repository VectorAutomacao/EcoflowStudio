/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author vincius
 */
public class UnidadesTableModel extends AbstractTableModel{
    
    private List<Unidade> listaUnidades = new ArrayList<>();
    private String colunas[] = {"Porta", "Nome", "Serviço", "Mat. Hidrometro", "N. Hidrometro", "Leitura"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return listaUnidades.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return listaUnidades.get(rowIndex).getPorta();
            case 1:
                return listaUnidades.get(rowIndex).getNome();
            case 2:
                return listaUnidades.get(rowIndex).getServico();
            case 3:
                return listaUnidades.get(rowIndex).getMatriculaHidrometro();
            case 4:
                return listaUnidades.get(rowIndex).getNumeroHidrometro();
            case 5:
                return listaUnidades.get(rowIndex).getLeitura();
        }
        
        return null;
    }
    
    public void setUnidades(List<Unidade> uns){
        this.listaUnidades = uns;
        this.fireTableDataChanged();
    }
    
}
