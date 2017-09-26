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
public class CentralTableModel extends AbstractTableModel{
    private List<Central> listaCentral = new ArrayList<>();
    private String colunas[] = {"Identificador", "Nome"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return listaCentral.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return listaCentral.get(rowIndex).getId();
            case 1:
                return listaCentral.get(rowIndex).getNome();
        }
        
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                listaCentral.get(rowIndex).setId(Integer.parseInt( (String) aValue ) );
                break;
            case 1:
                listaCentral.get(rowIndex).setNome((String) aValue);
                break;
        }
        
        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public void addRow(Central c){
        this.listaCentral.add(c);
        this.fireTableDataChanged();
    }
    
    public void setCentrais(List<Central> lc){
        this.listaCentral = lc;
        this.fireTableDataChanged();
    }
    
}
