/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.view;

import ecoflow.controle.ControleCentral;
import ecoflow.modelo.Central;
import ecoflow.modelo.CentralTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vinicius
 */
public class TelaCentral extends javax.swing.JInternalFrame {
    
    CentralTableModel   centralTableModel   = new CentralTableModel();
    List<Central>       listaCentral        = new ArrayList<>();
    ControleCentral     controleCentral     = new ControleCentral();
    
    /**
     * Creates new form TelaCentral
     */
    public TelaCentral() {
        initComponents();
        
        //Configurando tbCentral
        tbCentral.setModel(centralTableModel);
        tbCentral.setRowSorter(new TableRowSorter(centralTableModel) ); // Ordenar tbCentral
        
        //Verifica se arquivo existe senão existir cria um arquivo com ListaCentral
        controleCentral.criarLista(listaCentral);
        
        //Mostra lista de centrais já cadastrado
        listaCentral = controleCentral.getLista();
        centralTableModel.setCentrais(listaCentral);
        
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
        jLabel1 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        btAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCentral = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Identificador:");

        jLabel2.setText("Nome:");

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btAdicionar.setText("Adicionar Nova Central");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAlterar)
                    .addComponent(btAdicionar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbCentral.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCentral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCentralMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCentral);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        // TODO add your handling code here:
        Central central = new Central();
        
        if(
            !tfId.getText().isEmpty() &&
            !tfNome.getText().isEmpty()
        ){
            //Configura objeto central
            central.setId( Integer.parseInt(tfId.getText().trim() ) );
            central.setNome(tfNome.getText().trim() );
            
            if( !controleCentral.igual(central, listaCentral) ){

                //Adiciona listaCentral
                listaCentral.add(central);

                //Salvar listaCentral no xml
                controleCentral.saveLista(listaCentral);
                
                //Atualizar tbCentral
                centralTableModel.setCentrais(listaCentral);
                
            }else{
                JOptionPane.showMessageDialog(null, "Identificador já existe. Tente outro número.", "Inválido", JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Identificador ou Nome em branco", "Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void tbCentralMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCentralMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() < 2){
            if(tbCentral.getSelectedRow() != -1 ){
                tfId.setText( tbCentral.getValueAt(tbCentral.getSelectedRow(), 0).toString() );
                tfNome.setText(tbCentral.getValueAt(tbCentral.getSelectedRow(), 1).toString() );
            }            
        }else{
            System.out.println("Qtd clicks: " + evt.getClickCount() );
        }
    }//GEN-LAST:event_tbCentralMouseClicked

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        // TODO add your handling code here:
        if(
            tbCentral.getSelectedRow() != -1 &&
            !tfNome.getText().isEmpty()
        ){
            //Altera lista de centrais
            controleCentral.setListaCentralNome( tbCentral.getSelectedRow(), tfNome.getText().trim(), listaCentral);
            //Atualiza tabela
            centralTableModel.setCentrais(listaCentral);
            //Salva lista
            controleCentral.saveLista(listaCentral);
            //limpar textField
            tfId.setText("");
            tfNome.setText("");
        }
    }//GEN-LAST:event_btAlterarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btAlterar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCentral;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
