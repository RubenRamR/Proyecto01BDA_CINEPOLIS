/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.ClienteDTO;
import Persistencia.Entidades.Cliente;
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
public class ClienteDAO implements IClienteDAO{
    
    private IConexionBD conexionBD;
    private Menu menu;
    
    public ClienteDAO(){
    }
    
    public ClienteDAO(IConexionBD conexionBD){
        this.conexionBD=conexionBD;
    }

    public IConexionBD getConexion(){
       return conexionBD;
    }
    
    
    
    //INSERTAR CLIENTES
    @Override
    public Cliente insertarCliente(Cliente cliente) throws cinepolisException {
        Connection conexion = null;
        try{
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);
            
            String codigoSQL = "SELECT idCliente, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento FROM clientes "
                    + "WHERE nombre = ? AND apellidoPaterno = ? AND apellidoMaterno = ? AND correo = ? AND contrasena = ? AND ubicacion = ? AND fechaNacimiento = ?";
            PreparedStatement comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setString(1, cliente.getNombre());
            comandoSQL.setString(2, cliente.getApellidoPaterno());
            comandoSQL.setString(3, cliente.getApellidoMaterno());
            comandoSQL.setString(4, cliente.getCorreo());
            comandoSQL.setString(5, cliente.getContrasena());
            comandoSQL.setString(6, cliente.getUbicacion());
            comandoSQL.setDate(7, new Date(cliente.getFechaNacimiento().getTime()));
            

            ResultSet resultado = comandoSQL.executeQuery();
            if (resultado.next()) {
                conexion.rollback();
                throw new cinepolisException("El cliente ya existe");
            }
            
            
            
            
            codigoSQL = "INSERT INTO clientes (nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertCommand = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);
            insertCommand.setString(1, cliente.getNombre());
            insertCommand.setString(2, cliente.getApellidoPaterno());
            insertCommand.setString(3, cliente.getApellidoMaterno());
            insertCommand.setString(4, cliente.getCorreo());
            
            String contraseñaAuxiliar = encriptar(cliente.getContrasena());
            
            insertCommand.setString(5, contraseñaAuxiliar);
            insertCommand.setString(6, cliente.getUbicacion());
            insertCommand.setDate(7, new Date(cliente.getFechaNacimiento().getTime()));
            
            insertCommand.executeUpdate();

            ResultSet generatedKeys = insertCommand.getGeneratedKeys();
            if (generatedKeys.next()) {
                cliente.setId(generatedKeys.getLong(1));
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
                throw new cinepolisException("Hubo un error al registrar el cliente",ex);
                
            } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            return cliente;
            }
    
    public String encriptar(String contra) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(contra.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString().substring(0, Math.min(hexString.length(), 10));
    }
    
    //Login Clientes 
    public Cliente login(Cliente cliente) throws cinepolisException{
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        ResultSet resultado = null;

        try {
            conexion = conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            String codigoSQL = "SELECT * FROM clientes WHERE correo = ? AND contrasena = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setString(1, cliente.getCorreo());
            comandoSQL.setString(2, cliente.getContrasena());

            resultado = comandoSQL.executeQuery();

            if (!resultado.next()) {
                throw new cinepolisException("El cliente no existe");
            }

            cliente = cliente.convertirAEntidad(resultado);

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
            throw new cinepolisException("Hubo un error al registrar el cliente", ex);
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return cliente;
    }
    
    
    
    //EDITAR CLIENTES
    @Override
    public Cliente editarCliente(Cliente cliente) throws cinepolisException {
        String sql = "UPDATE clientes SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, contrasena = ?, ubicacion = ?, fechaNacimiento = ? WHERE idCliente = ?";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidoPaterno());
            preparedStatement.setString(3, cliente.getApellidoMaterno());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setString(5, cliente.getContrasena());
            preparedStatement.setString(6, cliente.getUbicacion());
            preparedStatement.setDate(7, new Date(cliente.getFechaNacimiento().getTime()));
            preparedStatement.setLong(8, cliente.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas == 0) {
                conexion.rollback(); 
                throw new cinepolisException("No se encontró el cliente con el ID especificado");
            }

            conexion.commit();
            return cliente;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Error al actualizar el cliente: " + ex.getMessage(), ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error de informacion: " + e.getMessage());
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

    //ElIMINAR CLIENTE
    @Override
    public Cliente eliminarClientePorID(long idCliente) throws cinepolisException {
        String sqlSelect = "SELECT * FROM clientes WHERE idCliente = ?";
        String sqlDelete = "DELETE FROM clientes WHERE idCliente = ?";
        Connection conexion = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        Cliente cliente = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            selectStatement = conexion.prepareStatement(sqlSelect);
            selectStatement.setLong(1, idCliente);
            ResultSet resultado = selectStatement.executeQuery();

            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));
                cliente.setUbicacion(resultado.getString("ubicacion"));
                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
            } else {
                conexion.rollback();
                throw new cinepolisException("No se encontró el cliente con el ID especificado");
            }
            
            deleteStatement = conexion.prepareStatement(sqlDelete);
            deleteStatement.setLong(1, idCliente);
            deleteStatement.executeUpdate();

            conexion.commit();
            return cliente;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al eliminar el cliente: " + ex.getMessage(), ex);
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
    
    public List<Cliente> buscarClientesTabla() throws cinepolisException {
        try{
            List<Cliente> clientesLista=null;
            Connection conexion=this.conexionBD.crearConexion();
            String codigoSQL= "SELECT idCliente, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento FROM clientes";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado=comandoSQL.executeQuery(codigoSQL);
            while(resultado.next()){
                if(clientesLista==null){
                    clientesLista=new ArrayList<>();
                }
                Cliente cliente = new Cliente();
                cliente=cliente.convertirAEntidad(resultado);
                clientesLista.add(cliente);
            }
            conexion.close();
            return clientesLista;
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            throw new cinepolisException("Ocurrio un error al leer la base de datos, intentelo de nuevo y si el error persiste");
        }
    }
    
    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws cinepolisException {
        try {
            List<ClienteDTO> clientesLista = new ArrayList<>();
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idCliente, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento FROM clientes";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);

            while (resultado.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));
                cliente.setUbicacion(resultado.getString("ubicacion"));
                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                clientesLista.add(cliente);
            }

            conexion.close();
            return clientesLista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new cinepolisException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste", ex);
        }
    }
    
    @Override
    public ClienteDTO obtenerClientePorID(long id) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        ClienteDTO cliente = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idCliente, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento FROM clientes WHERE idCliente = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setLong(1, id);
            ResultSet resultado = comandoSQL.executeQuery();

            if (resultado.next()) {
                cliente = new ClienteDTO();
                cliente.setId(resultado.getLong("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setContrasena(resultado.getString("contrasena"));
                cliente.setUbicacion(resultado.getString("ubicacion"));
                cliente.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
            }

            return cliente;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new cinepolisException("Ocurrió un error al buscar el cliente por ID", ex);
        } finally {
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
    }
    
    public List<Cliente> buscarClientesConFiltros(String nombreFiltro, Date fechaInicio, Date fechaFin) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conexion = this.conexionBD.crearConexion();
            StringBuilder sqlBuilder = new StringBuilder("SELECT idCliente, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento FROM clientes WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
                sqlBuilder.append(" AND nombre LIKE ?");
                parametros.add("%" + nombreFiltro + "%");
            }

            if (fechaInicio != null) {
                sqlBuilder.append(" AND fechaNacimiento >= ?");
                parametros.add(new java.sql.Date(fechaInicio.getTime()));
            }

            if (fechaFin != null) {
                sqlBuilder.append(" AND fechaNacimiento <= ?");
                parametros.add(new java.sql.Date(fechaFin.getTime()));
            }

            preparedStatement = conexion.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < parametros.size(); i++) {
                preparedStatement.setObject(i + 1, parametros.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getLong("idCliente"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                cliente.setCorreo(resultSet.getString("correo"));
                cliente.setContrasena(resultSet.getString("contrasena"));
                cliente.setUbicacion(resultSet.getString("ubicacion"));
                cliente.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));
                clientes.add(cliente);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new cinepolisException("Error al buscar clientes: " + ex.getMessage(), ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el ResultSet: " + e.getMessage());
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
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

        return clientes;
    }
    
    public Point2D.Double conseguirCoordenas(int idCliente){
        Point2D.Double coordenadasCliente = null;
    try (Connection conn = conexionBD.obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement("SELECT ubicacion FROM clientes WHERE idCliente = ?");
    ) { 
        pstmt.setInt(1, idCliente);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String coordenadasStr = rs.getString("ubicacion");
                String[] coordenadasArray = coordenadasStr.split(",");
                double latitud = Double.parseDouble(coordenadasArray[0]);
                double longitud = Double.parseDouble(coordenadasArray[1]);
                coordenadasCliente = new Point2D.Double(latitud, longitud);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return coordenadasCliente;
    }

    
    
}
