package datamanager;

import Entities.Login;

import java.sql.*;
import java.util.ArrayList;


public class LoginManager extends DaoManager{

    public ArrayList<Login> getData() {
        ArrayList<Login> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.login");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.login")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int loginId = rs.getInt("LoginId");
                String loginEmail = rs.getString("LoginEmail");
                String loginPassword = rs.getString("LoginPassword");
                int personId = rs.getInt("PersonId");


                Login login = new Login(loginId
                        ,loginEmail
                        ,loginPassword
                        ,personId
                );

                data.add(login);
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

    public boolean insert(Login login){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`login` (`LoginID`, `LoginEmail`, `LoginPassword`, `PersonID`)" +
                    " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, login.getLoginId());
            preparedStmt.setString (2, login.getLoginEmail());
            preparedStmt.setString (3, login.getLoginPassword());
            preparedStmt.setInt (4, login.getPersonId());


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

    public boolean deleteById(int personId){
        try
        {
            // the mysql insert statement
            String query = "DELETE FROM `stock_image`.`login` WHERE PersonId = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, personId);

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

    public boolean update(int personId, Login login){
        try
        {
            // the mysql insert statement
            String query = "UPDATE `stock_image`.`login` SET " +
                    " `LoginPassword` = ?," +
                    "WHERE (`PersonID` = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, login.getLoginPassword());
            preparedStmt.setInt (2, personId);

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

    public boolean isSucceed(String email,String password){
        boolean result = false;
        for(Login login:getData()){
            if(login.getLoginEmail().equals(email) && login.getLoginPassword().equals(password)){
                result = true;
                break;
            }
        }
        return result;
    }

    public Login getLoginByEmail(String email){
        for (Login login:getData()){
            if (login.getLoginEmail().equals(email)){
                return login;
            }
        }
        return null;
    }
}
