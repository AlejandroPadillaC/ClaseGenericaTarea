package clasegenerica;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

// Clase de conexion 
public class FirebaseSaveObject {
    
    public static FirebaseSaveObject conexionglobal;
    private FirebaseDatabase firebaseDatabase;
    
    static {
        try {
            conexionglobal = new FirebaseSaveObject();
        } catch (IOException e) {
            System.err.println("Error al inicializar FirebaseSaveObject: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    // constructor que ejecuta el metodo de conexion
    public FirebaseSaveObject() throws FileNotFoundException {
        initFirebase();
    }
    
// Metodo que conecta con la base de datos 
    public void initFree() throws FileNotFoundException{
        initFirebase();
    }
    
    private void initFirebase() throws FileNotFoundException {
        try {
            
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://bdatos1-e3869-default-rtdb.firebaseio.com")
                    
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\Alejandro Padilla\\Documents\\NetBeansProjects\\ZOOFirebase\\ProyectoZooParcial\\src\\bdatos1-e3869-firebase-adminsdk-7mv22-ab55be186c.json")))

                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa....");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
         
    }

    /**
     * Save item object in Firebase.
     * @param <T>
     * @param <U>
     * @param map
     * @throws java.io.FileNotFoundException
     */
    
    public <T, U> void saveFree(HashMap<Pair<T,U>, Pair<T,U>> map)throws FileNotFoundException{
        save(map);
    }
    
    public <T,U> void deleteFree(Pair <T,U> info, Pair <T,U> eps) throws FileNotFoundException{
        delete(info, eps);
    }
    public <T, U> void save(HashMap<Pair<T, U>, Pair<T, U>> map) {
        if (map != null && !map.isEmpty()) {

            // Obtener la referencia raíz de la base de datos
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");

            for (Map.Entry<Pair<T, U>, Pair<T, U>> entry : map.entrySet()) {
                Pair<T, U> key = entry.getKey();
                Pair<T, U> value = entry.getValue();

                // Usar la clave como referencia en la base de datos
                DatabaseReference childReference = databaseReference.child(key.toString());

                CountDownLatch countDownLatch = new CountDownLatch(1);

                // Guardar el valor en la referencia correspondiente
                childReference.setValue(value, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError de, DatabaseReference dr) {
                        countDownLatch.countDown();
                    }
                });

                try {
                    // Esperar a que la operación se complete antes de continuar
                    countDownLatch.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private <T,U> void delete(Pair <T,U> info, Pair<T,U> eps) throws FileNotFoundException{
        if (info != null) {
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            DatabaseReference childReference = databaseReference.child(info.toString());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //En esta linea se utiliza un metodo de la libreria de firebase para eliminar o poner un valor. 
            childReference.removeValue(new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
