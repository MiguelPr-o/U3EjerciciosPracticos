/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolBinario;

/**
 *
 * @author Miguel Angel Hernandez Godinez
 */
public class PruebaArbol {
    public static void main(String[] args) {
        // 1. Crear una instancia de la clase gestora del árbol
        ArbolBinario arbol = new ArbolBinario();

        System.out.println("Insertando valores: 50, 30, 70, 20, 40");
        // 2. Insertar valores usando el método público
        arbol.insertar(50); // Raíz
        arbol.insertar(30); // Izquierda de 50
        arbol.insertar(70); // Derecha de 50
        arbol.insertar(20); // Izquierda de 30
        arbol.insertar(40); // Derecha de 30

        // 3. Ejecutar el recorrido para verificar el orden
        // Resultado esperado (ordenado): 20 30 40 50 70
        arbol.recorrerInorden();
     }

}

