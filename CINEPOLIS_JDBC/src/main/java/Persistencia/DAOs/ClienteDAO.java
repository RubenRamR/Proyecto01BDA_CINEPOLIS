/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.CiudadDTO;
import Negocio.DTOs.ClienteDTO;
import Persistencia.Entidades.Ciudad;
import Persistencia.Entidades.Cliente;
import static Persistencia.Entidades.Cliente.convertirAEntidad;
import com.itextpdf.awt.geom.Point2D;
import excepciones.cinepolisException;
import java.awt.Menu;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rramirez
 */
public class ClienteDAO implements IClienteDAO {

    private IConexionBD conexionBD;
    private Menu menu;

    public ClienteDAO() {
    }

    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public IConexionBD getConexion() {
        return conexionBD;
    }

    //INSERTAR CLIENTES
    @Override
    public Cliente insertarCliente(Cliente cliente) throws cinepolisException {
        Connection conexion = null;
        try
        {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            String codigoSQL = "SELECT idCliente FROM clientes WHERE nombre = ? AND apellidoPaterno = ? AND apellidoMaterno = ? AND correo = ? AND contrasena = ? AND fechaNacimiento = ? AND ciudad = ?";
            PreparedStatement comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setString(1, cliente.getNombre());
            comandoSQL.setString(2, cliente.getApellidoPaterno());
            comandoSQL.setString(3, cliente.getApellidoMaterno());
            comandoSQL.setString(4, cliente.getCorreo());
            comandoSQL.setString(5, encriptar(cliente.getContrasena()));
            comandoSQL.setDate(6, new Date(cliente.getFechaNacimiento().getTime()));
            comandoSQL.setLong(7, cliente.getCiudad().getId());

            ResultSet resultado = comandoSQL.executeQuery();
            if (resultado.next())
            {
                conexion.rollback();
                throw new cinepolisException("El cliente ya existe");
            }

            codigoSQL = "INSERT INTO clientes (nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento, ciudad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertCommand = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);
            insertCommand.setString(1, cliente.getNombre());
            insertCommand.setString(2, cliente.getApellidoPaterno());
            insertCommand.setString(3, cliente.getApellidoMaterno());
            insertCommand.setString(4, cliente.getCorreo());
            insertCommand.setString(5, encriptar(cliente.getContrasena()));
            insertCommand.setDouble(6, cliente.getUbicacion().getX()); // Guardar la coordenada X
            insertCommand.setDouble(7, cliente.getUbicacion().getY()); // Guardar la coordenada Y
            insertCommand.setDate(8, new Date(cliente.getFechaNacimiento().getTime()));
            insertCommand.setLong(9, cliente.getCiudad().getId());

            insertCommand.executeUpdate();

            ResultSet generatedKeys = insertCommand.getGeneratedKeys();
            if (generatedKeys.next())
            {
                cliente.setId(generatedKeys.getLong(1));
            }

            conexion.commit();
        } catch (SQLException ex)
        {
            if (conexion != null)
            {
                try
                {
                    conexion.rollback();
                } catch (SQLException rollbackEx)
                {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            System.out.println(ex.getMessage());
            throw new cinepolisException("Hubo un error al registrar el cliente", ex);

        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            if (conexion != null)
            {
                try
                {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
        return cliente;
    }

    // Método de encriptación
    public String encriptar(String contra) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(contra.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes)
        {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString().substring(0, Math.min(hexString.length(), 10));
    }

    public Cliente login(Cliente cliente) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        ResultSet resultado = null;

        try
        {
            conexion = conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            String codigoSQL = "SELECT * FROM clientes WHERE correo = ? AND contrasena = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setString(1, cliente.getCorreo());
            comandoSQL.setString(2, cliente.getContrasena()); // Usa la contraseña sin encriptar

            resultado = comandoSQL.executeQuery();

            System.out.println("Correo: " + cliente.getCorreo());
            System.out.println("Contraseña: " + cliente.getContrasena()); // Mostrar contraseña para depuración

            if (!resultado.next())
            {
                throw new cinepolisException("El cliente no existe");
            }

            // Convertir el resultado a un objeto Cliente
            cliente = convertirAEntidad(resultado);
            conexion.commit();
        } catch (SQLException ex)
        {
            if (conexion != null)
            {
                try
                {
                    conexion.rollback();
                } catch (SQLException rollbackEx)
                {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            System.out.println(ex.getMessage());
            throw new cinepolisException("Hubo un error al realizar el login", ex);
        } finally
        {
            // Cierre de recursos
            try
            {
                if (resultado != null)
                {
                    resultado.close();
                }
                if (comandoSQL != null)
                {
                    comandoSQL.close();
                }
                if (conexion != null)
                {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException e)
            {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return cliente;
    }

    // Método de editar cliente
    @Override
    public Cliente editarCliente(Cliente cliente) throws cinepolisException {
        String sql = "UPDATE clientes SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, contrasena = ?, ubicacionX = ?, ubicacionY = ?, fechaNacimiento = ?, ciudad = ? WHERE idCliente = ?";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try
        {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidoPaterno());
            preparedStatement.setString(3, cliente.getApellidoMaterno());
            preparedStatement.setString(4, cliente.getCorreo());
            try
            {
                preparedStatement.setString(5, encriptar(cliente.getContrasena()));
            } catch (NoSuchAlgorithmException ex)
            {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStatement.setDouble(6, cliente.getUbicacion().getX()); // Guardar coordenada X
            preparedStatement.setDouble(7, cliente.getUbicacion().getY()); // Guardar coordenada Y
            preparedStatement.setDate(8, new Date(cliente.getFechaNacimiento().getTime()));
            preparedStatement.setLong(9, cliente.getCiudad().getId());
            preparedStatement.setLong(10, cliente.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas == 0)
            {
                conexion.rollback();
                throw new cinepolisException("No se encontró el cliente con el ID especificado");
            }

            conexion.commit();
            return cliente;
        } catch (SQLException ex)
        {
            if (conexion != null)
            {
                try
                {
                    conexion.rollback();
                } catch (SQLException rollbackEx)
                {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Error al actualizar el cliente: " + ex.getMessage(), ex);
        } finally
        {
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null)
            {
                try
                {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public Cliente eliminarClientePorID(long idCliente) throws cinepolisException {
        String sqlSelect = "SELECT * FROM clientes WHERE idCliente = ?";
        String sqlDelete = "DELETE FROM clientes WHERE idCliente = ?";
        Connection conexion = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        Cliente cliente = null;

        try
        {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            // Seleccionar el cliente por su ID
            selectStatement = conexion.prepareStatement(sqlSelect);
            selectStatement.setLong(1, idCliente);
            ResultSet resultado = selectStatement.executeQuery();

            if (resultado.next())
            {
                cliente = new Cliente();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));

                // Crear el objeto de ubicación
                Point2D.Double ubicacion = new Point2D.Double(resultado.getDouble("ubicacionX"), resultado.getDouble("ubicacionY"));
                cliente.setUbicacion(ubicacion);

                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                Ciudad ciudad = new Ciudad();
                ciudad.setId(resultado.getLong("ciudad"));
                cliente.setCiudad(ciudad);
            } else
            {
                conexion.rollback();
                throw new cinepolisException("No se encontró el cliente con el ID especificado");
            }

            // Eliminar el cliente
            deleteStatement = conexion.prepareStatement(sqlDelete);
            deleteStatement.setLong(1, idCliente);
            deleteStatement.executeUpdate();

            conexion.commit();
            return cliente;
        } catch (SQLException ex)
        {
            if (conexion != null)
            {
                try
                {
                    conexion.rollback();
                } catch (SQLException rollbackEx)
                {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al eliminar el cliente: " + ex.getMessage(), ex);
        } finally
        {
            if (selectStatement != null)
            {
                try
                {
                    selectStatement.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el selectStatement: " + e.getMessage());
                }
            }
            if (deleteStatement != null)
            {
                try
                {
                    deleteStatement.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el deleteStatement: " + e.getMessage());
                }
            }
            if (conexion != null)
            {
                try
                {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public List<Cliente> buscarClientesTabla() throws cinepolisException {
        List<Cliente> clientesLista = new ArrayList<>();

        String codigoSQL = "SELECT c.idCliente, c.nombre, c.apellidoPaterno, c.apellidoMaterno, c.correo, "
                + "c.contrasena, c.ubicacionX, c.ubicacionY, c.fechaNacimiento, "
                + "ci.id AS ciudadId, ci.nombre AS ciudadNombre, ci.localizacion "
                + "FROM clientes c "
                + "JOIN ciudad ci ON c.ciudad = ci.id";

        try (Connection conexion = this.conexionBD.crearConexion(); Statement comandoSQL = conexion.createStatement(); ResultSet resultado = comandoSQL.executeQuery(codigoSQL))
        {

            while (resultado.next())
            {
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));

                // Crear el objeto de ubicación usando ubicacionX y ubicacionY
                Point2D.Double ubicacion = new Point2D.Double(
                        resultado.getDouble("ubicacionX"),
                        resultado.getDouble("ubicacionY")
                );
                cliente.setUbicacion(ubicacion);

                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));

                Ciudad ciudad = new Ciudad();
                ciudad.setId(resultado.getLong("ciudadId")); // Obtener el ID de la ciudad
                ciudad.setNombre(resultado.getString("ciudadNombre")); // Obtener el nombre de la ciudad
                ciudad.setLocalizacion(resultado.getString("localizacion")); // Obtener la localización de la ciudad
                cliente.setCiudad(ciudad);

                clientesLista.add(cliente);
            }

        } catch (SQLException ex)
        {
            System.out.println("Error en la consulta de clientes: " + ex.getMessage());
            throw new cinepolisException("Ocurrió un error al leer la base de datos. Inténtelo de nuevo y si el error persiste, contacte al soporte.", ex);
        }

        return clientesLista;
    }

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws cinepolisException {
        List<ClienteDTO> clientesLista = new ArrayList<>();
        String codigoSQL = "SELECT c.idCliente, c.nombre, c.apellidoPaterno, c.apellidoMaterno, c.correo, "
                + "c.contrasena, c.ubicacionX, c.ubicacionY, c.fechaNacimiento, "
                + "ci.id AS ciudadId, ci.nombre AS ciudadNombre, ci.localizacion AS ciudadLocalizacion "
                + "FROM cinepolisdb.clientes c "
                + "JOIN ciudad ci ON c.ciudad = ci.id";

        try (Connection conexion = this.conexionBD.crearConexion(); Statement comandoSQL = conexion.createStatement(); ResultSet resultado = comandoSQL.executeQuery(codigoSQL))
        {

            while (resultado.next())
            {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));

                // Crear el objeto de ubicación usando ubicacionX y ubicacionY
                Point2D.Double ubicacion = new Point2D.Double(
                        resultado.getDouble("ubicacionX"),
                        resultado.getDouble("ubicacionY")
                );
                cliente.setUbicacion(ubicacion);

                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));

                CiudadDTO ciudad = new CiudadDTO();
                ciudad.setId(resultado.getLong("ciudadId")); // Obtener el ID de la ciudad
                ciudad.setNombre(resultado.getString("ciudadNombre")); // Obtener el nombre de la ciudad
                ciudad.setLocalizacion(resultado.getString("ciudadLocalizacion")); // Obtener la localización de la ciudad
                cliente.setCiudad(ciudad);

                clientesLista.add(cliente);
            }

        } catch (SQLException ex)
        {
            System.out.println("Error al obtener todos los clientes: " + ex.getMessage());
            throw new cinepolisException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste", ex);
        }

        return clientesLista;
    }

    @Override
    public ClienteDTO obtenerClientePorID(long id) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        ClienteDTO cliente = null;

        try
        {
            conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT c.idCliente, c.nombre, c.apellidoPaterno, c.apellidoMaterno, c.correo, "
                    + "c.contrasena, c.ubicacionX, c.ubicacionY, c.fechaNacimiento, ci.id AS ciudadId, "
                    + "ci.nombre AS ciudadNombre, ci.localizacion "
                    + "FROM clientes c "
                    + "JOIN ciudad ci ON c.ciudad = ci.id "
                    + "WHERE c.idCliente = ?";

            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setLong(1, id);
            ResultSet resultado = comandoSQL.executeQuery();

            if (resultado.next())
            {
                cliente = new ClienteDTO();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));

                // Crear el objeto de ubicación usando ubicacionX y ubicacionY
                Point2D.Double ubicacion = new Point2D.Double(
                        resultado.getDouble("ubicacionX"),
                        resultado.getDouble("ubicacionY")
                );
                cliente.setUbicacion(ubicacion);

                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));

                CiudadDTO ciudad = new CiudadDTO();
                ciudad.setId(resultado.getLong("ciudadId")); // ID de la ciudad
                ciudad.setNombre(resultado.getString("ciudadNombre")); // Nombre de la ciudad
                ciudad.setLocalizacion(resultado.getString("localizacion")); // Localización de la ciudad
                cliente.setCiudad(ciudad);
            }

            return cliente; // Puede retornar null si no se encuentra el cliente
        } catch (SQLException ex)
        {
            System.out.println("Error al buscar el cliente por ID: " + ex.getMessage());
            throw new cinepolisException("Ocurrió un error al buscar el cliente por ID", ex);
        } finally
        {
            // Cierre de recursos en el bloque finally
            if (comandoSQL != null)
            {
                try
                {
                    comandoSQL.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null)
            {
                try
                {
                    conexion.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public List<ClienteDTO> buscarClientesConFiltros(String nombreFiltro, Date fechaInicio, Date fechaFin, String ciudadFiltro) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ClienteDTO> clientes = new ArrayList<>();

        try
        {
            conexion = this.conexionBD.crearConexion();

            // Construcción de la consulta SQL
            StringBuilder sqlBuilder = new StringBuilder("SELECT c.idCliente, c.nombre, c.apellidoPaterno, c.apellidoMaterno, "
                    + "c.correo, c.contrasena, c.ubicacionX, c.ubicacionY, c.fechaNacimiento, ci.nombre AS ciudadNombre "
                    + "FROM clientes c INNER JOIN ciudad ci ON c.ciudad = ci.id WHERE 1=1");

            List<Object> parametros = new ArrayList<>();

            // Filtro por nombre
            if (nombreFiltro != null && !nombreFiltro.trim().isEmpty())
            {
                sqlBuilder.append(" AND c.nombre LIKE ?");
                parametros.add("%" + nombreFiltro + "%");
            }

            // Filtro por fecha de inicio
            if (fechaInicio != null)
            {
                sqlBuilder.append(" AND c.fechaNacimiento >= ?");
                parametros.add(new java.sql.Date(fechaInicio.getTime()));
            }

            // Filtro por fecha de fin
            if (fechaFin != null)
            {
                sqlBuilder.append(" AND c.fechaNacimiento <= ?");
                parametros.add(new java.sql.Date(fechaFin.getTime()));
            }

            // Filtro por ciudad
            if (ciudadFiltro != null && !ciudadFiltro.trim().isEmpty())
            {
                sqlBuilder.append(" AND ci.nombre LIKE ?");
                parametros.add("%" + ciudadFiltro + "%");
            }

            preparedStatement = conexion.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < parametros.size(); i++)
            {
                preparedStatement.setObject(i + 1, parametros.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(resultSet.getLong("idCliente"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                cliente.setCorreo(resultSet.getString("correo"));
                cliente.setContrasena(resultSet.getString("contrasena"));

                // Crear el objeto de ubicación usando ubicacionX y ubicacionY
                Point2D.Double ubicacion = new Point2D.Double(
                        resultSet.getDouble("ubicacionX"),
                        resultSet.getDouble("ubicacionY")
                );
                cliente.setUbicacion(ubicacion);

                cliente.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));

                // Setear la ciudad
                CiudadDTO ciudad = new CiudadDTO();
                ciudad.setNombre(resultSet.getString("ciudadNombre"));
                cliente.setCiudad(ciudad);

                clientes.add(cliente);
            }

        } catch (SQLException ex)
        {
            System.out.println("Error al buscar clientes: " + ex.getMessage());
            throw new cinepolisException("Error al buscar clientes: " + ex.getMessage(), ex);
        } finally
        {
            // Cierre de recursos
            if (resultSet != null)
            {
                try
                {
                    resultSet.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el ResultSet: " + e.getMessage());
                }
            }
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null)
            {
                try
                {
                    conexion.close();
                } catch (SQLException e)
                {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }

        return clientes;
    }

    public Point2D.Double conseguirCoordenas(int idCliente) {
        Point2D.Double coordenadasCliente = null;

        String sql = "SELECT ubicacion FROM clientes WHERE idCliente = ?";
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setInt(1, idCliente);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    String coordenadasStr = rs.getString("ubicacion");

                    // Verifica que las coordenadas no sean nulas y contengan el formato correcto
                    if (coordenadasStr != null && coordenadasStr.contains(","))
                    {
                        String[] coordenadasArray = coordenadasStr.split(",");
                        if (coordenadasArray.length == 2)
                        {
                            try
                            {
                                double latitud = Double.parseDouble(coordenadasArray[0].trim());
                                double longitud = Double.parseDouble(coordenadasArray[1].trim());
                                coordenadasCliente = new Point2D.Double(latitud, longitud);
                            } catch (NumberFormatException e)
                            {
                                System.out.println("Error al convertir coordenadas a double: " + e.getMessage());
                            }
                        } else
                        {
                            System.out.println("Formato de coordenadas incorrecto para el cliente ID: " + idCliente);
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return coordenadasCliente;
    }

}
