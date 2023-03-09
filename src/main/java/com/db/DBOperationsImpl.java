package com.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.Map;

public class DBOperationsImpl implements IDBOperations {
    static Connection con = null;

    @Override
    public Connection connectDB(String databaseName) {
        try {
//            Class.forName("org.sqlite.JDBC");
//            System.out.println("Driver Loaded");
            con = DriverManager.getConnection("jdbc:sqlite:/home/suhaniporwal/" + databaseName);
            System.out.println("Connected :)");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return con;
    }

    @Override
    public void insertIntoDB(String checksum, byte[] freqMap) {
        PreparedStatement pstmt=null;
        Connection con=null;
        try {
            con=connectDB("huffmanDB.db");
            if(con!=null) {
                String query = "INSERT INTO HuffmanTable(checksum,freqmap) VALUES (?,?)";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, checksum);
                pstmt.setBytes(2, freqMap);
                pstmt.executeUpdate();
                System.out.println("map inserted successfully");
                pstmt.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    @Override
    public void deleteFromDB() throws SQLException, ClassNotFoundException {

    }

    @Override
    public void updateIntoDB() throws SQLException, ClassNotFoundException {

    }

    @Override
    public Map<String, Integer> readFromDB(String checksum) {

        Connection con=connectDB("huffmanDB.db");

        if(con==null)return null;
        Map<String,Integer> freqmap=null;
        PreparedStatement pstmt =null;
        ResultSet rs=null;
        String query = "SELECT freqmap FROM HuffmanTable WHERE checksum=?";
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, checksum);
            rs=pstmt.executeQuery();
            byte[] freqArr=null;
            if(rs.next())
            freqArr = rs.getBytes("freqmap");

            if(freqArr!=null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(freqArr);
                ObjectInputStream ois = new ObjectInputStream(bais);
                freqmap = (Map<String, Integer>) ois.readObject();
                System.out.println("Read Map from DB successfully");
            }

            rs.close();
            pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        return freqmap;
    }
    @Override
    public void createNewTable() {
        Connection conn=connectDB("huffmanDB.db");
        String sql = "CREATE TABLE IF NOT EXISTS HuffmanTable (\n"
                + "	checksum text PRIMARY KEY NOT NULL,\n"
                + "	freqmap blob\n"
                + ");";

        if(conn != null){
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.execute(sql);
                System.out.println("Table Created Successfully");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public void readTable() {
        Connection con = connectDB("huffmanDB.db");

        if (con != null) {
            Statement st = null;
            ResultSet rs = null;
            String query = "SELECT * FROM HuffmanTable";
            try {
                st=con.createStatement();
                rs=st.executeQuery(query);

                while (rs.next())
                {
                    System.out.println(rs.getString(1)+" "+rs.getBytes(2));
                }
                System.out.println("Read Map from DB successfully");
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}