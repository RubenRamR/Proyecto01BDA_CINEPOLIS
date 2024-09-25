/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

import excepciones.cinepolisException;
import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stae
 */
public class Sucursal {

    //Declaración de variables
    private Long id;
    private String nombre;
    private String ubicacion;
    List<Sala> salas;
    private Point2D.Double coordenadas;

    //Constructor por omisión    
    public Sucursal() {
    }

    //Construtor que inicializa las coordenadas
    public Sucursal(Point2D.Double coordenadas) {
        this.coordenadas = coordenadas;
    }

    //Constructor que inicializa variables
    public Sucursal(Long id, String nombre, String ubicacion, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.salas = salas;
    }

    //Constructor que inicializa nombre, ubicacion, salas
    public Sucursal(String nombre, String ubicacion, List<Sala> salas) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.salas = salas;
    }

    //gets y sets
    public Point2D.Double getCoordenadas() {
        return coordenadas;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    //Método para convertir a entidad
    public Sucursal convertirAEntidad(ResultSet resultado) throws SQLException, cinepolisException {
        Long id = resultado.getLong("idSucursales");
        String nombre = resultado.getString("nombre");
        String ubicacion = resultado.getString("ubicacion");

        List<Sala> salas = new ArrayList<>();

        while (resultado.next()) {
            Long salaId = resultado.getLong("idSala");
            int numero = resultado.getInt("numero");
            Funcion funcion = new Funcion().convertirAEntidad(resultado);
            double costo = resultado.getDouble("costo");

            salas.add(new Sala(salaId, numero, funcion, costo));
        }

        return new Sucursal(id, nombre, ubicacion, salas);
    }

    
}
