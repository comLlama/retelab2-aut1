package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.generator.SecretGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad newAd) {
        newAd.setSecret(SecretGenerator.generate());
        return em.merge(newAd);
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT n FROM Ad n", Ad.class).getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }

    public List<Ad> findByPrice(int minPrice, int maxPrice) {
        //return em.createQuery("SELECT n FROM Note n WHERE n.text LIKE ?1", Note.class).setParameter(1, '%' + keyword + '%').getResultList();
        return em.createQuery("SELECT a FROM Ad a WHERE a.price >= ?1 AND a.price <= ?2", Ad.class)
                .setParameter(1, minPrice)
                .setParameter(2, maxPrice)
                .getResultList();
    }

    @Transactional
    public Ad modify(Ad newAd) throws AuthenticationException {
        Ad oldAd = findById(newAd.getId());
        if ( oldAd == null || !(oldAd.getSecret().equals( newAd.getSecret() )) ) {
            throw new AuthenticationException("Can't modify ad.");
        }
        newAd.setCreatedAt(oldAd.getCreatedAt());
        return em.merge(newAd);
    }

    @Transactional
    public void deleteById(long id) {
        Ad rm = findById(id);
        em.remove(rm);
    }
}
