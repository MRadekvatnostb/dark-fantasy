package data;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import heroes.*;
import logic.Player;
import utils.GameItem;
import utils.ListItem;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    // Данные для подключения (проверь пароль!)
    private static String url;
    private static String user;
    private static String password;

    static {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public static void createTableIfNotExists() {
        String sqlPlayer = "CREATE TABLE IF NOT EXISTS players (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "class_type VARCHAR(20), " +
                "hp INTEGER DEFAULT 100, " +
                "max_hp INTEGER DEFAULT 150, " +
                "min_dmg INTEGER, " +
                "max_dmg INTEGER, " +
                "armor INTEGER DEFAULT 0, " +
                "crit_chance INTEGER DEFAULT 0, " +
                "vampirism INTEGER DEFAULT 0, " +
                "dodge INTEGER DEFAULT 0, " +
                "energy INTEGER DEFAULT 0, " +
                "current_location VARCHAR(255) DEFAULT 'Начало', " +
                "experience INTEGER DEFAULT 0, " +
                "money INTEGER DEFAULT 0" +
                ")";
        String sqlInventory = "CREATE TABLE IF NOT EXISTS inventory (" +
                "id SERIAL PRIMARY KEY, " +
                "player_id INTEGER REFERENCES players(id) ON DELETE CASCADE, " +
                "item_name VARCHAR(100) NOT NULL)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // execute() подходит для любых команд создания (DDL)
            stmt.execute(sqlPlayer); // Создаем игроков
            stmt.execute(sqlInventory); // Создаем инвентарь
            System.out.println(">>> Проверка структуры БД: таблица players готова к работе.");

        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    private static void saveInventory(int playerId, ArrayList<GameItem> inventory) {
        // Чтобы не дублировать шмотки при каждом сохранении, сначала чистим старый рюкзак этого игрока
        String deleteSql = "DELETE FROM inventory WHERE player_id = ?";
        String insertSql = "INSERT INTO inventory (player_id, item_name) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            // Чистим
            try (PreparedStatement delPstmt = conn.prepareStatement(deleteSql)) {
                delPstmt.setInt(1, playerId);
                delPstmt.executeUpdate();
            }
            // Записываем текущие шмотки
            try (PreparedStatement insPstmt = conn.prepareStatement(insertSql)) {
                for (GameItem item : inventory) {
                    insPstmt.setInt(1, playerId);
                    insPstmt.setString(2, item.getName());
                    insPstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения рюкзака: " + e.getMessage());
        }
    }

    // Твой первый метод: Сохранение игрока
    public static void savePlayer(Player player) {
        String sql;
        // Если ID уже есть, значит герой существует — ОБНОВЛЯЕМ
        if (player.getId() > 0) {
            sql = "UPDATE players SET name=?, class_type=?, hp=?, max_hp=?, min_dmg=?, max_dmg=?, " +
                    "armor=?, dodge=?, crit_chance=?, vampirism=?, current_location=?, experience=?, money=? " +
                    "WHERE id = ?";
        } else {
            // Если ID нет (0) — создаем НОВУЮ запись
            sql = "INSERT INTO players (name, class_type, hp, max_hp, min_dmg, max_dmg, armor, " +
                    "dodge, crit_chance, vampirism, current_location, experience, money) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Заполняем общие данные (1-13)
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getClass().getSimpleName().toUpperCase());
            pstmt.setInt(3, player.getHp());
            pstmt.setInt(4, player.getMaxHp());
            pstmt.setInt(5, player.getMinDamage());
            pstmt.setInt(6, player.getMaxDamage());
            pstmt.setInt(7, player.getArmor());
            pstmt.setInt(8, player.getDodge());
            pstmt.setInt(9, player.getCritChance());
            pstmt.setInt(10, player.getVampirism());
            pstmt.setString(11, player.getCurrentLocation());
            pstmt.setInt(12, player.getExperience());
            pstmt.setInt(13, player.getMoney());

            if (player.getId() > 0) {
                // Для UPDATE добавляем ID в самый конец (14-й параметр)
                pstmt.setInt(14, player.getId());
                pstmt.executeUpdate();
                // Шмотки тоже обновляем (удаляем старые и пишем новые)
                saveInventory(player.getId(), player.showInventory(), Integer);
            } else {
                // Для INSERT получаем новый ID
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        player.setId(newId);
                        saveInventory(newId, player.getInventory());
                    }
                }
            }

            System.out.println(">>> Данные " + player.getName() + " синхронизированы (ID: " + player.getId() + ")");

        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
    private static void loadInventoryForPlayer(Player player) {
        String sql = "SELECT item_name FROM inventory WHERE player_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, player.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("item_name");
                // Тебе нужен метод в utils.ListItem, который создает предмет по имени!
                player.addItem(ListItem.createItemByName(name));
            }
        } catch (SQLException e) { System.out.println("Ошибка инвентаря: " + e.getMessage()); }
    }
    public static Player loadLastPlayer() {
        // Выбираем всё из таблицы, сортируем по ID (последний созданный будет сверху) и берем только 1 строку
        String sql = "SELECT * FROM players ORDER BY id DESC LIMIT 1";

        // Открываем коннект и готовим запрос
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // executeQuery используется для SELECT

            // rs.next() — это команда "прыгнуть на первую строку результата"
            if (rs.next()) {
                int playerId = rs.getInt("id");
                String type = rs.getString("class_type");
                Player player = null;

                // Воскрешаем нужный класс в зависимости от того, что написано в базе
                switch (type) {
                    case "KNIGHT": player = new Knight(); break;
                    case "VAMPIRE": player = new Vampire(); break;
                    case "ARCHER": player = new Archer(); break;
                    case "WIZARD": player = new Wizard(); break;
                    case "UNDEATH": player = new Undeath(); break;
                }

                if (player != null) {
                    player.setId(playerId);
                    player.setName(rs.getString("name"));
                    player.setHp(rs.getInt("hp"));
                    player.setMinDamage(rs.getInt("min_dmg"));
                    player.setMaxHp(rs.getInt("max_hp"));
                    player.setArmor(rs.getInt("armor"));
                    player.setMaxDamage(rs.getInt("max_dmg"));
                    player.setDodge(rs.getInt("dodge"));
                    player.setVampirism(rs.getInt("vampirism"));
                    player.setCritChance(rs.getInt("crit_chance"));
                    player.setCurrentLocation(rs.getString("current_location"));

                    System.out.println(">>> " + player.getName() + " успешно воскрешен из Бездны!");
                    loadInventoryForPlayer(player);
                    return player;
                }
            } else {
                System.out.println(">>> В Бездне пока пусто. Некого воскрешать.");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
        return null;
    }
}