/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Bar.Bebida;
import Bar.Garcom;
import Server.InterfaceRemota;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author euler
 */
public class Cliente implements Runnable, Serializable {

    private int id;
    private double granaTotal;
    private String nome;
    private Bebida bebidaFavorita;
    private InterfaceRemota repositorio;
    private Thread thread;
    private int contadorPedidosNegados;

    public Cliente(int id, double granaTotal, String nome, Bebida bebida, InterfaceRemota repositorio) {
        this.id = id;
        this.granaTotal = granaTotal;
        this.nome = nome;
        this.bebidaFavorita = bebida;
        this.repositorio = repositorio;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGranaTotal() {
        return granaTotal;
    }

    public void setGranaTotal(double granaTotal) {
        this.granaTotal = granaTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        while (getGranaTotal() >= bebidaFavorita.getValor()) {
            queroBeber(bebidaFavorita, 1);
        }
        System.out.println("Cliente " + getNome() + " sem grana suficiente");
    }

    public void queroBeber(Bebida bebida, int quantidade) {
        try {
            synchronized (this) {
                thread = Thread.currentThread();
                if (repositorio.getSemaphoreGarcom().availablePermits() > 0) {
                    repositorio.acquireGarcon();
                    Garcom oGarcom = repositorio.getGarcomDisponivel();
                    if (oGarcom != null) {
                        System.out.println(thread.getName() + " - Quero Beber " + bebida.getNome());
                        oGarcom.setCliente(this);
                        oGarcom.setPedido(true);

                        oGarcom.pegarBebida(bebida, quantidade);
                        oGarcom.setClientePedido(this.getId());
                    }
                } else {
                    System.out.println(thread.getName() + " está em espera!");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bebe(Bebida bebida, int quantidade) throws InterruptedException, RemoteException {
        notifyAll();

        synchronized (this) {
            if (bebida == null) {
                System.out.println("Seu pedido foi negado!");
                contadorPedidosNegados++;
                if (contadorPedidosNegados == 5) {
                    thread.setPriority(thread.getPriority() + 2);
                    contadorPedidosNegados = 0;
                }
            } else {
                System.out.println("Cliente " + thread.getName() + " - Bebendo a Bebida " + bebida.getNome());
                granaTotal -= bebidaFavorita.getValor();
            }
        }
        Thread.sleep(1000);
    }
}
