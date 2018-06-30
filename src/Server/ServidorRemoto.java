package Server;

import Bar.Bartender;
import Bar.Garcom;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author euler
 */
//Servidor Remoto
public class ServidorRemoto {

    public static void main(String[] args) {
        try {
            ServidorBar srv = new ServidorBar();

            InterfaceRemota stub = (InterfaceRemota) UnicastRemoteObject.exportObject(srv, 8000);
            Registry registry = LocateRegistry.createRegistry(8000);
            registry.bind("servidor", stub);
            System.out.println("servidor pronto");

            //criando garcos
            for (int i = 0; i <= 5; i++) {
                Garcom g = new Garcom(i, "Garcom " + i, stub);
                stub.addGarcom(g);
            }

            //criando bartenders
            for (int i = 0; i < 3; i++) {
                Bartender b = new Bartender(i, "Bartender " + i, stub);
                stub.addBartender(b);
            }

            srv.iniciaCardapio();
            srv.criarListaGarconsDisponiveis();
            srv.criarListaBartendersDisponiveis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
