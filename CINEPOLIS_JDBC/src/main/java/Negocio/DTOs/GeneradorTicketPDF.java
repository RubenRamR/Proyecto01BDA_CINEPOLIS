/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author stae
 */
public class GeneradorTicketPDF {
    
    public static void generarTicketPDF(BoletoDTO boleto, String sucursal, PeliculaDTO pelicula, SalaDTO sala, int cantidad) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            contentStream.showText("Cinepolis - Ticket de Compra");
            contentStream.newLine();
            contentStream.newLine();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaCompra = sdf.format(boleto.getFechaCompra());

            contentStream.showText("Sucursal: " + sucursal);
            contentStream.newLine();
            contentStream.showText("Pelicula: " + pelicula.getTitulo());
            contentStream.newLine();
            contentStream.showText("Fecha de Compra: " + fechaCompra);
            contentStream.newLine();
            contentStream.showText("Cantidad de Boletos: " + cantidad);
            contentStream.newLine();
            contentStream.showText("Costo Total: $" + 50 * cantidad);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Numero de Sala: $" + sala.getNumero());
            contentStream.newLine();
            contentStream.newLine();

            contentStream.endText();
        }

        document.save("Ticket.pdf");
        document.close();
    }
    
    
}
