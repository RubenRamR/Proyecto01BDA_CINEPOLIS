/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.Negocio;

import Negocio.DTOs.BoletoDTO;
import Negocio.DTOs.CiudadDTO;
import Negocio.DTOs.ClasificacionDTO;
import Negocio.DTOs.ClienteDTO;
import Negocio.DTOs.FuncionDTO;
import Negocio.DTOs.GeneroDTO;
import Negocio.DTOs.PeliculaDTO;
import Negocio.DTOs.SalaDTO;
import Negocio.DTOs.SucursalDTO;
import Persistencia.DAOs.BoletoDAO;
import Persistencia.DAOs.ClasificacionDAO;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.FuncionDAO;
import Persistencia.DAOs.GeneroDAO;
import Persistencia.DAOs.PeliculaDAO;
import Persistencia.DAOs.ReporteDAO;
import Persistencia.DAOs.SucursalesDAO;
import Persistencia.Entidades.Boleto;
import Persistencia.Entidades.Ciudad;
import Persistencia.Entidades.Clasificacion;
import Persistencia.Entidades.Cliente;
import Persistencia.Entidades.Funcion;
import Persistencia.Entidades.Genero;
import Persistencia.Entidades.Pelicula;
import Persistencia.Entidades.Reporte;
import com.itextpdf.awt.geom.Point2D;
import excepciones.cinepolisException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rramirez
 */
public class ClienteNegocio implements IClienteNegocio {

    ClienteDAO clienteDAO = new ClienteDAO();
    PeliculaDAO peliculaDAO = new PeliculaDAO();
    ClasificacionDAO clasificacionDAO;
    GeneroDAO generoDAO;
    FuncionDAO funcionDAO;
    ReporteDAO reporteDAO;
    SucursalesDAO sucursalesDAO;
    long id;
    BoletoDAO boletoDAO;

    public ClienteNegocio(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.peliculaDAO = new PeliculaDAO(clienteDAO.getConexion());
        this.clasificacionDAO = new ClasificacionDAO(clienteDAO.getConexion());
        this.generoDAO = new GeneroDAO(clienteDAO.getConexion());
        this.funcionDAO = new FuncionDAO(clienteDAO.getConexion());
        this.reporteDAO = new ReporteDAO(clienteDAO.getConexion());
        this.sucursalesDAO = new SucursalesDAO(clienteDAO.getConexion());
        this.boletoDAO = new BoletoDAO(clienteDAO.getConexion());
    }

    public ClienteNegocio() {
        this.clienteDAO = new ClienteDAO();
        this.peliculaDAO = new PeliculaDAO(clienteDAO.getConexion());
        this.clasificacionDAO = new ClasificacionDAO(clienteDAO.getConexion());
        this.generoDAO = new GeneroDAO(clienteDAO.getConexion());
        this.funcionDAO = new FuncionDAO(clienteDAO.getConexion());
        this.reporteDAO = new ReporteDAO(clienteDAO.getConexion());
        this.sucursalesDAO = new SucursalesDAO(clienteDAO.getConexion());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void obtenerIdClienteLogiado() throws cinepolisException {
        this.obtenerClientePorID(id);
    }

    @Override
    public ClienteDTO registro(ClienteDTO cliente) {
        Cliente clienteAuxiliar = null;
        try
        {
            clienteAuxiliar = convertirAEntidad(cliente);

            return convertirAEntidad(clienteDAO.insertarCliente(clienteAuxiliar));

        } catch (SQLException ex)
        {
            Logger.getLogger(ClienteNegocio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (cinepolisException ex)
        {
            Logger.getLogger(ClienteNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public ClienteDTO login(ClienteDTO cliente) {

        Cliente clienteAuxiliar = null;
        try
        {
            clienteAuxiliar = convertirAEntidadSinId(cliente);

            return convertirAEntidad(clienteDAO.login(clienteAuxiliar));

        } catch (SQLException ex)
        {
            Logger.getLogger(ClienteNegocio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (cinepolisException ex)
        {
            Logger.getLogger(ClienteNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public Cliente convertirAEntidad(ClienteDTO cliente) throws SQLException {
        Long id = cliente.getId();
        String nombre = cliente.getNombre();
        String paterno = cliente.getApellidoPaterno();
        String materno = cliente.getApellidoMaterno();
        String correo = cliente.getCorreo();
        String contrasena = cliente.getContrasena();
        String ubicacion = cliente.getUbicacion();
        Date fechaN = cliente.getFechaNacimiento();

        Ciudad ciudad = convertirCiudadAEntidad(cliente.getCiudad());

        return new Cliente(id, nombre, paterno, materno, correo, contrasena, ubicacion, fechaN, ciudad);
    }

    public Ciudad convertirCiudadAEntidad(CiudadDTO ciudadDTO) {
        Long id = ciudadDTO.getId();
        String nombre = ciudadDTO.getNombre();
        String localizacion = ciudadDTO.getLocalizacion();

        return new Ciudad(id, nombre, localizacion);
    }

    public Cliente convertirAEntidadSinId(ClienteDTO cliente) throws SQLException {
        String nombre = cliente.getNombre();
        String paterno = cliente.getApellidoPaterno();
        String materno = cliente.getApellidoMaterno();
        String correo = cliente.getCorreo();
        String contrasena = cliente.getContrasena();
        String ubicacion = cliente.getUbicacion();
        Date fechaN = cliente.getFechaNacimiento();

        Ciudad ciudad = convertirCiudadAEntidad(cliente.getCiudad());

        return new Cliente(nombre, paterno, materno, correo, contrasena, ubicacion, fechaN, ciudad);
    }

    public Pelicula convertirAEntidad(PeliculaDTO peliculaDTO) throws SQLException, cinepolisException {

        String titulo = peliculaDTO.getTitulo();
        int idGenero = obtenerIdGenero(peliculaDTO.getGenero());
        String sinopsis = peliculaDTO.getSinopsis();
        int idClasificacion = obtenerIdClasificacion(peliculaDTO.getClasificacion());
        double duracion = peliculaDTO.getDuracion();
        String pais = peliculaDTO.getPais();
        String trailer = peliculaDTO.getTrailer();

        return new Pelicula(titulo, sinopsis, trailer, duracion, pais, idGenero, idClasificacion);
    }

    public Pelicula convertirAEntidadEd(PeliculaDTO peliculaDTO) throws SQLException, cinepolisException {

        long id = peliculaDTO.getId();
        String titulo = peliculaDTO.getTitulo();
        int idGenero = obtenerIdGenero(peliculaDTO.getGenero());
        String sinopsis = peliculaDTO.getSinopsis();
        int idClasificacion = obtenerIdClasificacion(peliculaDTO.getClasificacion());
        double duracion = peliculaDTO.getDuracion();
        String pais = peliculaDTO.getPais();
        String trailer = peliculaDTO.getTrailer();

        return new Pelicula((int) id, titulo, sinopsis, trailer, duracion, pais, idGenero, idClasificacion);
    }

    public CiudadDTO convertirCiudadADTO(Ciudad ciudad) {
        Long id = ciudad.getId();
        String nombre = ciudad.getNombre();
        String localizacion = ciudad.getLocalizacion();

        return new CiudadDTO(id, nombre, localizacion);
    }

    @Override
    public ClienteDTO convertirAEntidad(Cliente cliente) throws SQLException {
        Long id = cliente.getId();
        String nombre = cliente.getNombre();
        String paterno = cliente.getApellidoPaterno();
        String materno = cliente.getApellidoMaterno();
        String correo = cliente.getCorreo();
        String contrasena = cliente.getContrasena();
        String ubicacion = cliente.getUbicacion();
        Date fechaN = cliente.getFechaNacimiento();

        Ciudad ciudad = cliente.getCiudad();
        CiudadDTO ciudadDTO = convertirCiudadADTO(ciudad);

        return new ClienteDTO(id, nombre, paterno, materno, correo, contrasena, ubicacion, fechaN, ciudadDTO);
    }

    @Override
    public PeliculaDTO convertirAEntidad(Pelicula pelicula) throws SQLException {
        PeliculaDTO peliculaDTO = new PeliculaDTO();

        peliculaDTO.setTitulo(pelicula.getTitulo());
        peliculaDTO.setSinopsis(pelicula.getSinopsis());
        peliculaDTO.setTrailer(pelicula.getTrailer());
        peliculaDTO.setDuracion(pelicula.getDuracion());
        peliculaDTO.setPais(pelicula.getPais());

        try
        {
            String genero = obtenerTipoGeneroPorID(pelicula.getGenero());
            peliculaDTO.setGenero(genero);
        } catch (cinepolisException ex)
        {
            System.out.println("Error al obtener el tipo de género: " + ex.getMessage());
        }

        try
        {
            String clasificacion = obtenerTipoClasificacionPorID(pelicula.getClasificacion());
            peliculaDTO.setClasificacion(clasificacion);
        } catch (cinepolisException ex)
        {
            System.out.println("Error al obtener el tipo de clasificación: " + ex.getMessage());
        }

        return peliculaDTO;

    }

    public PeliculaDTO convertirAEntidadEd(Pelicula pelicula) throws SQLException {
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setId((long) pelicula.getId());
        peliculaDTO.setTitulo(pelicula.getTitulo());
        peliculaDTO.setSinopsis(pelicula.getSinopsis());
        peliculaDTO.setTrailer(pelicula.getTrailer());
        peliculaDTO.setDuracion(pelicula.getDuracion());
        peliculaDTO.setPais(pelicula.getPais());

        try
        {
            String genero = obtenerTipoGeneroPorID(pelicula.getGenero());
            peliculaDTO.setGenero(genero);
        } catch (cinepolisException ex)
        {
            System.out.println("Error al obtener el tipo de género: " + ex.getMessage());
        }

        try
        {
            String clasificacion = obtenerTipoClasificacionPorID(pelicula.getClasificacion());
            peliculaDTO.setClasificacion(clasificacion);
        } catch (cinepolisException ex)
        {
            System.out.println("Error al obtener el tipo de clasificación: " + ex.getMessage());
        }

        return peliculaDTO;
    }

    public FuncionDTO convertirAEntidad(Funcion funcion) throws SQLException {
        FuncionDTO funcionDTO = new FuncionDTO();
        funcionDTO.setId((long) funcion.getId());
        funcionDTO.setHoraInicio(funcion.getHoraInicio());
        funcionDTO.setPeliculaDTO(convertirAEntidadEd(funcion.getPeliculas()));

        return funcionDTO;
    }

    public Funcion convertirAEntidad(FuncionDTO funcionDTO) throws SQLException, cinepolisException {

        System.out.println("Antes pero en el metodo de ConvertirAEntidad: " + (this.convertirAEntidadEd(funcionDTO.getPeliculaDTO()).getId()));

        Funcion funcion = new Funcion();
        Date fecha = new Date();
        fecha = funcionDTO.getFecha();
        funcion.setFecha(fecha);
        Double hora;
        hora = funcionDTO.getHoraInicio();
        funcion.setHoraInicio(hora);
        Pelicula p = new Pelicula();
        p = convertirAEntidadEd(funcionDTO.getPeliculaDTO());
        funcion.setPeliculas(p);

        System.out.println("ConvertirAEntidad: " + funcion.getPeliculas().getId() + " " + funcion.getFecha());

        return funcion;
    }

    public Funcion convertirAEntidadED(FuncionDTO funcionDTO) throws SQLException, cinepolisException {

        System.out.println("Antes pero en el metodo de ConvertirAEntidad: " + (this.convertirAEntidadEd(funcionDTO.getPeliculaDTO()).getId()));

        Funcion funcion = new Funcion();
        funcion.setId(funcionDTO.getId().intValue());
        Date fecha = new Date();
        fecha = funcionDTO.getFecha();
        funcion.setFecha(fecha);
        Double hora;
        hora = funcionDTO.getHoraInicio();
        funcion.setHoraInicio(hora);
        Pelicula p = new Pelicula();
        p = convertirAEntidadEd(funcionDTO.getPeliculaDTO());
        funcion.setPeliculas(p);

        System.out.println("ConvertirAEntidad: " + "ID: " + funcion.getId() + " " + funcion.getPeliculas().getId() + " " + funcion.getFecha());

        return funcion;
    }

    @Override
    public List<ClienteDTO> buscarClientesTabla() throws cinepolisException {
        try
        {
            List<Cliente> clientes = this.clienteDAO.buscarClientesTabla();
            return this.convertirClienteTablaDTO(clientes);
        } catch (cinepolisException ex)
        {

            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    private List<ClienteDTO> convertirClienteTablaDTO(List<Cliente> clientes) throws cinepolisException {
        if (clientes == null)
        {
            throw new cinepolisException("No se pudieron obtener los alumnos");
        }

        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes)
        {
            ClienteDTO dto = new ClienteDTO();
            dto.setId(cliente.getId());
            dto.setNombre(cliente.getNombre());
            dto.setApellidoPaterno(cliente.getApellidoPaterno());
            dto.setApellidoMaterno(cliente.getApellidoMaterno());
            dto.setCorreo(cliente.getCorreo());
            dto.setContrasena(cliente.getCorreo());
            dto.setUbicacion(cliente.getUbicacion());
            dto.setFechaNacimiento(cliente.getFechaNacimiento());
            clientesDTO.add(dto);
        }
        return clientesDTO;
    }

    @Override
    public ClienteDTO obtenerClientePorID(long id) throws cinepolisException {
        try
        {

            ClienteDTO cliente = clienteDAO.obtenerClientePorID(id);

            if (cliente == null)
            {
                throw new cinepolisException("No se encontró ningún cliente con el ID proporcionado.");
            }

            return cliente;
        } catch (cinepolisException ex)
        {
            throw new cinepolisException("Error al obtener el cliente por ID.", ex);
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws cinepolisException {
        try
        {

            List<ClienteDTO> clientes = clienteDAO.obtenerTodosLosClientes();

            if (clientes == null || clientes.isEmpty())
            {
                throw new cinepolisException("No se encontraron clientes en la base de datos.");
            }

            return clientes;
        } catch (cinepolisException ex)
        {

            throw new cinepolisException("Error al obtener todos los clientes.", ex);
        }
    }

    @Override
    public ClienteDTO editarCliente(ClienteDTO cliente) throws cinepolisException {
        try
        {
            Cliente clienteEntidad = convertirAEntidad(cliente);

            Cliente clienteEditado = clienteDAO.editarCliente(clienteEntidad);

            return convertirAEntidad(clienteEditado);
        } catch (SQLException ex)
        {
            throw new cinepolisException("Error al editar el cliente en la base de datos.", ex);
        }
    }

    @Override
    public ClienteDTO eliminarCliente(long idCliente) throws cinepolisException {
        try
        {
            ClienteDTO cliente = new ClienteDTO();
            cliente = convertirAEntidad(clienteDAO.eliminarClientePorID(idCliente));
            return cliente;
        } catch (SQLException ex)
        {
            throw new cinepolisException("Error al eliminar el cliente en la base de datos.", ex);
        }
    }

    public void agregarPelicula(PeliculaDTO pelicula) throws SQLException, cinepolisException {
        try
        {
            peliculaDAO.insertarPelicula(convertirAEntidad(pelicula));
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public int obtenerIdGenero(String generoSeleccionado) throws SQLException, cinepolisException {

        long id = generoDAO.obtenerIdPorNombre(generoSeleccionado);
        return (int) id;

    }

    public int obtenerIdClasificacion(String clasificacionSeleccionada) throws SQLException, cinepolisException {

        long id = clasificacionDAO.obtenerIdPorNombre(clasificacionSeleccionada);
        return (int) id;
    }

    @Override
    public void agregarCliente(ClienteDTO cliente) throws SQLException, cinepolisException {
        try
        {
            clienteDAO.insertarCliente(this.convertirAEntidad(cliente));
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<ClasificacionDTO> obtenerTodasLasClasificaciones() {

        List<ClasificacionDTO> clasificacionesDTO = new ArrayList<>();
        List<Clasificacion> clasificaciones = clasificacionDAO.obtenerTodos();
        for (Clasificacion clasificacion : clasificaciones)
        {
            ClasificacionDTO clasificacionDTO = new ClasificacionDTO();
            clasificacionDTO.setId(clasificacion.getId());
            clasificacionDTO.setTipo(clasificacion.getNombre());
            clasificacionesDTO.add(clasificacionDTO);
        }
        return clasificacionesDTO;
    }

    @Override
    public List<GeneroDTO> obtenerTodosLosGeneros() {

        try
        {
            List<GeneroDTO> generosDTO = new ArrayList<>();
            List<Genero> generos = generoDAO.obtenerTodos();
            for (Genero genero : generos)
            {
                GeneroDTO generoDTO = new GeneroDTO();
                generoDTO.setId(genero.getId());
                generoDTO.setTipo(genero.getNombre());
                generosDTO.add(generoDTO);
            }
            return generosDTO;
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public PeliculaDTO obtenerPeliculaPorID(long id) throws cinepolisException {
        try
        {

            PeliculaDTO pelicula = peliculaDAO.obtenerPeliculaPorId(id);

            if (pelicula == null)
            {
                throw new cinepolisException("No se encontró ningún cliente con el ID proporcionado.");
            }

            return pelicula;
        } catch (cinepolisException ex)
        {
            throw new cinepolisException("Error al obtener el cliente por ID.", ex);
        }
    }

    @Override
    public PeliculaDTO eliminarPelicula(long idPelicula) throws cinepolisException {
        PeliculaDTO pelicula = new PeliculaDTO();
        try
        {
            pelicula = convertirAEntidad(peliculaDAO.eliminarPeliculaPorID(idPelicula));
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
        return pelicula;
    }

    public String obtenerTipoGeneroPorID(int id) throws cinepolisException {
        String tipo = generoDAO.buscarPorId(id).getNombre();
        return tipo;
    }

    public String obtenerTipoClasificacionPorID(int id) throws cinepolisException {
        String tipo = clasificacionDAO.buscarPorId(id).getNombre();
        return tipo;
    }

    @Override
    public List<PeliculaDTO> obtenerTodasLasPeliculasTablaDTO() throws cinepolisException {
        try
        {

            List<PeliculaDTO> peliculas = peliculaDAO.obtenerTodasLasPeliculas();

            if (peliculas == null || peliculas.isEmpty())
            {
                throw new cinepolisException("No se encontraron peliculas en la base de datos.");
            }

            return peliculas;
        } catch (cinepolisException ex)
        {

            throw new cinepolisException("Error al obtener todas las peliculas.", ex);
        }
    }//envíame eso por

    @Override
    public List<PeliculaDTO> buscarPeliculasTabla() throws cinepolisException {
        try
        {

            List<Pelicula> peliculas = this.peliculaDAO.buscarPeliculasTabla();
            return this.convertirPeliculaTablaDTO(peliculas);
        } catch (SQLException ex)
        {

            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    private List<PeliculaDTO> convertirPeliculaTablaDTO(List<Pelicula> peliculas) throws cinepolisException, SQLException, SQLException {
        if (peliculas == null)
        {
            throw new cinepolisException("No se pudieron obtener los alumnos");
        }

        List<PeliculaDTO> PeliculasDTO = new ArrayList<>();
        for (Pelicula pelicula : peliculas)
        {
            PeliculaDTO peliculaDTO = new PeliculaDTO();
            peliculaDTO.setId((long) pelicula.getId());
            peliculaDTO.setTitulo(pelicula.getTitulo());
            peliculaDTO.setSinopsis(pelicula.getSinopsis());
            peliculaDTO.setTrailer(pelicula.getTrailer());
            peliculaDTO.setDuracion(pelicula.getDuracion());
            peliculaDTO.setPais(pelicula.getPais());
            peliculaDTO.setGenero(obtenerTipoGeneroPorID(pelicula.getGenero()));
            peliculaDTO.setClasificacion(obtenerTipoClasificacionPorID(pelicula.getClasificacion()));
            PeliculasDTO.add(peliculaDTO);
        }
        return PeliculasDTO;
    }

    private List<Pelicula> convertirPeliculaTabla(List<PeliculaDTO> peliculasDTO) throws cinepolisException {
        if (peliculasDTO == null)
        {
            throw new cinepolisException("No se pudieron obtener las películas");
        }

        List<Pelicula> peliculas = new ArrayList<>();
        for (PeliculaDTO peliculaDTO : peliculasDTO)
        {
            Pelicula pelicula = new Pelicula();
            pelicula.setId(peliculaDTO.getId().intValue());
            pelicula.setTitulo(peliculaDTO.getTitulo());
            pelicula.setSinopsis(peliculaDTO.getSinopsis());
            pelicula.setTrailer(peliculaDTO.getTrailer());
            pelicula.setDuracion(peliculaDTO.getDuracion());
            pelicula.setPais(peliculaDTO.getPais());
            try
            {
                int idGenero = obtenerIdGenero(peliculaDTO.getGenero());
                int idClasificacion = obtenerIdClasificacion(peliculaDTO.getClasificacion());
                pelicula.setGenero(idGenero);
                pelicula.setClasificacion(idClasificacion);
            } catch (SQLException ex)
            {
                throw new cinepolisException("Error al obtener el ID de género o clasificación.", ex);
            }
            peliculas.add(pelicula);
        }
        return peliculas;
    }

    @Override
    public PeliculaDTO editarPelicula(PeliculaDTO pelicula) throws cinepolisException {
        try
        {
            Pelicula peliculaEntidad = convertirAEntidadEd(pelicula);

            Pelicula peliculaEditado = peliculaDAO.editarPelicula(peliculaEntidad);

            return convertirAEntidad(peliculaEditado);
        } catch (SQLException ex)
        {
            throw new cinepolisException("Error al editar el cliente en la base de datos.", ex);
        }
    }

    public List<ClienteDTO> buscarClientes(String nombreFiltro, java.sql.Date fechaInicio, java.sql.Date fechaFin, String ciudadFiltro) throws cinepolisException {
        try
        {
            List<Cliente> clientes = clienteDAO.buscarClientesConFiltros(nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);
            return convertirClienteTablaDTO(clientes);
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    public List<PeliculaDTO> buscarPeliculas(String titulo, String genero, String clasificacion, String pais) throws cinepolisException, SQLException {
        try
        {
            System.out.println("CinepolisBO " + genero + " " + clasificacion);
            List<PeliculaDTO> peliculas = peliculaDAO.buscarPeliculasConFiltros(titulo, genero, clasificacion, pais);
            return peliculas;
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    public FuncionDTO obtenerFuncionPorId(long id) throws cinepolisException, SQLException {
        try
        {

            FuncionDTO funcion = convertirAEntidad(funcionDAO.buscarPorId((int) id));

            if (funcion == null)
            {
                throw new cinepolisException("No se encontró ningún cliente con el ID proporcionado.");
            }

            return funcion;
        } catch (cinepolisException ex)
        {
            throw new cinepolisException("Error al obtener el cliente por ID.", ex);
        }
    }

    @Override
    public List<FuncionDTO> buscarFuncionesTabla() throws cinepolisException {
        try
        {
            List<Funcion> funciones = this.funcionDAO.buscarFuncionesTabla();
            return this.convertirFuncionTablaDTO(funciones);
        } catch (cinepolisException ex)
        {

            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    private List<FuncionDTO> convertirFuncionTablaDTO(List<Funcion> funciones) throws cinepolisException {
        if (funciones == null)
        {
            throw new cinepolisException("No se pudieron obtener los alumnos");
        }

        List<FuncionDTO> funcionesDTO = new ArrayList<>();
        for (Funcion funcion : funciones)
        {

            try
            {
                FuncionDTO funcionDTO = new FuncionDTO();
                funcionDTO.setId((long) funcion.getId());
                funcionDTO.setHoraInicio(funcion.getHoraInicio());
                funcionDTO.setFecha(funcion.getFecha());
                funcionDTO.setPeliculaDTO(convertirAEntidad(funcion.getPeliculas()));

                funcionesDTO.add(funcionDTO);
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return funcionesDTO;
    }

    public PeliculaDTO buscarPeliculaPorTitulo(String titulo) throws cinepolisException, SQLException {

        try
        {
            PeliculaDTO pelicula = peliculaDAO.buscarPeliculaConTitulo(titulo);
            return pelicula;
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    public void agregarFuncion(FuncionDTO funcion) throws SQLException, cinepolisException {
        try
        {
            System.out.println("AgregarFuncion en CinepolisBO: " + funcion.getPeliculaDTO().getId());
            System.out.println("AgregarFuncion en CinepolisBO: " + funcion.getFecha());
            System.out.println("AgregarFuncion en CinepolisBO: " + this.convertirAEntidad(funcion).getPeliculas().getId());

            funcionDAO.insertarFuncion(this.convertirAEntidad(funcion));

            System.out.println("cinepolisBO" + convertirAEntidad(funcion).getPeliculas().getId() + " " + funcion.getFecha());
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public FuncionDTO editarFuncion(FuncionDTO funcion) throws cinepolisException {
        try
        {
            Funcion FuncionEntidad = convertirAEntidadED(funcion);

            Funcion FuncionEditado = funcionDAO.editarFuncion(FuncionEntidad);

            return convertirAEntidad(FuncionEntidad);
        } catch (SQLException ex)
        {
            throw new cinepolisException("Error al editar el cliente en la base de datos.", ex);
        }
    }

    public FuncionDTO eliminarFuncion(long idFuncion) throws cinepolisException, SQLException {
        FuncionDTO funcion = new FuncionDTO();
        try
        {
            funcion = convertirAEntidad(funcionDAO.eliminarFuncionPorId((int) idFuncion));
            return funcion;
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            throw new cinepolisException(ex.getMessage());
        }
    }

    public Reporte generarReporte(String ciudad, int peliculaId, int generoId, String fechaInicio, String fechaFin) {
        double gananciasPelicula = gananciasTotalesPorPelicula(ciudad, peliculaId, generoId, fechaInicio, fechaFin);

        Reporte reporte = new Reporte();
        reporte.setSucursal(ciudad);
        reporte.setPeliculaId(peliculaId);
        reporte.setGeneroId(generoId);

        reporteDAO.generarReporte(reporte, 2);

        return reporte;
    }

    public double gananciasTotalesPorPelicula(String ciudad, int peliculaId, int generoId, String fechaInicio, String fechaFin) {
        return reporteDAO.gananciasTotalesPorPelicula(ciudad, peliculaId, generoId, fechaInicio, fechaFin);
    }

    public List<SucursalDTO> obtenerCoordenadasSucursales() throws SQLException {
        List<SucursalDTO> sucursales = new ArrayList<>();
        List<String> nombresSucursales = obtenerNombresSucursales();

        for (String nombre : nombresSucursales)
        {
            List<Point2D.Double> coordenadas = obtenerCoordenadasSucursalesConNombreSucursal(nombre);
            if (coordenadas != null && !coordenadas.isEmpty())
            {
                // Aquí puedes asumir que las coordenadas están asociadas al primer resultado
                sucursales.add(new SucursalDTO(nombre, coordenadas.get(0)));
            }
        }

        return sucursales;
    }

    public List<Point2D.Double> obtenerCoordenadasSucursalesConNombreSucursal(String nombre) {
        return (List<Point2D.Double>) sucursalesDAO.obtenerCoordenadas(nombre);
    }

    public Point2D.Double obtenerCoordenadasCliente(long id) {
        return clienteDAO.conseguirCoordenas((int) id);
    }

    public List<String> obtenerNombresSucursales() {
        return sucursalesDAO.obtenerNombreSucursales();
    }

    public List<SucursalDTO> obtenerSucursales() {
        try
        {
            return sucursalesDAO.obtenerSucursales();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<String> obtenerTitulosPeliculasPorGeneroYSucursal(String generoSeleccionado, String sucursalSeleccionada) {
        try
        {
            // Utiliza el DAO para obtener los títulos de películas
            return peliculaDAO.obtenerTitulosPorGeneroYSucursal(generoSeleccionado, sucursalSeleccionada);
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<PeliculaDTO> PeliculasPOrSucursal(String nombreSucursal) throws SQLException {
        List<PeliculaDTO> peliculas = new ArrayList<>();

        List<Pelicula> peliculasAux = peliculaDAO.PeliculasPOrSucursal(nombreSucursal);

        for (Pelicula pelicula : peliculasAux)
        {
            peliculas.add(convertirAEntidad(pelicula));
        }
        return peliculas;
    }

    public List<PeliculaDTO> TodasLasPeliculas() throws SQLException {
        List<PeliculaDTO> peliculas = new ArrayList<>();

        List<Pelicula> peliculasAux = peliculaDAO.TodasLasPeliculas();

        for (Pelicula pelicula : peliculasAux)
        {
            peliculas.add(convertirAEntidad(pelicula));
        }
        return peliculas;
    }

    public SucursalDTO obtenerSucursalMasCercana(Point2D.Double ubicacionUsuario) throws SQLException {
        List<SucursalDTO> sucursales = sucursalesDAO.obtenerSucursales();
        SucursalDTO sucursalMasCercana = null;
        double menorDistancia = Double.MAX_VALUE;

        for (SucursalDTO sucursal : sucursales)
        {
            double distancia = calcularDistancia(ubicacionUsuario, sucursal.getCoordenadas());
            if (distancia < menorDistancia)
            {
                menorDistancia = distancia;
                sucursalMasCercana = new SucursalDTO(sucursal.getCoordenadas(), sucursal.getNombre());
            }
        }

        return sucursalMasCercana;
    }

    private double calcularDistancia(Point2D.Double p1, Point2D.Double p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<PeliculaDTO> obtenerDatosPeliculasPorGeneroYSucursal(String generoSeleccionado, String sucursalSeleccionada) {
        try
        {
            List<PeliculaDTO> p;
            p = peliculaDAO.obtenerDatosPorGeneroYSucursal(generoSeleccionado, sucursalSeleccionada);

            return p;
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void insertarBoleto(BoletoDTO boleto) {
        try
        {
            boletoDAO.insertarBoletoComprado(convertirBoletoDTOADAO(boleto));
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private Boleto convertirBoletoDTOADAO(BoletoDTO boleto) {
        Boleto boletoDAO = new Boleto();

        // Asignar los valores del BoletoDTO al BoletoDAO
        boletoDAO.setCosto(boleto.getCosto());
        boletoDAO.setEstado(true);
        boletoDAO.setFechaCompra(boleto.getFechaCompra());
        boletoDAO.setCliente(boleto.getCliente());
        boletoDAO.setFuncion(boleto.getFuncion());

        return boletoDAO;
    }

    public FuncionDTO obtenerIdFuncionPorSucursalYPelicula(String sucursal, Long id) {
        try
        {
            FuncionDTO f = this.obtenerFuncionPorId(funcionDAO.obtenerIdFuncionPorSucursalYPelicula(sucursal, id).getId());
            return f;
        } catch (cinepolisException ex)
        {
            System.out.println(ex.getMessage());
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public SalaDTO obtenerSalaFuncionSucursal(String sucursal, FuncionDTO f) {
        return funcionDAO.obtenerSalaFuncionSucursal(sucursal, f.getId());
    }

}
