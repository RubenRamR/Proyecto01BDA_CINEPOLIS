/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia.DAOs;

import Persistencia.Entidades.Reporte;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stae
 */
public class ReporteDAO implements IReporteDAO{
    
    
    private IConexionBD conexionBD;

    public ReporteDAO() {
    }

    public ReporteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    
    
    @Override
    public void generarReporte(Reporte reportes, int opcion) {
        Document doc = new Document() {
        };
        try {
            LocalDateTime ahora = LocalDateTime.now();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

            String ahorita = ahora.format(formato);

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Reporte_" + ahorita + ".pdf"));
            doc.open();

            // Título del PDF
            // Fuente grande y en color rojo oscuro      
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, new BaseColor(0, 0, 255));

            Paragraph titulo = new Paragraph("Cinepolis", fontTitulo);
            // Alineación centrada
            titulo.setAlignment(Element.ALIGN_CENTER);
            // Espacio después del título
            titulo.setSpacingAfter(20);

            doc.add(titulo);

            if (opcion == 1) {
                Font fontSubTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(0, 0, 255));
                Paragraph Subtitulo = new Paragraph("Reporte de la sucursal: " + reportes.getSucursal(), fontSubTitulo);
                // Alineación a la izquierda
                Subtitulo.setAlignment(Element.ALIGN_LEFT);
                // Espacio después del título
                Subtitulo.setSpacingAfter(20);
                doc.add(Subtitulo);
            }
            if (opcion == 2) {
                Font fontSubTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(0, 0, 255));
                Paragraph Subtitulo = new Paragraph("Reporte por pelicula", fontSubTitulo);
                // Alineación a la izquierda
                Subtitulo.setAlignment(Element.ALIGN_LEFT);
                // Espacio después del título
                Subtitulo.setSpacingAfter(20);
                doc.add(Subtitulo);
            }
            if (opcion == 3) {
                Font fontSubTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(182, 0, 0));
                Paragraph Subtitulo = new Paragraph("Reporte de tramites por tipo: " + reportes.getTipo(), fontSubTitulo);
                // Alineación a la izquierda
                Subtitulo.setAlignment(Element.ALIGN_LEFT);
                // Espacio después del título
                Subtitulo.setSpacingAfter(20);
                doc.add(Subtitulo);
            }

            PdfPTable tabla = new PdfPTable(5);
            // Ancho de la tabla al 100% de la página
            tabla.setWidthPercentage(100);
            // Espacio antes de la tabla
            tabla.setSpacingBefore(10);

            // Encabezados de la tabla
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            PdfPCell cellHeader = new PdfPCell();
            // Color de fondo blanco
            cellHeader.setBackgroundColor(new BaseColor(255, 255, 255));
            // Alineación centrada
            cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            // Alineación vertical centrada
            cellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
            // Color de borde rojo oscuro
            cellHeader.setBorderColor(new BaseColor(182, 0, 0));
            // Grosor del borde
            cellHeader.setBorderWidth(1);
            cellHeader.setPadding(5);

            cellHeader.setPhrase(new Phrase("Sucursal", fontHeader));
            tabla.addCell(cellHeader);

            cellHeader.setPhrase(new Phrase("Cantidad Funciones", fontHeader));
            tabla.addCell(cellHeader);

            cellHeader.setPhrase(new Phrase("Fecha", fontHeader));
            tabla.addCell(cellHeader);

            cellHeader.setPhrase(new Phrase("Total Ganancias fecha", fontHeader));
            tabla.addCell(cellHeader);

            cellHeader.setPhrase(new Phrase("Total ganancias", fontHeader));

            tabla.addCell(cellHeader);

            // Contenido de la tabla
            double totalSum = 0;
            Font fontContent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

            tabla.addCell(new Phrase(reportes.getSucursal(), fontContent));
            tabla.addCell(new Phrase(reportes.getCantFunciones(), fontContent));
            tabla.addCell(new Phrase(reportes.getTipo(), fontContent));
           

                // Convertir la fecha de Calendar a String
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tabla.addCell(new Phrase(sdf.format(reportes.getFecha().getTime()), fontContent));

            // Convertir el costo de double a String
            tabla.addCell(new Phrase(String.valueOf(reportes.getTotalGFecha()), fontContent));
            tabla.addCell(new Phrase(String.valueOf(reportes.getTotalGanancias()), fontContent));
            //totalSum += reportes.getCosto();

            // celda para el total
            tabla.addCell(new Phrase(" ", fontContent));
            tabla.addCell(new Phrase(" ", fontContent));
            tabla.addCell(new Phrase(" ", fontContent));
            tabla.addCell(new Phrase(" ", fontContent));
            PdfPCell cellTotal = new PdfPCell(new Phrase(String.valueOf(totalSum), fontContent));
            tabla.addCell(cellTotal);

            // Agregar caja alrededor de la tabla
            PdfContentByte cb = writer.getDirectContent();
            // Posición y tamaño de la caja
            if (opcion == 1) {
                cb.rectangle(36, 42, 523, 700 - (reportes.size() * 20 + 40));
            } else {
                cb.rectangle(36, 50, 523, 700 - (reportes.size() * 20 + 40));
            }
            // Color del borde
            cb.setColorStroke(new BaseColor(182, 0, 0));
            // Grosor del borde
            cb.setLineWidth(3);
            cb.stroke();

            doc.add(tabla);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ReporteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }

    public double gananciasTotalesPorPelicula(String ciudad, int peliculaId, int generoId, String fechaInicio, String fechaFin) {
        double gananciasT = 0.0;

        try (Connection conexion = this.conexionBD.crearConexion(); CallableStatement procedimiento = conexion.prepareCall("{CALL ReportePorPelicula(?, ?, ?, ?, ?)}")) {
            procedimiento.setString(1, ciudad);
            procedimiento.setInt(2, peliculaId);
            procedimiento.setInt(3, generoId);
            procedimiento.setDate(4, Date.valueOf(fechaInicio));
            procedimiento.setDate(5, Date.valueOf(fechaFin));
            try (ResultSet resultado = procedimiento.executeQuery()) {
                while (resultado.next()) {
                    gananciasT += resultado.getDouble("totalGanancia");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gananciasT;
    }

    
}
