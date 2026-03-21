package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		VitnemalDAO vitnemalDAO = new VitnemalDAO();
		
		//a) Søke opp vitnemålet til en gitt student.
        Vitnemal vm = vitnemalDAO.hentVitnemalForStudent(123456);
        System.out.println(vm);
		
		//b.i) Søke opp karakteren til en gitt student i et gitt kurs.
        Karakter k = vitnemalDAO.hentKarakterForStudentIEmne1(123456, "DAT107");
        System.out.println(k);

        //b.ii) Søke opp karakteren til en gitt student i et gitt kurs.
        //      Alternativ (bedre) løsning.
        k = vitnemalDAO.hentKarakterForStudentIEmne2(123456, "DAT107");
        System.out.println(k);

		//c) Registrere en ny karakter for en gitt student
		//		når karakter ikke finnes fra før.
        vitnemalDAO.registrerKarakterForStudent(123456, "DAT110", LocalDate.now(), "C");
        vm = vitnemalDAO.hentVitnemalForStudent(123456);
        System.out.println(vm);

		
		//d) Registrere en ny karakter for en gitt student
		//		når karakter finnes fra før. Samme metode som i sted.
        vitnemalDAO.registrerKarakterForStudent(123456, "DAT110", LocalDate.now(), "B");
        vm = vitnemalDAO.hentVitnemalForStudent(123456);
        System.out.println(vm);

		
		//"TEST"
		List<Karakter> hmmm = vitnemalDAO.hentKarakterlisteForFerdige("DAT107");
        System.out.print("\nvitnemalDAO.hentKarakterlisteForFerdige(\"DAT107\"): ");
		System.out.println(hmmm);
	}
	
//	private static Scanner scanner = new Scanner(System.in);
//	private static void pauseOgSjekkDatabasen(String prompt) {
//		System.out.println(prompt);
//		System.out.println("Trykk \"ENTER\" for å fortsette ...");
//		scanner.nextLine();
//	}
	

}





