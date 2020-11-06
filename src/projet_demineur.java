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
							if ( caseCorrecte(k,z) && Tadj[k][z]== -1) Tadj[i][j]++; //c'est un if important, pour les cases se trouvant en bordure ou en coin de Tadj, pour eviter que k et z soient égal a -1 ou a tadj.length et si une des cases à coté de la case à vérifier est une bombe, alors elle sera incrémentée de 1						
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
		//Affichage ligne 0
		int lettres = 65;
		System.out.print("  |");
		for ( int i = 0; i<T[0].length; i ++) {
			if (i!=25 && i< 53) {
				System.out.print((char)lettres+"|");
				lettres++;
			}
			else if ( i == 25) {
				System.out.print((char)lettres+"|");
				lettres = lettres + 7;
			}
		}
		System.out.println();
		//Affichage lignes
		int unité = 0;
		int dizaine = 0;	
		for (int i = 0; i < T.length; i++) {	
			//affichage 0	
			if ( unité > 9) {
				unité = 0;
				dizaine++;
			}
			System.out.print(dizaine+""+unité+"|");
			unité++;
			//affiche reste
			for ( int j = 0; j < T[i].length; j++) {
				if (affMines) {
					if (T[i][j] == 0) System.out.print(" |");							
					else if (T[i][j] == 1) {
						if (Tadj[i][j] == -1) {
							System.out.print("!|");
						}
						
						else System.out.print(Tadj[i][j] +"|");
					} 
					else System.out.print("X|");										
				} 
				else {
					if (Tadj[i][j] == -1) System.out.print("!|");
					else System.out.print(Tadj[i][j] +"|");
				}
			}
			System.out.println();		
		}		
	}


	//
	// Exercice 3 : Révéler une case
	//

	// Question 3.a]
	static boolean caseAdjacenteZero(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		for (int k = i-1; k <= i+1; k++) {
			for ( int z = j-1; z <= j+1; z++) { 
				if ( caseCorrecte(k,z) && Tadj[k][z]== 0 && T[k][z]==1 &&(k!=i || z!=j)) {
					return true;
				}				
			}
		}
		return false;
	}

	// Question 3.b]
	static void revelation(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		T[i][j]=1;
		boolean b = true;
		if (Tadj[i][j]==0) {
			while(b) {
				int c = 0;
				for ( int n = 0; n < T.length; n ++) {
					for ( int k = 0; k < T[0].length; k ++) {
						if (caseAdjacenteZero(n,k) && T[n][k]==0) {
							T[n][k]=1;
							c++;						
						}										
					}				
				}
				if ( c==0 )b = false;
			}		
		}
	}


	// Question 3.c] Optionnel
	static void revelation2(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		T[i][j]=1;
		if (Tadj[i][j]==0) {
			for (int k = i-1; k <= i+1; k++) {
				for ( int z = j-1; z <= j+1; z++) { 
					if ( caseCorrecte(k,z)) {
						T[k][z]=1;		
						if (Tadj[k][z]==0 &&( k!=i || z!=j))revelation2(k, z);
					}			
				}
			}	
		}
		
	
	}

	// Question 3.d]
	static void actionDrapeau(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		if ( T[i][j]!=1) {
			if (T[i][j]!=2) T[i][j] = 2;
			else T[i][j] = 0;
		}
	}
	
	
	// Question 3.e]
	static boolean revelerCase(int i , int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		if( Tadj[i][j]==-1)return false;
		else {
			revelation(i,j);
			return true;
		}
	}


	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	static boolean aGagne() {
		int compte = 0;
		for ( int i = 0; i < T.length; i++) {
			for ( int j = 0; j< T[0].length; j++) {
				if (( T[i][j]==1 && Tadj[i][j]!=-1)||(( T[i][j]==2||T[i][j]==0) && Tadj[i][j]==-1))compte++;
			}
		}
		return compte == T.length*T[0].length;
	}

	// Question 4.b]
	static boolean verifierFormat( String s ) { // ATTENTION, vous devez modifier la signature de cette fonction
		if ( s.length()!=4)return false;
		else {
			if ( s.charAt(0) != 'd' && s.charAt(0) != 'r' ) return false;	
			else if ( s.charAt(1)!='0'&& s.charAt(1)!='1'&& s.charAt(1)!='2'&& s.charAt(1)!='3'&& s.charAt(1)!='4'&& s.charAt(1)!='5'&& s.charAt(1)!='6'&& s.charAt(1)!='7'&& s.charAt(1)!='8'&& s.charAt(1)!='9') return false;
			else if ( s.charAt(2)!='0'&& s.charAt(2)!='1'&& s.charAt(2)!='2'&& s.charAt(2)!='3'&& s.charAt(2)!='4'&& s.charAt(2)!='5'&& s.charAt(2)!='6'&& s.charAt(2)!='7'&& s.charAt(2)!='8'&& s.charAt(2)!='9') return false;
			else {
				int lettres = 65;
				for ( int i = 0; i < 52;i ++) {
					if ( s.charAt(3)==(char)lettres)return true;
					if (i!=25) lettres++;																	
					else lettres=lettres + 7;																									
				}			
				return false;
			}			
		}
	}

	// Question 4.c]
	 static int[] conversionCoordonnees(String input) { // ATTENTION, vous devez modifier la signature de cette fonction
		int[]t = new int[3];
		t[0] = Integer.parseInt(input.substring(1, 3));
		int lettre = (int)input.charAt(3);
		if (lettre >90)t[1] = lettre-97;
		else t[1]=lettre-65;
		if (input.charAt(0)=='d')t[2]=0;
		else t[2]=1;
		return t;
	}

	// Question 4.d]
	static void jeu() {
		Scanner sc = new Scanner(System.in);
		boolean pasPerdu= true;
		while (pasPerdu) {	
			if (aGagne()) {
				pasPerdu = false;
				break;
			}
			afficherGrille(true);
			System.out.print("entrez votre action et vos coordonnées : ");
			String coord = sc.nextLine();		
			if (!verifierFormat(coord)) {
				System.out.print("ré entrez votre action et vos coordonnées, (elles sont fausses): ");
				coord = sc.nextLine();
			}
			
			int[]coord2 = conversionCoordonnees(coord);
			if (coord2[2]==0) {
				actionDrapeau(coord2[0],coord2[1]);
				T[coord2[0]][coord2[1]] = 2;
					
				}
			else {					
					pasPerdu = revelerCase(coord2[0],coord2[1]);
			}							
		}
		if(aGagne()) {
			afficherGrille(true);
			System.out.print("vous avez gagné");
		}
		else {
			afficherGrille(false);
			System.out.print("vous avez perdu");
		}
		sc.close();
		
		
	}

	// Question 4.e]
	// Votre *unique* méthode main
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("entrez une largeur : ");
		int largeur = scanner.nextInt();
		if (largeur>52||largeur<1) {
			System.out.print("ré-entrez une largeur, elle est trop grande ou trop petite ( entre 1 et 52 inclus).");
			largeur = scanner.nextInt();
		}
		System.out.print("entrez une hauteur : ");
		
		int hauteur = scanner.nextInt(); 
		if (hauteur>100||hauteur<1) {
			System.out.print("ré-entrez une hauteur, elle est trop grande ou trop petite ( entre 1 et 100 inclus).");
			hauteur = scanner.nextInt();
		}
		System.out.print("entrez le nombre de mines : ");
		int n = scanner.nextInt();
		if ( n>hauteur*largeur||n<1) {
			System.out.print("ré-entrez le nombre de mines. Il doit etre compris entre 1 et "+largeur*hauteur+"inclus. ");
			 n = scanner.nextInt();
		}		
		init (hauteur, largeur, n);
		calculerAdjacent();
		System.out.println("Action : r ( revele ), d ( met un drapeau). Coordonnées : lignes( 00,01,02,...), colonnes ( A,B,C...z");
		System.out.println();
		jeu();		
		scanner.close();
		
		
		
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
