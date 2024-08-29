
package clasegenerica;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import clasegenerica.FirebaseSaveObject;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Alejandro Padilla
 */
public class ClaseGenerica {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
        dateFormat.setLenient(false); // Para evitar fechas inválidas
        
        // Leer una línea de texto
        System.out.println("Introduce tu nombre:");
        String nombre = scanner.nextLine();
        
        // Leer un número entero
        System.out.println("Introduce tu edad:");
        int edad = scanner.nextInt();
        scanner.nextLine();

        // Leer un número decimal (double)
        System.out.println("Introduce tu EPS:");
        String Eps = scanner.nextLine();
        
        System.out.println("Introduce tu fecha de nacimiento de este modo (yyyy-MM-dd : ");
        String fecha = scanner.nextLine();
        Date date = dateFormat.parse(fecha);

        HashMap<Pair<String, Integer>,Pair<String, Date>> HashPerson = new HashMap<>();
        Pair<String,Integer> info = new Pair<String,Integer>(nombre,edad);
        Pair<String,Date> eps = new Pair<String,Date>(Eps,date);
        HashPerson.put(info,eps);;
        FirebaseSaveObject.conexionglobal.saveFree(HashPerson);
        System.out.println("Claves mapa: "+HashPerson.keySet());
        
        
        for (Map.Entry<Pair<String, Integer>, Pair<String, Date>> entry : HashPerson.entrySet()) {
            Pair<String, Integer> key = entry.getKey();
            Pair<String, Date> value = entry.getValue();
            System.out.println( key + "-" + value);
        }
    }
            
}
