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
import java.util.Random;

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
    private boolean vip;
    private boolean persistente;
    private boolean bebado;
    private int contadorBedado;

    public Cliente(int id, InterfaceRemota repositorio) throws RemoteException{
        Random random = new Random();
        this.id = id;
        this.repositorio = repositorio;
        this.nome = "Cliente " + id;
        this.bebado = false;
        this.granaTotal = (random.nextInt(2) + 1) * 100;
        this.bebidaFavorita = repositorio.getCardapio().get(random.nextInt(4));
        vip = random.nextInt(5) >= 3;
        persistente = random.nextInt(10) >= 5;
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
        thread = Thread.currentThread();
        if (vip) {
            thread.setPriority(8);
        }
        while (getGranaTotal() >= bebidaFavorita.getValor()) {
            Random random = new Random();
            if(contadorBedado == 2){
                bebado = true;
            }
            int qtde = random.nextInt(3) + 1;
            if(contadorPedidosNegados == 10){
                break;
            }
            if (persistente) {
                queroBeber(bebidaFavorita, qtde);
            } else {
                queroBeber(bebidaFavorita, qtde);
                int bebida = random.nextInt(4);
                try {
                    bebidaFavorita = repositorio.getCardapio().get(bebida);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }

        }
        if(contadorPedidosNegados == 10){
            System.out.println("Este bar não tem nada que eu goste! Estou saindo!");
        } else {
            System.out.println("Cliente " + getNome() + " sem grana suficiente");
        }
    }

    public void queroBeber(Bebida bebida, int quantidade) {
        try {
            synchronized (this) {
                if (repositorio.getSemaphoreGarcom().availablePermits() > 0) {
                    repositorio.acquireGarcon();
                    Garcom oGarcom = repositorio.getGarcomDisponivel();
                    if (oGarcom != null) {
                        System.out.println(getNome() + " - Quero Beber " + quantidade + " " + bebida.getNome());
                        oGarcom.setCliente(this);
                        oGarcom.setPedido(true);
                        if(bebado){
                            int prioridadeAtual = thread.getPriority();
                            int alteracaoDoGarcom = oGarcom.alteraClienteBebado();
                            int prioridadeAlterada = alteracaoDoGarcom + prioridadeAtual;
                            if(prioridadeAlterada <= 0){
                                prioridadeAlterada = 1;
                            }
                            if(prioridadeAlterada > 10){
                                prioridadeAlterada = 10;
                            }
                            thread.setPriority(prioridadeAlterada);
                            System.out.println("O Garcom percebeu que o cliente está bebado e "
                                    + "decidiu alterar sua prioridade para " + prioridadeAlterada + " (Escala de 1 a 10)");
                        }
                        oGarcom.pegarBebida(bebida, quantidade);
                        oGarcom.setClientePedido(this.getId());
                    }
                } else {
                    System.out.println(getNome() + " está em espera!");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bebe(Bebida bebida, int quantidade) throws InterruptedException, RemoteException {

        synchronized (this) {
            if (bebida == null) {
                contadorPedidosNegados++;
                System.out.println("Seu pedido foi negado!");
                System.out.println("Esta é a " + contadorPedidosNegados + "ª vez que isto acontece");
                if (contadorPedidosNegados%5 == 0) {
                    if(thread.getPriority() < 8){
                        thread.setPriority(thread.getPriority() + 2);
                    } else {
                        thread.setPriority(10);
                    }
                }
                Random random = new Random();
                int i = random.nextInt(4);
                try {
                    bebidaFavorita = repositorio.getCardapio().get(i);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                granaTotal -= bebidaFavorita.getValor();
                System.out.println("Cliente " + getNome() + " pagou " + bebida.getValor() + " e está bebendo um(a) " 
                        + bebida.getNome() + " e agora está com R$ " + granaTotal + " reais");
                contadorBedado++;
            }
        }
        Thread.sleep(1000);
    }
}