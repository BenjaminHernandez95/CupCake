package app.entities;

public class Cartline {


    private Topping topping;

    private Bottom bottom;

    private int quantity;

    public Cartline(Topping topping, Bottom bottom, int quantity) {
        this.topping = topping;
        this.bottom = bottom;
        this.quantity = quantity;
    }

    public int getPrice() {
        return quantity*(topping.getPrice() + bottom.getPrice());
    }

    public Topping getTopping() {
        return topping;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public int getQuantity() {
        return quantity;
    }
}
