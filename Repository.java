import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:hardware.db";

    public Repository() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            initializeDatabase();
            seedData();
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e.getMessage());
        }
    }

    private void initializeDatabase() throws SQLException {
        String createTable = """
            CREATE TABLE IF NOT EXISTS hardware (
                id      INTEGER PRIMARY KEY,
                name    TEXT    NOT NULL,
                spec    INTEGER NOT NULL,
                type    TEXT    NOT NULL
            );
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTable);
        }
    }

    private void seedData() throws SQLException {
        String checkEmpty = "SELECT COUNT(*) FROM hardware";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkEmpty)) {
            if (rs.next() && rs.getInt(1) > 0) return; // already seeded
        }

        String insert = "INSERT INTO hardware (id, name, spec, type) VALUES (?, ?, ?, ?)";
        Object[][] data = {
                {1,  "Dell XPS 13",     16, "Laptop"},
                {2,  "Samsung S24",     50, "Phone"},
                {3,  "MacBook Pro",     32, "Laptop"},
                {4,  "iPhone 15",       48, "Phone"},
                {5,  "ASUS Zenbook",    16, "Laptop"},
                {6,  "Google Pixel 8",  50, "Phone"},
                {7,  "Lenovo Legion",   32, "Laptop"},
                {8,  "Huawei P60",      48, "Phone"},
                {9,  "HP Spectre",      16, "Laptop"},
                {10, "Sony Xperia",     16, "Phone"}
        };

        try (PreparedStatement pstmt = connection.prepareStatement(insert)) {
            for (Object[] row : data) {
                pstmt.setInt(1, (int) row[0]);
                pstmt.setString(2, (String) row[1]);
                pstmt.setInt(3, (int) row[2]);
                pstmt.setString(4, (String) row[3]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public List<Hardware> getAllHardware() {
        List<Hardware> list = new ArrayList<>();
        String query = "SELECT * FROM hardware";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Hardware hw = mapRow(rs);
                if (hw != null) list.add(hw);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hardware: " + e.getMessage());
        }
        return list;
    }

    private Hardware mapRow(ResultSet rs) throws SQLException {
        int    id   = rs.getInt("id");
        String name = rs.getString("name");
        int    spec = rs.getInt("spec");
        String type = rs.getString("type");

        return switch (type.toLowerCase()) {
            case "laptop" -> new Laptop(id, name, spec);
            case "phone"  -> new Phone(id, name, spec);
            default       -> null;
        };
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}