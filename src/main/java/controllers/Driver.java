package controllers;

import models.RentalAgreement;
import models.Tool;
import models.ToolPolicy;
import models.ToolStore;

import java.time.LocalDate;

public class Driver {

    public static void main(String args[]){

        // Initialize all tools
        Tool chns = new Tool("CHNS", "Chainsaw", "Stihl");
        Tool ladw = new Tool("LADW", "Ladder", "Werner");
        Tool jakd = new Tool("JAKD", "Jackhammer", "DeWalt");
        Tool jakr = new Tool("JAKR", "Jackhammer", "Ridgid");

        // Initialize all tool policies
        ToolPolicy ladderPolicy = new ToolPolicy(1.99, true, true, false);
        ToolPolicy chainsawPolicy = new ToolPolicy(1.49, true, false, true);
        ToolPolicy jackhammerPolicy = new ToolPolicy(2.99, true, false, false);

        // Initialize the tool store
        ToolStore myToolStore = new ToolStore();

        try {
            // Add all tools to tool store inventory
            myToolStore.addToolToInventory(chns);
            myToolStore.addToolToInventory(ladw);
            myToolStore.addToolToInventory(jakd);
            myToolStore.addToolToInventory(jakr);

            // Add policies for tool types
            myToolStore.addPolicyForToolType("Chainsaw", chainsawPolicy);
            myToolStore.addPolicyForToolType("Ladder", ladderPolicy);
            myToolStore.addPolicyForToolType("Jackhammer", jackhammerPolicy);

            // Checkout
            RentalAgreement myRentalAgreement = myToolStore.checkout("chns", 21, 10, LocalDate.of(2022,4,16));

            // Print formatted values from the generated rental agreement
            myRentalAgreement.printAll();

        } catch (Exception e){
            System.out.println("An Exception has occurred: " + e.getMessage());
        }
    }
}
