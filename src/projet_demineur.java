// Rémi Cazoulat - IE Gr1 A CUPGE ESIR
// Théo Le Goc - IE Gr1 A CUPGE ESIR

// Pour utiliser des scanners pour lire des entrées depuis le clavier
// utilisés en questions 4.d] pour la fonction jeu()
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;

// L'unique classe de votre projet
public class projet_demineur {

	// Donné, utile pour la question 1.b]
	public static int entierAleatoire(int a, int b) {
		// Renvoie un entier aléatoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b - 1);
	}

	//
	// Exercice 1 : Initialisation des tableaux
	//

	// Question 1.a]
	static int[][] T; // Tableau indiquant les cases révélées
	static int[][] Tadj; // Tableau contenant la position des bombes

	// Question 1.b] Fonction init
	static void init(int hauteur, int largeur, int n) {

		Tadj = new int[hauteur][largeur]; // initialisation de Tadj
		T = new int[hauteur][largeur]; // initialisation de T

		for (int i = 0; i < n; i++) { // va placer des bombes aléatoirement dans Tadj
			int bombeL = entierAleatoire(0, largeur + 1);
			int bombeH = entierAleatoire(0, hauteur + 1);
			if (Tadj[bombeH][bombeL] != -1)
				Tadj[bombeH][bombeL] = -1; // Si pas de bombe, en pose une
			else
				n++; // Sinon, augmente n (sinon on se retrouve avec 1 bombe en moins)
		}
		for (int j = 0; j < hauteur; j++) { // Initialise toutes les cases non égales à -1 à 0 (place des espaces vides là ou il n'y a pas de bombes)
			for (int k = 0; k < largeur; k++) {
				if (Tadj[j][k] != -1)
					Tadj[j][k] = 0;
			}
		}
	}

	static void afficheTadj(int hauteur, int largeur) { // fonction qui va afficher Tabj (DEBUG)
		for (int j = 0; j < hauteur; j++) { // Parcours toutes les cases
			for (int k = 0; k < largeur; k++) {

				if (Tadj[j][k] != -1)
					System.out.print(" " + Tadj[j][k] + " ");
				else
					System.out.print(Tadj[j][k] + " ");
			}
			System.out.println();
		}
	}

	// Question 1.c] Fonction caseCorrecte
	static boolean caseCorrecte(int i, int j) {
		return i < Tadj.length && j < Tadj[0].length && i >= 0 && j >= 0; // vérifie que la case existe
	}

	// Question 1.d] Fonction calculerAdjacent
	static void calculerAdjacent() {
		for (int i = 0; i < Tadj.length; i++) { // Parcours le tableau
			for (int j = 0; j < Tadj[0].length; j++) {
				if (Tadj[i][j] != -1) { // Vérifie que ce n'est pas une bombe
					for (int k = i - 1; k <= i + 1; k++) { // Si c'en est pas une, parcours les cases adjacentes
						for (int z = j - 1; z <= j + 1; z++) {
							if (caseCorrecte(k, z) && Tadj[k][z] == -1)
								Tadj[i][j]++; // Si la case est correcte, ajoute 1 à la case en question (car à côté d'une bombe)
						}
					}
				}
			}
		}
	}

	//
	// Exercice 2 : Affichage de la grille
	//

	// Question 2.a]
	static void afficherGrille(boolean affMines) {

		// Affichage ligne 0 (LETTRES)
		int lettres = 65;
		System.out.print("  |");
		for (int i = 0; i < T[0].length; i++) {
			if (i != 25 && i < 53) {
				System.out.print((char) lettres + "|");
				lettres++;
			} else if (i == 25) {
				System.out.print((char) lettres + "|");
				lettres = lettres + 7;
			}
		}

		System.out.println();
		// Affichage lignes
		int unite = 0;
		int dizaine = 0;

		for (int i = 0; i < T.length; i++) { // Pour chaque ligne

			if (unite > 9) { // Update l'index de la ligne
				unite = 0;
				dizaine++;
			}

			System.out.print(dizaine + "" + unite + "|"); // Affiche l'index de la ligne
			unite++;

			// affiche le reste
			for (int j = 0; j < T[i].length; j++) { // Parcours les colonnes

				if (!affMines) { // Si les bombes ne doivent pas être révélées
					if (T[i][j] == 0)
						System.out.print(" |"); // Si la case n'est pas révélée, affiche rien
					else if (T[i][j] == 1) { // Sinon

						if (Tadj[i][j] == -1)
							System.out.print("!|"); // Si la case est une bombe, l'affiche

						else
							System.out.print(Tadj[i][j] + "|"); // Sinon affiche le contenu (Le nombre de bombes adjacentes)
					} else
						System.out.print("X|"); // Sinon la case est marqué et affiche un X
				} else { // Si les bombes doivent être révélées.
					if (Tadj[i][j] == -1)
						System.out.print("!|"); // Si la case est une bombe l'affiche
					else
						System.out.print(Tadj[i][j] + "|"); // Sinon affiche son contenu (bombes adjacentes)
				}
			}
			System.out.println(); // Retourne à la ligne
		}
	}

	//
	// Exercice 3 : Révéler une case
	//

	// Question 3.a]
	static boolean caseAdjacenteZero(int i, int j) {
		// Parcours les cases adjacentes
		for (int k = i - 1; k <= i + 1; k++) {
			for (int z = j - 1; z <= j + 1; z++) {
				// Si la case est correcte, égale à zéro, revelée, et n'est pas celle du milieu,
				// on retourne true.
				if (caseCorrecte(k, z) && Tadj[k][z] == 0 && T[k][z] == 1 && (k != i || z != j)) {
					return true;
				}
			}
		}
		return false;
	}

	// Question 3.b]
	static void revelation(int i, int j) {
		T[i][j] = 1; // Avant tout il faut réveler la case, en mettant t[i][j] à 1
		boolean b = true;
		if (Tadj[i][j] == 0) {
			while (b) {
				int c = 0; // cette entier va servir a determiner si le parcours de toute la grille aura révélé au moins une nouvelle case.
				for (int n = 0; n < T.length; n++) { // on parcours la grille
					for (int k = 0; k < T[0].length; k++) {
						if (caseAdjacenteZero(n, k) && T[n][k] == 0) { // si une case n'est pas révélé et qu'elle possède une case adjacente
																	   // qui n'a aucunes mines adjacentes,
							T[n][k] = 1;// alors on revele la case
							c++; // pour ne pas que la boucle while se stoppe
						}
					}
				}
				if (c == 0)
					b = false; // condition pour stopper la boucle while
			}
		}
	}

	// Question 3.c] Optionnel NON FONCTIONNELLE
	static void revelation2(int i, int j) {
		T[i][j] = 1;
		if (Tadj[i][j] == 0) {
			for (int k = i - 1; k <= i + 1; k++) {
				for (int z = j - 1; z <= j + 1; z++) {
					if (caseCorrecte(k, z)) {
						T[k][z] = 1;
						if (Tadj[k][z] == 0 && (k != i || z != j))
							revelation2(k, z);
					}
				}
			}
		}

	}

	// Question 3.d]
	static void actionDrapeau(int i, int j) {
		if (T[i][j] != 1) { // Si la case n'est pas révélée
			T[i][j] = T[i][j] == 2 ? 0 : 2; // Marque la case, sinon enlève le drapeau
		}
	}

	// Question 3.e]
	static boolean revelerCase(int i, int j) {
		if (Tadj[i][j] == -1)
			return false; // Si la case est une bombe, ne révèle pas la case
		else {
			revelation(i, j); // Révèle la case et les cases adjacentes aux cases révélés et qui n'ont pas de bombes adjacentes
			return true; // La case a été révélée
		}
	}

	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	static boolean aGagne() {
		// Pour toutes les cases
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				if (Tadj[i][j] != -1 && T[i][j] != 1)
					return false; // Si une des cases à révéler ne l'est pas, le joueur n'a pas gagné
			}
		}
		return true; // Toutes les cases sont révélées, le joueur à gagné
	}

	// Question 4.b]
	static boolean verifierFormat(String s) {

		if (s.compareTo("aide") == 0)
			return true; // Si la commande est aide, retourne ok

		else {
			if (s.length() != 4)
				return false; // Si la longueur n'est pas bonne (4)
			else {
				// Si la premiere case n'est pas le nom de la fonction (r ou d)
				if (s.charAt(0) != 'd' && s.charAt(0) != 'r')
					return false;
				// Vérifie que les 2 charactères suivant sont des chiffres
				else if (!(s.charAt(1) <= '9' && s.charAt(1) >= '1'))
					return false;
				else if (!(s.charAt(2) <= '9' && s.charAt(2) >= '1'))
					return false;
				// Vérifie que le dernier charactère est une lettre valide
				else if (!(s.charAt(2) <= 'Z' && s.charAt(2) >= 'A' && s.charAt(2) <= 'z' && s.charAt(2) >= 'a'))
					return false;
			}
		}

		return true;
	}

	// Question 4.c]
	static int[] conversionCoordonnees(String input) {
		int[] t = new int[3];// créé un nouveau tableau qui contiendra l'action, et les coordonnés d'une case
		t[0] = Integer.parseInt(input.substring(1, 3));// on récupère les coordonnées de la ligne ( qui sont toujours deux nombres, d'ou cette ligne)
		int lettre = (int) input.charAt(3); // on transforme le 4eme caractère de la saisie du joueur en un entier
		// puis on le convertit en coordonnées valides ( en enlevant 65 si c'est une lettre maj ou 71 si c'est une lettre min)


		if (lettre > 'Z')
			t[1] = lettre - 71;
		else
			t[1] = lettre - 65;
			
		// on remplit la 3eme case avec l'action à faire, c'est à dire soit mettre un drapeau soit révéler la case.
		if (input.charAt(0) == 'd')
			t[2] = 0;
		else
			t[2] = 1;
		return t;
	}

	// Question 4.d]
	static void jeu() {

		// Initialise le scanner
		Scanner sc = new Scanner(System.in);

		// Variable maintenant la boucle
		boolean pasPerdu = true;

		while (pasPerdu) {

			// Si le joueur à gagné, arrête la boucle
			if (aGagne()) {
				pasPerdu = false;
				break;
			}

			// Affiche la grile (sans les bombes)
			afficherGrille(false);

			System.out.print("entrez votre action et/ou vos coordonnées : ");
			String coord = sc.nextLine(); // Scanne l'action

			// Tant que les coordonnées ne sont pas correctes
			while (!verifierFormat(coord)) {
				System.out.print("ré entrez votre action et/ou vos coordonnées, (elles sont fausses): ");
				coord = sc.nextLine();
			}

			// Si l'action est "aide"
			if (coord.compareTo("aide") == 0) {
				aide(); // Demande de l'aide
			} else { // Sinon
				int[] coord2 = conversionCoordonnees(coord); // Convertis les coordonnées
				if (coord2[2] == 0) { // Action drapeau
					actionDrapeau(coord2[0], coord2[1]);
				} else { // Action réveler (vérifie si on révèle pas une mauvaise case)
					pasPerdu = revelerCase(coord2[0], coord2[1]);
				}
			}
		}

		// Quand la boucle est fini (perdu ou gagné)
		// On vérifie si le joueur à gagné ou non
		if (aGagne()) {
			afficherGrille(false);
			System.out.print("vous avez gagné");
		} else {
			afficherGrille(true);
			System.out.print("vous avez perdu");
		}

		// Ferme le scanner
		sc.close();
	}

	// Question 4.e]
	// Votre *unique* méthode main
	public static void main(String[] args) {
		// Initialise le scanner qui servira à la création de la partie
		Scanner scanner = new Scanner(System.in);



		System.out.print("entrez une largeur : ");
		int largeur = 0;

		// Essaye de récupérer le nombre (on utilise ici next line afin d'éviter un bug de "selfread" du à nextInt)
		try {
			largeur = Integer.parseInt(scanner.nextLine()); // Si on arrive pas à parser, ce n'est pas un nombre. largeur est donc égal à 0
		} catch (Exception e) {}

		// Même chose que précedemment, en boucle tant que la valeur n'est pas correctement définie
		while (largeur > 52 || largeur < 1) {
			System.out.println("ré-entrez une largeur, votre valeur n'est pas correcte ( entre 1 et 52 inclus). : ");
			try {
				largeur = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {}
		}



		System.out.print("entrez une hauteur : ");
		int hauteur = 0;
		try {
			hauteur = Integer.parseInt(scanner.nextLine()); // Si on arrive pas à parser, ce n'est pas un nombre. largeur est donc égal à 0
		} catch (Exception e) {}

		// Même chose que précedemment, en boucle tant que la valeur n'est pas correctement définie
		while (hauteur > 100 || hauteur < 1) {
			System.out.println("ré-entrez une hauteur, votre valeur n'est pas correcte ( entre 1 et 100 inclus). : ");
			try {
				hauteur = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {}
		}



		System.out.print("entrez le nombre de mines : ");
		int n = 0;
		try {
			n = Integer.parseInt(scanner.nextLine()); // Si on arrive pas à parser, ce n'est pas un nombre. largeur est donc égal à 0
		} catch (Exception e) {}

		// Même chose que précedemment, en boucle tant que la valeur n'est pas
		// correctement définie
		while (n > hauteur * largeur || n < 1) {
			System.out.print("ré-entrez le nombre de mines. Il doit etre compris entre 1 et " + largeur * hauteur + " inclus. : ");
			try {
				n = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
			}
		}

		// Initialise le jeu
		init(hauteur, largeur, n);
		// Calcule les cases
		calculerAdjacent();

		// Affiche le formattage des actions
		System.out.println("Action : r ( revele ), d ( met un drapeau). Coordonnées : lignes( 00,01,02,...), colonnes ( A,B,C...z)");
		scanner.close();

		// Lance le jeu
		jeu();

	}

	//
	// Exercice 5 bonus challenge : Pour aller plus loin
	//

	// Question 5.a] Optionnel
	static void aide() {
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; i++) {
				if (T[i][j] == 1) {
					int caseRev = 0;
					int caseAdj = 0;
					for (int k = i - 1; k <= i + 1; k++) {
						for (int z = j - 1; z <= j + 1; z++) {
							if (caseCorrecte(k, z) && k != i && z != j) {
								caseAdj++;
								if (T[k][z] == 1) {
									caseRev++;
								}
							}
						}
					}
					if (Tadj[i][j] == caseAdj - caseRev) {
						for (int k = i - 1; k <= i + 1; k++) {
							for (int z = j - 1; z <= j + 1; z++) {
								if (caseCorrecte(k, z) && T[k][z] == 0) {
									char tmp;
									if ((char) z <= 25)
										tmp = (char) (z + 65);
									else
										tmp = (char) (z + 71);
									System.out.println("la case " + k + tmp + " est probablement une bombe");
								}
							}
						}
						return;
					}
				}
			}
		}
		System.out.println("désolé, je ne peux pas t'aider, découvre une case au hasard et tu verras bien !");
	}

	// Question 5.b] Optionnel
	public static void intelligenceArtificielle() {

	}

	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA() {

	}
}