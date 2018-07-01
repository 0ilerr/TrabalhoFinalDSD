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

    public boolean verificarIngredientes(Bebida bebida, int qtde) throws RemoteException {
        boolean bRetorno = true;
        for (IngredienteBebida ingredientesBebida : bebida.getIngredientesBebidas()) {
            int iPosicaoIngrediente = interfaceRemota.getIngrediente(ingredientesBebida.getIngrediente().getId());
            if (interfaceRemota.getQntdeTotalIngrediente(iPosicaoIngrediente) < (ingredientesBebida.getQtde() * qtde)) {
                bRetorno = false;
            }
        }

        return bRetorno;
    }

    public void preparaBebida(Bebida b, int qtde) throws RemoteException, InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            if (verificarIngredientes(b, qtde)) {
                for (int i = 0; i < b.getIngredientesBebidas().size(); i++) {
                    int idIngrediente = b.getIngredientesBebidas().get(i).getIngrediente().getId();
                    int iPosicaoIngrediente = interfaceRemota.getIngrediente(idIngrediente);
                    int iQtdeIngrediente = b.getIngredientesBebidas().get(i).getQtde();

                    interfaceRemota.reduzQtdeEstoqueIngrediente(iPosicaoIngrediente, qtde, iQtdeIngrediente);

                }

                System.out.println("Bartender " + this.getNome() + " - Preparando Bebida " + b.getNome());
                this.entregaBebida(b, qtde);
            } else {
                System.out.println("Bartender " + this.getNome()
                        + " - Ingredientes Indisponiveis para o preparo do pedido!");
                entregaBebida(null, 0);
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
