package angers.albert.VraiFirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    Button connexionMail, connexionGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connexionMail = findViewById(R.id.connexionMail);

        //lors du clique sur connexion Mail
        connexionMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInMailActivity();
            }
        });

        connexionGithub = findViewById(R.id.connexionGithub);

        //lors du clique sur connexion Github
        connexionGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInGithubActivity();
            }
        });
    }

    //Connexion ou création de compte par mail
    private void startSignInMailActivity() {

        // Choix du type d'authentification
        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

        // Lancement de l'activité de Firebase permettant de créer/connecter un compte
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    //Récupère les résultats de l'authentification
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    //Vérifier l'état de l'authentification et rediriger vers la page d'accueil ou du menu
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, R.string.success_authentication, Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, activity_accueil.class);
                startActivity(i);
            } else {
                // ERRORS
                if (response == null) {
                    Toast.makeText(MainActivity.this, R.string.error_authentication_canceled, Toast.LENGTH_LONG).show();
                } else if (response.getError() != null) {
                    Toast.makeText(MainActivity.this, R.string.error_authentication, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //Connexion par Github
    private void startSignInGithubActivity() {
        // On désigne GitHub comme fournisseur d'authentification
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
        //on nous ouvre une nouvelle page pour s'authentifier en suivant le fournisseur d'authentification
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //Portion de code permettant de savoir si tu as déjà un compte sauvegardé sur le télephone
        /* Task<AuthResult> pendResultTask = firebaseAuth.getPendingAuthResult();
        if (pendResultTask != null) {
            pendResultTask.addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(MainActivity.this, R.string.success_authentication, Toast.LENGTH_LONG).show();
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, R.string.error_authentication, Toast.LENGTH_LONG).show();

                        }
                    }
            );
        } else {

        }*/

        //Lancement de l'activité de firebase github
        firebaseAuth.startActivityForSignInWithProvider(MainActivity.this, provider.build())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, R.string.success_authentication, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, activity_accueil.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, R.string.error_authentication_canceled, Toast.LENGTH_LONG).show();
                            }
                        }
                );


    }

    public void quitter(View v) {
        finish();
    }
    public void deconnexion(View v) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Deconnexion réussie", Toast.LENGTH_LONG).show();
    }
}

