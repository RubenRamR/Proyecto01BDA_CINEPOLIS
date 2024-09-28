/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import Persistencia.DAOs.FuncionDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author stae
 */
public class ReportePeliculaPDF {
    
    public static void reportePeliculaPDF(BoletoDTO boleto, SucursalDTO sucursal, PeliculaDTO pelicula, SalaDTO sala, int cantidad) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            contentStream.showText("Cinepolis - Reporte Ganancias Por Pelicula");
            contentStream.newLine();
            contentStream.newLine();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaCompra = sdf.format(boleto.getFechaCompra());

            contentStream.showText("Sucursal: " + sucursal.getNombre());
            contentStream.newLine();
            contentStream.showText("Cantidad funciones: " + sala);
            contentStream.newLine();
            contentStream.showText("Fecha de Compra: " + fechaCompra);
            contentStream.newLine();
            contentStream.showText("Fecha: " + cantidad);
            contentStream.newLine();
            contentStream.showText(" Total ganancias por fecha: $" + cantidad);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Total ganancias: $" + cantidad);
            contentStream.newLine();
            contentStream.newLine();

            contentStream.endText();
        }

        document.save("ReportePeliculas.pdf");
        document.close();
    }

    public int cantFunciones() {
        int total = 0;
        List<ReportePeliculaPDF> funciones = new ArrayList<>();
        FuncionDAO funcionDAO;
        //funciones = funcionDAO.obtenerFunciones();
        
        for (int i = 0; i < 10; i++) {
            
            total++;
        }
        
        
        return total;
    }
    
}
