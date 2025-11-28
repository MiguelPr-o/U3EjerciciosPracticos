/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolBinario;

/**
 *
 * @author Miguel Angel Hernandez Godinez
 */
public class NodoArbol {
    private int dato;

    public NodoArbol hijoIzquierdo;
    public NodoArbol hijoDerecho;
    
    public NodoArbol(int valor) {
        this.dato = valor;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }
    
    public int getDato(){
        return dato;
    }
    
    public void setDato(){
        this.dato = dato;
    }
    
        
    
    
}

