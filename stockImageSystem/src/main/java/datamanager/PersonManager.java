package datamanager;

import Entities.Person;

import java.sql.*;
import java.util.ArrayList;

public class PersonManager extends DaoManager{
    public ArrayList<Person> getData() {
        ArrayList<Person> data = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stock_image.persons");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT * FROM stock_image.persons")) {
                rs = stmt.getResultSet();

            }

            // Now do something with the ResultSet ....
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int personId = rs.getInt("PersonId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String userName = rs.getString("UserName");
                String gender = rs.getString("Gender");
                Date birthdate = rs.getDate("Birthdate");

                Person person = new Person(personId
                        ,firstName
                        ,lastName
                        ,userName
                        ,gender
                        ,birthdate
                );

                data.add(person);
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return data;
    }

    public boolean insert(Person person){
        try
        {
            // the mysql insert statement
            String query = "INSERT INTO `stock_image`.`persons` (`PersonID`, `FirstName`, `LastName`, `Username`, `Gender`, `Birthdate`)" +
                    " values (?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, person.getPersonId());
            preparedStmt.setString (2, person.getFirstName());
            preparedStmt.setString (3, person.getLastName());
            preparedStmt.setString (4, person.getUserName());
            preparedStmt.setString (5, person.getGender());
            preparedStmt.setDate   (6, person.getBirthdate());

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
            String query = "DELETE FROM `stock_image`.`persons` WHERE PersonID = ?";

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

    public boolean update(int personId,Person person){
        try
        {
            // the mysql insert statement
            String query = "UPDATE `stock_image`.`persons` SET " +
                    "`FirstName` = ?," +
                    " `LastName` = ?," +
                    " `Username` = ?," +
                    " `Gender` = ?," +
                    " `Birthdate` = ? " +
                    "WHERE (`PersonID` = ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, person.getFirstName());
            preparedStmt.setString (2, person.getLastName());
            preparedStmt.setString (3, person.getUserName());
            preparedStmt.setString (4, person.getGender());
            preparedStmt.setDate   (5, person.getBirthdate());
            preparedStmt.setInt (6, personId);

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

    public Person getById(int id){
        for (Person person:getData()){
            if (id==person.getPersonId()) return person;
        }

        return null;
    }
}
