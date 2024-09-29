/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion.Administrador;

import Negocio.DTOs.CiudadDTO;
import Negocio.DTOs.ClienteDTO;
import Negocio.Negocio.ClienteNegocio;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.ConexionBD;
import com.itextpdf.awt.geom.Point2D;
import excepciones.cinepolisException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author stae
 */
public class AgregarCliente extends javax.swing.JFrame {

    ClienteNegocio clienteNegocio;

    /**
     * Creates new form AgregarCliente
     */
    public AgregarCliente(ClienteNegocio clienteNegocio) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setSize(805, 600);
        this.clienteNegocio = clienteNegocio;
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
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        lblContraseña = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        botonAgregar = new javax.swing.JButton();
        lblContraseña1 = new javax.swing.JLabel();
        txtUbicacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnRegresar)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, -1));

        jLabel1.setFont(new java.awt.Font("Shree Devanagari 714", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Agregar Nuevo Cliente");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre completo:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 150, 20));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 270, 30));

        lblCorreo.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(255, 255, 255));
        lblCorreo.setText("Correo:");
        jPanel1.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, -1, -1));

        txtCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiudadActionPerformed(evt);
            }
        });
        jPanel1.add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 270, 30));

        lblContraseña.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        lblContraseña.setForeground(new java.awt.Color(255, 255, 255));
        lblContraseña.setText("Ubicacion:");
        jPanel1.add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, -1, -1));

        jLabel4.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha de nacimiento:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, -1, -1));
        jPanel1.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 270, 30));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 270, 30));

        botonAgregar.setBackground(new java.awt.Color(12, 33, 63));
        botonAgregar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        botonAgregar.setForeground(new java.awt.Color(255, 255, 255));
        botonAgregar.setText("Agregar Cliente");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(botonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 500, -1, 30));

        lblContraseña1.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        lblContraseña1.setForeground(new java.awt.Color(255, 255, 255));
        lblContraseña1.setText("Contraseña:");
        jPanel1.add(lblContraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, -1, -1));
        jPanel1.add(txtUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 460, 270, 30));

        jLabel5.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ciudad:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, -1, -1));

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, 270, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        CatalogoClientes catalogoClientes = new CatalogoClientes(clienteNegocio);
        catalogoClientes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void txtCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiudadActionPerformed

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarActionPerformed
        String nombreCompleto = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        Date fechaNacimiento = jDateChooser1.getDate();
        String ciudadNombre = txtCiudad.getText().trim(); // Obtener la ciudad

        // Validar campos obligatorios
        if (nombreCompleto.isEmpty() || correo.isEmpty() || contrasena.isEmpty()
                || fechaNacimiento == null || ciudadNombre.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Dividir el nombre completo
        String[] partesNombre = nombreCompleto.split(" ", 3);
        String nombre = partesNombre[0];
        String apellidoPaterno = partesNombre.length > 1 ? partesNombre[1] : "";
        String apellidoMaterno = partesNombre.length > 2 ? partesNombre[2] : "";

        // Crear nuevo cliente
        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setApellidoPaterno(apellidoPaterno);
        nuevoCliente.setApellidoMaterno(apellidoMaterno);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setFechaNacimiento(fechaNacimiento);

        // Obtener y establecer la ubicación
        String ubicacionStr = txtUbicacion.getText().trim();
        String[] coordenadasArray = ubicacionStr.split(",");

        if (coordenadasArray.length != 2)
        {
            JOptionPane.showMessageDialog(this, "Formato de coordenadas incorrecto. Debe ser: latitud,longitud", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            double latitud = Double.parseDouble(coordenadasArray[0].trim());
            double longitud = Double.parseDouble(coordenadasArray[1].trim());
            Point2D.Double ubicacion = new Point2D.Double(latitud, longitud);
            nuevoCliente.setUbicacion(ubicacion);
        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Coordenadas inválidas. Asegúrese de que el formato sea: latitud,longitud", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar y obtener la ciudad por nombre
        CiudadDTO ciudad = clienteNegocio.obtenerCiudadPorNombre(ciudadNombre);
        if (ciudad == null || ciudad.getId() == null)
        {
            JOptionPane.showMessageDialog(this, "Ciudad no válida o no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        nuevoCliente.setCiudad(ciudad); // Asignar la ciudad al cliente

        // Intentar agregar el cliente
        try
        {
            clienteNegocio.agregarCliente(nuevoCliente);
            JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            txtNombre.setText("");
            txtCiudad.setText("");
            txtContrasena.setText("");
            jDateChooser1.setDate(null);
            txtUbicacion.setText(""); // Limpiar campo de ubicación
        } catch (Exception e)
        {
            Logger.getLogger(AgregarCliente.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Error al agregar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonAgregarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

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
            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        ConexionBD conexion = new ConexionBD();
        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        ClienteNegocio clienteNegiocio = new ClienteNegocio(clienteDAO);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarCliente(clienteNegiocio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton btnRegresar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblContraseña1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
