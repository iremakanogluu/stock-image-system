package datamanager;

import Entities.BelongsTo;
import Entities.Image;

import java.sql.*;
import java.util.ArrayList;

public class ImageManager extends DaoManager{
    BelongsToManager belongsToManager = new BelongsToManager();


    public ArrayList<Image> getData() {
        ArrayList<Image> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.image");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.image")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int imageId = rs.getInt("ImageId");
                String imageName = rs.getString("ImageName");
                String URL = rs.getString("URL");
                int width = rs.getInt("Width");
                int height = rs.getInt("Height");
                int personId = rs.getInt("PersonId");
                Date publishDate = rs.getDate("Publishdate");

                Image image = new Image(imageId
                        ,imageName
                        ,URL
                        ,width
                        ,height
                        ,personId
                        ,publishDate
                );

                data.add(image);
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

    public boolean insert(Image image){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`image` (`ImageID`, `ImageName`, `URL`, `Width`, `Height`, `PersonID`, `PublishDate`)" +
                    " values (?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, image.getImageId());
            preparedStmt.setString (2, image.getImageName());
            preparedStmt.setString (3, image.getURL());
            preparedStmt.setInt (4, image.getWidth());
            preparedStmt.setInt (5, image.getHeight());
            preparedStmt.setInt (6, image.getPersonId());
            preparedStmt.setDate   (7, image.getPublishDate());

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

    public boolean deleteById(int imageId){
        for (BelongsTo c:belongsToManager.getCategoriesByImageId(imageId)){
            belongsToManager.deleteById(c);
        }

        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`image` WHERE ImageID = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, imageId);

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

    public boolean update(int imageId,Image image){
        try
        {
            // the mysql insert statement
            String query = "UPDATE `stock_image`.`image` SET " +
                    "`ImageName` = ?," +
                    " `URL` = ?," +
                    " `Width` = ?," +
                    " `Height` = ?," +
                    " `PublishDate` = ? " +
                    "WHERE (`ImageID` = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, image.getImageName());
            preparedStmt.setString (2, image.getURL());
            preparedStmt.setInt (3, image.getWidth());
            preparedStmt.setInt (4, image.getHeight());
            preparedStmt.setDate   (5, image.getPublishDate());
            preparedStmt.setInt (6, image.getImageId());

            System.out.println(preparedStmt);

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

    public Image getImageById(int id){
        for (Image image:getData()){
            if (image.getImageId()==id){
                return image;
            }
        }

        return null;
    }
}
