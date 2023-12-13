package resource;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entities.Emission;
import entities.User;
import service.EmissionService;
import service.UserService;

@Path("/users")
public class UserResource {

	 private final UserService userService;
	    private final EmissionService emissionService;

	    public UserResource() {
	        this.userService = new UserService();
	        this.emissionService = new EmissionService();
	    }


	// Basic methods
	@GET
	@Path("/hello")
	@Produces("text/plain")
	public String hello() {
		return "Hello World";
	}

	// View all
	@GET
	@Path("/users")
	@Produces("application/json")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// View one
	@GET
	@Path("/user/{id}")
	@Produces("application/json")
	public User getUser(@PathParam("id") int id) {
		return userService.getUserByID(id);
	}

	// Create
	@POST
	@Path("/user")
	@Consumes("application/json")
	public void createUser(User user) {
		userService.createUser(user);
	}

	// Update
	@PUT
	@Path("/user/{id}")
	@Consumes("application/json")
	public void updateUser(@PathParam("id") int id, User updatedUser) {
		userService.updateUser(id, updatedUser);
	}

	// Delete
	@DELETE
	@Path("/user/delete/{id}")
	public void deleteUser(@PathParam("id") int id) {
		userService.deleteUser(id);
	}

	// By username
	@GET
	@Path("/user/username/{username}")
	@Produces("application/json")
	public User getUserByUsername(@PathParam("username") String username) {
		return userService.getUserByUsername(username);
	}

//reg and login
	@POST
	@Path("/{username}/{password}/register")
	@Produces("application/json")
	public void registerUser(@PathParam("username") String username, @PathParam("password") String password) {
		try {
			userService.registerUser(username, password);
		} catch (IllegalArgumentException e) {
			// Registration failed
			throw e;
		}

	}

	@POST
	@Path("/{username}/{password}/login")
	@Produces("application/json")
	public void loginUser(@PathParam("username") String username, @PathParam("password") String password)
			throws LoginException {

		// Validate
		User user = userService.loginUser(username, password);

		if (user == null) {
			throw new LoginException("Invalid credentials");
		}

	}

	@GET
	@Path("/{username}/{password}/emissions")
	@Produces("application/json")
	public List<Emission> getAllEmissions(
	        @PathParam("username") String username,
	        @PathParam("password") String password) {
	  
	    User user = userService.loginUser(username, password);
	    if (user != null) {
	        return emissionService.getAllEmissions();
	    } else {
	      
	        return Collections.emptyList();
	 
	    }  
	}
	
	@GET
	@Path("/{username}/{password}/emissions/category/{category}")
	@Produces("application/json")
	public Emission getEmissionsByCategory(
	        @PathParam("username") String username,
	        @PathParam("password") String password,
	        @PathParam("category") String category) {
	    // Validate the user, then retrieve emissions by category
	    User user = userService.loginUser(username, password);
	    if (user != null) {
	        return emissionService.getEmissionByCategory(category);
	    } else {
	    
	        return null; 
	    }
	}


}
