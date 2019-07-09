/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.tpfrc.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mateus
 */
public class Servidor implements Runnable {

private static ArrayList<BufferedWriter> clientes;           
private static ServerSocket servidor; 
private String nomeCliente;
private Socket socketCliente;
private InputStream inputStream;
private InputStreamReader inputStreamReader;  
private BufferedReader bufferedReader;

    public Servidor(Socket socketCliente){
        this.socketCliente = socketCliente;
        try {
            inputStream  = socketCliente.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }                          
    }
    
    @Override
    public void run() {
         try{                              
            String mensagem;
            OutputStream outputStream =  this.socketCliente.getOutputStream();
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter); 
            clientes.add(bufferedWriter);
            nomeCliente = mensagem = bufferedReader.readLine();

            while(mensagem != null){           
                mensagem = bufferedReader.readLine();
                enviaParaTodos(bufferedWriter, mensagem);
                System.out.println(mensagem);                                              
            }
       }catch (Exception e) {
         e.printStackTrace();
       }  
    }
    
    public void enviaParaTodos(BufferedWriter bufferedWriterEnviado, String mensagem){
        for(BufferedWriter bufferedWriter : clientes){
            if(bufferedWriterEnviado != bufferedWriter){
                try {
                    bufferedWriter.write(nomeCliente + " : " + mensagem + "\r\n"); 
                    bufferedWriter.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }          
    }
    
    public static void main(String[] args) {
        try {
            //Inicia o servidor na porta 1999
            servidor = new ServerSocket(1999);
            clientes = new ArrayList<BufferedWriter>();
            while(true){
                System.out.println("Esperando alguém se conectar...");
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente conectado - IP do cliente: " +
                        socketCliente.getInetAddress().getHostAddress());
                Thread t = new Thread(new Servidor(socketCliente));
                t.start();   
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
