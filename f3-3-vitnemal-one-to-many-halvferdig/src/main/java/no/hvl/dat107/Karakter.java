package no.hvl.dat107;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(schema = "forelesning3_one2many")
public class Karakter {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int karNr;

	private String emnekode;
	private LocalDate eksdato;
	private String bokstav;
	
	@ManyToOne
    @JoinColumn(name = "studnr") //studnr er kolonnen studnr i karakter-tabellen
    private Vitnemal vitnemal;

    @Override
    public String toString() {
        return "\n\tKarakter{" +
                "karNr=" + karNr +
                ", emnekode='" + emnekode + '\'' +
                ", eksdato=" + eksdato +
                ", bokstav='" + bokstav + '\'' +
                '}';
    }

    public int getKarNr() {
        return karNr;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public LocalDate getEksdato() {
        return eksdato;
    }

    public String getBokstav() {
        return bokstav;
    }

    public Vitnemal getVitnemal() {
        return vitnemal;
    }
}
