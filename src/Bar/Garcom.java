package Bar;

import Cliente.Cliente;
import Server.InterfaceRemota;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author euler
 */
public class Garcom implements Serializable {

    private int id;
    private String nome;
    private InterfaceRemota repositorio;
    private int clientePedido;
    private Cliente cliente;
    private boolean pedido;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isPedido() {
        return pedido;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }

    public Garcom() {
    }

    public Garcom(int id, String nome, InterfaceRemota repositorio) {
        this.id = id;
        this.nome = nome;
        this.repositorio = repositorio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getClientePedido() {
        return clientePedido;
    }

    public void setClientePedido(int clientePedido) {
        this.clientePedido = clientePedido;
    }

    public synchronized void pegarBebida(Bebida b, int qtde) throws InterruptedException {
        Thread.sleep(1000);
        if (temIngrediente(b)) {
            try {
                while (isPedido()) {

                    System.out.println("Garcom " + this.getNome() + " - Estou buscando a Bebida " + b.getNome());
                    if (repositorio.getSemaphoreBartender().availablePermits() > 0) {
                        Bartender oBartender = repositorio.getBartenderDisponivel();
                        oBartender.setGarcom(this);
                        oBartender.preparaBebida(b, qtde);
                    } else {
                        System.out.println("Não possui bartenders disponíveis");
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean temIngrediente(Bebida b) {
        return true;
    }

    public void servirBebida(Bebida b, int qtde) throws RemoteException, InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            if (b == null) {

                System.out.println("Garçom " + this.getNome() + " - Pedido negado meu caro " + cliente.getNome());
                repositorio.releaseGarcom();
                repositorio.addGarcomDisponivel(getId());
                setPedido(false);
                cliente.bebe(b, qtde);
            } else {

                System.out.println("Garçom " + this.getNome() + " - Servindo bebida " + b.getNome());
                repositorio.releaseGarcom();
                repositorio.addGarcomDisponivel(getId());
                setPedido(false);
                cliente.bebe(b, qtde);
            }

        }
    }

}
