/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author stae
 */
public class ConexionBD implements IConexionBD{
    
    private final String SERVER = "localhost";
    private final String BASE_DATOS = "CinepolisDB";
    private final String CADENA_CONEXION = "jdbc:mysql://" + SERVER + "/" + BASE_DATOS;
    private final String USUARIO = "root";
    private final String CONTRASEÑA = "root";
    private Connection conexion;
    
    
    @Override
    public Connection crearConexion() throws SQLException {
        Connection conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
        return conexion;
    }

     @Override
    public Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            return crearConexion();
        }
        return conexion;
    }
}
