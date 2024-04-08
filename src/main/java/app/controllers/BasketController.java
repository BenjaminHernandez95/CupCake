package app.controllers;

import app.entities.Cart;
import app.entities.Cartline;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class BasketController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("basket", ctx -> ctx.render("basket.html"));
        app.post("deleteCartline", ctx -> deleteCartline(ctx, connectionPool));
        app.post("checkout", ctx -> checkout(ctx, connectionPool));
    }

    /*public static void viewOrders(Context ctx, ConnectionPool connectionPool) {
        try {
            Cart cart = ctx.sessionAttribute("cart");
        }
        catch (NullPointerException e) {
            e.getMessage();
        }

        ctx.render("basket.html");
    }*/

    public static void deleteCartline(Context ctx, ConnectionPool connectionPool) {
        int cartlineID = Integer.parseInt(ctx.formParam("cartline_id"));
        Cart cart = ctx.sessionAttribute("cart");
        cart.removeCartline(cartlineID);
        ctx.sessionAttribute("cart", cart);

        ctx.redirect("basket");

    }

    private static void checkout(Context ctx, ConnectionPool connectionPool) {
        try {
            try {
                User user = ctx.sessionAttribute("currentUser");
                Cart cart = ctx.sessionAttribute("cart");

                int orderID = OrderMapper.addOrder(user.getUserId(),connectionPool);
                ctx.attribute("OrderID",orderID);

                for (Cartline cartline : cart.getCartlines()) {
                    OrderMapper.addOrderline(orderID, cartline.getTopping().getId(), cartline.getBottom().getId(), cartline.getQuantity(), connectionPool);
                }
            } catch (NullPointerException e) {
                e.getMessage();
            }

            ctx.render("checkout.html");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

}
