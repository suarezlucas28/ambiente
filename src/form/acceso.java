package form;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import prgs.MotherBoardUtils;
import prgs.ver_conex;

public class acceso extends javax.swing.JFrame {


   int cargo;
   static int usuario;
   String Contra,Contra2;
   public String cpuId;
   private static acceso acceso;
   FileReader lector = null;
   String cpuid;

public acceso() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, FileNotFoundException, IOException {
    initComponents();
    cpuId = MotherBoardUtils.getMotherboardSN();
    cpuId = encriptaEnMD5(cpuId);
    try {
    //Creamos un archivo FileReader que obtiene lo que tenga el archivo
    lector=new FileReader("c:/ambiente/KeySystem.txt");
    }catch(Exception e)
    {
    JOptionPane.showMessageDialog(this,"Este sistema no posee llave de software, contacte al desarrollador del sistema","Atención",JOptionPane.ERROR_MESSAGE); 
    System.exit(0);
    }
    //El contenido de lector se guarda en un BufferedReader
    BufferedReader contenido=new BufferedReader(lector);
    //Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
    while((cpuid=contenido.readLine())!=null)
    /*{
    System.out.println(cpuid + " hi ");
    System.out.println(cpuId + " ho " );
    }*/
    
    if (cpuid.trim().equals(cpuId.trim())){
    
    } else {
    JOptionPane.showMessageDialog(this,"La llave del software es incorrecta, contacte al desarrollador del sistema","Atención",JOptionPane.ERROR_MESSAGE); 
    System.exit(0);
    }
    des_todo();
    String icon = "\\ambiente\\src\\imag\\software.png";
    ImageIcon icono = new ImageIcon(icon);
    this.setIconImage(icono.getImage());
    txtUsuario.setEnabled(true);
    txtUsuario.requestFocus();
}

 private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

    public static String encriptaEnMD5(String stringAEncriptar) {
    try
        {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++)
           {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
   }
 
      
/*public static acceso getInstance() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException//Implementacion Singleton
{
        try {
            if (acceso == null) acceso = new acceso(); return acceso;
        } catch (IOException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        }

}*/

private void des_todo(){
    this.txtUsuario.setEnabled(false);
    this.JPContraseña.setEnabled(false);
    this.btnAcepta.setEnabled(false);
}

public void BuscarDatos() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, SQLException, SQLException, IOException, InterruptedException//throws IllegalAccessException,ClassNotFoundException,InstantiationException
{
    String log = txtUsuario.getText(); //lee lo q esta en el cuadro de texto
    ver_conex conn =new ver_conex();//instanciamos
    conn.sentencia = conn.conexion.createStatement();
    conn.resultado = conn.sentencia.executeQuery("SELECT UPPER(usu_usuari),usu_contra,usu_estado FROM usuarios WHERE usu_usuari = '"+log+"' and usu_estado != 'BORRADO'");
    boolean encontro = conn.resultado.next();
    if(encontro == true){
        Contra2=conn.resultado.getString("usu_contra");
        System.out.println(" de la base "+Contra2);
        String estado = conn.resultado.getString("usu_estado");
            if (estado.equals("BLOQUEADO")){
                JOptionPane.showMessageDialog(null, "El Usuario se encuentra BLOQUEADO, consulte al administrador del sistema","Atención",JOptionPane.ERROR_MESSAGE);
                System.exit(0);                
            } else if (estado.equals("ACTIVO")){
                if(Contra.trim().equals(Contra2.trim()))//correcto
                {
                    recuper();
                    new menu().setVisible(true);
                    this.setVisible(false);
                }else//mal
                {
                    JOptionPane.showMessageDialog(
                    null,"CONTRASEÑA INCORRECTA");
                    contador = contador  + 1;
                   if(contador==3){
                        recuper();
                        bloqueado();
                        JOptionPane.showMessageDialog(
                        null,"USUARIO BLOQUEADO");
                        bloqueado();
                        System.exit(0);
                    }
                    else // menos de 3 intentos
                    {
                        Limpiar();
                        btnAcepta.setEnabled(false);
                        txtUsuario.setEnabled(true);
                        txtUsuario.requestFocus();
                     }
                   btnAcepta.setEnabled(false);
                   txtUsuario.setEnabled(true);
                   txtUsuario.requestFocus();
                }
            }
    }else {
        JOptionPane.showMessageDialog(null, "No se encontro el Usuario " + txtUsuario.getText().toUpperCase(),"Verifique",JOptionPane.ERROR_MESSAGE);    
        Limpiar();
        btnAcepta.setEnabled(false);
        txtUsuario.setEnabled(true);
        txtUsuario.requestFocus();
    }
}
       
private void bloqueado() throws SQLException{
try {
 ver_conex  conn = new ver_conex();
 conn.sentencia = conn.conexion.createStatement();
 conn.sentencia.executeUpdate("UPDATE `usuarios` SET `usu_estado` = 'BLOQUEADO' WHERE `usu_codigo` = "+usuario);//OJO LE PASO LA SENTENCIA
} catch (SQLException exceptionSql)
{
 JOptionPane.showMessageDialog(null, exceptionSql.getMessage(), "Error de Conexion con la Base de Datos", JOptionPane.ERROR_MESSAGE);
}
}      
    

public void Limpiar()
{
    txtUsuario.setText("");
    JPContraseña.setText("");
}
private void recumd5() throws SQLException{
            String SQL = "SELECT md5('"+ new String(JPContraseña.getPassword()) +"') as pass";
            ver_conex conn =new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery(SQL);
            conn.resultado.next();
            Contra =conn.resultado.getString("pass");
            System.out.print(SQL);System.out.print("del formu "+Contra);
}


public void recuper() throws SQLException{
            ver_conex conn =new ver_conex();//instanciamos
            conn.sentencia = conn.conexion.createStatement();
            conn.resultado = conn.sentencia.executeQuery("SELECT usu_codigo FROM usuarios WHERE usu_usuari = '"+ txtUsuario.getText()+"'");
            conn.resultado.next();
            usuario = conn.resultado.getInt("usu_codigo");
            System.out.println(usuario);
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPContraseña = new javax.swing.JPasswordField();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnAcepta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ACCESO AL SISTEMA");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        JPContraseña.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        JPContraseña.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JPContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JPContraseñaActionPerformed(evt);
            }
        });
        JPContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                JPContraseñaFocusLost(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelar.setText("SALIR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("CONTRASEÑA:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag/llave.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("USUARIO:");

        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtUsuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });

        btnAcepta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAcepta.setText("ACEPTAR");
        btnAcepta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptaActionPerformed(evt);
            }
        });
        btnAcepta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptaKeyPressed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(txtUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(JPContraseña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(layout.createSequentialGroup()
                        .add(btnAcepta, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(btnCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(txtUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel9))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(JPContraseña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(btnAcepta, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btnCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-402)/2, (screenSize.height-187)/2, 402, 187);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
     System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void JPContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JPContraseñaActionPerformed
String campo=JPContraseña.getText();
if (campo.isEmpty()) {
    JOptionPane.showMessageDialog(this,"El campo está vacío, Cargue los datos correctamente");
    return;
} else {
    String campo1 = txtUsuario.getText().toUpperCase();
    txtUsuario.setText(campo1);
    btnAcepta.setEnabled(true);
    JPContraseña.setEnabled(false);
    btnAcepta.requestFocus();
}
    }//GEN-LAST:event_JPContraseñaActionPerformed

    private void JPContraseñaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JPContraseñaFocusLost
        
    }//GEN-LAST:event_JPContraseñaFocusLost

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
String campo=txtUsuario.getText();
if (campo.isEmpty()) {
 JOptionPane.showMessageDialog(this,"El campo está vacío, Cargue los datos correctamente");
 return;
} else {
JPContraseña.setEnabled(true);
JPContraseña.requestFocus();
txtUsuario.setEnabled(false);
}
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER){
btnCancelar.doClick();
} 
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnAceptaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptaActionPerformed
        try {
            recumd5();
            BuscarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (IOException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnAceptaActionPerformed

    private void btnAceptaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptaKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
        {
            this.btnAcepta.doClick();
        }
    }//GEN-LAST:event_btnAceptaKeyPressed

    //@SuppressWarnings("static-access")
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    try {
                        new acceso().setVisible(true);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null, ex);
                    }
             
                    Logger.getLogger(acceso.class.getName()).log(Level.SEVERE, null);
                }
            
        });
    }
int contador =0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField JPContraseña;
    private javax.swing.JButton btnAcepta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
