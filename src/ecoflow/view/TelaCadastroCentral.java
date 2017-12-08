/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoflow.view;

import ecoflow.controle.ControleCentral;
import ecoflow.controle.ControleConexao;
import ecoflow.modelo.Central;
import ecoflow.modelo.CentraisTableModel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.net.TCPMasterConnection;
import util.outros.CampoInt;
import util.outros.Tela;

/**
 *
 * @author vinicius
 */
public class TelaCadastroCentral extends javax.swing.JInternalFrame {
    
    private JDesktopPane        desktopPane         = new JDesktopPane();
    private CentraisTableModel  centralTableModel   = new CentraisTableModel();
    
    private List<Central>       listaCentral        = new ArrayList<>();
    
    private ControleCentral     controleCentral     = new ControleCentral();
    private ControleConexao     controleConexao     = new ControleConexao();
        
    private TCPMasterConnection tcp;
    
    private Boolean flag = true;
    
    /**
     * Creates new form TelaCentral
     * @param dp
     */
    public TelaCadastroCentral(JDesktopPane dp){
        initComponents();
        
        this.desktopPane = dp;
                        
        //Configurando tbCentral
        tbCentral.setModel(centralTableModel);
        tbCentral.setRowSorter(new TableRowSorter(centralTableModel) ); // Ordenar tbCentral
        
        //Verifica se arquivo existe senão existir cria um arquivo com ListaCentral
        controleCentral.criarListaCentralXML(listaCentral);
        
        //Mostra lista de centrais já cadastrado
        listaCentral = controleCentral.getListaCentralXML();
        centralTableModel.setCentrais(listaCentral);
               
    }
    
    public Central getCentral(){
        return listaCentral.get(tbCentral.getSelectedRow() );
    }
    
    private void adicionarNovaCentral(Central c){
                
        if( !controleCentral.igual(c, listaCentral) ){
                        
            try {
                //Escrever na central
                controleCentral.setIdCentral(c);
            
                //Adiciona listaCentral
                listaCentral.add(c);

                //Salvar listaCentral no xml
                controleCentral.saveListaCentralXML(listaCentral);

                //Atualizar tbCentral
                centralTableModel.setCentrais(listaCentral);            
            } catch (ModbusException ex) {
                Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Problema ao escrever na central.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }else{
            JOptionPane.showMessageDialog(null, "Identificador já existe. Tente outro número.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void selecionaTbCentral(){
        tfId.setText( tbCentral.getValueAt(tbCentral.getSelectedRow(), 0).toString() );
        tfNome.setText(tbCentral.getValueAt(tbCentral.getSelectedRow(), 1).toString() );
    }
    
    private void abrirTelaCadastroRemota(){
        if(flag){
            flag = false;

            //Inicia tela carregando
            final TelaCarregando telaCarregando = new TelaCarregando();
            telaCarregando.setVisible(true);
            
            //Thread para processamento
            Thread t = new Thread(){
                @Override
                public void run(){
                    try {

                        //Configurando a conexao
                        tcp = controleConexao.getTcpMasterConnection();
                        controleCentral.setTcpMasterConnection(tcp);

                        //Verifica se central selecionada na tabela é mesma conectada
                        if(controleCentral.getIdCentral() == listaCentral.get(tbCentral.getSelectedRow() ).getId() ){

                            //Inicia tela cadastro de remotas
                            TelaCadastroRemota telaRemota;
                            try {
                                telaRemota = new TelaCadastroRemota(listaCentral.get( tbCentral.getSelectedRow() ) );
                                Tela.chamarInternalFrame(desktopPane,telaRemota, true);
                            } catch (Exception ex) {
                                Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Problema na conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }

                            //Fechar tela carregando
                            telaCarregando.dispose();

                            flag = true; 
                        }else{
                            JOptionPane.showMessageDialog(null, "Central selecionada inválida.", "Alerta", JOptionPane.WARNING_MESSAGE);
                            flag = true;
                            //Fechar tela carregando
                            telaCarregando.dispose();
                        }
                    } catch (ModbusException ex) {
                        Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Problema para indentificar central.", "Erro", JOptionPane.ERROR_MESSAGE);
                        flag = true;
                        //Fechar tela carregando
                        telaCarregando.dispose();
                    } catch (Exception ex) {
                        Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Problema ao criar conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
                        flag = true; 
                        //Fechar tela carregando
                        telaCarregando.dispose();
                    }

                }
            };

            t.start();
        }
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
        jLabel2 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        btAdicionar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCentral = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(600, 400));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Identificador:");

        jLabel2.setText("Nome:");

        tfNome.setNextFocusableComponent(btAdicionar);
        tfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomeKeyPressed(evt);
            }
        });

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });
        btAlterar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btAlterarKeyPressed(evt);
            }
        });

        btAdicionar.setText("Adicionar Nova Central");
        btAdicionar.setNextFocusableComponent(btAlterar);
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });
        btAdicionar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btAdicionarKeyPressed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.setNextFocusableComponent(tbCentral);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });
        btExcluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btExcluirKeyPressed(evt);
            }
        });

        jLabel3.setText("*Identificador é um valor único e numérico");

        tfId.setDocument(new CampoInt(3));
        tfId.setNextFocusableComponent(tfNome);
        tfId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfIdKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btAdicionar)
                        .addGap(18, 18, 18)
                        .addComponent(btAlterar)
                        .addGap(18, 18, 18)
                        .addComponent(btExcluir)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAlterar)
                    .addComponent(btAdicionar)
                    .addComponent(btExcluir))
                .addContainerGap())
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
        tbCentral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbCentralKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCentralKeyReleased(evt);
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
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        // TODO add your handling code here:
        Central c = new Central();
        
        
        if(flag){
            flag = false;
                        
            //Verifica se campos textFiel não estão em branco
            if(
                !tfId.getText().isEmpty() &&
                !tfNome.getText().isEmpty()
            ){  
                try {
                                        
                    //Configurando a conexao
                    tcp = controleConexao.getTcpMasterConnection();
                    controleCentral.setTcpMasterConnection(tcp);
                    
                    //Verifica se central possui id
                    if(controleCentral.getIdCentral() == 0){
                        //Configura objeto central
                        c.setId( Integer.parseInt(tfId.getText().trim() ) );
                        c.setNome(tfNome.getText().trim() );
                        
                        adicionarNovaCentral(c);                    
                    }else{
                        //Configura objeto central
                        c.setId(controleCentral.getIdCentral() );
                        c.setNome(tfNome.getText().trim() );
                        
                        //Verifica se central já esta cadastrado
                        if(!controleCentral.igual(c, listaCentral) ){
                            //Altera lista de centrais
                            adicionarNovaCentral(c);
                        }else{
                            JOptionPane.showMessageDialog(null, "Central já cadastrado.", "Alerta", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (ModbusException ex) {            
                    Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problema para identificar central.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problema ao criar conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }else{
                JOptionPane.showMessageDialog(null, "Identificador ou Nome em branco", "Inválido", JOptionPane.ERROR_MESSAGE);
            }
                        
            flag = true;
        }
        
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void tbCentralMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCentralMouseClicked
        // TODO add your handling code here:
                
        //Verifica se uma linha foi selecionada
        if(tbCentral.getSelectedRow() != -1 ){
            //Verifica o duplo click do mouse
            if(evt.getClickCount() < 2){
                selecionaTbCentral();
            }else{
                abrirTelaCadastroRemota();
            }
        }
        
    }//GEN-LAST:event_tbCentralMouseClicked

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        // TODO add your handling code here:
        Central c = new Central();
        
        
        if(flag){
            flag = false;
                        
            //Verifica se campos são não nulos e se uma linha na tabela central foi selecionado
            if(
                tbCentral.getSelectedRow() != -1 &&
                !tfId.getText().isEmpty() &&
                !tfNome.getText().isEmpty() &&
                !controleCentral.igual(c, listaCentral)
            ){
                try {
                    
                    
                    //Configurando a conexao
                    tcp = controleConexao.getTcpMasterConnection();
                    controleCentral.setTcpMasterConnection(tcp);
                    
                    //Verifica se central selecionada na tabela e a mesma central conectada
                    if(controleCentral.getIdCentral() == listaCentral.get(tbCentral.getSelectedRow() ).getId() ){
                        //configurar objeto central
                        c.setId( Integer.parseInt(tfId.getText().trim()) );
                        c.setNome(tfNome.getText().trim() );
                        
                        //Escrever na central
                        controleCentral.setIdCentral(c);
                        
                        //Altera lista de centrais
                        controleCentral.setListaCentral( tbCentral.getSelectedRow(), c, listaCentral);
                        
                        //Atualiza tabela
                        centralTableModel.setCentrais(listaCentral);
                        
                        //Salva lista
                        controleCentral.saveListaCentralXML(listaCentral);
                        
                        //limpar textField
                        tfId.setText("");
                        tfNome.setText("");
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Central selecionada inválida.", "Alerta", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ModbusException ex) {
                    Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problema para indentificar central.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(TelaCadastroCentral.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problema na conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Identicador ou nome invalido ou linha na tabela não selecionada.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            
            flag = true;
        }
        
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        
        if(tbCentral.getSelectedRow() != -1){
            
            //Excluir xml da central
            controleCentral.removeCentralXML(listaCentral.get(tbCentral.getSelectedRow() ) );
            
            //Altera lista de centrais
            listaCentral.remove(tbCentral.getSelectedRow() );
            
            //Atualiza tabela
            centralTableModel.setCentrais(listaCentral);
            
            //Salva lista
            controleCentral.saveListaCentralXML(listaCentral);
            
            //limpar textField
            tfId.setText("");
            tfNome.setText("");
        }else{
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btAdicionarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btAdicionarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           btAdicionar.doClick();
        }
    }//GEN-LAST:event_btAdicionarKeyPressed

    private void btAlterarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btAlterarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           btAlterar.doClick();
        }
    }//GEN-LAST:event_btAlterarKeyPressed

    private void btExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btExcluirKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           btExcluir.doClick();
       }
    }//GEN-LAST:event_btExcluirKeyPressed

    private void tbCentralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCentralKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            abrirTelaCadastroRemota();
        }
        
    }//GEN-LAST:event_tbCentralKeyPressed

    private void tbCentralKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCentralKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN){
            selecionaTbCentral();
        }
    }//GEN-LAST:event_tbCentralKeyReleased

    private void tfIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfIdKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           btAdicionar.doClick();
        }
    }//GEN-LAST:event_tfIdKeyPressed

    private void tfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           btAdicionar.doClick();
        }
    }//GEN-LAST:event_tfNomeKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCentral;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
