package ohjtuotanto.varausjarjestelma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class SqlKomennot {
    Statement statement;

    public SqlKomennot() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3307/vn",
                "root",
                "root"

        );
        statement = connection.createStatement();
    }
    private ObservableList<String> executeQuery(String query) throws SQLException {
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet set = statement.executeQuery(query);
        while (set.next()) {
            lista.add(set.getString(1));
        }
        return lista;
    }

    public ObservableList<String> valitseKaikkiAlueet() throws SQLException {
        return executeQuery("select nimi from alue");
    }

    public ObservableList<String> valitseKaikkiAsiakkaat() throws SQLException {
        return executeQuery("select asiakas_id from asiakas");
    }


    public ObservableList<String> valitseKaikkiPalvelut() throws SQLException {
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet set = statement.executeQuery("select palvelu_id from asiakas");
        while (set.next()) {
            lista.add(set.getString(1));
        }
        return lista;

    }

    public void mokinArvo() throws SQLException {
        ResultSet set = statement.executeQuery("select hinta from mokki");
    }
}
