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

    public Sucursal(String nombre, String ubicacion, List<Sala> salas, Point2D.Double coordenadas) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.salas = salas;
        this.coordenadas = coordenadas;
    }

    public Sucursal(Long id, String nombre, String ubicacion, List<Sala> salas, Point2D.Double coordenadas) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.salas = salas;
        this.coordenadas = coordenadas;
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

    // Método para convertir a entidad
    public Sucursal convertirAEntidad(ResultSet resultado) throws SQLException, cinepolisException {
        Long id = resultado.getLong("idSucursal");
        String nombre = resultado.getString("nombre");
        String wktUbicacion = resultado.getString("ubicacion");

        Point2D.Double coordenadas = parsearWKT(wktUbicacion);

        List<Sala> salas = new ArrayList<>();
        // Aquí puedes agregar la lógica para obtener salas si es necesario

        return new Sucursal(id, nombre, ubicacion, salas, coordenadas);
    }

    public Point2D.Double parsearWKT(String wkt) {
        // Verificar que el formato WKT sea válido
        if (wkt != null && wkt.startsWith("POINT(") && wkt.endsWith(")"))
        {
            // Extraer las coordenadas
            String coordenadas = wkt.substring(6, wkt.length() - 1); // Remueve "POINT(" y ")"
            String[] partes = coordenadas.split(" ");

            if (partes.length == 2)
            {
                try
                {
                    // Convertir a double
                    double x = Double.parseDouble(partes[0]);
                    double y = Double.parseDouble(partes[1]);
                    return new Point2D.Double(x, y);
                } catch (NumberFormatException e)
                {
                    // Manejar el error de formato
                    e.printStackTrace();
                }
            }
        }
        return null; // O lanzar una excepción si no se puede parsear
    }

}
