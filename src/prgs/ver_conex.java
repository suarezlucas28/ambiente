package prgs;


import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ver_conex
{
    public Connection conexion = null;
    public static String bd = "ambiente";
    public static String login = "root";
    public static String password = "";
    public final String CONTROLADOR_JDBC = "com.mysql.jdbc.Driver";
    public static String URL_BASEDEDATOS = "jdbc:mysql://localhost/";
 
     public Statement sentencia;
     public ResultSet resultado;
    ////
   
    public ver_conex() // constructor
    {
        try  // todo bien
        {
            Class.forName("com.mysql.jdbc.Driver");//Se registra el Driver jdbc de MySQL
            conexion = DriverManager.getConnection(URL_BASEDEDATOS+bd,login,password);
           
        }//fin todo bien
         catch (Exception e) // error de conexion
        {   
            JOptionPane.showMessageDialog(null, "Hubo un problema con la conexion" , "Reintente",JOptionPane.INFORMATION_MESSAGE);
             //System.exit(0);
        }
    }//fin del constructor

    public Connection getConnection(){
      return conexion;
   }
 }




