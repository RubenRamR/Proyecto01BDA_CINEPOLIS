/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.FuncionDTO;
import Negocio.DTOs.SalaDTO;
import Persistencia.Entidades.Funcion;
import Persistencia.Entidades.Pelicula;
import excepciones.cinepolisException;
import java.awt.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stae
 */
public class FuncionDAO implements IFuncionDAO{
    
    private IConexionBD conexionBD;
    private Menu menu;
    
    public FuncionDAO(IConexionBD conexionBD){
         this.conexionBD = conexionBD;
    }
    
    //INSERTAR FUNCION
    @Override
    public Funcion insertarFuncion(Funcion funcion) throws cinepolisException {
        String sql = "INSERT INTO funciones (fecha, horaInicio, idpeliculas) VALUES (?, ?, ?)";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        System.out.println("funcionDAO "+funcion.getPeliculas().getId());
        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            preparedStatement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new java.sql.Date(funcion.getFecha().getTime()));
            preparedStatement.setDouble(2, funcion.getHoraInicio());
            preparedStatement.setInt(3, funcion.getPeliculas().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                funcion.setId(generatedKeys.getInt(1));
            }

            conexion.commit();
            return funcion;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al registrar la función: " + ex.getMessage(), ex);
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

    //EDITAR FUNCION
    @Override
    public Funcion editarFuncion(Funcion funcion) throws cinepolisException {
        String sql = "UPDATE funciones SET fecha = ?, horaInicio = ?, idpeliculas = ? WHERE idFuncion = ?";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(funcion.getFecha().getTime()));
            preparedStatement.setDouble(2, funcion.getHoraInicio());
            preparedStatement.setInt(3, funcion.getPeliculas().getId());
            preparedStatement.setInt(4, funcion.getId());

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas == 0) {
                conexion.rollback();
                throw new cinepolisException("No se encontró la función con el ID especificado");
            }

            conexion.commit();
            return funcion;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al actualizar la función: " + ex.getMessage(), ex);
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

    //ELIMINAR FUNCION
    @Override
    public Funcion eliminarFuncionPorId(int idFuncion) throws cinepolisException {
        String sqlSelect = "SELECT * FROM funciones WHERE idFuncion = ?";
        String sqlDelete = "DELETE FROM funciones WHERE idFuncion = ?";
        Connection conexion = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        Funcion funcion = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            selectStatement = conexion.prepareStatement(sqlSelect);
            selectStatement.setInt(1, idFuncion);
            ResultSet resultado = selectStatement.executeQuery();

            if (resultado.next()) {
                funcion = new Funcion();
                funcion.setId(resultado.getInt("idFuncion"));
                funcion.setFecha(resultado.getDate("fecha"));
                funcion.setHoraInicio(resultado.getDouble("horaInicio"));
                funcion.setPeliculas(new Pelicula(resultado.getInt("idpeliculas")));
            } else {
                conexion.rollback();
                throw new cinepolisException("No se encontró la función con el ID especificado");
            }
            
            deleteStatement = conexion.prepareStatement(sqlDelete);
            deleteStatement.setInt(1, idFuncion);
            deleteStatement.executeUpdate();

            conexion.commit(); 
            return funcion;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al eliminar la función: " + ex.getMessage(), ex);
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

    @Override
    public Funcion buscarPorId(int idFuncion) throws cinepolisException {
        String query = "SELECT * FROM funciones WHERE idFuncion = ?";
        Funcion funcion = null;
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexion = this.conexionBD.crearConexion();

            conexion.setAutoCommit(false);

            stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idFuncion);

            rs = stmt.executeQuery();

            if (rs.next()) {
                funcion = new Funcion();
                PeliculaDAO pelicula=new PeliculaDAO(this.conexionBD);
                funcion.setId(rs.getInt("idFuncion"));
                funcion.setFecha(rs.getDate("fecha"));
                funcion.setHoraInicio(rs.getDouble("horaInicio"));
                funcion.setPeliculas(pelicula.obtenerPeliculaPorIda(rs.getInt("idpeliculas")));
            } else {
                throw new cinepolisException("No se encontró una función con el ID especificado");
            }
            conexion.commit();

        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    throw new cinepolisException("Error al realizar rollback de la transacción", ex);
                }
            }
            throw new cinepolisException("Error al buscar la función por ID", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return funcion;
        }

    @Override
    public List<Funcion> buscarFuncionesTabla() throws cinepolisException {
           List<Funcion> funcionLista = new ArrayList<>();
            Connection conexion = null;
            PreparedStatement comandoSQL = null;
            ResultSet resultado = null;

            try {
                conexion = this.conexionBD.crearConexion();
                String codigoSQL = "SELECT f.idFuncion, f.fecha, f.horaInicio, p.idPelicula, p.titulo, p.sinopsis, p.trailer, p.duracion, p.pais, p.idGenero, p.idClasificacion\n" +
                                    "FROM funciones f\n" +
                                    "INNER JOIN peliculas p ON f.idpeliculas = p.idPelicula";
                comandoSQL = conexion.prepareStatement(codigoSQL);
                resultado = comandoSQL.executeQuery();

                while (resultado.next()) {
                    Funcion funcion = new Funcion();
                    funcion.setId(resultado.getInt("idFuncion"));
                    funcion.setFecha(resultado.getDate("fecha"));
                    funcion.setHoraInicio(resultado.getDouble("horaInicio"));

                    Pelicula pelicula = new Pelicula();
                    pelicula=pelicula.convertirAEntidad(resultado);

                    funcion.setPeliculas(pelicula);

                    funcionLista.add(funcion);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                throw new cinepolisException("Ocurrió un error al leer la base de datos, intentelo de nuevo y si el error persiste", ex);
            } finally {
                // Cerrar ResultSet, PreparedStatement y Connection en el bloque finally
                if (resultado != null) {
                    try {
                        resultado.close();
                    } catch (SQLException e) {
                        System.out.println("Error al cerrar el ResultSet: " + e.getMessage());
                    }
                }
                if (comandoSQL != null) {
                    try {
                        comandoSQL.close();
                    } catch (SQLException e) {
                        System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                    }
                }
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (SQLException e) {
                        System.out.println("Error al cerrar la conexión: " + e.getMessage());
                    }
                }
            }
            return funcionLista;
        }

        public FuncionDTO obtenerIdFuncionPorSucursalYPelicula(String sucursal, Long id) {
            String consulta = "SELECT f.idFuncion " +
                              "FROM funciones f " +
                              "JOIN peliculas p ON f.idpeliculas = p.idPelicula " +
                              "JOIN salas s ON f.idFuncion = s.idfunciones " +
                              "JOIN sucursales sc ON s.idSucursal = sc.idSucursal " +
                              "WHERE sc.nombre = ? AND p.idPelicula = ?";

            System.out.println("Sucursal: " + sucursal);
            System.out.println("ID Película: " + id);

            try (Connection conexion = this.conexionBD.crearConexion();
                 PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, sucursal);
                pstmt.setLong(2, id);
                System.out.println("Executing query: " + pstmt);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        FuncionDTO funcion = new FuncionDTO();
                        funcion.setId(rs.getLong("idFuncion"));
                        return funcion;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; 
        }
        

    public SalaDTO obtenerSalaFuncionSucursal(String sucursal, Long id) {
        String consulta = "SELECT s.*\n" +
                    "FROM salas s\n" +
                    "JOIN funciones f ON s.idfunciones = f.idFuncion\n" +
                    "JOIN sucursales sc ON s.idSucursal = sc.idSucursal " + 
                    "WHERE sc.nombre = ? AND f.idFuncion = ?";

           try (Connection conexion = this.conexionBD.crearConexion();
                PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
               pstmt.setString(1, sucursal);
               pstmt.setLong(2, id);
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                       SalaDTO sala = new SalaDTO();
                       sala.setId(rs.getLong("idSala"));
                       sala.setNumero(rs.getInt("numero"));
                       return sala;
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return null;
       }
    
}
