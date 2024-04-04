package app.controllers;

import app.entities.Orderline;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderlineMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;

public class BuyPageController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("order", ctx -> addOrder(ctx, connectionPool));
        app.post("checkout", ctx -> checkout(ctx, connectionPool));
    }

    private static void checkout(Context ctx, ConnectionPool connectionPool) {
        try {
            try {
                int currentOrderID = ctx.sessionAttribute("currentOrder");
                ArrayList<Orderline> orderlines = OrderlineMapper.getOrderlines(currentOrderID,connectionPool);

                ctx.attribute("orderlines",orderlines);

            }
            catch (NullPointerException e) {
                e.getMessage();
            }

            ctx.render("basket.html");
        }

        catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addOrder(Context ctx, ConnectionPool connectionPool) {
        int toppingID = Integer.parseInt(ctx.formParam("Topping"));
        int bottomID = Integer.parseInt(ctx.formParam("Bottom"));
        int quantity = Integer.parseInt(ctx.formParam("Quantity"));
        User user = ctx.sessionAttribute("currentUser");


        try {
            OrderlineMapper.addOrderline(user.getUserId(), toppingID, bottomID, quantity, connectionPool);
        }
        catch (DatabaseException e) {
            e.getMessage();
        }
        ctx.redirect("buyPage");

    }
}

