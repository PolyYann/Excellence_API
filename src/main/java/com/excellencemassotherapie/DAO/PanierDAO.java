package com.excellencemassotherapie.DAO;

import com.excellencemassotherapie.modele.LigneCommande;
import com.excellencemassotherapie.modele.Panier;
import com.excellencemassotherapie.modele.Produit;
import com.excellencemassotherapie.modele.Soin;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PanierDAO implements ICommonDAO<Panier> {
    private final EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("hibernate");
    private EntityManager entityManager = null;


    @Override
    public List<Panier> getAll() {
        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Panier> criteriaQuery = criteriaBuilder.createQuery(Panier.class);
        Root<Panier> panierRoot = criteriaQuery.from(Panier.class);
        criteriaQuery.select(panierRoot);
        Query query = entityManager.createQuery(criteriaQuery);
        List<Panier> paniers = query.getResultList();
        entityManager.close();
        return paniers;
    }

    @Override
    public Panier getById(int id) {
        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Panier> criteriaQuery = criteriaBuilder.createQuery(Panier.class);
        Root<Panier> panierRoot = criteriaQuery.from(Panier.class);
        criteriaQuery.select(panierRoot).where(criteriaBuilder.equal(panierRoot.get("idPanier"), id));
        Query query = entityManager.createQuery(criteriaQuery);
        Panier panier = (Panier) query.getSingleResult();
        entityManager.close();
        return panier;

    }
    public Panier getByClientAndNonPaye(int idClient) {
        entityManager = entityManagerFactory.createEntityManager();
        String hql= "SELECT p FROM Panier p WHERE p.client.idClient = :idClient AND p.paye = false";
        TypedQuery<Panier> query = entityManager.createQuery(hql, Panier.class);
        query.setParameter("idClient", idClient);
       Panier panierClient =query.getSingleResult();
        entityManager.close();
        return panierClient;
    }

    public void payerPanier(Panier panier) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        panier.setPaye(true);
        entityManager.merge(panier);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * méthode à ne pas implémenté
     */
    @Override
    public Panier getByName(String nomClient) {

        return null;
    }

    @Override
    public void insert(Panier panier) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(panier);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}

