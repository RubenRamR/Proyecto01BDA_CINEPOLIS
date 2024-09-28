/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion.Administrador;

import Negocio.DTOs.ClienteDTO;
import Negocio.Negocio.ClienteNegocio;
import Persistencia.DAOs.ClienteDAO;
import Persistencia.DAOs.ConexionBD;
import excepciones.cinepolisException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class CatalogoClientes extends javax.swing.JFrame {

    private int pagina = 1;
    private int LIMITE = 2;
    ClienteNegocio clienteNegocio;
    boolean conFiltro;

    /**
     * Creates new form CatalogoClientes
     */
    public CatalogoClientes(ClienteNegocio clienteNegocio) {
        initComponents();
        this.clienteNegocio = clienteNegocio;
        this.setLocationRelativeTo(this);
        this.cargarMetodosIniciales();
        this.setSize(955, 600);
        NumeroDePagina.setEditable(false);
        conFiltro = false;
    }

    private long getIdSeleccionadoTablaClientes() {
        int indiceFilaSeleccionada = this.tblClientes.getSelectedRow();
        if (indiceFilaSeleccionada != -1)
        {
            DefaultTableModel modelo = (DefaultTableModel) this.tblClientes.getModel();
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
        this.cargarConfiguracionInicialTablaClientes();
        this.cargarClientesEnTabla();
    }

    private void cargarConfiguracionInicialTablaClientes() {
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Metodo para editar un alumno
                editar();
            }
        };
        int indiceColumnaEditar = 4;
        TableColumnModel modeloColumnas = this.tblClientes.getColumnModel();
        Color color = new Color(178, 218, 250);
        modeloColumnas.getColumn(indiceColumnaEditar).setCellRenderer(new JButtonRenderer("Editar", color));
        modeloColumnas.getColumn(indiceColumnaEditar).setCellEditor(new JButtonCellEditor("Editar", onEditarClickListener));

        ActionListener onEliminarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        };
        int indiceColumnaEliminar = 5;
        color = new Color(255, 105, 97);
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellRenderer(new JButtonRenderer("Eliminar", color));
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellEditor(new JButtonCellEditor("Eliminar", onEliminarClickListener));
    }

    private void editar() {
        try
        {
            long id = this.getIdSeleccionadoTablaClientes();
            EditarCliente editar = new EditarCliente(this.clienteNegocio, id);
            this.setVisible(false);
            editar.show();
        } catch (excepciones.cinepolisException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try
        {
            long id = this.getIdSeleccionadoTablaClientes();
            ClienteDTO cliente = clienteNegocio.obtenerClientePorID(id);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar al cliente?\n"
                    + "ID: " + cliente.getId() + "\n"
                    + "Nombre: " + cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno() + "\n"
                    + "Correo: " + cliente.getCorreo(),
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION)
            {
                clienteNegocio.eliminarCliente(id);
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarClientesEnTabla();
            }
        } catch (cinepolisException ex)
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTablaClientes(List<ClienteDTO> clienteLista) {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblClientes.getModel();
        modeloTabla.setRowCount(0); // Limpiar filas existentes

        if (clienteLista != null)
        {
            clienteLista.forEach(row ->
            {
                Object[] fila = new Object[6];
                fila[0] = row.getId();
                fila[1] = row.getNombre() + " " + row.getApellidoPaterno() + " " + row.getApellidoMaterno();
                fila[2] = row.getCorreo();

                // Debugging
                String contrasena = row.getContrasena();
                System.out.println("Contraseña para el cliente " + row.getNombre() + ": " + contrasena); // Verifica que la contraseña sea la correcta

                fila[3] = contrasena; // Esto debe ser la contraseña

                fila[4] = "Eliminar"; // Acción de eliminar
                fila[5] = "Editar"; // Acción de editar
                modeloTabla.addRow(fila);
            });
        }
    }

    private void cargarClientesEnTabla() {
        try
        {
            int indiceInicio = (pagina - 1) * LIMITE;
            List<ClienteDTO> todosLosClientes = clienteNegocio.buscarClientesTabla();

            // Imprimir el número total de clientes
            System.out.println("Número total de clientes: " + todosLosClientes.size());

            // Validar si hay clientes
            if (todosLosClientes.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No hay clientes disponibles para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir si no hay clientes
            }

            int indiceFin = Math.min(indiceInicio + LIMITE, todosLosClientes.size());
            List<ClienteDTO> clientesPagina = obtenerClientesPagina(indiceInicio, indiceFin);

            // Verifica si se están obteniendo clientes de la página
            System.out.println("Número de clientes en la página: " + clientesPagina.size());

            llenarTablaClientes(clientesPagina);
            actualizarNumeroDePagina();
        } catch (cinepolisException ex)
        {
            ex.printStackTrace();
        }
    }

    private List<ClienteDTO> obtenerClientesPagina(int indiceInicio, int indiceFin) {
        try
        {
            List<ClienteDTO> todosLosClientes = clienteNegocio.buscarClientesTabla();
            List<ClienteDTO> clientesPaginas = new ArrayList<>();

            indiceFin = Math.min(indiceFin, todosLosClientes.size());

            for (int i = indiceInicio; i < indiceFin; i++)
            {
                clientesPaginas.add(todosLosClientes.get(i));
            }

            return clientesPaginas;
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        textoFiltroNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        botonRestaurar = new javax.swing.JButton();
        LblCiudad = new javax.swing.JLabel();
        textoFiltroCiudad = new javax.swing.JTextField();
        btnNuevoCliente = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        NumeroDePagina = new javax.swing.JTextField();
        CambiarLimite = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Shree Devanagari 714", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Catalogos Clientes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Correo", "Contraseña", "Eliminar", "Editar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 790, 260));

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jLabel2.setFont(new java.awt.Font("Shree Devanagari 714", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Filtro de Busqueda");

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha Inicio:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha Fin:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clientes:");

        btnBuscar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        botonRestaurar.setText("Restaurar");
        botonRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRestaurarActionPerformed(evt);
            }
        });

        LblCiudad.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        LblCiudad.setForeground(new java.awt.Color(255, 255, 255));
        LblCiudad.setText("Ciudad:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(49, 49, 49)
                                .addComponent(LblCiudad))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textoFiltroCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(textoFiltroNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 326, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(211, 211, 211)
                        .addComponent(botonRestaurar)))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(LblCiudad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textoFiltroNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar)
                        .addComponent(textoFiltroCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 770, 80));

        btnNuevoCliente.setBackground(new java.awt.Color(0, 153, 255));
        btnNuevoCliente.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btnNuevoCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCliente.setText("+Nuevo Cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        btnAtras.setBackground(new java.awt.Color(12, 33, 63));
        btnAtras.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        jPanel1.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, -1, -1));

        btnSiguiente.setBackground(new java.awt.Color(12, 33, 63));
        btnSiguiente.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 500, -1, -1));

        NumeroDePagina.setBackground(new java.awt.Color(200, 200, 200));
        NumeroDePagina.setText("1");
        NumeroDePagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumeroDePaginaActionPerformed(evt);
            }
        });
        jPanel1.add(NumeroDePagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 510, 20, -1));

        CambiarLimite.setText("1");
        CambiarLimite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CambiarLimiteActionPerformed(evt);
            }
        });
        jPanel1.add(CambiarLimite, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, 20, 40));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel7.setText("Numero de Resultados");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 510, -1, -1));

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

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        AgregarCliente agregarCliente = new AgregarCliente(clienteNegocio);
        agregarCliente.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        if (pagina > 1)
        {
            pagina--;
            cargarClientesEnTabla();
            actualizarNumeroDePagina();
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
        try
        {
            List<ClienteDTO> todosLosClientes = clienteNegocio.buscarClientesTabla();

            int totalPaginas = (int) Math.ceil((double) todosLosClientes.size() / LIMITE);

            if (pagina < totalPaginas)
            {
                pagina++;
                cargarClientesEnTabla();
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
            List<ClienteDTO> todosLosclientes = clienteNegocio.buscarClientesTabla();

            int totalPaginas = (int) Math.ceil((double) todosLosclientes.size() / LIMITE);

            int nuevaPagina = Integer.parseInt(NumeroDePagina.getText());

            if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas)
            {
                pagina = nuevaPagina;

                cargarClientesEnTabla();

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
                cargarClientesEnTabla();
                actualizarNumeroDePagina();
            } else
            {
                int nuevoLimite = Integer.parseInt(CambiarLimite.getText());
                this.LIMITE = nuevoLimite;
                String nombreFiltro = textoFiltroNombre.getText();
                java.sql.Date fechaInicio = null;
                if (jDateChooser1.getDate() != null)
                {
                    fechaInicio = new java.sql.Date(jDateChooser1.getDate().getTime());
                }
                java.sql.Date fechaFin = null;
                if (jDateChooser2.getDate() != null)
                {
                    fechaFin = new java.sql.Date(jDateChooser2.getDate().getTime());
                }
                String ciudadFiltro = textoFiltroCiudad.getText();
                cargarClientesEnTablaActualizada(nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);
                actualizarNumeroDePagina();
                conFiltro = true;
            }
        } catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el límite", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CambiarLimiteActionPerformed

    private void botonRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRestaurarActionPerformed
        // TODO add your handling code here:
        conFiltro = false;
        textoFiltroNombre.setText("");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        textoFiltroCiudad.setText("");
        this.cargarMetodosIniciales();
    }//GEN-LAST:event_botonRestaurarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String nombreFiltro = textoFiltroNombre.getText();
        // Verificar si jDateChooser1 tiene una fecha seleccionada
        java.sql.Date fechaInicio = null;
        if (jDateChooser1.getDate() != null)
        {
            fechaInicio = new java.sql.Date(jDateChooser1.getDate().getTime());
        }
        // Verificar si jDateChooser2 tiene una fecha seleccionada
        java.sql.Date fechaFin = null;
        if (jDateChooser2.getDate() != null)
        {
            fechaFin = new java.sql.Date(jDateChooser2.getDate().getTime());
        }
        String ciudadFiltro = textoFiltroCiudad.getText();

        // Reiniciar la página a 1 antes de buscar
        pagina = 1; // Reinicia la página a 1

        // Cargar clientes en la tabla con los filtros
        cargarClientesEnTablaActualizada(nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);

        // Actualiza el número de página
        actualizarNumeroDePagina();

        // Indica que hay un filtro aplicado
        conFiltro = true;
    }//GEN-LAST:event_btnBuscarActionPerformed

    private List<ClienteDTO> obtenerClientesPaginaActualizado(int indiceInicio, int indiceFin, String nombreFiltro, java.sql.Date fechaInicio, java.sql.Date fechaFin, String ciudadFiltro) {
        try
        {
            List<ClienteDTO> todosLosClientes = clienteNegocio.buscarClientes(nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);
            List<ClienteDTO> clientesPaginas = new ArrayList<>();

            indiceFin = Math.min(indiceFin, todosLosClientes.size());

            for (int i = indiceInicio; i < indiceFin; i++)
            {
                clientesPaginas.add(todosLosClientes.get(i));
            }

            return clientesPaginas;
        } catch (cinepolisException ex)
        {

            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void cargarClientesEnTabla(List<ClienteDTO> clientesEncontrados) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tblClientes.getModel();

        // Limpiar filas existentes
        modeloTabla.setRowCount(0);

        if (clientesEncontrados != null)
        {
            clientesEncontrados.forEach(row ->
            {
                Object[] fila = new Object[6];
                fila[0] = row.getId();
                fila[1] = row.getNombre() + " " + row.getApellidoPaterno() + " " + row.getApellidoMaterno();
                fila[2] = row.getCorreo();
                fila[3] = row.getContrasena();
                fila[4] = "Eliminar";
                fila[5] = "Editar";
                modeloTabla.addRow(fila);
            });
        }

    }

    private void cargarClientesEnTablaActualizada(String nombreFiltro, java.sql.Date fechaInicio, java.sql.Date fechaFin, String ciudadFiltro) {
        try
        {
            int indiceInicio = (pagina - 1) * LIMITE;
            List<ClienteDTO> todosLosClientes = clienteNegocio.buscarClientes(nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);
            int indiceFin = Math.min(indiceInicio + LIMITE, todosLosClientes.size());

            List<ClienteDTO> clientesPagina = obtenerClientesPaginaActualizado(indiceInicio, indiceFin, nombreFiltro, fechaInicio, fechaFin, ciudadFiltro);

            cargarClientesEnTabla(clientesPagina);

            actualizarNumeroDePagina();
        } catch (cinepolisException ex)
        {
            ex.printStackTrace();
        }
    }

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
            java.util.logging.Logger.getLogger(CatalogoClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(CatalogoClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(CatalogoClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(CatalogoClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        ConexionBD conexion = new ConexionBD();
        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatalogoClientes(clienteNegocio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CambiarLimite;
    private javax.swing.JLabel LblCiudad;
    private javax.swing.JTextField NumeroDePagina;
    private javax.swing.JButton botonRestaurar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnSiguiente;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField textoFiltroCiudad;
    private javax.swing.JTextField textoFiltroNombre;
    // End of variables declaration//GEN-END:variables
}
