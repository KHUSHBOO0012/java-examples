import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
public class CustomerInformation {
JFrame f;
JPanel p1, p2, p3;
JTabbedPane tp;
JLabel namelabel1, emaillabel1, countrylabel1, genderlabel1,
deletelabel, idlabel2, namelabel2, emaillabel2, countrylabel2,
genderlabel2;
JTextField name1, email1, gender1, delete_id, name2, email2,
gender2, id2;
JButton savebtn, resetbtn, editbtn1, editbtn2, deletebtn;
JComboBox country1, country2;
CustomerInformation() {
f = new JFrame("Customer Form");
p1 = new JPanel(new GridLayout(5, 2));
p2 = new JPanel(new GridLayout(6, 2));
p3 = new JPanel(new GridLayout(2, 2));
tp = new JTabbedPane();
namelabel1 = new JLabel("Customer Name:");
emaillabel1 = new JLabel("Customer email:");
countrylabel1 = new JLabel("Customer Country:");
genderlabel1 = new JLabel("Customer Gender:");
deletelabel = new JLabel("Enter Customer ID to deleterecord:");
idlabel2 = new JLabel("Customer ID:");
namelabel2 = new JLabel("Customer Name:");
emaillabel2 = new JLabel("Customer email:");
countrylabel2 = new JLabel("Customer Country:");
genderlabel2 = new JLabel("Customer Gender:");
name1 = new JTextField(12);email1 = new JTextField(12);
gender1 = new JTextField(12);
delete_id = new JTextField(12);
name2 = new JTextField(12);
email2 = new JTextField(12);
gender2 = new JTextField(12);
id2 = new JTextField(12);
country1 = new JComboBox();
country1.addItem("INDIA");
country1.addItem("AMERICA");
country1.addItem("AUSTRALIA");
country1.addItem("PHILLIPHINES");
country1.addItem("SPAIN");
country2 = new JComboBox();
country2.addItem("INDIA");
country2.addItem("AMERICA");
country2.addItem("AUSTRALIA");
country2.addItem("PHILLIPHINES");
country2.addItem("SPAIN");
savebtn = new JButton(" Add ");
resetbtn = new JButton(" Reset");
editbtn1 = new JButton(" Edit ");
editbtn2 = new JButton(" Save");
deletebtn = new JButton("Delete");
p1.add(namelabel1);
p1.add(name1);
p1.add(emaillabel1);
p1.add(email1);
p1.add(countrylabel1);
p1.add(country1);
p1.add(genderlabel1);
p1.add(gender1);
p1.add(savebtn);
p1.add(resetbtn);
p2.add(idlabel2);
p2.add(id2);
p2.add(namelabel2);
p2.add(name2);
p2.add(emaillabel2);
p2.add(email2);
p2.add(countrylabel2);
p2.add(country2);
p2.add(genderlabel2);
p2.add(gender2);
p2.add(editbtn1);
p2.add(editbtn2);
p3.add(deletelabel);
p3.add(delete_id);
p3.add(deletebtn);
resetbtn.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ae) {
		name1.setText("");
		email1.setText("");
		country1.setSelectedIndex(0);
		gender1.setText("");
	}
});
savebtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {
String value1 = name1.getText();
String value2 = email1.getText();
String value3 = (String) country1.getSelectedItem();
String value4 = gender1.getText();
try {
Connection con = getConnection();
int customerid = getCustomerID(con);
PreparedStatement st = con.prepareStatement("insert into CUSTOMER_INFO(CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_EMAIL,CUSTOMER_COUNTRY,CUSTOMER_GENDER) values(?,?,?,?,?)");
st.setInt(1, customerid);
st.setString(2, value1);
st.setString(3, value2);
st.setString(4, value3);
st.setString(5, value4);
st.executeUpdate();
JOptionPane.showMessageDialog(p1,"data is succesgully entered into database");
}
catch (ClassNotFoundException e) {
JOptionPane.showMessageDialog(p1,"erorr in submitting data!");
}
catch (SQLException ex) {
Logger.getLogger(CustomerInformation.class.getName()).log(Level.SEVERE, null, ex); }
}
});

editbtn1.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {
int value = Integer.parseInt(id2.getText());
try {
Connection con = getConnection();
PreparedStatement st = con.prepareStatement("select * from CUSTOMER_INFO where CUSTOMER_ID=?");
st.setInt(1, value);
ResultSet res = st.executeQuery();
res.next();
id2.setText(Integer.toString(res.getInt(1)));
name2.setText(res.getString(2));
email2.setText(res.getString(3));
country2.setSelectedItem(res.getString(4));
gender2.setText(res.getString(5));
con.close();
}
catch (ClassNotFoundException e) {
JOptionPane.showMessageDialog(p2, "Can not edit data");
} 
catch (SQLException ex) {
	Logger.getLogger(CustomerInformation.class.getName()).log(Level.SEVERE, null, ex);
}
}
});
editbtn2.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {
int x = JOptionPane.showConfirmDialog(p2, "Confirm edit? All data will be replaced");
if (x == 0) {
try {
int value1 = Integer.parseInt(id2.getText());
String value2 = name2.getText();
String value3 = email2.getText();
String value4 = (String) country2.getSelectedItem();
String value5 = gender2.getText();
Connection con = getConnection();
Statement st = (Statement) con.createStatement();
((java.sql.Statement) st).executeUpdate("update CUSTOMER_INFO set CUSTOMER_NAME='" +
value2 + "', CUSTOMER_EMAIL='"+ value3 + "',CUSTOMER_COUNTRY='" +
value4 + "',CUSTOMER_GENDER='" + value5 + "' where CUSTOMER_ID=" +
value1 + "");
JOptionPane.showMessageDialog(p2, "Updated successfully");
con.close();
} catch (ClassNotFoundException ex) {
JOptionPane.showMessageDialog(p2, "Error in updating edit fields");
} catch (SQLException ex) {
Logger.getLogger(CustomerInformation.class.getName()).log(Level.SEVERE, null, ex);
}
}
}
});

}
void dis() {
f.getContentPane().add(tp);
tp.addTab("Add Record", p1);
tp.addTab("Edit Record", p2);
tp.addTab("Delete Record", p3);
f.setSize(400, 200);
f.setVisible(true);
f.setResizable(true);
}
public void createDatabase() throws ClassNotFoundException,SQLException {
	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	Connection con = DriverManager.getConnection("jdbc:derby:cust;create=true;user=app;password=app");
	String createString = "create table CUSTOMER_INFO(CUSTOMER_ID INTEGER PRIMARY KEY,\r\n" +
			"CUSTOMER_NAME VARCHAR(20),\r\n" +
			"CUSTOMER_EMAIL VARCHAR(50),\r\n" +
			"CUSTOMER_COUNTRY VARCHAR(20),\r\n" +
			"CUSTOMER_GENDER VARCHAR(20))";
   Statement stmt = (Statement) con.createStatement();
			((java.sql.Statement) stmt).executeUpdate(createString);
			
			
}
public Connection getConnection() throws ClassNotFoundException,
SQLException {
Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
Connection con = DriverManager.getConnection("jdbc:derby:cust;create=true;user=app;password=app");
return con;
}
private int getCustomerID(Connection con) throws SQLException {
int value = 0;
Statement stmt = (Statement) con.createStatement();
ResultSet rs = ((java.sql.Statement) stmt).executeQuery("Select max(CUSTOMER_ID) from CUSTOMER_INFO");
if(rs.next()) value = rs.getInt(1);
return value+1;
}
public void closeConnection(Connection con) throws SQLException{
//try {
//createDatabase();
//} catch (Exception e1) {
//e1.printStackTrace();
//}
con.close();
}

public static void main(String z[]) {
CustomerInformation pro = new CustomerInformation();
pro.dis();
}

}
