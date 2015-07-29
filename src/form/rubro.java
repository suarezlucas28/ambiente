/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.Statement;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import prgs.ver_conex;

/**
 *
 * @author LUke
 */
public class rubro extends javax.swing.JPanel {
int operacion,unidad;
private int ab;
java.sql.Statement snt;
ResultSet recur1; 
String citem,item;
javax.swing.table.DefaultTableModel cursor1;
String wqqqq;

DefaultTableModel modelo = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

DefaultTableModel modelo1 = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
     
      Class[] types = new Class [] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };
         
         public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
};

    DefaultTableModel modelo2 = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    public rubro() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initComponents();
        des_btn();
        cabeceragrilla();
        cargagrilla();
        grilla.setEnabled(false);
        cursor1 = (javax.swing.table.DefaultTableModel)grilla1.getModel();
        cargamedidas();
        CabDetalle1();
        cabecerabuscador();
    }

private void gencod(){
    try
	{
	ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(rub_codigo),0)+1 AS xxx FROM rubro");
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
    conn.resultado = conn.sentencia.executeQuery("SELECT rub_codigo,rub_descri from rubro order by rub_descri");
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

}

private void lim_text(){
txtcodigo.setText("");
txtdescripcion.setText("");
txt_item_cod.setText("");
lbldescri.setText("");
txtcantidad.setText("");
}



private void des_text(){
txtcodigo.setEnabled(false);
txtdescripcion.setEnabled(false);
txt_item_cod.setEnabled(false);
lbldescri.setEnabled(false);
txtcantidad.setEnabled(false);
}

    public void cargamedidas() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
String SQL = "Select uni_descri from unidades order by uni_descri";
ver_conex  conn = new ver_conex();
conn.sentencia = conn.conexion.createStatement();
conn.resultado = conn.sentencia.executeQuery(SQL);
jComboBox3.removeAllItems();
while (conn.resultado.next()) {
jComboBox3.addItem(conn.resultado.getString("uni_descri"));
}
}

    public void grilla1()
       {
         String ac;
          modelo2.setRowCount(0);
        
        try {
            ver_conex conn2 =new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1=snt.executeQuery("SELECT ite_codigo,ite_descri FROM items WHERE ite_descri LIKE '%"+wqqqq+"%'");
            System.out.println(recur1);
            if (recur1.wasNull())
            {
                    ab=0;
                    return;
            }
            
                if(recur1.next()) {
                  do {
                    ab = recur1.getInt("ite_codigo");
                    ac = recur1.getString("ite_descri");
                    
                    
                    modelo2.addRow(new Object[]{ab,ac});
                } while(recur1.next());
                } else {
                    ab=0;
                }

            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis" , "Verifique",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
         public int buscaunidades(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
    String SQL = "SELECT * from unidades where uni_descri = '" + jComboBox3.getSelectedItem().toString() +"'" ;
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
    
    private void CabDetalle1(){
modelo1.addColumn("CÓDIGO");
modelo1.addColumn("DESCRIPCIÓN");
modelo1.addColumn("CANTIDAD");
modelo1.addColumn("MEDIDA");
grilla1.getColumnModel().getColumn(0).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(1).setPreferredWidth(101);
grilla1.getColumnModel().getColumn(2).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(3).setPreferredWidth(31);
DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
tcr.setHorizontalAlignment(SwingConstants.RIGHT);
grilla1.getColumnModel().getColumn(3).setCellRenderer(tcr);
grilla1.getColumnModel().getColumn(2).setCellRenderer(tcr);
}
    
    private void cabecerabuscador(){
    modelo2.addColumn("CODIGO");
    modelo2.addColumn("DESCRIPCIÓN");
    grilla2.getColumnModel().getColumn(0).setPreferredWidth(31);
    grilla2.getColumnModel().getColumn(1).setPreferredWidth(191);
}
    
    public double getDecimal(int numeroDecimales,double decimal){
decimal = decimal*(java.lang.Math.pow(10, numeroDecimales));
decimal = java.lang.Math.round(decimal);
decimal = decimal/java.lang.Math.pow(10, numeroDecimales);

return decimal;  }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buscador = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        grilla2 = new javax.swing.JTable();
        txtbuscador = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
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
        jComboBox2 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_item_cod = new javax.swing.JTextField();
        lbldescri = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtcantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        grilla1 = new javax.swing.JTable();

        jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane3MouseClicked(evt);
            }
        });

        grilla2.setModel(modelo2);
        grilla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grilla2MouseClicked(evt);
            }
        });
        grilla2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grilla2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(grilla2);

        txtbuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscadorActionPerformed(evt);
            }
        });
        txtbuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscadorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscadorKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Buscador:");

        javax.swing.GroupLayout buscadorLayout = new javax.swing.GroupLayout(buscador.getContentPane());
        buscador.getContentPane().setLayout(buscadorLayout);
        buscadorLayout.setHorizontalGroup(
            buscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(buscadorLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                .addContainerGap())
        );
        buscadorLayout.setVerticalGroup(
            buscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setMaximumSize(new java.awt.Dimension(550, 505));
        setMinimumSize(new java.awt.Dimension(550, 505));
        setPreferredSize(new java.awt.Dimension(550, 505));

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
        jLabel3.setText("Detalle:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        jComboBox1.setEnabled(false);
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Estado:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CERRADO", "ABIERTO" }));
        jComboBox2.setEnabled(false);
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Material:");

        txt_item_cod.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_item_cod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_item_cod.setEnabled(false);
        txt_item_cod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_item_codMouseClicked(evt);
            }
        });
        txt_item_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_item_codActionPerformed(evt);
            }
        });
        txt_item_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_item_codKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_item_codKeyTyped(evt);
            }
        });

        lbldescri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad:");

        txtcantidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcantidad.setEnabled(false);
        txtcantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidadActionPerformed(evt);
            }
        });
        txtcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidadKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Unidad:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setEnabled(false);
        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        grilla1.setModel(modelo1);
        grilla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grilla1MouseClicked(evt);
            }
        });
        grilla1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grilla1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(grilla1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtdescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, 134, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                conn.resultado = conn.sentencia.executeQuery("SELECT `rub_descri` FROM `rubro` WHERE `rub_descri` = '" + txtdescripcion.getText() + "'");
                boolean encontro = conn.resultado.next();
                if (encontro == true)//encontro
                {
                    JOptionPane.showMessageDialog(null, "Ya existe un Rubro con esta Descripción!: " + txtdescripcion.getText(), "Atención", JOptionPane.ERROR_MESSAGE);
                    txtdescripcion.requestFocus();
                    txtdescripcion.setText("");
                } else // no encontro
                {
                    jComboBox1.setEnabled(true);
                    jComboBox1.requestFocus();
                    txtdescripcion.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(rubro.class.getName()).log(Level.SEVERE, null, ex);
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
           if (operacion == 1){
            try {
                agregar();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
            } else if (operacion == 3){
            try {
                modificar();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            btncancelar.doClick();
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        try {
            cargagrilla();
            jComboBox1.setEnabled(false);
            jComboBox2.setEnabled(false);
            jComboBox3.setEnabled(false);
            grilla.setEnabled(false);
            des_btn();
            lim_text();
            des_text();
            modelo1.setRowCount(0);
            modelo2.setRowCount(0);
        } catch (SQLException ex) {
            Logger.getLogger(rubro.class.getName()).log(Level.SEVERE, null, ex);
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
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM rubro WHERE rub_codigo = " + codigo);
                    conn.resultado.next();
                    jComboBox1.setSelectedItem(conn.resultado.getString("rub_detalle"));
                    jComboBox2.setSelectedItem(conn.resultado.getString("rub_cerrad"));
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT items.ite_codigo,ite_descri,rde_cantid,uni_descri FROM rubro_detalle,items,unidades WHERE rubro_detalle.ite_codigo = items.ite_codigo AND rubro_detalle.uni_codigo = unidades.uni_codigo AND rub_codigo = "+codigo);
                    modelo1.setRowCount(0);
                    Object [] fila = new Object[4];
                    while (conn.resultado.next()){
                         fila[0] = conn.resultado.getObject(1);
                         fila[1] = conn.resultado.getObject(2);
                         fila[2] = conn.resultado.getObject(3);
                         fila[3] = conn.resultado.getObject(4);
                         modelo1.addRow(fila);
                    }
                    conn.resultado.first();
                    if(operacion == 3){
                    txtdescripcion.setEnabled(true);
                    txtdescripcion.requestFocus();
                    grilla.setEnabled(false);
                    } else {
                          int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtdescripcion.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                          if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                          {
                              borrar();
                          }
                          else // no quiere borrar
                          {
                              JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                          }
                          btncancelar.doClick();
                       ////
                    }
            } catch (SQLException ex) {
                Logger.getLogger(rubro.class.getName()).log(Level.SEVERE, null, ex);
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
                    conn.resultado = conn.sentencia.executeQuery("SELECT * FROM rubro WHERE rub_codigo = " + codigo);
                    conn.resultado.next();
                    jComboBox1.setSelectedItem(conn.resultado.getString("rub_detalle"));
                    jComboBox2.setSelectedItem(conn.resultado.getString("rub_cerrad"));
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT items.ite_codigo,ite_descri,rde_cantid,uni_descri FROM rubro_detalle,items,unidades WHERE rubro_detalle.ite_codigo = items.ite_codigo AND rubro_detalle.uni_codigo = unidades.uni_codigo AND rub_codigo = "+codigo);
                    modelo1.setRowCount(0);
                    Object [] fila = new Object[4];
                    while (conn.resultado.next()){
                         fila[0] = conn.resultado.getObject(1);
                         fila[1] = conn.resultado.getObject(2);
                         fila[2] = conn.resultado.getObject(3);
                         fila[3] = conn.resultado.getObject(4);
                         modelo1.addRow(fila);
                    }
                    conn.resultado.first();
                    if(operacion == 3){
                    txtdescripcion.setEnabled(true);
                    txtdescripcion.requestFocus();
                    grilla.setEnabled(false);
                    } else {
                          int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Borrar-->"+txtdescripcion.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                          if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                          {
                              borrar();
                          }
                          else // no quiere borrar
                          {
                              JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                          }
                          btncancelar.doClick();
                       ////
                    }
            } catch (SQLException ex) {
                Logger.getLogger(rubro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grillaKeyPressed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {   
           if(jComboBox1.getSelectedItem().toString().endsWith("NO")){
                try {
                    ver_conex  conn = new ver_conex();
                    conn.sentencia = conn.conexion.createStatement();
                    conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri,1,uni_descri FROM unidades,items WHERE items.uni_codigo = unidades.uni_codigo AND ite_descri LIKE '%GENERICO%'");
                    modelo1.setRowCount(0);
                    Object [] fila = new Object[4];
                    while (conn.resultado.next()){
                         fila[0] = conn.resultado.getObject(1);
                         fila[1] = conn.resultado.getObject(2);
                         fila[2] = conn.resultado.getObject(3);
                         fila[3] = conn.resultado.getObject(4);
                         modelo1.addRow(fila);
                    }
         conn.resultado.first();
                } catch (SQLException ex) {
                    Logger.getLogger(rubro.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           jComboBox2.setEnabled(true);
           jComboBox1.setEnabled(false);
           jComboBox2.requestFocus();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {   
            if(jComboBox1.getSelectedItem().toString().endsWith("NO")){
               btngrabar.setEnabled(true);
               jComboBox2.setEnabled(false);
               btngrabar.requestFocus(); 
            } else {
             txt_item_cod.setEnabled(true);
             jComboBox2.setEnabled(false);
             txt_item_cod.requestFocus();   
            } 
        } 
    }//GEN-LAST:event_jComboBox2KeyPressed

    private void txt_item_codMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_codMouseClicked
        txt_item_cod.setEnabled(true);
        txt_item_cod.setText("");
        txt_item_cod.requestFocus();
    }//GEN-LAST:event_txt_item_codMouseClicked

    private void txt_item_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_codActionPerformed
        String campo = txt_item_cod.getText();
        if (campo.isEmpty()) {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri FROM items");
                modelo2.setRowCount(0);
                Object [] fila = new Object[2];
                while (conn.resultado.next()){
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    modelo2.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscador.setIconImage(icono.getImage());
                buscador.setTitle("MATERIALES");
                buscador.pack();
                buscador.setLocationRelativeTo(null);
                buscador.setModal(true);
                txtbuscador.requestFocus();
                buscador.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_item_codActionPerformed

    private void txt_item_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyPressed

    }//GEN-LAST:event_txt_item_codKeyPressed

    private void txt_item_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyTyped
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

        String asas=txt_item_cod.getText();
        int conta1=asas.length();
        if(evt.getKeyChar()=='0'&&conta1==0){
            evt.consume();
        }else{
            if(Character.isDigit(k)==false) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_item_codKeyTyped

    private void txtcantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidadActionPerformed
        String campo = txtcantidad.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
             txtcantidad.setText(String.valueOf(getDecimal(3,Double.parseDouble(txtcantidad.getText()))));
            jComboBox3.setEnabled(true);
            jComboBox3.requestFocus();
            txtcantidad.setEnabled(false);    
        }
    }//GEN-LAST:event_txtcantidadActionPerformed

    private void txtcantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyPressed

    }//GEN-LAST:event_txtcantidadKeyPressed

    private void txtcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyTyped
        int k = (int) evt.getKeyChar();
        //Este if no permite el ingreso de letras y otros símbolos
        if ((k >= 32 && k <= 45) || (k >= 58 && k <= 126)) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            evt.consume();
        }
        //Esteif no permite el ingreso de "ñ" ," Ñ" ni "/"
        if (k == 241 || k == 209 || k == 47) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            evt.consume();
        }

//        String asas = txtcantidad.getText();
//        int conta1 = asas.length();
//        if (evt.getKeyChar() == '0' && conta1 == 0) {
//            evt.consume();
//        } else {
//            if (Character.isDigit(k) == false) {
//                evt.consume();
//            }
//        }

        if ((k == 65)){
            txt_item_cod.setText("");
            txt_item_cod.requestFocus();
        }

        if ((k == 97)){
            txt_item_cod.setText("");
            txt_item_cod.requestFocus();
        }
    }//GEN-LAST:event_txtcantidadKeyTyped

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            jComboBox1.setEnabled(false);
            tirarGrilla();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void grilla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_grilla1MouseClicked

    private void grilla1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla1KeyPressed
if(evt.getKeyCode() == KeyEvent.VK_DELETE){
                cursor1.removeRow(grilla1.getSelectedRow());
                txt_item_cod.requestFocus();
        }

 if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                cursor1.removeRow(grilla1.getSelectedRow());
                txt_item_cod.requestFocus();
        }            
    }//GEN-LAST:event_grilla1KeyPressed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void grilla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla2MouseClicked
        try {
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                lbldescri.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT uni_descri FROM unidades,items WHERE items.uni_codigo = unidades.uni_codigo AND items.ite_codigo =  " + codigo);
                conn.resultado.next();
                txt_item_cod.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtcantidad.setEnabled(true);
                txtcantidad.requestFocus();
        }
        catch (SQLException ex) {
            Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grilla2MouseClicked

    private void grilla2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla2KeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
             try {
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                lbldescri.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT uni_descri FROM unidades,items WHERE items.uni_codigo = unidades.uni_codigo AND items.ite_codigo =  " + codigo);
                conn.resultado.next();
                txt_item_cod.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtcantidad.setEnabled(true);
                txtcantidad.requestFocus();
        }
        catch (SQLException ex) {
            Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_grilla2KeyPressed

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked

    }//GEN-LAST:event_jScrollPane3MouseClicked

    private void txtbuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscadorActionPerformed

    private void txtbuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscadorKeyPressed

        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_DOWN) {
            grilla2.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscadorKeyPressed

    private void txtbuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscadorKeyReleased
        wqqqq = txtbuscador.getText();
        grilla1();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscadorKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnborrar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngrabar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JDialog buscador;
    private javax.swing.JTable grilla;
    private javax.swing.JTable grilla1;
    private javax.swing.JTable grilla2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbldescri;
    private javax.swing.JTextField txt_item_cod;
    private javax.swing.JTextField txtbuscador;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtdescripcion;
    // End of variables declaration//GEN-END:variables

    private void tirarGrilla() {
        float cant = Float.parseFloat(txtcantidad.getText());
        Object[] datos = new Object[4];
        datos[0] = txt_item_cod.getText();
        datos[1] = lbldescri.getText();
        datos[2] = cant;
        datos[3] = jComboBox3.getSelectedItem().toString();
        modelo1.addRow(datos);
        btngrabar.setEnabled(true);
        txt_item_cod.setText("");
        lbldescri.setText("");
        txtcantidad.setText("");
        jComboBox3.setEnabled(false);
        txt_item_cod.setEnabled(true);
        txt_item_cod.requestFocus();
    }
    
    private void agregar() throws SQLException {
        String SQL = "INSERT INTO rubro(rub_descri,rub_detalle,rub_cerrad) VALUES ('"+txtdescripcion.getText()+"','"+jComboBox1.getSelectedItem().toString()+"','"+jComboBox2.getSelectedItem().toString()+"')";   
        System.out.println(SQL);
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        try {
       javax.swing.table.TableModel model = grilla1.getModel();

               int c = 0;

               for (int i = 0; i < model.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                       Object codigo    =grilla1.getValueAt(i,0);
                       Object cantidad    =grilla1.getValueAt(i,2);
                       Object unidad    =grilla1.getValueAt(i,3);
                       String SQL1 = "SELECT * from unidades where uni_descri = '" + unidad +"'" ;
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery(SQL1);
                        conn.resultado.next();
                        unidad = conn.resultado.getString("uni_codigo");
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO rubro_detalle (ite_codigo,rub_codigo,uni_codigo,rde_cantid) VALUES ("+codigo+","+txtcodigo.getText()+","+unidad+","+cantidad+")";
                       stmta.executeUpdate(insercionSQL1);
                       c++;
                   }
               
               System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

               }catch (SQLException e) {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
               }
               
            }

    private void modificar() throws SQLException {
        String SQL = "UPDATE rubro SET rub_descri = '"+txtdescripcion.getText()+"', rub_detalle = '"+jComboBox1.getSelectedItem().toString()+"', rub_cerrad = '"+jComboBox2.getSelectedItem().toString()+"' WHERE rub_codigo = " + txtcodigo.getText();   
        System.out.println(SQL);
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        String SQL2 = "DELETE FROM rubro_detalle WHERE rub_codigo = "+txtcodigo.getText();
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL2);
        
        try {
       javax.swing.table.TableModel model = grilla1.getModel();

               int c = 0;

               for (int i = 0; i < model.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                       Object codigo    =grilla1.getValueAt(i,0);
                       Object cantidad    =grilla1.getValueAt(i,2);
                       Object unidad    =grilla1.getValueAt(i,3);
                       String SQL1 = "SELECT * from unidades where uni_descri = '" + unidad +"'" ;
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery(SQL1);
                        conn.resultado.next();
                        unidad = conn.resultado.getString("uni_codigo");
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO rubro_detalle (ite_codigo,rub_codigo,uni_codigo,rde_cantid) VALUES ("+codigo+","+txtcodigo.getText()+","+unidad+","+cantidad+")";
                       stmta.executeUpdate(insercionSQL1);
                       c++;
                   }
               
               System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

               }catch (SQLException e) {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
               }
        
    }

    private void borrar() throws SQLException {
         String SQL = "DELETE FROM rubro_detalle WHERE rub_codigo = " + txtcodigo.getText(); 
         String SQL1 = " DELETE FROM rubro WHERE rub_codigo = " + txtcodigo.getText();   
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        conn.sentencia.executeUpdate(SQL1);//OJO LE PASO LA SENTENCIA
    }
}
