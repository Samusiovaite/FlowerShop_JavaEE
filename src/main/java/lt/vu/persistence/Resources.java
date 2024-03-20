package lt.vu.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.SynchronizationType;

// Klase, kuri atsakinga už EntityManager objektų kūrią su JPA ir CDI
// Suteikia funkcionalumą valdyti em objektus, gautus HTTP užklausos gyvavimo
// metu. Užtikrina, kad objektai bus sunaikinti, kai tampa nebereikalingais.
@ApplicationScoped
public class Resources {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @Default
    @RequestScoped
    private EntityManager createJTAEntityManager() {
        return emf.createEntityManager(SynchronizationType.SYNCHRONIZED);
    }

    private void closeDefaultEntityManager(@Disposes @Default EntityManager em) {
        em.close();
    }
}
