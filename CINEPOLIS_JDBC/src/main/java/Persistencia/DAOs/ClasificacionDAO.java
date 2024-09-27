/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Clasificacion;
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
public class ClasificacionDAO implements IClasificacionDAO {
    
    private IConexionBD conexionBD;
    
    
    public ClasificacionDAO(){}
    
    // Constructor
    public ClasificacionDAO(IConexionBD conexion) {
        this.conexionBD = conexion;
    }



    @Override
    public boolean insert(Clasificacion clasificacion) {
        // Implementación para insertar una clasificación
        return false;
    }

    @Override
    public boolean update(Clasificacion clasificacion) {
        // Implementación para actualizar una clasificación
        return false;
    }

    @Override
    public boolean delete(long id) {
        // Implementación para eliminar una clasificación por su ID
        return false;
    }

    @Override
    public Clasificacion buscarPorId(long id) throws cinepolisException{
        Connection conexion = null;
        Clasificacion clasificacion = null;
        String query = "SELECT * FROM clasificacion WHERE idClasificacion = ?";

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    clasificacion = new Clasificacion();
                    clasificacion.setId(resultSet.getLong("idClasificacion"));
                    clasificacion.setNombre(resultSet.getString("tipo"));
                }
            }

            conexion.commit();
        } catch (SQLException e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        return clasificacion;
    }

    @Override
    public List<Clasificacion> obtenerTodos() {
        Connection conexion = null;
        List<Clasificacion> clasificaciones = new ArrayList<>();
        String query = "SELECT * FROM clasificacion";

        try {
            conexion = this.conexionBD.crearConexion();

            try (
                PreparedStatement consulta = conexion.prepareStatement(query);
                ResultSet resultado = consulta.executeQuery();
            ) {
                while (resultado.next()) {
                    Clasificacion clasificacion = new Clasificacion();
                    clasificacion.setId(resultado.getLong("idClasificacion"));
                    clasificacion.setNombre(resultado.getString("tipo"));
                    clasificaciones.add(clasificacion);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener todas las clasificaciones: " + ex.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Error al cerrar la conexión: " + closeEx.getMessage());
            }
        }

        return clasificaciones;
    }

    @Override
    public Long obtenerIdPorNombre(String nombre) throws SQLException {
        Connection conexion = null;
        Long idClasificacion = null;
        String query = "SELECT idClasificacion FROM clasificacion WHERE tipo = ?";

        try {
            conexion = this.conexionBD.crearConexion();

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, nombre);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idClasificacion = resultSet.getLong("idClasificacion");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener el ID de clasificación por nombre: " + ex.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Error al cerrar la conexión: " + closeEx.getMessage());
            }
        }

        if (idClasificacion == null) {
            throw new SQLException("No se encontró una clasificación con el nombre especificado");
        }

        return idClasificacion;
    }
    
}
