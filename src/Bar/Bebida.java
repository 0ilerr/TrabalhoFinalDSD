/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author euler
 */
public class Bebida implements Serializable{

    private int id;
    private String nome;
    private ArrayList<IngredienteBebida> ingredientes;
    private double valor;

    public Bebida(int id, String nome, double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ingredientes = new ArrayList<>();
    }

    public Bebida() {
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

    public ArrayList<IngredienteBebida> getIngredientesBebidas() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<IngredienteBebida> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void addIngrediente(IngredienteBebida ib) {
        this.ingredientes.add(ib);
    }

}
