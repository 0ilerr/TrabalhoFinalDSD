/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Bar.Bartender;
import Bar.Bebida;
import Bar.Estoque;
import Bar.Garcom;
import Bar.IngredienteBebida;
import Cliente.Cliente;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author euler
 */
public class ServidorBar implements InterfaceRemota, Serializable {

    public static Estoque estoque = Estoque.getInstance();
    public static ArrayList<Bebida> cardapio = new ArrayList<>();
    private List<Garcom> garcons = new ArrayList<>();
    private List<Garcom> garconsDisponiveis = new ArrayList<>();
    private List<Bartender> bartenders = new ArrayList<Bartender>();
    private List<Bartender> bartendersDisponiveis = new ArrayList<Bartender>();
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private Semaphore garconsSemaphore;
    private Semaphore bartenderSemaphore;
    private int garconsPermitidos;

    public ServidorBar() throws RemoteException {
        super();
    }

    public void setGarconsSemaphore(Semaphore garconsSemaphore) throws RemoteException {
        this.garconsSemaphore = garconsSemaphore;
    }

    @Override
    public void addGarcom(Garcom garcom) throws RemoteException {
        garcons.add(garcom);
    }

    @Override
    public void addBartender(Bartender bartender) throws RemoteException {
        bartenders.add(bartender);
    }

    @Override
    public Garcom getGarcomId(int id) throws RemoteException {
        return this.garcons.stream().filter(a
                -> a.getId() == (id)).findFirst().get();
    }

    @Override
    public Bartender getBartenderId(int id) throws RemoteException {
        return this.bartenders.stream().filter(a
                -> a.getId() == (id)).findFirst().get();
    }

    @Override
    public Semaphore getSemaphoreGarcom() throws RemoteException {
        return garconsSemaphore;
    }

    public Semaphore getBartenderSemaphore() throws RemoteException {
        return bartenderSemaphore;
    }

    public void setBartenderSemaphore(Semaphore bartenderSemaphore) throws RemoteException {
        this.bartenderSemaphore = bartenderSemaphore;
    }

    @Override
    public void setGarconsDisponiveis(int garconsPermitidos) throws RemoteException {
        this.garconsPermitidos = garconsPermitidos;
    }

    public void criarListaGarconsDisponiveis() throws RemoteException {
        this.garconsPermitidos = garcons.size();
        for (int i = 0; i < garcons.size(); i++) {
            garconsDisponiveis.add(garcons.get(i));
        }
        Semaphore s = new Semaphore(this.garconsPermitidos);
        this.setGarconsSemaphore(s);
    }

    @Override
    public int totalClientes() throws RemoteException {
        return clientes.size();
    }

    public void criarListaBartendersDisponiveis() throws RemoteException {
        for (int i = 0; i < bartenders.size(); i++) {
            bartendersDisponiveis.add(bartenders.get(i));
        }
        Semaphore s = new Semaphore(this.bartendersDisponiveis.size());
        this.setBartenderSemaphore(s);
    }

    @Override
    public int garconsPermitidos() throws RemoteException {
        return garconsDisponiveis.size();
    }

    @Override
    public void teste() throws RemoteException {
        System.out.println("Garcons Size " + garcons.size());
    }

    public void iniciaCardapio() {
        IngredienteBebida coca1 = new IngredienteBebida(1, estoque.getIngredienteById(0), 1);
        IngredienteBebida gelo1 = new IngredienteBebida(2, estoque.getIngredienteById(1), 1);
        IngredienteBebida vodka1 = new IngredienteBebida(3, estoque.getIngredienteById(2), 1);
        IngredienteBebida coca2 = new IngredienteBebida(4, estoque.getIngredienteById(0), 2);
        IngredienteBebida gelo2 = new IngredienteBebida(5, estoque.getIngredienteById(1), 2);
        IngredienteBebida vodka2 = new IngredienteBebida(6, estoque.getIngredienteById(2), 2);
        IngredienteBebida coca3 = new IngredienteBebida(7, estoque.getIngredienteById(0), 3);
        IngredienteBebida gelo3 = new IngredienteBebida(8, estoque.getIngredienteById(1), 3);
        IngredienteBebida vodka3 = new IngredienteBebida(9, estoque.getIngredienteById(2), 3);

        Bebida cuba231 = new Bebida(0, "Cuba 2/3/1", 10);
        cuba231.addIngrediente(coca2);
        cuba231.addIngrediente(gelo3);
        cuba231.addIngrediente(vodka1);
        Bebida cuba123 = new Bebida(1, "Cuba 1/2/3", 10);
        cuba123.addIngrediente(coca1);
        cuba123.addIngrediente(gelo2);
        cuba123.addIngrediente(vodka3);
        Bebida cuba321 = new Bebida(2, "Cuba 3/2/1", 10);
        cuba321.addIngrediente(coca3);
        cuba321.addIngrediente(gelo2);
        cuba321.addIngrediente(vodka1);
        Bebida cuba112 = new Bebida(3, "Cuba 1/1/2", 10);
        cuba112.addIngrediente(coca1);
        cuba112.addIngrediente(gelo1);
        cuba112.addIngrediente(vodka2);

        cardapio.add(cuba231);
        cardapio.add(cuba123);
        cardapio.add(cuba321);
        cardapio.add(cuba112);
    }

    @Override
    public ArrayList<Bebida> getCardapio() throws RemoteException {
        return cardapio;
    }

    @Override
    public Estoque getEstoque() throws RemoteException {
        return estoque;
    }

    @Override
    public Garcom getGarcomDisponivel() throws RemoteException {
        if (garconsDisponiveis.size() > 0) {
            Garcom garcom = garconsDisponiveis.get(garconsDisponiveis.size() - 1);
            garconsDisponiveis.remove(garcom);
            return garcom;
        }
        return null;
    }

    @Override
    public void addGarcomDisponivel(int id) throws RemoteException {
        for (int i = 0; i < garcons.size(); i++) {
            if (garcons.get(i).getId() == id) {
                garconsDisponiveis.add(garcons.get(i));
            }
        }
    }

    @Override
    public void acquireGarcon() throws RemoteException {
        try {
            this.garconsSemaphore.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void releaseGarcom() throws RemoteException {
        try {
            garconsSemaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCliente(Cliente cliente) throws RemoteException {
        clientes.add(cliente);
    }

    @Override
    public Cliente getClienteId(int id) throws RemoteException {
        return this.clientes.stream().filter(a
                -> a.getId() == (id)).findFirst().get();
    }

    @Override
    public Semaphore getSemaphoreBartender() throws RemoteException {
        return this.bartenderSemaphore;
    }

    @Override
    public Bartender getBartenderDisponivel() throws RemoteException {
        try {
            bartenderSemaphore.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bartender bartender = bartendersDisponiveis.get(bartendersDisponiveis.size() - 1);
        bartendersDisponiveis.remove(bartender);
        return bartender;
    }

    @Override
    public void addBartenderDisponivel(int id) throws RemoteException {
        for (int i = 0; i < bartenders.size(); i++) {
            if (bartenders.get(i).getId() == id) {
                bartendersDisponiveis.add(bartenders.get(i));
            }
        }
    }

    @Override
    public void releaseBartender() throws RemoteException {
        try {
            bartenderSemaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getIngrediente(int id) throws RemoteException {
        for (int i = 0; i < estoque.getIngredientes().size(); i++) {
            if (estoque.getIngredientes().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

}
