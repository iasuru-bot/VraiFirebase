package angers.albert.VraiFirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class activity_accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void retour(View v){
        Intent i = new Intent(activity_accueil.this, MainActivity.class);
        startActivity(i);
    }

    public void deconnexion(View v){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(activity_accueil.this, "Deconnexion réussie", Toast.LENGTH_LONG).show();
        this.retour(v);
    }
}