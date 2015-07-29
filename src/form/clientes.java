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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prgs.ver_conex;

/**
 *
 * @author LUke
 */
public class clientes extends javax.swing.JPanel {
int operacion = 0,permiso,activida;
static JDialog jgenerador;
DefaultTableModel modelo = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    public clientes() throws SQLException {
        initComponents();
        des_btn();
        cabeceragrilla();
        //cargaactividad();
        cargagrilla();
        grilla.setEnabled(false);
    }

private void gencod(){
    try
	{
	ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(cli_codigo),0)+1 AS xxx FROM cliente");
        conn.resultado.next();
        txtcodigo.setText(conn.resultado.getString("xxx"));

 	}
	catch (SQLException exceptionSql)
	{
		JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
	}
}

private void cargagrilla() throws SQLException{
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery("SELECT cli_codigo,cli_nomape from cliente  where cli_codigo != 0 order by cli_nomape");
    modelo.setRowCount(0);
    Object [] fila = new Object[2];
    while (conn.resultado.next()){
        fila[0] = conn.resultado.getObject(1);
        fila[1] = conn.resultado.getObject(2);
        modelo.addRow(fila);
    }
    conn.resultado.first();
}

private void cabeceragrilla(){
        modelo.addColumn("CODIGO");
        modelo.addColumn("NOMBRE");
        grilla.getColumnModel().getColumn(0).setPreferredWidth(51);
        grilla.getColumnModel().getColumn(1).setPreferredWidth(151);
}

private void hab_btn(){
btnagregar.setEnabled(false);   
btnborrar.setEnabled(false);
btnmodificar.setEnabled(false);
btngrabar.setEnabled(false);
btncancelar.setEnabled(true);
}

private void des_btn(){
btnagregar.setEnabled(true);   
btnagregar.requestFocus();
btnborrar.setEnabled(true);
btnmodificar.setEnabled(true);
btngrabar.setEnabled(false);
btncancelar.setEnabled(false);
}

private void operaciones (){
        try {
            String SQL = "call abm_cliente("+operacion+","+txtcodigo.getText()+",'"+txtnombre.getText()+"','"+txtdireccion.getText()+"','"+txtemail.getText()+"')";
            ver_conex conn =new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();          
            conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
            conn.sentencia.close();
            JOptionPane.showMessageDialog(null, "La operacion se a realizado con éxito satisfactoriamente.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error en la operación o bien esta intentando borrar un Cliente en uso", "", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
}

 /*public void cargaactividad() throws SQLException{
            String SQL = "SELECT * FROM actividad order by act_descri";
            ver_conex  conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery(SQL);
            cboactividad.removeAllItems();
            while (conn.resultado.next()) {
            cboactividad.addItem(conn.resultado.getString("act_descri"));
}
}*/
 
  /*public int buscaactividad(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
    String SQL = "SELECT * from actividad where act_descri = '" + cboactividad.getSelectedItem() +"'" ;
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery(SQL);
    conn.resultado.next();
    return(conn.resultado.getInt("act_codigo"));
}*/

private void lim_text(){
txtcodigo.setText("");
txtnombre.setText("");
txtdireccion.setText("");
txtemail.setText("");
}

private void des_text(){
txtcodigo.setEnabled(false);
txtnombre.setEnabled(false);
txtdireccion.setEnabled(false);
txtemail.setEnabled(false);
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        btnborrar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btngrabar = new javax.swing.JButton();
        txtcodigo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(550, 200));
        setMinimumSize(new java.awt.Dimension(550, 200));
        setPreferredSize(new java.awt.Dimension(550, 200));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Nombre y Apellido:");

        btnagregar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnagregar.setText("Agregar");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });
        btnagregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnagregarKeyPressed(evt);
            }
        });

        btnborrar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnborrar.setText("Borrar");
        btnborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnborrarActionPerformed(evt);
            }
        });
        btnborrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnborrarKeyPressed(evt);
            }
        });

        btnmodificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        btnmodificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnmodificarKeyPressed(evt);
            }
        });

        btncancelar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
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

        btngrabar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btngrabar.setText("Grabar");
        btngrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngrabarActionPerformed(evt);
            }
        });
        btngrabar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btngrabarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnagregar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngrabar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcodigo.setEnabled(false);
        txtcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodigoActionPerformed(evt);
            }
        });
        txtcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoKeyTyped(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        grilla.setModel(modelo);
        grilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grillaMouseClicked(evt);
            }
        });
        grilla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grillaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(grilla);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Dirección:");

        txtdireccion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtdireccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdireccion.setEnabled(false);
        txtdireccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdireccionMouseClicked(evt);
            }
        });
        txtdireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdireccionActionPerformed(evt);
            }
        });
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdireccionKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Telefono:");

        txtemail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtemail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtemail.setEnabled(false);
        txtemail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtemailMouseClicked(evt);
            }
        });
        txtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailActionPerformed(evt);
            }
        });
        txtemail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtemailKeyTyped(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtnombre.setEnabled(false);
        txtnombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtnombreMouseClicked(evt);
            }
        });
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtnombre)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void txtcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyTyped
int k=(int)evt.getKeyChar();
//Este if no permite el ingreso de letras y otros símbolos
if ((k >= 32 && k <= 45) ||(k >= 58 && k <= 126)  ){
 evt.setKeyChar((char)KeyEvent.VK_CLEAR);
 evt.consume();
}
//Esteif no permite el ingreso de "ñ" ," Ñ" ni "/"
if(k==241 || k==209|| k==47){
 evt.setKeyChar((char)KeyEvent.VK_CLEAR);
 evt.consume();
}
    }//GEN-LAST:event_txtcodigoKeyTyped

    private void btngrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngrabarActionPerformed
        /*try {*/
            //activida = buscaactividad((String) cboactividad.getSelectedItem()); 
            operaciones();
            btncancelar.doClick();
        /*} catch (ClassNotFoundException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        try {
            operacion = 0;
            cargagrilla();
            grilla.setEnabled(false);
            des_btn();
            lim_text();
            des_text();
        } catch (SQLException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        operacion = 3;
        hab_btn();
        grilla.setEnabled(true);
        grilla.requestFocus();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnborrarActionPerformed
        operacion = 2;
        hab_btn();
        grilla.setEnabled(true);
        grilla.requestFocus();
    }//GEN-LAST:event_btnborrarActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        operacion = 1;
        hab_btn();
        gencod();
        txtnombre.setEnabled(true);
        txtnombre.requestFocus();
    }//GEN-LAST:event_btnagregarActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void grillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grillaMouseClicked
        try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String descrip = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            txtcodigo.setText(codigo);
            txtnombre.setText(descrip);
            ver_conex conn = new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT * FROM `cliente` WHERE `cli_codigo` = " + codigo);
            conn.resultado.next();
            txtdireccion.setText(conn.resultado.getString("cli_domici"));
            txtemail.setText(conn.resultado.getString("cli_telefo"));
            if(operacion == 3){
            txtnombre.setEnabled(true);
            txtnombre.requestFocus();
            grilla.setEnabled(false);
            } else {
                  int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtnombre.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                  if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                  {
                      operaciones();
                  }
                  else // no quiere borrar
                  {
                      JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                  }
                  btncancelar.doClick();
               ////
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grillaMouseClicked

    private void btnagregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnagregarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnagregar.doClick();
        }
    }//GEN-LAST:event_btnagregarKeyPressed

    private void btnborrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnborrarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnborrar.doClick();
        }
    }//GEN-LAST:event_btnborrarKeyPressed

    private void btnmodificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnmodificarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnmodificar.doClick();
        }
    }//GEN-LAST:event_btnmodificarKeyPressed

    private void btngrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngrabarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btngrabar.doClick();
        }
    }//GEN-LAST:event_btngrabarKeyPressed

    private void btncancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyPressed

    private void txtdireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdireccionActionPerformed
String campo = txtdireccion.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtdireccion.setText(campo.toUpperCase());
            txtemail.setEnabled(true);          
            txtemail.requestFocus();
            txtdireccion.setEnabled(false);
        }
    }//GEN-LAST:event_txtdireccionActionPerformed

    private void txtdireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyTyped
      if (txtdireccion.getText().length() == 30) {
            txtdireccion.setText(txtdireccion.getText().toUpperCase());
            txtdireccion.setEnabled(false);
            txtemail.setEnabled(true);
            txtemail.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtdireccion.getText().length() == 30) {
                txtdireccion.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtdireccionKeyTyped

    private void txtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailActionPerformed
String campo = txtemail.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtemail.setText(campo.toUpperCase());
            btngrabar.setEnabled(true);          
            btngrabar.requestFocus();
            txtemail.setEnabled(false);
        }
    }//GEN-LAST:event_txtemailActionPerformed

    private void txtemailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtemailKeyTyped
      if (txtemail.getText().length() == 30) {
            txtemail.setText(txtemail.getText().toUpperCase());
            txtemail.setEnabled(false);
            btngrabar.setEnabled(true);          
            btngrabar.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtemail.getText().length() == 30) {
                txtemail.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtemailKeyTyped

    private void txtdireccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdireccionMouseClicked
if (operacion != 0){txtdireccion.setEnabled(true);
txtnombre.setEnabled(false);
txtemail.setEnabled(false);
txtdireccion.requestFocus();
}
    }//GEN-LAST:event_txtdireccionMouseClicked

    private void txtemailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtemailMouseClicked
if (operacion != 0){txtemail.setEnabled(true);
txtnombre.setEnabled(false);
txtdireccion.setEnabled(false);
txtemail.requestFocus();
}
    }//GEN-LAST:event_txtemailMouseClicked

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
String campo = txtnombre.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtnombre.setText(campo.toUpperCase());
            txtdireccion.setEnabled(true);          
            txtdireccion.requestFocus();
            txtnombre.setEnabled(false);
        }
    }//GEN-LAST:event_txtnombreActionPerformed

    private void txtnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyTyped
      if (txtnombre.getText().length() == 50) {
            txtnombre.setText(txtnombre.getText().toUpperCase());
            txtnombre.setEnabled(false);
            txtdireccion.setEnabled(true);
            txtdireccion.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtnombre.getText().length() == 50) {
                txtnombre.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtnombreKeyTyped

    private void txtnombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnombreMouseClicked
if (operacion != 0){txtnombre.setEnabled(true);
txtdireccion.setEnabled(false);
txtemail.setEnabled(false);
txtnombre.requestFocus();
}
    }//GEN-LAST:event_txtnombreMouseClicked

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
        try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String descrip = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            txtcodigo.setText(codigo);
            txtnombre.setText(descrip);
            ver_conex conn = new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT * FROM `cliente` WHERE `cli_codigo` = " + codigo);
            conn.resultado.next();
            txtdireccion.setText(conn.resultado.getString("cli_domici"));
            txtemail.setText(conn.resultado.getString("cli_telefo"));
            if(operacion == 3){
            txtnombre.setEnabled(true);
            txtnombre.requestFocus();
            grilla.setEnabled(false);
            } else {
                  int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtnombre.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                  if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                  {
                      operaciones();
                  }
                  else // no quiere borrar
                  {
                      JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                  }
                  btncancelar.doClick();
               ////
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_grillaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnborrar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngrabar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JTable grilla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
