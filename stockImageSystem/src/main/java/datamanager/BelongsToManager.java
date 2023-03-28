package datamanager;

import Entities.BelongsTo;

import java.sql.*;
import java.util.ArrayList;

public class BelongsToManager extends DaoManager {
    public ArrayList<BelongsTo> getData() {
        ArrayList<BelongsTo> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.belongs_to");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.belongs_to")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int categoryId = rs.getInt("CategoryId");
                int imageId = rs.getInt("ImageId");


                BelongsTo belongsTo = new BelongsTo(categoryId,imageId);

                data.add(belongsTo);
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }

        return data;
    }

    public boolean insert(BelongsTo belongsTo){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`belongs_to` (`CategoryID`, `ImageID`)" +
                    " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, belongsTo.getCategoryId());
            preparedStmt.setInt (2, belongsTo.getImageId());

            // execute the preparedstatement
            preparedStmt.execute();

            return true;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return false;
    }
    public boolean deleteById(BelongsTo belongsTo){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`belongs_to` WHERE (CategoryId = ? AND ImageId = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, belongsTo.getCategoryId());
            preparedStmt.setInt (2, belongsTo.getImageId());

            // execute the preparedstatement
            preparedStmt.execute();

            return true;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return false;
    }

    public ArrayList<BelongsTo> getCategoriesByImageId(int imageId){
        ArrayList<BelongsTo> r = new ArrayList<>();

        for(BelongsTo belongsTo:getData()){
            if (belongsTo.getImageId()==imageId){
                r.add(belongsTo);
            }
        }
        return r;
    }
}
