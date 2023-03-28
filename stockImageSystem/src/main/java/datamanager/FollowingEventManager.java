package datamanager;

import Entities.FollowingEvent;

import java.sql.*;
import java.util.ArrayList;

public class FollowingEventManager extends DaoManager{


    public ArrayList<FollowingEvent> getData() {
        ArrayList<FollowingEvent> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.followingevent");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.followingevent")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int followingId = rs.getInt("FollowingId");
                int followerId = rs.getInt("FollowerId");


                FollowingEvent followingEvent = new FollowingEvent(followingId,followerId);

                data.add(followingEvent);
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

    public boolean insert(FollowingEvent followingEvent){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`followingevent` (`FollowerID`, `FollowingID`)" +
                    " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, followingEvent.getFollowerId());
            preparedStmt.setInt (2, followingEvent.getFollowingId());

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
    public boolean deleteById(FollowingEvent followingEvent){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`followingevent` WHERE (FollowerID = ? AND FollowingID = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, followingEvent.getFollowerId());
            preparedStmt.setInt (2, followingEvent.getFollowingId());

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
    public int getNumberOfFollowers(int id){
        int c = 0;
        for (FollowingEvent followingEvent:getData()){
            if (followingEvent.getFollowingId()==id) c++;
        }

        return c;
    }
    public int getNumberOfFollowing(int id){
        int c = 0;
        for (FollowingEvent followingEvent:getData()){
            if (followingEvent.getFollowerId()==id) c++;
        }

        return c;
    }

    public boolean isFollowing(int following,int follower){
        for (FollowingEvent followingEvent:getData()){
            if (followingEvent.getFollowingId()==following && followingEvent.getFollowerId()==follower){
                return true;
            }
        }
        return false;
    }
}
