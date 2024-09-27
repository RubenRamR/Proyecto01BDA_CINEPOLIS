/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.PeliculaDTO;
import Persistencia.Entidades.Pelicula;
import excepciones.cinepolisException;
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
public class PeliculaDAO implements IPeliculaDAO{
    
    private IConexionBD conexionBD = new ConexionBD();

    public PeliculaDAO() {
    }

    public PeliculaDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    //INSERTAR PELICULAS
    @Override
    public Pelicula insertarPelicula(Pelicula pelicula) throws cinepolisException {
        Connection conexion = null;
        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            String codigoSQL = "SELECT idPelicula, titulo FROM peliculas WHERE titulo = ? AND idGenero = ? AND idClasificacion = ?";
            PreparedStatement comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setString(1, pelicula.getTitulo());
            comandoSQL.setInt(2, pelicula.getGenero());
            comandoSQL.setInt(3, pelicula.getClasificacion());

            ResultSet resultado = comandoSQL.executeQuery();
            if (resultado.next()) {
                conexion.rollback();
                throw new cinepolisException("La película ya existe");
            }

            codigoSQL = "INSERT INTO peliculas (titulo, sinopsis, trailer, duracion, pais, idGenero, idClasificacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertCommand = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);
            insertCommand.setString(1, pelicula.getTitulo());
            insertCommand.setString(2, pelicula.getSinopsis());
            insertCommand.setString(3, pelicula.getTrailer());
            insertCommand.setDouble(4, pelicula.getDuracion());
            insertCommand.setString(5, pelicula.getPais());
            insertCommand.setInt(6, pelicula.getGenero());
            insertCommand.setInt(7, pelicula.getClasificacion());

            insertCommand.executeUpdate();

            ResultSet generatedKeys = insertCommand.getGeneratedKeys();
            if (generatedKeys.next()) {
                pelicula.setId(generatedKeys.getInt(1));
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
            throw new cinepolisException("Hubo un error al registrar la película", ex);
        } finally {
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
        return pelicula;
    }

    //EDITAR PELICULAS
    @Override
    public Pelicula editarPelicula(Pelicula pelicula) throws cinepolisException {
        String sql = "UPDATE peliculas SET titulo = ?, sinopsis = ?, trailer = ?, duracion = ?, pais = ?, idGenero = ?, idClasificacion = ? WHERE idPelicula = ?";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, pelicula.getTitulo());
            preparedStatement.setString(2, pelicula.getSinopsis());
            preparedStatement.setString(3, pelicula.getTrailer());
            preparedStatement.setDouble(4, pelicula.getDuracion());
            preparedStatement.setString(5, pelicula.getPais());
            preparedStatement.setInt(6, pelicula.getGenero());
            preparedStatement.setInt(7, pelicula.getClasificacion());
            preparedStatement.setLong(8, pelicula.getId());

            System.out.println("Ejecutando SQL: " + sql);
            System.out.println("Con valores: ");
            System.out.println("titulo = " + pelicula.getTitulo());
            System.out.println("sinopsis = " + pelicula.getSinopsis());
            System.out.println("trailer = " + pelicula.getTrailer());
            System.out.println("duracion = " + pelicula.getDuracion());
            System.out.println("pais = " + pelicula.getPais());
            System.out.println("idGenero = " + pelicula.getGenero());
            System.out.println("idClasificacion = " + pelicula.getClasificacion());
            System.out.println("idPelicula = " + pelicula.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas == 0) {
                conexion.rollback();
                throw new cinepolisException("No se encontró la película con el ID especificado");
            }

            conexion.commit();
            return pelicula;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Error al actualizar la película: " + ex.getMessage(), ex);
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

    //ELIMINAR PELICULAS
    @Override
    public Pelicula eliminarPeliculaPorID(Long idPelicula) throws cinepolisException {
        String sqlSelect = "SELECT * FROM peliculas WHERE idPelicula = ?";
        String sqlDelete = "DELETE FROM peliculas WHERE idPelicula = ?";
        Connection conexion = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        Pelicula pelicula = null;

        try {
            conexion = this.conexionBD.crearConexion();
            conexion.setAutoCommit(false);

            selectStatement = conexion.prepareStatement(sqlSelect);
            selectStatement.setLong(1, idPelicula);
            ResultSet resultado = selectStatement.executeQuery();

            if (resultado.next()) {
                pelicula = new Pelicula();
                pelicula.setId(resultado.getInt("idPelicula"));
                pelicula.setTitulo(resultado.getString("titulo"));
                pelicula.setSinopsis(resultado.getString("sinopsis"));
                pelicula.setTrailer(resultado.getString("trailer"));
                pelicula.setDuracion(resultado.getDouble("duracion"));
                pelicula.setPais(resultado.getString("pais"));
                pelicula.setGenero(resultado.getInt("idGenero"));
                pelicula.setClasificacion(resultado.getInt("idClasificacion"));
            } else {
                conexion.rollback();
                throw new cinepolisException("No se encontró la película con el ID especificado");
            }

            deleteStatement = conexion.prepareStatement(sqlDelete);
            deleteStatement.setLong(1, idPelicula);
            deleteStatement.executeUpdate();

            conexion.commit();
            return pelicula;
        } catch (SQLException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            throw new cinepolisException("Hubo un error al eliminar la película: " + ex.getMessage(), ex);
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

    //OBTENER PELICULA POR ID
    @Override
    public PeliculaDTO obtenerPeliculaPorId(long id) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        PeliculaDTO pelicula = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idPelicula, titulo, sinopsis, trailer, duracion, pais, idGenero,idClasificacion FROM peliculas WHERE idPelicula = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setLong(1, id);
            ResultSet resultado = comandoSQL.executeQuery();

            if (resultado.next()) {
                pelicula = new PeliculaDTO();
                pelicula.setId(resultado.getLong("idPelicula"));
                pelicula.setTitulo(resultado.getString("titulo"));
                pelicula.setSinopsis(resultado.getString("sinopsis"));
                pelicula.setTrailer(resultado.getString("trailer"));
                pelicula.setDuracion(resultado.getDouble(("duracion")));
                pelicula.setPais(resultado.getString("pais"));

                long idGenero = resultado.getLong("idGenero");
                String tipoGenero = obtenerTipoGeneroPorID(idGenero);
                pelicula.setGenero(tipoGenero);

                long idClasificacion = resultado.getLong("idClasificacion");
                String tipoClasificacion = obtenerTipoClasificacionPorID(idClasificacion);
                pelicula.setClasificacion(tipoClasificacion);
            }

            return pelicula;
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

    public Pelicula obtenerPeliculaPorIdPelicula(long id) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        Pelicula pelicula = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idPelicula, titulo, sinopsis, trailer, duracion, pais, idGenero,idClasificacion FROM peliculas WHERE idPelicula = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setLong(1, id);
            ResultSet resultado = comandoSQL.executeQuery();

            if (resultado.next()) {
                pelicula = new Pelicula();
                pelicula.setId(resultado.getInt("idPelicula"));
                pelicula.setTitulo(resultado.getString("titulo"));
                pelicula.setSinopsis(resultado.getString("sinopsis"));
                pelicula.setTrailer(resultado.getString("trailer"));
                pelicula.setDuracion(resultado.getDouble(("duracion")));
                pelicula.setPais(resultado.getString("pais"));
                pelicula.setGenero(resultado.getInt("idGenero"));
                pelicula.setClasificacion(resultado.getInt("idClasificacion"));
            }

            return pelicula;
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

    public Pelicula obtenerPeliculaPorIda(long id) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement comandoSQL = null;
        Pelicula pelicula = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idPelicula, titulo, sinopsis, trailer, duracion, pais, idGenero,idClasificacion FROM peliculas WHERE idPelicula = ?";
            comandoSQL = conexion.prepareStatement(codigoSQL);
            comandoSQL.setLong(1, id);
            ResultSet resultado = comandoSQL.executeQuery();

            if (resultado.next()) {
                pelicula = new Pelicula();
                pelicula.setId(resultado.getInt("idPelicula"));
                pelicula.setTitulo(resultado.getString("titulo"));
                pelicula.setSinopsis(resultado.getString("sinopsis"));
                pelicula.setTrailer(resultado.getString("trailer"));
                pelicula.setDuracion(resultado.getDouble(("duracion")));
                pelicula.setPais(resultado.getString("pais"));
                pelicula.setClasificacion(resultado.getInt("idClasificacion"));
                pelicula.setGenero(resultado.getInt("idGenero"));

            }

            return pelicula;
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

    //OBTENER IDS
    private String obtenerTipoGeneroPorID(long idGenero) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String tipoGenero = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String query = "SELECT tipo FROM genero WHERE idGenero = ?";
            statement = conexion.prepareStatement(query);
            statement.setLong(1, idGenero);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoGenero = resultSet.getString("tipo");
            }
        } catch (SQLException e) {
            throw new cinepolisException("Error al obtener el tipo de género por ID: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }

        return tipoGenero;
    }

    private String obtenerTipoClasificacionPorID(long idClasificacion) throws cinepolisException {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String tipoClasificacion = null;

        try {
            conexion = this.conexionBD.crearConexion();
            String query = "SELECT tipo FROM clasificacion WHERE idClasificacion = ?";
            statement = conexion.prepareStatement(query);
            statement.setLong(1, idClasificacion);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoClasificacion = resultSet.getString("tipo");
            }
        } catch (SQLException e) {
            throw new cinepolisException("Error al obtener el tipo de clasificación por ID: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }

        return tipoClasificacion;
    }

    @Override
    public List<PeliculaDTO> obtenerTodasLasPeliculas() throws cinepolisException {

        try {
            List<PeliculaDTO> peliculasLista = new ArrayList<>();
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idPelicula, titulo, sinopsis, trailer, duracion, pais, idGenero,idClasificacion FROM peliculas";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);

            while (resultado.next()) {
                PeliculaDTO pelicula = new PeliculaDTO();
                pelicula.setId(resultado.getLong("idPelicula"));
                pelicula.setTitulo(resultado.getString("titulo"));
                pelicula.setSinopsis(resultado.getString("sinopsis"));
                pelicula.setTrailer(resultado.getString("trailer"));
                pelicula.setDuracion(resultado.getDouble("duracion"));
                pelicula.setPais(resultado.getString("pais"));
                pelicula.setGenero(obtenerTipoGeneroPorID(resultado.getInt("idGenero")));
                pelicula.setClasificacion(obtenerTipoClasificacionPorID(resultado.getInt("idClasificacion")));
                peliculasLista.add(pelicula);
            }

            conexion.close();
            return peliculasLista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new cinepolisException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste", ex);
        }
    }

    public List<Pelicula> buscarPeliculasTabla() throws cinepolisException {

        try {
            List<Pelicula> pelicuasLista = null;
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idPelicula, titulo, sinopsis, trailer, duracion, pais, idGenero,idClasificacion FROM peliculas";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            while (resultado.next()) {
                if (pelicuasLista == null) {
                    pelicuasLista = new ArrayList<>();
                }
                Pelicula pelicula = new Pelicula();
                pelicula = pelicula.convertirAEntidad(resultado);
                pelicuasLista.add(pelicula);
            }
            conexion.close();
            return pelicuasLista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new cinepolisException("Ocurrio un error al leer la base de datos, intentelo de nuevo y si el error persiste");
        }
    }

    @Override
    public List<PeliculaDTO> buscarPeliculasConFiltros(String titulo, String genero, String clasificacion, String pais) throws SQLException, cinepolisException {
        List<PeliculaDTO> peliculas = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM peliculas WHERE 1=1");

        if (titulo != null && !titulo.isEmpty()) {
            query.append(" AND titulo LIKE ?");
        }
        if (genero != null && !genero.isEmpty()) {
            query.append(" AND idGenero = (SELECT idGenero FROM genero WHERE tipo = ?)");
        }

        if (clasificacion != null && !clasificacion.isEmpty()) {
            query.append(" AND idClasificacion = (SELECT idClasificacion FROM clasificacion WHERE tipo = ?)");
        }
        if (pais != null && !pais.isEmpty()) {
            query.append(" AND pais LIKE ?");
        }
        System.out.println("PeliculaDAO " + genero + " " + clasificacion);

        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

            int paramIndex = 1;

            if (titulo != null && !titulo.isEmpty()) {
                stmt.setString(paramIndex++, "%" + titulo + "%");
            }
            if (genero != null && !genero.isEmpty()) {
                stmt.setString(paramIndex++, genero);
            }
            if (clasificacion != null && !clasificacion.isEmpty()) {
                stmt.setString(paramIndex++, clasificacion);
            }
            if (pais != null && !pais.isEmpty()) {
                stmt.setString(paramIndex++, "%" + pais + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PeliculaDTO pelicula = new PeliculaDTO();
                    pelicula.setId(rs.getLong("idPelicula"));
                    pelicula.setTitulo(rs.getString("titulo"));
                    pelicula.setSinopsis(rs.getString("sinopsis"));
                    pelicula.setTrailer(rs.getString("trailer"));
                    pelicula.setDuracion(rs.getDouble("duracion"));
                    pelicula.setPais(rs.getString("pais"));
                    pelicula.setGenero(obtenerTipoGeneroPorID(rs.getInt("idGenero")));
                    pelicula.setClasificacion(obtenerTipoClasificacionPorID(rs.getInt("idClasificacion")));
                    peliculas.add(pelicula);
                    System.out.println("Id ingresado genero: " + obtenerTipoGeneroPorID(rs.getInt("idGenero")) + " id ingresadoClasificacion: " + obtenerTipoClasificacionPorID(rs.getInt("idClasificacion")));
                }
            }
        }
        return peliculas;
    }

    public PeliculaDTO buscarPeliculaConTitulo(String titulo) throws SQLException, cinepolisException {
        PeliculaDTO pelicula = null;
        String query = "SELECT * FROM peliculas WHERE titulo LIKE ?";

        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, "%" + titulo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pelicula = new PeliculaDTO();
                    pelicula.setId(rs.getLong("idPelicula"));
                    pelicula.setTitulo(rs.getString("titulo"));
                    pelicula.setSinopsis(rs.getString("sinopsis"));
                    pelicula.setTrailer(rs.getString("trailer"));
                    pelicula.setDuracion(rs.getDouble("duracion"));
                    pelicula.setPais(rs.getString("pais"));
                    pelicula.setGenero(obtenerTipoGeneroPorID(rs.getInt("idGenero")));
                    pelicula.setClasificacion(obtenerTipoClasificacionPorID(rs.getInt("idClasificacion")));
                }
            }
        }

        if (pelicula == null) {
            throw new cinepolisException("No se encontró ninguna película con el título especificado");
        }

        return pelicula;
    }

    public List<String> obtenerTitulosPorGeneroYSucursal(String generoSeleccionado, String sucursalSeleccionada) throws SQLException {
        List<String> titulos = new ArrayList<>();
        StringBuilder consulta = new StringBuilder("SELECT " +
                "    p.titulo AS Titulo_Pelicula " +
                "FROM " +
                "    funciones f " +
                "JOIN " +
                "    peliculas p ON f.idpeliculas = p.idPelicula " +
                "JOIN " +
                "    salas s ON f.idFuncion = s.idfunciones " +
                "JOIN " +
                "    sucursales sc ON s.idSucursal = sc.idSucursal ");

        // Agregar filtro de género si se proporciona
        if (generoSeleccionado != null && !generoSeleccionado.isEmpty()) {
            consulta.append("JOIN " +
                            "    genero g ON p.idGenero = g.idGenero " +
                            "WHERE " +
                            "    g.tipo = ? ");
        }

        // Agregar filtro de sucursal
        consulta.append("AND sc.nombre = ? ");

        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement pstmt = conexion.prepareStatement(consulta.toString())) {
            int index = 1;
            // Asignar valores de parámetros
            if (generoSeleccionado != null && !generoSeleccionado.isEmpty()) {
                pstmt.setString(index++, generoSeleccionado);
            }
            pstmt.setString(index, sucursalSeleccionada);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String titulo = rs.getString("Titulo_Pelicula");
                    titulos.add(titulo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        if (!titulos.isEmpty()) {
            System.out.println(titulos.get(0));
        }
        return titulos;
    }

    public List<Pelicula> PeliculasPOrSucursal(String nombreSucursal) throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        String consulta = "SELECT * FROM  funciones JOIN  peliculas ON funciones.idpeliculas = peliculas.idPelicula "
                + "JOIN  salas ON funciones.idsala = salas.idSala "
                + "JOIN  sucursales ON salas.idSucursal = sucursales.idSucursal where sucursales.nombre = ?";
        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, nombreSucursal);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pelicula peliAuxiliar = new Pelicula();
                    peliculas.add(peliAuxiliar.convertirAEntidad(rs));
                }
            }
        }
        
        return peliculas;
    }
    
     public List<Pelicula> TodasLasPeliculas() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        String consulta = "SELECT * FROM  funciones JOIN  peliculas ON funciones.idpeliculas = peliculas.idPelicula "
                + "JOIN  salas ON funciones.idsala = salas.idSala "
                + "JOIN  sucursales ON salas.idSucursal = sucursales.idSucursal";
        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
           
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pelicula peliAuxiliar = new Pelicula();
                    peliculas.add(peliAuxiliar.convertirAEntidad(rs));
                }
            }
        }
        return peliculas;
    }

    public List<PeliculaDTO> obtenerDatosPorGeneroYSucursal(String generoSeleccionado, String sucursalSeleccionada) throws SQLException {
        List<PeliculaDTO> peliculas = new ArrayList<>();
        StringBuilder consulta = new StringBuilder("SELECT " +
                "    p.idPelicula AS ID_Pelicula, " +
                "    p.titulo AS Titulo_Pelicula, " +
                "    p.sinopsis AS Sinopsis_Pelicula, " +
                "    p.duracion AS Duracion_Pelicula " +
                "FROM " +
                "    funciones f " +
                "JOIN " +
                "    peliculas p ON f.idpeliculas = p.idPelicula " +
                "JOIN " +
                "    salas s ON f.idFuncion = s.idfunciones " +
                "JOIN " +
                "    sucursales sc ON s.idSucursal = sc.idSucursal ");

        // Agregar filtro de género si se proporciona
        if (generoSeleccionado != null && !generoSeleccionado.isEmpty()) {
            consulta.append("JOIN " +
                            "    genero g ON p.idGenero = g.idGenero " +
                            "WHERE " +
                            "    g.tipo = ? ");
        }

        // Agregar filtro de sucursal
        consulta.append(generoSeleccionado != null && !generoSeleccionado.isEmpty() ? " AND sc.nombre = ? " : " WHERE sc.nombre = ? ");

        try (Connection conexion = this.conexionBD.crearConexion(); PreparedStatement pstmt = conexion.prepareStatement(consulta.toString())) {
            int index = 1;
            // Asignar valores de parámetros
            if (generoSeleccionado != null && !generoSeleccionado.isEmpty()) {
                pstmt.setString(index++, generoSeleccionado);
            }
            pstmt.setString(index, sucursalSeleccionada);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PeliculaDTO pelicula = new PeliculaDTO();
                    pelicula.setId(rs.getLong("ID_Pelicula"));
                    pelicula.setTitulo(rs.getString("Titulo_Pelicula"));
                    pelicula.setSinopsis(rs.getString("Sinopsis_Pelicula"));
                    pelicula.setDuracion(rs.getDouble("Duracion_Pelicula"));
                    peliculas.add(pelicula);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        if (!peliculas.isEmpty()) {
            System.out.println(peliculas.get(0).getTitulo());
        }
        return peliculas;
        }
    
}
