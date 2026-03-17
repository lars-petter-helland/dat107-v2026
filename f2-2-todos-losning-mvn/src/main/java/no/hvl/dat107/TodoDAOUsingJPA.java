package no.hvl.dat107;

import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class TodoDAOUsingJPA implements TodoDAO {
	
	private final EntityManagerFactory emf
			= Persistence.createEntityManagerFactory("todoPersistenceUnit");
	
	/* --------------------------------------------------------------------- */

	@Override
	public Todo finnTodoMedPk(int id) {

        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Todo.class, id);
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public List<Todo> finnAlleTodos() {

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Todo> query = em.createQuery(
                    "select t from Todo t", Todo.class);
            return query.getResultList();
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public Todo finnTodoMedTekst(String tekst) {

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Todo> query = em.createQuery(
                    "select t from Todo t where t.tekst like :tekst", Todo.class);
            query.setParameter("tekst", tekst);
            return query.getSingleResult(); // NB! Exception hvis ingen eller flere resultater
        }
	}
	
	/* --------------------------------------------------------------------- */

	@Override
	public List<Todo>  finnTodosMedTekst(String tekst) {

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Todo> query = em.createQuery(
                    "select t from Todo t where t.tekst like :tekst", Todo.class);
            query.setParameter("tekst", tekst);
            return query.getResultList(); // NB! Tom liste hvis ingen resultat
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public void lagreNyTodo(int id, String tekst) {
		
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (em) {
            tx.begin();

            Todo todo = new Todo(id, tekst);
            em.persist(todo);

            tx.commit();

        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public void slettTodoMedPk(int id) {
		
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (em) {
            tx.begin();

//			Todo todo = new Todo(id, "");
//			todo = em.merge(todo);

            Todo todo = em.find(Todo.class, id);
            em.remove(todo);

            tx.commit();

        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public void oppdaterTodo(Todo todo) {
		
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (em) {
            tx.begin();

            em.merge(todo);

            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
	}

	/* --------------------------------------------------------------------- */

	@Override
	public void oppdaterTekst(int id, String tekst) {
		
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (em) {
            tx.begin();

            Todo managedTodo = em.find(Todo.class, id);
            // NB!: Kan ikke gjøre:
            // Todo managedTodo = finnTodoMedPk(id);

            managedTodo.setTekst(tekst);

            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
	}
}
