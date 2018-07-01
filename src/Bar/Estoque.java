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
public class Estoque implements Serializable {

    private static Estoque instance;

    public static Object getEstoqueInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private ArrayList<Ingrediente> ingredientes;

    public static synchronized Estoque getInstance() {
        if (instance == null) {
            instance = new Estoque();
        }

        return instance;
    }

    private Estoque() {
        Ingrediente i2 = new Ingrediente(1, 30, "Gelo");
        Ingrediente i3 = new Ingrediente(2, 30, "Vodka");
        Ingrediente i4 = new Ingrediente(3, 30, "Limão");
        ingredientes = new ArrayList<>();
        ingredientes.add(i2);
        ingredientes.add(i3);
        ingredientes.add(i4);
    }

    public Ingrediente getIngredienteById(int id) {
        return ingredientes.get(id);
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
//
//    public void addIngrediente(Ingrediente i){
//        this.ingredientes.add(i);
//    }

}
