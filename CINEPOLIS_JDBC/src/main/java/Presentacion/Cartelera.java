/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import Negocio.DTOs.GeneroDTO;
import Negocio.DTOs.PeliculaDTO;
import Negocio.Negocio.ClienteNegocio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author stae
 */
public class Cartelera extends javax.swing.JFrame {

    private int pagina = 1;
       private final int LIMITE = 6;  
       private List<String> peliculas;
       private ClienteNegocio negocio;
       private String sucursalSeleccionada;
       private String sucursal;
       private int totalPaginas;
       List<PeliculaDTO>datosPeliculas;
    
    
    /**
     * Creates new form Cartelera
     */
    public Cartelera(String sucursal, ClienteNegocio negocio) {
        this.negocio = negocio;
        this.sucursalSeleccionada = sucursal;
        this.sucursal=sucursal;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(955, 580);
        setVisible(true);
        
        llenarComboBoxGenero();

        ComboBoxGenero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarBotonesPeliculas(pagina);
            }
        });
    }
    
    public void agregarOpcionesMenu() {

        JMenu menuSala = new JMenu("Sala");
        JMenuItem verSalas = new JMenuItem("Ver Salas");
        verSalas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open your frame here
                Sala sala = new Sala();
                sala.setVisible(true);

                dispose();
            }
        });

        
        menuSala.add(verSalas);
        
       
        
    }


    private void llenarComboBoxGenero() {
        ComboBoxGenero.addItem(""); 
        List<GeneroDTO> generos = negocio.obtenerTodosLosGeneros();
        
        for (GeneroDTO genero : generos) {
            ComboBoxGenero.addItem(genero.getTipo());
        }
    }
    
    private int calcularTotalPaginas() {
        return (int) Math.ceil((double) peliculas.size() / 7); 
    }

    private void llenarBotonesPeliculas(int pagina) {
        this.peliculas = negocio.obtenerTitulosPeliculasPorGeneroYSucursal((String) ComboBoxGenero.getSelectedItem(), sucursalSeleccionada);
        this.totalPaginas = calcularTotalPaginas();
        this.datosPeliculas = negocio.obtenerDatosPeliculasPorGeneroYSucursal((String) ComboBoxGenero.getSelectedItem(), sucursalSeleccionada);
        int inicio = (pagina - 1) * 8;
        int fin = Math.min(inicio + 8, peliculas.size());

        try {
            for (int i = 0; i < 8; i++) {
                int index = inicio + i;
                if (index < fin) {
                    switch (i) {
                        case 0:
                            botonPelicula1.setText(peliculas.get(index));
                            break;
                        case 1:
                            botonPelicula2.setText(peliculas.get(index));
                            break;
                        case 2:
                            botonPelicula3.setText(peliculas.get(index));
                            break;
                        case 3:
                            botonPelicula8.setText(peliculas.get(index));
                            break;
                        case 4:
                            botonPelicula5.setText(peliculas.get(index));
                            break;
                        case 5:
                            botonPelicula6.setText(peliculas.get(index));
                            break;
                        case 6:
                            botonPelicula7.setText(peliculas.get(index));
                            break;
                        case 7:
                            botonPelicula8.setText(peliculas.get(index));
                            break;
                    }
                    
                } else {
                    // Si no hay más películas para mostrar, limpiar el texto del botón
                    switch (i) {
                        case 0:
                            botonPelicula1.setText("");
                            break;
                        case 1:
                            botonPelicula2.setText("");
                            break;
                        case 2:
                            botonPelicula3.setText("");
                            break;
                        case 3:
                            botonPelicula8.setText("");
                            break;
                        case 4:
                            botonPelicula5.setText("");
                            break;
                        case 5:
                            botonPelicula6.setText("");
                            break;
                        case 6:
                            botonPelicula7.setText("");
                            break;
                        case 7:
                            botonPelicula8.setText(peliculas.get(index));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        ComboBoxGenero = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnAtras = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        botonPelicula2 = new javax.swing.JButton();
        botonPelicula3 = new javax.swing.JButton();
        botonPelicula7 = new javax.swing.JButton();
        botonPelicula1 = new javax.swing.JButton();
        botonPelicula8 = new javax.swing.JButton();
        botonPelicula5 = new javax.swing.JButton();
        botonPelicula6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(12, 33, 63));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(12, 33, 63));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 570));

        jPanel5.setBackground(new java.awt.Color(12, 33, 63));

        ComboBoxGenero.setBackground(new java.awt.Color(12, 33, 63));
        ComboBoxGenero.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        ComboBoxGenero.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(821, Short.MAX_VALUE)
                .addComponent(ComboBoxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(ComboBoxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 910, 40));

        jLabel1.setFont(new java.awt.Font("Serif", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cartelera ");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, -1, -1));

        btnAtras.setBackground(new java.awt.Color(12, 33, 63));
        btnAtras.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAtras.setText("←");
        btnAtras.setContentAreaFilled(false);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        jPanel3.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        btnSiguiente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnSiguiente.setText("→");
        btnSiguiente.setContentAreaFilled(false);
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 530, -1, -1));

        botonPelicula2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula2ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 150, 190));

        botonPelicula3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula3ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 110, 150, 190));

        botonPelicula7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula7ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula7, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 330, 150, 190));

        botonPelicula1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula1ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 150, 190));

        botonPelicula8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula8ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 150, 190));

        botonPelicula5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula5ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 150, 190));

        botonPelicula6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPelicula6ActionPerformed(evt);
            }
        });
        jPanel3.add(botonPelicula6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, 150, 190));
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 150, 190));

        jMenu1.setText("Salas");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Ver Salas");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem1);

        btnMenu.add(jMenu1);

        setJMenuBar(btnMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        if (pagina > 1) {
            pagina--;
            llenarBotonesPeliculas(pagina);
        } else {
            JOptionPane.showMessageDialog(this, "Ya estás en la primera página", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        if (pagina < totalPaginas) {
            pagina++;
            llenarBotonesPeliculas(pagina);
        } else {
            JOptionPane.showMessageDialog(this, "No hay más páginas disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void botonPelicula2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula2ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula2ActionPerformed

    private void botonPelicula3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula3ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula3ActionPerformed

    private void botonPelicula7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula7ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula7ActionPerformed

    private void botonPelicula1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula1ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
    }//GEN-LAST:event_botonPelicula1ActionPerformed

    private void botonPelicula8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula8ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula8ActionPerformed

    private void botonPelicula5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula5ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula5ActionPerformed

    private void botonPelicula6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPelicula6ActionPerformed
        JButton boton = (JButton) evt.getSource(); // Obtener el botón que activó el evento
        String tituloPelicula = boton.getText(); // Obtener el texto del botón
        confirmarCompra(tituloPelicula, datosPeliculas);
    }//GEN-LAST:event_botonPelicula6ActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Cartelera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Cartelera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Cartelera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Cartelera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Cartelera().setVisible(true);
//            }
//        });
//    }
    
    private void confirmarCompra(String tituloPelicula, List<PeliculaDTO> datosPeliculas) {
        // Mostrar los datos de la película seleccionada
        PeliculaDTO peliculaSeleccionada = null;
        for (PeliculaDTO pelicula : datosPeliculas) {
            if (pelicula.getTitulo().equals(tituloPelicula)) {
                peliculaSeleccionada = pelicula;
                break;
            }
        }

        if (peliculaSeleccionada != null) {
            System.out.println("Título: " + peliculaSeleccionada.getTitulo());
            System.out.println("Sinopsis: " + peliculaSeleccionada.getSinopsis());
            System.out.println("Duración: " + peliculaSeleccionada.getDuracion());

           
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea confirmar la compra de entradas para esta película?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
               CompraBoleto b=new CompraBoleto(sucursal, peliculaSeleccionada, negocio);
               b.setVisible(true);
               this.dispose();
            } else {
                System.out.println("Compra cancelada.");
            }
        } else {
            System.out.println("La película seleccionada no se encuentra en la lista de datos.");
        }
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxGenero;
    private javax.swing.JButton botonPelicula1;
    private javax.swing.JButton botonPelicula2;
    private javax.swing.JButton botonPelicula3;
    private javax.swing.JButton botonPelicula5;
    private javax.swing.JButton botonPelicula6;
    private javax.swing.JButton botonPelicula7;
    private javax.swing.JButton botonPelicula8;
    private javax.swing.JButton btnAtras;
    private javax.swing.JMenuBar btnMenu;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    // End of variables declaration//GEN-END:variables
}
