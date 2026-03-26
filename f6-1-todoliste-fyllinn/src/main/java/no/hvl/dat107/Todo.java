package no.hvl.dat107;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "f6_todoliste")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tekst;
    
    @ManyToOne
    @JoinColumn(name="listeid")
    private Todoliste liste;
    
    public Todo() {}
    
    public Todo(String tekst) {
        this.tekst = tekst;
    }

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	
    public void setListe(Todoliste liste) {
		this.liste = liste;
	}

	@Override
    public String toString() {
        return tekst;
    }

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Todo todo = (Todo) o;
		return id == todo.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
