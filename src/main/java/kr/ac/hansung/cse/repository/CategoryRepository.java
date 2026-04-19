package kr.ac.hansung.cse.repository;

import jakarta.persistence.EntityManager;
import kr.ac.hansung.cse.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {

    @Autowired
    private EntityManager em;

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }

    public void save(Category category) {
        em.persist(category);
    }

    public Optional<Category> findByName(String name) {
        List<Category> result = em.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findFirst();
    }

    public long countProductsByCategoryId(Long categoryId) {
        return em.createQuery("select count(p) from Product p where p.category.id = :categoryId", Long.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
    }

    public void delete(Long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }
}