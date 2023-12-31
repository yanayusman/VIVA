package PRICECATCHER;

public class Item {
    //instance variable
    private String item_code;
    private String item;
    private String unit;
    private String item_group;
    private String item_category;

    //constructor
    public Item(String itemCode, String item, String unit, String itemGroup, String itemCategory){
        this.item_code = itemCode;
        this.item = item;
        this.unit = unit;
        this.item_group = itemGroup;
        this.item_category = itemCategory;
    }

    //mutator
    public void setItemCode(String itemCode){
        this.item_code = itemCode;
    }

    public void setItem(String item){
        this.item = item;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setItemGroup(String itemGroup){
        this.item_group = itemGroup;
    }

    public void setItemCategory(String itemCategory){
        this.item_category = itemCategory;
    }

    //accessor
    public String getItemCode(){
        return item_code;
    }

    public String getItem(){
        return item;
    }

    public String getUnit(){
        return unit;
    }

    public String getItemGroup(){
        return item_group;
    }

    public String getItemCategory(){
        return item_category;
    }
}
