package app.controllers;

import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;

public class AdminController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("adminView", ctx -> getAllOrders(ctx, connectionPool));
        app.post("viewOrder", ctx -> getAllOrderlines(ctx, connectionPool));
        app.post("deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
        app.post("deleteOrderline", ctx -> deleteOrderline(ctx, connectionPool));
    }

    public static void getAllOrders(Context ctx, ConnectionPool connectionPool) {

        ArrayList<Order> orders = new ArrayList<>();
        try {
            orders = OrderMapper.getOrders(connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        ctx.sessionAttribute("orders", orders);
        ctx.render("adminView.html");
    }

    public static void getAllOrderlines(Context ctx, ConnectionPool connectionPool) {


        ArrayList<Orderline> orderlines = new ArrayList<>();
        int orderlineID = Integer.parseInt(ctx.formParam("orderID"));

        try {
            orderlines = OrderMapper.getOrderlines(orderlineID, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        ctx.attribute("orderlines", orderlines);
        ctx.render("adminViewOrder.html");
    }

    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        int orderID = Integer.parseInt(ctx.formParam("orderID"));

        try {
            OrderMapper.deleteOrder(orderID, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        ctx.redirect("adminView");
    }

    public static void deleteOrderline(Context ctx, ConnectionPool connectionPool) {
        int orderlineID = Integer.parseInt(ctx.formParam("orderlineID"));

        try {
            OrderMapper.deleteOrderlineByID(orderlineID, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        ctx.redirect("viewOrder");
    }


}
