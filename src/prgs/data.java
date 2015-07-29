//elder joel monzon alcaraz
//3ºaño informatica
//utic
//turno mañaña

package prgs;
////////////
import java.text.SimpleDateFormat;
import java.util.Date;
//////////////

public class data {
 public String mes, dia, ano ,dia_semana, hora;
 SimpleDateFormat horaformato=new SimpleDateFormat("HH:mm:ss");

 //metodo para leer la hora
 public void le_hora()
 {
   Date horaActual=new Date();
   hora=horaformato.format(horaActual);

 }
    public void le_data() {
         {
        Date data = new Date();
        //mes==+fecha.getMonth(); // 0 a 11
        dia=""+data.getDate();
        ano=""+(1900+data.getYear());
        //dia=semana=""+fecha.getDay;


        switch (data.getDay())
       {
            case 0: dia_semana="Domingo";break;
            case 1: dia_semana="Lunes";break;
            case 2: dia_semana="Martes";break;
            case 3: dia_semana="Miercoles";break;
            case 4: dia_semana="Jueves";break;
            case 5: dia_semana="Viernes";break;
            case 6: dia_semana="Sabado";break;

        }

        switch (data.getMonth())
        {
            case 0: mes="Ene";break;
            case 1: mes="Feb";break;
            case 2: mes="Mar";break;
            case 3: mes="Abr";break;
            case 4: mes="May";break;
            case 5: mes="Jun";break;
            case 6: mes="Jul";break;
            case 7: mes="Ago";break;
            case 8: mes="Sep";break;
            case 9: mes="Oct";break;
            case 10: mes="Nov";break;
            case 11: mes="Dic";break;


        }

    }
    }


}