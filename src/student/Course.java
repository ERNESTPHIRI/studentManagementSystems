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

public class Course {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from course");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getId(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField11.setText(String.valueOf(rs.getInt(1)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student ID doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int determineNextSemester(int id) {
    try {
        ps = con.prepareStatement("SELECT COUNT(*) AS total FROM course WHERE student_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int coursesTaken = rs.getInt("total");
            int semester = coursesTaken / 10; // Assuming 10 courses per semester

            // Add 1 to semester since division truncates decimals
            if (coursesTaken % 10 != 0) {
                semester += 1;
            }

            // Return the next semester (semester + 1)
            return semester + 1;
        }
    } catch (SQLException ex) {
        Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1; // Unable to determine the next semester
}





    public boolean isSemesterExist(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("select * from student where student = ? and subject = ?");
            ps.setInt(1, sid);
            ps.setInt(1, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

   public boolean isSubjectExist(int studentId, int semester, String subject) {
    // Check if the semester is within the allowed range (1 to 4)
    if (semester > 4) {
        // Assuming all subjects are completed for semester > 4
        return true;
    }
    
    // Constructing the column names for subjects dynamically
    String[] subjectColumns = new String[10]; // Assuming there are 10 subject columns
    
    for (int i = 1; i <= 10; i++) {
        subjectColumns[i - 1] = "subject" + i;
    }

    try {
        for (String column : subjectColumns) {
            String sql = "SELECT * FROM course WHERE student_id = ? AND semester = ? AND " + column + " = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, semester);
            ps.setString(3, subject);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; // Return true if subject exists for the student in the specified semester and column
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false; // Subject not found for the student in any of the columns
}



    public void insert(int id, int sid, int semester, String subject1, String subject2,
            String subject3, String subject4, String subject5, String subject6, String subject7,
            String subject8, String subject9, String subject10) {

        String sql = "insert into course values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, subject1);
            ps.setString(5, subject2);
            ps.setString(6, subject3);
            ps.setString(7, subject4);
            ps.setString(8, subject5);
            ps.setString(9, subject6);
            ps.setString(10, subject7);
            ps.setString(11, subject8);
            ps.setString(12, subject9);
            ps.setString(13, subject10);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Subject added successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getCourseValues(JTable table, String searchValue) {
        String sql = "SELECT * FROM course WHERE CONCAT(id, student_id, semester) LIKE ? ORDER BY id DESC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel(); // Assuming the table already has a model assigned

            while (rs.next()) {
                Object[] row = new Object[13];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[9] = rs.getString(12);
                row[10] = rs.getString(13);
                model.addRow(row); // Add each row to the model
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
