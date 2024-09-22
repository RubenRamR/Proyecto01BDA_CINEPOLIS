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
 * @author stae
 */
public class Boleto {
 
    private Long id;
    private double costo;
    private boolean estado;
    private Date fechaCompra;
    private Funcion funcion;
    private Cliente cliente;

    public Boleto() {
    }

    public Boleto(Long id, double costo, boolean estado, Date fechaCompra, Funcion funcion, Cliente cliente) {
        this.id = id;
        this.costo = costo;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.funcion = funcion;
        this.cliente = cliente;
    }

    
    public Boleto(double costo, boolean estado, Date fechaCompra,  Funcion funcion, Cliente cliente) {
        this.costo = costo;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.funcion = funcion;
        this.cliente = cliente;
    }
    
    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Boleto convertirAEntidad(ResultSet resultado) throws SQLException {

        Long id = resultado.getLong("idBoleto");
        Double costo = resultado.getDouble("costo");
        boolean estado = resultado.getBoolean("estado");
        Date fechaCompra = resultado.getDate("fechaCompra");
        Funcion funcion = new Funcion().convertirAEntidad(resultado);
        Cliente cliente = new Cliente().convertirAEntidad(resultado);
        

        return new Boleto(id, costo, estado, fechaCompra, funcion, cliente);
    }
    
}
