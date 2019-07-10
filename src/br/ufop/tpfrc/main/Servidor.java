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
//classe servidor implementa a interface Runnable, para utilizar threads
public class Servidor implements Runnable { 
private static ArrayList<BufferedWriter> clientes; //serve para armazenar dados de escrita do cliente     
private static ServerSocket servidor;  //reserva socket pro servidor
private String nomeCliente;
private Socket socketCliente; //reserva socket para o cliente
private InputStream inputStream; //permite fluxos de entrada
private InputStreamReader inputStreamReader;  //realiza a leitura do fluxo de dados 
private BufferedReader bufferedReader; //reserva uma região de memória para o que foi lido no fluxo de entrada

//construtor da classe Servidor recebe como parâmetro socket do cliente
    public Servidor(Socket socketCliente){
        this.socketCliente = socketCliente; //inicializa o atributo socketCliente com o socket passado por parâmetro
        try {
            inputStream  = socketCliente.getInputStream(); //permite o fluxo de entrada de dados
            inputStreamReader = new InputStreamReader(inputStream); //instancia um leitor de fluxo de dados
            bufferedReader = new BufferedReader(inputStreamReader); //instancia uma região de memória pra armazenar os dados
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
            clientes.add(bufferedWriter); //região de memória que armazena a saida de dados de um cliente
            nomeCliente = mensagem = bufferedReader.readLine(); //leitura da saida de dados do cliente

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
        for(BufferedWriter bufferedWriter : clientes){ //percorre a lista de clientes, sendo o bufferedWriter o elementro em questão
            if(bufferedWriterEnviado!= bufferedWriter){ //verifica se o bufferedWriter em questão é do cliente que estã enviando a mensagem
                try {
                    bufferedWriter.write(nomeCliente + " : " + mensagem + "\r\n"); 
                    bufferedWriter.flush(); //limpa a região de memória do bufferedWriter
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
                Socket socketCliente = servidor.accept(); //servidor aceita qualquer conexão solicitada 
                System.out.println("Cliente conectado - IP do cliente: " +
                        socketCliente.getInetAddress().getHostAddress()); //recupera o IP do cliente
                Servidor s = new Servidor(socketCliente); 
                Thread t = new Thread(s); //representa um servidor que está associado a um socket de um cliente especifico
                t.start();  //invoca o método run da classe referente ao objeto que foi instanciado
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
