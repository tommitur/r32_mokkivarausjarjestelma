package ohjtuotanto.varausjarjestelma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class SqlKomennot {
    Statement statement;
    Connection connection;

    public SqlKomennot() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/vn",
                "root",
                "salis123"

        );
        statement = connection.createStatement();
    }

    //String query
    private ObservableList<String> executeQuery(String query) throws SQLException {
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet set = statement.executeQuery(query);
        while (set.next()) {
            lista.add(set.getString(1));
        }
        return lista;
    }
    //Integer query
    private ObservableList<Integer> executeQueryINT(String query) throws SQLException {
        ObservableList<Integer> lista = FXCollections.observableArrayList();
        ResultSet set = statement.executeQuery(query);
        while (set.next()) {
            lista.add(set.getInt(1));
        }
        return lista;
    }

    public void updateQuery(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public ObservableList<String> valitseKaikkiAlueet() throws SQLException {
        return executeQuery("select nimi from alue");
    }

    public ObservableList<Integer> valitseKaikkiAsiakkaat() throws SQLException {
        return executeQueryINT("select asiakas_id from asiakas");
    }

    public ObservableList<String> valitseKaikkiMokit() throws SQLException {
        return executeQuery("select mokkinimi from mokki");
    }


    public ObservableList<String> valitseKaikkiPalvelut() throws SQLException {
        return executeQuery("select nimi from palvelu");

    }

    public ObservableList<String> haeAlueenID(String alue) throws SQLException{
        return executeQuery("select alue_id from alue where nimi = '" + alue + "'");
    }

    public ObservableList<Integer> haePostriNrot() throws SQLException{
        return executeQueryINT("select postinro from posti");
    }

    public void mokinArvo() throws SQLException {
        ResultSet set = statement.executeQuery("select hinta from mokki");
    }
}
