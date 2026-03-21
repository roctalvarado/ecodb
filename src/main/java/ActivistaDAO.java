import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 * 
 * @author josel
 */
public class ActivistaDAO implements IActivistaDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EcoActivistasPU");

    @Override
    public boolean agregar(Activista a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean actualizar(Activista a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(a); // merge actualiza el registro si el ID ya existe
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Activista a = em.find(Activista.class, id);
            if (a != null) {
                em.remove(a);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Activista consultar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Activista.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Activista> consultarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Activista a ORDER BY a.id ASC", Activista.class).getResultList();
        } finally {
            em.close();
        }
    }
}
