package Bar;

import Server.InterfaceRemota;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author euler
 */
public class Bartender implements Serializable {

    private int id;
    private String nome;
    private InterfaceRemota interfaceRemota;
    private int clientePedido;
    private Estoque estoque = Estoque.getInstance();
    private Garcom garcom;
    private boolean ingredientes;

    public Bartender(int id, String nome, InterfaceRemota interfaceRemota) {
        this.id = id;
        this.nome = nome;
        this.interfaceRemota = interfaceRemota;
    }

    public Bartender() {
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

    public Garcom getGarcom() {
        return garcom;
    }

    public void setGarcom(Garcom garcom) {
        this.garcom = garcom;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean verificarIngredientes(Bebida bebida) {

        return true;
    }

    public void preparaBebida(Bebida b, int qtde) throws RemoteException, InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            if (verificarIngredientes(b)) {
                for (int i = 0; i < b.getIngredientesBebidas().size(); i++) {
                    System.out.println(estoque.getIngredientes().get(i).getNome()
                            + " - " + estoque.getIngredientes().get(i).getQtdeTotal());
                    
                    estoque.getIngredientes().get(interfaceRemota.getIngrediente(b.getIngredientesBebidas().get(i).getIngrediente().getId()))
                            .reduzQtde(b.getIngredientesBebidas().get(i).getQtde());
                    
                    System.out.println(estoque.getIngredientes().get(i).getNome()
                                + " - " + estoque.getIngredientes().get(i).getQtdeTotal());
                }

                System.out.println("Bartender Disponivel!");
                System.out.println("Bartender " + this.getNome() + " - Preparando Bebida " + b.getNome());
                this.entregaBebida(b, qtde);
            } else {
                entregaBebida(null, 0);
                System.out.println("Bartender " + this.getNome()
                        + "Ingredientes Indisponiveis para o preparo do pedido!");
            }
        }
    }

    public void entregaBebida(Bebida b, int qtde) throws RemoteException, InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Bartender " + this.getNome() + " - Devolvendo Pedido");
            interfaceRemota.addBartenderDisponivel(getId());
            interfaceRemota.releaseBartender();
            garcom.servirBebida(b, qtde);
        }
    }

    void setClientePedido(int clientePedido) {
        this.clientePedido = clientePedido;
    }

}
