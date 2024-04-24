package ohjtuotanto.varausjarjestelma;

import java.sql.*;

public class testicommit {
    public static void main(String[] args) {

        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/vn",
                    "root",
                    "salis123"
            );

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from alue");

            while(set.next()){
                System.out.println(set.getInt("alue_id"));
                System.out.println(set.getString("nimi"));

            }
        }catch(SQLException e){
            System.out.println(e.getErrorCode());
        }


    }

}
