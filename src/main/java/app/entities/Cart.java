package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Cartline> cartlines = new ArrayList<>();


    public void add(Topping topping, Bottom bottom, int quantity) {
        Cartline cartline = new Cartline(topping, bottom, quantity);
        cartlines.add(cartline);
    }

    public List<Cartline> getCartlines() {
        return cartlines;
    }

    public int cartCount() {
        return cartlines.size();
    }

    public int getTotalPrice() {
        int sum = 0;

        for (Cartline cartline : cartlines) {
            sum += cartline.getPrice();
        }

        return sum;
    }

    public void removeCartline(int cartlineID) {
        cartlines.remove(cartlineID - 1);
    }

}
