import java.sql.*;
import java.util.*;
import java.util.Random;

class User {
    final String url = "jdbc:mysql://localhost:3306/smms?useUnicode=true&characterEncoding=UTF-8";
    final String user = "root"; 
    final String password = "mk8328"; 

    int id;
    String name;
    String pno;
    Scanner sc = new Scanner(System.in);

    void display() {
        System.out.printf("Name : %s\n", name);
        System.out.printf("Phone No : %s\n", pno);
        System.out.printf("ID : %d\n", id);
        System.out.println("To continue registering & save the account enter 1");
        int n = sc.nextInt();
        if (n == 1) {
            saveUser();
        } else {
            System.out.println("Wrong Choice, Returning to home page");
        }
    }

    void saveUser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String q = "INSERT INTO userdata (CusID, Name, `Phone No`) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {

            try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, pno);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Customer Account Registered Successfully");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void vegedisplay()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String fq="SELECT * from vegeinfo";
        try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement stat = conn.createStatement();) {
            ResultSet rs = stat.executeQuery(fq);
           
            System.out.println("******************************************************************"+"\n");
            while (rs.next()) {
                System.out.print("Vegetable ID: " + rs.getInt("Id") + "\n");
                System.out.print("Vegetable Name: " + rs.getString("Name") + "\n");
                System.out.print("Available Qty: " + rs.getInt("Qty") + "\n");
                System.out.print("Unit Price / Kg: " + rs.getFloat("UP") + " \n");
                System.out.println("******************************************************************"+"\n");
               
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("To Purchase enter 1");
        int t=sc.nextInt();
        if(t==1)
        {
             purchasevegetable();
        }
       
    }
    void fruitdisplay()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String fq="SELECT * from fruitinfo";
        try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement stat = conn.createStatement();) {
            ResultSet rs = stat.executeQuery(fq);
            
            System.out.println("******************************************************************"+"\n");
            while (rs.next()) {
                System.out.print("Fruit ID: " + rs.getInt("Id") + "\n");
                System.out.print("Fruit Name: " + rs.getString("Name") + "\n");
                System.out.print("Fruit Qty: " + rs.getInt("Qty") + "\n");
                System.out.print("Unit Price / Kg: " + rs.getFloat("UP") + " \n");
                System.out.println("******************************************************************"+"\n");
               
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
       System.out.println("To Purchase enter 1");
       int t=sc.nextInt();
       if(t==1)
       {
            purchasefruit();
       }
    }
    void luckydisplay()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String date = sc.next();  

        String fq = "WITH TotalOrderValuePerCustomer AS (\r\n" + 
                    "    SELECT Id, Name, SUM(Amt) AS Amount\r\n" + 
                    "    FROM billinfo\r\n" + 
                    "    WHERE Date = '" + date + "'\r\n" +  
                    "    GROUP BY Id, Name\r\n" + 
                    "),\r\n" + 
                    "AverageOrderValue AS (\r\n" + 
                    "    SELECT AVG(Amount) AS avg_value\r\n" + 
                    "    FROM TotalOrderValuePerCustomer\r\n" + 
                    ")\r\n" + 
                    "SELECT Id, Name\r\n" + 
                    "FROM TotalOrderValuePerCustomer, AverageOrderValue\r\n" + 
                    "WHERE Amount > AverageOrderValue.avg_value;\r\n";
        

        try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement stat = conn.createStatement();) {
            ResultSet rs = stat.executeQuery(fq);
            // Check if the connection is established
            System.out.println("******************************************************************"+"\n");
            while (rs.next()) {
                System.out.print("Customer ID: " + rs.getInt("Id") + "\n");
                System.out.print("Customer Name: " + rs.getString("Name") + "\n");
                System.err.println("CONGRATULATIONS");
                System.out.println("******************************************************************"+"\n");
                
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void purchasevegetable() {
        System.out.println("Enter your Customer ID");
        int id = sc.nextInt();
        System.out.println("Enter Your Name");
        String name = sc.next();
        System.out.println("Enter Vegetable ID ");
        int fid = sc.nextInt();
        System.out.println("Enter Vegetable Name ");
        String fna = sc.next();
        System.out.println("Enter Vegetable Qty ");
        float fq = sc.nextFloat();
        int st = 1;
        float fqc = 0;
        int upp = 1;
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
     
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {
    
            String vq = "SELECT Id, Qty, UP FROM vegeinfo WHERE Id = ? AND Qty >= ?";
            boolean exists = false;
    
            try (PreparedStatement pstmt = conn.prepareStatement(vq)) {
                pstmt.setInt(1, fid);
                pstmt.setFloat(2, fq);
                try (ResultSet rs = pstmt.executeQuery()) {
                    exists = rs.next();
                    if (exists) {
                        fqc = rs.getFloat("Qty");
                        upp = rs.getInt("UP");
                    }
                }
            }
     
            if (exists) {
                fqc -= fq;
                String q = "UPDATE vegeinfo SET Qty = ? WHERE Id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setFloat(1, fqc);
                    pstmt.setInt(2, fid);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("PURCHASED!");
                        st = 0;
                    } else {
                        System.out.println("No fruit found with the given ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entered ID or Qty is not available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        float usp = (float) upp;
        float tot = fq * usp;
        if (st == 0) {
            System.out.println("To confirm purchase enter 1");
            System.out.println("Enter Today Date");
            String date = sc.next();
            int cp = sc.nextInt();
            if (cp == 1) {
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     Statement stat = conn.createStatement();) {
    
                    String p = "INSERT INTO billinfo (Id, Name, Pid, Pname, Qty, Amt, Date) VALUES (?,?,?,?,?,?,?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(p)) {
                        pstmt.setInt(1, id);
                        pstmt.setString(2, name);
                        pstmt.setInt(3, fid);
                        pstmt.setString(4, fna);
                        pstmt.setFloat(5, fq);
                        pstmt.setFloat(6, tot);
                        pstmt.setString(7, date);
                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("A new row was inserted successfully!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    void purchasefruit() {
        System.out.println("Enter your Customer ID");
        int id = sc.nextInt();
        System.out.println("Enter Your Name");
        String name = sc.next();
        System.out.println("Enter Fruit ID ");
        int fid = sc.nextInt();
        System.out.println("Enter Fruit Name ");
        String fna = sc.next();
        System.out.println("Enter Fruit Qty ");
        float fq = sc.nextFloat();
        int st = 1;
        float fqc = 0;
        int upp = 1;
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {
    
            String vq = "SELECT Id, Qty, UP FROM fruitinfo WHERE Id = ? AND Qty >= ?";
            boolean exists = false;
    
            try (PreparedStatement pstmt = conn.prepareStatement(vq)) {
                pstmt.setInt(1, fid);
                pstmt.setFloat(2, fq);
                try (ResultSet rs = pstmt.executeQuery()) {
                    exists = rs.next();
                    if (exists) {
                        fqc = rs.getFloat("Qty");
                        upp = rs.getInt("UP");
                    }
                }
            }
    
            if (exists) {
                fqc -= fq;
                String q = "UPDATE fruitinfo SET Qty = ? WHERE Id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setFloat(1, fqc);
                    pstmt.setInt(2, fid);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("PURCHASED!");
                        st = 0;
                    } else {
                        System.out.println("No fruit found with the given ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entered ID or Qty is not available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        float usp = (float) upp;
        float tot = fq * usp;
        if (st == 0) {
            System.out.println("To confirm purchase enter 1");
            System.out.println("Enter Today Date");
            String date = sc.next();
            int cp = sc.nextInt();
            if (cp == 1) {
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     Statement stat = conn.createStatement();) {
    
                    String p = "INSERT INTO billinfo (Id, Name, Pid, Pname, Qty, Amt, Date) VALUES (?,?,?,?,?,?,?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(p)) {
                        pstmt.setInt(1, id);
                        pstmt.setString(2, name);
                        pstmt.setInt(3, fid);
                        pstmt.setString(4, fna);
                        pstmt.setFloat(5, fq);
                        pstmt.setFloat(6, tot);
                        pstmt.setString(7, date);
                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("A new row was inserted successfully!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    void displayBillGroupedByDate() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
        String query = "SELECT b.idbillinfo, a.Date, a.Id, a.Name, a.Pid, a.Pname, a.TotalQty, a.TotalAmt "
                     + "FROM (SELECT Date, Id, Name, Pid, Pname, SUM(Qty) AS TotalQty, SUM(Amt) AS TotalAmt "
                     + "      FROM billinfo "
                     + "      GROUP BY Date, Id, Name, Pid, Pname) a "
                     + "JOIN billinfo b ON a.Date = b.Date AND a.Id = b.Id AND a.Name = b.Name AND a.Pid = b.Pid AND a.Pname = b.Pname";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {
            
            System.out.println("******************************************************************\n");
            while (rs.next()) {
                System.out.print("Bill ID: " + rs.getString("idbillinfo") + "\n");
                System.out.print("Date: " + rs.getString("Date") + "\n");
                System.out.print("Customer ID: " + rs.getInt("Id") + "\n");
                System.out.print("Customer Name: " + rs.getString("Name") + "\n");
                System.out.print("Product ID: " + rs.getInt("Pid") + "\n");
                System.out.print("Product Name: " + rs.getString("Pname") + "\n");
                System.out.print("Total Quantity: " + rs.getFloat("TotalQty") + "\n");
                System.out.print("Total Amount: " + rs.getFloat("TotalAmt") + "\n");
                System.out.println("******************************************************************\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    boolean checkBillIdExists(int billId) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM billinfo WHERE idbillinfo = ?";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
    
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setInt(1, billId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        exists = (count > 0);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return exists;
    }
    void makedelivery(int x) {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int t=x;
        System.out.println("Enter your name");
        String xna=sc.next();
        System.out.println("Enter the distance of the delivery destination place in kms");
        float km=sc.nextFloat();
        String w="NA";
        String z="NA";
        String wq="Not Delivered";
        Random random = new Random();
        int randomInt = Math.abs(random.nextInt());
        String q = "INSERT INTO deliverydata (Billid, Name, Km, DName,Dpno,OTP,Status) VALUES (?, ?, ?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {

            try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setInt(1, t);
                pstmt.setString(2, xna);
                pstmt.setFloat(3, km);
                pstmt.setString(4, w);
                pstmt.setString(5, z);
                pstmt.setInt(6, randomInt);
                pstmt.setString(7, wq);
                int rowsInserted = pstmt.executeUpdate();
               
                if (rowsInserted > 0) {
                    System.out.println("Delivery Registered Successfully");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void deliverysts(int p)
    {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    
        String query = "SELECT `Billid`, `DName`, `Dpno`, `Status`, `OTP` "
                     + "FROM deliverydata "
                     + "WHERE `Billid` = ?";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, p);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Delivery Details:");
                    System.out.println("Bill ID: " + rs.getInt("Billid"));
                    System.out.println("Delivery Name: " + rs.getString("DName"));
                    System.out.println("Delivery Phone No: " + rs.getString("Dpno"));
                    System.out.println("Status: " + rs.getString("Status"));
                    System.out.println("OTP value: " + Math.abs(rs.getInt("OTP")));
                    System.out.println("******************************************");
                } else {
                    System.out.println("No delivery details found for Bill ID: " + p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
    

class Admin {
    final String url = "jdbc:mysql://localhost:3306/smms?useUnicode=true&characterEncoding=UTF-8"; 
    final String user = "root"; 
    final String password = "mk8328"; 

    int id;
    String name;
    String pno;
    Scanner sc = new Scanner(System.in);

    void display() {
        System.out.printf("Name : %s\n", name);
        System.out.printf("Phone No : %s\n", pno);
        System.out.printf("Admin ID : %d\n", id);
        System.out.println("To continue registering & save the account enter 1");
        int n = sc.nextInt();
        if (n == 1) {
            saveAdmin();
        } else {
            System.out.println("Wrong Choice, Returning to home page");
        }
    }

    void saveAdmin() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String q = "INSERT INTO admindata (AdminID, Name, `Phone No`) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {

            try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, pno);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Admin Account Registered Successfully");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean validate3() {
        System.out.println("Enter Your Admin ID");
        int n = sc.nextInt();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to DB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false; 
        }

        boolean exists = false;

        String query = "SELECT AdminID FROM admindata";

        try (Connection conn = DriverManager.getConnection(url,user,password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int adid = rs.getInt("AdminID");
                if (adid == n) {
                    exists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  
        }

        if (exists) {
            return true;
        } else {
            System.out.println("Invalid Admin");
            return false;
        }
    }

    void validate1()
    {
        System.out.println("Enter Your Admin ID");
        int n=sc.nextInt();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to DB");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        int adid=0;
        boolean exists=false;
        try (Connection conn=DriverManager.getConnection(url, user, password);
        Statement stat = conn.createStatement();)
        {
            String qr="SELECT AdminID from admindata";
            
           ResultSet rs = stat.executeQuery(qr);
            while(rs.next())
            {
            adid=rs.getInt(1);
                if(adid==n)
                {
                    exists=true;
                }
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        if(exists)
        {
            updateVegetableStock();
        }
        else
        {
            System.out.println("Invalid Admin ");
        }
    }
    void validate2()
    {
        System.out.println("Enter Your Admin ID");
        int n=sc.nextInt();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to DB");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        int adid=0;
        boolean exists=false;
        try (Connection conn=DriverManager.getConnection(url, user, password);
        Statement stat = conn.createStatement();)
        {
            String qr="SELECT AdminID from admindata";
            
           ResultSet rs = stat.executeQuery(qr);
            while(rs.next())
            {
            adid=rs.getInt(1);
                if(adid==n)
                {
                    exists=true;
                }
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        if(exists)
        {
            updateFruitStock();
        }
        else
        {
            System.out.println("Invalid Admin ");
        }
    }
    void updateFruitStock() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Enter the Fruit reference id ");
        int vid = sc.nextInt();
        System.out.println("Enter Fruit name ");
        String vna = sc.next();
        System.out.println("Enter Fruit Weight Stock (float)");
        float vw = sc.nextFloat();
        System.out.println("Enter Fruit Unit Price");
        int vu = sc.nextInt();
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {

            String vq = "SELECT Id FROM fruitinfo WHERE Id = ?";
            boolean exists = false;
            try (PreparedStatement pstmt = conn.prepareStatement(vq)) {
                pstmt.setInt(1, vid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    exists = rs.next();
                }
            }

            if (exists) {
                String q = "UPDATE fruitinfo SET Qty = ?, UP = ? WHERE Id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setFloat(1, vw);
                    pstmt.setInt(2, vu);
                    pstmt.setInt(3, vid);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("The Fruit quantity was updated successfully!");
                    } else {
                        System.out.println("No vegetable found with the given ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String q = "INSERT INTO fruitinfo (Id, Name, Qty, UP) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setInt(1, vid);
                    pstmt.setString(2, vna);
                    pstmt.setFloat(3, vw);
                    pstmt.setInt(4, vu);
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Fruit Stock set Successfully");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void deliveryupdate()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Enter Bill ID to update:");
        int bd = sc.nextInt();
    
        System.out.println("Enter Name:");
        String dna = sc.next();
    
        System.out.println("Enter  Phone No:");
        String dno= sc.next();
    
        String updateQuery = "UPDATE deliverydata SET `DName` = ?, `Dpno` = ? WHERE `Billid` = ?";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
    
            pstmt.setString(1, dna);
            pstmt.setString(2, dno);
            pstmt.setInt(3, bd);
    
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Delivery data updated successfully for Bill ID: " + bd);
            } else {
                System.out.println("No delivery data found for Bill ID: " + bd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void updateDeliveryStatus() {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        

        System.out.println("Enter Bill ID to update status:");
        int bd = sc.nextInt();

        System.out.println("Enter OTP value:");
        String otp= sc.next();

        String selectq = "SELECT `OTP` FROM deliverydata WHERE `Billid` = ?";
        String updateq = "UPDATE deliverydata SET `Status` = 'Delivered' WHERE `Billid` = ?";

        try (Connection conn = DriverManager.getConnection(url,user,password);
             PreparedStatement selectPstmt = conn.prepareStatement(selectq);
             PreparedStatement updatePstmt = conn.prepareStatement(updateq)) {

            selectPstmt.setInt(1, bd);

            try (ResultSet rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    String storedOtp = rs.getString("OTP");
                    if (storedOtp.equals(otp)) {
                        updatePstmt.setInt(1, bd);
                        int rowsAffected = updatePstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Delivery status updated to 'Delivered' for Bill ID: " + bd);
                        } else {
                            System.out.println("Failed to update delivery status for Bill ID: " + bd);
                        }
                    } else {
                        System.out.println("Invalid OTP value for Bill ID: " + bd);
                    }
                } else {
                    System.out.println("No delivery info found for Bill ID: " + bd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void notdelivery()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String query = "SELECT `Billid`, `Km` FROM deliverydata WHERE `Status` = 'Not Delivered'";

        try (Connection conn = DriverManager.getConnection(url,user,password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Not Delivered Bills:");
            while (rs.next()) {
                int billId = rs.getInt("Billid");
                double kms = rs.getDouble("Km");
                System.out.println("Bill ID: " + billId + ", kms: " + kms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void updateVegetableStock() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class found and connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Enter the vegetable reference id ");
        int vid = sc.nextInt();
        System.out.println("Enter vegetable name ");
        String vna = sc.next();
        System.out.println("Enter Vegetable Weight Stock (float)");
        float vw = sc.nextFloat();
        System.out.println("Enter Vegetable Unit Price");
        int vu = sc.nextInt();
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stat = conn.createStatement();) {

            String vq = "SELECT Id FROM vegeinfo WHERE Id = ?";
            boolean exists = false;
            try (PreparedStatement pstmt = conn.prepareStatement(vq)) {
                pstmt.setInt(1, vid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    exists = rs.next();
                }
            }

            if (exists) {
                String q = "UPDATE vegeinfo SET Qty = ?, UP = ? WHERE Id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setFloat(1, vw);
                    pstmt.setInt(2, vu);
                    pstmt.setInt(3, vid);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("The vegetable quantity was updated successfully!");
                    } else {
                        System.out.println("No vegetable found with the given ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String q = "INSERT INTO vegeinfo (Id, Name, Qty, UP) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(q)) {
                    pstmt.setInt(1, vid);
                    pstmt.setString(2, vna);
                    pstmt.setFloat(3, vw);
                    pstmt.setInt(4, vu);
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Vegetable Stock set Successfully");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to Smart Market - For Admin login enter 1 - For Customer login enter 2");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 2) {
            System.out.println("To Turn on the machine Enter 1");
            int x = sc.nextInt();
            while (x != 0) {
                System.out.println("To create an account Enter 1");
                System.out.println("To purchase vegetables Enter 2");
                System.out.println("To purchase fruits Enter 3");
                System.out.println("To see the lucky customer  Enter 4");
                System.out.println("To make a delivery  Enter 5");
                System.out.println("To check a delivery  Enter 6");
                System.out.println("To close the machine Enter 7");
                int nc = sc.nextInt();
                if (nc == 1) {
                    User u = new User();
                    System.out.println("Enter your Name");
                    u.name = sc.next();
                    System.out.println("Enter your Customer id <To get a Customer ID just dial 9988>");
                    u.id = sc.nextInt();
                    System.out.println("Enter your Phone Number");
                    u.pno = sc.next();
                    u.display();
                }
                else if(nc==2 || nc==3)
                {
                    User u=new User();
                    System.out.println("To order enter 1");
                    int ch=sc.nextInt();
                    while(ch!=0)
                    {
                        System.out.println("For Vegetables 1");
                        System.out.println("For Fruits 2");
                        int t=sc.nextInt();
                        if(t==2)
                        {
                            u.vegedisplay();
                        }
                        else
                        {
                            u.fruitdisplay();
                        }
                        System.out.println("To place order and make bill enter 0");
                        ch=sc.nextInt();
                    }
                    if(ch==0)
                    {
                        u.displayBillGroupedByDate();
                    }
                
                }
                else if(nc==4)
                {
                    User u = new User();
                    u.luckydisplay();
                }
                 else if (nc == 5) {
                    User u =new User();
                    u.displayBillGroupedByDate();
                    System.out.println("Enter the Bill ID ");
                    int k=sc.nextInt();
                    boolean f=u.checkBillIdExists(k);
                    if(f)
                    {
                        u.makedelivery(k);
                    }
                }
                else if(nc==6)
                {
                    User u=new User();
                    System.out.println("Enter your bill id");
                    int t=sc.nextInt();
                    u.deliverysts(t);
                }
                else if (nc == 7) {
                    x = 0;
                }

            }
        } else if (n == 1) {
            System.out.println("To Turn on the machine Enter 1");
            int x = sc.nextInt();
            while (x != 0) {
                System.out.println("To create an Admin account Enter 1");
                System.out.println("To update vegetable stock Enter 2");
                System.out.println("To update fruit stock Enter 3");
                System.out.println("To take a delivery 4");
                System.out.println("To confirm a delivery 5");
                System.out.println("To close the machine Enter 6");
                int nc = sc.nextInt();
                if (nc == 1) {
                    Admin ad = new Admin();
                    System.out.println("Enter your Name");
                    ad.name = sc.next();
                    System.out.println("Enter your Admin id <To get your Admin ID kindly dial 8877 if you are an employee, there will be an ID for you>");
                    ad.id = sc.nextInt();
                    System.out.println("Enter your Phone Number");
                    ad.pno = sc.next();
                    ad.display();
                } else if (nc == 2) {
                    Admin ad = new Admin();
                    ad.validate1();
                }
                else if(nc==3)
                {
                    Admin ad=new Admin();
                    ad.validate2();
                } 
                else if(nc==4)
                {
                    User u=new User();
                    Admin a=new Admin();
                    boolean f=a.validate3();
                    if(f)
                    {
                        a.notdelivery();
                        a.deliveryupdate();
                    }
                }
                else if(nc==5)
                {
                    Admin a =new Admin();
                    a.updateDeliveryStatus();
                }
                else if (nc == 6) {
                    x = 0;
                }

            }
        }
    }
}
