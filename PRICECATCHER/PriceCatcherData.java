package PRICECATCHER;

public class PriceCatcherData {
    //instance variable
    private String date;
    private String premise_code;
    private String item_code;
    private double price;

    //constructor
    public PriceCatcherData(String date, String premiseCode, String itemCode, double price){
        this.date = date;
        this.premise_code = premiseCode;
        this.item_code = itemCode;
        this.price = price;
    }

    //mutator
    public void setDate(String date){
        this.date = date;
    }

    public void setPremiseCode(String premiseCode){
        this.premise_code = premiseCode;
    }

    public void setItemCode(String itemCode){
        this.item_code = itemCode;
    }

    public void setPrice(double price){
        this.price = price;
    }

    //accessor
    public String getDate(){
        return date;
    }

    public String getPremiseCode(){
        return premise_code;
    }

    public String getItemCode(){
        return item_code;
    }

    public double getPrice(){
        return price;
    }
}

