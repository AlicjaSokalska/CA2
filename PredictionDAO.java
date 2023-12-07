package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Emission;
import entities.Prediction;

public class PredictionDAO {
	  
		protected static EntityManagerFactory emf = 
		 		Persistence.createEntityManagerFactory("GHGEmission"); 	
		
		public PredictionDAO() {
			
		}
		
		public void persist(Prediction prediction) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(prediction);
			em.getTransaction().commit();
			em.close();
		}
		
		public void remove(Prediction prediction) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(prediction));
			em.getTransaction().commit();
			em.close();
		}
		
		public Prediction merge(Prediction prediction) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Prediction updatedPrediction = em.merge(prediction);
			em.getTransaction().commit();
			em.close();
			return updatedPrediction;
		}
		
		public List<Prediction> getAllPredictions() {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Prediction> predictionsFromDB  = em.createQuery("SELECT p FROM Prediction p",Prediction.class).getResultList();
			em.getTransaction().commit();
			em.close();
			return predictionsFromDB;
		}
		//get emission catergory
		
		public Prediction getPredictionsByCategory(String category){
			EntityManager em = emf.createEntityManager();
			List<Prediction> predictions = (List<Prediction>) 
					em.createQuery("SELECT p FROM Prediction p WHERE p.category = :category",Prediction.class).
					setParameter("category", category).getResultList();
			em.close();
			//Do whatever you want with the subscriber(s) with that username
			//Here we just return the first one
		Prediction pre = new Prediction();
			for(Prediction p:predictions) {
				pre = p;
			}
			return pre;
		}
		//by scenario
		public List<Prediction> getPredictionsByScenario(String scenario) {
	        EntityManager em = emf.createEntityManager();
	        List<Prediction> predictions = em.createQuery("SELECT p FROM Prediction p WHERE p.scenario = :scenario", Prediction.class)
	                .setParameter("scenario", scenario)
	                .getResultList();
	        em.close();
	        return predictions;
	    }
//by gasUnit 
	    public List<Prediction> getPredictionsByGasUnit(String gasUnit) {
	        EntityManager em = emf.createEntityManager();
	        List<Prediction> predictions = em.createQuery("SELECT p FROM Prediction p WHERE p.gasUnit = :gasUnit", Prediction.class)
	                .setParameter("gasUnit", gasUnit)
	                .getResultList();
	        em.close();
	        return predictions;
	    }
//value
	    public List<Prediction> getPredictionsByValue(double value) {
	        EntityManager em = emf.createEntityManager();
	        List<Prediction> predictions = em.createQuery("SELECT p FROM Prediction p WHERE p.value = :value", Prediction.class)
	                .setParameter("value", value)
	                .getResultList();
	        em.close();
	        return predictions;
	    }

		
		//get emission year
	
		public Prediction getPredictionsByYear(int year){
			EntityManager em = emf.createEntityManager();
			List<Prediction>predictions = (List<Prediction>) 
					em.createQuery("SELECT p FROM Prediction p WHERE p.year = :year", Prediction.class).
					setParameter("year", year).getResultList();
			em.close();
			//Do whatever you want with the subscriber(s) with that username
			//Here we just return the first one
			Prediction pre = new Prediction();
			for(Prediction p:predictions) {
				pre = p;
			}
			return pre;
		}
		
	    public Prediction getPredictionById(int id) {
	        EntityManager em = emf.createEntityManager();
	        Prediction prediction = em.find(Prediction.class, id);
	        em.close();
	        return prediction;
	    }
}
		
	