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
	static int[][] T; //Tableau indiquant les cases révélées
	static int[][] Tadj; //Tableau contenant la position des bombes
	


	// Question 1.b] Fonction init
	static void init(int hauteur, int largeur, int n) {

		Tadj = new int[hauteur][largeur]; // initialisation de Tadj
		T = new int[hauteur][largeur]; // initialisation de T
		
		for(int i = 0; i < n; i++) { //va placer des bombes aléatoirement dans Tadj
			int bombeL = entierAleatoire(0, largeur+1);
			int bombeH = entierAleatoire(0, hauteur+1);
			if (Tadj[bombeH][bombeL] != -1) Tadj[bombeH][bombeL] = -1;	//Si pas de bombe, en pose une
			else n++; //Sinon, augmente n (sinon on se retrouve avec 1 bombe en moins)
		}
		for ( int j = 0; j < hauteur; j ++) { //Initialise toutes les cases non égales à -1 à 0 (place des espaces vides là ou il n'y a pas de bombes)
			for ( int k = 0; k < largeur; k ++) {
				if (Tadj[j][k] != -1) Tadj[j][k]= 0;
			}
		}
	}

	
	static void afficheTadj (int hauteur,int largeur) { //fonction qui va afficher Tabj (DEBUG)
		for ( int j = 0; j < hauteur; j ++) { //Parcours toutes les cases
			for ( int k = 0; k < largeur; k ++) {

				if (Tadj[j][k]!=-1) System.out.print(" "+Tadj[j][k]+" ");			
				else System.out.print(Tadj[j][k]+" ");	
			}
			System.out.println();
		}
	}
	
		
	// Question 1.c] Fonction caseCorrecte
	static boolean caseCorrecte(int i, int j) {
		return i < Tadj.length && j < Tadj[0].length && i >= 0 && j >= 0; // rempli les conditions que i et j doivent etre compris entre 0 (inclu) et longueur ou largeur (non inclu)
	}

	// Question 1.d] Fonction calculerAdjacent
	static void calculerAdjacent() {
		for ( int i = 0; i < Tadj.length; i++) {    				 //Parcours le tableau
			for ( int j = 0; j< Tadj[0].length; j++) {
				if (Tadj[i][j]!=-1) {								 //Vérifie que ce n'est pas une bombe
					for (int k = i-1; k <= i+1; k++) {				 //Si c'en est pas une, parcours les cases adjacentes
						for ( int z = j-1; z <= j+1; z++) {			 							
							if ( caseCorrecte(k,z) && Tadj[k][z]== -1) Tadj[i][j]++; //Si la case est correcte, ajoute 1 à la case en question (car à côté d'une bombe)					
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
		
		//Affichage ligne 0 (LETTRES)
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
		int unite = 0;
		int dizaine = 0;	
		
		for (int i = 0; i < T.length; i++) {	//Pour chaque ligne
			
			if ( unite > 9) { //Update l'index de la ligne
				unite = 0;
				dizaine++;
			}

			System.out.print(dizaine+""+unite+"|"); //Affiche l'index de la ligne
			unite++;

			//affiche le reste
			for ( int j = 0; j < T[i].length; j++) { //Parcours les colonnes
				
				if (!affMines) { //Si les bombes ne doivent pas être révélées
					if (T[i][j] == 0) System.out.print(" |"); //Si la case n'est pas révélée, affiche rien					
					else if (T[i][j] == 1) { //Sinon

						if (Tadj[i][j] == -1) System.out.print("!|"); //Si la case est une bomb, l'affiche
						
						
						else System.out.print(Tadj[i][j] +"|"); //Sinon affiche le contenu (Le nombre de bombes adjacentes)
					} 
					else System.out.print("X|");	//Sinon la case est marqué et affiche un X									
				} 
				else { //Si les bombes doivent être révélées.
					if (Tadj[i][j] == -1) System.out.print("!|");  //Si la case est une bombe l'affiche
					else System.out.print(Tadj[i][j] +"|"); //Sinon affiche son contenu (bombes adjacentes)
				}
			}
			System.out.println(); //Retourne à la ligne
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
		if ( s.compareTo("aide")==0)return true;
		else {
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
	}

	// Question 4.c]
	 static int[] conversionCoordonnees(String input) { // ATTENTION, vous devez modifier la signature de cette fonction
		int[]t = new int[3];
		t[0] = Integer.parseInt(input.substring(1, 3));
		int lettre = (int)input.charAt(3);
		if (lettre >'Z')t[1] = lettre-71;
		else t[1]=lettre-65;
		if (input.charAt(0)=='d')t[2]=0;
		else t[2]=1;
		return t;
	}

	// Question 4.d]
	static void jeu() {
		Scanner sc = new Scanner(System.in);
		boolean pasPerdu = true;
		while (pasPerdu) {	
			if (aGagne()) {
				pasPerdu = false;
				break;
			}
			afficherGrille(false);
			System.out.print("entrez votre action et/ou vos coordonnées : ");
			String coord = sc.nextLine();		
			while (!verifierFormat(coord)) {
				System.out.print("ré entrez votre action et/ou vos coordonnées, (elles sont fausses): ");
				coord = sc.nextLine();
			}
			if ( coord.compareTo("aide")==0){
				System.out.println("débrouille toi un peu la fonction aide est pas encore prête.");
			}
			else {
				int[]coord2 = conversionCoordonnees(coord);
				if (coord2[2]==0) {
					actionDrapeau(coord2[0],coord2[1]);
					T[coord2[0]][coord2[1]] = 2;
					
				}
				else {					
					pasPerdu = revelerCase(coord2[0],coord2[1]);
				}							
			}
		}	
		if(aGagne()) {
			afficherGrille(false);
			System.out.print("vous avez gagné");
		}
		else {
			afficherGrille(true);
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
		while (largeur>52||largeur<1) {
			System.out.print("ré-entrez une largeur, elle est trop grande ou trop petite ( entre 1 et 52 inclus) : ");
			largeur = scanner.nextInt();
		}
		System.out.print("entrez une hauteur : ");
		
		int hauteur = scanner.nextInt(); 
		while (hauteur>100||hauteur<1) {
			System.out.print("ré-entrez une hauteur, elle est trop grande ou trop petite ( entre 1 et 100 inclus) : ");
			hauteur = scanner.nextInt();
		}
		System.out.print("entrez le nombre de mines : ");
		int n = scanner.nextInt();
		while ( n>hauteur*largeur||n<1) {
			System.out.print("ré-entrez le nombre de mines. Il doit etre compris entre 1 et "+largeur*hauteur+" inclus : ");
			 n = scanner.nextInt();
		}		
		init (hauteur, largeur, n);
		calculerAdjacent();
		System.out.println("Action : r ( revele ), d ( met un drapeau). Coordonnées : lignes( 00,01,02,...), colonnes ( A,B,C...z)");
		jeu();		
		scanner.close();
		
		
		
	}


	//
	// Exercice 5 bonus challenge : Pour aller plus loin
	//

	// Question 5.a] Optionnel
	static void aide() {
		for (int i = 0; i<T.length; i ++){
			for ( int j = 0; j<T[0].length; i++ ){
				if (T[i][j]==1){
					int tmp = 0;
					for (int k = i-1; k <= i+1; k++) {
						for ( int z = j-1; z <= j+1; z++) {
							if (T[k][z]==1&& k!=i && z!=j) tmp++;					
						}
					}
					if (Tadj[i][j]==8-tmp) {
						for (int k = i-1; k <= i+1; k++) {
							for ( int z = j-1; z <= j+1; z++) {
								if (T[k][z]==0){
									T[k][z]=2;
								}
							}
						}
						return;
					}
				}
			}
		}
	}

	// Question 5.b] Optionnel
	public static void intelligenceArtificielle() {
		
	}

	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA() {
		
	}


	// As taken from
    // https://stackoverflow.com/questions/2979383/java-clear-the-console

}