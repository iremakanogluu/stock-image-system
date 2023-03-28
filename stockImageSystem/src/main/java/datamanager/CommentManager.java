package datamanager;

import Entities.Comment;

import java.sql.*;
import java.util.ArrayList;

public class CommentManager extends DaoManager{


    public ArrayList<Comment> getData() {
        ArrayList<Comment> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.comments");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.comments")) {
                rs = stmt.getResultSet();
            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int personId = rs.getInt("PersonId");
                int imageId = rs.getInt("ImageId");
                String commentText = rs.getString("CommentText");

                Comment comment = new Comment(personId
                        ,imageId
                        ,commentText
                );

                data.add(comment);
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

    public boolean insert(Comment comment){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`comments` (`PersonID`, `ImageID`, `CommentText`)" +
                    " values (?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, comment.getPersonId());
            preparedStmt.setInt (2, comment.getImageId());
            preparedStmt.setString(3, comment.getCommentText());

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
    public boolean deleteById(Comment comment){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`comments` WHERE (PersonId = ? AND ImageId = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, comment.getPersonId());
            preparedStmt.setInt (2, comment.getImageId());

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
    public ArrayList<Comment> getCommentsByImageId(int id){
        ArrayList<Comment> comments = new ArrayList<>();

        for (Comment comment:getData()){
            if (id==comment.getImageId()){
                comments.add(comment);
            }
        }

        return comments;
    }

    public int getNumberOfComments(int id){
        return getCommentsByImageId(id).size();
    }
}
