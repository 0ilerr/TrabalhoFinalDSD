package Cliente;

import Server.InterfaceRemota;
import java.rmi.Naming;
import java.util.Random;

/**
 *
 * @author euler
 */
public class ClienteCliente {

    public static void main(String[] args) {
        try {
            InterfaceRemota repositorioCliente = (InterfaceRemota) Naming.lookup("rmi://127.0.0.1:8000/servidor");
            Random random = new Random();
            int bebida = random.nextInt(4);
            int id = repositorioCliente.totalClientes();
            String nome = "Cliente " + id + "";
            Cliente c = new Cliente(id, 20, nome, repositorioCliente.getCardapio().get(bebida), repositorioCliente);
            repositorioCliente.addCliente(c);
            new Thread(c, nome).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
