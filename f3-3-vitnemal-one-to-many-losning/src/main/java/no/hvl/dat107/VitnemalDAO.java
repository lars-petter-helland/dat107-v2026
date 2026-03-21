package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

public class VitnemalDAO {

    private EntityManagerFactory emf;

    public VitnemalDAO() {
        emf = Persistence.createEntityManagerFactory("vitnemalPU");
    }
    
    /* --------------------------------------------------------------------- */

    public Vitnemal hentVitnemalForStudent(int studnr) {
        
        EntityManager em = emf.createEntityManager();
        try {
        	return em.find(Vitnemal.class, studnr); //Returnerer null om ikke funnet.
        	
        } finally {
            em.close();
        }
    }

    /* --------------------------------------------------------------------- */

    public Karakter hentKarakterForStudentIEmne1(int studnr, String emnekode) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
            /*
             * En mulig løsning er altså å hente ut aktuelt vitnemål inkl. listen av karakterer,
             * og deretter finne den ene karakteren vi er interessert i ved Java-logikk.
             * Ulempe: Vi henter (mye) mer data fra databasen enn vi er interessert i.
             */
        	Vitnemal vm = em.find(Vitnemal.class, studnr); //Managed Vitnemal
            for (Karakter k : vm.getKarakterer()) { //Dette vil trigge LAZY-fetching hvis ikke EAGER er satt.
                if (k.getEmnekode().equals(emnekode)) {
                    return k;
                }
            }
            return null;

        } finally {
            em.close();
        }
    }

    /* --------------------------------------------------------------------- */

    public Karakter hentKarakterForStudentIEmne2(int studnr, String emnekode) {

        EntityManager em = emf.createEntityManager();

        try {
            /*
             * En annen mulig løsning er å lage en JPQL-spørring. Denne henter kun
             * den karakteren vi er interessert i fra databasen.
             */

            String sporring = """
                    select k from Karakter as k
                    where k.emnekode = :ek
                    and k.vitnemal.studnr = :sn """;

            TypedQuery<Karakter> q = em.createQuery(sporring, Karakter.class);
            q.setParameter("ek", emnekode);
            q.setParameter("sn", studnr);

            return q.getSingleResult();

        } catch (NoResultException e) { //NoResultException må håndteres hvis vi ønsker null i retur.
            return null;

        } finally {
            em.close();
        }
    }
    /* --------------------------------------------------------------------- */

    public void registrerKarakterForStudent(
            int studnr, String emnekode, LocalDate eksdato, String bokstav) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
        	tx.begin();

            /*
             * Denne løsningen bygger på at vi må oppdatere objektmodellen når vi registrerer
             * en karakter for en student. Hvis studenten allerede har en karakter i aktuelt
             * emne, ble vi enig om at denne slettes før den nye karakteren legges inn.
             *
             * Håper koden gjenspeiler logikken vi snakket oss gjennom i timen.
             */

            Vitnemal vm = em.find(Vitnemal.class, studnr);

            Karakter gmlK = hentKarakterForStudentIEmne2(studnr, emnekode);
            if (gmlK != null) {

                // Fjerne gammel karakter fra karakterlisten i vitnemålet.
                // List.remove() bruker Karakter.equals() for å finne/slette rett karakter.
                vm.getKarakterer().remove(gmlK);

                // Må også fjerne gammel karakter fra database / persistence context.
                // Må være managed først, så gjør merge() før remove().
                gmlK = em.merge(gmlK);
                em.remove(gmlK);

                // Når vi nedenfor prøver å opprette en rad med den nye karakteren får vi
                // org.postgresql.util.PSQLException:
                //      ERROR: duplicate key value violates unique constraint "karunique"
                //      Detail: Key (emnekode, studnr)=(DAT109, 123456) already exists.
                //
                // Dette er fordi databasen ikke har fått med seg ennå at vi har slettet en rad.
                // Gjør en syncing med databasen her med em.flush() for å unngå dette problemet!
                em.flush();
            }

            // Opprette og koble opp den nye karakteren til vitnemålet.
            Karakter nyK = new Karakter(emnekode, eksdato, bokstav, vm); //ref. fra owning side
            vm.getKarakterer().add(nyK); //ref. fra reverse side

            // Opprette en rad i databasen, inkl. få generert primærnøkkel og gjøre nyK managed.
            em.persist(nyK);
        	
        	tx.commit();
        	
        } finally {
            em.close();
        }
    }
    
    /* --------------------------------------------------------------------- */

    public List<Karakter> hentKarakterlisteForFerdige(String emnekode) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
        	/*
        	    I SQL-filen står denne SQL-spørringen,
                ... som finner DAT107-karakterer for studenter som er
                ferdig (har sluttdato). Forventer kun (1, DAT107, '2022-05-30', 'A', 123456)

                    SELECT *
                    FROM karakter k JOIN vitnemal v ON k.studnr = v.studnr
                    WHERE v.studieslutt IS NOT NULL
                    AND k.emnekode LIKE 'DAT107';

                Tilsvarende spørring i JPQL kan være slik. Ser dere forskjellen?
                - JOIN via fremmednøkkel erstattes av navigering via objektreferanse.
             */
        	String jpqlQuery = """
        			select k 
        			from Karakter k
        			where k.vitnemal.studieslutt is not null 
        			and k.emnekode like :emnekode """;
        	
			TypedQuery<Karakter> query = em.createQuery(jpqlQuery, Karakter.class);
			query.setParameter("emnekode", emnekode);

			return query.getResultList();
        	
        } finally {
            em.close();
        }
    }
    

}

