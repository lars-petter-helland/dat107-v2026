package no.hvl.dat107.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(schema = "f6c_subtyper_per_class")
public class Student extends Person { 

    private String studium;
    
    public Student() {}

    public Student(String fNr, String fornavn, String etternavn, String studium) {
        super(fNr, fornavn, etternavn);
        this.studium = studium;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", studium=" + studium;
    }
    
}

