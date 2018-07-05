package Bar;

import Bar.Bartender;
import Bar.Garcom;
import Cliente.ClienteCliente;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *
 * @author euler
 */
//Servidor Remoto
public class ServidorRemoto {

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            ServidorBar srv = new ServidorBar();

            InterfaceRemota stub = (InterfaceRemota) UnicastRemoteObject.exportObject(srv, 8000);
            Registry registry = LocateRegistry.createRegistry(8000);
            registry.bind("servidor", stub);
            System.out.println("Informe uma quantidade de Garçons:");
            int garcons = s.nextInt();
            //criando garcons
            for (int i = 0; i < garcons; i++) {
                Garcom g = new Garcom(i, "Garcom " + i, stub);
                stub.addGarcom(g);
            }

            System.out.println("Informe uma quantidade de Bartenders:");
            int bartenders = s.nextInt();
            //criando bartenders
            for (int i = 0; i < bartenders; i++) {
                Bartender b = new Bartender(i, "Bartender " + i, stub);
                stub.addBartender(b);
            }

            srv.iniciaCardapio();
            srv.criarListaGarconsDisponiveis();
            srv.criarListaBartendersDisponiveis();
            System.out.println("O Servidor está pronto. Pode executar a quantidade de clientes que desejar");
            ClienteCliente c = new ClienteCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
