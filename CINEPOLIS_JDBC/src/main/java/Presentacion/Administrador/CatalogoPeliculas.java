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
public class CatalogoPeliculas extends javax.swing.JFrame {

    ClienteNegocio clienteNegocio;
    private int pagina=1;
    private int LIMITE=2;
    boolean conFiltro;
    
    
    /**
     * Creates new form CatalogoPeliculas
     */
    public CatalogoPeliculas(ClienteNegocio clienteNegocio) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setSize(955, 600);
        this.clienteNegocio = clienteNegocio;
        this.cargarMetodosIniciales();
        NumeroDePagina.setEditable(false);
        conFiltro=false;

        llenarComboBoxGenero();
        llenarComboBoxClasificacion();
    }
    
    private void llenarComboBoxGenero() {
        boxGenero.addItem(""); // Primera opción en blanco
        List<GeneroDTO> generos = clienteNegocio.obtenerTodosLosGeneros();
        for (GeneroDTO genero : generos) {
            boxGenero.addItem(genero.getTipo());
        }
    }

    private void llenarComboBoxClasificacion() {
            // Llenar ComboBox de Clasificación
            boxClasificacion.addItem(""); // Primera opción en blanco
            List<ClasificacionDTO> clasificaciones = clienteNegocio.obtenerTodasLasClasificaciones();
            for (ClasificacionDTO clasificacion : clasificaciones) {
                boxClasificacion.addItem(clasificacion.getTipo());
            }
        }

    
    private long getIdSeleccionadoTablaPelicula() throws cinepolisException {
         int selectedRow = tblPeliculas.getSelectedRow();
        if (selectedRow >= 0) {
            return (long) tblPeliculas.getValueAt(selectedRow, 0);
        } else {
            throw new excepciones.cinepolisException("No se ha seleccionado ninguna película");
        }
    }
        
    private void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialTablaClientes();
        this.cargarPeliculasEnTabla();
    }
    
        private void cargarConfiguracionInicialTablaClientes() {
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        };
        int indiceColumnaEditar = 4;
        TableColumnModel modeloColumnas = this.tblPeliculas.getColumnModel();
        Color color = new Color(178, 218, 250);
        modeloColumnas.getColumn(indiceColumnaEditar).setCellRenderer(new JButtonRenderer("Editar",color));
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
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellRenderer(new JButtonRenderer("Eliminar",color));
        modeloColumnas.getColumn(indiceColumnaEliminar).setCellEditor(new JButtonCellEditor("Eliminar", onEliminarClickListener));
        }
        
            private void editar() {
            try {
            long id = this.getIdSeleccionadoTablaPelicula();
            System.out.println("ID de la película seleccionada: " + id);
            EditarPelicula editar = new EditarPelicula(this.clienteNegocio, id);
            this.setVisible(false);
            editar.show();
        } catch (excepciones.cinepolisException e) {
            System.out.println("Error: " + e.getMessage());
        }
        }

        private void eliminar() {
            try {
            long id = this.getIdSeleccionadoTablaPelicula();
            PeliculaDTO pelicula = clienteNegocio.obtenerPeliculaPorID(id);
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                        "¿Está seguro que desea eliminar la película?\n" +
                        "ID: " + pelicula.getId() + "\n" +
                        "Título: " + pelicula.getTitulo() + "\n" +
                        "Sinopsis: " + pelicula.getGenero(),
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                clienteNegocio.eliminarPelicula(id);
                JOptionPane.showMessageDialog(this, "Película eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPeliculasEnTabla();
            }
            } catch (cinepolisException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void llenarTablaPeliculas(List<PeliculaDTO> peliculasLista) {
         DefaultTableModel modeloTabla = (DefaultTableModel) this.tblPeliculas.getModel();

        modeloTabla.setRowCount(0);

        if (peliculasLista != null) {
            peliculasLista.forEach(row -> {
                Object[] fila = new Object[6];
                fila[0] = row.getId();
                fila[1] = row.getTitulo();
                fila[2] = row.getGenero();
                fila[3] = row.getClasificacion();
                fila[4] = "Eliminar";
                fila[5] = "Editar"; 
                modeloTabla.addRow(fila);
            });
        }
        }
        
        private void cargarPeliculasEnTabla() {
    try {
        int indiceInicio = (pagina - 1) * LIMITE;
        List<PeliculaDTO> todasLasPeliculas = clienteNegocio.obtenerTodasLasPeliculasTablaDTO();
        int indiceFin = Math.min(indiceInicio + LIMITE, todasLasPeliculas.size());

        List<PeliculaDTO> peliculasPagina = obtenerPeliculasPagina(indiceInicio, indiceFin);

        llenarTablaPeliculas(peliculasPagina);

        actualizarNumeroDePagina();
    } catch (cinepolisException ex) {
        ex.printStackTrace();
    }
    }
        
        private List<PeliculaDTO> obtenerPeliculasPagina(int indiceInicio, int indiceFin) throws cinepolisException {
            List<PeliculaDTO> todasLasPeliculas = clienteNegocio.buscarPeliculasTabla();
            List<PeliculaDTO> peliculasPaginas = new ArrayList<>();
            indiceFin = Math.min(indiceFin, todasLasPeliculas.size());
            for (int i = indiceInicio; i < indiceFin; i++) {
                peliculasPaginas.add(todasLasPeliculas.get(i));
            }
            return peliculasPaginas;
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
        tblPeliculas = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textoTitulo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        boxGenero = new javax.swing.JComboBox<>();
        boxClasificacion = new javax.swing.JComboBox<>();
        botonRestaurar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        textoPais = new javax.swing.JTextField();
        btnNuevaPelicula = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        NumeroDePagina = new javax.swing.JTextField();
        CambiarLimite = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnGenerarReporte = new javax.swing.JButton();

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
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel1.setFont(new java.awt.Font("Shree Devanagari 714", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Catalogo Peliculas ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, -1, -1));

        tblPeliculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Titulo", "Genero", "Clasificacion", "Eliminar", "Editar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPeliculas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 790, 260));

        jPanel6.setBackground(new java.awt.Color(12, 33, 63));

        jLabel3.setFont(new java.awt.Font("Shree Devanagari 714", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Filtro de Busqueda");

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Genero");

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clasificacion");

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Peliculas:");

        btnBuscar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        boxGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxGeneroActionPerformed(evt);
            }
        });

        botonRestaurar.setText("Restaurar");
        botonRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRestaurarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pais");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(237, 237, 237)
                .addComponent(botonRestaurar)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(boxClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(textoPais, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(textoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(botonRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(5, 5, 5)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar))
                    .addComponent(boxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boxClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textoPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 770, 80));

        btnNuevaPelicula.setBackground(new java.awt.Color(12, 33, 63));
        btnNuevaPelicula.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btnNuevaPelicula.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaPelicula.setText("+Nueva Pelicula");
        btnNuevaPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaPeliculaActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        btnSiguiente.setBackground(new java.awt.Color(0, 153, 255));
        btnSiguiente.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 490, -1, -1));

        btnAtras.setBackground(new java.awt.Color(0, 153, 255));
        btnAtras.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        jPanel1.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, -1, -1));

        NumeroDePagina.setText("1");
        NumeroDePagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumeroDePaginaActionPerformed(evt);
            }
        });
        jPanel1.add(NumeroDePagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 500, 20, 20));

        CambiarLimite.setText("1");
        CambiarLimite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CambiarLimiteActionPerformed(evt);
            }
        });
        jPanel1.add(CambiarLimite, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, 20, 40));

        jLabel7.setFont(new java.awt.Font("Shree Devanagari 714", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Numero de Resultados");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 500, -1, -1));

        btnGenerarReporte.setBackground(new java.awt.Color(0, 153, 255));
        btnGenerarReporte.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        btnGenerarReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarReporte.setText("Generar Reporte");
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, 130, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        AdministrarCatalogos administrarCatalogos = new AdministrarCatalogos(clienteNegocio);
        administrarCatalogos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            // TODO add your handling code here:
            String generoFiltro = boxGenero.getSelectedItem().toString();
            String clasificacionFiltro = boxClasificacion.getSelectedItem().toString();
            String tituloFiltro = textoTitulo.getText();
            String pais = textoPais.getText();

            cargarPeliculasEnTablaActualizada(generoFiltro, clasificacionFiltro, tituloFiltro,pais);
            System.out.println("CatalogoPeliculas "+generoFiltro+" "+clasificacionFiltro);
            actualizarNumeroDePagina();
            conFiltro = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void boxGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxGeneroActionPerformed

    private void botonRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRestaurarActionPerformed
        conFiltro = false;

        boxGenero.setSelectedIndex(0);
        boxClasificacion.setSelectedIndex(0);
        textoTitulo.setText("");

        cargarPeliculasEnTabla();

        actualizarNumeroDePagina();
    }//GEN-LAST:event_botonRestaurarActionPerformed

    private void btnNuevaPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPeliculaActionPerformed
        // TODO add your handling code here:
        AgregarPeliculas agregarPeliculas = new AgregarPeliculas(clienteNegocio);
        agregarPeliculas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNuevaPeliculaActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        try {
            List<PeliculaDTO> todasLasPeliculas = clienteNegocio.buscarPeliculasTabla();

            int totalPaginas = (int) Math.ceil((double) todasLasPeliculas.size() / LIMITE);

            if (pagina < totalPaginas) {
                pagina++;
                cargarPeliculasEnTabla();
                actualizarNumeroDePagina();
            } else {

                JOptionPane.showMessageDialog(this, "No hay más páginas disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (cinepolisException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        if (pagina > 1) {
            pagina--;
            cargarPeliculasEnTabla();
            actualizarNumeroDePagina();
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void NumeroDePaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumeroDePaginaActionPerformed

        try {
            List<PeliculaDTO> todasLasPeliculas = clienteNegocio.buscarPeliculasTabla();

            int totalPaginas = (int) Math.ceil((double) todasLasPeliculas.size() / LIMITE);

            int nuevaPagina = Integer.parseInt(NumeroDePagina.getText());

            if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas) {
                pagina = nuevaPagina;

                cargarPeliculasEnTabla();

                actualizarNumeroDePagina();
            } else {
                JOptionPane.showMessageDialog(this, "Número de página inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la página", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (cinepolisException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_NumeroDePaginaActionPerformed

    private void CambiarLimiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CambiarLimiteActionPerformed
        try {
            if (!conFiltro) {
                int nuevoLimite = Integer.parseInt(CambiarLimite.getText());
                this.LIMITE = nuevoLimite;
                cargarPeliculasEnTabla();
                actualizarNumeroDePagina();
            } else {
                int nuevoLimite = Integer.parseInt(CambiarLimite.getText());
                this.LIMITE = nuevoLimite;
                String generoFiltro = boxGenero.getSelectedItem().toString();
                String clasificacionFiltro = boxClasificacion.getSelectedItem().toString();
                String tituloFiltro = textoTitulo.getText();
                String pais = textoPais.getText();
                cargarPeliculasEnTablaActualizada(generoFiltro, clasificacionFiltro, tituloFiltro, pais);
                actualizarNumeroDePagina();
                conFiltro = true;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el límite", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_CambiarLimiteActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        // TODO add your handling code here:
        ReportePorPeliculas reportePorPelicula  = new ReportePorPeliculas();
        reportePorPelicula.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    
    private void cargarPeliculasEnTablaActualizada(String generoFiltro, String clasificacionFiltro, String tituloFiltro, String paisFiltro) throws SQLException {
        try {
             System.out.println("Filtros: Género = " + generoFiltro + ", Clasificación = " + clasificacionFiltro + ", Título = " + tituloFiltro + ", País = " + paisFiltro);
            int indiceInicio = (pagina - 1) * LIMITE;
            List<PeliculaDTO> todasLasPeliculas = clienteNegocio.buscarPeliculas(tituloFiltro, generoFiltro, clasificacionFiltro, paisFiltro);
            int indiceFin = Math.min(indiceInicio + LIMITE, todasLasPeliculas.size());

            List<PeliculaDTO> peliculasPagina = obtenerPeliculasPaginaActualizado(indiceInicio, indiceFin, generoFiltro, clasificacionFiltro, tituloFiltro, paisFiltro);

            cargarPeliculasEnTabla(peliculasPagina);

            actualizarNumeroDePagina();
        } catch (cinepolisException ex) {
            ex.printStackTrace();
        }
    }

    private List<PeliculaDTO> obtenerPeliculasPaginaActualizado(int indiceInicio, int indiceFin, String generoFiltro, String clasificacionFiltro, String tituloFiltro, String paisFiltro) throws SQLException {
        try {
            List<PeliculaDTO> todasLasPeliculas = clienteNegocio.buscarPeliculas(tituloFiltro, generoFiltro, clasificacionFiltro, paisFiltro);
            List<PeliculaDTO> peliculasPagina = new ArrayList<>();

            indiceFin = Math.min(indiceFin, todasLasPeliculas.size());

            for (int i = indiceInicio; i < indiceFin; i++) {
                peliculasPagina.add(todasLasPeliculas.get(i));
            }

            return peliculasPagina;
        } catch (cinepolisException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    
        private void cargarPeliculasEnTabla(List<PeliculaDTO> peliculasEncontradas) {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblPeliculas.getModel();

        modeloTabla.setRowCount(0);

        if (peliculasEncontradas != null) {
            peliculasEncontradas.forEach(row -> {
                Object[] fila = new Object[6];
                fila[0] = row.getId();
                fila[1] = row.getTitulo();
                fila[2] = row.getGenero();
                fila[3] = row.getClasificacion();
                fila[4] = "Eliminar";
                fila[5] = "Editar"; 
                modeloTabla.addRow(fila);
            });
        }
    }
    
    private void actualizarNumeroDePagina() {
    NumeroDePagina.setText(""+ pagina);
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CatalogoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatalogoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatalogoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatalogoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        ConexionBD conexion = new ConexionBD();
        ClienteDAO clienteDAO= new ClienteDAO (conexion);
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatalogoPeliculas(clienteNegocio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CambiarLimite;
    private javax.swing.JTextField NumeroDePagina;
    private javax.swing.JButton botonRestaurar;
    private javax.swing.JComboBox<String> boxClasificacion;
    private javax.swing.JComboBox<String> boxGenero;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnNuevaPelicula;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeliculas;
    private javax.swing.JTextField textoPais;
    private javax.swing.JTextField textoTitulo;
    // End of variables declaration//GEN-END:variables
}
