package com.telenav.nds.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created on 4/25/2016.
 */
public class SQLiteJDBC {

    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:E:\\NDS\\NDS2.4.2_BJ5X5\\PRODUCT\\C1\\NAME.NDS");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM namedObjectTable;" );
            while ( rs.next() ) {
                int namedObjectId = rs.getInt("namedObjectId");
                int versionId = rs.getInt("versionId");
                rs.getBlob("ndsData");

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
