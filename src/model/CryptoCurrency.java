package model;

public class CryptoCurrency {
    private String name;
    private int basePrice;
    private int minPrice;
    private int maxPrice;
    private String imgFilePath;
    private double volatility;
    public CryptoCurrency(String name){
        this.name = name;
    };

    @Override
    public String toString(){
        return "Name: " + name;
    }

}
