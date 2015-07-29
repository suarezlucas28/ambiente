/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prgs.ver_conex;

/**
 *
 * @author LUke
 */
public class permisos extends javax.swing.JPanel {
int personal;
int sucursal;
String usuario;
public int personalcbo;
DefaultTableModel modelo1 = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        if(column == 2)
         return true;
        return false;
    }
     
     Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class 
            };
         
         public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
};

DefaultTableModel modelo = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    public permisos() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        cabecera();
        cargausuario();
        des_todo ();
    }

    private void cabecera(){
  modelo1.addColumn("CÓDIGO");
  modelo1.addColumn("PANTALLA");
  modelo1.addColumn("PERMISO");
  grilla.getColumnModel().getColumn(0).setPreferredWidth(51);
  grilla.getColumnModel().getColumn(1).setPreferredWidth(151);
  grilla.getColumnModel().getColumn(2).setPreferredWidth(51);
}
    
    private void cabecera1(){
  modelo.addColumn("CÓDIGO");
  modelo.addColumn("USUARIO");
  grdusuarios.getColumnModel().getColumn(0).setPreferredWidth(151);
  grdusuarios.getColumnModel().getColumn(1).setPreferredWidth(151);
}
  
  public void cargausuario() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
            String SQL = "SELECT upper(usu_usuari) FROM usuarios where usu_estado like 'ACTIVO'";
            ver_conex  conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery(SQL);
            cbopersonal.removeAllItems();
            while (conn.resultado.next()) {
            cbopersonal.addItem(conn.resultado.getString("upper(usu_usuari)"));
}
}
   
  private void des_todo (){
  this.cbopersonal.setEnabled(false);
  this.grilla.setEnabled(false);
  this.btnregistrar.setEnabled(false);
  this.btncancelar.setEnabled(false);
  this.btngestionar.setEnabled(true);
  btngestionar.requestFocus();
  }

  private void bah_todo (){
  this.cbopersonal.setEnabled(true);
  this.grilla.setEnabled(true);
  this.btnregistrar.setEnabled(true);
  this.btncancelar.setEnabled(true);
  this.btngestionar.setEnabled(false);
  }

  public int buscausuario(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
            String SQL = "SELECT usu_codigo FROM usuarios WHERE usu_usuari ='" + a +"'";
            ver_conex  conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery(SQL);
            conn.resultado.next();
            return(conn.resultado.getInt("usu_codigo"));
}
  
  private void checkboxgrilla () throws SQLException{
  try {
javax.swing.table.TableModel model = grilla.getModel();

        int c = 0;

        for (int i = 0; i < model.getRowCount(); i++) {

            java.lang.Boolean value = (Boolean)model.getValueAt(i, 2);

            if (value != null && value) {

                System.out.println("El valor en la posición " + String.valueOf(i+1) + " esta encendido.");
                String Dato = (String) grilla.getValueAt(i,0).toString();
                int x = Integer.parseInt(Dato);
                System.out.println(x);
                String SQL = "INSERT INTO permisos (pan_codigo,usu_codigo,per_permis) VALUES ("+x+","+personalcbo+",1)";
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.sentencia.executeUpdate(SQL);
                conn.sentencia.close();
                c++;
                System.out.print(SQL);
              } else {
                String Dato = (String) grilla.getValueAt(i,0).toString();
                int x = Integer.parseInt(Dato);
                System.out.println(x);
                String SQL = "INSERT INTO permisos (pan_codigo,usu_codigo,per_permis) VALUES ("+x+","+personalcbo+",0)";
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.sentencia.executeUpdate(SQL);
                conn.sentencia.close();
                c++;
            }
        }
        System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");
        JOptionPane.showMessageDialog(null, "El registro se ha grabado satisfactoriamente.", "", JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
        }
}
  
  public void cargadatos() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String SQL = "SELECT usu_codigo,usu_usuari FROM usuarios WHERE usu_estado like 'BLOQUEADO'";
        ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);

           modelo.setRowCount(0);
        Object [] fila = new Object[2];
        while (conn.resultado.next()){
            fila[0] = conn.resultado.getObject(1);
            fila[1] = conn.resultado.getObject(2);
            modelo.addRow(fila);
     }
     conn.resultado.first();
     }
  
  private void desbloqueo() throws SQLException{
try {
 ver_conex  conn = new ver_conex();
 conn.sentencia = conn.conexion.createStatement();
 conn.sentencia.executeUpdate("UPDATE usuarios SET usu_estado = 'ACTIVO' WHERE usu_codigo = " + personal);//OJO LE PASO LA SENTENCIA
} catch (SQLException exceptionSql)
{
 JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
}
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desbloquear = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdusuarios = new javax.swing.JTable();
        btnanular = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        btngestionar = new javax.swing.JButton();
        btnregistrar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cbopersonal = new javax.swing.JComboBox();

        grdusuarios.setModel(modelo);
        grdusuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdusuariosMouseClicked(evt);
            }
        });
        grdusuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grdusuariosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(grdusuarios);

        btnanular.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnanular.setText("Desbloquear");
        btnanular.setEnabled(false);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });
        btnanular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnanularKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout desbloquearLayout = new javax.swing.GroupLayout(desbloquear.getContentPane());
        desbloquear.getContentPane().setLayout(desbloquearLayout);
        desbloquearLayout.setHorizontalGroup(
            desbloquearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desbloquearLayout.createSequentialGroup()
                .addGroup(desbloquearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(desbloquearLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(desbloquearLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnanular)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        desbloquearLayout.setVerticalGroup(
            desbloquearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desbloquearLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnanular, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        setMaximumSize(new java.awt.Dimension(352, 238));
        setMinimumSize(new java.awt.Dimension(352, 238));
        setPreferredSize(new java.awt.Dimension(352, 238));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("ENTER PARA CARGAR PERMISOS");

        grilla.setModel(modelo1);
        jScrollPane3.setViewportView(grilla);

        btngestionar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btngestionar.setText("Gestionar");
        btngestionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngestionarActionPerformed(evt);
            }
        });
        btngestionar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btngestionarKeyPressed(evt);
            }
        });

        btnregistrar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnregistrar.setText("Registrar");
        btnregistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarActionPerformed(evt);
            }
        });
        btnregistrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnregistrarKeyPressed(evt);
            }
        });

        btncancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });
        btncancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btncancelarKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Usuario:");

        jButton1.setText("Desbloquear");
        jButton1.setToolTipText("Desbloqueo de Usuarios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        cbopersonal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbopersonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbopersonalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel16))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btngestionar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)
                                .addComponent(cbopersonal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbopersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btngestionar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btngestionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngestionarActionPerformed
        cbopersonal.setEnabled(true);
        cbopersonal.requestFocus();
        bah_todo();
    }//GEN-LAST:event_btngestionarActionPerformed

    private void btnregistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarActionPerformed
        try {
            String SQL = "delete from permisos WHERE usu_codigo = " + personalcbo;
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.sentencia.executeUpdate(SQL);
            conn.sentencia.close();
            checkboxgrilla();
        } catch (SQLException ex) {
            Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btncancelar.doClick();
    }//GEN-LAST:event_btnregistrarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        des_todo();
        javax.swing.table.DefaultTableModel dt;
        dt = (javax.swing.table.DefaultTableModel) grilla.getModel();
        grilla.setModel(dt);
        dt.setRowCount(0);
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btngestionarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngestionarKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btngestionar.doClick();
        }
    }//GEN-LAST:event_btngestionarKeyPressed

    private void btnregistrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnregistrarKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnregistrar.doClick();
        }
    }//GEN-LAST:event_btnregistrarKeyPressed

    private void btncancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            cabecera1();
            cargadatos();
            cargausuario();
            desbloquear.setTitle("DESBLOQUEAR USUARIOS");
            ImageIcon icono = new ImageIcon("\\flyrock\\src\\imagenes\\Sin título.png");
            this.desbloquear.setIconImage(icono.getImage());
            this.desbloquear.pack();
            this.desbloquear.setLocationRelativeTo(null);
            this.desbloquear.setModal(true);
            grdusuarios.requestFocus();
            this.desbloquear.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void grdusuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdusuariosMouseClicked
        String Codigo = (String) modelo.getValueAt(grdusuarios.getSelectedRow(), 0).toString();
        usuario = (String) modelo.getValueAt(grdusuarios.getSelectedRow(), 1).toString();
        personal = Integer.parseInt(Codigo);
        JOptionPane.showMessageDialog(null, "Para DESBLOQUEAR presione Desbloquear", "Atención", JOptionPane.INFORMATION_MESSAGE);
        btnanular.setEnabled(true);
        btnanular.requestFocus();
    }//GEN-LAST:event_grdusuariosMouseClicked

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        int mensaje = JOptionPane.showConfirmDialog(this, "Deseas DESBLOQUEAR el usuario -->"  + usuario, "Confirmar", JOptionPane.YES_NO_OPTION);
        if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
        {
            try {
                desbloqueo();
                cargadatos();
                cargausuario();
                JOptionPane.showMessageDialog(null, "El usuario se ha desbloqueado correctamente", "Atención", JOptionPane.INFORMATION_MESSAGE);
                btnanular.setEnabled(false);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else // no quiere borrar
        {
            JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
            btnanular.setEnabled(false);
        }
    }//GEN-LAST:event_btnanularActionPerformed

    private void cbopersonalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbopersonalKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
{
            try {
                personalcbo = buscausuario((String) cbopersonal.getSelectedItem());
                String SQL;
                SQL = "SELECT * FROM pantallas";
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery(SQL);
                modelo1.setRowCount(0);
                Object[] datos = new Object[3];
                while (conn.resultado.next()) {
                datos[0] = conn.resultado.getObject(1);
                datos[1] = conn.resultado.getObject(2);
                datos[2] = new Boolean(false);
                modelo1.addRow(datos);  
                }
                grilla.requestFocus();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(permisos.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    }//GEN-LAST:event_cbopersonalKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.jButton1.doClick();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void grdusuariosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdusuariosKeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
           String Codigo = (String) modelo.getValueAt(grdusuarios.getSelectedRow(), 0).toString();
        usuario = (String) modelo.getValueAt(grdusuarios.getSelectedRow(), 1).toString();
        personal = Integer.parseInt(Codigo);
        JOptionPane.showMessageDialog(null, "Para DESBLOQUEAR presione Desbloquear", "Atención", JOptionPane.INFORMATION_MESSAGE);
        btnanular.setEnabled(true);
        btnanular.requestFocus(); 
        }
    }//GEN-LAST:event_grdusuariosKeyPressed

    private void btnanularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnanularKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnanular.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnanularKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btncancelar;
    public static javax.swing.JButton btngestionar;
    private javax.swing.JButton btnregistrar;
    private javax.swing.JComboBox cbopersonal;
    private javax.swing.JDialog desbloquear;
    private javax.swing.JTable grdusuarios;
    private javax.swing.JTable grilla;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
