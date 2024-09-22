/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

/**
 *
 * @author stae
 */
public class Genero {
    
    //declaración de variables
    private long id;
    private String nombre;
    
    //Constructor por omisión
    public Genero(){
    
    }

    
    //Constructor que inicializa las variables
    public Genero(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    //gets y sets
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
