/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

/**
 *
 * @author stae
 */
public class SalaDTO {
    
    //Declaración de variables
    private Long id;
    private int numero;
    private FuncionDTO funcionDTO ;
     
    //Constructor por omisión
    public SalaDTO() {
    }

    public SalaDTO(Long id, int numero, FuncionDTO funcionDTO) {
        this.id = id;
        this.numero = numero;
        this.funcionDTO = funcionDTO;
    }

    //gets y sets
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public FuncionDTO getFuncionDTO() {
        return funcionDTO;
    }

    public void setFuncionDTO(FuncionDTO funcionDTO) {
        this.funcionDTO = funcionDTO;
    }
    
}
