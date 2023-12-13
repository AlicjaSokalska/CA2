package dao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import entities.User;

import java.util.List;

@Transactional
public class UserDAO {
  
	protected static EntityManagerFactory emf = 
	 		Persistence.createEntityManagerFactory("GHGEmission"); 	
	
	public UserDAO() {
		
	}
	
	public void persist(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(user));
		em.getTransaction().commit();
		em.close();
	}
	
	public User merge(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User updatedUser = em.merge(user);
		em.getTransaction().commit();
		em.close();
		return updatedUser;
	}
	
	public List<User> getAllUsers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<User> usersFromDB  = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return usersFromDB;
	}

	public User getUserByUsername(String username){
		EntityManager em = emf.createEntityManager();
		List<User> users = (List<User>) 
				em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class).
				setParameter("username", username).getResultList();
		em.close();
		//Do whatever you want with the subscriber(s) with that username
		//Here we just return the first one
		User use = new User();
		for(User u:users) {
			use = u;
		}
		return use;
	}
	


	   public User getUserByID(int id) {
	        EntityManager em = emf.createEntityManager();
	        User user = em.find(User.class, id);
	        em.close();
	        return user;
	    }
	   
	  
	   public User registerUser(String username, String password) {
	        EntityManager em = emf.createEntityManager();
	        EntityTransaction transaction = em.getTransaction();

	        try {
	            transaction.begin();

	            // Check if the user already exists
	            List<User> existingUsers = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
	                    .setParameter("username", username)
	                    .getResultList();

	            if (!existingUsers.isEmpty()) {
	                // User already exists, throw an exception or handle it as needed
	                throw new IllegalArgumentException("Username already exists");
	            }

	            // Create a new User entity
	            User newUser = new User();
	            newUser.setUsername(username);
	            newUser.setPassword(password);

	            // Persist the new user
	            em.persist(newUser);

	            transaction.commit();

	            return newUser;
	        } finally {
	            if (transaction.isActive()) {
	                transaction.rollback();
	            }
	            em.close();
	        }
	    }

	   
	 
	   public User loginUser(String username, String password) {
	        EntityManager em = emf.createEntityManager();
	        try {
	            List<User> users = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
	                    .setParameter("username", username)
	                    .setParameter("password", password)
	                    .getResultList();

	            return users.isEmpty() ? null : users.get(0);
	        } finally {
	            em.close();
	        }
	    }

	    public void saveUser(User user) {
	        persist(user);
	    }
	    
	    
	 
	}







