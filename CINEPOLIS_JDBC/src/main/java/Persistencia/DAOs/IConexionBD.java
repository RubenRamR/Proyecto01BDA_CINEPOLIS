/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia.DAOs;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author stae
 */
public interface IConexionBD {

    Connection crearConexion() throws SQLException;

    public Connection obtenerConexion() throws SQLException;

}
