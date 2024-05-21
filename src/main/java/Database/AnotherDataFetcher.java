package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnotherDataFetcher {

    public static List<int[]> fetchData() {
        List<int[]> data = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/telegram_bot_db";
        String user = "postgres";
        String password = "123";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT year FROM datayearanother")) {

            while (rs.next()) {
                int[] row = {
                        rs.getInt("col1")
                };
                data.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
