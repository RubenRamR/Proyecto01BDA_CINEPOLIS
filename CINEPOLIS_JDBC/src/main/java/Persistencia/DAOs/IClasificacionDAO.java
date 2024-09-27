/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Clasificacion;
import excepciones.cinepolisException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author stae
 */
public interface IClasificacionDAO {
    
    Clasificacion buscarPorId(long id) throws cinepolisException ;
    List<Clasificacion> obtenerTodos() throws cinepolisException;
    boolean insert(Clasificacion clasificacion) throws cinepolisException;
    boolean update(Clasificacion clasificacion);
    boolean delete(long id);
    public Long obtenerIdPorNombre(String nombre) throws SQLException;
    
}
