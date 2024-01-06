package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Score {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getDetails(int id, int semesterNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? and semester = ?");
            ps.setInt(1, id);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField14.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField12.setText(String.valueOf(rs.getInt(3)));
                Home.jTextSubject1.setText(rs.getString(4));
                Home.jTextSubject2.setText(rs.getString(5));
                Home.jTextSubject3.setText(rs.getString(6));
                Home.jTextSubject4.setText(rs.getString(7));
                Home.jTextSubject5.setText(rs.getString(8));
                Home.jTextSubject6.setText(rs.getString(9));
                Home.jTextSubject7.setText(rs.getString(10));
                Home.jTextSubject8.setText(rs.getString(11));
                Home.jTextSubject9.setText(rs.getString(12));
                Home.jTextSubject10.setText(rs.getString(13));

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student ID or Form doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("select * from score where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isSidSemasterNoExist(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("select * from score where student_id = ? and semester=?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int id, int sid, int semester, String subject1, String subject2,
        String subject3, String subject4, String subject5, String subject6, String subject7,
        String subject8, String subject9, String subject10, double score1, double score2, double score3,
        double score4, double score5, double score6, double score7, double score8, double score9, double score10, String grade) {

   
        String sql = "insert into score values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, subject1);
            ps.setDouble(5, score1);
            ps.setString(6, subject2);
            ps.setDouble(7, score2);
            ps.setString(8, subject3);
            ps.setDouble(9, score3);
            ps.setString(10, subject4);
            ps.setDouble(11, score4);
            ps.setString(12, subject5);
            ps.setDouble(13, score5);
            ps.setString(14, subject6);
            ps.setDouble(15, score6);
            ps.setString(16, subject7);
            ps.setDouble(17, score7);
            ps.setString(18, subject8);
            ps.setDouble(19, score8);
            ps.setString(20, subject9);
            ps.setDouble(21, score9);
            ps.setString(22, subject10);
            ps.setDouble(23, score10);
            ps.setString(24, grade);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score added successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getScoreValues(JTable table, String searchValue) {
        String sql = "SELECT * FROM score WHERE CONCAT(id, student_id, semester) LIKE ? ORDER BY id DESC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel(); // Assuming the table already has a model assigned

            while (rs.next()) {
                Object[] row = new Object[24];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getString(14);
                row[14] = rs.getDouble(15);
                row[15] = rs.getString(16);
                row[16] = rs.getDouble(17);
                row[17] = rs.getString(18);
                row[18] = rs.getDouble(19);
                row[19] = rs.getString(20);
                row[20] = rs.getDouble(21);
                row[21] = rs.getString(22);
                row[22] = rs.getDouble(23);
                row[23] = rs.getString(24);
                model.addRow(row); // Add each row to the model
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, double score1, double score2, double score3, double score4, double score5,
                   double score6, double score7, double score8, double score9, double score10, String grade) {
    String sql = "UPDATE score SET score1=?, score2=?, score3=?, score4=?, score5=?, " +
                 "score6=?, score7=?, score8=?, score9=?, score10=?, grade=? WHERE id=?";
    try {
        ps = con.prepareStatement(sql);
        ps.setDouble(1, score1);
        ps.setDouble(2, score2);
        ps.setDouble(3, score3);
        ps.setDouble(4, score4);
        ps.setDouble(5, score5);
        ps.setDouble(6, score6);
        ps.setDouble(7, score7);
        ps.setDouble(8, score8);
        ps.setDouble(9, score9);
        ps.setDouble(10, score10);
        ps.setString(11, grade);
        ps.setInt(12, id);

        if (ps.executeUpdate() > 0) {
            JOptionPane.showInternalMessageDialog(null, "Score updated successfully");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
}


   public class OverallGradeCalculator {

    // Method to calculate the overall grade based on subject scores
    public static String calculateGrade(double[] subjectScores) {
        Arrays.sort(subjectScores);

        double bestFiveTotal = 0;

        // Calculate the total of the best five scores
        for (int i = 5; i < 10; i++) {
            bestFiveTotal += subjectScores[i];
        }

        // Add the English score
        bestFiveTotal += subjectScores[0]; // Assuming English is at index 0

        double average = bestFiveTotal / 6; // Dividing by 6 since 5 subjects + English

        // Determine grade based on the average
        if (average >= 90 && average <= 100) {
            return "A+";
        } else if (average >= 80 && average < 90) {
            return "A";
        } else if (average >= 75 && average < 80) {
            return "B+";
        } else if (average >= 70 && average < 75) {
            return "B";
        } else if (average >= 65 && average < 70) {
            return "C+";
        } else if (average >= 60 && average < 65) {
            return "C";
        } else if (average >= 55 && average < 60) {
            return "D+";
        } else if (average >= 50 && average < 55) {
            return "D";
        } else {
            return "F";
        }
    }}}
