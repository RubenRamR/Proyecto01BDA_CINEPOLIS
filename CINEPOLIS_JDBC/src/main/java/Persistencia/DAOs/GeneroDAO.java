/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Genero;
import excepciones.cinepolisException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stae
 */
public class GeneroDAO implements IGeneroDAO{
    
    private IConexionBD conexionBD;

     public GeneroDAO(){}
    
    public GeneroDAO(IConexionBD conexion) {
        this.conexionBD = conexion;
    }

    @Override
    public Genero buscarPorId(long id) throws cinepolisException {
    Connection conexion = null;
    Genero genero = null;
    String query = "SELECT * FROM genero WHERE idGenero = ?";

    try {
        conexion = conexionBD.crearConexion();
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    genero = new Genero();
                    genero.setId(resultSet.getLong("idGenero"));
                    genero.setNombre(resultSet.getString("tipo"));
                }
            }
        }
    } catch (SQLException e) {
        throw new cinepolisException("Error al buscar el género por ID: " + e.getMessage());
    } finally {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                throw new cinepolisException("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    return genero;
    }

    @Override
    public List<Genero> obtenerTodos() throws cinepolisException {
        Connection conexion = null;
        List<Genero> generos = new ArrayList<>();
        String query = "SELECT * FROM genero";

        try {
            conexion = conexionBD.crearConexion();
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Genero genero = new Genero();
                        genero.setId(resultSet.getLong("idGenero"));
                        genero.setNombre(resultSet.getString("tipo"));
                        generos.add(genero);
                    }
                }
            }
        } catch (SQLException e) {
            throw new cinepolisException("Error al obtener todos los géneros: " + e.getMessage());
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    throw new cinepolisException("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }

        return generos;
    }

    @Override
    public boolean insert(Genero genero) throws cinepolisException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Genero genero) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Long obtenerIdPorNombre(String nombre) throws cinepolisException  {
        Connection conexion = null;
        String query = "SELECT idGenero FROM genero WHERE tipo = ?";
        try {
            conexion = conexionBD.crearConexion();
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, nombre);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("idGenero");
                    }
                }
            }
        } catch (SQLException e) {
            throw new cinepolisException("Error al obtener el ID del género por nombre: " + e.getMessage());
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    throw new cinepolisException("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
        System.out.println("Nombre recibido: " + nombre);
        throw new cinepolisException("No se encontró un género con el nombre especificado");
    }
    
    
}
