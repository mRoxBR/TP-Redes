/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.tpfrc.main;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Mateus
 */
public class Cliente extends javax.swing.JFrame{

    private Socket socket;
    private OutputStream outputStream;
    private Writer outputStreamWriter; 
    private BufferedWriter bufferedWriter;
    private final JTextField txtNome;
    private final JTextField txtIP;
    /**
     * Creates new form Cliente
     */
    public Cliente() {
        initComponents();
        
        JLabel lblMensagem = new JLabel("Informe o seu nome e o IP destino:");
        txtNome = new JTextField("Nome");
        txtIP = new JTextField("127.0.0.1");
        Object[] texts = {lblMensagem, txtNome, txtIP};  
        JOptionPane.showMessageDialog(null, texts);
        
        //Faz scrolling automaticamente quando novas mensagens são enviadas
        DefaultCaret caret = (DefaultCaret)txtaChat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    
    public void conectar(){
        try {
            socket = new Socket(txtIP.getText(),1999);
            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(txtNome.getText() + "\r\n");
            bufferedWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarMensagem(String mensagem){  
        try {
            bufferedWriter.write(mensagem + "\r\n");
            txtaChat.append(txtNome.getText() + "(eu) : " + txtMsg.getText() + "\r\n");
            bufferedWriter.flush();        
            txtMsg.setText("");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void escutarEntrada(){                  
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String mensagem = "";
            
            while(mensagem != null){
                if(bufferedReader.ready()){
                    mensagem = bufferedReader.readLine();         
                    txtaChat.append(mensagem + "\r\n");
                } 
            }               
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        txtaChat = new javax.swing.JTextArea();
        txtMsg = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtaChat.setEditable(false);
        txtaChat.setColumns(20);
        txtaChat.setRows(5);
        jScrollPane1.setViewportView(txtaChat);

        txtMsg.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMsgKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMsgKeyTyped(evt);
            }
        });

        btnEnviar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtMsg))
                    .addComponent(btnEnviar))
                .addGap(3, 3, 3))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
        enviarMensagem(txtMsg.getText());
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void txtMsgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMsgKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMsgKeyTyped

    private void txtMsgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMsgKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            enviarMensagem(txtMsg.getText());                                                          
        } 
    }//GEN-LAST:event_txtMsgKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        
        Cliente cliente = new Cliente();
        cliente.setVisible(true);
        cliente.conectar();
        cliente.escutarEntrada();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMsg;
    private static javax.swing.JTextArea txtaChat;
    // End of variables declaration//GEN-END:variables

}
