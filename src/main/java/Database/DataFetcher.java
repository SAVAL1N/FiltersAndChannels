package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher {

    public static List<int[]> fetchData() {
        List<int[]> data = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/telegram_bot_db";
        String user = "postgres";
        String password = "123";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT year2023, year2024, year2025 FROM datayear")) {

            while (rs.next()) {
                int[] row = {
                        rs.getInt("year2023"),
                        rs.getInt("year2024"),
                        rs.getInt("year2025")
                };
                data.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}

