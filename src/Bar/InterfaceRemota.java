/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bar;

import Bar.Bartender;
import Bar.Bebida;
import Bar.Estoque;
import Bar.Garcom;
import Cliente.Cliente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author euler
 */
public interface InterfaceRemota extends Remote {

    void addCliente(Cliente cliente) throws RemoteException;

    int garconsPermitidos() throws RemoteException;

    int totalClientes() throws RemoteException;

    void setGarconsDisponiveis(int garconsPermitidos) throws RemoteException;

    Semaphore getSemaphoreGarcom() throws RemoteException;

    Semaphore getSemaphoreBartender() throws RemoteException;

    Cliente getClienteId(int id) throws RemoteException;

    void addGarcom(Garcom garcom) throws RemoteException;

    Garcom getGarcomId(int id) throws RemoteException;

    void addBartender(Bartender bartender) throws RemoteException;

    Bartender getBartenderId(int id) throws RemoteException;

    ArrayList<Bebida> getCardapio() throws RemoteException;

    Garcom getGarcomDisponivel() throws RemoteException;

    void reduzQtdeEstoqueIngrediente(int iPosicaoIngrediente, int qntde, int iQtdeIngrediente) throws RemoteException;
    
    int getQntdeTotalIngrediente(int iPosicaoIngrediente) throws RemoteException; 
    
    Estoque getEstoqueInstance() throws RemoteException;

    public void acquireGarcon() throws RemoteException;

    void addGarcomDisponivel(int id) throws RemoteException;

    Bartender getBartenderDisponivel() throws RemoteException;

    void addBartenderDisponivel(int id) throws RemoteException;

    int getIngrediente(int id) throws RemoteException;

    void releaseGarcom() throws RemoteException;

    public void releaseBartender() throws RemoteException;

    void teste() throws RemoteException;
}
