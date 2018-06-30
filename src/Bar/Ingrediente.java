/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bar;

import java.io.Serializable;

/**
 *
 * @author euler
 */
public class Ingrediente implements Serializable{

    private int id;
    private int qtdeTotal;
    private String nome;

    public Ingrediente() {
    }

    public Ingrediente(int id, int qtdeTotal, String nome) {
        this.id = id;
        this.qtdeTotal = qtdeTotal;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtdeTotal() {
        return qtdeTotal;
    }

    public void setQtdeTotal(int qtdeTotal) {
        this.qtdeTotal = qtdeTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void reduzQtde(int qtde) {
        this.qtdeTotal -= qtde;
    }
}
