package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad newad) {
        return em.merge(newad);
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT n FROM Ad n", Ad.class).getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }
/*
    public List<Note> findByKeyword(String keyword) {
        return em.createQuery("SELECT n FROM Note n WHERE n.text LIKE ?1", Note.class).setParameter(1, '%' + keyword + '%').getResultList();
    }
*/
    @Transactional
    public void deleteById(long id) {
        Ad rm = findById(id);
        em.remove(rm);
    }
}
