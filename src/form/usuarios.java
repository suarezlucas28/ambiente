/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prgs.ver_conex;

/**
 *
 * @author LUke
 */
public class usuarios extends javax.swing.JPanel {
int operacion,sucursal;
String contraseña;
DefaultTableModel modelo = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    public usuarios() throws SQLException {
        initComponents();
        des_btn();
        cabeceragrilla();
        cargagrilla();
        grilla.setEnabled(false);
    }
    
private void gencod(){
    try
	{
	ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(usu_codigo),0)+1 AS xxx FROM usuarios");
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
    conn.resultado = conn.sentencia.executeQuery("SELECT usu_codigo,usu_usuari from usuarios where usu_estado like 'ACTIVO' order by usu_usuari");
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
        modelo.addColumn("USUARIO");
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

private void operaciones () throws SQLException{
String SQL = "CALL abm_usuarios("+operacion+","+txtcodigo.getText()+",'"+txtusuario.getText()+"','"+contraseña+"')";
ver_conex conn =new ver_conex();//instanciamos
conn.sentencia = conn.conexion.createStatement();          
conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
conn.sentencia.close();
JOptionPane.showMessageDialog(null, "La operacion se a realizado con éxito satisfactoriamente.", "", JOptionPane.INFORMATION_MESSAGE);
}

private void lim_text(){
txtcodigo.setText("");
txtusuario.setText("");
txtcontraseña.setText("");
}

private void des_text(){
txtcodigo.setEnabled(false);
txtusuario.setEnabled(false);
txtcontraseña.setEnabled(false);
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        btnborrar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btngrabar = new javax.swing.JButton();
        txtcodigo = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        txtcontraseña = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(499, 175));
        setMinimumSize(new java.awt.Dimension(499, 175));
        setPreferredSize(new java.awt.Dimension(499, 175));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Usuario:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Contraseña:");

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
                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        txtusuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtusuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtusuario.setEnabled(false);
        txtusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusuarioActionPerformed(evt);
            }
        });
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtusuarioKeyTyped(evt);
            }
        });

        txtcontraseña.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcontraseña.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcontraseña.setEnabled(false);
        txtcontraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcontraseñaActionPerformed(evt);
            }
        });
        txtcontraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcontraseñaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcontraseñaKeyTyped(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void txtcontraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcontraseñaActionPerformed
        String campo = txtcontraseña.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            try {
                String SQL = "SELECT MD5('"+campo+"') as md5";
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery(SQL);
                conn.resultado.next();
                contraseña = conn.resultado.getString("md5");
                btngrabar.setEnabled(true);
                btngrabar.requestFocus();
                txtcontraseña.setEnabled(false);
            } catch (SQLException ex) {
                Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtcontraseñaActionPerformed

    private void txtcontraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcontraseñaKeyTyped
        if (txtcontraseña.getText().length() == 100) {
            try {
                txtcontraseña.setEnabled(false);
                String SQL = "SELECT md5('"+txtcontraseña.getText()+"') as md5";
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery(SQL);
                conn.resultado.next();
                contraseña = conn.resultado.getString("md5");
                btngrabar.setEnabled(true);
                btngrabar.requestFocus();
                txtcontraseña.setEnabled(false);
            } catch (SQLException ex) {
                Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtcontraseña.getText().length() == 100) {
                txtcontraseña.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtcontraseñaKeyTyped

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
        try {
            operaciones();
            btncancelar.doClick();
        } catch (SQLException ex) {
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        try {
            cargagrilla();
            grilla.setEnabled(false);
            des_btn();
            lim_text();
            des_text();
        } catch (SQLException ex) {
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
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
        txtusuario.setEnabled(true);
        txtusuario.requestFocus();
    }//GEN-LAST:event_btnagregarActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void grillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grillaMouseClicked
       try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String usuario = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            txtcodigo.setText(codigo);
            txtusuario.setText(usuario);
            if(operacion == 3){
            txtusuario.setEnabled(true);
            txtusuario.requestFocus();
            grilla.setEnabled(false);
            } else {
                  int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtusuario.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
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
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grillaMouseClicked

    private void txtcontraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcontraseñaKeyPressed

    }//GEN-LAST:event_txtcontraseñaKeyPressed

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

    private void txtusuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c) == false) {
        } else {
            evt.consume();
        }
        if (txtusuario.getText().length() == 30) {
            txtusuario.setText(txtusuario.getText().toUpperCase());
            txtusuario.setEnabled(false);
            txtcontraseña.setEnabled(true);
            txtcontraseña.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtusuario.getText().length() == 30) {
                txtusuario.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtusuarioKeyTyped

    private void txtusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusuarioActionPerformed
        String campo = txtusuario.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            if (operacion == 1){
                try {
                    //Comprobar que no exista el registro con esa descripcion
                    txtusuario.setText(campo.toUpperCase());
                    ver_conex conn = new ver_conex();//instanciamos
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT `usu_usuari` FROM `usuarios` WHERE `usu_usuari` = '" + txtusuario.getText() + "'");
                    boolean encontro = conn.resultado.next();
                    if (encontro == true)//encontro
                    {
                        JOptionPane.showMessageDialog(null, "Ya existe Usuario con esta Nombre!: " + txtusuario.getText(), "Atención", JOptionPane.ERROR_MESSAGE);
                        txtusuario.requestFocus();
                        txtusuario.setText("");
                    } else // no encontro
                    {
                        txtcontraseña.setEnabled(true);
                        txtcontraseña.requestFocus();
                        txtusuario.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                txtusuario.setText(campo.toUpperCase());
                txtcontraseña.setEnabled(true);
                txtcontraseña.requestFocus();
                txtusuario.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtusuarioActionPerformed

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String usuario = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            txtcodigo.setText(codigo);
            txtusuario.setText(usuario);
            if(operacion == 3){
            txtusuario.setEnabled(true);
            txtusuario.requestFocus();
            grilla.setEnabled(false);
            } else {
                  int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtusuario.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
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
            Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtcontraseña;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables


}
