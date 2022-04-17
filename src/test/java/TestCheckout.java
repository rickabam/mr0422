import models.RentalAgreement;
import models.Tool;
import models.ToolPolicy;
import models.ToolStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCheckout {

    private ToolStore testToolStore;
    private RentalAgreement testRentalAgreement;

    @Before
    public void initializeTestStore(){

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
        testToolStore = new ToolStore();

        try {
            // Add all tools to tool store inventory
            testToolStore.addToolToInventory(chns);
            testToolStore.addToolToInventory(ladw);
            testToolStore.addToolToInventory(jakd);
            testToolStore.addToolToInventory(jakr);

            // Add policies for tool types
            testToolStore.addPolicyForToolType("Chainsaw", chainsawPolicy);
            testToolStore.addPolicyForToolType("Ladder", ladderPolicy);
            testToolStore.addPolicyForToolType("Jackhammer", jackhammerPolicy);

        } catch (Exception e){
            System.out.println("An Exception has occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase1(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.assertEquals("Checkout failed! A discount percentage can only be within the range of 0 to 100 (inclusive).", e.getMessage());
        }
    }

    @Test
    public void testCase2(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testCase3(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testCase4(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testCase5(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testCase6(){
        try {
            RentalAgreement testRentalAgreement = testToolStore.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
            testRentalAgreement.printAll();
        } catch (Exception e){
            Assert.fail();
        }
    }

}
