package app.entities;

public class Orderline {

    private int id;

    private int quantity;

    private int topping_id;

    private int bottom_id;

    private int order_id;

    public Orderline(int id, int quantity, int topping_id, int bottom_id, int order_id) {
        this.id = id;
        this.quantity = quantity;
        this.topping_id = topping_id;
        this.bottom_id = bottom_id;
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getToppings_id() {
        return topping_id;
    }

    public int getBottoms_id() {
        return bottom_id;
    }

    public int getOrder_id() {
        return order_id;
    }
}
