/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author stae
 */
public class GeneroDTO {
    
    //Declaración de variables
    private long id;
    private String tipo;

    //Constructor por omisión
    public GeneroDTO() {
    }

    public GeneroDTO(long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return tipo;
    }
    
}
