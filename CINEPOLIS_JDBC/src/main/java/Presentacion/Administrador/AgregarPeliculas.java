/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion.Administrador;

import Negocio.DTOs.ClasificacionDTO;
import Negocio.DTOs.GeneroDTO;
import Negocio.DTOs.PeliculaDTO;
import Negocio.Negocio.ClienteNegocio;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.ConexionBD;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author stae
 */
public class AgregarPeliculas extends javax.swing.JFrame {

    ClienteNegocio clienteNegocio;

    /**
     * Creates new form AgregarPeliculas
     */
    public AgregarPeliculas(ClienteNegocio clienteNegocio) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setSize(805, 600);
        this.clienteNegocio = clienteNegocio;
        cargarClasificaciones();
        cargarGeneros();
    }

    private void cargarGeneros() {
        List<GeneroDTO> generos = clienteNegocio.obtenerTodosLosGeneros();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (GeneroDTO genero : generos)
        {
            model.addElement(genero.getTipo());
        }
        jComboBoxGenero.setModel(model);
    }

    private void cargarClasificaciones() {
        List<ClasificacionDTO> clasificaciones = clienteNegocio.obtenerTodasLasClasificaciones();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (ClasificacionDTO clasificacion : clasificaciones)
        {
            model.addElement(clasificacion.getTipo());
        }
        jComboBoxClasificacion.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnPortada = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxGenero = new javax.swing.JComboBox<>();
        textoTitulo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textSinopsis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxClasificacion = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        textoDuracion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textoPais = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textoLink = new javax.swing.JTextField();
        btnAgregarPelicula = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(12, 33, 63));

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("←");
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(505, 505, 505))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 40));

        jLabel1.setFont(new java.awt.Font("Shree Devanagari 714", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Agregar Nueva Pelicula");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        btnPortada.setBackground(new java.awt.Color(0, 153, 255));
        btnPortada.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnPortada.setForeground(new java.awt.Color(255, 255, 255));
        btnPortada.setText("Agregar Portada");
        btnPortada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPortadaActionPerformed(evt);
            }
        });
        jPanel1.add(btnPortada, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, -1, -1));

        jLabel2.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Titulo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, -1, -1));

        jComboBoxGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGeneroActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, 30));

        textoTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoTituloActionPerformed(evt);
            }
        });
        jPanel1.add(textoTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 240, 30));

        jLabel3.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Genero:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Sinopsis: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, -1, -1));

        textSinopsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSinopsisActionPerformed(evt);
            }
        });
        jPanel1.add(textSinopsis, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 240, 80));

        jLabel5.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clasificacion:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, -1, -1));

        jComboBoxClasificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClasificacionActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 110, 30));

        jLabel6.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Duracion:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, -1, -1));

        textoDuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoDuracionActionPerformed(evt);
            }
        });
        jPanel1.add(textoDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 110, 30));

        jLabel7.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pais:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, -1, -1));
        jPanel1.add(textoPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 150, 30));

        jLabel8.setFont(new java.awt.Font("Shree Devanagari 714", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Link Trailer:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, -1, -1));

        textoLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoLinkActionPerformed(evt);
            }
        });
        jPanel1.add(textoLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 290, 30));

        btnAgregarPelicula.setBackground(new java.awt.Color(0, 153, 255));
        btnAgregarPelicula.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnAgregarPelicula.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPelicula.setText("Agregar Pelicula");
        btnAgregarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPeliculaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 500, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        CatalogoPeliculas catalogoPeliculas = new CatalogoPeliculas(clienteNegocio);
        catalogoPeliculas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnPortadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPortadaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnPortadaActionPerformed

    private void jComboBoxGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxGeneroActionPerformed

    private void textoTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoTituloActionPerformed

    private void textSinopsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSinopsisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSinopsisActionPerformed

    private void jComboBoxClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClasificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClasificacionActionPerformed

    private void textoDuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoDuracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoDuracionActionPerformed

    private void textoLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoLinkActionPerformed

    private void btnAgregarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPeliculaActionPerformed
        String titulo = textoTitulo.getText();
        String genero = (String) jComboBoxGenero.getSelectedItem();
        String sinopsis = textSinopsis.getText();
        String clasificacion = (String) jComboBoxClasificacion.getSelectedItem();
        String duracionStr = textoDuracion.getText();
        String pais = textoPais.getText();
        String linkTrailer = textoLink.getText();

        // Validar que no haya campos vacíos
        if (titulo.isEmpty() || genero.isEmpty() || sinopsis.isEmpty() || clasificacion.isEmpty()
                || duracionStr.isEmpty() || pais.isEmpty() || linkTrailer.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir duración a número
        int duracion;
        try
        {
            duracion = Integer.parseInt(duracionStr);
        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener datos de la UI y crear un DTO
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setTitulo(textoTitulo.getText());
        peliculaDTO.setGenero((String) jComboBoxGenero.getSelectedItem());
        peliculaDTO.setSinopsis(textSinopsis.getText());
        peliculaDTO.setClasificacion((String) jComboBoxClasificacion.getSelectedItem());
        peliculaDTO.setDuracion(Double.parseDouble(textoDuracion.getText()));
        peliculaDTO.setPais(textoPais.getText());
        peliculaDTO.setTrailer(textoLink.getText());

        // Llamar a la capa de negocio para agregar la película
        try
        {
            clienteNegocio.agregarPelicula(peliculaDTO);
            JOptionPane.showMessageDialog(this, "Película agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos después de agregar
            textoTitulo.setText("");
            textSinopsis.setText("");
            textoDuracion.setText("");
            textoPais.setText("");
            textoLink.setText("");
            jComboBoxGenero.setSelectedIndex(0);
            jComboBoxClasificacion.setSelectedIndex(0);
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error al agregar película: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarPeliculaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(AgregarPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(AgregarPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(AgregarPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AgregarPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        ConexionBD conexion = new ConexionBD();
        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarPeliculas(clienteNegocio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPelicula;
    private javax.swing.JButton btnPortada;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> jComboBoxClasificacion;
    private javax.swing.JComboBox<String> jComboBoxGenero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField textSinopsis;
    private javax.swing.JTextField textoDuracion;
    private javax.swing.JTextField textoLink;
    private javax.swing.JTextField textoPais;
    private javax.swing.JTextField textoTitulo;
    // End of variables declaration//GEN-END:variables
}
