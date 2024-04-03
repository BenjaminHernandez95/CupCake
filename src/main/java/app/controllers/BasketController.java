package app.controllers;

import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class BasketController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("basket", ctx -> viewOrders(ctx, connectionPool));
        app.post("deleteOrderline", ctx -> deleteOrderline(ctx, connectionPool));
    }

    public static void viewOrders(Context ctx, ConnectionPool connectionPool) {
        try {
            try {
                int currentOrderID = ctx.sessionAttribute("currentOrder");
                ArrayList<Orderline> orderlines = OrderMapper.getOrderlines(currentOrderID,connectionPool);

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

    public static void deleteOrderline(Context ctx, ConnectionPool connectionPool) {
        int orderlineID = Integer.parseInt(ctx.formParam("orderline_id"));
        try {
            OrderMapper.deleteOrderline(orderlineID, connectionPool);
        }
        catch (DatabaseException e) {
            e.getMessage();
        }
        ctx.redirect("basket");

    }

}
