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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
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
public class presupuesto extends javax.swing.JPanel {

int operacion,fila,busca = 0; 
data mostra_data;
String wqqqq,medida;
String fechahora,Fecha;
javax.swing.table.DefaultTableModel cursor;
javax.swing.table.DefaultTableModel cursor1;
private int ab;
public com.mysql.jdbc.ResultSet resu;
java.sql.Statement snt;
ResultSet recur1; 
String citem,item,estado,b;

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
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
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

        DefaultTableModel modelo3 = new DefaultTableModel(){
     @Override
        public boolean isCellEditable(int row, int column) {
        return false;
    }
};
    
    public presupuesto() {
        initComponents();
        cursor = (javax.swing.table.DefaultTableModel)grilla.getModel();
        cursor1 = (javax.swing.table.DefaultTableModel)grilla1.getModel();
        mostra_data = new data();
        mostra_data.le_data();
        jLabel4.setText("Rubro:   ");
        timer2.start();
        des_btn();
        CabDetalle();
        CabDetalle1();
        cabecerabuscador();
        cabecerabuscador1();
        initListeners();
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

private void CabDetalle(){
modelo.addColumn("CÓD");
modelo.addColumn("RUBRO");
modelo.addColumn("MATERIAL");
modelo.addColumn("CANTIDAD");
modelo.addColumn("U");
modelo.addColumn("CANT. A CONS.");
modelo.addColumn("T. MATERIAL");
modelo.addColumn("MED. COMER.");
modelo.addColumn("U");
modelo.addColumn("TOTAL");
modelo.addColumn("UNIDADES");
modelo.addColumn("P/UNITARIO");
modelo.addColumn("SUBTOTAL");
grilla.getColumnModel().getColumn(0).setPreferredWidth(5);
grilla.getColumnModel().getColumn(1).setPreferredWidth(91);
grilla.getColumnModel().getColumn(2).setPreferredWidth(131);
grilla.getColumnModel().getColumn(3).setPreferredWidth(41);
grilla.getColumnModel().getColumn(4).setPreferredWidth(5);
grilla.getColumnModel().getColumn(5).setPreferredWidth(61);
grilla.getColumnModel().getColumn(6).setPreferredWidth(61);
grilla.getColumnModel().getColumn(7).setPreferredWidth(61);
grilla.getColumnModel().getColumn(8).setPreferredWidth(5);
grilla.getColumnModel().getColumn(9).setPreferredWidth(51);
grilla.getColumnModel().getColumn(10).setPreferredWidth(51);
grilla.getColumnModel().getColumn(11).setPreferredWidth(51);
grilla.getColumnModel().getColumn(12).setPreferredWidth(51);
DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
tcr1.setHorizontalAlignment(SwingConstants.CENTER);
grilla.getColumnModel().getColumn(4).setCellRenderer(tcr1);
grilla.getColumnModel().getColumn(8).setCellRenderer(tcr1);
grilla.getColumnModel().getColumn(10).setCellRenderer(tcr1);
DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
tcr.setHorizontalAlignment(SwingConstants.RIGHT);
grilla.getColumnModel().getColumn(0).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(3).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(5).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(6).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(7).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(9).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(11).setCellRenderer(tcr);
grilla.getColumnModel().getColumn(12).setCellRenderer(tcr);
}

private void CabDetalle1(){
modelo1.addColumn("CÓDIGO");
modelo1.addColumn("DESCRIPCIÓN");
modelo1.addColumn("U.MEDIDA");
modelo1.addColumn("C/UNITARIO");
modelo1.addColumn("CANTIDAD");
modelo1.addColumn("SUBTOTAL");
grilla1.getColumnModel().getColumn(0).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(1).setPreferredWidth(151);
grilla1.getColumnModel().getColumn(2).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(3).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(4).setPreferredWidth(31);
grilla1.getColumnModel().getColumn(5).setPreferredWidth(31);
DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
tcr1.setHorizontalAlignment(SwingConstants.CENTER);
grilla1.getColumnModel().getColumn(2).setCellRenderer(tcr1);
DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
tcr.setHorizontalAlignment(SwingConstants.RIGHT);
grilla1.getColumnModel().getColumn(0).setCellRenderer(tcr);
grilla1.getColumnModel().getColumn(3).setCellRenderer(tcr);
grilla1.getColumnModel().getColumn(4).setCellRenderer(tcr);
grilla1.getColumnModel().getColumn(5).setCellRenderer(tcr);
}

private void initListeners() {
    grilla.addMouseListener(new MouseAdapter() {

    public void mousePressed(MouseEvent me) {
        JTable table =(JTable) me.getSource();
        Point p = me.getPoint();
        int row = table.rowAtPoint(p);
        if (me.getClickCount() == 2) {
            fila = grilla.getSelectedRow();
            String codigo = modelo.getValueAt(grilla.getSelectedRow(), 0).toString();
            String rubro = modelo.getValueAt(grilla.getSelectedRow(), 1).toString();
            String material = modelo.getValueAt(grilla.getSelectedRow(), 2).toString();
            String cantidad = modelo.getValueAt(grilla.getSelectedRow(), 3).toString();
            String unidad1 = modelo.getValueAt(grilla.getSelectedRow(), 4).toString();
            String cantidadc = modelo.getValueAt(grilla.getSelectedRow(), 5).toString();
            String totalmaterial = modelo.getValueAt(grilla.getSelectedRow(), 6).toString();
            String medidacomer = modelo.getValueAt(grilla.getSelectedRow(), 7).toString();
            String unidad2 = modelo.getValueAt(grilla.getSelectedRow(), 8).toString();
            String total = modelo.getValueAt(grilla.getSelectedRow(), 9).toString();
            String unidad3 = modelo.getValueAt(grilla.getSelectedRow(), 10).toString();
            String punitario = modelo.getValueAt(grilla.getSelectedRow(), 11).toString();
            String subtotal = modelo.getValueAt(grilla.getSelectedRow(), 12).toString();
            jLabel24.setText(codigo);
            jLabel22.setText(rubro);
            jLabel23.setText(material);
            jTextField2.setText(cantidad);
            jLabel21.setText(unidad1);
            jTextField1.setText(cantidadc);
            jTextField3.setText(totalmaterial);
            jTextField4.setText(medidacomer);
            jLabel29.setText(unidad2);
            jTextField5.setText(total);
            jLabel32.setText(unidad3);
            jTextField6.setText(punitario);
            jTextField7.setText(subtotal);
            ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                Editor.setIconImage(icono.getImage());
                Editor.setTitle("EDITOR DE MATERIALES");
                Editor.pack();
                Editor.setLocationRelativeTo(null);
                Editor.setModal(true);
                jTextField2.setEnabled(true);
                jTextField2.requestFocus();
                Editor.setVisible(true);
        }
    }
    });
           } 



private void cabecerabuscador(){
    modelo2.addColumn("CODIGO");
    modelo2.addColumn("DESCRIPCIÓN");
    grilla2.getColumnModel().getColumn(0).setPreferredWidth(31);
    grilla2.getColumnModel().getColumn(1).setPreferredWidth(191);
}

 private void cabecerabuscador1(){
modelo3.addColumn("CÓDIGO");
modelo3.addColumn("FECHA Y HORA");
modelo3.addColumn("DESTINATARIO");
modelo3.addColumn("PROYECTO");
grilla3.getColumnModel().getColumn(0).setPreferredWidth(21);
grilla3.getColumnModel().getColumn(1).setPreferredWidth(71);
grilla3.getColumnModel().getColumn(2).setPreferredWidth(151);
grilla3.getColumnModel().getColumn(3).setPreferredWidth(191);
}

private void lim_text(){
lblcodigo.setText("");
txtdestinatario.setText("");
txtdenominacion.setText("");
txtproyecto.setText("");
txt_item_cod1.setText("");
lbldescri1.setText("");
txtpreci1.setText("");
txtcantidad1.setText("");
txtsubtotalmo.setText("");
txt_item_cod.setText("");
lbldescri.setText("");
txtdenominacion.setText("");
txtcantidad.setText("");
txtsubtotalma.setText("");
txttotal.setText("");
txtporhon.setText("");
txtmonporhon.setText("");
txtmontoporgas.setText("");
txtporgas.setText("");
}

private void des_text(){
lblcodigo.setEnabled(false);
txtdestinatario.setEnabled(false);
txtdenominacion.setEnabled(false);
txtproyecto.setEnabled(false);
txt_item_cod1.setEnabled(false);
lbldescri1.setEnabled(false);
txtpreci1.setEnabled(false);
txtcantidad1.setEnabled(false);
txtsubtotalmo.setEnabled(false);
txt_item_cod.setEnabled(false);
lbldescri.setEnabled(false);
txtdenominacion.setEnabled(false);
txtcantidad.setEnabled(false);
txtsubtotalma.setEnabled(false);
txttotal.setEnabled(false);
txtporhon.setEnabled(false);
txtmonporhon.setEnabled(false);
txtmontoporgas.setEnabled(false);
txtporgas.setEnabled(false);
}

private void gencod(){
    try
	{
	ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(pre_codigo),0)+1 AS xxx FROM presupuesto");
        conn.resultado.next();
        lblcodigo.setText(conn.resultado.getString("xxx"));

 	}
	catch (SQLException exceptionSql)
	{
		JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
	}
}

public void grilla1()
       {
         String ac;
          modelo2.setRowCount(0);
        
        try {
            ver_conex conn2 =new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            if(busca == 1){
            recur1=snt.executeQuery("SELECT rub_codigo,rub_descri FROM rubro WHERE rub_descri LIKE '%"+wqqqq+"%'");
            } else if(busca == 3){
            recur1=snt.executeQuery("SELECT ite_codigo,ite_descri FROM items WHERE ite_descri LIKE '%"+wqqqq+"%'");
            } else {
            recur1=snt.executeQuery("SELECT mdo_codigo,mdo_descri FROM mano_de_obra WHERE mdo_descri LIKE '%"+wqqqq+"%'");   
            }
            System.out.println(recur1);
            if (recur1.wasNull())
            {
                    ab=0;
                    return;
            }
            
                if(recur1.next()) {
                  do {
                    if(busca == 1){
            ab = recur1.getInt("rub_codigo");
                    ac = recur1.getString("rub_descri");
            } else if(busca == 3){
            ab = recur1.getInt("ite_codigo");
                    ac = recur1.getString("ite_descri");
            } else {
             ab = recur1.getInt("mdo_codigo");
                    ac = recur1.getString("mdo_descri");   
            }
                    
                    
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

public void grilla2()
       {
         String ac,ad,ae;
          modelo2.setRowCount(0);
        
        try {
            ver_conex conn2 =new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1=snt.executeQuery("SELECT pre_codigo,pre_fechor,pre_destin,pre_proyec FROM presupuesto WHERE pre_estado LIKE 'ACTIVO' AND pre_proyec LIKE '%"+wqqqq+"%' ORDER BY PRE_codigo DESC");
            if (recur1.wasNull())
            {
                    ab=0;
                    return;
            }
            
                if(recur1.next()) {
                  do {
                    ab = recur1.getInt("pre_codigo");
                    ac = recur1.getString("pre_fechor");
                    ad = recur1.getString("pre_destin");
                    ae = recur1.getString("pre_proyec");
                    modelo2.addRow(new Object[]{ab,ac,ad,ae});
                } while(recur1.next());
                } else {
                    ab=0;
                }

            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de Sintaxis" , "Verifique",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }

private void tirarGrilla() throws SQLException{
ver_conex  conn = new ver_conex();
conn.sentencia = conn.conexion.createStatement();
conn.resultado = conn.sentencia.executeQuery("SELECT I.ite_codigo,rub_descri,I.ite_descri,rde_cantid,U3.uni_descri,I.ite_medcom,U1.uni_descri descri1,U2.uni_descri descri2,I.ite_costo"
        + " FROM items I "
        + "INNER JOIN unidades U1 ON I.uni_codigo = U1.uni_codigo"
        + "  INNER JOIN unidades U2 ON I.uni_precom = U2.uni_codigo"
        + "  INNER JOIN `rubro_detalle` rubro_detalle ON rubro_detalle.ite_codigo = I.ite_codigo"
        + "  INNER JOIN `rubro` rubro ON rubro.rub_codigo = rubro_detalle.rub_codigo"
        + "  INNER JOIN unidades U3 ON rubro_detalle.uni_codigo = U3.uni_codigo"
        + " AND rubro.rub_codigo = " + txt_item_cod.getText() + " ORDER BY I.ite_descri");
Double cant = Double.parseDouble(txtcantidad.getText());
Object[] datos = new Object[13];
while (conn.resultado.next()){
datos[0] = conn.resultado.getString("ite_codigo");
datos[1] = conn.resultado.getString("rub_descri");
datos[2] = conn.resultado.getString("ite_descri");
datos[3] = conn.resultado.getString("rde_cantid");
datos[4] = conn.resultado.getString("uni_descri");
datos[5] = String.valueOf(cant);
datos[6] = String.valueOf(getDecimal(3,(Double.parseDouble(conn.resultado.getString("rde_cantid"))*cant)));
datos[7] = String.valueOf(getDecimal(2,(Double.parseDouble(conn.resultado.getString("ite_medcom")))));
datos[8] = conn.resultado.getString("descri1");
datos[9] = String.valueOf(getDecimal(0,((Double.parseDouble(conn.resultado.getString("rde_cantid"))*cant)/Double.parseDouble(conn.resultado.getString("ite_medcom")))));
System.out.println(datos[9]);
datos[10] = conn.resultado.getString("descri2");
datos[11] = conn.resultado.getString("ite_costo");
datos[12] = String.valueOf(getDecimal(2,((Float.parseFloat((String)datos[9])*Double.parseDouble(conn.resultado.getString("ite_costo"))))));
modelo.addRow(datos);
}
conn.resultado.first();
recorrergrilla();
btngrabar.setEnabled(true);
txt_item_cod.setText("");
lbldescri.setText("");
txtcantidad.setEnabled(false);
if(estado.equals("CERRADO")){
    txtdenominacion.setText("");
    txtcantidad.setText("");
    txt_item_cod.setEnabled(true);
    txt_item_cod.requestFocus();
} else {
                  int mensaje = JOptionPane.showConfirmDialog(this,"DESEA AGREGAR MATERIALES AL RUBRO "+ txtdenominacion.getText() +" AHORA?","CONFIRMAR",JOptionPane.YES_NO_OPTION);
                  if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar                  
                  {
                      b = "MATERIALES";
                      jLabel4.setText("Material:");
                      txt_item_cod.setEnabled(true);
                      txt_item_cod.requestFocus();
                  }
                  else // no quiere borrar
                  {
                       txtdenominacion.setText("");
                       txtcantidad.setText("");
                       txt_item_cod.setEnabled(true);
                       txt_item_cod.requestFocus();
                  } 
}

}

public void nodupli(){
        String aux;
        int cantfilas = grilla.getRowCount();
        int x;
        citem = txtdenominacion.getText();
        for (x = 0; x < cantfilas; x++) {
        item  = (String) grilla.getValueAt(x, 1);//codigo
        aux = txtdenominacion.getText();
        if (item.equals(citem)){
        int mensaje = JOptionPane.showConfirmDialog(this,"RUBRO "+ txtdenominacion.getText() +" YA SE ENCUENTRA EN EL PRESUPUESTO, DESEA AGREGARLE MATERIALES?","CONFIRMAR",JOptionPane.YES_NO_OPTION);
                    if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar                  
                    {
                      b = "MATERIALES";
                      jLabel4.setText("Material:   ");
                      txt_item_cod.setText("");
                      txt_item_cod.setEnabled(true);
                      txt_item_cod.requestFocus();
                    }
                    else // no quiere borrar
                   {    
                       b = "RUBRO"; 
                       jLabel4.setText("Rubro:   ");
                       txt_item_cod.setText("");
                       txtdenominacion.setText("");
                       txtcantidad.setText("");
                       txt_item_cod.setEnabled(true);
                       txt_item_cod.requestFocus();
                  } 
                  return;
    }
    }
} 

private void tirarGrilla1() throws SQLException{
float cant = Float.parseFloat(txtcantidad1.getText());
Object[] datos = new Object[6];
datos[0] = txt_item_cod1.getText();
datos[1] = lbldescri1.getText();
datos[2] = medida;
datos[3] = String.valueOf(Redondear(Float.parseFloat(txtpreci1.getText())));
datos[4] = txtcantidad1.getText();
datos[5] = cant*Float.parseFloat(txtpreci1.getText());
modelo1.addRow(datos);
recorrergrilla1();
btngrabar.setEnabled(true);
txt_item_cod1.setText("");
lbldescri1.setText("");
txtpreci1.setText("");
txtcantidad1.setText("");
txtcantidad1.setEnabled(false);
txt_item_cod1.setEnabled(true);
txt_item_cod1.requestFocus();
}

private void recorrergrilla () throws SQLException {
javax.swing.table.TableModel model = grilla.getModel();
int c = 0;
float total_1 = 0;
for (int i = 0; i < model.getRowCount();i++) {
 String value=  (String) modelo.getValueAt(i, 12).toString();
 Float value1 = Float.parseFloat(value);
 if (value1 != 0 ) {
  total_1=total_1+value1;
  c++;
 }
}
String cadena = String.valueOf(Redondear(total_1));
txtsubtotalma.setText(cadena); 
if (txtsubtotalmo.getText().isEmpty()) {
    txtsubtotalmo.setText("0");    
}
Float porgas = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText()))*Float.parseFloat(txtporgas.getText()))/100;
txtmontoporgas.setText(String.valueOf(Redondear(porgas)));
Float porhon = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText()))*Float.parseFloat(txtporhon.getText()))/100;
txtmonporhon.setText(String.valueOf(Redondear(porhon)));
txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));

}

private void recorrergrilla1 () throws SQLException {
javax.swing.table.TableModel model=grilla1.getModel();
int c = 0;
float total_1 = 0;
for (int i = 0; i < model.getRowCount();i++) {
 Float value= (Float) model.getValueAt(i,5);
 if (value != 0 ) {
  total_1=total_1+value;
  c++;
 }
}
String cadena = String.valueOf(Redondear(total_1));
txtsubtotalmo.setText(cadena); 
if (txtsubtotalma.getText().isEmpty()) {
    txtsubtotalma.setText("0");    
}
Float porgas = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText()))*Float.parseFloat(txtporgas.getText()))/100;
txtmontoporgas.setText(String.valueOf(Redondear(porgas)));
Float porhon = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText()))*Float.parseFloat(txtporhon.getText()))/100;
txtmonporhon.setText(String.valueOf(Redondear(porhon)));
txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));

}

public double Redondear(double numero)
{
 return Math.rint(numero*100)/100;
}

    public double getDecimal(int numeroDecimales,double decimal){
decimal = decimal*(java.lang.Math.pow(10, numeroDecimales));
decimal = java.lang.Math.round(decimal);
decimal = decimal/java.lang.Math.pow(10, numeroDecimales);

return decimal;  }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timer2 = new org.netbeans.examples.lib.timerbean.Timer();
        buscador = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        grilla2 = new javax.swing.JTable();
        txtbuscador = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        buscadorpresupuesto = new javax.swing.JDialog();
        jLabel19 = new javax.swing.JLabel();
        txtbuscador1 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        grilla3 = new javax.swing.JTable();
        Editor = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btngrabar = new javax.swing.JButton();
        btnborrar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_item_cod = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtcantidad = new javax.swing.JTextField();
        lbldescri = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        grilla = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtsubtotalma = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtdenominacion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_item_cod1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lbldescri1 = new javax.swing.JLabel();
        txtpreci1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtcantidad1 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        grilla1 = new javax.swing.JTable();
        txtsubtotalmo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txthora = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtdestinatario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtproyecto = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtporhon = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtmonporhon = new javax.swing.JTextField();
        txtmontoporgas = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtporgas = new javax.swing.JTextField();

        timer2.addTimerListener(new org.netbeans.examples.lib.timerbean.TimerListener() {
            public void onTime(java.awt.event.ActionEvent evt) {
                timer2OnTime(evt);
            }
        });

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
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
        jScrollPane2.setViewportView(grilla2);

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Buscador:");

        txtbuscador1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyReleased(evt);
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

        javax.swing.GroupLayout buscadorpresupuestoLayout = new javax.swing.GroupLayout(buscadorpresupuesto.getContentPane());
        buscadorpresupuesto.getContentPane().setLayout(buscadorpresupuestoLayout);
        buscadorpresupuestoLayout.setHorizontalGroup(
            buscadorpresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorpresupuestoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorpresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(buscadorpresupuestoLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador1)))
                .addContainerGap())
        );
        buscadorpresupuestoLayout.setVerticalGroup(
            buscadorpresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscadorpresupuestoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buscadorpresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtbuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Código:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Rubro:");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Material:");

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("    ");

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setText("    ");

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setText("    ");

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("    ");

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setText("Cantidad a Construir:");

        jTextField1.setEnabled(false);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

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

        jLabel27.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel27.setText("Total Materiales:");

        jTextField3.setEnabled(false);
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setText("Medida Comercial:");

        jTextField4.setEnabled(false);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setText("    ");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setText("Total:");

        jTextField5.setEnabled(false);
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setText("Unidades:");

        jLabel32.setBackground(new java.awt.Color(255, 255, 255));
        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("    ");

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
        jLabel34.setText("Precio Unitario:");

        jTextField7.setEnabled(false);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditorLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EditorLayout.createSequentialGroup()
                        .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EditorLayout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5))
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(EditorLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EditorLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel34)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel33)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField7)))))
                .addGap(8, 8, 8))
        );
        EditorLayout.setVerticalGroup(
            EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(EditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Material:");

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

        lbldescri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        grilla.setModel(modelo);
        grilla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grillaKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(grilla);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Total:");

        txtsubtotalma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtsubtotalma.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtsubtotalma.setEnabled(false);
        txtsubtotalma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsubtotalmaActionPerformed(evt);
            }
        });
        txtsubtotalma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotalmaKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Rubro:");

        txtdenominacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtdenominacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdenominacion.setEnabled(false);
        txtdenominacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdenominacionMouseClicked(evt);
            }
        });
        txtdenominacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdenominacionActionPerformed(evt);
            }
        });
        txtdenominacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdenominacionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdenominacionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtdenominacion, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(325, 325, 325)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtsubtotalma, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtdenominacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsubtotalma, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MATERIALES", jPanel2);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Trabajo:");

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

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Costo:");

        lbldescri1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

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

        grilla1.setModel(modelo1);
        grilla1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grilla1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(grilla1);

        txtsubtotalmo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtsubtotalmo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtsubtotalmo.setEnabled(false);
        txtsubtotalmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsubtotalmoActionPerformed(evt);
            }
        });
        txtsubtotalmo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotalmoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Total:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txt_item_cod1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbldescri1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtpreci1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jLabel12)
                        .addGap(27, 27, 27)
                        .addComponent(txtcantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtsubtotalmo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_item_cod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(lbldescri1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtpreci1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtcantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsubtotalmo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MANO DE OBRA", jPanel3);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        lblcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblcodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lblcodigo.setEnabled(false);
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

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Destinatario:");

        txtdestinatario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtdestinatario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdestinatario.setEnabled(false);
        txtdestinatario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdestinatarioMouseClicked(evt);
            }
        });
        txtdestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdestinatarioActionPerformed(evt);
            }
        });
        txtdestinatario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdestinatarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdestinatarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdestinatarioKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Proyecto:");

        txtproyecto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtproyecto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtproyecto.setEnabled(false);
        txtproyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtproyectoMouseClicked(evt);
            }
        });
        txtproyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproyectoActionPerformed(evt);
            }
        });
        txtproyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtproyectoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproyectoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtproyectoKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Honorarios %:");

        txtporhon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtporhon.setText("0");
        txtporhon.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtporhon.setEnabled(false);
        txtporhon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtporhonMouseClicked(evt);
            }
        });
        txtporhon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtporhonActionPerformed(evt);
            }
        });
        txtporhon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtporhonFocusLost(evt);
            }
        });
        txtporhon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtporhonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtporhonKeyTyped(evt);
            }
        });

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

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Total:");

        txtmonporhon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmonporhon.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtmonporhon.setEnabled(false);
        txtmonporhon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmonporhonMouseClicked(evt);
            }
        });
        txtmonporhon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmonporhonActionPerformed(evt);
            }
        });
        txtmonporhon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtmonporhonFocusLost(evt);
            }
        });
        txtmonporhon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonporhonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonporhonKeyTyped(evt);
            }
        });

        txtmontoporgas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmontoporgas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtmontoporgas.setEnabled(false);
        txtmontoporgas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmontoporgasMouseClicked(evt);
            }
        });
        txtmontoporgas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmontoporgasActionPerformed(evt);
            }
        });
        txtmontoporgas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtmontoporgasFocusLost(evt);
            }
        });
        txtmontoporgas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmontoporgasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmontoporgasKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("Gastos Varios %:");

        txtporgas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtporgas.setText("0");
        txtporgas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtporgas.setEnabled(false);
        txtporgas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtporgasMouseClicked(evt);
            }
        });
        txtporgas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtporgasActionPerformed(evt);
            }
        });
        txtporgas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtporgasFocusLost(evt);
            }
        });
        txtporgas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtporgasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtporgasKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtdestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(lblcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txthora, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(26, 26, 26)
                        .addComponent(txtporgas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtmontoporgas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(26, 26, 26)
                        .addComponent(txtporhon, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtmonporhon, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(314, 314, 314))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtporgas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(txtmontoporgas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtporhon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(txtmonporhon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        operacion = 1;
        b = "RUBRO";
        hab_btn();
        gencod();
        txtdestinatario.setEnabled(true);
        txtdestinatario.requestFocus();
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnagregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnagregarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnagregar.doClick();
        }
    }//GEN-LAST:event_btnagregarKeyPressed

    private void btnborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnborrarActionPerformed
        operacion = 2;
        hab_btn();
        lblcodigo.setEnabled(true);
        lblcodigo.requestFocus();
    }//GEN-LAST:event_btnborrarActionPerformed

    private void btnborrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnborrarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnborrar.doClick();
        }
    }//GEN-LAST:event_btnborrarKeyPressed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        operacion = 3;
        b = "RUBRO";
        hab_btn();
        lblcodigo.setEnabled(true);
        lblcodigo.requestFocus();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnmodificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnmodificarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnmodificar.doClick();
        }
    }//GEN-LAST:event_btnmodificarKeyPressed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
            grilla.setEnabled(false);
            grilla1.setEnabled(false);
            des_btn();
            lim_text();
            des_text();
            txtporgas.setText("0");
            txtporhon.setText("0");
            modelo.setRowCount(0);
            modelo1.setRowCount(0);
            b = "RUBRO";
            busca = 1;
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btncancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btncancelarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btncancelar.doClick();
        }
    }//GEN-LAST:event_btncancelarKeyPressed

    private void btngrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngrabarActionPerformed
//            unidad = buscaunidades ((String) jComboBox1.getSelectedItem());
//            operaciones();
        
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
            int mensaje = JOptionPane.showConfirmDialog(this,"DESEA IMPRIMIR EL PRESUPUESTO?","CONFIRMA",JOptionPane.YES_NO_OPTION);
                  if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                  {
            try {
                imprimir1();
                imprimir2();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
                  }
                  else // no quiere borrar
                  {
                      //JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atencion", JOptionPane.ERROR_MESSAGE);
                  }
            btncancelar.doClick();
    }//GEN-LAST:event_btngrabarActionPerformed

    private void btngrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btngrabarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btngrabar.doClick();
        }
    }//GEN-LAST:event_btngrabarKeyPressed

    private void txthoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthoraActionPerformed

    private void txtdestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdestinatarioActionPerformed
        String campo = txtdestinatario.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtproyecto.setEnabled(true);
            txtdestinatario.setEnabled(false);
            txtproyecto.requestFocus();
        }
    }//GEN-LAST:event_txtdestinatarioActionPerformed

    private void txtdestinatarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdestinatarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdestinatarioKeyPressed

    private void txtdestinatarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdestinatarioKeyReleased
        txtdestinatario.setText(txtdestinatario.getText().toUpperCase());
    }//GEN-LAST:event_txtdestinatarioKeyReleased

    private void txtdestinatarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdestinatarioKeyTyped
        if (txtdestinatario.getText().length() == 100) {
            txtdestinatario.setText(txtdestinatario.getText().toUpperCase());
            txtdestinatario.setEnabled(false);
            txtproyecto.setEnabled(true);
            txtproyecto.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtdestinatario.getText().length() == 100) {
                txtdestinatario.setEnabled(true);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtdestinatarioKeyTyped

    private void txtproyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproyectoActionPerformed
        String campo = txtproyecto.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txt_item_cod.setEnabled(true);
            txtproyecto.setEnabled(false);
            txt_item_cod.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoActionPerformed

    private void txtproyectoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyPressed

    private void txtproyectoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyReleased
      txtproyecto.setText(txtproyecto.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyReleased

    private void txtproyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyTyped
if (txtproyecto.getText().length() == 100) {
            txtproyecto.setText(txtproyecto.getText().toUpperCase());
            txtproyecto.setEnabled(false);
            txt_item_cod.setEnabled(true);
            txt_item_cod.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtproyecto.getText().length() == 100) {
                txtproyecto.setEnabled(true);
            }
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyTyped

    private void txt_item_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_codActionPerformed
        String campo = txt_item_cod.getText();
        if (b.equals("RUBRO")){
        busca = 1;
        if (campo.isEmpty()) {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT rub_codigo,rub_descri FROM rubro");
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
                buscador.setTitle("RUBROS");
                buscador.pack();
                buscador.setLocationRelativeTo(null);
                buscador.setModal(true);
                txtbuscador.requestFocus();
                buscador.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT rub_codigo,rub_descri,rub_cerrad FROM rubro WHERE rub_codigo = " + txt_item_cod.getText());
                boolean encontro = conn.resultado.next();
                if (encontro==true){
                    txtdenominacion.setText(conn.resultado.getString("rub_descri"));
                    estado = conn.resultado.getString("rub_cerrad");
                    txt_item_cod.setEnabled(true);
                    buscador.setVisible(false);
                    buscador.setModal(false);
                    txtbuscador.setText("");
                    txtcantidad.setText("");
                    txtcantidad.setEnabled(true);
                    txtcantidad.selectAll();
                    txtcantidad.requestFocus();
                    nodupli();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encuentra Material con codigo -->" + txt_item_cod.getText() , "Atención", JOptionPane.ERROR_MESSAGE);
                    txt_item_cod.setText("");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } else {
          busca = 3;
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
        } else {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT ite_codigo,ite_descri,uni_descri FROM items,unidades WHERE items.uni_codigo = unidades.uni_codigo AND items.ite_codigo = " + txt_item_cod.getText());
                boolean encontro = conn.resultado.next();
                if (encontro==true){
                    lbldescri.setText(conn.resultado.getString("ite_descri"));
                    txt_item_cod.setEnabled(true);
                    buscador.setVisible(false);
                    buscador.setModal(false);
                    txtbuscador.setText("");
                    tirargrilla1();
                    int mensaje = JOptionPane.showConfirmDialog(this,"DESEA AGREGAR OTRO MATERIAL AL RUBRO "+ txtdenominacion.getText() +" AHORA?","CONFIRMAR",JOptionPane.YES_NO_OPTION);
                    if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar                  
                    {
                      txt_item_cod.setEnabled(true);
                      txt_item_cod.requestFocus();
                    }
                    else // no quiere borrar
                   {    
                       b = "RUBRO"; 
                       jLabel4.setText("Rubro:   ");
                       txtdenominacion.setText("");
                       txtcantidad.setText("");
                       txt_item_cod.setEnabled(true);
                       txt_item_cod.requestFocus();
                  } 
                } else {
                    JOptionPane.showMessageDialog(null, "No se encuentra Material con codigo -->" + txt_item_cod.getText() , "Atención", JOptionPane.ERROR_MESSAGE);
                    txt_item_cod.setText("");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }  
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
            try {
                txtcantidad.setText(String.valueOf(getDecimal(3,Double.parseDouble(txtcantidad.getText()))));
                tirarGrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtcantidadActionPerformed

    private void txtcantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyPressed

    }//GEN-LAST:event_txtcantidadKeyPressed

    private void txtcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyTyped
               char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtcantidad.getText().contains(".")) {
            evt.consume();
        }  
    }//GEN-LAST:event_txtcantidadKeyTyped

    private void txt_item_cod1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_cod1ActionPerformed
String campo = txt_item_cod1.getText();
busca = 2;
        if (campo.isEmpty()) {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT mdo_codigo,mdo_descri FROM mano_de_obra");
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
                buscador.setTitle("MANO DE OBRA");
                buscador.pack();
                buscador.setLocationRelativeTo(null);
                buscador.setModal(true);
                txtbuscador.requestFocus();
                buscador.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT mdo_codigo,mdo_descri,uni_descri FROM mano_de_obra,unidades WHERE mano_de_obra.uni_codigo = unidades.uni_codigo AND mano_de_obra.mdo_codigo = " + txt_item_cod1.getText());
                boolean encontro = conn.resultado.next();
                if (encontro==true){
                    lbldescri1.setText(conn.resultado.getString("mdo_descri"));
                    medida = conn.resultado.getString("uni_descri");
                    txt_item_cod1.setEnabled(true);
                    buscador.setVisible(false);
                    buscador.setModal(false);
                    txtbuscador.setText("");
                    txtpreci1.setText("");
                    txtcantidad1.setText("");
                    txtpreci1.setEnabled(true);
                    txtpreci1.selectAll();
                    txtpreci1.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encuentra Mano de Obra con codigo -->" + txt_item_cod.getText() , "Atención", JOptionPane.ERROR_MESSAGE);
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

        String asas=txt_item_cod1.getText();
        int conta1=asas.length();
        if(evt.getKeyChar()=='0'&&conta1==0){
            evt.consume();
        }else{
            if(Character.isDigit(k)==false) {
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
                tirarGrilla1();
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

    private void txtsubtotalmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsubtotalmoActionPerformed

    }//GEN-LAST:event_txtsubtotalmoActionPerformed

    private void txtsubtotalmoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotalmoKeyTyped

    }//GEN-LAST:event_txtsubtotalmoKeyTyped

    private void txtsubtotalmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsubtotalmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotalmaActionPerformed

    private void txtsubtotalmaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotalmaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotalmaKeyTyped

    private void txtporhonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtporhonActionPerformed
        String campo =  txtporhon.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                txtporhon.setText(String.valueOf(Redondear(Float.parseFloat( txtporhon.getText()))));
                btngrabar.setEnabled(true);
                Float porhon = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText()))*Float.parseFloat(txtporhon.getText()))/100;
                txtmonporhon.setText(String.valueOf(Redondear(porhon)));
                txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));
                btngrabar.requestFocus();
                 txtporhon.setEnabled(false);
        }
    }//GEN-LAST:event_txtporhonActionPerformed

    private void txtporhonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtporhonFocusLost
//        txtdescuento.setText(String.valueOf(Redondear(Float.parseFloat(txtdescuento.getText()))));
    }//GEN-LAST:event_txtporhonFocusLost

    private void txtporhonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporhonKeyPressed
//        char c = evt.getKeyChar();
//        if (((c > '0') || (c < '9')) || (c == '.')) {
//            TimerTask tarea = new TimerTask(){
//                public void run() {
//                    precio();
//                }
//            };
//            Timer temp = new Timer();
//            temp.schedule(tarea, 50);}
    }//GEN-LAST:event_txtporhonKeyPressed

    private void txtporhonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporhonKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtporhon.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtporhonKeyTyped

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

    private void txttotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalKeyTyped

    private void txtdenominacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdenominacionMouseClicked
txtdenominacion.setEnabled(true);
txtdenominacion.selectAll();    
txtdenominacion.requestFocus();
    }//GEN-LAST:event_txtdenominacionMouseClicked

    private void txtdenominacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdenominacionActionPerformed
        String campo = txtdenominacion.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            txtcantidad.setEnabled(true);
            txtdenominacion.setEnabled(false);
            txtcantidad.requestFocus();
        }
    }//GEN-LAST:event_txtdenominacionActionPerformed

    private void txtdenominacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdenominacionKeyTyped
        if (txtdenominacion.getText().length() == 50) {
            txtdenominacion.setText(txtdenominacion.getText().toUpperCase());
            txtdenominacion.setEnabled(false);
            txtcantidad.setEnabled(true);
            txtcantidad.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtdenominacion.getText().length() == 50) {
                txtdenominacion.setEnabled(true);
            }
        }  
    }//GEN-LAST:event_txtdenominacionKeyTyped

    private void timer2OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer2OnTime
        mostra_data.le_hora();
        txthora.setText(mostra_data.ano+"/"+mostra_data.mes+"/"+mostra_data.dia+" " + mostra_data.hora);
    }//GEN-LAST:event_timer2OnTime

    private void txtmonporhonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmonporhonActionPerformed
String campo = txtmonporhon.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                txtmonporhon.setText(String.valueOf(Redondear(Float.parseFloat(txtmonporhon.getText()))));
                txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));
                txtporhon.setText(String.valueOf(Redondear((Float.parseFloat(txtmonporhon.getText())*100)/(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())))));
                btngrabar.setEnabled(true);
                btngrabar.requestFocus();
                txtmonporhon.setEnabled(false);
        }        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonporhonActionPerformed

    private void txtmonporhonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtmonporhonFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonporhonFocusLost

    private void txtmonporhonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonporhonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonporhonKeyPressed

    private void txtmonporhonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonporhonKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtmonporhon.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtmonporhonKeyTyped

    private void txtmontoporgasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmontoporgasActionPerformed
 String campo = txtmontoporgas.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                txtmontoporgas.setText(String.valueOf(Redondear(Float.parseFloat(txtmontoporgas.getText()))));
                txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));
                txtporgas.setText(String.valueOf(Redondear((Float.parseFloat(txtmontoporgas.getText())*100)/(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())))));
                txtporhon.setEnabled(true);
                txtporhon.requestFocus();
                txtmontoporgas.setEnabled(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoporgasActionPerformed

    private void txtmontoporgasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtmontoporgasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoporgasFocusLost

    private void txtmontoporgasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoporgasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoporgasKeyPressed

    private void txtmontoporgasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoporgasKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtmontoporgas.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoporgasKeyTyped

    private void txtporgasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtporgasActionPerformed
        String campo =  txtporgas.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
             txtporgas.setText(String.valueOf(Redondear(Float.parseFloat( txtporgas.getText()))));
                txtporhon.setEnabled(true);
                Float porgas = ((Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText()))*Float.parseFloat(txtporgas.getText()))/100;
                txtmontoporgas.setText(String.valueOf(Redondear(porgas)));
                txttotal.setText(String.valueOf(Redondear(Float.parseFloat(txtsubtotalma.getText())+Float.parseFloat(txtsubtotalmo.getText())+Float.parseFloat(txtmontoporgas.getText())+Float.parseFloat(txtmonporhon.getText()))));
                txtporhon.requestFocus();
                 txtporgas.setEnabled(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtporgasActionPerformed

    private void txtporgasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtporgasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtporgasFocusLost

    private void txtporgasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporgasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtporgasKeyPressed

    private void txtporgasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporgasKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && txtporgas.getText().contains(".")) {
            evt.consume();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtporgasKeyTyped

    private void txt_item_codMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_codMouseClicked
txt_item_cod.setEnabled(true);
txt_item_cod.setText("");    
txt_item_cod.requestFocus();
    }//GEN-LAST:event_txt_item_codMouseClicked

    private void txt_item_cod1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_cod1MouseClicked
txt_item_cod1.setEnabled(true);
txt_item_cod1.setText("");    
txt_item_cod1.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_item_cod1MouseClicked

    private void grilla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla2MouseClicked
      try {
                if (busca == 1){
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                txtdenominacion.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("select rub_cerrad from rubro where rub_codigo = " + codigo);
                conn.resultado.next();
                estado = conn.resultado.getString("rub_cerrad");
                txt_item_cod.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtcantidad.setEnabled(true);
                txtcantidad.selectAll();
                txtcantidad.requestFocus();
                nodupli();
                } else if (busca == 3) { 
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                lbldescri.setText(descri);
                tirargrilla1();
                int mensaje = JOptionPane.showConfirmDialog(this,"DESEA AGREGAR OTRO MATERIAL AL RUBRO "+ txtdenominacion.getText() +" AHORA?","CONFIRMAR",JOptionPane.YES_NO_OPTION);
                    if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar                  
                    {
                      txt_item_cod.setEnabled(true);
                      txt_item_cod.requestFocus();
                    }
                    else // no quiere borrar
                   {    
                       b = "RUBRO";
                       txt_item_cod.setText("");
                       lbldescri.setText("");
                       buscador.setVisible(false);
                       buscador.setModal(false);
                       jLabel4.setText("Rubro:   ");
                       txtdenominacion.setText("");
                       txtcantidad.setText("");
                       txt_item_cod.setEnabled(true);
                       txt_item_cod.requestFocus();
                  } 
                } else {
                 String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod1.setText(codigo);
                lbldescri1.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT uni_descri FROM unidades,mano_de_obra WHERE mano_de_obra.uni_codigo = unidades.uni_codigo AND mano_de_obra.mdo_codigo =  " + codigo);
                conn.resultado.next();
                medida = conn.resultado.getString("uni_descri");
                txt_item_cod1.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtpreci1.setEnabled(true);
                txtpreci1.selectAll();
                txtpreci1.requestFocus();   
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_grilla2MouseClicked

    private void grilla2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla2KeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            try {
                if (busca == 1){
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                txtdenominacion.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("select rub_cerrad from rubro where rub_codigo = " + codigo);
                conn.resultado.next();
                estado = conn.resultado.getString("rub_cerrad");
                txt_item_cod.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtcantidad.setEnabled(true);
                txtcantidad.selectAll();
                txtcantidad.requestFocus();
                nodupli();
                } else if (busca == 3) {
                String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod.setText(codigo);
                lbldescri.setText(descri);
                tirargrilla1();
                int mensaje = JOptionPane.showConfirmDialog(this,"DESEA AGREGAR OTRO MATERIAL AL RUBRO "+ txtdenominacion.getText() +" AHORA?","CONFIRMAR",JOptionPane.YES_NO_OPTION);
                    if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar                  
                    {
                      txt_item_cod.setEnabled(true);
                      txt_item_cod.requestFocus();
                    }
                    else // no quiere borrar
                   {    
                       b = "RUBRO"; 
                       buscador.setVisible(false);
                       buscador.setModal(false);
                       txt_item_cod.setText("");
                       lbldescri.setText("");
                       jLabel4.setText("Rubro:   ");
                       txtdenominacion.setText("");
                       txtcantidad.setText("");
                       txt_item_cod.setEnabled(true);
                       txt_item_cod.requestFocus();
                  } 
                }  else {
                 String codigo = modelo2.getValueAt(grilla2.getSelectedRow(), 0).toString();
                String descri = modelo2.getValueAt(grilla2.getSelectedRow(), 1).toString();
                txt_item_cod1.setText(codigo);
                lbldescri1.setText(descri);
                ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT uni_descri FROM unidades,mano_de_obra WHERE mano_de_obra.uni_codigo = unidades.uni_codigo AND mano_de_obra.mdo_codigo =  " + codigo);
                conn.resultado.next();
                medida = conn.resultado.getString("uni_descri");
                txt_item_cod1.setEnabled(true);
                buscador.setVisible(false);
                buscador.setModal(false);
                txtbuscador.setText("");
                txtpreci1.setEnabled(true);
                txtpreci1.selectAll();
                txtpreci1.requestFocus();   
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_grilla2KeyPressed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked

    }//GEN-LAST:event_jScrollPane2MouseClicked

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

    private void txtdenominacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdenominacionKeyReleased
        txtdenominacion.setText(txtdenominacion.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtdenominacionKeyReleased

    private void txtporgasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtporgasMouseClicked
txtporgas.setEnabled(true);
txtporgas.selectAll();               // TODO add your handling code here:
    }//GEN-LAST:event_txtporgasMouseClicked

    private void txtporhonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtporhonMouseClicked
txtporhon.setEnabled(true);
txtporhon.selectAll();              // TODO add your handling code here:
    }//GEN-LAST:event_txtporhonMouseClicked

    private void txtmontoporgasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmontoporgasMouseClicked
txtmontoporgas.setEnabled(true);
txtmontoporgas.selectAll();    
txtmontoporgas.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_txtmontoporgasMouseClicked

    private void txtmonporhonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmonporhonMouseClicked
txtmonporhon.setEnabled(true);
txtmonporhon.selectAll();    
txtmonporhon.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_txtmonporhonMouseClicked

    private void txtdestinatarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdestinatarioMouseClicked
txtdestinatario.setEnabled(true);
txtdestinatario.selectAll();    
txtdestinatario.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtdestinatarioMouseClicked

    private void txtproyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtproyectoMouseClicked
txtproyecto.setEnabled(true);
txtproyecto.selectAll();    
txtproyecto.requestFocus();           // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoMouseClicked

    private void txtbuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscadorActionPerformed

    private void grillaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grillaKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            try {
                cursor.removeRow(grilla.getSelectedRow());
                recorrergrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

 if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            try {
                cursor.removeRow(grilla.getSelectedRow());
                recorrergrilla();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grillaKeyPressed

    private void grilla1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla1KeyPressed
if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            try {
                cursor1.removeRow(grilla1.getSelectedRow());
                recorrergrilla1();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

 if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            try {
                cursor1.removeRow(grilla1.getSelectedRow());
                recorrergrilla1();
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_grilla1KeyPressed

    private void lblcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblcodigoKeyTyped
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

char c = evt.getKeyChar();
        if (Character.isDigit(c) == false) {
        } else {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodigoKeyTyped

    private void lblcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblcodigoKeyPressed
if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {   
try {
            ver_conex  conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT pre_codigo,pre_fechor,pre_destin,pre_proyec FROM presupuesto WHERE pre_estado LIKE 'ACTIVO' ORDER BY PRE_codigo DESC");
            modelo3.setRowCount(0);
            Object [] fila = new Object[4];
            while (conn.resultado.next()){
                fila[0] = conn.resultado.getObject(1);
                fila[1] = conn.resultado.getObject(2);
                fila[2] = conn.resultado.getObject(3);
                fila[3] = conn.resultado.getObject(4);
                modelo3.addRow(fila);
            }
            conn.resultado.first();
            ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
            buscadorpresupuesto.setIconImage(icono.getImage());
            buscadorpresupuesto.setTitle("PRESUPUESTOS");
            buscadorpresupuesto.pack();
            buscadorpresupuesto.setLocationRelativeTo(null);
            buscadorpresupuesto.setModal(true);
            txtbuscador1.requestFocus();
            buscadorpresupuesto.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        }                   // TODO add your handling code here:
    }//GEN-LAST:event_lblcodigoKeyPressed

    private void txtbuscador1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_DOWN) {
            grilla3.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscador1KeyPressed

    private void txtbuscador1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyReleased
        wqqqq = txtbuscador1.getText();
        grilla2();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador1KeyReleased

    private void grilla3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla3MouseClicked
        try {
            String Dato = (String) modelo3.getValueAt(grilla3.getSelectedRow(),0).toString();
            lblcodigo.setText(Dato);
            lblcodigo.setEnabled(false);
            buscadorpresupuesto.setVisible(false);
            buscadorpresupuesto.setModal(false);
            cargadatos();
             if (operacion == 2){
 
                int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Anular Presupuesto N° --> "+lblcodigo.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                          if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                          {
                              borrar();
                          }
                          else // no quiere borrar
                          {
                              JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                          }
                          btncancelar.doClick();

                } else {
                txtdestinatario.setEnabled(true);
                txtdestinatario.selectAll();
                txtdestinatario.requestFocus();}
        } catch (SQLException ex) {
            Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_grilla3MouseClicked

    private void grilla3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla3KeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            try {
                String Dato = (String) modelo3.getValueAt(grilla3.getSelectedRow(),0).toString();
                lblcodigo.setText(Dato);
                lblcodigo.setEnabled(false);
                buscadorpresupuesto.setVisible(false);
                buscadorpresupuesto.setModal(false);
                cargadatos();
                if (operacion == 2){
 
                int mensaje = JOptionPane.showConfirmDialog(this,"Deseas Anular Presupuesto N° --> "+lblcodigo.getText(),"Confirmar",JOptionPane.YES_NO_OPTION);
                          if(mensaje == JOptionPane.YES_OPTION)//si quiere borrar
                          {
                              borrar();
                          }
                          else // no quiere borrar
                          {
                              JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Atención", JOptionPane.ERROR_MESSAGE);
                          }
                          btncancelar.doClick();

                } else {
                txtdestinatario.setEnabled(true);
                txtdestinatario.selectAll();
                txtdestinatario.requestFocus();}
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_grilla3KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField2.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField2.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField2.getText()))));
                jTextField1.setEnabled(true);
                jTextField2.setEnabled(false);
                jTextField1.requestFocus();
        }  
        }
    }//GEN-LAST:event_jTextField2KeyPressed

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

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField1.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField1.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField1.getText()))));
                jTextField3.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField2.getText())*Double.parseDouble(jTextField1.getText()))));
                jTextField3.setEnabled(true);
                jTextField1.setEnabled(false);
                jTextField3.requestFocus();
        }  
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField4.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField4.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField4.getText()))));
                jTextField5.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField3.getText())/Double.parseDouble(jTextField4.getText()))));
                jTextField5.setEnabled(true);
                jTextField4.setEnabled(false);
                jTextField5.requestFocus();
        }  
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField3.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField3.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField3.getText()))));
                jTextField4.setEnabled(true);
                jTextField3.setEnabled(false);
                jTextField4.requestFocus();
        }  
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField5.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField5.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField5.getText()))));
                jTextField6.setEnabled(true);
                jTextField5.setEnabled(false);
                jTextField6.requestFocus();
        }  
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField6.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                jTextField6.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField6.getText()))));
                jTextField7.setText(String.valueOf(getDecimal(3,(Double.parseDouble(jTextField6.getText())*Double.parseDouble(jTextField5.getText())))));
                jTextField7.setEnabled(true);
                jTextField6.setEnabled(false);
                jTextField7.requestFocus();
        }  
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyPressed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
          String campo = jTextField7.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
                try {
                    jTextField7.setText(String.valueOf(getDecimal(3,Double.parseDouble(jTextField7.getText()))));
                    jTextField7.setEnabled(false);
                    grilla.setValueAt(jTextField2.getText(), fila, 3);
                    grilla.setValueAt(jTextField1.getText(), fila, 5);
                    grilla.setValueAt(jTextField3.getText(), fila, 6);
                    grilla.setValueAt(jTextField4.getText(), fila, 7);
                    grilla.setValueAt(jTextField5.getText(), fila, 9);
                    grilla.setValueAt(jTextField6.getText(), fila, 11);
                    grilla.setValueAt(jTextField7.getText(), fila, 12);
                    limpiarcampos();
                    Editor.setVisible(false);
                    Editor.setModal(false);
                    recorrergrilla();
                    grilla.clearSelection();
                } catch (SQLException ex) {
                    Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
                }
        }  
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField2.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField3.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField4.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
            && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && jTextField5.getText().contains(".")) {
            evt.consume();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyTyped

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Editor;
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnborrar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btngrabar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JDialog buscador;
    private javax.swing.JDialog buscadorpresupuesto;
    private javax.swing.JTable grilla;
    private javax.swing.JTable grilla1;
    private javax.swing.JTable grilla2;
    private javax.swing.JTable grilla3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField lblcodigo;
    private javax.swing.JLabel lbldescri;
    private javax.swing.JLabel lbldescri1;
    private org.netbeans.examples.lib.timerbean.Timer timer2;
    private javax.swing.JTextField txt_item_cod;
    private javax.swing.JTextField txt_item_cod1;
    private javax.swing.JTextField txtbuscador;
    private javax.swing.JTextField txtbuscador1;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcantidad1;
    private javax.swing.JTextField txtdenominacion;
    private javax.swing.JTextField txtdestinatario;
    private javax.swing.JTextField txthora;
    private javax.swing.JTextField txtmonporhon;
    private javax.swing.JTextField txtmontoporgas;
    private javax.swing.JTextField txtporgas;
    private javax.swing.JTextField txtporhon;
    private javax.swing.JTextField txtpreci1;
    private javax.swing.JTextField txtproyecto;
    private javax.swing.JTextField txtsubtotalma;
    private javax.swing.JTextField txtsubtotalmo;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables

    public void Fecha(String fecha){
Calendar c = new GregorianCalendar();
String dia, mes, annio,hora,minuto,segundo;
dia = Integer.toString(c.get(Calendar.DATE));
int a = Integer.parseInt(dia);
if (a <= 9)dia = "0"+dia;
mes = Integer.toString(c.get(Calendar.MONTH)+1);
int b = Integer.parseInt(mes);
if (b <= 9)mes = "0"+mes;
annio = Integer.toString(c.get(Calendar.YEAR));
hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
minuto = Integer.toString(c.get(Calendar.MINUTE));
segundo = Integer.toString(c.get(Calendar.SECOND));
fecha = (annio + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo);
fechahora = fecha;
Fecha = (annio + "-" + mes + "-" + dia);
}
    int codigoarticulo,codigorubro,unidad1,unidad2,unidad3;
float cantidad,canticon,totamat,medidacomer,total,preciounitario,subtotal;
    
    private void agregar() throws SQLException {
        Fecha("Fecha");
        String SQL = "INSERT INTO presupuesto (pre_codigo,usu_codigo,pre_destin,pre_proyec,pre_fechor,pre_manobr,pre_materi,pre_total,pre_porhon,pre_honora,pre_porgas,pre_gasvar)"
                + " VALUES ("+lblcodigo.getText()+","+acceso.usuario+",'"+txtdestinatario.getText()+"','"+txtproyecto.getText()+"','"+fechahora+"',"+txtsubtotalmo.getText()+","+txtsubtotalma.getText()+","+txttotal.getText()+","+txtporhon.getText()+","+txtmonporhon.getText()+","+txtporgas.getText()+","+txtmontoporgas.getText()+")";   
        System.out.println(SQL);
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        try {
       javax.swing.table.TableModel model = grilla.getModel();

               int c = 0;

               for (int i = 0; i < model.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                       String codigo = modelo.getValueAt(i, 0).toString();
                        String rubro = modelo.getValueAt(i, 1).toString();
                        String cantida = modelo.getValueAt(i, 3).toString();
                        String unida1 = modelo.getValueAt(i, 4).toString();
                        String cantcon = modelo.getValueAt(i, 5).toString();
                        String totmat = modelo.getValueAt(i, 6).toString();
                        String medcom = modelo.getValueAt(i, 7).toString();
                        String unida2 = modelo.getValueAt(i, 8).toString();
                        String tot = modelo.getValueAt(i, 9).toString();
                        String unida3 = modelo.getValueAt(i, 10).toString();
                        String preuni = modelo.getValueAt(i, 11).toString();
                        String subtot = modelo.getValueAt(i, 12).toString();
                        codigoarticulo = Integer.parseInt(codigo);
                        cantidad = Float.parseFloat(cantida);
                        canticon = Float.parseFloat(cantcon);
                        totamat = Float.parseFloat(totmat);
                        total = Float.parseFloat(tot);
                        medidacomer = Float.parseFloat(medcom);
                        preciounitario = Float.parseFloat(preuni);
                        subtotal = Float.parseFloat(subtot);
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select rub_codigo from rubro where rub_descri like '" + rubro + "'");
                        conn.resultado.next();
                        codigorubro = conn.resultado.getInt("rub_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida1 + "'");
                        conn.resultado.next();
                        unidad1 = conn.resultado.getInt("uni_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida2 + "'");
                        conn.resultado.next();
                        unidad2 = conn.resultado.getInt("uni_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida3 + "'");
                        conn.resultado.next();
                        unidad3 = conn.resultado.getInt("uni_codigo");
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO presupuesto_has_items (ite_codigo,pre_codigo,uni_codig2,uni_codig1,uni_codigo,rub_codigo,pdt_cantid,pdt_cancon,pdt_totmat,pdt_medcom,pdi_total,pdi_preuni,pdi_subtot)"
                               + " VALUES ("+codigoarticulo+","+lblcodigo.getText()+","+unidad3+","+unidad2+","+unidad1+","+codigorubro+","+cantidad+","+canticon+","+totamat+","+medidacomer+","+total+","+preciounitario+","+subtotal+")";
                       stmta.executeUpdate(insercionSQL1);
                       c++;
                   }
               
               System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

               }catch (SQLException e) {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
               }
        
        try {
       javax.swing.table.TableModel model1 = grilla1.getModel();

               int c = 0;

               for (int i = 0; i < model1.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                       Object codigo    =grilla1.getValueAt(i,0);
                       Object cantidad    =grilla1.getValueAt(i,4);
                       Object subtotal    =grilla1.getValueAt(i,5);
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO presupuesto_detalle_mo (pre_codigo,mdo_codigo,pdm_cantid,pdm_subtot) VALUES  ("+lblcodigo.getText()+","+codigo+","+cantidad+","+subtotal+")";
                       stmta.executeUpdate(insercionSQL1);
                       c++;
                   }
               
               System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

               }catch (SQLException e) {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
               }
        
               
                }

    private void cargadatos() throws SQLException {
        ver_conex  conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT * FROM presupuesto WHERE pre_codigo = "+lblcodigo.getText());
        conn.resultado.next();
        txtdestinatario.setText(conn.resultado.getString("pre_destin"));
        txtproyecto.setText(conn.resultado.getString("pre_proyec"));
        txtsubtotalma.setText(conn.resultado.getString("pre_materi"));
        txtsubtotalmo.setText(conn.resultado.getString("pre_manobr"));
        txtporgas.setText(conn.resultado.getString("pre_porgas"));
        txtmontoporgas.setText(conn.resultado.getString("pre_gasvar"));
        txtporhon.setText(conn.resultado.getString("pre_porhon"));
        txtmonporhon.setText(conn.resultado.getString("pre_honora"));
        txttotal.setText(conn.resultado.getString("pre_total"));
        

        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT I.ite_codigo,rub_descri,ite_descri,pdt_cantid,U1.uni_descri descri1,pdt_cancon,pdt_totmat,pdt_medcom,U2.uni_descri descri2,pdi_total,U3.uni_descri descri3,pdi_preuni,pdi_subtot"
                + " FROM presupuesto_has_items I INNER JOIN `rubro` rubro ON rubro.rub_codigo = I.rub_codigo "
                + "INNER JOIN `items` items ON items.ite_codigo = I.ite_codigo "
                + "INNER JOIN unidades U1 ON I.uni_codigo = U1.uni_codigo INNER JOIN unidades U2 ON I.uni_codig1 = U2.uni_codigo "
                + "INNER JOIN unidades U3 ON I.uni_codig2 = U3.uni_codigo AND I.pre_codigo = "+lblcodigo.getText());
        System.out.println("SELECT I.ite_codigo,rub_descri,ite_descri,pdt_cantid,U1.uni_descri descri1,pdt_cancon,pdt_totmat,pdt_medcom,U2.uni_descri descri2,pdi_total,U3.uni_descri descri3,pdi_preuni,pdi_subtot"
                + " FROM presupuesto_has_items I INNER JOIN `rubro` rubro ON rubro.rub_codigo = I.rub_codigo "
                + "INNER JOIN `items` items ON items.ite_codigo = I.ite_codigo "
                + "INNER JOIN unidades U1 ON I.uni_codigo = U1.uni_codigo INNER JOIN unidades U2 ON I.uni_codig1 = U2.uni_codigo "
                + "INNER JOIN unidades U3 ON I.uni_codig2 = U3.uni_codigo AND I.pre_codigo = "+lblcodigo.getText());
         modelo.setRowCount(0);
            Object [] fila = new Object[13];
            while (conn.resultado.next()){
                fila[0] = conn.resultado.getObject(1);
                fila[1] = conn.resultado.getObject(2);
                fila[2] = conn.resultado.getObject(3);
                fila[3] = conn.resultado.getObject(4);
                fila[4] = conn.resultado.getObject(5);
                fila[5] = conn.resultado.getObject(6);
                fila[6] = conn.resultado.getObject(7);
                fila[7] = conn.resultado.getObject(8);
                fila[8] = conn.resultado.getObject(9);
                fila[9] = conn.resultado.getObject(10);
                fila[10] = conn.resultado.getObject(11);
                fila[11] = conn.resultado.getObject(12);
                fila[12] = conn.resultado.getObject(13);
                modelo.addRow(fila);
            }
            conn.resultado.first();
            conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT presupuesto_detalle_mo.mdo_codigo,mdo_descri,uni_descri,ROUND(pdm_subtot/pdm_cantid,2) AS cunitario,pdm_cantid,pdm_subtot FROM presupuesto_detalle_mo,mano_de_obra,unidades WHERE presupuesto_detalle_mo.mdo_codigo = mano_de_obra.mdo_codigo AND mano_de_obra.uni_codigo = unidades.uni_codigo AND presupuesto_detalle_mo.pre_codigo = "+lblcodigo.getText());
         modelo1.setRowCount(0);
            Object [] fila1 = new Object[6];
            while (conn.resultado.next()){
                fila1[0] = conn.resultado.getObject(1);
                fila1[1] = conn.resultado.getObject(2);
                fila1[2] = conn.resultado.getObject(3);
                fila1[3] = conn.resultado.getObject(4);
                fila1[4] = conn.resultado.getObject(5);
                fila1[5] = conn.resultado.getObject(6);
                modelo1.addRow(fila1);
            }
            conn.resultado.first();
    }

    private void modificar() throws SQLException {
        Fecha("Fecha");
        String SQL = "UPDATE presupuesto SET usu_codigo = "+acceso.usuario+", pre_destin = '"+txtdestinatario.getText()+"', pre_proyec = '"+txtproyecto.getText()+"', pre_fechor = '"+fechahora+"', pre_manobr = "+txtsubtotalmo.getText()+", pre_materi = "+txtsubtotalma.getText()+", pre_total = "+txttotal.getText()+", pre_porhon = "+txtporhon.getText()+", pre_honora = "+txtmonporhon.getText()+", pre_porgas = "+txtporgas.getText()+", pre_gasvar = "+txtmontoporgas.getText()+" WHERE pre_codigo = " + lblcodigo.getText();   
        System.out.println(SQL);
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        String SQL1 = "DELETE FROM presupuesto_has_items WHERE pre_codigo =  "+lblcodigo.getText();
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL1);
        String SQL2 = "DELETE FROM presupuesto_detalle_mo WHERE pre_codigo =  "+lblcodigo.getText();
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL2);
        try {
       javax.swing.table.TableModel model = grilla.getModel();

               int c = 0;

               for (int i = 0; i < model.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                        String codigo = modelo.getValueAt(i, 0).toString();
                        String rubro = modelo.getValueAt(i, 1).toString();
                        String cantida = modelo.getValueAt(i, 3).toString();
                        String unida1 = modelo.getValueAt(i, 4).toString();
                        String cantcon = modelo.getValueAt(i, 5).toString();
                        String totmat = modelo.getValueAt(i, 6).toString();
                        String medcom = modelo.getValueAt(i, 7).toString();
                        String unida2 = modelo.getValueAt(i, 8).toString();
                        String tot = modelo.getValueAt(i, 9).toString();
                        String unida3 = modelo.getValueAt(i, 10).toString();
                        String preuni = modelo.getValueAt(i, 11).toString();
                        String subtot = modelo.getValueAt(i, 12).toString();
                        codigoarticulo = Integer.parseInt(codigo);
                        cantidad = Float.parseFloat(cantida);
                        canticon = Float.parseFloat(cantcon);
                        totamat = Float.parseFloat(totmat);
                        total = Float.parseFloat(tot);
                        medidacomer = Float.parseFloat(medcom);
                        preciounitario = Float.parseFloat(preuni);
                        subtotal = Float.parseFloat(subtot);
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select rub_codigo from rubro where rub_descri like '" + rubro + "'");
                        conn.resultado.next();
                        codigorubro = conn.resultado.getInt("rub_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida1 + "'");
                        conn.resultado.next();
                        unidad1 = conn.resultado.getInt("uni_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida2 + "'");
                        conn.resultado.next();
                        unidad2 = conn.resultado.getInt("uni_codigo");
                        conn.sentencia = conn.conexion.createStatement();
                        conn.resultado = conn.sentencia.executeQuery("select uni_codigo from unidades where uni_descri like '" + unida3 + "'");
                        conn.resultado.next();
                        unidad3 = conn.resultado.getInt("uni_codigo");
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO presupuesto_has_items (ite_codigo,pre_codigo,uni_codig2,uni_codig1,uni_codigo,rub_codigo,pdt_cantid,pdt_cancon,pdt_totmat,pdt_medcom,pdi_total,pdi_preuni,pdi_subtot)"
                               + " VALUES ("+codigoarticulo+","+lblcodigo.getText()+","+unidad3+","+unidad2+","+unidad1+","+codigorubro+","+cantidad+","+canticon+","+totamat+","+medidacomer+","+total+","+preciounitario+","+subtotal+")";
                       stmta.executeUpdate(insercionSQL1);
                       c++;
                   }
               
               System.out.println("Hay " + String.valueOf(c) + " valores encendidos.");

               }catch (SQLException e) {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"Atención ", JOptionPane.INFORMATION_MESSAGE);
               }
        
        try {
       javax.swing.table.TableModel model1 = grilla1.getModel();

               int c = 0;

               for (int i = 0; i < model1.getRowCount(); i++) {
                       Statement stmta=null;
                       stmta=(Statement) conn.conexion.createStatement();
                       Object codigo    =grilla1.getValueAt(i,0);
                       Object cantidad    =grilla1.getValueAt(i,4);
                       Object subtotal    =grilla1.getValueAt(i,5);
                       conn.sentencia = conn.conexion.createStatement();
                       String insercionSQL1="INSERT INTO presupuesto_detalle_mo (pre_codigo,mdo_codigo,pdm_cantid,pdm_subtot) VALUES  ("+codigo+","+lblcodigo.getText()+","+cantidad+","+subtotal+")";
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
         String SQL = "UPDATE presupuesto SET pre_estado = 'ANULADO' WHERE pre_codigo = " + lblcodigo.getText();   
        System.out.println(SQL);
        ver_conex conn =new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
    }

    private void limpiarcampos() {
        jLabel24.setText("");
        jLabel22.setText("");
        jLabel23.setText("");
        jTextField2.setText("");
        jLabel21.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jLabel29.setText("");
        jTextField5.setText("");
        jLabel32.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
    }

    private void tirargrilla1() throws SQLException {
        ver_conex  conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT I.ite_codigo,I.ite_descri,I.ite_medcom,U1.uni_descri descri1,U2.uni_descri descri2,I.ite_costo FROM items I INNER JOIN unidades U1 ON I.uni_codigo = U1.uni_codigo INNER JOIN unidades U2 ON I.uni_precom = U2.uni_codigo and ite_codigo = " + txt_item_cod.getText());
                conn.resultado.next();
                Object[] datos = new Object[13];
                datos[0] = txt_item_cod.getText();
                datos[1] = txtdenominacion.getText();
                datos[2] = lbldescri.getText();
                datos[3] = "1";
                datos[4] = conn.resultado.getString("descri1");
                if (txtcantidad.getText().trim().isEmpty()){
                datos[5] = "1";    
                datos[6] = String.valueOf(getDecimal(3,(Double.parseDouble("1")*1)));
                } else {
                datos[5] = txtcantidad.getText(); 
                datos[6] = String.valueOf(getDecimal(3,(Double.parseDouble(txtcantidad.getText())*1)));
                }
                datos[7] = String.valueOf(getDecimal(2,(Double.parseDouble(conn.resultado.getString("ite_medcom")))));
                datos[8] = conn.resultado.getString("descri1");
                if (txtcantidad.getText().trim().isEmpty()){ 
                datos[9] = String.valueOf(getDecimal(2,((Double.parseDouble("1")*1)/Double.parseDouble(conn.resultado.getString("ite_medcom")))));
                } else {
                datos[9] = String.valueOf(getDecimal(2,((Double.parseDouble(txtcantidad.getText())*1)/Double.parseDouble(conn.resultado.getString("ite_medcom")))));
                }
                datos[10] = conn.resultado.getString("descri2");
                datos[11] = conn.resultado.getString("ite_costo");
                if (txtcantidad.getText().trim().isEmpty()){ 
                datos[12] = String.valueOf(getDecimal(2,(((Double.parseDouble("1")*1)/Double.parseDouble(conn.resultado.getString("ite_medcom")))*Double.parseDouble(conn.resultado.getString("ite_costo")))));
                } else {
                datos[12] = String.valueOf(getDecimal(2,(((Double.parseDouble(txtcantidad.getText())*1)/Double.parseDouble(conn.resultado.getString("ite_medcom")))*Double.parseDouble(conn.resultado.getString("ite_costo")))));
                }
                int aux = 0;
                int cantfilas = grilla.getRowCount();
                int x;
                citem = txtdenominacion.getText();
                for (x = 0; x < cantfilas; x++) {
                item  = (String) grilla.getValueAt(x, 1);//codigo
                if (item.equals(citem)){
                aux = x;
                }          
    }
                modelo.insertRow(aux+1, datos);
                btngrabar.setEnabled(true);
                recorrergrilla ();
    }
    
    private void imprimir2() throws SQLException, JRException {
        try {

            String sentencia = "SELECT CONCAT(pre_proyec,' - ',pre_destin),pre_fechor,pre_manobr,pre_materi,pre_total,pre_gasvar,pre_honora,mdo_descri,uni_descri,pdm_cantid,pdm_subtot FROM presupuesto_detalle_mo,mano_de_obra,unidades,presupuesto WHERE presupuesto.pre_codigo = presupuesto_detalle_mo.pre_codigo AND presupuesto_detalle_mo.mdo_codigo = mano_de_obra.mdo_codigo AND mano_de_obra.uni_codigo = unidades.uni_codigo AND presupuesto.pre_codigo = " + lblcodigo.getText();
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
        URL urlMaestro = getClass().getClassLoader().getResource("repo/presu2.jasper");
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
    
    private void imprimir1() throws SQLException, JRException {
            int presupuesto = Integer.parseInt(lblcodigo.getText());
            /////////////////////////////////instanciamos
            ver_conex conn =new ver_conex();
            HashMap parameters = new HashMap();
            ///////////////////////////////// preparamos parametros del reporte
            parameters.put("presupuesto",presupuesto);
            parameters.put("SUBREPORT_DIR", "\\ambiente\\src\\repo\\");
            ///////////////////////////////// patch de los reporetes
            URL urlMaestro = getClass().getClassLoader().getResource("repo/presu1.jasper");
            ///////////////////////////////// Cargamos el reporte
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
            JasperPrint masterPrint = null;
            //////////////////////////////// paso de parametros y patch
            masterPrint = JasperFillManager.fillReport(masterReport, parameters,conn.conexion);
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(false);
            JasperPrintManager.printReport(masterPrint, false);

}
}



 

