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
 * @author vinicius
 */
public class RemotasTableModel extends AbstractTableModel{
    
    List<Remota> listaRemota = new ArrayList<>();
    private String colunas[] = {"Remotas"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public int getRowCount() {
        return listaRemota.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return "Remota " + (listaRemota.get(rowIndex).getId() + 1 );
        }
        
        return null;
    }
    
    public void setRemotas(List<Remota> rem){
        this.listaRemota = rem;
        this.fireTableDataChanged();
    }
    
}
