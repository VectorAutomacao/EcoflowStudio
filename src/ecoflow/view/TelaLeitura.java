/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.view;

import ecoflow.controle.ControleCentral;
import ecoflow.controle.ControleConexao;
import ecoflow.modelo.Central;
import ecoflow.modelo.Unidade;
import ecoflow.modelo.UnidadesTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.net.TCPMasterConnection;

/**
 *
 * @author vinicius
 */
public class TelaLeitura extends javax.swing.JInternalFrame {
        
    private Central             central             = new Central();
    private List<Unidade>       unidades            = new ArrayList<>();
    
    private UnidadesTableModel  unidadesTableModel  = new UnidadesTableModel();
    
    private ControleCentral     controleCentral     = new ControleCentral();
    private ControleConexao     controleConexao     = new ControleConexao();
    
    private static TCPMasterConnection tcp;
    
    private Boolean flag = true;
    
    /**
     * Creates new form Unidade
     */
    public TelaLeitura() throws Exception {
        initComponents();
                       
        //Configurando tbUnidades
        tbUnidade.setModel(unidadesTableModel);
        tbUnidade.setRowSorter(new TableRowSorter(unidadesTableModel) ); //Ordenar tbUnidades
        tbUnidade.getColumnModel().removeColumn(tbUnidade.getColumnModel().getColumn(0) ); //Remove coluna Porta
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btLeitura = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUnidade = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(400, 300));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btLeitura.setText("Leitura");
        btLeitura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLeituraActionPerformed(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.setEnabled(false);
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btLeitura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSalvar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLeitura)
                    .addComponent(btSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbUnidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbUnidade);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btLeituraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLeituraActionPerformed
        // TODO add your handling code here:
        
        int idCentral = 0;
        int qtdRemota = 0;
        
        if(flag){
            flag = false;
            
            try {
                
                //Configurando a conexao
                tcp = controleConexao.getTcpMasterConnection();
                controleCentral.setTcpMasterConnection(tcp);
                
                //Le na central
                idCentral = controleCentral.getIdCentral();
                qtdRemota = controleCentral.getQtdRemota();

                //Le arquivo xml
                central = controleCentral.getCentralXML(idCentral);

                if(central != null){
                    if(central.getQtdRemotas() == qtdRemota){
                        
                        //Recupera lista de unidades
                        controleCentral.getRemotasLeituras(central.getRemotas() );
                        unidades = controleCentral.listaUnidadesCentral(central);

                        //Atualizar tabela
                        unidadesTableModel.setUnidades(unidades);

                        //Ativa botão salvar
                        btSalvar.setEnabled(true); 

                    }else{
                        JOptionPane.showMessageDialog(null, "Central desatualizada no sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Central não Cadastrado no sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ModbusException ex) {
                Logger.getLogger(TelaLeitura.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Problema ao ler a central.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(TelaLeitura.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Problema ao criar conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            flag = true;
        }
               
    }//GEN-LAST:event_btLeituraActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        
        try {
            // TODO add your handling code here:
            controleCentral.saveUnidadesXLS(unidades);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Problema ao salvar o arquivo. Tente novamente", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(TelaLeitura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLeitura;
    private javax.swing.JButton btSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbUnidade;
    // End of variables declaration//GEN-END:variables
}
