package angers.albert.VraiFirebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class activity_accueil extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://vraifirebase-cb5a9-default-rtdb.europe-west1.firebasedatabase.app");
    private final DatabaseReference myRefWrite = database.getReference();
    private final DatabaseReference myRefRead = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String userId = user.getUid();

        myRefRead.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    
                    boolean newUser = true;
                    //Avoir une liste de json correspondant à chaque utilisateur
                    //[{UID:test,email:test},{UID:test2,email:test2}]
                    ArrayList<JSONObject> listJson = new ArrayList<>();
                    try {
                        for (DataSnapshot child : task.getResult().getChildren()) {
                            listJson.add(new JSONObject(String.valueOf(child)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //On fait le tour de la liste et pour chaque JSON on regarde les différentes clés
                    // listeKey est [UID,email]
                    for (JSONObject jsonObject : listJson) {
                        Iterator<?> keys = jsonObject.keys();

                        //Avec les clés on fait le tour du json pour voir si la combinaison key=UID et value=userID existe
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            String value = null;
                            try {
                                value = jsonObject.getString(key);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Si la combinaison existe alors le user existe déjà
                            if (value != null && key.equals("UID") && value.equals(userId)) {
                                newUser = false;
                            }
                        }
                    }

                    if (newUser) {
                        myRefWrite.child("users").child(userId).child("UID").setValue(userId);
                        myRefWrite.child("users").child(userId).child("email").setValue(email);
                    }

                }
            }
        });
    }

    public void retour(View v) {
        finish();
    }

    public void deconnexion(View v) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(activity_accueil.this, "Deconnexion réussie", Toast.LENGTH_LONG).show();
        this.retour(v);
    }
}
