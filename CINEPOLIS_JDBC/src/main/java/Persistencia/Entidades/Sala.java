/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

import excepciones.cinepolisException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author stae
 */
public class Sala {
    
    //Declaración de variables
    private Long id;
    private int numero;
    private Funcion funcion ;
    private double costo;

    //Constructor por omisión
    public Sala() {
    }

    //Constructor que inicializa las variables
    public Sala(Long id, int numero, Funcion funcion, double costo) {
        this.id = id;
        this.numero = numero;
        this.funcion = funcion;
        this.costo = costo;
    }

    //Constructor que inicializa numero y funcion
    public Sala(int numero, Funcion funcion) {
        this.numero = numero;
        this.funcion = funcion;
    }

    //gets y sets
    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

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

    public Funcion getFunciones() {
        return funcion;
    }

    public void setFunciones(Funcion funcion) {
        this.funcion = funcion;
    }
    
    //Método a converit a entidad
    public Sala convertirAEntidad(ResultSet resultado) throws SQLException, cinepolisException {

        Long id = resultado.getLong("idSalas");
        int numero = resultado.getInt("numero");
        double costo = resultado.getDouble("costo");
        Funcion funcion = new Funcion().convertirAEntidad(resultado);
        

        return new Sala(id, numero, funcion, costo);
    }
    

    
}
