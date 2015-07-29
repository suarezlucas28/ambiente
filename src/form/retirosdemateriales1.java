/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.Statement;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import prgs.data;
import prgs.ver_conex;

/**
 *
 * @author Luke
 */
public class retirosdemateriales1 extends javax.swing.JPanel {

    DefaultTableModel modelo = new DefaultTableModel() {
@Override
        public boolean isCellEditable(int row, int column) {
        if(column == 6)
         return true;
        return false;
    }
     
     Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class 
            };
         
         public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
    };
    DefaultTableModel modelo1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel modelo2 = new DefaultTableModel() {
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
    int operacion, unidad;
    String wqqqq, medida, fechahora, Fecha;
    private int ab, fila, proveedor;
    javax.swing.table.DefaultTableModel cursor;
    public com.mysql.jdbc.ResultSet resu;
    java.sql.Statement snt;
    ResultSet recur1;
    String citem, item;

    public retirosdemateriales1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        cursor = (javax.swing.table.DefaultTableModel) grilla.getModel();
        mostra_data = new data();
        mostra_data.le_data();
        timer2.start();
        CabDetalle();
        des_btn();
        CabItem();
        cargaproveedor();
        initListeners();
        Cabbusca();
    }

    private void hab_btn() {
        btnagregar.setEnabled(false);
        btnborrar.setEnabled(false);
        btnmodificar.setEnabled(false);
        btngrabar.setEnabled(false);
        btncancelar.setEnabled(true);
    }

    private void des_btn() {
        btnagregar.setEnabled(true);
        btnagregar.requestFocus();
        btnborrar.setEnabled(true);
        btnmodificar.setEnabled(true);
        btngrabar.setEnabled(false);
        btncancelar.setEnabled(false);
    }

    private void gencod() {
        try {
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(ret_codigo),0)+1 AS xxx FROM retiros1");
            conn.resultado.next();
            lblcodigo.setText(conn.resultado.getString("xxx"));

        } catch (SQLException exceptionSql) {
            JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lim_text() {
        lblcodigo.setText("");
        txthora.setText("");
        txt_item_cod1.setText("");
        lbldescri1.setText("");
        txtpreci1.setText("");
        txtcantidad1.setText("");
        txt_item_cod.setText("");
        txttotal.setText("");
    }

    private void des_text() {
        lblcodigo.setEnabled(false);
        txthora.setEnabled(false);
        txt_item_cod1.setEnabled(false);
        lbldescri1.setEnabled(false);
        txtpreci1.setEnabled(false);
        txtcantidad1.setEnabled(false);
        txt_item_cod.setEnabled(false);
        txttotal.setEnabled(false);
    }

    private void CabDetalle() {
        modelo.addColumn("CÓD");
        modelo.addColumn("MATERIAL");
        modelo.addColumn("UNIDAD");
        modelo.addColumn("P/UNITARIO");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("SUBTOTAL");
        modelo.addColumn("PAGADO");
        grilla.getColumnModel().getColumn(0).setPreferredWidth(5);
        grilla.getColumnModel().getColumn(1).setPreferredWidth(151);
        grilla.getColumnModel().getColumn(2).setPreferredWidth(71);
        grilla.getColumnModel().getColumn(3).setPreferredWidth(41);
        grilla.getColumnModel().getColumn(4).setPreferredWidth(5);
        grilla.getColumnModel().getColumn(5).setPreferredWidth(61);
        grilla.getColumnModel().getColumn(6).setPreferredWidth(5);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        grilla.getColumnModel().getColumn(3).setCellRenderer(tcr);
        grilla.getColumnModel().getColumn(4).setCellRenderer(tcr);
        grilla.getColumnModel().getColumn(5).setCellRenderer(tcr);
    }

    private void CabItem() {
        modelo2.addColumn("CÓD");
        modelo2.addColumn("ITEM");
        grilla2.getColumnModel().getColumn(0).setPreferredWidth(5);
        grilla2.getColumnModel().getColumn(1).setPreferredWidth(151);
    }

    private void Cabbusca() {
        modelo3.addColumn("CÓD");
        modelo3.addColumn("PROVEEDOR");
        modelo3.addColumn("CLIENTE Y OBRA");
        modelo3.addColumn("FECHA Y HORA");
        grilla3.getColumnModel().getColumn(0).setPreferredWidth(5);
        grilla3.getColumnModel().getColumn(1).setPreferredWidth(131);
        grilla3.getColumnModel().getColumn(2).setPreferredWidth(171);
        grilla3.getColumnModel().getColumn(3).setPreferredWidth(131);
    }

    public void grilla() {
        String ac;
        modelo1.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT obras.obr_codigo,CONCAT(cli_nomape,' - ',obr_proyec) AS DETALLE FROM obras,cliente WHERE obras.cli_codigo = cliente.cli_codigo AND CONCAT(cli_nomape,' - ',obr_proyec) LIKE '%" + wqqqq + "%' AND obr_estado LIKE 'ACTIVO' ORDER BY obras.obr_codigo DESC");
            System.out.println(recur1);
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("obr_codigo");
                    ac = recur1.getString("DETALLE");
                    modelo1.addRow(new Object[]{ab, ac});
                } while (recur1.next());
            } else {
                ab = 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis", "Verifique",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void grilla1() {
        String ac;
        modelo2.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT ite_codigo,ite_descri FROM items WHERE ite_descri LIKE '%" + wqqqq + "%' ORDER BY ite_descri");
            System.out.println(recur1);
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("ite_codigo");
                    ac = recur1.getString("ite_descri");
                    modelo2.addRow(new Object[]{ab, ac});
                } while (recur1.next());
            } else {
                ab = 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis", "Verifique",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void grilla3() {
        String ac, ad, ae;
        modelo3.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT ret_codigo,pro_nombre,ret_destin,ret_fechor FROM retiros1,proveedor WHERE retiros1.pro_codigo = proveedor.pro_codigo AND ret_destin LIKE '%" + wqqqq + "%' AND ret_estado LIKE 'ACTIVO' ORDER BY ret_codigo DESC");
            System.out.println(recur1);
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("ret_codigo");
                    ac = recur1.getString("pro_nombre");
                    ad = recur1.getString("ret_destin");
                    ae = recur1.getString("ret_fechor");
                    modelo3.addRow(new Object[]{ab, ac, ad, ae});
                } while (recur1.next());
            } else {
                ab = 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis", "Verifique",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cargaproveedor() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String SQL = "SELECT pro_codigo,pro_nombre FROM proveedor ORDER BY pro_nombre";
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);
        jComboBox1.removeAllItems();
        while (conn.resultado.next()) {
            jComboBox1.addItem(conn.resultado.getString("pro_nombre"));
        }
    }

    public void nodupli() {
        String aux;
        int cantfilas = grilla.getRowCount();
        int x;
        citem = lbldescri1.getText();
        for (x = 0; x < cantfilas; x++) {
            item = (String) grilla.getValueAt(x, 1);//codigo
            aux = lbldescri1.getText();
            if (item.equals(citem)) {
                JOptionPane.showMessageDialog(null, "ITEM " + aux + " ESTA REPETIDO", "VERIFIQUE",
                        JOptionPane.INFORMATION_MESSAGE);
                txt_item_cod1.setText("");
                lbldescri1.setText("");
                txtpreci1.setText("");
                txtpreci1.setEnabled(false);
                txt_item_cod1.setEnabled(true);
                txt_item_cod1.requestFocus();
                return;
            }
        }
    }

    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }

    private void tirarGrilla() throws SQLException {
        float cant = Float.parseFloat(txtcantidad1.getText());
        Object[] datos = new Object[7];
        datos[0] = txt_item_cod1.getText();
        datos[1] = lbldescri1.getText();
        datos[2] = medida;
        datos[3] = String.valueOf(Redondear(Float.parseFloat(txtpreci1.getText())));
        datos[4] = txtcantidad1.getText();
        datos[5] = cant * Float.parseFloat(txtpreci1.getText());
        datos[6] = new Boolean(true);
        modelo.addRow(datos);
        recorrergrilla();
        btngrabar.setEnabled(true);
        txt_item_cod1.setText("");
        lbldescri1.setText("");
        txtpreci1.setText("");
        txtcantidad1.setText("");
        txtcantidad1.setEnabled(false);
        txt_item_cod1.setEnabled(true);
        txt_item_cod1.requestFocus();
    }

    private void recorrergrilla() throws SQLException {
        javax.swing.table.TableModel model = grilla.getModel();
        int c = 0;
        float total_1 = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String value = (String) modelo.getValueAt(i, 5).toString();
            Float value1 = Float.parseFloat(value);
            if (value1 != 0) {
                total_1 = total_1 + value1;
                c++;
            }
        }
        String cadena = String.valueOf(Redondear(total_1));
        txttotal.setText(cadena);
    }

    private void initListeners() {
        grilla.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    fila = grilla.getSelectedRow();
                    String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
                    String material = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
                    String unidad = modelo.getValueAt(grilla.getSelectedRow(), 2).toString();
                    String costo = modelo.getValueAt(grilla.getSelectedRow(), 3).toString();
                    String cantidad = modelo.getValueAt(grilla.getSelectedRow(), 4).toString();
                    String costototal = modelo.getValueAt(grilla.getSelectedRow(), 5).toString();
                    jLabel24.setText(codigo);
                    jLabel23.setText(material);
                    jLabel29.setText(unidad);
                    jTextField6.setText(costo);
                    jTextField2.setText(cantidad);
                    jTextField7.setText(costototal);
                    ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                    Editor.setIconImage(icono.getImage());
                    Editor.setTitle("EDITOR DE MATERIALES");
                    Editor.pack();
                    Editor.setLocationRelativeTo(null);
                    Editor.setModal(true);
                    jTextField6.setEnabled(true);
                    jTextField6.selectAll();
                    jTextField6.requestFocus();
                    Editor.setVisible(true);
                }
            }
        });
    }

    public int buscaproveedor(String a) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String SQL = "SELECT * from proveedor where pro_nombre = '" + jComboBox1.getSelectedItem().toString() + "'";
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery(SQL);
        conn.resultado.next();
        return (conn.resultado.getInt("pro_codigo"));
    }

    private void limpiarcampos() {
        jLabel24.setText("");
        jLabel23.setText("");
        jTextField2.setText("");
        jLabel29.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
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

    private void agregar() throws SQLException {
        Fecha("Fecha");
        String SQL = "INSERT INTO retiros1 (ret_codigo,pro_codigo,ret_destin,usu_codigo,ret_fechor,ret_monto) "
                + " VALUES (" + lblcodigo.getText() + "," + proveedor + ",'" + txt_item_cod.getText() + "'," + acceso.usuario + ",'" + fechahora + "'," + txttotal.getText() + ")";
        System.out.println(SQL);
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        
        javax.swing.table.TableModel model = grilla.getModel();

        int c = 0;

        for (int i = 0; i < model.getRowCount(); i++) {

            java.lang.Boolean value = (Boolean)model.getValueAt(i, 6);

            if (value != null && value) {

                System.out.println("El valor en la posición " + String.valueOf(i+1) + " esta encendido.");
                String codigo = modelo.getValueAt(i, 0).toString();
                String medida = modelo.getValueAt(i, 1).toString();
                String costo = modelo.getValueAt(i, 3).toString();
                String cantidad = modelo.getValueAt(i, 4).toString();
                String cantcon = modelo.getValueAt(i, 5).toString();
                String SQL1 = "INSERT INTO retiros_detalle_material1 (ret_codigo,ite_codigo,rdm_cantid,rdm_subtot,rdm_unitari) VALUES (" + lblcodigo.getText() + "," + codigo + "," + cantidad + "," + cantcon + "," + costo + ")";
                conn.sentencia = conn.conexion.createStatement();
                conn.sentencia.executeUpdate(SQL1);
                conn.sentencia.close();
                c++;
                System.out.print(SQL);
              } else {
                String codigo = modelo.getValueAt(i, 0).toString();
                String medida = modelo.getValueAt(i, 1).toString();
                String costo = modelo.getValueAt(i, 3).toString();
                String cantidad = modelo.getValueAt(i, 4).toString();
                String cantcon = modelo.getValueAt(i, 5).toString();
                String SQL1 = "INSERT INTO retiros_detalle_material1 (ret_codigo,ite_codigo,rdm_cantid,rdm_subtot,rdm_unitari,rdm_pagado) VALUES (" + lblcodigo.getText() + "," + codigo + "," + cantidad + "," + cantcon + "," + costo + ",'NO')";
                conn.sentencia = conn.conexion.createStatement();
                conn.sentencia.executeUpdate(SQL1);
                conn.sentencia.close();
                c++;
            }
        }
        System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

//        conn.sentencia = conn.conexion.createStatement();
//        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(cue_codigo),0)+1 AS xxx FROM movimientos");
//        conn.resultado.next();
//        String a = (conn.resultado.getString("xxx"));
//
//        String SQL2 = "INSERT INTO movimientos (cue_codigo,usu_codigo,obr_codigo,cue_monto,cue_segun,cue_fechor,cue_opera)"
//                + " VALUES (" + a + "," + acceso.usuario + "," + txt_item_cod.getText() + "," + txttotal.getText() + ",'ENTREGA " + lblcodigo.getText() + "','" + fechahora + "','RETIRO')";
//        System.out.println(SQL2);
//        conn.sentencia = conn.conexion.createStatement();
//        conn.sentencia.executeUpdate(SQL2);//OJO LE PASO LA SENTENCIA
    }

    private void modificar() throws SQLException {
        Fecha("Fecha");
        String SQL = "UPDATE retiros1 SET pro_codigo = " + proveedor + ",ret_destin = '" + txt_item_cod.getText() + "',usu_codigo = " + acceso.usuario + ",ret_monto = " + txttotal.getText() + " WHERE ret_codigo = " + lblcodigo.getText();
//        String SQL2 = "UPDATE movimientos SET cue_monto = " + txttotal.getText() + ",cue_fechor = '" + fechahora + "' WHERE cue_segun = 'ENTREGA " + lblcodigo.getText() + "'";
        System.out.println(SQL);
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);
        conn.sentencia = conn.conexion.createStatement();
//        conn.sentencia.executeUpdate(SQL2);//OJO LE PASO LA SENTENCIA
        String SQL1 = "DELETE FROM retiros_detalle_material1 WHERE ret_codigo =  " + lblcodigo.getText();
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL1);
        try {
            javax.swing.table.TableModel model = grilla.getModel();

            int c = 0;

            for (int i = 0; i < model.getRowCount(); i++) {
                Statement stmta = null;
                stmta = (Statement) conn.conexion.createStatement();
                String codigo = modelo.getValueAt(i, 0).toString();
                String medida = modelo.getValueAt(i, 1).toString();
                String costo = modelo.getValueAt(i, 3).toString();
                String cantidad = modelo.getValueAt(i, 4).toString();
                String cantcon = modelo.getValueAt(i, 5).toString();
                conn.sentencia = conn.conexion.createStatement();
                String insercionSQL1 = "INSERT INTO retiros_detalle_material1 (ret_codigo,ite_codigo,rdm_cantid,rdm_subtot,rdm_unitari) VALUES (" + lblcodigo.getText() + "," + codigo + "," + cantidad + "," + cantcon + "," + costo + ")";
                stmta.executeUpdate(insercionSQL1);
                c++;
            }


            System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error " + e.toString(), "Atención ", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void borrar() throws SQLException {
        String SQL = "UPDATE retiros1 SET ret_estado = 'ANULADO' WHERE ret_codigo = " + lblcodigo.getText();
//        String SQL2 = "UPDATE movimientos SET cue_estado = 'ANULADO' WHERE cue_segun = 'ENTREGA " + lblcodigo.getText() + "'";
        System.out.println(SQL);
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
//        conn.sentencia.executeUpdate(SQL2);
    }

    private void cargadatos() throws SQLException {
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT * FROM retiros1 WHERE ret_codigo = " + lblcodigo.getText());
        conn.resultado.next();
        txttotal.setText(conn.resultado.getString("ret_monto"));
        txthora.setText(conn.resultado.getString("ret_fechor"));
        proveedor = conn.resultado.getInt("pro_codigo");
        txt_item_cod.setText(conn.resultado.getString("ret_destin"));
        conn.resultado = conn.sentencia.executeQuery("SELECT * FROM proveedor WHERE pro_codigo = " + proveedor);
        conn.resultado.next();
        jComboBox1.setSelectedItem(conn.resultado.getString("pro_nombre"));
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT retiros_detalle_material1.ite_codigo,ite_descri,uni_descri,rdm_unitari,rdm_cantid,rdm_subtot,rdm_pagado FROM unidades,retiros_detalle_material1,items WHERE items.ite_codigo = retiros_detalle_material1.ite_codigo AND items.uni_precom = unidades.uni_codigo AND ret_codigo = " + lblcodigo.getText());
        modelo.setRowCount(0);
        Object[] fila = new Object[7];
        while (conn.resultado.next()) {
            fila[0] = conn.resultado.getObject(1);
            fila[1] = conn.resultado.getObject(2);
            fila[2] = conn.resultado.getObject(3);
            fila[3] = conn.resultado.getObject(4);
            fila[4] = conn.resultado.getObject(5);
            fila[5] = conn.resultado.getObject(6);
            if(conn.resultado.getString("rdm_pagado").equals("SI")){
            fila[6] = new Boolean(true);    
            } else {
            fila[6] = new Boolean(false);        
            }
            modelo.addRow(fila);
        }
        conn.resultado.first();
        btngrabar.setEnabled(true);
    }
    
    private void imprimir() throws SQLException, JRException {
        try {

            String sentencia = "SELECT retiros1.ret_codigo,DAY(`ret_fechor`) AS dia, MONTH(`ret_fechor`) AS mes, YEAR(`ret_fechor`) AS anno,pro_nombre,ret_destin,rdm_cantid,ite_descri,rdm_pagado,rdm_unitari,rdm_subtot FROM items,retiros1,proveedor,retiros_detalle_material1 WHERE retiros1.pro_codigo = proveedor.pro_codigo AND retiros_detalle_material1.ret_codigo = retiros1.ret_codigo AND retiros_detalle_material1.ite_codigo = items.ite_codigo AND retiros1.ret_codigo = " + lblcodigo.getText();
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
        URL urlMaestro = getClass().getClassLoader().getResource("repo/entregademateriales1.jasper");
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timer2 = new org.netbeans.examples.lib.timerbean.Timer();
        buscadormateriales = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        grilla2 = new javax.swing.JTable();
        txtbuscador1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        Editor = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        buscadorentrega = new javax.swing.JDialog();
        jLabel21 = new javax.swing.JLabel();
        txtbuscador2 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        grilla3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btngrabar = new javax.swing.JButton();
        btnborrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txthora = new javax.swing.JTextField();
        txt_item_cod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txt_item_cod1 = new javax.swing.JTextField();
        lbldescri1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtpreci1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtcantidad1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();

        timer2.addTimerListener(new org.netbeans.examples.lib.timerbean.TimerListener() {
            public void onTime(java.awt.event.ActionEvent evt) {
                timer2OnTime(evt);
            }
        });

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

        txtbuscador1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscador1ActionPerformed(evt);
            }
        });
        txtbuscador1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Buscador:");

        javax.swing.GroupLayout buscadormaterialesLayout = new javax.swing.GroupLayout(buscadormateriales.getContentPane());
        buscadormateriales.getContentPane().setLayout(buscadormaterialesLayout);
        buscadormaterialesLayout.setHorizontalGroup(
            buscadormaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadormaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadormaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(buscadormaterialesLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                .addContainerGap())
        );
        buscadormaterialesLayout.setVerticalGroup(
            buscadormaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadormaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buscadormaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Código:");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Material:");

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setText("    ");

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("    ");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel26.setText("Cantidad:");

        jTextField2.setEnabled(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setText("    ");

        jLabel33.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel33.setText("Subtotal:");

        jTextField6.setEnabled(false);
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel34.setText("Costo Unitario:");

        jTextField7.setEnabled(false);
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout EditorLayout = new javax.swing.GroupLayout(Editor.getContentPane());
        Editor.getContentPane().setLayout(EditorLayout);
        EditorLayout.setHorizontalGroup(
            EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(EditorLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6))
                    .addGroup(EditorLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel20)))
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditorLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)))
                .addGap(18, 18, 18)
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField7)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        EditorLayout.setVerticalGroup(
            EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Buscador:");

        txtbuscador2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscador2ActionPerformed(evt);
            }
        });
        txtbuscador2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador2KeyReleased(evt);
            }
        });

        grilla3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        grilla3.setModel(modelo3);
        grilla3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grilla3MouseClicked(evt);
            }
        });
        grilla3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grilla3KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(grilla3);

        javax.swing.GroupLayout buscadorentregaLayout = new javax.swing.GroupLayout(buscadorentrega.getContentPane());
        buscadorentrega.getContentPane().setLayout(buscadorentregaLayout);
        buscadorentregaLayout.setHorizontalGroup(
            buscadorentregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorentregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorentregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(buscadorentregaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador2)))
                .addContainerGap())
        );
        buscadorentregaLayout.setVerticalGroup(
            buscadorentregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscadorentregaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buscadorentregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtbuscador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnagregar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnagregar.setText("Nuevo");
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

        btnborrar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnborrar.setText("Anular");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnagregar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngrabar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        lblcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblcodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lblcodigo.setEnabled(false);
        lblcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblcodigoActionPerformed(evt);
            }
        });
        lblcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblcodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblcodigoKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Hora y Fecha:");

        txthora.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txthora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txthora.setEnabled(false);
        txthora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthoraActionPerformed(evt);
            }
        });

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

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Destinatario:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Proveedor:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setEnabled(false);
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Item:");

        txt_item_cod1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_item_cod1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_item_cod1.setEnabled(false);
        txt_item_cod1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_item_cod1MouseClicked(evt);
            }
        });
        txt_item_cod1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_item_cod1ActionPerformed(evt);
            }
        });
        txt_item_cod1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_item_cod1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_item_cod1KeyTyped(evt);
            }
        });

        lbldescri1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Costo:");

        txtpreci1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtpreci1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtpreci1.setEnabled(false);
        txtpreci1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpreci1MouseClicked(evt);
            }
        });
        txtpreci1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpreci1ActionPerformed(evt);
            }
        });
        txtpreci1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpreci1KeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Cantidad:");

        txtcantidad1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcantidad1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcantidad1.setEnabled(false);
        txtcantidad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidad1ActionPerformed(evt);
            }
        });
        txtcantidad1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad1KeyTyped(evt);
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

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Total:");

        txttotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txttotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txttotal.setEnabled(false);
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });
        txttotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttotalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_item_cod1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txthora, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(lbldescri1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel7)
                                .addGap(26, 26, 26)
                                .addComponent(txtpreci1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(27, 27, 27)
                                .addComponent(txtcantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txthora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txt_item_cod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbldescri1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtpreci1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtcantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        operacion = 1;
        hab_btn();
        gencod();
        txt_item_cod.setEnabled(true);
        txt_item_cod.requestFocus();
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnagregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnagregarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnagregar.doClick();
        }
    }//GEN-LAST:event_btnagregarKeyPressed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        operacion = 3;
        hab_btn();
        lblcodigo.setEnabled(true);
        lblcodigo.requestFocus();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnmodificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnmodificarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnmodificar.doClick();
        }
    }//GEN-LAST:event_btnmodificarKeyPressed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
//        grilla.setEnabled(false);
//        grilla1.setEnabled(false);
//        grilla2.setEnabled(false);
        jComboBox1.setEnabled(false);
        des_btn();
        lim_text();
        des_text();
        modelo.setRowCount(0);
        modelo1.setRowCount(0);
        modelo2.setRowCount(0);
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btncancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyPressed

    private void btngrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngrabarActionPerformed
        try {
            proveedor = buscaproveedor((String) jComboBox1.getSelectedItem());
//            operaciones();

            if (operacion == 1) {
                try {
                    agregar();
                } catch (SQLException ex) {
                    Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (operacion == 3) {
                try {
                    modificar();
                } catch (SQLException ex) {
                    Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int mensaje = JOptionPane.showConfirmDialog(this, "DESEA IMPRIMIR LA ORDEN DE ENTREGA DE MATERIALES?", "CONFIRMA", JOptionPane.YES_NO_OPTION);
            if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
            {
    try {
        imprimir();
        imprimir();
    } catch (SQLException ex) {
        Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JRException ex) {
        Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
    }
            } else // no quiere borrar
            {
                //JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atencion", JOptionPane.ERROR_MESSAGE);
            }
            btncancelar.doClick();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btngrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngrabarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btngrabar.doClick();
        }
    }//GEN-LAST:event_btngrabarKeyPressed

    private void btnborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnborrarActionPerformed
        operacion = 2;
        hab_btn();
        lblcodigo.setEnabled(true);
        lblcodigo.requestFocus();
    }//GEN-LAST:event_btnborrarActionPerformed

    private void btnborrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnborrarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnborrar.doClick();
        }
    }//GEN-LAST:event_btnborrarKeyPressed

    private void lblcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblcodigoKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ret_codigo,pro_nombre,ret_destin,ret_fechor FROM retiros1,proveedor WHERE retiros1.pro_codigo = proveedor.pro_codigo AND ret_estado LIKE 'ACTIVO' ORDER BY ret_codigo DESC");
                modelo3.setRowCount(0);
                Object[] fila = new Object[4];
                while (conn.resultado.next()) {
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    fila[2] = conn.resultado.getObject(3);
                    fila[3] = conn.resultado.getObject(4);
                    modelo3.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscadorentrega.setIconImage(icono.getImage());
                buscadorentrega.setTitle("ENTREGAS DE MATERIALES");
                buscadorentrega.pack();
                buscadorentrega.setLocationRelativeTo(null);
                buscadorentrega.setModal(true);
                txtbuscador2.requestFocus();
                buscadorentrega.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                   // TODO add your handling code here:
    }//GEN-LAST:event_lblcodigoKeyPressed

    private void lblcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblcodigoKeyTyped
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodigoKeyTyped

    private void txthoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthoraActionPerformed

    private void txt_item_codMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_codMouseClicked
    }//GEN-LAST:event_txt_item_codMouseClicked

    private void txt_item_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_codActionPerformed
String campo = txt_item_cod.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txt_item_cod.setText(txt_item_cod.getText().toUpperCase());
            jComboBox1.setEnabled(true);
            txt_item_cod.setEnabled(false);
            jComboBox1.requestFocus();
        }
    }//GEN-LAST:event_txt_item_codActionPerformed

    private void txt_item_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyPressed
    }//GEN-LAST:event_txt_item_codKeyPressed

    private void txt_item_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyTyped
        if (txt_item_cod.getText().length() == 50) {
            txt_item_cod.setText(txt_item_cod.getText().toUpperCase());
            txt_item_cod.setEnabled(false);
            jComboBox1.setEnabled(true);
            jComboBox1.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txt_item_cod.getText().length() == 50) {
                txt_item_cod.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txt_item_codKeyTyped

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            txt_item_cod1.setEnabled(true);
            jComboBox1.setEnabled(false);
            txt_item_cod1.requestFocus();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void txt_item_cod1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_cod1MouseClicked
    }//GEN-LAST:event_txt_item_cod1MouseClicked

    private void txt_item_cod1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_cod1ActionPerformed
        String campo = txt_item_cod1.getText();
        if (campo.isEmpty()) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri FROM items ORDER BY ite_descri");
                modelo2.setRowCount(0);
                Object[] fila = new Object[2];
                while (conn.resultado.next()) {
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    modelo2.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscadormateriales.setIconImage(icono.getImage());
                buscadormateriales.setTitle("ITEMS");
                buscadormateriales.pack();
                buscadormateriales.setLocationRelativeTo(null);
                buscadormateriales.setModal(true);
                txtbuscador1.requestFocus();
                buscadormateriales.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri,ite_costo,uni_descri FROM items,unidades WHERE unidades.uni_codigo = items.uni_precom AND ite_codigo = " + txt_item_cod1.getText());
                boolean encontro = conn.resultado.next();
                if (encontro == true) {
                    lbldescri1.setText(conn.resultado.getString("ite_descri"));
                    medida = conn.resultado.getString("uni_descri");
                    txtpreci1.setText(conn.resultado.getString("ite_costo"));
                    txt_item_cod1.setEnabled(true);
                    buscadormateriales.setVisible(false);
                    buscadormateriales.setModal(false);
                    txtbuscador1.setText("");
                    txtpreci1.setEnabled(true);
                    txtpreci1.selectAll();
                    txtpreci1.requestFocus();
                    nodupli();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encuentra Item con codigo -->" + txt_item_cod.getText(), "Atención", JOptionPane.ERROR_MESSAGE);
                    txt_item_cod1.setText("");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_item_cod1ActionPerformed

    private void txt_item_cod1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_cod1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_item_cod1KeyPressed

    private void txt_item_cod1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_cod1KeyTyped
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

        String asas = txt_item_cod1.getText();
        int conta1 = asas.length();
        if (evt.getKeyChar() == '0' && conta1 == 0) {
            evt.consume();
        } else {
            if (Character.isDigit(k) == false) {
                evt.consume();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_item_cod1KeyTyped

    private void txtpreci1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpreci1MouseClicked
        txtcantidad1.setEnabled(false);
        txtcantidad1.setText("");
        txtpreci1.setEnabled(true);
        txtpreci1.requestFocus();
        txtpreci1.selectAll();
    }//GEN-LAST:event_txtpreci1MouseClicked

    private void txtpreci1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpreci1ActionPerformed
        String campo = txtpreci1.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtpreci1.setText(String.valueOf(Redondear(Float.parseFloat(txtpreci1.getText()))));
            txtcantidad1.setEnabled(true);
            txtcantidad1.requestFocus();
            txtpreci1.setEnabled(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtpreci1ActionPerformed

    private void txtpreci1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreci1KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtpreci1.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtpreci1KeyTyped

    private void txtcantidad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidad1ActionPerformed
        String campo = txtcantidad1.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            try {
                txtcantidad1.setText(String.valueOf(Redondear(Float.parseFloat(txtcantidad1.getText()))));
                tirarGrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtcantidad1ActionPerformed

    private void txtcantidad1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad1KeyPressed

    private void txtcantidad1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad1KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtcantidad1.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtcantidad1KeyTyped

    private void grillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grillaMouseClicked
    }//GEN-LAST:event_grillaMouseClicked

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            try {
                cursor.removeRow(grilla.getSelectedRow());
                recorrergrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            try {
                cursor.removeRow(grilla.getSelectedRow());
                recorrergrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grillaKeyPressed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void timer2OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer2OnTime
        mostra_data.le_hora();
        txthora.setText(mostra_data.ano + "/" + mostra_data.mes + "/" + mostra_data.dia + " " + mostra_data.hora);
    }//GEN-LAST:event_timer2OnTime

    private void grilla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla2MouseClicked
        try {
            String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
            String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
            txt_item_cod1.setText(codigo);
            lbldescri1.setText(descri);
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri,ite_costo,uni_descri FROM items,unidades WHERE unidades.uni_codigo = items.uni_precom AND ite_codigo = " + codigo);
            conn.resultado.next();
            txtpreci1.setText(conn.resultado.getString("ite_costo"));
            medida = conn.resultado.getString("uni_descri");
            txt_item_cod.setEnabled(true);
            buscadormateriales.setVisible(false);
            buscadormateriales.setModal(false);
            txtbuscador1.setText("");
            txtpreci1.setEnabled(true);
            txtpreci1.selectAll();
            txtpreci1.requestFocus();
            nodupli();
        } // TODO add your handling code here:
        catch (SQLException ex) {
            Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grilla2MouseClicked

    private void grilla2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod1.setText(codigo);
                lbldescri1.setText(descri);
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri,ite_costo,uni_descri FROM items,unidades WHERE unidades.uni_codigo = items.uni_precom AND ite_codigo = " + codigo);
                conn.resultado.next();
                txtpreci1.setText(conn.resultado.getString("ite_costo"));
                medida = conn.resultado.getString("uni_descri");
                txt_item_cod.setEnabled(true);
                buscadormateriales.setVisible(false);
                buscadormateriales.setModal(false);
                txtbuscador1.setText("");
                txtpreci1.setEnabled(true);
                txtpreci1.selectAll();
                txtpreci1.requestFocus();
                nodupli();
            } // TODO add your handling code here:
            catch (SQLException ex) {
                Logger.getLogger(retirosdemateriales1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }        // TODO add your handling code here:
    }//GEN-LAST:event_grilla2KeyPressed

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked
    }//GEN-LAST:event_jScrollPane3MouseClicked

    private void txtbuscador1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscador1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador1ActionPerformed

    private void txtbuscador1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyPressed

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            grilla2.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscador1KeyPressed

    private void txtbuscador1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyReleased
        wqqqq = txtbuscador1.getText();
        grilla1();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador1KeyReleased

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
    }//GEN-LAST:event_txttotalActionPerformed

    private void txttotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttotalKeyTyped
    }//GEN-LAST:event_txttotalKeyTyped

    private void jTextField7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField7.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String campo = jTextField7.getText();
            if (campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
                return;
            } else {
                try {
                    jTextField7.setText(String.valueOf(Redondear(Float.parseFloat(jTextField7.getText()))));
                    jTextField7.setEnabled(false);
                    grilla.setValueAt(jTextField6.getText(), fila, 3);
                    grilla.setValueAt(jTextField2.getText(), fila, 4);
                    grilla.setValueAt(jTextField7.getText(), fila, 5);
                    limpiarcampos();
                    Editor.setVisible(false);
                    Editor.setModal(false);
                    recorrergrilla();
                    grilla.clearSelection();
                    txt_item_cod1.requestFocus();
                } catch (SQLException ex) {
                    Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField6.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String campo = jTextField6.getText();
            if (campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
                return;
            } else {
                jTextField6.setText(String.valueOf(Redondear(Float.parseFloat(jTextField6.getText()))));
                jTextField7.setText(String.valueOf(Redondear((Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField2.getText())))));
                jTextField2.setEnabled(true);
                jTextField6.setEnabled(false);
                jTextField2.requestFocus();
            }
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyPressed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField2.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {

            String campo = jTextField2.getText();
            if (campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
                return;
            } else {
                jTextField2.setText(String.valueOf(Redondear(Float.parseFloat(jTextField2.getText()))));
                jTextField7.setText(String.valueOf(Redondear((Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField2.getText())))));
                jTextField7.setEnabled(true);
                jTextField2.setEnabled(false);
                jTextField7.requestFocus();

            }
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void txtbuscador2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            grilla3.requestFocus();
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

    private void grilla3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla3MouseClicked
        try {
            String Dato = (String) modelo3.getValueAt(grilla3.getSelectedRow(), 0).toString();
            lblcodigo.setText(Dato);
            lblcodigo.setEnabled(false);
            buscadorentrega.setVisible(false);
            buscadorentrega.setModal(false);
            cargadatos();
            if (operacion == 2) {

                int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Entrega de Materiales N° --> " + lblcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
                if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                {
                    borrar();
                } else // no quiere borrar
                {
                    JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                }
                btncancelar.doClick();

            } else {
                jComboBox1.setEnabled(true);
                jComboBox1.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grilla3MouseClicked

    private void grilla3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla3KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                String Dato = (String) modelo3.getValueAt(grilla3.getSelectedRow(), 0).toString();
                lblcodigo.setText(Dato);
                lblcodigo.setEnabled(false);
                buscadorentrega.setVisible(false);
                buscadorentrega.setModal(false);
                cargadatos();
                if (operacion == 2) {

                    int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Entrega de Materiales N° --> " + lblcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                    {
                        borrar();
                    } else // no quiere borrar
                    {
                        JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                    }
                    btncancelar.doClick();

                } else {
                    jComboBox1.setEnabled(true);
                    jComboBox1.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grilla3KeyPressed

    private void lblcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblcodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodigoActionPerformed

    private void txtbuscador2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscador2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Editor;
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnborrar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngrabar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JDialog buscadorentrega;
    private javax.swing.JDialog buscadormateriales;
    private javax.swing.JTable grilla;
    private javax.swing.JTable grilla2;
    private javax.swing.JTable grilla3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField lblcodigo;
    private javax.swing.JLabel lbldescri1;
    private org.netbeans.examples.lib.timerbean.Timer timer2;
    private javax.swing.JTextField txt_item_cod;
    private javax.swing.JTextField txt_item_cod1;
    private javax.swing.JTextField txtbuscador1;
    private javax.swing.JTextField txtbuscador2;
    private javax.swing.JTextField txtcantidad1;
    private javax.swing.JTextField txthora;
    private javax.swing.JTextField txtpreci1;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
