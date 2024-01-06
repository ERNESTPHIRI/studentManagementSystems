package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MarksSheet {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public boolean isIdExist(int sid) {
        try {
            ps = con.prepareStatement("select * from score where student_id = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void getScoreValues(JTable table, int sid) {
        String sql = "SELECT * FROM score WHERE student_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
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
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public String getOverallGrade(int sid) {
    String overallGrade = "N/A"; // Default value or appropriate placeholder
    
    try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT AVG(grade) FROM score WHERE student_id = " + sid + " AND semester BETWEEN 1 AND 4");
        
        int numberOfRegisteredSemesters = 0;
        double cumulativeGrade = 0.0;
        
        while (rs.next()) {
            double semesterAverageGrade = rs.getDouble(1);
            
            if (!rs.wasNull()) {
                numberOfRegisteredSemesters++;
                cumulativeGrade += semesterAverageGrade;
            }
        }
        
        if (numberOfRegisteredSemesters > 0) {
            double overallAverageGrade = cumulativeGrade / numberOfRegisteredSemesters;
            
            // Assuming overallAverageGrade is between 0 and 100
            if (overallAverageGrade >= 90) {
                overallGrade = "A+";
            } else if (overallAverageGrade >= 80) {
                overallGrade = "A";
            } else if (overallAverageGrade >= 75) {
                overallGrade = "B+";
            } else if (overallAverageGrade >= 70) {
                overallGrade = "B";
            } else if (overallAverageGrade >= 65) {
                overallGrade = "C+";
            } else if (overallAverageGrade >= 60) {
                overallGrade = "C";
            } else if (overallAverageGrade >= 55) {
                overallGrade = "D+";
            } else if (overallAverageGrade >= 50) {
                overallGrade = "D";
            } else {
                overallGrade = "F";
            }
        } else {
            overallGrade = "N/A"; // Placeholder for no data found for the given student
        }
    } catch (SQLException ex) {
        Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        // Handle the exception: log or manage accordingly
    }
    return overallGrade;
}

}
