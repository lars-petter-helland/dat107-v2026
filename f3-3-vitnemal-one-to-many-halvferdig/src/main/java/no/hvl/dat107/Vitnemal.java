package no.hvl.dat107;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "forelesning3_one2many")
public class Vitnemal {

    @Id private int studnr;
    private LocalDate studiestart;
    private LocalDate studieslutt;

    @OneToMany(mappedBy = "vitnemal", //vitnemal er objektvariabelen i Karakter-klassen
            fetch = FetchType.EAGER)
    private List<Karakter> karakterer;

    @Override
    public String toString() {
        return "\nVitnemal{" +
                "studnr=" + studnr +
                ", studiestart=" + studiestart +
                ", studieslutt=" + studieslutt +
                ", karakterer=" + karakterer +
                '}';
    }

    public int getStudnr() {
        return studnr;
    }

    public LocalDate getStudiestart() {
        return studiestart;
    }

    public LocalDate getStudieslutt() {
        return studieslutt;
    }

    public List<Karakter> getKarakterer() {
        return karakterer;
    }
}
