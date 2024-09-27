/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Funcion;
import excepciones.cinepolisException;
import java.util.List;

/**
 *
 * @author stae
 */
public interface IFuncionDAO {
    
    public Funcion insertarFuncion(Funcion funcion) throws cinepolisException;
    
    public Funcion editarFuncion(Funcion funcion) throws cinepolisException;
    
    public Funcion eliminarFuncionPorId(int idFuncion) throws cinepolisException;
    
    public Funcion buscarPorId(int idFuncion) throws cinepolisException;
    
    public List<Funcion> buscarFuncionesTabla() throws cinepolisException;
    
}
