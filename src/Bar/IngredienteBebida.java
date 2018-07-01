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
public class IngredienteBebida implements Serializable{

    private int id;
    private Ingrediente ingrediente;
    private int qtde;

    public int getId() {
        return id;
    }

    public IngredienteBebida(int id, Ingrediente ingrediente, int qtde) {
        this.id = id;
        this.ingrediente = ingrediente;
        this.qtde = qtde;
    }

    public IngredienteBebida() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

}
