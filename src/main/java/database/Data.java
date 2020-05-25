package database;

import model.Customers;
import model.Products;
import model.Sales;
import model.SalesDet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


/**
 *
 * @author scis
 */
public class Data {
    private static Connection con;
    private static Integer lastid;

    private Data() {
        // private constructor
    }

    public static void setConnection(){
        try {
            String conStr = "jdbc:mysql://localhost:3308/sales?user=root&password=&useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            con = DriverManager.getConnection(conStr);
            System.out.println("connected");
            lastid = 0;
            setLastCust();
        } catch (Exception e) {
            System.out.println("bad connection");
        }
    }

    public static String saveProduct(Products newP) {
        String s = "";
        PreparedStatement ps, psc;
        String st = "INSERT INTO products(prodid, description, price)  VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(st);
            ps.setString(1, newP.getProdid());
            ps.setString(2, newP.getDescription());
            ps.setDouble(3, newP.getPrice());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException se) {
            s =   se.getErrorCode() + " " + se.getMessage();
        } catch (Exception e) {
            s = e.getMessage();
        }
        return s;
    }

    public static String saveCustomer(Customers newC) {
        String s = "";
        PreparedStatement ps, psc;
        String st = "INSERT INTO customers(custid, custname, address, telno)  VALUES (?,?,?,?)";
        try {
            lastid = lastid + 1;
            ps = con.prepareStatement(st);
            ps.setInt(1, lastid);
            ps.setString(2, newC.getCustname());
            ps.setString(3, newC.getAddress());
            ps.setString(4, newC.getTelno());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException se) {
            s =   se.getErrorCode() + " " + se.getMessage();
        } catch (Exception e) {
            s = e.getMessage();
        }
        return s;
    }

    public static ArrayList<Customers> getCustomers() {
        ArrayList<Customers> ca = new ArrayList<>();
        Customers cust;
        try {
            Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = st.executeQuery("Select * from customers order by custname");

            while (rs.next()) {
                cust = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4),rs.getDouble(5));
                ca.add(cust);
            }

            rs.close();
            st.close();
        } catch (SQLException se){

        }
        return ca;
    }

    public static List<Sales> getSales() {
        final List<Sales> sales = new ArrayList<>();
        final String query = "SELECT s.invid, s.invdate, p.prodid, p.description, p.price, sd.qtysold, c.custid, " +
                "c.custname, c.address, c.telno, c.balance, s.amount FROM sales s JOIN customers c ON s.custid = c.custid JOIN " +
                "sales_details sd ON s.invid = sd.invid JOIN products p ON sd.prodid = p.prodid";

        try {
            final Statement statement = con.createStatement(ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_SCROLL_SENSITIVE);
            final ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                final String invid = resultSet.getString(1);
                final Date invdate = resultSet.getDate(2);

                final String prodid = resultSet.getString(3);
                final String productDescription = resultSet.getString(4);
                final double productPrice = resultSet.getDouble(5);
                final Products product = new Products(prodid, productDescription, productPrice);

                final int quantitySold = resultSet.getInt(6);
                final SalesDet salesDet = new SalesDet(product, quantitySold);

                final Integer customerId = resultSet.getInt(7);
                final String customerName = resultSet.getString(8);
                final String address = resultSet.getString(9);
                final String telno = resultSet.getString(10);
                final double balance = resultSet.getDouble(11);
                final Customers customer = new Customers(customerId, customerName, address, telno, balance);

                final double amount = resultSet.getDouble(12);

                sales.add(new Sales(invid, invdate, salesDet, customer, amount));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return sales;
    }

    public static ArrayList<Products> getProducts() {
        ArrayList<Products> pa = new ArrayList<>();
        try {
            Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = st.executeQuery("Select * from Products order by description");
//            rs.beforeFirst();
            while (rs.next()) {
                Products prod = new Products(rs.getString(1), rs.getString(2),rs.getDouble(3));
                pa.add(prod);
            }
            rs.close();
            st.close();
        } catch (SQLException se){

        }
        return pa;
    }

    public static String saveSales(Sales newS) throws SQLException {
        String s = "";
        PreparedStatement ps;
        String stsa = "INSERT INTO sales(invdate, amount, balance, custid)  VALUES (?,?,?,?)";
        String stsd = "INSERT INTO sales_details(invid, prodid, qtysold, unitprice)  VALUES (?,?,?,?)";

            Customers customer = newS.getCustomer();
            final String invid = getLastInvidRecord();
            //update sales header
            ps = con.prepareStatement(stsa);

            //transaction processing
            ps.setDate(1, newS.getInvdate());
            ps.setDouble(2, newS.getAmount());
            ps.setDouble(3, newS.getCustomerBalance());
            ps.setInt(4, customer.getCustid());
            ps.execute();
            ps.close();


            ps = con.prepareStatement(stsd);
            SalesDet sdet = newS.getSalesDet();
            Products product = sdet.getProduct();
            ps.setString(1, invid);

            ps.setString(2, product.getProdid());
            ps.setInt(3, sdet.getQtysold());
            ps.setDouble(4, product.getPrice());
            ps.execute();

            ps.close();

        return s;
    }

    public static String getLastInvidRecord() throws SQLException {
        String invid = " ";
        final String getNewestInvid = "SELECT MAX(invid) FROM sales;";
        final Statement statement = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        final ResultSet resultSet = statement.executeQuery(getNewestInvid);

        if (resultSet.next()) {
            invid = resultSet.getString(1);
        }

        resultSet.close();
        statement.close();

        return invid;
    }

    private static void setLastCust() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select max(custid) from customers");
            rs.beforeFirst();
            while (rs.next()) {
                lastid = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException se){

        }
    }

    public static Integer getLastid(){
        return lastid;
    }

    public static void DbDone() throws Exception {
        if (con!=null) {
            con.close();
            System.out.println("connection closed");
        }
    }

}
