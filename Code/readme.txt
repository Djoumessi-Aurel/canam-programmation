Le code du projet se trouve dans le dossier drawer.

Les prérequis nécessaires pour compiler le code sont:
- Avoir Java installé (version 1.8 minimum)

- Avoir Maven installé (NB: les IDE tels que IntelliJ et Eclipse incluent déjà maven)


Pour compiler le code:
- Ouvrez le dossier drawer dans votre IDE

- Exécutez la commande: mvn clean install
	Cela va télécharger et installer toutes les bibliothèques nécessaires au fonctionnement du projet.
	NB: Dans l'IDE on n'a pas besoin d'écrire la commande manuellement; on peut la lancer en un clic sur un bouton.

- Lancer la compilation: c'est la classe MainWindow (dans le package gui) qui contient la méthode main().


Base de données:
Dans le dossier drawer, on a:
- le fichier drawshapes.db qui est la base de données que l'application va utiliser lorsqu'on va la compiler.

- le fichier dessins.sql qui contient le script qui permet de recréer la structure de la base de données.


NB: Pour gérer les bases de données SQLite (par exemple ouvrir un fichier .db pour voir son contenu), vous pouvez vous servir d'un logiciel tel que "DB Browser for SQLite".
