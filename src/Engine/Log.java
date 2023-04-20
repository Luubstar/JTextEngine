package Engine;

public class Log {
    private String message;
    private int quantity;

    public Log(String m){
        message = m;
        quantity = 1;
    }

    public String getMessage() {return message;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int q){quantity = q;}
    public String getQuantityString(){
        if (quantity > 1){return "x" + quantity;}
        else{return "";}
    }
}
