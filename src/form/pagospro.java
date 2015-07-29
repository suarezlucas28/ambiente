/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import prgs.NumberToLetterConverter;
import prgs.data;
import prgs.ver_conex;

/**
 *
 * @author Luke
 */
public class pagospro extends javax.swing.JPanel {

    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel modelo3 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    data mostra_data;
    String Fecha, fechahora, numletra;
    String wqqqq;
    private int ab;
    int proveedor;
    public com.mysql.jdbc.ResultSet resu;
    java.sql.Statement snt;
    ResultSet recur1;

    public pagospro() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initComponents();
        mostra_data = new data();
        mostra_data.le_data();
        timer2.start();
        CabDetalle();
        hab_btn();
        cargagrilla();
        cargaproveedor();
        cabecerabuscador();
    }

    private void CabDetalle() {
        modelo.addColumn("OBRA");
        modelo.addColumn("DETALLE");
        grilla.getColumnModel().getColumn(0).setPreferredWidth(21);
        grilla.getColumnModel().getColumn(1).setPreferredWidth(191);
    }

    private void gencod() {
        try {
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(ppr_codigo),0)+1 AS xxx FROM paprove");
            conn.resultado.next();
            txtcodigo.setText(conn.resultado.getString("xxx"));

        } catch (SQLException exceptionSql) {
            JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void cargaproveedor() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String SQL = "SELECT pro_codigo,pro_nombre FROM proveedor ORDER BY pro_nombre";
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);
        txtdestinatario.removeAllItems();
        while (conn.resultado.next()) {
            txtdestinatario.addItem(conn.resultado.getString("pro_nombre"));
        }
    }

    public double getDecimal(int numeroDecimales, double decimal) {
        decimal = decimal * (java.lang.Math.pow(10, numeroDecimales));
        decimal = java.lang.Math.round(decimal);
        decimal = decimal / java.lang.Math.pow(10, numeroDecimales);

        return decimal;
    }

    private void cargagrilla() throws SQLException {
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT obras.obr_codigo,CONCAT(cli_nomape,' - ',obr_proyec) AS DETALLE FROM obras,cliente WHERE obras.cli_codigo = cliente.cli_codigo AND obr_estado LIKE 'ACTIVO' ORDER BY obras.obr_codigo DESC");
        modelo.setRowCount(0);
        Object[] fila = new Object[2];
        while (conn.resultado.next()) {
            fila[0] = conn.resultado.getObject(1);
            fila[1] = conn.resultado.getObject(2);
            modelo.addRow(fila);
        }
        conn.resultado.first();
    }

    private void hab_btn() {
        btnnuevo.setEnabled(true);
        btnnuevo.requestFocus();
        btnregistrar.setEnabled(false);
        btncancelar.setEnabled(false);
        btnanular.setEnabled(true);
    }

    private void des_btn() {
        btnnuevo.setEnabled(false);
        btnregistrar.setEnabled(false);
        btncancelar.setEnabled(true);
        btnanular.setEnabled(false);
    }

    public void Fecha(String fecha) {
        Calendar c = new GregorianCalendar();
        String dia, mes, annio, hora, minuto, segundo;
        dia = Integer.toString(c.get(Calendar.DATE));
        int a = Integer.parseInt(dia);
        if (a <= 9) {
            dia = "0" + dia;
        }
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        int b = Integer.parseInt(mes);
        if (b <= 9) {
            mes = "0" + mes;
        }
        annio = Integer.toString(c.get(Calendar.YEAR));
        hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        minuto = Integer.toString(c.get(Calendar.MINUTE));
        segundo = Integer.toString(c.get(Calendar.SECOND));
        fecha = (annio + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo);
        fechahora = fecha;
        Fecha = (annio + "-" + mes + "-" + dia);
    }

    private void numeroaletra() throws SQLException {
        float tarifa = Float.parseFloat(txtmontoacobrar.getText());
        numletra = NumberToLetterConverter.convertNumberToLetter(tarifa);
    }
    
        public int buscaproveedor(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String SQL = "SELECT * from proveedor where pro_nombre = '" + txtdestinatario.getSelectedItem().toString() + "'";
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);
        conn.resultado.next();
        return (conn.resultado.getInt("pro_codigo"));
    }


    private void grabar() throws SQLException {
        Fecha("Fecha");
        ver_conex conn = new ver_conex();
        String SQL = "INSERT INTO paprove (ppr_codigo,usu_codigo,obr_codigo,ppr_horfec,ppr_monto,pro_codigo,ppr_numletra) VALUES ("+txtcodigo.getText()+","+acceso.usuario+","+txt_item_cod.getText()+",'"+fechahora+"',"+txtmontoacobrar.getText()+",'"+proveedor+"','"+numletra+"')";
        System.out.println(SQL);
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(cue_codigo),0)+1 AS xxx FROM movimientos");
        conn.resultado.next();
        String a = (conn.resultado.getString("xxx"));
        
        String SQL2 = "INSERT INTO movimientos (cue_codigo,usu_codigo,obr_codigo,cue_monto,cue_segun,cue_fechor,cue_opera)"
                + " VALUES (" + a + "," + acceso.usuario + "," + txt_item_cod.getText() + "," + txtmontoacobrar.getText() + ",'PAGO PRO " + txtcodigo.getText() + "','" + fechahora + "','RETIRO')";
        System.out.println(SQL2);
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL2);//OJO LE PASO LA SENTENCIA
    }

    private void imprimir() throws SQLException, JRException {
        try {

            String sentencia = "SELECT pag_codigo,pag_monto AS monto,pag_destin AS cliente,pag_numletra,CONCAT(cli_nomape,' - ',obr_proyec) AS obr_proyec,DAY(`pag_horfec`) AS dia, MONTH(`pag_horfec`) AS mes, YEAR(`pag_horfec`) AS anno FROM pagos,obras,cliente WHERE cliente.cli_codigo = obras.cli_codigo AND obras.obr_codigo = pagos.obr_codigo AND pag_codigo = " + txtcodigo.getText();
            ////////////////////////////////instanciamos
            System.out.println(sentencia);
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            resu = (com.mysql.jdbc.ResultSet) conn.sentencia.executeQuery(sentencia);//OJO LE PASO LA SENTENCIA
        } catch (Exception ex) {
        }
        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        HashMap parameters = new HashMap();

        ////////////////////////////// preapra el patch
        URL urlMaestro = getClass().getClassLoader().getResource("repo/recibo1.jasper");
        ////////////////////////////// Cargamos el reporte
        JasperReport masterReport = null;
        masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
        JasperPrint masterPrint = null;
        ////////////////////////////// pasa el patch y paametros
        masterPrint = JasperFillManager.fillReport(masterReport, parameters, jrRS);
        JasperViewer ventana = new JasperViewer(masterPrint, false);
        ventana.setTitle("Vista Previa");
        ventana.setVisible(false);
        JasperPrintManager.printReport(masterPrint, false);
        resu = null;
    }

    public void grilla3() {
        String ac, ad, ae, af;
        modelo3.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT ppr_codigo,CONCAT(cli_nomape,' - ',obr_proyec) AS obra,ppr_monto,pro_nombre,ppr_horfec FROM paprove,cliente,obras,proveedor WHERE obras.cli_codigo = cliente.cli_codigo AND paprove.obr_codigo = obras.obr_codigo AND proveedor.pro_codigo = paprove.pro_codigo AND CONCAT(cli_nomape,' - ',obr_proyec) LIKE '%" + wqqqq + "%' AND ppr_estado LIKE 'ACTIVO' ORDER BY ppr_codigo DESC");
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("ppr_codigo");
                    ac = recur1.getString("obra");
                    ad = recur1.getString("ppr_monto");
                    ae = recur1.getString("pro_nombre");
                    af = recur1.getString("ppr_horfec");
                    modelo3.addRow(new Object[]{ab, ac, ad, ae, af});
                } while (recur1.next());
            } else {
                ab = 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis", "Verifique",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cabecerabuscador() {
        modelo3.addColumn("CODIGO");
        modelo3.addColumn("OBRA");
        modelo3.addColumn("M.PAGO");
        modelo3.addColumn("PROVEEDOR");
        modelo3.addColumn("FECHA Y HORA");
        grilla4.getColumnModel().getColumn(0).setPreferredWidth(31);
        grilla4.getColumnModel().getColumn(1).setPreferredWidth(191);
        grilla4.getColumnModel().getColumn(2).setPreferredWidth(31);
        grilla4.getColumnModel().getColumn(3).setPreferredWidth(61);
        grilla4.getColumnModel().getColumn(4).setPreferredWidth(51);
    }

    private void cargadatos() throws SQLException {
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT ppr_codigo,obr_codigo,ppr_monto,pro_nombre FROM paprove,proveedor WHERE proveedor.pro_codigo = paprove.pro_codigo AND ppr_codigo = " + txtcodigo.getText());
        conn.resultado.next();
        txtcodigo.setText(conn.resultado.getString("ppr_codigo"));
        txt_item_cod.setText(conn.resultado.getString("obr_codigo"));
        txtmontoacobrar.setText(conn.resultado.getString("ppr_monto"));
        txtdestinatario.setSelectedItem(conn.resultado.getString("pro_nombre"));
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(SUM(ppr_monto),0) AS PAGADO FROM paprove WHERE obr_codigo = " + txt_item_cod.getText() + " AND ppr_estado LIKE 'ACTIVO'");
        conn.resultado.next();
        txtmontoacobrar1.setText(conn.resultado.getString("PAGADO"));
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT CONCAT(cli_nomape,' - ',obr_proyec) AS DETALLE FROM obras,cliente WHERE obras.cli_codigo = cliente.cli_codigo AND obr_codigo = " + txt_item_cod.getText() + " AND obr_estado LIKE 'ACTIVO'");
        conn.resultado.next();
        lbldescri.setText(conn.resultado.getString("DETALLE"));
    }

    private void borrar() throws SQLException {
        String SQL;
        SQL = "UPDATE paprove SET ppr_estado = 'ANULADO' WHERE ppr_codigo = " + txtcodigo.getText();
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        String SQL2 = "UPDATE movimientos SET cue_estado = 'ANULADO' WHERE cue_segun = 'PAGO PRO " + txtcodigo.getText() + "'";
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL2);
        btncancelar.doClick();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timer2 = new org.netbeans.examples.lib.timerbean.Timer();
        buscadorentregas = new javax.swing.JDialog();
        jLabel19 = new javax.swing.JLabel();
        txtbuscador2 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        grilla4 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnregistrar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        txtcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_item_cod = new javax.swing.JTextField();
        lbldescri = new javax.swing.JLabel();
        txtfechahora = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtmontoacobrar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtmontoacobrar1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtdestinatario = new javax.swing.JComboBox();

        timer2.addTimerListener(new org.netbeans.examples.lib.timerbean.TimerListener() {
            public void onTime(java.awt.event.ActionEvent evt) {
                timer2OnTime(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Buscador:");

        txtbuscador2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador2KeyReleased(evt);
            }
        });

        grilla4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        grilla4.setModel(modelo3);
        grilla4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grilla4MouseClicked(evt);
            }
        });
        grilla4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grilla4KeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(grilla4);

        javax.swing.GroupLayout buscadorentregasLayout = new javax.swing.GroupLayout(buscadorentregas.getContentPane());
        buscadorentregas.getContentPane().setLayout(buscadorentregasLayout);
        buscadorentregasLayout.setHorizontalGroup(
            buscadorentregasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorentregasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorentregasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addGroup(buscadorentregasLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador2)))
                .addContainerGap())
        );
        buscadorentregasLayout.setVerticalGroup(
            buscadorentregasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscadorentregasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buscadorentregasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtbuscador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setMaximumSize(new java.awt.Dimension(585, 305));
        setMinimumSize(new java.awt.Dimension(585, 305));
        setPreferredSize(new java.awt.Dimension(585, 305));

        btnnuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnnuevo.setText("Nueva");
        btnnuevo.setToolTipText("");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        btnnuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnnuevoKeyPressed(evt);
            }
        });

        btnregistrar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnregistrar.setText("Grabar");
        btnregistrar.setToolTipText("");
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
        btncancelar.setToolTipText("");
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

        btnanular.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnanular.setText("Anular");
        btnanular.setToolTipText("");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Obra:");

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

        txtfechahora.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfechahora.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtfechahora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfechahora.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Fecha y Hora:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Codigo:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Monto a Pagar:");

        txtmontoacobrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmontoacobrar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtmontoacobrar.setEnabled(false);
        txtmontoacobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmontoacobrarActionPerformed(evt);
            }
        });
        txtmontoacobrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontoacobrarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmontoacobrarKeyTyped(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        grilla.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        grilla.setModel(modelo);
        grilla.setEnabled(false);
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

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Total Pagado:");

        txtmontoacobrar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmontoacobrar1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtmontoacobrar1.setEnabled(false);
        txtmontoacobrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmontoacobrar1ActionPerformed(evt);
            }
        });
        txtmontoacobrar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontoacobrar1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmontoacobrar1KeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Proveedor:");

        txtdestinatario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtdestinatario.setEnabled(false);
        txtdestinatario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdestinatarioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(29, 29, 29)
                                .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtmontoacobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtmontoacobrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtdestinatario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel2))
                            .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel1))
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel3))
                            .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtmontoacobrar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtdestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5))
                            .addComponent(txtmontoacobrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        gencod();
        grilla.setEnabled(true);
        grilla.requestFocus();
        des_btn();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnnuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnnuevo.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnnuevoKeyPressed

    private void btnregistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarActionPerformed
        try {
            proveedor = buscaproveedor((String) txtdestinatario.getSelectedItem());
            numeroaletra();
            grabar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(pagospro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(pagospro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(pagospro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(pagosmo.class.getName()).log(Level.SEVERE, null, ex);
        }
        btncancelar.doClick();
    }//GEN-LAST:event_btnregistrarActionPerformed

    private void btnregistrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnregistrarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnregistrar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnregistrarKeyPressed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        try {
            grilla.setEnabled(false);
            txtmontoacobrar.setEnabled(false);
            txtcodigo.setEnabled(false);
            txtfechahora.setEnabled(false);
            txt_item_cod.setEnabled(false);
            txtdestinatario.setEnabled(false);
            txtcodigo.setText("");
            txt_item_cod.setText("");
            lbldescri.setText("");
            txtmontoacobrar1.setText("");
            lbldescri.setEnabled(false);
            txtmontoacobrar.setText("");
            grilla.setEnabled(false);
            cargagrilla();
            hab_btn();
        } catch (SQLException ex) {
            Logger.getLogger(pagosmo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btncancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btncancelar.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btncancelarKeyPressed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        des_btn();
        txtcodigo.setEnabled(true);
        txtcodigo.requestFocus();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnanularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnanularKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnanular.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnanularKeyPressed

    private void txtcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ppr_codigo,CONCAT(cli_nomape,' - ',obr_proyec) AS obra,ppr_monto,pro_nombre,ppr_horfec FROM paprove,cliente,obras,proveedor WHERE obras.cli_codigo = cliente.cli_codigo AND paprove.obr_codigo = obras.obr_codigo AND proveedor.pro_codigo = paprove.pro_codigo AND ppr_estado LIKE 'ACTIVO' ORDER BY ppr_codigo DESC");
                modelo3.setRowCount(0);
                Object[] fila = new Object[5];
                while (conn.resultado.next()) {
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    fila[2] = conn.resultado.getObject(3);
                    fila[3] = conn.resultado.getObject(4);
                    fila[4] = conn.resultado.getObject(5);
                    modelo3.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscadorentregas.setIconImage(icono.getImage());
                buscadorentregas.setTitle("PAGOS PROVEEDOR");
                buscadorentregas.pack();
                buscadorentregas.setLocationRelativeTo(null);
                buscadorentregas.setModal(true);
                buscadorentregas.requestFocus();
                txtbuscador2.requestFocus();
                buscadorentregas.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtcodigoKeyPressed

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

        char c = evt.getKeyChar();
        if (Character.isDigit(c) == false) {
        } else {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtcodigoKeyTyped

    private void txtmontoacobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmontoacobrarActionPerformed
        String campo = txtmontoacobrar.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtmontoacobrar.setText(String.valueOf(getDecimal(2, Double.parseDouble(txtmontoacobrar.getText()))));
            btnregistrar.setEnabled(true);
            btnregistrar.requestFocus();
        }
    }//GEN-LAST:event_txtmontoacobrarActionPerformed

    private void txtmontoacobrarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoacobrarKeyReleased
    }//GEN-LAST:event_txtmontoacobrarKeyReleased

    private void txtmontoacobrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoacobrarKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtmontoacobrar.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtmontoacobrarKeyTyped

    private void txt_item_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyTyped
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

        String asas = txt_item_cod.getText();
        int conta1 = asas.length();
        if (evt.getKeyChar() == '0' && conta1 == 0) {
            evt.consume();
        } else {
            if (Character.isDigit(k) == false) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_item_codKeyTyped

    private void txt_item_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyPressed
    }//GEN-LAST:event_txt_item_codKeyPressed

    private void txt_item_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_codActionPerformed
    }//GEN-LAST:event_txt_item_codActionPerformed

    private void txt_item_codMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_codMouseClicked
    }//GEN-LAST:event_txt_item_codMouseClicked

    private void grillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grillaMouseClicked
        try {
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String monto = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            txt_item_cod.setText(codigo);
            lbldescri.setText(monto);
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(SUM(ppr_monto),0) AS PAGADO FROM paprove WHERE obr_codigo = " + codigo + " AND ppr_estado LIKE 'ACTIVO'");
            conn.resultado.next();
            txtmontoacobrar1.setText(conn.resultado.getString("PAGADO"));
            txtdestinatario.setEnabled(true);
            txtdestinatario.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(pagosmo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grillaMouseClicked

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
                String monto = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                lbldescri.setText(monto);
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(SUM(ppr_monto),0) AS PAGADO FROM paprove WHERE obr_codigo = " + codigo + " AND ppr_estado LIKE 'ACTIVO'");
                conn.resultado.next();
                txtmontoacobrar1.setText(conn.resultado.getString("PAGADO"));
                txtdestinatario.setEnabled(true);
                txtdestinatario.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(pagosmo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_grillaKeyPressed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void timer2OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer2OnTime
        mostra_data.le_hora();
        txtfechahora.setText(mostra_data.ano + "/" + mostra_data.mes + "/" + mostra_data.dia + " " + mostra_data.hora);
    }//GEN-LAST:event_timer2OnTime

    private void txtmontoacobrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmontoacobrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoacobrar1ActionPerformed

    private void txtmontoacobrar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoacobrar1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoacobrar1KeyReleased

    private void txtmontoacobrar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoacobrar1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoacobrar1KeyTyped

    private void txtbuscador2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            grilla4.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscador2KeyPressed

    private void txtbuscador2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador2KeyReleased
        wqqqq = txtbuscador2.getText();
        grilla3();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador2KeyReleased

    private void grilla4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla4MouseClicked
        try {
            String Dato = (String) modelo3.getValueAt(grilla4.getSelectedRow(), 0).toString();
            txtcodigo.setText(Dato);
            txtcodigo.setEnabled(false);
            buscadorentregas.setVisible(false);
            buscadorentregas.setModal(false);
            cargadatos();

            int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Pago N° --> " + txtcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
            if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
            {
                borrar();
            } else // no quiere borrar
            {
                JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
            }
            btncancelar.doClick();
        } catch (SQLException ex) {
            Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grilla4MouseClicked

    private void grilla4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla4KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                String Dato = (String) modelo3.getValueAt(grilla4.getSelectedRow(), 0).toString();
                txtcodigo.setText(Dato);
                txtcodigo.setEnabled(false);
                buscadorentregas.setVisible(false);
                buscadorentregas.setModal(false);
                cargadatos();
                int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Pago N° --> " + txtcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
                if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                {
                    borrar();
                } else // no quiere borrar
                {
                    JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                }
                btncancelar.doClick();
            } catch (SQLException ex) {
                Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grilla4KeyPressed

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void txtdestinatarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdestinatarioKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            txtmontoacobrar.setEnabled(true);
            txtdestinatario.setEnabled(false);
            txtmontoacobrar.requestFocus();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtdestinatarioKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btncancelar;
    public static javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnregistrar;
    private javax.swing.JDialog buscadorentregas;
    private javax.swing.JTable grilla;
    private javax.swing.JTable grilla4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbldescri;
    private org.netbeans.examples.lib.timerbean.Timer timer2;
    private javax.swing.JTextField txt_item_cod;
    private javax.swing.JTextField txtbuscador2;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JComboBox txtdestinatario;
    private javax.swing.JTextField txtfechahora;
    private javax.swing.JTextField txtmontoacobrar;
    private javax.swing.JTextField txtmontoacobrar1;
    // End of variables declaration//GEN-END:variables
}
