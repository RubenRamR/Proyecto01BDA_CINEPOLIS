/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion.Administrador;

import Negocio.DTOs.FuncionDTO;
import Negocio.Negocio.ClienteNegocio;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.ConexionBD;
import excepciones.cinepolisException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import utilerias.JButtonCellEditor;
import utilerias.JButtonRenderer;

/**
 *
 * @author stae
 */
public class AdministrarFunciones extends javax.swing.JFrame {

    ClienteNegocio clienteNegocio;
    private int pagina = 1;
    private int LIMITE = 3;
    boolean conFiltro;

    /**
     * Creates new form AdministrarFunciones
     */
    public AdministrarFunciones(ClienteNegocio clienteNegocio) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setSize(955, 600);
        this.clienteNegocio = clienteNegocio;
        this.cargarMetodosIniciales();
    }

    private long getIdSeleccionadoTablaFunciones() {
        int indiceFilaSeleccionada = this.tblFunciones.getSelectedRow();
        if (indiceFilaSeleccionada != -1)
        {
            DefaultTableModel modelo = (DefaultTableModel) this.tblFunciones.getModel();
            long indiceColumnaId = 0;
            long idSocioSeleccionado = (long) modelo.getValueAt(indiceFilaSeleccionada,
                    (int) indiceColumnaId);
            return idSocioSeleccionado;
        } else
        {
            return 0;
        }
    }

    private void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialTablaFunciones();
        this.cargarFuncionesEnTabla();
    }

    private void cargarConfiguracionInicialTablaFunciones() {
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    //Metodo para editar un alumno
                    editar();
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        };
        int indiceColumnaEditar = 4;
        TableColumnModel modeloColumnas = this.tblFunciones.getColumnModel();
        Color color = new Color(178, 218, 250);
        modeloColumnas.getColumn(indiceColumnaEditar).setCellRenderer(new JButtonRenderer("Editar", color));
        modeloColumnas.getColumn(indiceColumnaEditar).setCellEditor(new JButtonCellEditor("Editar", onEditarClickListener));

        ActionListener onEliminarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    eliminar();
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        };
        int indiceColumnaEliminar = 5;
        color = new Color(255, 105, 97);
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellRenderer(new JButtonRenderer("Eliminar", color));
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellEditor(new JButtonCellEditor("Eliminar", onEliminarClickListener));
    }

    private void editar() throws SQLException {
        long id = this.getIdSeleccionadoTablaFunciones();
        EditarFuncion editar = new EditarFuncion(this.clienteNegocio, (int) id);
        this.setVisible(false);
        editar.show();
    }

    private void eliminar() throws SQLException {
        try
        {
            long id = this.getIdSeleccionadoTablaFunciones();
            FuncionDTO funcion = clienteNegocio.obtenerFuncionPorId(id);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar al cliente?\n"
                    + "ID: " + funcion.getId() + "\n"
                    + "Pelicula: " + funcion.getPeliculaDTO().getTitulo() + "\n"
                    + "Fecha: " + funcion.getFecha().toString(),
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION)
            {
                clienteNegocio.eliminarFuncion(id);
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarFuncionesEnTabla();
            }
        } catch (cinepolisException ex)
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTablaFunciones(List<FuncionDTO> clienteLista) {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblFunciones.getModel();

        // Clear existing rows
        modeloTabla.setRowCount(0);

        if (clienteLista != null)
        {
            clienteLista.forEach(row ->
            {
                Object[] fila = new Object[6];
                fila[0] = row.getId();
                fila[1] = row.getFecha();
                fila[2] = row.getHoraInicio();
                fila[3] = row.getPeliculaDTO().getDuracion();
                fila[4] = "Eliminar";
                fila[5] = "Editar";
                modeloTabla.addRow(fila);
            });
        }
    }

    private void cargarFuncionesEnTabla() {
        try
        {
            int indiceInicio = (pagina - 1) * LIMITE;
            List<FuncionDTO> todasLasFunciones = clienteNegocio.buscarFuncionesTabla();
            int indiceFin = Math.min(indiceInicio + LIMITE, todasLasFunciones.size());

            List<FuncionDTO> funcionesPagina = obtenerFuncionesPagina(indiceInicio, indiceFin);

            llenarTablaFunciones(funcionesPagina);

            actualizarNumeroDePagina();
        } catch (cinepolisException ex)
        {
            ex.printStackTrace();
        }
    }

    private List<FuncionDTO> obtenerFuncionesPagina(int indiceInicio, int indiceFin) {
        try
        {
            List<FuncionDTO> todasLasFunciones = clienteNegocio.buscarFuncionesTabla();
            List<FuncionDTO> funcionesPaginas = new ArrayList<>();

            indiceFin = Math.min(indiceFin, todasLasFunciones.size());

            for (int i = indiceInicio; i < indiceFin; i++)
            {
                funcionesPaginas.add(todasLasFunciones.get(i));
            }

            return funcionesPaginas;
        } catch (cinepolisException ex)
        {

            ex.printStackTrace();
            return Collections.emptyList();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFunciones = new javax.swing.JTable();
        btnNuevaFuncion = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        NumeroDePagina = new javax.swing.JTextField();
        CambiarLimite = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

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
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, -1));

        jLabel1.setFont(new java.awt.Font("Shree Devanagari 714", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Administrar Funciones");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        tblFunciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Hora Inicio", "Duracion", "Eliminar", "Editar"
            }
        ));
        jScrollPane1.setViewportView(tblFunciones);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 720, 320));

        btnNuevaFuncion.setBackground(new java.awt.Color(12, 33, 63));
        btnNuevaFuncion.setFont(new java.awt.Font("Shree Devanagari 714", 0, 14)); // NOI18N
        btnNuevaFuncion.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaFuncion.setText("+Nueva Funcion");
        btnNuevaFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFuncionActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        btnAtras.setBackground(new java.awt.Color(12, 33, 63));
        btnAtras.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        jPanel1.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 500, -1, -1));

        btnSiguiente.setBackground(new java.awt.Color(12, 33, 63));
        btnSiguiente.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 490, -1, -1));

        NumeroDePagina.setBackground(new java.awt.Color(200, 200, 200));
        NumeroDePagina.setText("1");
        NumeroDePagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumeroDePaginaActionPerformed(evt);
            }
        });
        jPanel1.add(NumeroDePagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 20, -1));

        CambiarLimite.setText("1");
        CambiarLimite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CambiarLimiteActionPerformed(evt);
            }
        });
        jPanel1.add(CambiarLimite, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 20, 40));

        jLabel7.setFont(new java.awt.Font("Shree Devanagari 714", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Numero de Resultados");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        AdministrarCatalogos administrarCatalogos = new AdministrarCatalogos(clienteNegocio);
        administrarCatalogos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnNuevaFuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFuncionActionPerformed
        // TODO add your handling code here:

        NuevaFuncion nuevaFuncion = new NuevaFuncion(clienteNegocio);
        nuevaFuncion.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNuevaFuncionActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        if (pagina > 1)
        {
            pagina--;
            cargarFuncionesEnTabla();
            actualizarNumeroDePagina();
        }

    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        try
        {
            List<FuncionDTO> todasLasFunciones = clienteNegocio.buscarFuncionesTabla();

            int totalPaginas = (int) Math.ceil((double) todasLasFunciones.size() / LIMITE);

            if (pagina < totalPaginas)
            {
                pagina++;
                cargarFuncionesEnTabla();
                actualizarNumeroDePagina();
            } else
            {

                JOptionPane.showMessageDialog(this, "No hay más páginas disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (cinepolisException ex)
        {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void NumeroDePaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumeroDePaginaActionPerformed
        // TODO add your handling code here:
        try
        {
            List<FuncionDTO> todasLasFunciones = clienteNegocio.buscarFuncionesTabla();

            int totalPaginas = (int) Math.ceil((double) todasLasFunciones.size() / LIMITE);

            int nuevaPagina = Integer.parseInt(NumeroDePagina.getText());

            if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas)
            {
                pagina = nuevaPagina;

                cargarFuncionesEnTabla();

                actualizarNumeroDePagina();
            } else
            {
                JOptionPane.showMessageDialog(this, "Número de página inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la página", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (cinepolisException ex)
        {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_NumeroDePaginaActionPerformed

    private void CambiarLimiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CambiarLimiteActionPerformed
        try
        {
            if (conFiltro = false)
            {

                int nuevoLimite = Integer.parseInt(CambiarLimite.getText());
                this.LIMITE = nuevoLimite;
                cargarFuncionesEnTabla();
                actualizarNumeroDePagina();
            } else
            {

            }
        } catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el límite", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CambiarLimiteActionPerformed

    private void actualizarNumeroDePagina() {
        NumeroDePagina.setText("" + pagina);
    }

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
            java.util.logging.Logger.getLogger(AdministrarFunciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(AdministrarFunciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(AdministrarFunciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AdministrarFunciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        ConexionBD conexion = new ConexionBD();
        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarFunciones(clienteNegocio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CambiarLimite;
    private javax.swing.JTextField NumeroDePagina;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnNuevaFuncion;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFunciones;
    // End of variables declaration//GEN-END:variables
}
