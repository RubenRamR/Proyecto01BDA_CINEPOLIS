/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.Entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author rramirez
 */
public class Cliente {

    //Declaración de variables
    private long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasena;
    private String ubicacion;
    private Date fechaNacimiento;
    private Ciudad ciudad;

    //Constructor por omisión
    public Cliente() {

    }

    //Constructor que inicializa las variables
    public Cliente(long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasena, String ubicacion, Date fechaNacimiento, Ciudad ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasena = contrasena;
        this.ubicacion = ubicacion;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
    }

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasena, String ubicacion, Date fechaNacimiento, Ciudad ciudad) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasena = contrasena;
        this.ubicacion = ubicacion;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public static Cliente convertirAEntidad(ResultSet resultado) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultado.getLong("idCliente"));
        cliente.setNombre(resultado.getString("nombre"));
        cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
        cliente.setCorreo(resultado.getString("correo"));

        String contrasena = resultado.getString("contrasena");
        System.out.println("Contraseña recuperada: " + contrasena);
        cliente.setContrasena(contrasena);

        cliente.setUbicacion(resultado.getString("ubicacion"));
        cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));

        Ciudad ciudad = new Ciudad();
        ciudad.setId(resultado.getLong("ciudadId"));
        ciudad.setNombre(resultado.getString("ciudadNombre"));
        ciudad.setLocalizacion(resultado.getString("localizacion"));
        cliente.setCiudad(ciudad);

        return cliente;
    }

}
