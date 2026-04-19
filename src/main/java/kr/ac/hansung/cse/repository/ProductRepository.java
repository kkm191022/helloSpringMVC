package kr.ac.hansung.cse.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.hansung.cse.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.category ORDER BY p.id ASC", Product.class).getResultList();
    }

    public List<Product> findByNameContaining(String keyword) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :keyword", Product.class)
                .setParameter("keyword", "%" + keyword + "%").getResultList();
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return em.createQuery("SELECT p FROM Product p WHERE p.category.id = :cid", Product.class)
                .setParameter("cid", categoryId).getResultList();
    }

    public void save(Product product) { em.persist(product); }
    public void delete(Long id) {
        Product p = em.find(Product.class, id);
        if (p != null) em.remove(p);
    }
}