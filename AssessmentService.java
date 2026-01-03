import java.sql.*;

public class AssessmentService {

    public static void saveAssessment(int userId, String mindset, int score) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO assessments(user_id, mindset, score) VALUES(?,?,?)");
        ps.setInt(1, userId);
        ps.setString(2, mindset);
        ps.setInt(3, score);

        ps.executeUpdate();
    }

    public static Integer getLastScore(int userId) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT score FROM assessments WHERE user_id = ? ORDER BY created_at DESC LIMIT 1");
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("score");
        }
        return null;
    }
}

