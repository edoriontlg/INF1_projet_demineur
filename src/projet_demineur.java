// Ceci est un squelette à REMPLIR pour le projet INF1 sur le jeu de démineur
//
// - N'oubliez pas de renseigner vos deux noms
// Prénom Nom Groupe : élève 1/2
// Prénom Nom Groupe élève 2/2
//
// - Pour chaque question, le squelette donne le nom de la fonction à écrire mais *pas* la signature
//   il faut remplir les types d'entrées et de sorties (indiqués par ?) et remplir l'intérieur du code de chaque fonction.
//
// - L'unique fichier de code que vous soumettrez sera ce fichier Java, donc n'hésitez pas à le commenter abondamment.
//   inutile d'exporter votre projet comme archive Zip et de rendre ce zip.
//   Optionnel : vous pouvez aussi joindre un document PDF donnant des explications supplémentaires (si vous utilisez OpenOffice/LibreOffice/Word, exportez le document en PDF), avec éventuellement des captures d'écran montrant des étapes affichées dans la console
//
// - Regardez en ligne sur le Moodle pour le reste des consignes, et dans le fichier PDF du sujet du projet
//   https://foad.univ-rennes1.fr/mod/assign/view.php?id=534254
//
// - A rendre avant le vendredi 04 décembre, maximum 23h59.
//
// - ATTENTION Le projet est assez long, ne commencez pas au dernier moment !
//
// - Enfin, n'hésitez pas à contacter l'équipe pédagogique, en posant une question sur le forum du Moodle, si quelque chose n'est pas clair.
//

// Pour utiliser des scanners pour lire des entrées depuis le clavier
// utilisés en questions 4.d] pour la fonction jeu()
import java.io.IOException;
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;

// L'unique classe de votre projet
public class projet_demineur {

	// Donné, utile pour la question 1.b]
	public static int entierAleatoire(int a, int b){
		// Renvoie un entier aléatoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b-1);
	}


	//
	// Exercice 1 : Initialisation des tableaux
	//

	// Question 1.a] déclarez les variables globales T et Tadj ici
	static int[][] T; //Voici les variables globale
	static int[][] Tadj;
	


	// Question 1.b] Fonction init
	static void init(int hauteur, int largeur, int n) { // ATTENTION, vous devez modifier la signature de cette fonction
		
		Tadj = new int[hauteur][largeur]; // initialisation de Tadj
		T = new int[hauteur][largeur]; // initialisation de T
		for(int i = 0; i < n; i++) { //cette boucle for va placer des bombes aléatoirement dans Tadj
			int bombeL = entierAleatoire(0, largeur);
			int bombeH = entierAleatoire(0, hauteur);
			if (Tadj[bombeH][bombeL] != -1) Tadj[bombeH][bombeL] = -1;	// ce if evite qu'il y ai moins de bombes que prévu, si 2 bombes se trouvent au meme endroits
			else n++;
		}
		for ( int j = 0; j < hauteur; j ++) { // cette boucle place les 0 sur toutes les autres cases de Tabj
			for ( int k = 0; k < largeur; k ++) {
				if (Tadj[j][k] != -1) Tadj[j][k]= 0;
			}
		}
	}

	
	static void afficheTabj (int hauteur,int largeur) { //fonction qui va afficher Tabj
		for ( int j = 0; j < hauteur; j ++) {
			for ( int k = 0; k < largeur; k ++) {
				if (Tadj[j][k]!=-1) System.out.print(" "+Tadj[j][k]+" ");			
				else System.out.print(Tadj[j][k]+" ");	
			}
			System.out.println();

		}
	}
	
		
	// Question 1.c] Fonction caseCorrecte
	static boolean caseCorrecte(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction)
		return i < Tadj.length && j < Tadj[0].length && i >= 0 && j >= 0; // rempli les conditions que i et j doivent etre compris entre 0 (inclu) et longueur ou largeur (non inclu)
	}

	// Question 1.d] Fonction calculerAdjacent
	static void calculerAdjacent() {
		for ( int i = 0; i < Tadj.length; i++) {
			for ( int j = 0; j< Tadj[0].length; j++) { //ces 2 boucles for servent à scrupter pour chaques cases du tableau
				if (Tadj[i][j]!=-1) { //ce if vérifie que la case en question n'est pas une bombe
					for (int k = i-1; k <= i+1; k++) {
						for ( int z = j-1; z <= j+1; z++) { // ces 2 boucles for vont scrupter toutes les cases autour de la case à vérifier ( 1 case en diagonal et une 1 case de haut en bas et de droite a gauche )
							
							if ( caseCorrecte(k,z) && Tadj[k][z]== -1) Tadj[i][j]++; //c'est un if important, pour les cases se trouvant en bordure ou en coin de Tadj, pour eviter que k et z soient égal a -1 ou a tadj.length
								 // si une des cases à coté de la case à vérifier est une bombe, alors elle sera incrémentée de 1
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
	static void afficherGrille(boolean affMines) { // ATTENTION, vous devez modifier la signature de cette fonction
		//affichage ligne 0
		int lettres = 65;
		System.out.print("  | ");
		for ( int i = 0; i<T[0].length; i ++) {
			if (i!=25 && i< 53) {
				System.out.print((char)lettres+" | ");
				lettres++;
			}
			else if ( i == 25) {
				System.out.print((char)lettres+" | ");
				lettres = lettres + 7;
			}
		}
		System.out.println();
<<<<<<< Updated upstream


		//============================================
=======
>>>>>>> Stashed changes
		//Affichage lignes
		int unité = 0;
		int dizaine = 0;
		for (int i = 0; i < T.length; i++) {		
			//affichage 0	
			if ( unité > 9) {
				unité = 0;
				dizaine++;
			}	
			System.out.print(dizaine+""+unité+"| ");
			unité++;	
			//affiche reste
			for ( int j = 0; j < T[i].length; j++) {
				if (!affMines) {
					if (T[i][j] == 0) {
						System.out.print((char)32+" | ");
					} else if (T[i][j] == 1) {
						if (Tadj[i][j] == -1) System.out.print(Tadj[i][j] +"| ");
						else System.out.print(Tadj[i][j] +" | ");
					} else {
						System.out.print("X"+" | ");
					}
				} else {
					if (Tadj[i][j] == -1) System.out.print(Tadj[i][j] +"| ");
					else System.out.print(Tadj[i][j] +" | ");
				}
			}
			
			
							
					
			
			
			System.out.println();		
		}
						
		
		
			
			
		
		
	}


	//
	// Exercice 3 : Révéler une case
	//

	// Question 3.a]
	public static void caseAdjacenteZero() { // ATTENTION, vous devez modifier la signature de cette fonction
		
		
	}

	// Question 3.b]
	public static void revelation() { // ATTENTION, vous devez modifier la signature de cette fonction
		

	}


	// Question 3.c] Optionnel
	public static void relevation2() { // ATTENTION, vous devez modifier la signature de cette fonction
		
	
	}

	// Question 3.d]
	public static void actionDrapeau() { // ATTENTION, vous devez modifier la signature de cette fonction
		
	}
	
	
	// Question 3.e]
	public static void revelerCase() { // ATTENTION, vous devez modifier la signature de cette fonction
		
	}


	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	public static void aGagne() {
		
	}

	// Question 4.b]
	public static void verifierFormat() { // ATTENTION, vous devez modifier la signature de cette fonction
		
		
	}

	// Question 4.c]
	public static void conversionCoordonnees() { // ATTENTION, vous devez modifier la signature de cette fonction
		
		
	}

	// Question 4.d]
	public static void jeu() {
		
	}

	// Question 4.e]
	// Votre *unique* méthode main
	public static void main(String[] args) {
		int largeur = 10; // initialisation de la hauteur et la largeur des 
		int hauteur = 10;
		int n = 16;
		
		init (hauteur, largeur, n);
		calculerAdjacent();
		//afficheTabj(hauteur, largeur);
		
		
		
		afficherGrille(true);
		
	}


	//
	// Exercice 5 bonus challenge : Pour aller plus loin
	//

	// Question 5.a] Optionnel
	public static void aide() {
		
	}

	// Question 5.b] Optionnel
	public static void intelligenceArtificielle() {
		
	}

	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA() {
		
	}


	// As taken from
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
        }
    }
}
