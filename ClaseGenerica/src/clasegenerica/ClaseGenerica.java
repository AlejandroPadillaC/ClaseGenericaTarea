
package clasegenerica;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import clasegenerica.FirebaseSaveObject;
import java.io.FileNotFoundException;
/**
 *
 * @author Alejandro Padilla
 */
public class ClaseGenerica {


    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner scanner = new Scanner(System.in);
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
        
        System.out.println("Introduce tu documento de identidad: ");
        int doc = scanner.nextInt();
        scanner.nextLine();
        
        HashMap<Pair<String, Integer>,Pair<String, Integer>> HashPerson = new HashMap<>();
        Pair<String,Integer> info = new Pair<String,Integer>(nombre,edad);
        Pair<String,Integer> eps = new Pair<String,Integer>(Eps,doc);
        HashPerson.put(info, eps);
        FirebaseSaveObject.conexionglobal.saveFree(HashPerson);
        
        for (Map.Entry<Pair<String, Integer>, Pair<String, Integer>> entry : HashPerson.entrySet()) {
            Pair<String, Integer> key = entry.getKey();
            Pair<String, Integer> value = entry.getValue();
            System.out.println( key + "-" + value);
        } 
    }
}
