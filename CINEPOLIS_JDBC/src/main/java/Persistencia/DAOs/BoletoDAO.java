/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Boleto;
import excepciones.cinepolisException;
import java.awt.Menu;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


/**
 *
 * @author stae
 */
public class BoletoDAO implements IBoletoDAO{
    
    private IConexionBD conexionBD;
    private Menu menu;
    
    public BoletoDAO(){
        this.conexionBD = new ConexionBD();
    }
    
    public BoletoDAO(IConexionBD conexionBD){
        this.conexionBD=conexionBD;
    }

    //insertar Boleto
    @Override
    public Boleto insertarBoletoComprado(Boleto boleto) throws cinepolisException{
        Connection conexion = null;
        try{
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);
             LocalDate fechaActual = LocalDate.now();
        // Convertir LocalDate a java.sql.Date
            java.sql.Date fechaSql = java.sql.Date.valueOf(fechaActual);
            String codigoSQL = "INSERT INTO boleto (costo, estado, fechaCompra, idFuncion, idCliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertCommand = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);
            insertCommand.setDouble(1, boleto.getCosto());
            insertCommand.setBoolean(2, boleto.isEstado());
            insertCommand.setDate(3, fechaSql);
            insertCommand.setLong(4, boleto.getFuncion().getId());
            insertCommand.setLong(5, boleto.getCliente().getId());
            
            insertCommand.executeUpdate();

            ResultSet generatedKeys = insertCommand.getGeneratedKeys();
            if (generatedKeys.next()) {
                boleto.setId(generatedKeys.getLong(1));
            }
  
            conexion.commit();
            } catch (SQLException ex) {
                
                if (conexion != null) {
                    try {
                        conexion.rollback();
                    } catch (SQLException rollbackEx) {
                        System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                    }
                }
                
                System.out.println(ex.getMessage());
                throw new cinepolisException("Hubo un error al registrar el boleto",ex);
                
            }finally {
        
                if (conexion != null) {
                    try {
                        conexion.setAutoCommit(true);
                        conexion.close();
                    } catch (SQLException e) {
                        System.out.println("Error al cerrar la conexión: " + e.getMessage());
                        }
                    }
                 }
            return boleto;
    }
    
     //EDITAR boleto
    @Override
    public Boleto editarBoleto(Boleto boleto) throws cinepolisException {
        String sql = "UPDATE boletos SET costo = ?, estado = ?, fechaCompra = ?, idFuncion = ?, idCliente = ? WHERE idBoleto = ?";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);
            
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setDouble(1, boleto.getCosto());
            preparedStatement.setBoolean(2, boleto.isEstado());
            preparedStatement.setDate(3, (Date) boleto.getFechaCompra());
            preparedStatement.setLong(4, boleto.getFuncion().getId());
            preparedStatement.setLong(5, boleto.getCliente().getId());

            System.out.println("Ejecutando SQL: " + sql);
            System.out.println("Con valores: ");
            System.out.println("costo = " + boleto.getCosto());
            System.out.println("estado = " + boleto.isEstado());
            System.out.println("fechaCompra = " + boleto.getFechaCompra());
            System.out.println("idFuncion = " + boleto.getFuncion().getId());
            System.out.println("idCliente = " + boleto.getCliente().getId());
 
            
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas == 0) {
                conexion.rollback();
                throw new cinepolisException("No se encontró el boleto con el ID especificado");
            }

            conexion.commit();
            return boleto;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Error al actualizar el boleto: " + ex.getMessage(), ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
    
    //ELIMINAR Boleto
    @Override
    public Boleto eliminarBoletoPorID(Long idBoleto) throws cinepolisException {
        String sqlSelect = "SELECT * FROM boletos WHERE idBoleto = ?";
        String sqlDelete = "DELETE FROM peliculas WHERE idBoleto = ?";
        Connection conexion = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        Boleto boleto = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            selectStatement = conexion.prepareStatement(sqlSelect);
            selectStatement.setLong(1, idBoleto);
            ResultSet resultado = selectStatement.executeQuery();

            if (resultado.next()) {
                boleto = new Boleto();
                boleto.setId(resultado.getLong("idBoleto"));
                boleto.setCosto(resultado.getDouble("costo"));
                boleto.setEstado((boolean) resultado.getBoolean("estado"));
                boleto.setFechaCompra(resultado.getDate("fechaCompra"));
                //boleto.setFuncion(resultado.getString("idFuncion"));
                //boleto.setCliente(resultado.getInt("idCliente"));
      
            } else {
                conexion.rollback();
                throw new cinepolisException("No se encontró boleto con el ID especificado");
            }

            deleteStatement = conexion.prepareStatement(sqlDelete);
            deleteStatement.setLong(1, idBoleto);
            deleteStatement.executeUpdate();

            conexion.commit();
            return boleto;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al eliminar el boleto: " + ex.getMessage(), ex);
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el selectStatement: " + e.getMessage());
                }
            }
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el deleteStatement: " + e.getMessage());
                }
            }
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
    
}
