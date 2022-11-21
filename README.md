# VraiFirebase
Welcome in VraiFirebase

## Project description:
Ce projet est un projet android qui utilise Firebase. C'est un projet scolaire permettant de découvrir et d'utiliser Firebase afin de créer une application native permettant de se connecter. On peut retrouver les connexions dans la console de Firebase et dans la realtime Database.

## Table of Contents:
### How to install and run it
### How to use it
#### MainActivity
#### activity_accueil
#### Les classes externes
### Include Credits

## How to install and run it:
Pour installer le projet, il suffit de cloner ce dépot et d'installer Android Studio. Une fois Android studio installé, il faut ouvrir le dépôt dans le logiciel. Puis il suffit de le run par le biais de votre téléphone android ou par le téléphone virtuel d'android studio.

## How to use it
Ce projet est constitué de 2 classes principales: activity_accueil.java et MainActivity.java. 

### MainActivity
MainActivity va représenter le fonctionnement de la page initiale.
Elle contient deux boutons: **connexionMail, connexionGithub**. Lorsqu'un des boutons est utilisé la fonction correspondante est lancé: **startSignInMailActivity()** et **startSignInGithubActivity()**

#### startSignInMailActivity()
La fonction commence par le choix du type d'authentification : **mail**. Puis lance l'activité de connexion/création par mail fourni par Firebase, en particulier **AuthUI**.
A la suite de cette activité, la fonction **onActivityResult()** se lance. Elle permet de récupérer des donnes de l'activité précédente. 
Elle lance ensuite **handleResponseAfterSignIn()**. Cette dernière fonction va vérifier l'état de retour de l'activité. En fonction du résultat, il va diriger vers la bonne solution: 
- retour vers la page initiale en cas d'erreur
- envoie vers la page d'accueil en cas de réussite

#### startSignInGithubActivity()
La fonction commence par le choix du type d'authentification : **Github**. Puis lance l'activité de connexion/création par github fourni par Firebase et Github. (Des configurations ont été faites dans Firebase afin de rediriger vers Github). Ensuite en fontion du succès ou de l'echec grâce à **addOnSuccessListener et addOnFailureListener**, une redirection est faite:
- retour vers la page initiale en cas d'erreur
- envoie vers la page d'accueil en cas de réussite

### activity_accueil
activity_accueil va représenter le fonctionnement de la page d'accueil post-connexion.
Elle contient:
  - String email
  - String userId
  - String key
  - String value
  - Boolean newUser
  - FirebaseDatabase database -> représentation de la bdd realtime database
  - DatabaseReference myRefWrite -> référence précise de la bdd realtime database
  - DatabaseReference myRefRead -> référence précise de la bdd realtime database


 Dans le **OnCreate()** de cette activité, l'email et l'userId sont récupéré grâce à **FireBaseUser**. Puis grace à une référence à la database, on va récupérer le noeud "user" de la bdd puis le modifier afin de récupérer l'ensemble des clés/valeurs afin de vérifier la présence de la combinaison **key=UID et value=userID**. Si elle n'existe pas, on inscrit dans la bdd la nouvelle combinaison.


### Les classes externes
Ce projet dépend de classes externes: 
- com.firebase.ui.auth.AuthUI, 
- com.firebase.ui.auth.IdpResponse, 
- com.google.firebase.auth.OAuthProvider,
- com.google.firebase.auth.FirebaseUser,
- com.google.firebase.database.DataSnapshot,
- com.google.firebase.database.DatabaseReference,
- com.google.firebase.database.FirebaseDatabase

#### AuthUI
Classe permettant l'utilisation simplifiée de Firebase. ELle définit aussi quel provider est utilisé. 

#### IdpResponse
Classe récupère les résultats de l'authentification

#### OAuthProvider
Represents the login authentication provider for a generic OAuth2 provider. 

#### FirebaseUser
Classe permettant de récuperre les données de la session actuel de l'utilisateur.

#### DataSnapshot
Classe qui contient les datas de la Firebase Database.

#### DatabaseReference
Une référence à la bdd permettant de lire ou d'écrire de données à un emplacement précis.

#### FirebaseDatabase
Initialise une connexion avec la database 

## Include Credits:
- Matteo PEREIRA
- Axel ALBERT
