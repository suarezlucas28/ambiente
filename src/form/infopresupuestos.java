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

public class infopresupuestos extends javax.swing.JPanel {

    public ResultSet resu;
    public String sentencia;
    public String reporte, mask;
    public int cliente, operacion;
    String propietario;

    public infopresupuestos() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        des_op();
        des_btn();
    }

    private void hab_btn() {
        btnnuevo.setEnabled(false);
        btngenerar.setEnabled(false);
        btncancelar.setEnabled(true);
    }

    private void des_btn() {
        btnnuevo.setEnabled(true);
        btnnuevo.requestFocus();
        btngenerar.setEnabled(false);
        btncancelar.setEnabled(false);
    }

    private void lim_text() {
        txtcodigo.setText("");
        txtfdesde.setText("");
        txtfhasta.setText("");
    }

    private void hab_op() {
        op1.setEnabled(true);
        op2.setEnabled(true);
        op3.setEnabled(true);
        op8.setEnabled(true);
        op1.setSelected(false);
        op2.setSelected(false);
        op3.setSelected(false);
        op8.setSelected(false);
        op5.setEnabled(true);
        op5.setSelected(false);
//    op9.setEnabled(true);
//    op9.setSelected(false);
    }

    private void des_op() {
        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op8.setEnabled(false);
        op1.setSelected(false);
        op2.setSelected(false);
        op3.setSelected(false);
        op8.setSelected(false);
        op5.setEnabled(false);
        op5.setSelected(false);
//    op9.setEnabled(false);
//    op9.setSelected(false);
    }

    private void imprimir2() throws SQLException, JRException {
        try {

            String sentencia1 = "SELECT CONCAT(pre_proyec,' - ',pre_destin),pre_fechor,pre_manobr,pre_materi,pre_total,pre_gasvar,pre_honora,mdo_descri,uni_descri,pdm_cantid,pdm_subtot FROM presupuesto_detalle_mo,mano_de_obra,unidades,presupuesto WHERE presupuesto.pre_codigo = presupuesto_detalle_mo.pre_codigo AND presupuesto_detalle_mo.mdo_codigo = mano_de_obra.mdo_codigo AND mano_de_obra.uni_codigo = unidades.uni_codigo AND presupuesto.pre_codigo = " + txtcodigo.getText();
            System.out.println(sentencia1);
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            resu = (com.mysql.jdbc.ResultSet) conn.sentencia.executeQuery(sentencia1);//OJO LE PASO LA SENTENCIA
        } catch (Exception ex) {
        }
        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        des_op();
        des_btn();
        lim_text();
        jLabel4.setText("Fecha Desde:");
        jLabel5.setText("Fecha Hasta:");
        HashMap parameters = new HashMap();
        try {
            ////////////////////////////// preapra el patch
            URL urlMaestro = getClass().getClassLoader().getResource("repo/presu2.jasper");
            ////////////////////////////// Cargamos el reporte
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
            JasperPrint masterPrint = null;
            ////////////////////////////// pasa el patch y paametros
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, jrRS);
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
            ventana.setExtendedState(ventana.MAXIMIZED_BOTH);
            ventana.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    menu.jgenerador.setModal(true);
                    menu.jgenerador.setVisible(true);
                }
            });
        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error " + e.toString(), "Atención ", JOptionPane.INFORMATION_MESSAGE);
        }
        resu = null;
    }

    private void imprimir1() throws SQLException, JRException {
        int presupuesto = Integer.parseInt(txtcodigo.getText());
        /////////////////////////////////instanciamos
        ver_conex conn = new ver_conex();
        HashMap parameters = new HashMap();
        ///////////////////////////////// preparamos parametros del reporte
        parameters.put("presupuesto", presupuesto);
        parameters.put("SUBREPORT_DIR", "\\ambiente\\src\\repo\\");
        ///////////////////////////////// patch de los reporetes
        URL urlMaestro = getClass().getClassLoader().getResource("repo/presu1.jasper");
        ///////////////////////////////// Cargamos el reporte
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
                try {
                    imprimir2();
                } catch (SQLException ex) {
                    Logger.getLogger(infopresupuestos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(infopresupuestos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void gen_reporte() throws JRException {
        try {
            if (op1.isSelected()) {
                sentencia = "SELECT presupuesto.`pre_codigo` AS presupuesto_pre_codigo,presupuesto.`pre_destin` AS presupuesto_pre_destin,presupuesto.`pre_proyec` AS presupuesto_pre_proyec,presupuesto.`pre_fechor` AS presupuesto_pre_fechor,presupuesto.`pre_manobr` AS presupuesto_pre_manobr,presupuesto.`pre_materi` AS presupuesto_pre_materi,presupuesto.`pre_total` AS presupuesto_pre_total,presupuesto.`pre_honora` AS presupuesto_pre_honora,presupuesto.`pre_gasvar` AS presupuesto_pre_gasvar,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `presupuesto` presupuesto ON usuarios.`usu_codigo` = presupuesto.`usu_codigo` WHERE pre_estado LIKE 'ACTIVO' ORDER BY pre_codigo DESC";
            } else if (op3.isSelected()) {
                sentencia = "SELECT presupuesto.`pre_codigo` AS presupuesto_pre_codigo,presupuesto.`pre_destin` AS presupuesto_pre_destin,presupuesto.`pre_proyec` AS presupuesto_pre_proyec,presupuesto.`pre_fechor` AS presupuesto_pre_fechor,presupuesto.`pre_manobr` AS presupuesto_pre_manobr,presupuesto.`pre_materi` AS presupuesto_pre_materi,presupuesto.`pre_total` AS presupuesto_pre_total,presupuesto.`pre_honora` AS presupuesto_pre_honora,presupuesto.`pre_gasvar` AS presupuesto_pre_gasvar,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `presupuesto` presupuesto ON usuarios.`usu_codigo` = presupuesto.`usu_codigo` WHERE LEFT(`pre_fechor`,10) BETWEEN  '" + txtfdesde.getText() + "' AND '" + txtfhasta.getText() + "' AND pre_estado LIKE 'ACTIVO' ORDER BY pre_codigo DESC";
            } else if (op5.isSelected()) {
                sentencia = "SELECT ite_descri,uni_descri,SUM(pdi_total) AS cantidadtotal FROM presupuesto_has_items,items,unidades WHERE items.uni_precom = unidades.uni_codigo AND presupuesto_has_items.ite_codigo = items.ite_codigo AND pre_codigo = "+txtcodigo.getText()+" GROUP BY presupuesto_has_items.ite_codigo ORDER BY ite_descri";
            } else if (op8.isSelected()) {
                sentencia = "SELECT presupuesto.`pre_codigo` AS presupuesto_pre_codigo,presupuesto.`pre_destin` AS presupuesto_pre_destin,presupuesto.`pre_proyec` AS presupuesto_pre_proyec,presupuesto.`pre_fechor` AS presupuesto_pre_fechor,presupuesto.`pre_manobr` AS presupuesto_pre_manobr,presupuesto.`pre_materi` AS presupuesto_pre_materi,presupuesto.`pre_total` AS presupuesto_pre_total,presupuesto.`pre_honora` AS presupuesto_pre_honora,presupuesto.`pre_gasvar` AS presupuesto_pre_gasvar,usuarios.`usu_usuari` AS usuarios_usu_usuari FROM `usuarios` usuarios INNER JOIN `presupuesto` presupuesto ON usuarios.`usu_codigo` = presupuesto.`usu_codigo` WHERE pre_estado LIKE 'ANULADO' ORDER BY pre_codigo DESC";
            }  /*else if (op9.isSelected()){
             sentencia = "SELECT articulo.art_codigo,art_descri,SUM(vde_cantid) AS catindadvendida FROM venta,venta_detalle,articulo WHERE venta.ven_codigo = venta_detalle.ven_codigo AND venta_detalle.art_codigo = articulo.art_codigo AND LEFT(`ven_horfec`,10) BETWEEN '"+txtfdesde.getText().substring(0,10)+"' AND '"+txtfhasta.getText().substring(0,10)+"' GROUP BY articulo.art_codigo ORDER BY SUM(vde_cantid) DESC";    
             } */

            menu.jgenerador.setModal(false);
            menu.jgenerador.setVisible(false);
            ////////////////////////////////instanciamos
            System.out.println(sentencia);
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            resu = (com.mysql.jdbc.ResultSet) conn.sentencia.executeQuery(sentencia);//OJO LE PASO LA SENTENCIA
        } catch (Exception ex) {
        }
        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        des_op();
        des_btn();
        lim_text();
        jLabel4.setText("Fecha Desde:");
        jLabel5.setText("Fecha Hasta:");
        HashMap parameters = new HashMap();
        try {
            ////////////////////////////// preapra el patch
            URL urlMaestro = getClass().getClassLoader().getResource("repo/" + reporte + ".jasper");
            ////////////////////////////// Cargamos el reporte
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
            JasperPrint masterPrint = null;
            ////////////////////////////// pasa el patch y paametros
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, jrRS);
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
            ventana.setExtendedState(ventana.MAXIMIZED_BOTH);
            ventana.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    menu.jgenerador.setModal(true);
                    menu.jgenerador.setVisible(true);
                }
            });
        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error " + e.toString(), "Atención ", JOptionPane.INFORMATION_MESSAGE);
        }
        resu = null;
    }

    public int buscacliente(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String SQL = "SELECT * FROM cliente WHERE cli_nomape ='" + a + "'";
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);
        conn.resultado.next();
        return (conn.resultado.getInt("cli_codigo"));
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
        op8 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        op5 = new javax.swing.JRadioButton();
        txtcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
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

        setMaximumSize(new java.awt.Dimension(360, 306));
        setMinimumSize(new java.awt.Dimension(360, 306));
        setPreferredSize(new java.awt.Dimension(360, 306));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(op1)
                        .addGap(27, 27, 27)
                        .addComponent(op2)
                        .addGap(19, 19, 19)
                        .addComponent(op3)
                        .addGap(18, 18, 18)
                        .addComponent(op8))
                    .addComponent(jLabel1))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(op1)
                    .addComponent(op3)
                    .addComponent(op2)
                    .addComponent(op8))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informes Genéricos", jPanel1);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Selecione una opción para desplegar el informe:");

        op5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        op5.setText("Materiales x Cantidad");
        op5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op5ActionPerformed(evt);
            }
        });
        op5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                op5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(op5))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(op5)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Otros Informes", jPanel2);

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

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
        add(txtcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 144, 78, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Código:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 146, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Fecha Desde:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 198, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Fecha Hasta:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 198, -1, -1));

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
                .addContainerGap(15, Short.MAX_VALUE))
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

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 233, 339, -1));

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
        add(txtfhasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 196, 79, -1));

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
        add(txtfdesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 196, 79, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void op1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op1ActionPerformed
        op2.setEnabled(false);
        op3.setEnabled(false);
        op8.setEnabled(false);
        op2.setSelected(false);
        op3.setSelected(false);
        op8.setSelected(false);
        op5.setEnabled(false);
        op5.setSelected(false);
//op9.setEnabled(false);
//op9.setSelected(false);
        btngenerar.setEnabled(true);
        btngenerar.requestFocus();
        reporte = "rpresupuesto";
    }//GEN-LAST:event_op1ActionPerformed

    private void op2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op2ActionPerformed
        op1.setEnabled(false);
        op3.setEnabled(false);
        op8.setEnabled(false);
        op1.setSelected(false);
        op3.setSelected(false);
        op8.setSelected(false);
        op5.setEnabled(false);
        op5.setSelected(false);
//op9.setEnabled(false);
//op9.setSelected(false);
        txtcodigo.setEnabled(true);
        txtcodigo.requestFocus();
        reporte = "rventa";
    }//GEN-LAST:event_op2ActionPerformed

    private void op3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op3ActionPerformed

        op1.setEnabled(false);
        op2.setEnabled(false);
        op8.setEnabled(false);
        op1.setSelected(false);
        op2.setSelected(false);
        op8.setSelected(false);
        op5.setEnabled(false);
        op5.setSelected(false);
//            op9.setEnabled(false);
//            op9.setSelected(false);
        reporte = "rpresupuesto";
        operacion = 1;
        calendario.pack();
        calendario.setTitle("SELECCIONAR FECHA DESDE");
        calendario.setLocationRelativeTo(null);
        calendario.setModal(true);
        calendario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        calendario.setResizable(false);
        calendario.setVisible(true);
    }//GEN-LAST:event_op3ActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        hab_op();
        hab_btn();
        op1.requestFocus();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btngenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarActionPerformed
        try {
            if (op2.isSelected()) {
                imprimir1();
            } else {
                gen_reporte();
            }
        } catch (SQLException ex) {
            Logger.getLogger(infopresupuestos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(infopresupuestos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngenerarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        des_op();
        des_btn();
        lim_text();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void txtcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyTyped

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

        String asas = txtcodigo.getText();
        int conta1 = asas.length();
        if (evt.getKeyChar() == '0' && conta1 == 0) {
            evt.consume();
        } else {
            if (Character.isDigit(k) == false) {
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
        op1.setEnabled(false);
        op2.setSelected(false);
        op3.setSelected(false);
        op1.setSelected(false);
        op5.setEnabled(false);
        op5.setSelected(false);
//op9.setEnabled(false);
//op9.setSelected(false);
        btngenerar.setEnabled(true);
        btngenerar.requestFocus();
        reporte = "rpresupuesto";
    }//GEN-LAST:event_op8ActionPerformed

    private void btnnuevoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnnuevo.doClick();
        }
    }//GEN-LAST:event_btnnuevoKeyTyped

    private void btngenerarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngenerarKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btngenerar.doClick();
        }
    }//GEN-LAST:event_btngenerarKeyTyped

    private void btncancelarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyTyped

    private void op5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op5ActionPerformed
        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op1.setSelected(false);
        op2.setSelected(false);
        op3.setSelected(false);
        op8.setEnabled(false);
        op8.setSelected(false);
        reporte = "rpcanmat";
        txtcodigo.setEnabled(true);
        txtcodigo.requestFocus();
    }//GEN-LAST:event_op5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (operacion == 1) {
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

    private void op8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op8KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            op8.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_op8KeyPressed

    private void op5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_op5KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            op5.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_op5KeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngenerar;
    public static javax.swing.JButton btnnuevo;
    private javax.swing.JDialog calendario;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton op1;
    private javax.swing.JRadioButton op2;
    private javax.swing.JRadioButton op3;
    private javax.swing.JRadioButton op5;
    private javax.swing.JRadioButton op8;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtfdesde;
    private javax.swing.JTextField txtfhasta;
    // End of variables declaration//GEN-END:variables
}
