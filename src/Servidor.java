import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        
        final ServerSocket servidorSoquete;
        final Socket clienteSoquete;
        final BufferedReader entrada;
        final PrintWriter saida;

        final Scanner teclado = new Scanner(System.in);

        try {
            servidorSoquete = new ServerSocket(3322);
            clienteSoquete = servidorSoquete.accept();

            saida = new PrintWriter(clienteSoquete.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(clienteSoquete.getInputStream()));

            Thread enviar = new Thread(new Runnable() {
                
                // variável que conterá o gravador de dados pelo usuário
                String msg;
                
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while(true) {

                        // lê dados do teclado do usuário
                        msg = teclado.nextLine();

                        // gravar dados armazenados em msg no clienteSoquete
                        saida.println(msg);

                        // força o envio dos dados
                        saida.flush();

                    }
                }
            });
            enviar.start();

            Thread receber = new Thread(new Runnable() {
                
                String msg;

                @Override
                public void run() {
                    
                    try {
                        
                        msg = entrada.readLine();

                        while(msg != null) {

                            System.out.println("Cliente : " + msg);
                            msg = entrada.readLine();
                        }
                        System.out.println("Cliente desconectado!");

                        saida.close();
                        clienteSoquete.close();
                        servidorSoquete.close();

                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });

            receber.start();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}