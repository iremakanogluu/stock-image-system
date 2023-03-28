package datamanager;

import Entities.Comment;
import Entities.Like;

import java.sql.*;
import java.util.ArrayList;

public class LikeManager extends DaoManager{


    public ArrayList<Like> getData() {
        ArrayList<Like> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.likes");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.likes")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int personId = rs.getInt("PersonId");
                int likeId = rs.getInt("ImageId");


                Like like = new Like(personId,likeId);

                data.add(like);
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

    public boolean insert(Like like){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`likes` (`PersonID`, `ImageID`)" +
                    " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, like.getPersonId());
            preparedStmt.setInt (2, like.getImageId());

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
    public boolean deleteById(Like like){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`likes` WHERE (PersonId = ? AND ImageId = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, like.getPersonId());
            preparedStmt.setInt (2, like.getImageId());

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

    public ArrayList<Like> getLikesByImageId(int id){
        ArrayList<Like> likes = new ArrayList<>();

        for (Like like:getData()){
            if (id==like.getImageId()){
                likes.add(like);
            }
        }

        return likes;
    }

    public int getNumberOfLikes(int id){
        return getLikesByImageId(id).size();
    }
}
