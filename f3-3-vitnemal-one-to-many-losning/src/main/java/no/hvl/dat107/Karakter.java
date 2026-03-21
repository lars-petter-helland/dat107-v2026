package no.hvl.dat107;

import java.time.LocalDate;
import java.util.Objects;

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
    @JoinColumn(name = "studnr") //studnr er kolonnen studnr i karakter-tabellen !!
    private Vitnemal vitnemal;

    public Karakter() {}

    public Karakter(String emnekode, LocalDate eksdato, String bokstav, Vitnemal vitnemal) {
        this.emnekode = emnekode;
        this.eksdato = eksdato;
        this.bokstav = bokstav;
        this.vitnemal = vitnemal;
    }

    /**
     * Sier at to karakterer er like hvis de har samme primærnøkkel (karNr).
     * Denne brukes ved remove(k) fra listen Vitnemal.karakterer..
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        return karNr == ((Karakter) other).karNr;
    }

    /**
     * Denne defineres til å matche equals(), dvs. at det kun er primærnøkkel (karNr)
     * som brukes i beregning av hashkode.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(karNr);
    }

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
