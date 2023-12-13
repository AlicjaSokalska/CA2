package main;

import dao.UserDAO;
import entities.User;

public class Test {

    public static void main(String[] args) {
        // Test User
        testUser();

       
    }

    private static void testUser() {
        User user = new User(0,"tom", "password123");

        // Create UserDAO instance
        UserDAO userDAO = new UserDAO();

        // Persist User
        userDAO.persist(user);

        // Retrieve User by email
        User retrievedUser = userDAO.getUserByUsername("tom");
        if (retrievedUser != null) {
            System.out.println("Retrieved User: " + retrievedUser);
        } else {
            System.out.println("User not found");
        }
    }
}