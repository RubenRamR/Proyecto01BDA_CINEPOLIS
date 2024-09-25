/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import javax.swing.JFrame;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author rramirez
 */
public class Registro extends javax.swing.JFrame {

    /**
     * Creates new form InicioSesion
     */
    public Registro() {
        initComponents();
        this.cargarMetodosIniciales();
    }

    public void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialPantalla();
    }

    public void cargarConfiguracionInicialPantalla() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        LblCorreo = new javax.swing.JLabel();
        LblRegistro = new javax.swing.JLabel();
        TxtCorreo = new javax.swing.JTextField();
        LblCorreo1 = new javax.swing.JLabel();
        TxtContraseña = new javax.swing.JPasswordField();
        BtnAceptar = new javax.swing.JButton();
        LblNombre = new javax.swing.JLabel();
        TxtNombres = new javax.swing.JTextField();
        LblApPaterno = new javax.swing.JLabel();
        TxtApPaterno = new javax.swing.JTextField();
        TxtApMaterno1 = new javax.swing.JTextField();
        LblApMaterno1 = new javax.swing.JLabel();
        LblSexo = new javax.swing.JLabel();
        CmbxSexo = new javax.swing.JComboBox<>();
        LblFechaNacimiento = new javax.swing.JLabel();
        DatePickerFechaNacimiento = new com.github.lgooddatepicker.components.DatePicker();
        LblCiudad = new javax.swing.JLabel();
        TxtCiudad = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BtnAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        LblCorreo.setBackground(new java.awt.Color(0, 0, 0));
        LblCorreo.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        LblCorreo.setForeground(new java.awt.Color(0, 0, 0));
        LblCorreo.setText("Correo*");

        LblRegistro.setBackground(new java.awt.Color(0, 0, 0));
        LblRegistro.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LblRegistro.setForeground(new java.awt.Color(0, 0, 0));
        LblRegistro.setText("REGISTRO");

        TxtCorreo.setBackground(new java.awt.Color(255, 255, 255));
        TxtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TxtCorreo.setForeground(new java.awt.Color(0, 0, 0));
        TxtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCorreoActionPerformed(evt);
            }
        });

        LblCorreo1.setBackground(new java.awt.Color(0, 0, 0));
        LblCorreo1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        LblCorreo1.setForeground(new java.awt.Color(0, 0, 0));
        LblCorreo1.setText("Contraseña*");

        TxtContraseña.setBackground(new java.awt.Color(255, 255, 255));
        TxtContraseña.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TxtContraseña.setForeground(new java.awt.Color(0, 0, 0));
        TxtContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtContraseñaActionPerformed(evt);
            }
        });

        BtnAceptar.setBackground(new java.awt.Color(255, 255, 255));
        BtnAceptar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnAceptar.setForeground(new java.awt.Color(0, 0, 153));
        BtnAceptar.setText("Aceptar");
        BtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAceptarActionPerformed(evt);
            }
        });

        LblNombre.setBackground(new java.awt.Color(0, 0, 0));
        LblNombre.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblNombre.setForeground(new java.awt.Color(0, 0, 0));
        LblNombre.setText("Nombre(s)*");

        TxtNombres.setBackground(new java.awt.Color(255, 255, 255));
        TxtNombres.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TxtNombres.setForeground(new java.awt.Color(0, 0, 0));
        TxtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombresActionPerformed(evt);
            }
        });

        LblApPaterno.setBackground(new java.awt.Color(0, 0, 0));
        LblApPaterno.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblApPaterno.setForeground(new java.awt.Color(0, 0, 0));
        LblApPaterno.setText("Apellido Paterno*");

        TxtApPaterno.setBackground(new java.awt.Color(255, 255, 255));
        TxtApPaterno.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TxtApPaterno.setForeground(new java.awt.Color(0, 0, 0));
        TxtApPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtApPaternoActionPerformed(evt);
            }
        });

        TxtApMaterno1.setBackground(new java.awt.Color(255, 255, 255));
        TxtApMaterno1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TxtApMaterno1.setForeground(new java.awt.Color(0, 0, 0));
        TxtApMaterno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtApMaterno1ActionPerformed(evt);
            }
        });

        LblApMaterno1.setBackground(new java.awt.Color(0, 0, 0));
        LblApMaterno1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblApMaterno1.setForeground(new java.awt.Color(0, 0, 0));
        LblApMaterno1.setText("Apellido Materno*");

        LblSexo.setBackground(new java.awt.Color(0, 0, 0));
        LblSexo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblSexo.setForeground(new java.awt.Color(0, 0, 0));
        LblSexo.setText("Sexo*");

        CmbxSexo.setBackground(new java.awt.Color(255, 255, 255));
        CmbxSexo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        CmbxSexo.setForeground(new java.awt.Color(0, 0, 0));
        CmbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hombre", "Mujer" }));
        CmbxSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbxSexoActionPerformed(evt);
            }
        });

        LblFechaNacimiento.setBackground(new java.awt.Color(0, 0, 0));
        LblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblFechaNacimiento.setForeground(new java.awt.Color(0, 0, 0));
        LblFechaNacimiento.setText("Fecha de nacimiento*");

        DatePickerFechaNacimiento.setBackground(new java.awt.Color(255, 255, 255));
        DatePickerFechaNacimiento.setForeground(new java.awt.Color(255, 255, 255));

        LblCiudad.setBackground(new java.awt.Color(0, 0, 0));
        LblCiudad.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblCiudad.setForeground(new java.awt.Color(0, 0, 0));
        LblCiudad.setText("Ciudad*");

        TxtCiudad.setBackground(new java.awt.Color(255, 255, 255));
        TxtCiudad.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TxtCiudad.setForeground(new java.awt.Color(0, 0, 0));
        TxtCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCiudadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(BtnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(570, 570, 570))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LblRegistro)
                .addGap(574, 574, 574))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblNombre)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LblCorreo1)
                        .addComponent(TxtCorreo)
                        .addComponent(TxtContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE))
                    .addComponent(TxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblCorreo)
                    .addComponent(TxtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblCiudad)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblApPaterno)
                            .addComponent(TxtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LblSexo)
                            .addComponent(CmbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblFechaNacimiento)
                            .addComponent(LblApMaterno1)
                            .addComponent(TxtApMaterno1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DatePickerFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(LblRegistro)
                .addGap(9, 9, 9)
                .addComponent(LblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LblApPaterno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LblApMaterno1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtApMaterno1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(LblSexo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CmbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(LblCiudad)
                                .addGap(63, 63, 63))
                            .addComponent(TxtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(LblFechaNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DatePickerFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(LblCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblCorreo1)
                .addGap(18, 18, 18)
                .addComponent(TxtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(BtnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\rramirez\\OneDrive\\Escritorio\\BDA_3\\Proyecto01BDA_CINEPOLIS\\Resources\\cinepolis_logo.png")); // NOI18N

        BtnAtras.setBackground(new java.awt.Color(0, 0, 153));
        BtnAtras.setForeground(new java.awt.Color(0, 0, 153));
        BtnAtras.setIcon(new javax.swing.ImageIcon("C:\\Users\\rramirez\\OneDrive\\Escritorio\\BDA_3\\Proyecto01BDA_CINEPOLIS\\Resources\\BtnAtras.png")); // NOI18N
        BtnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(BtnAtras)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAceptarActionPerformed
        // TODO add your handling code here:
        SucursalesCartelera sc = new SucursalesCartelera();
        sc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnAceptarActionPerformed

    private void TxtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCorreoActionPerformed

    private void TxtContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtContraseñaActionPerformed

    private void TxtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombresActionPerformed

    private void TxtApPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtApPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtApPaternoActionPerformed

    private void TxtApMaterno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtApMaterno1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtApMaterno1ActionPerformed

    private void CmbxSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbxSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbxSexoActionPerformed

    private void BtnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAtrasActionPerformed
        // TODO add your handling code here:
        InicioSesion i = new InicioSesion();
        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnAtrasActionPerformed

    private void TxtCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCiudadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAceptar;
    private javax.swing.JButton BtnAtras;
    private javax.swing.JComboBox<String> CmbxSexo;
    private com.github.lgooddatepicker.components.DatePicker DatePickerFechaNacimiento;
    private javax.swing.JLabel LblApMaterno1;
    private javax.swing.JLabel LblApPaterno;
    private javax.swing.JLabel LblCiudad;
    private javax.swing.JLabel LblCorreo;
    private javax.swing.JLabel LblCorreo1;
    private javax.swing.JLabel LblFechaNacimiento;
    private javax.swing.JLabel LblNombre;
    private javax.swing.JLabel LblRegistro;
    private javax.swing.JLabel LblSexo;
    private javax.swing.JTextField TxtApMaterno1;
    private javax.swing.JTextField TxtApPaterno;
    private javax.swing.JTextField TxtCiudad;
    private javax.swing.JPasswordField TxtContraseña;
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JTextField TxtNombres;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
