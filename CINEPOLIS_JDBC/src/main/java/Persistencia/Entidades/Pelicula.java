/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author stae
 */
public class Pelicula {
    
     private int idPelicula;
    private String titulo;
    private String sinopsis;
    private String trailer;
    private double duracion;
    private String pais;
    private int idGenero;
    private int idClasificacion;
    

    public Pelicula() {
    }
    
    public Pelicula(int id) {
        this.idPelicula=id;
    }

    public Pelicula(int id, String titulo, String sinopsis, String trailer, double duracion, String pais, int idGenero, int idClasificacion) {
        this.idPelicula = id;
        this.titulo = titulo;
        this.idGenero = idGenero;
        this.sinopsis = sinopsis;
        this.trailer = trailer;
        this.duracion = duracion;
        this.pais = pais;
        this.idClasificacion = idClasificacion;
    }

    public Pelicula(String titulo, String sinopsis, String trailer, double duracion, String pais, int idGenero, int idClasificacion) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.trailer = trailer;
        this.duracion = duracion;
        this.pais = pais;
        this.idGenero = idGenero;
        this.idClasificacion = idClasificacion;
    }

    public int getId(){
        return idPelicula;
    }
    
    public void setId(int id){
        this.idPelicula=id;
    }
    
    
    public void setTitulo(String titulo) {
        this.titulo=titulo;
    }
    
    public String getTitulo() {
        return titulo;
    }


    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getClasificacion() {
        return idClasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.idClasificacion = clasificacion;
    }
    
    public int getGenero(){
        return idGenero;
    }
    
    public void setGenero(int idGenero){
        this.idGenero=idGenero;
    }
    
    public Pelicula convertirAEntidad(ResultSet resultado) throws SQLException {

        int id = resultado.getInt("idPelicula");
        String titulo = resultado.getString("titulo");
        String sinopsis = resultado.getString("sinopsis");
        String trailer = resultado.getString("trailer");
        Double duracion = resultado.getDouble("duracion");
        String pais = resultado.getString("pais");
        int idGenero = resultado.getInt("idGenero");
        int idClasificacion = resultado.getInt("idClasificacion");
        

        return new Pelicula(id, titulo, sinopsis, trailer, duracion, pais, idGenero, idClasificacion);
    }
    
}
