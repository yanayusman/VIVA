package PRICECATCHER;

public class Premise {
    //instance variable
    private String premise_code;
    private String premise;
    private String address;
    private String premise_type;
    private String state;
    private String district;

    //constructor
    public Premise(String premiseCode, String premise, String address, String premiseType, String state, String district){
        this.premise_code = premiseCode;
        this.premise = premise;
        this.address = address;
        this.premise_type = premiseType;
        this.state = state;
        this.district = district;
    }

    //mutator
    public void setPremiseCode(String premiseCode){
        this.premise_code = premiseCode;
    }

    public void setPremise(String premise){
        this.premise = premise;
    }

    public void setAdress(String address){
        this.address = address;
    }

    public void setPremiseType(String premiseType){
        this.premise_type = premiseType;
    }

    public void setState(String state){
        this.state = state;
    }

    public void setDistrict(String district){
        this.district = district;
    }

    //accessor
    public String getPremiseCode(){
        return premise_code;
    }

    public String getPremise(){
        return premise;
    }

    public String getAddress(){
        return address;
    }

    public String getPremiseType(){
        return premise_type;
    }

    public String getState(){
        return state;
    }

    public String getDistrict(){
        return district;
    }
}

