package models;

public class Tool {

    private final String toolCode;
    private final String toolType;
    private final String brand;

    public Tool(String newToolCode, String newToolType, String newToolBrand) {

        this.toolCode = newToolCode;
        this.toolType = newToolType;
        this.brand = newToolBrand;

    }

    public String getToolCode(){
        return this.toolCode;
    }

    public String getToolType(){
        return this.toolType;
    }

    public String getBrand(){
        return this.brand;
    }
}
