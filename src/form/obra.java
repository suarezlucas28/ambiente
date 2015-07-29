/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.Statement;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prgs.data;
import prgs.ver_conex;

public class obra extends javax.swing.JPanel {

    data mostra_data;
    String wqqqq, wqqqq1, fechahora, Fecha, cliente;
    int dupli, permiso, guarani, firma, despachante;
    float peso, dolaro, dolarb;
    private int ab;
    static JDialog jgenerador;
    java.sql.Statement snt;
    ResultSet recur1;
    String citem, item, codigo, descri, servicio;
    javax.swing.table.DefaultTableModel cursor;
    DefaultTableModel modelo = new DefaultTableModel() {
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
    DefaultTableModel modelo1 = new DefaultTableModel() {
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

    public obra() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        mostra_data = new data();
        mostra_data.le_data();
        timer2.start();
        hab_btn();
        cabecerabuscador();
        CabDetalle();
        cabecerabuscador1();
    }

    private void CabDetalle() {
        modelo2.addColumn("CÓDIGO");
        modelo2.addColumn("DESTINATARIO");
        modelo2.addColumn("OPERACIÓN");
        modelo2.addColumn("FECHA");
        grilla3.getColumnModel().getColumn(0).setPreferredWidth(21);
        grilla3.getColumnModel().getColumn(1).setPreferredWidth(71);
        grilla3.getColumnModel().getColumn(2).setPreferredWidth(191);
        grilla3.getColumnModel().getColumn(3).setPreferredWidth(31);
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

    public void grilla1() {
        String ac;
        modelo1.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT cli_codigo,cli_nomape FROM cliente where cli_nomape LIKE '%" + wqqqq + "%'");
            System.out.println(recur1);
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("cli_codigo");
                    ac = recur1.getString("cli_nomape");

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

    public void grilla2() {
        String ac, ad, ae;
        modelo3.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT obr_codigo,obr_fecha,cli_nomape,obr_proyec FROM obras,cliente WHERE obras.cli_codigo = cliente.cli_codigo AND obr_estado LIKE 'ACTIVO' and cli_nomape LIKE '%" + wqqqq + "%' ORDER BY obr_codigo DESC");
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("obr_codigo");
                    ac = recur1.getString("obr_fecha");
                    ad = recur1.getString("cli_nomape");
                    ae = recur1.getString("obr_proyec");
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

    public void grilla3() {
        String ac, ad, ae;
        modelo2.setRowCount(0);

        try {
            ver_conex conn2 = new ver_conex();//instanciamos
            snt = conn2.conexion.createStatement();
            recur1 = snt.executeQuery("SELECT pre_codigo,pre_destin,CONCAT(pre_proyec,' - $ ',pre_total) AS operacion,LEFT(pre_fechor,10) FROM presupuesto WHERE CONCAT(pre_proyec,' - $ ',pre_total) LIKE '%" + wqqqq + "%' AND pre_estado LIKE 'ACTIVO' ORDER BY pre_codigo DESC");
            if (recur1.wasNull()) {
                ab = 0;
                return;
            }

            if (recur1.next()) {
                do {
                    ab = recur1.getInt("pre_codigo");
                    ac = recur1.getString("pre_destin");
                    ad = recur1.getString("operacion");
                    ae = recur1.getString("LEFT(pre_fechor,10)");
                    modelo2.addRow(new Object[]{ab, ac, ad, ae});
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
        modelo1.addColumn("CODIGO");
        modelo1.addColumn("CLIENTE");
        grilla1.getColumnModel().getColumn(0).setPreferredWidth(31);
        grilla1.getColumnModel().getColumn(1).setPreferredWidth(191);
    }

    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timer2 = new org.netbeans.examples.lib.timerbean.Timer();
        buscadorclientes = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        grilla1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        ok_cliente = new javax.swing.JButton();
        agregar_cliente = new javax.swing.JButton();
        txtbuscador = new javax.swing.JTextField();
        buscadorpresupuestos = new javax.swing.JDialog();
        jLabel14 = new javax.swing.JLabel();
        txtbuscador1 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        grilla3 = new javax.swing.JTable();
        buscadorobra = new javax.swing.JDialog();
        jLabel19 = new javax.swing.JLabel();
        txtbuscador2 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        grilla4 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnregistrar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtfechahora = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtobserva = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        cbo_presupuesto = new javax.swing.JComboBox();
        txtpresupuesto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtproyecto = new javax.swing.JTextField();
        txt_item_cod = new javax.swing.JTextField();
        lbldescri = new javax.swing.JLabel();

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
        jScrollPane3.setViewportView(grilla1);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Buscador:");

        ok_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag/new file.png"))); // NOI18N
        ok_cliente.setToolTipText("AGREGAR NUEVO CLIENTE");
        ok_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_clienteActionPerformed(evt);
            }
        });

        agregar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag/refresh1.png"))); // NOI18N
        agregar_cliente.setToolTipText("ACTUALIZAR");
        agregar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_clienteActionPerformed(evt);
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

        javax.swing.GroupLayout buscadorclientesLayout = new javax.swing.GroupLayout(buscadorclientes.getContentPane());
        buscadorclientes.getContentPane().setLayout(buscadorclientesLayout);
        buscadorclientesLayout.setHorizontalGroup(
            buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorclientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(buscadorclientesLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ok_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agregar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        buscadorclientesLayout.setVerticalGroup(
            buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorclientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtbuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(buscadorclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ok_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(agregar_cliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Buscador:");

        txtbuscador1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador1KeyReleased(evt);
            }
        });

        grilla3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        grilla3.setModel(modelo2);
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

        javax.swing.GroupLayout buscadorpresupuestosLayout = new javax.swing.GroupLayout(buscadorpresupuestos.getContentPane());
        buscadorpresupuestos.getContentPane().setLayout(buscadorpresupuestosLayout);
        buscadorpresupuestosLayout.setHorizontalGroup(
            buscadorpresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorpresupuestosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorpresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addGroup(buscadorpresupuestosLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador1)))
                .addContainerGap())
        );
        buscadorpresupuestosLayout.setVerticalGroup(
            buscadorpresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscadorpresupuestosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buscadorpresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtbuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout buscadorobraLayout = new javax.swing.GroupLayout(buscadorobra.getContentPane());
        buscadorobra.getContentPane().setLayout(buscadorobraLayout);
        buscadorobraLayout.setHorizontalGroup(
            buscadorobraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscadorobraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscadorobraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(buscadorobraLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscador2)))
                .addContainerGap())
        );
        buscadorobraLayout.setVerticalGroup(
            buscadorobraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscadorobraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buscadorobraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtbuscador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setMaximumSize(new java.awt.Dimension(590, 265));
        setMinimumSize(new java.awt.Dimension(590, 265));
        setPreferredSize(new java.awt.Dimension(590, 265));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Observación:");

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
                .addGap(69, 69, 69)
                .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnregistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Codigo:");

        txtcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcodigo.setEnabled(false);
        txtcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Cliente:");

        txtfechahora.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfechahora.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtfechahora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfechahora.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Fecha y Hora:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Proyecto:");

        txtobserva.setColumns(20);
        txtobserva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtobserva.setRows(5);
        txtobserva.setText(" ");
        txtobserva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtobserva.setEnabled(false);
        txtobserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtobservaMouseClicked(evt);
            }
        });
        txtobserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtobservaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtobserva);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Presupuesto:");

        cbo_presupuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        cbo_presupuesto.setEnabled(false);
        cbo_presupuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_presupuestoMouseClicked(evt);
            }
        });
        cbo_presupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbo_presupuestoKeyPressed(evt);
            }
        });

        txtpresupuesto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtpresupuesto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtpresupuesto.setEnabled(false);
        txtpresupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpresupuestoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpresupuestoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpresupuestoKeyTyped(evt);
            }
        });

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(156, 156, 156)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addComponent(cbo_presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtpresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_item_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lbldescri, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbo_presupuesto)
                    .addComponent(txtpresupuesto)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        gencod();
        txt_item_cod.setEnabled(true);
        txt_item_cod.requestFocus();
        des_btn();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnnuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnnuevo.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnnuevoKeyPressed

    private void btnregistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarActionPerformed
        try {
            grabar();
        } catch (SQLException ex) {
            Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnregistrarActionPerformed

    private void btnregistrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnregistrarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            this.btnregistrar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnregistrarKeyPressed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        cbo_presupuesto.setEnabled(false);
        txtproyecto.setEnabled(false);
        txtcodigo.setEnabled(false);
        txtfechahora.setEnabled(false);
        txt_item_cod.setEnabled(false);
        txtobserva.setEnabled(false);
        txtcodigo.setText("");
        txt_item_cod.setText("");
        lbldescri.setText("");
        lbldescri.setEnabled(false);
        txtpresupuesto.setText("");
        txtproyecto.setText("");
        jLabel15.setText("");
        txtobserva.setText("");
        hab_btn();
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

    private void timer2OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer2OnTime
        mostra_data.le_hora();
        txtfechahora.setText(mostra_data.ano + "/" + mostra_data.mes + "/" + mostra_data.dia + " " + mostra_data.hora);
    }//GEN-LAST:event_timer2OnTime

    private void grilla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla1MouseClicked
        codigo = modelo1.getValueAt(grilla1.getSelectedRow(), 0).toString();
        descri = modelo1.getValueAt(grilla1.getSelectedRow(), 1).toString();
        txt_item_cod.setText(codigo);
        lbldescri.setText(descri);
        buscadorclientes.setVisible(false);
        buscadorclientes.setModal(false);
        txtbuscador.setText("");
        txtproyecto.setEnabled(true);
        txtproyecto.requestFocus();
    }//GEN-LAST:event_grilla1MouseClicked

    private void grilla1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla1KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            codigo = modelo1.getValueAt(grilla1.getSelectedRow(), 0).toString();
            descri = modelo1.getValueAt(grilla1.getSelectedRow(), 1).toString();
            txt_item_cod.setText(codigo);
            lbldescri.setText(descri);
            buscadorclientes.setVisible(false);
            buscadorclientes.setModal(false);
            txtbuscador.setText("");
            txtproyecto.setEnabled(true);
            txtproyecto.requestFocus();
        }
    }//GEN-LAST:event_grilla1KeyPressed

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked
    }//GEN-LAST:event_jScrollPane3MouseClicked

    private void txtbuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscadorKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            grilla1.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscadorKeyPressed

    private void txtbuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscadorKeyReleased
        wqqqq = txtbuscador.getText();
        grilla1();
    }//GEN-LAST:event_txtbuscadorKeyReleased

    private void txtobservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtobservaMouseClicked
        txtobserva.setEnabled(true);
        txtobserva.requestFocus();
        jLabel15.setText("F12 - Terminar Obs.");
    }//GEN-LAST:event_txtobservaMouseClicked

    private void txtobservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobservaKeyPressed
        if (evt.getKeyCode() == 123) {
            btnregistrar.setEnabled(true);
            btnregistrar.requestFocus();
            txtobserva.setEnabled(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtobservaKeyPressed

    private void ok_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_clienteActionPerformed
        try {
            ver_conex conn = new ver_conex();//instanciamos
            try {
                conn.sentencia = conn.conexion.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn.resultado = conn.sentencia.executeQuery("SELECT pan_codigo,usu_codigo,per_permis FROM permisos WHERE pan_codigo = 21 and usu_codigo = " + acceso.usuario);
            try {
                conn.resultado.next();
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                permiso = conn.resultado.getInt("per_permis");
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (permiso == 1) // todo bien
            {
                try {
                    jgenerador = new JDialog();
                    jgenerador.getContentPane().add(new clientes());
                    jgenerador.setTitle("CLIENTES");
                    ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                    jgenerador.setIconImage(icono.getImage());
                    jgenerador.pack();
                    jgenerador.setLocationRelativeTo(null);
                    jgenerador.setModal(true);
                    jgenerador.setResizable(false);
                    clientes.btnagregar.requestFocus();
                    jgenerador.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else // error
            {
                JOptionPane.showMessageDialog(null, "No tiene Permisos para acceder a este formulario", "Verifique",
                        JOptionPane.INFORMATION_MESSAGE);// salir del sistema
            }
        } catch (SQLException ex) {
            Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ok_clienteActionPerformed

    private void agregar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_clienteActionPerformed
        buscadorclientes.setVisible(false);
        buscadorclientes.setModal(false);

        try {
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT cli_codigo,cli_nomape FROM cliente ORDER BY cli_nomape");
            modelo1.setRowCount(0);
            Object[] fila = new Object[2];
            while (conn.resultado.next()) {
                fila[0] = conn.resultado.getObject(1);
                fila[1] = conn.resultado.getObject(2);
                modelo1.addRow(fila);
            }
            conn.resultado.first();
            ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
            buscadorclientes.setIconImage(icono.getImage());
            buscadorclientes.setTitle("CLIENTES");
            buscadorclientes.pack();
            buscadorclientes.setLocationRelativeTo(null);
            buscadorclientes.setModal(true);
            txtbuscador.requestFocus();
            buscadorclientes.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_agregar_clienteActionPerformed

    private void cbo_presupuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_presupuestoMouseClicked
        cbo_presupuesto.setEnabled(true);
        cbo_presupuesto.requestFocus();
    }//GEN-LAST:event_cbo_presupuestoMouseClicked

    private void cbo_presupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbo_presupuestoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (cbo_presupuesto.getSelectedItem().toString().equals("NO")) {
                txtpresupuesto.setText("SIN PRESUPUESTO");
                cbo_presupuesto.setEnabled(false);
                btnregistrar.setEnabled(true);
                btnregistrar.requestFocus();
            } else {
                txtpresupuesto.setText("");
                txtpresupuesto.setEnabled(true);
                cbo_presupuesto.setEnabled(false);
                txtpresupuesto.requestFocus();
            }
        }
    }//GEN-LAST:event_cbo_presupuestoKeyPressed

    private void txtpresupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpresupuestoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT pre_codigo,pre_destin,CONCAT(pre_proyec,' - $ ',pre_total) AS operacion,LEFT(pre_fechor,10) FROM presupuesto WHERE pre_estado LIKE 'ACTIVO' ORDER BY pre_codigo DESC");
                modelo2.setRowCount(0);
                Object[] fila = new Object[4];
                while (conn.resultado.next()) {
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    fila[2] = conn.resultado.getObject(3);
                    fila[3] = conn.resultado.getObject(4);
                    modelo2.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscadorpresupuestos.setIconImage(icono.getImage());
                buscadorpresupuestos.setTitle("PRESUPUESTOS");
                buscadorpresupuestos.pack();
                buscadorpresupuestos.setLocationRelativeTo(null);
                buscadorpresupuestos.setModal(true);
                txtbuscador1.requestFocus();
                buscadorpresupuestos.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }              // TODO add your handling code here:
    }//GEN-LAST:event_txtpresupuestoKeyPressed

    private void txtbuscador1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            grilla3.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_DOWN);
            {
                //este codigo lo que hace es convertir el enter en tab
            }

        }
    }//GEN-LAST:event_txtbuscador1KeyPressed

    private void txtbuscador1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador1KeyReleased
        wqqqq = txtbuscador1.getText();
        grilla3();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador1KeyReleased

    private void grilla3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla3MouseClicked
        String Dato = (String) modelo2.getValueAt(grilla3.getSelectedRow(), 0).toString();
        txtpresupuesto.setText(Dato);
        txtpresupuesto.requestFocus();
        buscadorpresupuestos.setVisible(false);
        buscadorpresupuestos.setModal(false);
        btnregistrar.setEnabled(true);
        btnregistrar.requestFocus();
    }//GEN-LAST:event_grilla3MouseClicked

    private void grilla3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grilla3KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String Dato = (String) modelo2.getValueAt(grilla3.getSelectedRow(), 0).toString();
            txtpresupuesto.setText(Dato);
            txtpresupuesto.requestFocus();
            buscadorpresupuestos.setVisible(false);
            buscadorpresupuestos.setModal(false);
            btnregistrar.setEnabled(true);
            btnregistrar.requestFocus();
        }
    }//GEN-LAST:event_grilla3KeyPressed

    private void txtpresupuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpresupuestoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpresupuestoKeyReleased

    private void txtpresupuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpresupuestoKeyTyped
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
    }//GEN-LAST:event_txtpresupuestoKeyTyped

    private void txtproyectoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyPressed

    private void txtproyectoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyReleased
        txtproyecto.setText(txtproyecto.getText().toUpperCase());        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyReleased

    private void txtproyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproyectoKeyTyped
        if (txtproyecto.getText().length() == 100) {
            txtproyecto.setText(txtproyecto.getText().toUpperCase());
            txtproyecto.setEnabled(false);
            cbo_presupuesto.setEnabled(true);
            cbo_presupuesto.requestFocus();
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (txtproyecto.getText().length() == 100) {
                txtproyecto.setEnabled(true);
            }
        }           // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoKeyTyped

    private void txt_item_codMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_item_codMouseClicked
        txt_item_cod.setEnabled(true);
        txt_item_cod.setText("");
        txt_item_cod.requestFocus();
    }//GEN-LAST:event_txt_item_codMouseClicked

    private void txt_item_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_item_codActionPerformed
        String campo = txt_item_cod.getText();
        if (campo.isEmpty()) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT cli_codigo,cli_nomape FROM cliente ORDER BY cli_nomape");
                modelo1.setRowCount(0);
                Object[] fila = new Object[2];
                while (conn.resultado.next()) {
                    fila[0] = conn.resultado.getObject(1);
                    fila[1] = conn.resultado.getObject(2);
                    modelo1.addRow(fila);
                }
                conn.resultado.first();
                ImageIcon icono = new ImageIcon("\\ambiente\\src\\imag\\software.png");
                buscadorclientes.setIconImage(icono.getImage());
                buscadorclientes.setTitle("CLIENTES");
                buscadorclientes.pack();
                buscadorclientes.setLocationRelativeTo(null);
                buscadorclientes.setModal(true);
                txtbuscador.requestFocus();
                buscadorclientes.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(obra.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se encuentra Material con codigo -->" + txt_item_cod.getText(), "Atención", JOptionPane.ERROR_MESSAGE);
            txt_item_cod.setText("");
            return;
        }
    }//GEN-LAST:event_txt_item_codActionPerformed

    private void txt_item_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_codKeyPressed
    }//GEN-LAST:event_txt_item_codKeyPressed

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

    private void txtproyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproyectoActionPerformed
        String campo = txtproyecto.getText();
        if (campo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo está vacío, Cargue los datos correctamente");
            return;
        } else {
            cbo_presupuesto.setEnabled(true);
            txtproyecto.setEnabled(false);
            cbo_presupuesto.requestFocus();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoActionPerformed

    private void txtproyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtproyectoMouseClicked
        txtproyecto.setEnabled(true);
        txtproyecto.selectAll();
        txtproyecto.requestFocus();          // TODO add your handling code here:
    }//GEN-LAST:event_txtproyectoMouseClicked

    private void txtcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                ver_conex conn = new ver_conex();
                conn.sentencia = conn.conexion.createStatement();
                conn.resultado = conn.sentencia.executeQuery("SELECT obr_codigo,obr_fecha,cli_nomape,obr_proyec FROM obras,cliente WHERE obras.cli_codigo = cliente.cli_codigo AND obr_estado LIKE 'ACTIVO' ORDER BY obr_codigo DESC");
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
                buscadorobra.setIconImage(icono.getImage());
                buscadorobra.setTitle("OBRRAS");
                buscadorobra.pack();
                buscadorobra.setLocationRelativeTo(null);
                buscadorobra.setModal(true);
                txtbuscador1.requestFocus();
                buscadorobra.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }             // TODO add your handling code here:
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
        grilla2();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador2KeyReleased

    private void grilla4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grilla4MouseClicked
        try {
            String Dato = (String) modelo3.getValueAt(grilla4.getSelectedRow(), 0).toString();
            txtcodigo.setText(Dato);
            txtcodigo.setEnabled(false);
            buscadorobra.setVisible(false);
            buscadorobra.setModal(false);
            cargadatos();

            int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Obra N° --> " + txtcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
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
                buscadorobra.setVisible(false);
                buscadorobra.setModal(false);
                cargadatos();
                int mensaje = JOptionPane.showConfirmDialog(this, "Deseas Anular Presupuesto N° --> " + txtcodigo.getText(), "Confirmar", JOptionPane.YES_NO_OPTION);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar_cliente;
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btncancelar;
    public static javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnregistrar;
    private javax.swing.JDialog buscadorclientes;
    private javax.swing.JDialog buscadorobra;
    private javax.swing.JDialog buscadorpresupuestos;
    private javax.swing.JComboBox cbo_presupuesto;
    private javax.swing.JTable grilla1;
    private javax.swing.JTable grilla3;
    private javax.swing.JTable grilla4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbldescri;
    private javax.swing.JButton ok_cliente;
    private org.netbeans.examples.lib.timerbean.Timer timer2;
    private javax.swing.JTextField txt_item_cod;
    private javax.swing.JTextField txtbuscador;
    private javax.swing.JTextField txtbuscador1;
    private javax.swing.JTextField txtbuscador2;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtfechahora;
    private javax.swing.JTextArea txtobserva;
    private javax.swing.JTextField txtpresupuesto;
    private javax.swing.JTextField txtproyecto;
    // End of variables declaration//GEN-END:variables

    private void cabecerabuscador1() {
        modelo3.addColumn("CÓDIGO");
        modelo3.addColumn("FECHA Y HORA");
        modelo3.addColumn("CLIENTE");
        modelo3.addColumn("PROYECTO");
        grilla4.getColumnModel().getColumn(0).setPreferredWidth(21);
        grilla4.getColumnModel().getColumn(1).setPreferredWidth(71);
        grilla4.getColumnModel().getColumn(2).setPreferredWidth(151);
        grilla4.getColumnModel().getColumn(3).setPreferredWidth(191);
    }

    private void gencod() {
        try {
            ver_conex conn = new ver_conex();
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT IFNULL(MAX(obr_codigo),0)+1 AS xxx FROM obras");
            conn.resultado.next();
            txtcodigo.setText(conn.resultado.getString("xxx"));

        } catch (SQLException exceptionSql) {
            JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void grabar() throws SQLException {
        String SQL;
        gencod();
        Fecha("fecha");
        SQL = "INSERT INTO obras (obr_codigo,cli_codigo,usu_codigo,obr_fecha,obr_proyec,obr_presup,obr_obser) "
                + "VALUES (" + txtcodigo.getText() + "," + txt_item_cod.getText() + "," + acceso.usuario + ",'" + fechahora + "','" + txtproyecto.getText() + "','" + txtpresupuesto.getText() + "','" + txtobserva.getText() + "')";
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        btncancelar.doClick();
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

    private void cargadatos() throws SQLException {
        ver_conex conn = new ver_conex();
        conn.sentencia = conn.conexion.createStatement();
        conn.resultado = conn.sentencia.executeQuery("SELECT obr_codigo,cliente.cli_codigo,cli_nomape,obr_fecha,obr_proyec,obr_presup,obr_obser FROM obras,cliente WHERE obras.obr_codigo = cliente.cli_codigo and obras.obr_codigo = " + txtcodigo.getText());
        conn.resultado.next();
        txtcodigo.setText(conn.resultado.getString("obr_codigo"));
        txt_item_cod.setText(conn.resultado.getString("cli_codigo"));
        lbldescri.setText(conn.resultado.getString("cli_nomape"));
        txtproyecto.setText(conn.resultado.getString("obr_proyec"));
        txtpresupuesto.setText(conn.resultado.getString("obr_presup"));
        txtobserva.setText(conn.resultado.getString("obr_obser"));
        if (txtpresupuesto.getText().equals("SIN PRESUPUESTO")) {
            cbo_presupuesto.setSelectedItem("NO");
        } else {
            cbo_presupuesto.setSelectedItem("SI");
        }
    }

    private void borrar() throws SQLException {
        String SQL;
        SQL = "UPDATE obras SET obr_estado = 'ANULADO' WHERE obr_codigo = " + txtcodigo.getText();
        ver_conex conn = new ver_conex();//instanciamos
        conn.sentencia = conn.conexion.createStatement();
        conn.sentencia.executeUpdate(SQL);//OJO LE PASO LA SENTENCIA
        btncancelar.doClick();
    }
}
