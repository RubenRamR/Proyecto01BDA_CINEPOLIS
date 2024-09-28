/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package PruebasMetodos;

import Negocio.DTOs.ClienteDTO;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.ConexionBD;
import Persistencia.DAOs.IClienteDAO;
import Persistencia.DAOs.IConexionBD;
import Persistencia.DAOs.IPeliculaDAO;
import Persistencia.DAOs.PeliculaDAO;
import Persistencia.Entidades.Ciudad;
import Persistencia.Entidades.Cliente;
import excepciones.cinepolisException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rramirez
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws cinepolisException {
        IConexionBD conexionBD = new ConexionBD();
        IPeliculaDAO peliculaDAO = new PeliculaDAO(conexionBD);
        
        System.out.println(        peliculaDAO.obtenerTodasLasPeliculas()
);        
    }

}


