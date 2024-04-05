package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;

public class BuyPageController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("addOrderline", ctx -> addToCart(ctx, connectionPool));
        app.get("buyPage", ctx -> ctx.render("buyPage.html"));
    }

    private static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int toppingID = Integer.parseInt(ctx.formParam("topping"));
        int bottomID = Integer.parseInt(ctx.formParam("bottom"));
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        Cart cart = ctx.sessionAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        try {
            Topping top = CupcakeMapper.getToppingById(toppingID, connectionPool);
            Bottom bot = CupcakeMapper.getBottomById(bottomID, connectionPool);
            cart.add(top, bot, quantity);
        }
        catch (DatabaseException e) {
            e.getMessage();
        }

        ctx.sessionAttribute("cart",cart);
        ctx.redirect("buyPage");

    }
}

