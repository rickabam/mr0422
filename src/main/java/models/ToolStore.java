package models;

import java.security.Policy;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ToolStore {

    private ArrayList<Tool> inventory;
    private HashMap<String, ToolPolicy> policies;

    public ToolStore() {
        this.inventory = new ArrayList<Tool>();
        this.policies = new HashMap<String, ToolPolicy>();
    }

    public ArrayList<Tool> getInventory(){
        return this.inventory;
    }

    public Tool getToolFromInventory(String toolCodeToFind) throws Exception{
        for (Tool tool : this.inventory){
            if (toolCodeToFind.equalsIgnoreCase(tool.getToolCode()))
                return tool;
        }
        throw new Exception("Tool with provided Tool Code not found!");
    }

    public HashMap<String, ToolPolicy> getPolicies(){
        return this.policies;
    }

    public ToolPolicy getPolicyForToolType(String toolTypeToFind) throws Exception{
        for (String toolTypeForPolicy : this.policies.keySet()){
            if (toolTypeForPolicy.equals(toolTypeToFind)){
                return this.policies.get(toolTypeToFind);
            }
        }
        throw new Exception("Policy for provided Tool Type not found!");
    }

    public static boolean containsToolCode(ArrayList<Tool> inventoryToCheck, String toolCodeToCheck){

        for (Tool tool : inventoryToCheck){
            if (tool.getToolCode().equals(toolCodeToCheck)){
                return true;
            }
        }
        return false;
    }

    public void addToolToInventory(Tool newTool) throws Exception{

         if (!ToolStore.containsToolCode(this.inventory, newTool.getToolCode()))
            this.inventory.add(newTool);
         else
             throw new Exception("Tool not added to inventory! Each tool added should have a unique tool code.");
    }

    public void addPolicyForToolType(String newToolTypeForPolicy, ToolPolicy newPolicy){

        this.policies.put(newToolTypeForPolicy, newPolicy);

    }

    public boolean isIndependenceDay(LocalDate providedDate){
        // Determine if Independence Day falls on a weekend for the given year and should be observed the day before or day after
        int offset = 0;
        if (LocalDate.of(providedDate.getYear(), 7, 4).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).equalsIgnoreCase("SATURDAY")){
            offset = -1;
        } else if (LocalDate.of(providedDate.getYear(), 7, 4).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).equalsIgnoreCase("SUNDAY")){
            offset = 1;
        }

        // Determine if the date passed in is in the month of July and is on the day of July that Independence Day is observed for the given year
        if (providedDate.getMonthValue() == 7 && providedDate.getDayOfMonth() == (4 + offset)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLaborDay(LocalDate providedDate){
        // Determine if the date passed in is in the month of September
        if (providedDate.getMonthValue() == 9) {
            // Determine if the date passed in is Monday
            if (providedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).equalsIgnoreCase("MONDAY")){
                // Since Monday occurs once per seven day cycle, determine if the previous Monday falls before the first of the given month
                if ( (providedDate.getDayOfMonth() - 7) <= 0 ){
                    // If all the above are true, the date passed in is Labor Day
                    return true;
                }
            }
        }
        // If any of the above are false, the date passed in is not Labor Day
        return false;
    }

    public boolean isHoliday(LocalDate providedDate){
        if (isIndependenceDay(providedDate) || isLaborDay(providedDate))
            return true;
        else
            return false;
    }

    public boolean isWeekend(LocalDate providedDate){
        if (providedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).equalsIgnoreCase("SATURDAY")
                || providedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).equalsIgnoreCase("SUNDAY")) {
            return true;
        } else
            return false;
    }

    public int calculateTotalBillableDays(String providedToolType, LocalDate providedCheckoutDate, int providedRentalDays) throws Exception{

        // Declare variables, initialize where appropriate
        int sumOfBillableDays = 0;
        LocalDate dateToCheck = providedCheckoutDate;
        boolean isHoliday;
        boolean isWeekend;

        // Iterate through each day of the proposed rental agreement
        for (int dayCount = 0; dayCount < providedRentalDays; dayCount++){

            // Reset holiday and weekend flag at the start of each iteration based on methods defined above
            isHoliday = isHoliday(dateToCheck);
            isWeekend = isWeekend(dateToCheck);

            // Retrieve the appropriate policy for the tool type
            ToolPolicy policyToCheck = this.getPolicyForToolType(providedToolType);

            // Add a billable day if the policy is to charge on the type of day determined
            if (policyToCheck.isHolidayCharge() && isHoliday){
                sumOfBillableDays++;
            } else if (policyToCheck.isWeekendCharge() && isWeekend){
                sumOfBillableDays++;
            } else if (policyToCheck.isWeekdayCharge() && !isHoliday && !isWeekend) {
                sumOfBillableDays++;
            }

            // Set the date to check to the next day at the end of each iteration
            dateToCheck = dateToCheck.plusDays(1);
        }

        return sumOfBillableDays;
    }

    public RentalAgreement checkout(String providedToolCode, int providedRentalDays, int providedDiscountPercent, LocalDate providedCheckoutDate) throws Exception{

        // Provided Data Validation
        if (providedRentalDays < 1){
            throw new Exception("Checkout failed! A tool cannot be rented for less than one day.");
        } else if (providedDiscountPercent < 0 || providedDiscountPercent > 100) {
            throw new Exception("Checkout failed! A discount percentage can only be within the range of 0 to 100 (inclusive).");
        }

        // Declare and calculate derived fields
        String derivedToolType = this.getToolFromInventory(providedToolCode).getToolType();
        String derivedToolBrand = this.getToolFromInventory(providedToolCode).getBrand();
        LocalDate derivedDueDate = providedCheckoutDate.plusDays(providedRentalDays);
        double derivedDailyRentalCharge = this.getPolicyForToolType(derivedToolType).getDailyCharge();
        int derivedTotalBillableDays = this.calculateTotalBillableDays(derivedToolType, providedCheckoutDate, providedRentalDays);
        double derivedGrossCharge = derivedDailyRentalCharge * derivedTotalBillableDays;
        double derivedDiscountAmount = derivedGrossCharge * ((double)providedDiscountPercent / 100);
        double roundedDiscountAmount = Math.round(derivedDiscountAmount * 100) / (double)100;
        double derivedFinalCharge = derivedGrossCharge - roundedDiscountAmount;

        // Construct the new Rental Agreement and return it
        return new RentalAgreement(providedToolCode,
                derivedToolType,
                derivedToolBrand,
                providedRentalDays,
                providedCheckoutDate,
                derivedDueDate,
                derivedDailyRentalCharge,
                derivedTotalBillableDays,
                derivedGrossCharge,
                providedDiscountPercent,
                roundedDiscountAmount,
                derivedFinalCharge);



    }


}
