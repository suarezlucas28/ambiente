/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.ResultSet;
import java.awt.Dialog;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import prgs.ver_conex;


public class inforetiros1 extends javax.swing.JPanel {

    public ResultSet resu;
    public String sentencia;
    public String reporte,mask;
    public int cliente,operacion;
    String propietario;
    
    public inforetiros1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        cargacliente();
        des_op();
        des_btn();
    }
    
    private void hab_btn(){
    btnnuevo.setEnabled(false);
    btngenerar.setEnabled(false);
    btncancelar.setEnabled(true);
    }
    
    private void des_btn(){
    btnnuevo.setEnabled(true);
    btnnuevo.requestFocus();
    btngenerar.setEnabled(false);
    btncancelar.setEnabled(false);
    }
    
    private void lim_text(){
    txtcodigo.setText("");
    txtfdesde.setText("");
    txtfhasta.setText("");
    }

    private void hab_op(){
    op1.setEnabled(true);  
    op2.setEnabled(true);  
    op3.setEnabled(true);  
    op4.setEnabled(true);
    op8.setEnabled(true);
    op1.setSelected(false);  
    op2.setSelected(false);  
    op3.setSelected(false);  
    op4.setSelected(false);
    op8.setSelected(false);
    }
    
    private void des_op(){
    op1.setEnabled(false);  
    op2.setEnabled(false);  
    op3.setEnabled(false);  
    op4.setEnabled(false);  
    op8.setEnabled(false);
    op1.setSelected(false);  
    op2.setSelected(false);  
    op3.setSelected(false);  
    op4.setSelected(false);
    op8.setSelected(false);
    }
    
    //carga combo cliente
    public void cargacliente() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
    String SQL = "SELECT pro_nombre FROM proveedor ORDER BY pro_nombre";
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery(SQL);
    cbo_cliente.removeAllItems();
    while (conn.resultado.next()) {
    cbo_cliente.addItem(conn.resultado.getString("pro_nombre"));
    }
    }
    
    public void gen_reporte() throws JRException {
    try {
    if (op1.isSelected()){
    sentencia = "SELECT retiros1.`ret_codigo` AS retiros_ret_codigo,proveedor.`pro_nombre` AS proveedor_pro_nombre,ret_destin AS obras_obr_proyec,retiros1.`ret_fechor` AS retiros_ret_fechor,retiros1.`ret_monto` AS retiros_ret_monto,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `retiros1` retiros1 ON usuarios.`usu_codigo` = retiros1.`usu_codigo` INNER JOIN `proveedor` proveedor ON retiros1.`pro_codigo` = proveedor.`pro_codigo` WHERE ret_estado LIKE 'ACTIVO' ORDER BY ret_codigo DESC";    
    } else if (op2.isSelected()){
    sentencia = "SELECT retiros1.ret_codigo,DAY(`ret_fechor`) AS dia, MONTH(`ret_fechor`) AS mes, YEAR(`ret_fechor`) AS anno,pro_nombre,ret_destin,rdm_cantid,ite_descri,rdm_pagado,rdm_unitari,rdm_subtot FROM items,retiros1,proveedor,retiros_detalle_material1 WHERE retiros1.pro_codigo = proveedor.pro_codigo and retiros_detalle_material1.ret_codigo = retiros1.ret_codigo AND retiros_detalle_material1.ite_codigo = items.ite_codigo AND retiros1.ret_codigo = "+txtcodigo.getText();
    } else if (op3.isSelected()){
    sentencia = "SELECT retiros1.`ret_codigo` AS retiros_ret_codigo,proveedor.`pro_nombre` AS proveedor_pro_nombre,ret_destin AS obras_obr_proyec,retiros1.`ret_fechor` AS retiros_ret_fechor,retiros1.`ret_monto` AS retiros_ret_monto,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `retiros1` retiros1 ON usuarios.`usu_codigo` = retiros1.`usu_codigo` INNER JOIN `proveedor` proveedor ON retiros1.`pro_codigo` = proveedor.`pro_codigo` WHERE ret_estado LIKE 'ACTIVO' AND LEFT(`ret_fechor`,10) BETWEEN  '"+txtfdesde.getText()+"' AND '"+txtfhasta.getText()+"' ORDER BY ret_codigo DESC";    
    } else if (op4.isSelected()){
    sentencia = "SELECT retiros1.`ret_codigo` AS retiros_ret_codigo,proveedor.`pro_nombre` AS proveedor_pro_nombre,ret_destin AS obras_obr_proyec,retiros1.`ret_fechor` AS retiros_ret_fechor,retiros1.`ret_monto` AS retiros_ret_monto,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `retiros1` retiros1 ON usuarios.`usu_codigo` = retiros1.`usu_codigo` INNER JOIN `proveedor` proveedor ON retiros1.`pro_codigo` = proveedor.`pro_codigo` WHERE ret_estado LIKE 'ACTIVO' AND `retiros1`.`pro_codigo` = "+cliente+" ORDER BY ret_codigo DESC";    
    } else if (op8.isSelected()){
    sentencia = "SELECT retiros1.`ret_codigo` AS retiros_ret_codigo,proveedor.`pro_nombre` AS proveedor_pro_nombre,ret_destin AS obras_obr_proyec,retiros1.`ret_fechor` AS retiros_ret_fechor,retiros1.`ret_monto` AS retiros_ret_monto,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `retiros1` retiros1 ON usuarios.`usu_codigo` = retiros1.`usu_codigo` INNER JOIN `proveedor` proveedor ON retiros1.`pro_codigo` = proveedor.`pro_codigo` WHERE ret_estado LIKE 'ANULADO' ORDER BY ret_codigo DESC";    
    }
    
    menu.jgenerador.setModal(false);
    menu.jgenerador.setVisible(false);
    ////////////////////////////////instanciamos
    System.out.println(sentencia);
    ver_conex conn =new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    resu = (com.mysql.jdbc.ResultSet) conn.sentencia.executeQuery(sentencia);//OJO LE PASO LA SENTENCIA
    } catch (Exception ex) {
    }
    JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
    des_op();
    des_btn();
    lim_text();
    jLabel2.setText("Código");
    jLabel4.setText("Fecha Desde:");
    jLabel5.setText("Fecha Hasta:");
    cbo_cliente.setEnabled(false);
    HashMap parameters = new HashMap();
    try{
    ////////////////////////////// preapra el patch
    URL urlMaestro = getClass().getClassLoader().getResource("repo/"+reporte+".jasper");
    ////////////////////////////// Cargamos el reporte
    JasperReport masterReport = null;
    masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
    JasperPrint masterPrint = null;
    ////////////////////////////// pasa el patch y paametros
    masterPrint = JasperFillManager.fillReport(masterReport, parameters,jrRS);
    JasperViewer ventana = new JasperViewer(masterPrint,false);
    ventana.setTitle("Vista Previa");
    ventana.setVisible(true);
    ventana.setExtendedState(ventana.MAXIMIZED_BOTH);
    ventana.addWindowListener(new WindowAdapter () {
         public void windowClosing(WindowEvent  e) {
       menu.jgenerador.setModal(true);
       menu.jgenerador.setVisible(true);
    }});
    }catch(JRException e){
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
    }   
    resu = null;
    }
    
    public int buscacliente(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
    String SQL = "SELECT * FROM proveedor WHERE pro_nombre ='" + a +"'";
    ver_conex  conn = new ver_conex();
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery(SQL);
    conn.resultado.next();
    return(conn.resultado.getInt("pro_codigo"));
    }  
    
    private void gen_reporte2() throws JRException {
        int presupuesto = Integer.parseInt(txtcodigo.getText());
        /////////////////////////////////instanciamos
        ver_conex conn = new ver_conex();
        HashMap parameters = new HashMap();
        ///////////////////////////////// preparamos parametros del reporte
        parameters.put("obra", presupuesto);
        parameters.put("SUBREPORT_DIR", "\\ambiente\\src\\repo\\");
        ///////////////////////////////// patch de los reporetes
        URL urlMaestro = getClass().getClassLoader().getResource("repo/rpcanmatuti.jasper");
        ///////////////////////////////// Cargamos el reporte
        des_op();
        des_btn();
        lim_text();
        jLabel2.setText("Código");
        txtcodigo.setText("");
        jLabel4.setText("Fecha Desde:");
        jLabel5.setText("Fecha Hasta:");
        cbo_cliente.setEnabled(false);
        JasperReport masterReport = null;
        masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
        JasperPrint masterPrint = null;
        menu.jgenerador.setModal(false);
        menu.jgenerador.setVisible(false);
        //////////////////////////////// paso de parametros y patch
        masterPrint = JasperFillManager.fillReport(masterReport, parameters, conn.conexion);
        JasperViewer ventana1 = new JasperViewer(masterPrint, false);
        ventana1.setTitle("Vista Previa");
        ventana1.setVisible(true);
        ventana1.setExtendedState(ventana1.MAXIMIZED_BOTH);
        ventana1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    menu.jgenerador.setModal(true);
                    menu.jgenerador.setVisible(true);
                
            }
        });

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendario = new javax.swing.JDialog();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        op1 = new javax.swing.JRadioButton();
        op3 = new javax.swing.JRadioButton();
        op2 = new javax.swing.JRadioButton();
        op4 = new javax.swing.JRadioButton();
        op8 = new javax.swing.JRadioButton();
        txtcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbo_cliente = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btngenerar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        txtfhasta = new javax.swing.JTextField();
        txtfdesde = new javax.swing.JTextField();

        calendario.setResizable(false);

        jButton1.setText("Listo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout calendarioLayout = new javax.swing.GroupLayout(calendario.getContentPane());
        calendario.getContentPane().setLayout(calendarioLayout);
        calendarioLayout.setHorizontalGroup(
            calendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calendarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(calendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        calendarioLayout.setVerticalGroup(
            calendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calendarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setMaximumSize(new java.awt.Dimension(370, 306));
        setMinimumSize(new java.awt.Dimension(370, 306));
        setPreferredSize(new java.awt.Dimension(370, 306));

        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Selecione una opción para desplegar el informe:");

        op1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op1.setText("Todos");
        op1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op1ActionPerformed(evt);
            }
        });
        op1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op1KeyPressed(evt);
            }
        });

        op3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op3.setText("Fecha");
        op3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op3ActionPerformed(evt);
            }
        });
        op3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op3KeyPressed(evt);
            }
        });

        op2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op2.setText("Uno");
        op2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op2ActionPerformed(evt);
            }
        });
        op2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op2KeyPressed(evt);
            }
        });

        op4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op4.setText("Proveedor");
        op4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op4ActionPerformed(evt);
            }
        });
        op4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op4KeyPressed(evt);
            }
        });

        op8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op8.setText("Anulados");
        op8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op8ActionPerformed(evt);
            }
        });
        op8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op8KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(op1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(op2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(op3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(op4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(op8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(op1)
                    .addComponent(op4)
                    .addComponent(op3)
                    .addComponent(op2)
                    .addComponent(op8))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informes Genéricos", jPanel1);

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

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Código:");

        cbo_cliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_cliente.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Proveedor:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Fecha Desde:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Fecha Hasta:");

        btnnuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnnuevo.setText("Nuevo");
        btnnuevo.setToolTipText("Nuevo Reporte");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        btnnuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnnuevoKeyTyped(evt);
            }
        });

        btngenerar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btngenerar.setText("Generar");
        btngenerar.setToolTipText("Generar Reporte");
        btngenerar.setMaximumSize(new java.awt.Dimension(63, 23));
        btngenerar.setMinimumSize(new java.awt.Dimension(63, 23));
        btngenerar.setPreferredSize(new java.awt.Dimension(63, 23));
        btngenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarActionPerformed(evt);
            }
        });
        btngenerar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btngenerarKeyTyped(evt);
            }
        });

        btncancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btncancelar.setText("Cancelar");
        btncancelar.setToolTipText("Nueva Venta");
        btncancelar.setMaximumSize(new java.awt.Dimension(63, 23));
        btncancelar.setMinimumSize(new java.awt.Dimension(63, 23));
        btncancelar.setPreferredSize(new java.awt.Dimension(63, 23));
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });
        btncancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btncancelarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btngenerar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtfhasta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfhasta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfhasta.setEnabled(false);
        txtfhasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfhastaActionPerformed(evt);
            }
        });
        txtfhasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfhastaKeyTyped(evt);
            }
        });

        txtfdesde.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfdesde.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfdesde.setEnabled(false);
        txtfdesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfdesdeActionPerformed(evt);
            }
        });
        txtfdesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfdesdeKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfdesde, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfhasta, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtfdesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtfhasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void op1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op1ActionPerformed
op2.setEnabled(false);
op3.setEnabled(false);
op4.setEnabled(false);
op8.setEnabled(false);
op2.setSelected(false);
op3.setSelected(false);
op4.setSelected(false);
op8.setSelected(false);
btngenerar.setEnabled(true);
btngenerar.requestFocus();
reporte = "rretiros";
    }//GEN-LAST:event_op1ActionPerformed

    private void op2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op2ActionPerformed
op1.setEnabled(false);
op3.setEnabled(false);
op4.setEnabled(false);
op8.setEnabled(false);
op1.setSelected(false);
op3.setSelected(false);
op4.setSelected(false);
op8.setSelected(false);
txtcodigo.setEnabled(true);
txtcodigo.requestFocus();
reporte = "entregademateriales1";
    }//GEN-LAST:event_op2ActionPerformed

    private void op3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op3ActionPerformed

            op1.setEnabled(false);
            op2.setEnabled(false);
            op4.setEnabled(false);
            op8.setEnabled(false);
            op1.setSelected(false);
            op2.setSelected(false);
            op4.setSelected(false);
            op8.setSelected(false);
            reporte = "rretiros";
            operacion = 1;
            calendario.pack();
            calendario.setTitle("SELECCIONAR FECHA DESDE");
            calendario.setLocationRelativeTo(null);
            calendario.setModal(true);
            calendario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            calendario.setResizable(false);
            calendario.setVisible(true);
    }//GEN-LAST:event_op3ActionPerformed

    private void op4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op4ActionPerformed
op1.setEnabled(false);
op2.setEnabled(false);
op3.setEnabled(false);
op8.setEnabled(false);
op1.setSelected(false);
op2.setSelected(false);
op3.setSelected(false);
op8.setSelected(false);
reporte = "rretiros";
cbo_cliente.setEnabled(true);
cbo_cliente.requestFocus();
btngenerar.setEnabled(true);
    }//GEN-LAST:event_op4ActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
hab_op();
hab_btn();
op1.requestFocus();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btngenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarActionPerformed
        try {
            cliente = buscacliente((String) cbo_cliente.getSelectedItem());
            gen_reporte();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(inforetiros1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(inforetiros1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(inforetiros1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(inforetiros1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(inforetiros1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngenerarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
des_op();
des_btn();
lim_text();
    }//GEN-LAST:event_btncancelarActionPerformed

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

String asas=txtcodigo.getText();
      int conta1=asas.length();
        if(evt.getKeyChar()=='0'&&conta1==0){
             evt.consume();
        }else{
         if(Character.isDigit(k)==false) {
            evt.consume();
        }
        }
    }//GEN-LAST:event_txtcodigoKeyTyped

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
 String campo = txtcodigo.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            btngenerar.setEnabled(true);
            btngenerar.requestFocus();
            txtcodigo.setEnabled(false);
        }
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void op8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op8ActionPerformed
op2.setEnabled(false);
op3.setEnabled(false);
op4.setEnabled(false);
op1.setEnabled(false);
op2.setSelected(false);
op3.setSelected(false);
op4.setSelected(false);
op1.setSelected(false);
btngenerar.setEnabled(true);
btngenerar.requestFocus();
reporte = "rretiros";
    }//GEN-LAST:event_op8ActionPerformed

    private void btnnuevoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyTyped
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnnuevo.doClick();
        }
    }//GEN-LAST:event_btnnuevoKeyTyped

    private void btngenerarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngenerarKeyTyped
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btngenerar.doClick();
        }
    }//GEN-LAST:event_btngenerarKeyTyped

    private void btncancelarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyTyped
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (operacion == 1){
            txtfdesde.setText("");
            String formato = "yyyy-MM-dd";
            //Formato
            java.util.Date date = jCalendar1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            txtfdesde.setText((sdf.format(date)));
            calendario.dispose();
            operacion = 2;
            calendario.pack();
            calendario.setTitle("SELECCIONAR FECHA HASTA");
            calendario.setLocationRelativeTo(null);
            calendario.setModal(true);
            calendario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            calendario.setResizable(false);
            calendario.setVisible(true);
        } else {
            txtfhasta.setText("");
            String formato = "yyyy-MM-dd";
            //Formato
            java.util.Date date = jCalendar1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            txtfhasta.setText((sdf.format(date)));
            calendario.dispose();
            btngenerar.setEnabled(true);
            btngenerar.requestFocus();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtfhastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfhastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfhastaActionPerformed

    private void txtfhastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfhastaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfhastaKeyTyped

    private void txtfdesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfdesdeActionPerformed

    }//GEN-LAST:event_txtfdesdeActionPerformed

    private void txtfdesdeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfdesdeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfdesdeKeyTyped

    private void op1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op1KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
op1.doClick();
}
    }//GEN-LAST:event_op1KeyPressed

    private void op2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op2KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
op2.doClick();
}        // TODO add your handling code here:
    }//GEN-LAST:event_op2KeyPressed

    private void op3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op3KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
op3.doClick();
}        // TODO add your handling code here:
    }//GEN-LAST:event_op3KeyPressed

    private void op4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op4KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
op4.doClick();
}        // TODO add your handling code here:
    }//GEN-LAST:event_op4KeyPressed

    private void op8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op8KeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
op8.doClick();
}        // TODO add your handling code here:
    }//GEN-LAST:event_op8KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngenerar;
    public static javax.swing.JButton btnnuevo;
    private javax.swing.JDialog calendario;
    private javax.swing.JComboBox cbo_cliente;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton op1;
    private javax.swing.JRadioButton op2;
    private javax.swing.JRadioButton op3;
    private javax.swing.JRadioButton op4;
    private javax.swing.JRadioButton op8;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtfdesde;
    private javax.swing.JTextField txtfhasta;
    // End of variables declaration//GEN-END:variables
}
