import java.util.Scanner;
import java.util.Random;

/**
 * @author s77258
 *
 */
public class Roulette {

	enum tipp {
		SCHWARZ, WEISS
	};

	double guthaben;
	double einsatz;
	Scanner scanner;
	tipp meintipp;
	double random;
	boolean again;
	double wahl;
	long versuche;
	int highestvalue;
	int numberofgames;
	boolean bigtest;
	boolean AIisrunning;

	public static void main(String[] args) {
		Roulette r = new Roulette();
		r.startgame();
	}

	public Roulette() {
		guthaben = 100;
		einsatz = 5;
		scanner = new Scanner(System.in);
		again = true;
		versuche = 0;
		int highestvalue = 0;
		numberofgames = 1000;
		bigtest = false;
		AIisrunning = true;

	}

	void startgame() {
		System.out.println("*************************************");
		System.out.println("Willkommen beim Scwhwarz/Weiss Roulett Tisch!");
		System.out.println("Du startest mit 100 Dollars und einem Einsatz von 5 Dollars!");
		System.out.println("*************************************");
		this.options();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String s = scanner.next();

			if (s.equals("6")) {
				bigtest = true;
				this.bigtest();
				this.options();
			}

			if (s.equals("5")) {
				System.out.println("KI ist gestartet.");
				this.startAI();
				System.out.println("KI ist beendet.");
				this.options();
			}
			if (s.equals("4")) {
				this.quit();
			}
			if (s.equals("3")) {
				this.setEinsatz();
				this.options();

			}
			if (s.equals("2")) {
				this.setGuthaben();
				this.options();
			}
			if (s.equals("1")) {
				again = true;
				while (again == true)
					this.gamblethisshityourself();
				this.options();
			}
		}

	}

	void zeigeGuthaben() {
		System.out.println("Dein Guthaben betraegt: " + guthaben + "\n");
	}

	void setEinsatz() {
		System.out.println("Neuer Einsatz:  ");
		double f = -1;
		f = scanner.nextDouble();
		if (f > 0.0) {
			einsatz = f;
			System.out.println(f);
			System.out.println("Aenderung erfolgreich.");
		}
	}

	void setGuthaben() {
		System.out.println("Neues Guthaben:  ");

		double b = -1;
		b = scanner.nextDouble();
		if (b > 0.0) {
			guthaben = b;
			versuche = 0;
			System.out.println(b);
			System.out.println("Aenderung erfolgreich.");
		}

	}

	void gamblethisshityourself() {
		System.out.println("Spiel  " + versuche + ":  ");
		System.out.println("Setzt du auf Schwarz(s) oder Weiss(w)?");

		while (true) {
			String s = scanner.next();
			if (s.equals("s")) {
				meintipp = tipp.SCHWARZ;
				scanner.nextLine();
				break;
			}
			if (s.equals("w")) {
				meintipp = tipp.WEISS;
				scanner.nextLine();
				break;
			}
		}
		if (guthaben >= einsatz) {
			random = Math.random();
			this.decide(random, meintipp);
			this.weiterspielen();
		} else {
			System.out.println("Du hast nicht genug Geld fuer diese Wette!");
			again = false;
		}

	}

	void startAI() {
		while (AIisrunning) {
			wahl = Math.random();
			random = Math.random();

			if (wahl >= 0.5)
				meintipp = tipp.SCHWARZ;
			if (wahl < 0.5)
				meintipp = tipp.WEISS;

			System.out.println("Spiel  " + versuche + ":  ");
			decide(random, meintipp);
			/*
			 * try { Thread.sleep(); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			if (guthaben > highestvalue)
				highestvalue = (int) guthaben;
		}
	}

	void win() {
		System.out.println("Richtig!");
		guthaben += einsatz;
	}

	void lose() {
		System.out.println("Falsch!");
		guthaben -= einsatz;
	}

	void quit() {
		System.out.println("Programm beendet");
		System.exit(0);
	}

	void options() {
		System.out.println("*************************************");
		System.out.println("Drücke die 1 wenn du losspielen moechtest");
		System.out.println("Drücke die 2 wenn du das Guthaben  aendern moechtest");
		System.out.println("Drücke die 3 wenn den Einsatz   aendern moechtest");
		System.out.println("Drücke die 4 wenn du das Spiel beenden moechtest");
		System.out.println("Drücke die 5 wenn die KI uebernehmen soll");
		System.out.println("Drücke die 6 wenn du den grossen Test machen moechtest");
		System.out.println("*************************************");
	}

	void decide(double x, Roulette.tipp y) {

		if ((x <= 0.486 && y == tipp.SCHWARZ) || (x > 0.514 && y == tipp.WEISS)) {
			this.win();
			versuche++;
		}

		else {
			this.lose();
			versuche++;
			if (!(guthaben > einsatz)) {
				System.out.println("GAME OVER nach " + versuche + " Spielen!");
				System.out.println("Highest Value was " + highestvalue);
				if (!bigtest)
					this.quit();
				else
					AIisrunning = false;
			}
		}
		this.zeigeGuthaben();
	}

	void weiterspielen() {
		System.out.println("Weiterspielen: (y/n)?");
		String s = scanner.next();
		if (s.equals("y")) {
			again = true;
		}
		if (s.equals("n")) {
			again = false;
		}
	}

	void bigtest() {
		long games = 0;
		long mostmoney = 0;
		for (int i = 0; i < numberofgames; i++) {
			this.startAI();

			games += versuche;
			versuche = 0;

			mostmoney += (long) highestvalue;
			highestvalue = 0;

			guthaben = 100;
			einsatz = 5;

			AIisrunning = true;
		}
		double a = (double) (games / numberofgames);
		double b = (double) (mostmoney / numberofgames);
		System.out.println("On average the number of games played was " + a + " with an average money spike of " + b
				+ " after 1000 rounds tested.\n");

		bigtest = false;
	}

}
