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
public class items extends javax.swing.JPanel {
int operacion,unidad,unidad1;
DefaultTableModel modelo = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    public items() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initComponents();
        des_btn();
        cabeceragrilla();
        cargagrilla();
        grilla.setEnabled(false);
        cargamedidas();
        cargamedidas1();
    }

private void gencod(){
    try
	{
	ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(ite_codigo),0)+1 AS xxx FROM items");
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
    conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri from items order by ite_descri");
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
        modelo.addColumn("DESCRIPCIÓN");
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
            String SQL = "call abm_items("+operacion+","+txtcodigo.getText()+",'"+txtdescripcion.getText()+"',"+unidad+","+txtpreci.getText()+","+unidad1+","+txtmedcome.getText()+")";
            ver_conex conn =new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();          
            conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
            conn.sentencia.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error en la operación o bien esta intentando borrar un Item en uso", "", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
        }
}

private void lim_text(){
txtcodigo.setText("");
txtdescripcion.setText("");
txtpreci.setText("");
txtmedcome.setText("");
}

private void des_text(){
txtcodigo.setEnabled(false);
txtdescripcion.setEnabled(false);
txtpreci.setEnabled(false);
txtmedcome.setText("");
}

    public void cargamedidas() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
String SQL = "Select uni_descri from unidades order by uni_descri";
ver_conex  conn = new ver_conex();
conn.sentencia = conn.conexion.createStatement();
conn.resultado = conn.sentencia.executeQuery(SQL);
jComboBox1.removeAllItems();
while (conn.resultado.next()) {
jComboBox1.addItem(conn.resultado.getString("uni_descri"));
}
}
    
        public void cargamedidas1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
String SQL = "Select uni_descri from unidades order by uni_descri";
ver_conex  conn = new ver_conex();
conn.sentencia = conn.conexion.createStatement();
conn.resultado = conn.sentencia.executeQuery(SQL);
jComboBox2.removeAllItems();
while (conn.resultado.next()) {
jComboBox2.addItem(conn.resultado.getString("uni_descri"));
}
}

    
         public int buscaunidades(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
    String SQL = "SELECT * from unidades where uni_descri = '" + jComboBox1.getSelectedItem().toString() +"'" ;
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery(SQL);
    conn.resultado.next();
    return(conn.resultado.getInt("uni_codigo"));
    }
         
         public int buscaunidades1(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
    String SQL = "SELECT * from unidades where uni_descri = '" + jComboBox2.getSelectedItem().toString() +"'" ;
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery(SQL);
    conn.resultado.next();
    return(conn.resultado.getInt("uni_codigo"));
    }
         
    public double Redondear(double numero)
{
 return Math.rint(numero*100)/100;
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
        txtdescripcion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtpreci = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtmedcome = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();

        setMaximumSize(new java.awt.Dimension(510, 305));
        setMinimumSize(new java.awt.Dimension(510, 305));
        setPreferredSize(new java.awt.Dimension(510, 305));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Descripción:");

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
                .addContainerGap()
                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
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

        txtdescripcion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtdescripcion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdescripcion.setEnabled(false);
        txtdescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdescripcionActionPerformed(evt);
            }
        });
        txtdescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescripcionKeyTyped(evt);
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

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Unidad:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setEnabled(false);
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Precio:");

        txtpreci.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtpreci.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtpreci.setEnabled(false);
        txtpreci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpreciMouseClicked(evt);
            }
        });
        txtpreci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpreciActionPerformed(evt);
            }
        });
        txtpreci.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpreciKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("M.Comercial:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("U. Comercial:");

        txtmedcome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmedcome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtmedcome.setEnabled(false);
        txtmedcome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmedcomeMouseClicked(evt);
            }
        });
        txtmedcome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmedcomeActionPerformed(evt);
            }
        });
        txtmedcome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmedcomeKeyTyped(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setEnabled(false);
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtmedcome, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtpreci, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtmedcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtpreci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void txtdescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdescripcionActionPerformed
        String campo = txtdescripcion.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            if (operacion == 1){
                try {
                //Comprobar que no exista el registro con esa descripcion
                txtdescripcion.setText(campo.toUpperCase());
                ver_conex conn = new ver_conex();//instanciamos
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT `ite_descri` FROM `items` WHERE `ite_descri` = '" + txtdescripcion.getText() + "'");
                boolean encontro = conn.resultado.next();
                if (encontro == true)//encontro
                {
                    JOptionPane.showMessageDialog(null, "Ya existe una Item con esta Descripción!: " + txtdescripcion.getText(), "Atención", JOptionPane.ERROR_MESSAGE);
                    txtdescripcion.requestFocus();
                    txtdescripcion.setText("");
                } else // no encontro
                {
                    jComboBox1.setEnabled(true);
                    jComboBox1.requestFocus();
                    txtdescripcion.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
            }
          } else {
                 txtdescripcion.setText(campo.toUpperCase());
                 jComboBox1.setEnabled(true);
                 jComboBox1.requestFocus();
                 txtdescripcion.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtdescripcionActionPerformed

    private void txtdescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionKeyTyped
        if (txtdescripcion.getText().length() == 50) {
            txtdescripcion.setText(txtdescripcion.getText().toUpperCase());
            txtdescripcion.setEnabled(false);
            btngrabar.setEnabled(true);
            btngrabar.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtdescripcion.getText().length() == 50) {
                txtdescripcion.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtdescripcionKeyTyped

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
            unidad = buscaunidades ((String) jComboBox1.getSelectedItem());
            unidad1 = buscaunidades1 ((String) jComboBox2.getSelectedItem());
            operaciones();
            btncancelar.doClick();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        try {
            cargagrilla();
            jComboBox1.setEnabled(false);
            grilla.setEnabled(false);
            des_btn();
            lim_text();
            des_text();
        } catch (SQLException ex) {
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
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
        txtdescripcion.setEnabled(true);
        txtdescripcion.requestFocus();
    }//GEN-LAST:event_btnagregarActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void grillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grillaMouseClicked
        try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
                    String descrip = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
                    txtcodigo.setText(codigo);
                    txtdescripcion.setText(descrip);
                    ver_conex conn = new ver_conex();//instanciamos
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM items WHERE ite_codigo = " + codigo);
                    conn.resultado.next();
                    txtpreci.setText(conn.resultado.getString("ite_costo"));
                    txtmedcome.setText(conn.resultado.getString("ite_medcom"));
                    int uni1 = Integer.parseInt(conn.resultado.getString("uni_precom"));
                    int uni = Integer.parseInt(conn.resultado.getString("uni_codigo"));
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM unidades WHERE uni_codigo = " + uni);
                    conn.resultado.next();
                    jComboBox1.setSelectedItem(conn.resultado.getString("uni_descri"));
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM unidades WHERE uni_codigo = " + uni1);
                    conn.resultado.next();
                    jComboBox2.setSelectedItem(conn.resultado.getString("uni_descri"));
                    if(operacion == 3){
                    txtdescripcion.setEnabled(true);
                    txtdescripcion.requestFocus();
                    grilla.setEnabled(false);
                    } else {
                          int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtdescripcion.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
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
            Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
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

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            try {
                String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
                    String descrip = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
                    txtcodigo.setText(codigo);
                    txtdescripcion.setText(descrip);
                    ver_conex conn = new ver_conex();//instanciamos
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM items WHERE ite_codigo = " + codigo);
                    conn.resultado.next();
                    txtpreci.setText(conn.resultado.getString("ite_costo"));
                    txtmedcome.setText(conn.resultado.getString("ite_medcom"));
                    int uni1 = Integer.parseInt(conn.resultado.getString("uni_precom"));
                    int uni = Integer.parseInt(conn.resultado.getString("uni_codigo"));
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM unidades WHERE uni_codigo = " + uni);
                    conn.resultado.next();
                    jComboBox1.setSelectedItem(conn.resultado.getString("uni_descri"));
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM unidades WHERE uni_codigo = " + uni1);
                    conn.resultado.next();
                    jComboBox2.setSelectedItem(conn.resultado.getString("uni_descri"));
                    if(operacion == 3){
                    txtdescripcion.setEnabled(true);
                    txtdescripcion.requestFocus();
                    grilla.setEnabled(false);
                    } else {
                          int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtdescripcion.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
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
                Logger.getLogger(items.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grillaKeyPressed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {   
           txtmedcome.setEnabled(true);
           jComboBox1.setEnabled(false);
           txtmedcome.requestFocus();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void txtpreciMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpreciMouseClicked
       // TODO add your handling code here:
    }//GEN-LAST:event_txtpreciMouseClicked

    private void txtpreciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpreciActionPerformed
        String campo = txtpreci.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtpreci.setText(String.valueOf(Redondear(Float.parseFloat(txtpreci.getText()))));
            btngrabar.setEnabled(true);
            btngrabar.requestFocus();
            txtpreci.setEnabled(false);
        }
    }//GEN-LAST:event_txtpreciActionPerformed

    private void txtpreciKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreciKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtpreci.getText().contains(".")) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtpreciKeyTyped

    private void txtmedcomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmedcomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmedcomeMouseClicked

    private void txtmedcomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmedcomeActionPerformed
 String campo = txtmedcome.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtmedcome.setText(String.valueOf(Redondear(Float.parseFloat(txtmedcome.getText()))));
            jComboBox2.setEnabled(true);
            jComboBox2.requestFocus();
            txtmedcome.setEnabled(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtmedcomeActionPerformed

    private void txtmedcomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmedcomeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmedcomeKeyTyped

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {   
           txtpreci.setEnabled(true);
           jComboBox2.setEnabled(false);
           txtpreci.requestFocus();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnborrar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngrabar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JTable grilla;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtmedcome;
    private javax.swing.JTextField txtpreci;
    // End of variables declaration//GEN-END:variables
}
