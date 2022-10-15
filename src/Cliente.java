import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Cliente {


    public static void main(String[] args) {
        
        final Socket clienteSoquete;
        final BufferedReader entrada;
        final PrintWriter saida;

        final Scanner teclado = new Scanner(System.in);

        try {

            clienteSoquete = new Socket("127.0.0.1", 3322);

            saida = new PrintWriter(clienteSoquete.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(clienteSoquete.getInputStream()));

            Thread enviar = new Thread(new Runnable() {
                
                String msg;

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while(true) {

                        msg = teclado.nextLine();
                        saida.println(msg);
                        saida.flush();
                    }
                }
            });

            enviar.start();

            Thread recceber = new Thread(new Runnable() {
                
                String msg;

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        
                        msg = entrada.readLine();

                        while(msg != null) {

                            System.out.println("Servidor : " + msg);
                            msg = entrada.readLine();
                        }

                        System.out.println("Servidor fora de serviço");
                        saida.close();
                        clienteSoquete.close();
                        
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
            recceber.start();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    


}
