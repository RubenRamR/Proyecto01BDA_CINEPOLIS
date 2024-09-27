/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Genero;
import excepciones.cinepolisException;
import java.util.List;

/**
 *
 * @author stae
 */
public interface IGeneroDAO {
    
    Genero buscarPorId(long id) throws cinepolisException ;
    List<Genero> obtenerTodos() throws cinepolisException;
    boolean insert(Genero genero) throws cinepolisException;
    boolean update(Genero genero);
    boolean delete(long id);
    public Long obtenerIdPorNombre(String nombre) throws cinepolisException;
    
}
