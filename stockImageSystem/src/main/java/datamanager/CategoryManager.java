package datamanager;

import Entities.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryManager extends DaoManager{


    public ArrayList<Category> getData() {
        ArrayList<Category> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.category");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.category")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int categoryId = rs.getInt("CategoryId");
                String categoryName = rs.getString("CategoryName");


                Category category = new Category(categoryId,categoryName);

                data.add(category);
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

    public boolean insert(Category category){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`category` (`CategoryID`, `CategoryName`)" +
                    " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, category.getCategoryId());
            preparedStmt.setString (2, category.getCategoryName());

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
    public boolean deleteById(int categoryId){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`category` WHERE CategoryID = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, categoryId);
            ;

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
    public Category getCategoryById(int id){
        for (Category category:getData()){
            if (category.getCategoryId()==id){
                return category;
            }
        }
        return null;
    }

    public int getCategoryIdByCategoryName(String categoryName){
        for (Category category:getData()){
            if (category.getCategoryName().equals(categoryName)){
                return category.getCategoryId();
            }
        }
        return -1;
    }
}
