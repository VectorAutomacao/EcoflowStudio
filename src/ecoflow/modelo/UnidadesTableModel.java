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
    private String colunas[] = {"Nome", "Serviço", "Leitura"};

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
                return listaUnidades.get(rowIndex).getNome();
            case 1:
                return listaUnidades.get(rowIndex).getServico();
            case 2:
                return listaUnidades.get(rowIndex).getLeitura();
        }
        
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                listaUnidades.get(rowIndex).setNome((String) aValue);
                break;
            case 1:
                listaUnidades.get(rowIndex).setServico(Integer.parseInt( (String) aValue ) );
                break;
            case 2:
                listaUnidades.get(rowIndex).setLeitura(Integer.parseInt( (String) aValue ));
                break;
        }
        
        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public void addRow(Unidade un){
        this.listaUnidades.add(un);
        this.fireTableDataChanged();
    }
    
    public void removeRow(int linha){
        this.listaUnidades.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }
    
    public void setUnidades(List<Unidade> uns){
        this.listaUnidades = uns;
        this.fireTableDataChanged();
    }
    
}
