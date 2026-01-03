import java.sql.*;

public class UserService {

    public static int getOrCreateUser(String name) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT id FROM users WHERE name = ?");
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("\nWelcome back, " + name );
            return rs.getInt("id");
        }

        ps = con.prepareStatement(
                "INSERT INTO users(name) VALUES(?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        rs.next();

        System.out.println("\nNice to meet you, " + name);
        return rs.getInt(1);
    }
}
