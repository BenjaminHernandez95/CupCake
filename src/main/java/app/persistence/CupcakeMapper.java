package app.persistence;

import app.entities.Bottom;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.SQLException;

public class CupcakeMapper {

    public static Topping getToppingById(int toppingID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM topping WHERE topping_id = ?";
        try (var connection = connectionPool.getConnection())
        {
            try (var prepareStatement = connection.prepareStatement(sql))
            {
                prepareStatement.setInt(1, toppingID);
                var resultSet = prepareStatement.executeQuery();

                if (resultSet.next())
                {
                    int topping_id = resultSet.getInt("topping_id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    return new Topping(topping_id, name, price);
                } else
                {
                    return null;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database");
        }
    }

    public static Bottom getBottomById(int bottomID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM bottom WHERE bottom_id = ?";
        try (var connection = connectionPool.getConnection())
        {
            try (var prepareStatement = connection.prepareStatement(sql))
            {
                prepareStatement.setInt(1, bottomID);
                var resultSet = prepareStatement.executeQuery();

                if (resultSet.next())
                {
                    int bottom_id = resultSet.getInt("bottom_id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    return new Bottom(bottom_id, name, price);
                } else
                {
                    return null;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database");
        }
    }

}
