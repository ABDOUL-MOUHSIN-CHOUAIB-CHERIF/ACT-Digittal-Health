package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertUser {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (username, password_hash, role, first_name, last_name, email, phone, department, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            //CORRECT: Each parameter gets unique index
            ps.setString(1, "ABDOUL MOUHSIN CHOUAIB CHERIF");  // username
            ps.setString(2, "Ch4r1f");                         // password_hash
            ps.setString(3, "DOCTOR");                         // role
            ps.setString(4, "Abdoul Mouhsin");                 // first_name
            ps.setString(5, "Chouaib Cherif");                 // last_name
            ps.setString(6, "abdoulmouhsincherif@gmail.com");  // email
            ps.setString(7, "690480803");                      // phone
            ps.setString(8, "Braintology");                    // department
            ps.setInt(9, 1);                                   // is_active

            ps.executeUpdate();
            System.out.println("✅ User inserted successfully!");
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error inserting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}