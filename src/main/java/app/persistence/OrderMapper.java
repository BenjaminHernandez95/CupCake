package app.persistence;

import app.entities.Orderline;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper {

    public static ArrayList<Orderline> getOrderlines (int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from orderline where order_id=?";

        ArrayList<Orderline> orderlines = new ArrayList<>();

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int quantity = rs.getInt("quantity");
                int topping_id = rs.getInt("topping_id");
                int bottom_id = rs.getInt("bottom_id");
                int order_id = rs.getInt("order_id");

                orderlines.add(new Orderline(id, quantity, topping_id, bottom_id, order_id));
            }
            else {
                throw new DatabaseException("Fejl i hentning af orders, prøv igen");
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
        return orderlines;
    }

    public static void deleteOrderline(int orderlineID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orderline where order_id=?";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderlineID);

            ResultSet rs = ps.executeQuery();
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af en task");
            }

        }
        catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

}
