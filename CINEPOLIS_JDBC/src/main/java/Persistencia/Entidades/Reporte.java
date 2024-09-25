/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author stae
 */
public class Reporte {
 
    //Declaración de variables
    private Long id;
    private String descripcion;
    private String sucursal;
    private String cantFunciones;
    private Date Fecha;
    private String totalGFecha;
    private String totalGanancias;
    private String tipo;
    private List<Genero>  generos = new ArrayList<>();
    private List<Pelicula>  peliculas = new ArrayList<>();
    private List<Ciudad>  ciudades = new ArrayList<>();
    private int peliculaId;
    private int generoId;
    
    //Constructor por omisión
    public Reporte() {
    }

    //Constructor que inicializa las variables
    public Reporte(Long id, String descripcion, String sucursal, String cantFunciones, Date Fecha, String totalGFecha, String totalGanancias, String tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.sucursal = sucursal;
        this.cantFunciones = cantFunciones;
        this.Fecha = Fecha;
        this.tipo = tipo;
        this.totalGFecha = totalGFecha;
        this.totalGanancias = totalGanancias;
    }

    //Constructor que incializa variables
    public Reporte(String descripcion, String sucursal, String cantFunciones, Date Fecha, String totalGFecha, String totalGanancias, String tipo) {
        this.descripcion = descripcion;
        this.sucursal = sucursal;
        this.cantFunciones = cantFunciones;
        this.Fecha = Fecha;
        this.tipo = tipo;
        this.totalGFecha = totalGFecha;
        this.totalGanancias = totalGanancias;
    }
    
    //gets y sets
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public void setGeneroId(int generoId) {
        this.generoId = generoId;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCantFunciones() {
        return cantFunciones;
    }

    public void setCantFunciones(String cantFunciones) {
        this.cantFunciones = cantFunciones;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTotalGFecha() {
        return totalGFecha;
    }

    public void setTotalGFecha(String totalGFecha) {
        this.totalGFecha = totalGFecha;
    }

    public String getTotalGanancias() {
        return totalGanancias;
    }

    public void setTotalGanancias(String totalGanancias) {
        this.totalGanancias = totalGanancias;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int size() {
        return 0;
    }
    
}
