/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import com.itextpdf.awt.geom.Point2D;
import java.util.List;

/**
 *
 * @author stae
 */
public class SucursalDTO {

    //Declaración de variables
    private Long id;
    private String nombre;
    private String ubicacion;
    List<SalaDTO> salaDTO;
    private Point2D.Double coordenadas;

    //Constructos por omisión
    public SucursalDTO() {

    }

    public SucursalDTO(Point2D.Double coordenadas, String nombre) {
        this.coordenadas = coordenadas;
        this.nombre = nombre;
    }

    public SucursalDTO(String nombre, Point2D.Double coordenadas) {
        this.nombre = nombre;
        this.coordenadas = coordenadas;
    }

    public SucursalDTO(Long id, String nombre, String ubicacion, List<SalaDTO> salaDTO) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.salaDTO = salaDTO;
    }

    //gets y sets
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

    public Point2D.Double getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Point2D.Double coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<SalaDTO> getSalaDTO() {
        return salaDTO;
    }

    public void setSalaDTO(List<SalaDTO> salas) {
        this.salaDTO = salaDTO;
    }

}
