package example.app.com.ksoap2xmlparseexample;


public class DataAccess {

    public DataAccess(){

    }

    private String product;
    private String price;

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public  void getPrice(String price){
        this.price = price;
    }

    @Override
    public String toString() {
        return product + " -> " + price + " THB";
    }

}
