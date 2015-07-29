/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prgs;

import form.acceso;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author LUke
 */
public class MotherBoardUtils  {


private MotherBoardUtils() { }

public static String getMotherboardSN() {
String result = "";
try {
File file = File.createTempFile("realhowto",".vbs");
file.deleteOnExit();
FileWriter fw = new java.io.FileWriter(file);

String vbs =
"Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
+ "Set colItems = objWMIService.ExecQuery _ \n"
+ " (\"Select * from Win32_BaseBoard\") \n"
+ "For Each objItem in colItems \n"
+ " Wscript.Echo objItem.SerialNumber \n"
+ " exit for ' do the first cpu only! \n"
+ "Next \n";

fw.write(vbs);
fw.close();
Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
BufferedReader input =
new BufferedReader
(new InputStreamReader(p.getInputStream()));
String line;
while ((line = input.readLine()) != null) {
result += line;
}
input.close();
}
catch(Exception e){
e.printStackTrace();
}
return result.trim();
}


public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, IOException, UnsupportedLookAndFeelException{
    /*try {
        UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
        /*UpperEssentialLookAndFeel uelaf=new UpperEssentialLookAndFeel();
        UpperTheme ut=new UpperTheme();
        ut.setPrimary1(Color.red);
        ut.setPrimary2(Color.blue);
        ut.setPrimary3(Color.orange);
        ut.setSecondary1(Color.green);
        ut.setSecondary2(Color.LIGHT_GRAY);
        ut.setSecondary3(Color.cyan);
        ut.setBlack(Color.magenta);
        ut.setWhite(Color.black);
        uelaf.setCurrentTheme(ut);
        UIManager.setLookAndFeel(uelaf);
    } catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}*/
new acceso().setVisible(true);
}
}
