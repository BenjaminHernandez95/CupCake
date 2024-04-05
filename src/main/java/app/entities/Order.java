package app.entities;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private int order_id;

    private Date date;

    private int customer_id;

    private ArrayList<Orderline> orderlines;

    public Order(int order_id, Date date, int customer_id) {
        this.order_id = order_id;
        this.date = date;
        this.customer_id = customer_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Date getDate() {
        return date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public ArrayList<Orderline> getOrderlines() {
        return orderlines;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setOrderlines(ArrayList<Orderline> orderlines) {
        this.orderlines = orderlines;
    }

    public void addOrderline(Orderline orderline) {
        this.orderlines.add(orderline);
    }
}
