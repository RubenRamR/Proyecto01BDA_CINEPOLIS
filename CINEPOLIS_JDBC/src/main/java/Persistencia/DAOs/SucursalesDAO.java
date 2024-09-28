/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Negocio.DTOs.SucursalDTO;
import com.itextpdf.awt.geom.Point2D;
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
public class SucursalesDAO implements ISucursalesDAO {

    private IConexionBD conexionBD;

    public SucursalesDAO() {
        this.conexionBD = new ConexionBD();
    }

    public SucursalesDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public IConexionBD getConexion() {
        return conexionBD;
    }

    public double[] obtenerCoordenadasSucursal(String nombreSucursal) {
        double[] coordenadas = null;
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("SELECT ubicacion FROM sucursales WHERE nombre = ?"))
        {
            pstmt.setString(1, nombreSucursal);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    String coordenadasStr = rs.getString("ubicacion");
                    String[] coordenadasArray = coordenadasStr.split(",");
                    coordenadas = new double[coordenadasArray.length];
                    for (int i = 0; i < coordenadasArray.length; i++)
                    {
                        coordenadas[i] = Double.parseDouble(coordenadasArray[i]);
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return coordenadas;
    }

    public List<Point2D.Double> obtenerCoordenadasSucursales() throws SQLException {
        List<Point2D.Double> coordenadasSucursales = new ArrayList<>();
        String consulta = "SELECT ST_AsText(ubicacion) AS coordenadas FROM sucursales";
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next())
            {
                String wkt = rs.getString("coordenadas");
                Point2D.Double coordenadas = parsearWKT(wkt);
                coordenadasSucursales.add(coordenadas);
            }
        }
        return coordenadasSucursales;
    }

    private Point2D.Double parsearWKT(String wkt) {
        Point2D.Double coordenadas = null;
        try
        {
            // Eliminar el prefijo "POINT" y los paréntesis del texto WKT
            String coordenadasStr = wkt.replace("", "").replace("", "");

            // Separar las coordenadas en X y Y
            String[] partes = coordenadasStr.split(" ");
            double x = Double.parseDouble(partes[0]);
            double y = Double.parseDouble(partes[1]);

            coordenadas = new Point2D.Double(x, y);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        return coordenadas;
    }

    public Point2D.Double obtenerCoordenadasa(String coordenadasStr) {
        coordenadasStr = coordenadasStr.replace("POINT (", "").replace(")", ""); // Elimina el texto adicional y los paréntesis
        String[] coordenadasArray = coordenadasStr.split(" ");
        double x = Double.parseDouble(coordenadasArray[0]);
        double y = Double.parseDouble(coordenadasArray[1]);
        return new Point2D.Double(x, y);
    }

    /**
     *
     * @param nombreSucursal
     * @return
     */
    @Override
    public Point2D.Double obtenerCoordenadas(String nombreSucursal) {
        Point2D.Double coordenadas = null;
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("SELECT ubicacion FROM sucursales WHERE nombre = ?"))
        {
            pstmt.setString(1, nombreSucursal);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    String coordenadasStr = rs.getString("ubicacion");
                    String[] coordenadasArray = coordenadasStr.split(",");
                    double x = Double.parseDouble(coordenadasArray[0]);
                    double y = Double.parseDouble(coordenadasArray[1]);
                    coordenadas = new Point2D.Double(x, y);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return coordenadas;
    }

    public List<String> obtenerNombreSucursales() {
        List<String> nombresSucursales = new ArrayList<>();
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("SELECT nombre FROM sucursales"); ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next())
            {
                nombresSucursales.add(rs.getString("nombre"));
            }
        } catch (SQLException e)
        {
            // Manejo de excepciones
            e.printStackTrace();
        }
        return nombresSucursales;
    }

    public List<SucursalDTO> obtenerSucursales() throws SQLException {
        List<SucursalDTO> sucursales = new ArrayList<>();
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("SELECT idSucursal,nombre,ST_AsText(ubicacion) AS ubicacion FROM sucursales"); ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next())
            {
                // Obtener los datos de la sucursal y crear un objeto SucursalDTO
                SucursalDTO sucursal = new SucursalDTO();
                sucursal.setId(rs.getLong("idSucursal"));
                sucursal.setNombre(rs.getString("nombre"));
                String wkt = rs.getString("ubicacion");
                Point2D.Double coordenadas = parsearWKT(wkt);
                sucursal.setCoordenadas(coordenadas);
                sucursales.add(sucursal);
            }
        }
        return sucursales;
    }

}
