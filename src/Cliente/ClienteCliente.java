package Cliente;

import Server.InterfaceRemota;
import java.rmi.Naming;

/**
 *
 * @author euler
 */
public class ClienteCliente {

    public static void main(String[] args) {
        try {
            InterfaceRemota repositorioCliente = (InterfaceRemota) Naming.lookup("rmi://127.0.0.1:8000/servidor");
            int id = repositorioCliente.totalClientes() + 1;
            Cliente c = new Cliente(id, repositorioCliente);
            repositorioCliente.addCliente(c);
            new Thread(c).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}