/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Disciplina;

/**
 *
 * @author Monnalisa Medeiros
 */
public class DisciplinaJpaController implements Serializable {

    public DisciplinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disciplina disciplina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina preRequisito = disciplina.getPreRequisito();
            if (preRequisito != null) {
                preRequisito = em.getReference(preRequisito.getClass(), preRequisito.getId());
                disciplina.setPreRequisito(preRequisito);
            }
            em.persist(disciplina);
            if (preRequisito != null) {
                Disciplina oldPreRequisitoOfPreRequisito = preRequisito.getPreRequisito();
                if (oldPreRequisitoOfPreRequisito != null) {
                    oldPreRequisitoOfPreRequisito.setPreRequisito(null);
                    oldPreRequisitoOfPreRequisito = em.merge(oldPreRequisitoOfPreRequisito);
                }
                preRequisito.setPreRequisito(disciplina);
                preRequisito = em.merge(preRequisito);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplina disciplina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina persistentDisciplina = em.find(Disciplina.class, disciplina.getId());
            Disciplina preRequisitoOld = persistentDisciplina.getPreRequisito();
            Disciplina preRequisitoNew = disciplina.getPreRequisito();
            if (preRequisitoNew != null) {
                preRequisitoNew = em.getReference(preRequisitoNew.getClass(), preRequisitoNew.getId());
                disciplina.setPreRequisito(preRequisitoNew);
            }
            disciplina = em.merge(disciplina);
            if (preRequisitoOld != null && !preRequisitoOld.equals(preRequisitoNew)) {
                preRequisitoOld.setPreRequisito(null);
                preRequisitoOld = em.merge(preRequisitoOld);
            }
            if (preRequisitoNew != null && !preRequisitoNew.equals(preRequisitoOld)) {
                Disciplina oldPreRequisitoOfPreRequisito = preRequisitoNew.getPreRequisito();
                if (oldPreRequisitoOfPreRequisito != null) {
                    oldPreRequisitoOfPreRequisito.setPreRequisito(null);
                    oldPreRequisitoOfPreRequisito = em.merge(oldPreRequisitoOfPreRequisito);
                }
                preRequisitoNew.setPreRequisito(disciplina);
                preRequisitoNew = em.merge(preRequisitoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = disciplina.getId();
                if (findDisciplina(id) == null) {
                    throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina;
            try {
                disciplina = em.getReference(Disciplina.class, id);
                disciplina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.", enfe);
            }
            Disciplina preRequisito = disciplina.getPreRequisito();
            if (preRequisito != null) {
                preRequisito.setPreRequisito(null);
                preRequisito = em.merge(preRequisito);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplina> findDisciplinaEntities() {
        return findDisciplinaEntities(true, -1, -1);
    }

    public List<Disciplina> findDisciplinaEntities(int maxResults, int firstResult) {
        return findDisciplinaEntities(false, maxResults, firstResult);
    }

    private List<Disciplina> findDisciplinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplina.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Disciplina findDisciplina(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplina.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplina> rt = cq.from(Disciplina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
