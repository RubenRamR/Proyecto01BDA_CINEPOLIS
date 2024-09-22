/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

/**
 *
 * @author stae
 */
public class Ciudad {
 
    //declaración de variables
    private Long id;
    private String nombre;
    private String localizacion;

    //Constructor por omisión
    public Ciudad() {
    }

    //Constructor que incializa las variables nombre y localizacion
    public Ciudad(String nombre, String localizacion) {
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    //Constructor que inicializa variables
    public Ciudad(Long id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    //Gets y sets
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    
}
