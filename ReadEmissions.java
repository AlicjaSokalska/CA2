package Parser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import entities.Emission;
import service.EmissionService;

//exact 2023----> emission// json


public class ReadEmissions {

    public static void main(String[] args) throws IOException, JSONException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("GreenhouseGasEmissions2023.json")));

        JSONObject jsonObject = new JSONObject(jsonContent);

        // Extract data node
        JSONObject dataNode = jsonObject.getJSONObject("data");
        String date = dataNode.getString("date");
        String country = dataNode.getString("country");

        System.out.println("Date: " + date);
        System.out.println("Country: " + country);

        // Extract emissions array
        JSONArray emissionsArray = jsonObject.getJSONArray("Emissions");

        int entryCount = 0; // Counter for entries

        // Create an instance of the EmissionService to interact with the database
        EmissionService emissionService = new EmissionService();

        for (int i = 0; i < emissionsArray.length(); i++) {
            JSONObject emissionJson = emissionsArray.getJSONObject(i);
            entryCount++; // Increment the entry count

            String category = emissionJson.getString("Category");
            String gasUnits = emissionJson.getString("Gas Units");
            double value = emissionJson.getDouble("Value");
            // Assuming the year is present in the JSON

            // Create an Emission entity
            Emission emission = new Emission();
            emission.setCategory(category);
            emission.setGasUnit(gasUnits);
            emission.setValue(value);
       

            // Save the Emission entity to the database
            emissionService.createEmission(emission);

            System.out.println("\nCategory: " + category);
            System.out.println("Gas Units: " + gasUnits);
            System.out.println("Value: " + value);
        
        }

        System.out.println("\nTotal Entries: " + entryCount);
    }
}
