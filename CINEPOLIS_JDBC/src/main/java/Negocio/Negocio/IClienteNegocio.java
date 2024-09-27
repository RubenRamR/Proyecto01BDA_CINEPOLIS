/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Negocio;

import Negocio.DTOs.ClasificacionDTO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.FuncionDTO;
import Negocio.DTOs.GeneroDTO;
import Negocio.DTOs.PeliculaDTO;
import Persistencia.Entidades.Cliente;
import Persistencia.Entidades.Pelicula;
import excepciones.cinepolisException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author rramirez
 */
public interface IClienteNegocio {
    
    public ClienteDTO registro(ClienteDTO cliente);
    
    public ClienteDTO login(ClienteDTO cliente);
    
    public Cliente convertirAEntidad(ClienteDTO cliente) throws SQLException ;
    
    public ClienteDTO convertirAEntidad(Cliente cliente) throws SQLException;
    
    public List<ClienteDTO> buscarClientesTabla() throws cinepolisException;
    
    public ClienteDTO obtenerClientePorID(long id) throws cinepolisException;
    
    public List<ClienteDTO> obtenerTodosLosClientes() throws cinepolisException;
    
    public ClienteDTO editarCliente(ClienteDTO cliente) throws cinepolisException;
    
    public ClienteDTO eliminarCliente(long idCliente) throws cinepolisException ;
     
    public void agregarCliente(ClienteDTO cliente) throws SQLException, cinepolisException;
    
    public List<ClasificacionDTO> obtenerTodasLasClasificaciones();
    
    public List<GeneroDTO> obtenerTodosLosGeneros();
    
    public PeliculaDTO obtenerPeliculaPorID(long id) throws cinepolisException;
    
    public PeliculaDTO eliminarPelicula(long idPelicula) throws cinepolisException;
    
    public PeliculaDTO convertirAEntidad(Pelicula pelicula) throws SQLException;
    
    public List<PeliculaDTO> obtenerTodasLasPeliculasTablaDTO() throws cinepolisException;
    
    public List<PeliculaDTO> buscarPeliculasTabla() throws cinepolisException;
    
   public PeliculaDTO editarPelicula(PeliculaDTO pelicula) throws cinepolisException;
   
   public FuncionDTO obtenerFuncionPorId(long id) throws cinepolisException, SQLException;
   
   public List<FuncionDTO> buscarFuncionesTabla() throws cinepolisException;
    
}
