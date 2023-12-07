package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import entities.Emission;
import entities.Prediction;
import entities.User;

import java.util.List;
//update


public class EmissionDAO {
	  
		protected static EntityManagerFactory emf = 
		 		Persistence.createEntityManagerFactory("GHGEmission"); 	
		
		public  EmissionDAO() {
			
		}
		
		public void persist(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(emission);
			em.getTransaction().commit();
			em.close();
		}
		
		public void remove(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(emission));
			em.getTransaction().commit();
			em.close();
		}
		
		public Emission merge(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Emission updatedEmission = em.merge(emission);
			em.getTransaction().commit();
			em.close();
			return updatedEmission;
		}
		
		public List<Emission> getAllEmissions() {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Emission> emissionsFromDB  = em.createQuery("SELECT e FROM Emission e",Emission.class).getResultList();
			em.getTransaction().commit();
			em.close();
			return emissionsFromDB;
		}
		//get emission catergory
		//changed from createnamedquery to create queary
		public Emission getEmissionsByCategory(String category){
			EntityManager em = emf.createEntityManager();
			List<Emission> emissions = (List<Emission>) 
					em.createQuery("SELECT e FROM Emission e WHERE e.category = :category", Emission.class).
					setParameter("category", category).getResultList();
			em.close();
			Emission ems = new Emission();
			for(Emission e:emissions) {
				ems = e;
			}
			return ems;
		}
		
		//get emission year
		 public List<Emission> getEmissionsByGasUnit(String gasUnit) {
		        EntityManager em = emf.createEntityManager();
		        List<Emission> emissions = em.createQuery("SELECT e FROM Emission e WHERE e.gasUnit = :gasUnit", Emission.class)
		                .setParameter("gasUnit", gasUnit)
		                .getResultList();
		        em.close();
		        return emissions;
		    }
		//value
		
		  public List<Emission> getEmissionsByValue(double value) {
		        EntityManager em = emf.createEntityManager();
		        List<Emission> emissions = em.createQuery("SELECT e FROM Emissionep WHERE e.value = :value", Emission.class)
		                .setParameter("value", value)
		                .getResultList();
		        em.close();
		        return emissions;
		    }

			//get emission by id
		   public Emission getEmissionByID(int id) {
		        EntityManager em = emf.createEntityManager();
		        Emission emission= em.find(Emission.class, id);
		        em.close();
		        return emission;
		    }
		}
