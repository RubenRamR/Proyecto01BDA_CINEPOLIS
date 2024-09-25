/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import Persistencia.Entidades.Cliente;
import Persistencia.Entidades.Funcion;
import java.util.Date;

/**
 *
 * @author stae
 */
public class BoletoDTO {
    
   private Long id;
    private double costo;
    private boolean estado;
    private Date fechaCompra;
    private Funcion funcion;
    private Cliente cliente;

    
    //Constructor por omisión
    public BoletoDTO() {
    }

    public BoletoDTO(Long id, double costo, boolean estado, Date fechaCompra, Funcion funcion, Cliente cliente) {
        this.id = id;
        this.costo = costo;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.funcion = funcion;
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
    
    
    
}
