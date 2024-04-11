package app.persistence;

import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderMapper {

    public static ArrayList<Orderline> getOrderlines(int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from orderline where order_id=?";

        ArrayList<Orderline> orderlines = new ArrayList<>();

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("orderline_id");
                int quantity = rs.getInt("quantity");
                int topping_id = rs.getInt("topping_id");
                int bottom_id = rs.getInt("bottom_id");
                int order_id = rs.getInt("order_id");

                orderlines.add(new Orderline(id, quantity, topping_id, bottom_id, order_id));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
        return orderlines;
    }

    public static ArrayList<Order> getOrders(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from orders";

        ArrayList<Order> orders = new ArrayList<>();

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                Date date = rs.getDate("date");
                int customer_id = rs.getInt("customer_id");

                orders.add(new Order(order_id, date, customer_id));
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
        return orders;
    }

    public static void deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orders where order_id = ?";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en task");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void deleteOrderlineByID(int orderlineID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orderline where orderline_id = ?";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderlineID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en task");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void deleteOrderlineByOrderID(int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from orderline where order_id = ?";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en task");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void addOrderline(int orderID, int toppingID, int bottomID, int quantity, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orderline (order_id, topping_id, bottom_id, quantity) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ps.setInt(2, toppingID);
            ps.setInt(3, bottomID);
            ps.setInt(4, quantity);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i tilføjelse af en ordrelinje");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
    }

    public static int addOrder(int customerID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orders (date, customer_id) VALUES (?, ?)";

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, customerID);
            ps.executeUpdate();

            ResultSet keyset = ps.getGeneratedKeys();

            if (keyset.next()) {
                int order_id = keyset.getInt(1);
                return order_id;
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return 0;
    }

}
