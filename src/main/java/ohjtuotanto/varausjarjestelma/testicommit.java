package ohjtuotanto.varausjarjestelma;

import java.sql.*;

public class testicommit {
    public static void main(String[] args) {
        System.out.println("testi");
        System.out.println("testi2");

        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/vn",
                    "root",
                    "Kukkakaali50"
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
