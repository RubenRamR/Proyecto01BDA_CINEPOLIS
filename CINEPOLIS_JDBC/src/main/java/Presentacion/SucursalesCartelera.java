/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import Negocio.DTOs.SucursalDTO;
import Negocio.Negocio.ClienteNegocio;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author rramirez
 */
public class SucursalesCartelera extends javax.swing.JFrame {

    ClienteNegocio cine;
    
    /**
     * Creates new form InicioSesion
     */
    public SucursalesCartelera(ClienteNegocio cine) throws SQLException{
        initComponents();
        this.cargarMetodosIniciales();
    }

    public void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialPantalla();
    }

    public void cargarConfiguracionInicialPantalla() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void llenarComboBoxNombreSucursal() throws SQLException {
        // Suponiendo que obtenerSucursales() devuelve una lista de SucursalDTO
        List<SucursalDTO> sucursales = cine.obtenerSucursales();
        // Limpiar ComboBox antes de agregar nuevos elementos
        //comboBoxNombreSucursal.removeAllItems();
        // Agregar cada nombre de sucursal al ComboBox
        for (SucursalDTO sucursal : sucursales) {
            //comboBoxNombreSucursal.addItem(sucursal.getNombre()); // Suponiendo que SucursalDTO tiene un método getNombre()
        }
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
        LblSucursales = new javax.swing.JLabel();
        LblCiudad = new javax.swing.JLabel();
        BtnBuscarSucursal = new javax.swing.JButton();
        CmbxCiudad = new javax.swing.JComboBox<>();
        CmbxSucursal = new javax.swing.JComboBox<>();
        LblCiudad1 = new javax.swing.JLabel();
        LblPagina = new javax.swing.JLabel();
        LblCiudad2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblCartelera = new javax.swing.JTable();
        LblCartelera = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnAtrasTabla = new javax.swing.JButton();
        BtnAdelanteTabla = new javax.swing.JButton();
        Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BtnAtras = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuOpciones = new javax.swing.JMenu();
        MenuCerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblSucursales.setBackground(new java.awt.Color(0, 0, 0));
        LblSucursales.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LblSucursales.setText("Sucursales");
        jPanel2.add(LblSucursales, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 20, -1, -1));

        LblCiudad.setBackground(new java.awt.Color(0, 0, 0));
        LblCiudad.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblCiudad.setText("Ciudad");
        jPanel2.add(LblCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 114, -1, -1));

        BtnBuscarSucursal.setBackground(new java.awt.Color(0, 204, 255));
        BtnBuscarSucursal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        BtnBuscarSucursal.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscarSucursal.setText("Buscar sucursal mas cercana");
        BtnBuscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarSucursalActionPerformed(evt);
            }
        });
        jPanel2.add(BtnBuscarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1034, 168, -1, 54));

        CmbxCiudad.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        CmbxCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbxCiudadActionPerformed(evt);
            }
        });
        jPanel2.add(CmbxCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 300, -1));

        CmbxSucursal.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jPanel2.add(CmbxSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 164, 300, -1));

        LblCiudad1.setBackground(new java.awt.Color(0, 0, 0));
        LblCiudad1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LblCiudad1.setText("Seleccionar");
        jPanel2.add(LblCiudad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(716, 316, -1, 24));

        LblPagina.setBackground(new java.awt.Color(0, 0, 0));
        LblPagina.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LblPagina.setText("1");
        jPanel2.add(LblPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(781, 921, -1, 52));

        LblCiudad2.setBackground(new java.awt.Color(0, 0, 0));
        LblCiudad2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        LblCiudad2.setText("Sucursal");
        jPanel2.add(LblCiudad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 114, -1, -1));

        TblCartelera.setBackground(new java.awt.Color(255, 255, 255));
        TblCartelera.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TblCartelera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "(Nombre 1)", "(Nombre 2)", "(Nombre 3)"
            }
        ));
        jScrollPane1.setViewportView(TblCartelera);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 346, 1241, 569));

        LblCartelera.setBackground(new java.awt.Color(0, 0, 0));
        LblCartelera.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LblCartelera.setText("Cartelera");
        jPanel2.add(LblCartelera, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 258, -1, 52));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 633, -1, -1));

        BtnAtrasTabla.setBackground(new java.awt.Color(255, 255, 255));
        BtnAtrasTabla.setIcon(new javax.swing.ImageIcon("C:\\Users\\rramirez\\OneDrive\\Escritorio\\BDA_3\\Proyecto01BDA_CINEPOLIS\\Resources\\BtnAtrasT.png")); // NOI18N
        jPanel2.add(BtnAtrasTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 621, -1, 60));

        BtnAdelanteTabla.setBackground(new java.awt.Color(255, 255, 255));
        BtnAdelanteTabla.setIcon(new javax.swing.ImageIcon("C:\\Users\\rramirez\\OneDrive\\Escritorio\\BDA_3\\Proyecto01BDA_CINEPOLIS\\Resources\\BtnAdelanteT.png")); // NOI18N
        BtnAdelanteTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAdelanteTablaActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAdelanteTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(1369, 599, -1, -1));

        Panel.setBackground(new java.awt.Color(0, 0, 153));
        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 587, -1, -1));

        BtnAtras.setBackground(new java.awt.Color(0, 0, 153));
        BtnAtras.setForeground(new java.awt.Color(0, 0, 153));
        BtnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAtrasActionPerformed(evt);
            }
        });
        Panel.add(BtnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 849, -1, 99));

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        MenuOpciones.setText("Opciones");

        MenuCerrarSesion.setText("Cerrar Sesión");
        MenuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCerrarSesionActionPerformed(evt);
            }
        });
        MenuOpciones.add(MenuCerrarSesion);

        jMenuBar1.add(MenuOpciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBuscarSucursalActionPerformed

    private void BtnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAtrasActionPerformed
        // TODO add your handling code here:
//        InicioSesion i = new InicioSesion();
//        i.setVisible(true);
//        this.dispose();
    }//GEN-LAST:event_BtnAtrasActionPerformed

    private void BtnAdelanteTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAdelanteTablaActionPerformed
        // TODO add your handling code here:
        PeliculaResumen pl = new PeliculaResumen();
        pl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnAdelanteTablaActionPerformed

    private void MenuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCerrarSesionActionPerformed
        // TODO add your handling code here:
//        InicioSesion i = new InicioSesion();
//        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MenuCerrarSesionActionPerformed

    private void CmbxCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbxCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbxCiudadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdelanteTabla;
    private javax.swing.JButton BtnAtras;
    private javax.swing.JButton BtnAtrasTabla;
    private javax.swing.JButton BtnBuscarSucursal;
    private javax.swing.JComboBox<String> CmbxCiudad;
    private javax.swing.JComboBox<String> CmbxSucursal;
    private javax.swing.JLabel LblCartelera;
    private javax.swing.JLabel LblCiudad;
    private javax.swing.JLabel LblCiudad1;
    private javax.swing.JLabel LblCiudad2;
    private javax.swing.JLabel LblPagina;
    private javax.swing.JLabel LblSucursales;
    private javax.swing.JMenuItem MenuCerrarSesion;
    private javax.swing.JMenu MenuOpciones;
    private javax.swing.JPanel Panel;
    private javax.swing.JTable TblCartelera;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
