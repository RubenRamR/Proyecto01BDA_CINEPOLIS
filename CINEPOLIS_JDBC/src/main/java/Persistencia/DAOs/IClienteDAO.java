/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.ClienteDTO;
import Persistencia.Entidades.Cliente;
import excepciones.cinepolisException;
import java.util.List;

/**
 *
 * @author rramirez
 */
public interface IClienteDAO {
    
    
    public Cliente insertarCliente(Cliente cliente) throws cinepolisException;
    
    public Cliente login(Cliente cliente) throws cinepolisException;
    
    public Cliente editarCliente(Cliente cliente) throws cinepolisException;
    
    public Cliente eliminarClientePorID(long idCliente) throws cinepolisException;
    
    public List<Cliente> buscarClientesTabla() throws cinepolisException;
    
    public List<ClienteDTO> obtenerTodosLosClientes() throws cinepolisException;
     
    public ClienteDTO obtenerClientePorID(long id) throws cinepolisException;
    
    
}
