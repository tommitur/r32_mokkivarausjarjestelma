package ohjtuotanto.varausjarjestelma;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqlKomennot {
    Statement statement;
    Connection connection;

    private static final String URL = "jdbc:mysql://127.0.0.1:3307/vn";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public SqlKomennot() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3307/vn",
                "root",
                "root"

        );
        statement = connection.createStatement();
    }

    ObservableList<String> testi(String query) throws SQLException {

        ObservableList<String> lista = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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


    public void updateQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public ObservableList<String> valitseKaikkiAlueet() throws SQLException {
        return executeQuery("SELECT nimi FROM alue");
    }

    public ObservableList<Integer> valitseKaikkiAsiakkaat() throws SQLException {
        return executeQueryINT("SELECT asiakas_id FROM asiakas");
    }

    public ObservableList<String> valitseKaikkiMokit() throws SQLException {
        return executeQuery("SELECT mokkinimi FROM mokki");
    }

    public ObservableList<String> valitseKaikkiPalvelut() throws SQLException {
        return executeQuery("SELECT nimi FROM palvelu");
    }

    public ObservableList<String> valitseKaikkiSahkopostit() throws SQLException {
        return executeQuery("SELECT email FROM asiakas");
    }

    public void yksittainenKysely(String kysely) throws SQLException {
        System.out.println(executeQuery(kysely).getLast());
    }
    /*public ObservableList<String> yksittainenKysely(String kysely) throws SQLException {
        return executeQuery(kysely);
    }*/

    public ObservableList<String> haeAlueenID(String alue) throws SQLException {
        return executeQuery("SELECT alue_id FROM alue WHERE nimi = '" + alue + "'");
    }

    public ObservableList<String> haeAlueenmokit(String alue) throws SQLException {
        String alueid = String.valueOf(haeAlueenID(alue));
        alueid = alueid.replaceAll("[\\[\\](){}]", "");
        return executeQuery("SELECT mokkinimi FROM mokki WHERE alue_id = '" + alueid + "'");
    }

    public ObservableList<String> haeAlueenpalvelut(String alue) throws SQLException {
        String alueid = String.valueOf(haeAlueenID(alue));
        alueid = alueid.replaceAll("[\\[\\](){}]", "");
        return executeQuery("SELECT nimi FROM palvelu WHERE alue_id = '" + alueid + "'");
    }

    public ObservableList<Integer> haePostriNrot() throws SQLException {
        return executeQueryINT("SELECT postinro FROM posti");
    }

    public void mokinArvo() throws SQLException {
        ResultSet set = statement.executeQuery("SELECT hinta FROM mokki");
    }


    static class Palvelu {
        int palveluId;
        int alueId;
        String nimi;
        String kuvaus;
        Double hinta;
        Double alv;

        public Palvelu(int palveluId, int alueId, String nimi, String kuvaus, Double hinta, Double alv) {
            this.palveluId = palveluId;
            this.alueId = alueId;
            this.nimi = nimi;
            this.kuvaus = kuvaus;
            this.hinta = hinta;
            this.alv = alv;
        }
    }

    public static Palvelu fetchPalvelu(int palveluId) {
        Palvelu palvelu = null;
        String sql = "SELECT * FROM palvelu WHERE palvelu_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, palveluId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int alueId = rs.getInt("alue_id");
                String nimi = rs.getString("nimi");
                String kuvaus = rs.getString("kuvaus");
                Double hinta = rs.getDouble("hinta");
                Double alv = rs.getDouble("alv");

                palvelu = new Palvelu(palveluId, alueId, nimi, kuvaus, hinta, alv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palvelu;


    }

    public static int fetchPalveluId(String palvelunNimi) {
        String sql = "SELECT palvelu_id FROM palvelu WHERE nimi = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, palvelunNimi);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("palvelu_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    static class Asiakas {
        int asiakasId;
        int postiNro;
        String etunimi;
        String sukunimi;
        String lahiosoite;
        String email;
        int puhelinumero;

        public Asiakas(int asiakasId, int postiNro, String etunimi, String sukunimi, String lahiosoite, String email, int puhelinumero) {
            this.asiakasId = asiakasId;
            this.postiNro = postiNro;
            this.etunimi = etunimi;
            this.sukunimi = sukunimi;
            this.lahiosoite = lahiosoite;
            this.email = email;
            this.puhelinumero = puhelinumero;
        }
    }

    public static Asiakas fetchAsiakas(int asiakasId) {
        Asiakas asiakas = null;
        String sql = "SELECT * FROM asiakas WHERE asiakas_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, asiakasId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int postiNro = rs.getInt("postinro");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String lahiosoite = rs.getString("lahiosoite");
                String email = rs.getString("email");
                int puhelinumero = rs.getInt("puhelinnro");


                asiakas = new Asiakas(asiakasId, postiNro, etunimi, sukunimi, lahiosoite, email, puhelinumero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asiakas;

    }

    public static String fetchAsiakaanPosti(String postiPaikka) {
        String sql = "SELECT toimipaikka FROM posti WHERE postinro = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, postiPaikka);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("toimipaikka");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int fetchAsiakkaanIDsahkopostilla(String email) {
        String sql = "SELECT asiakas_id FROM asiakas WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("asiakas_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    static class Mokki {

        int mokkiId;
        int alueId;
        int postiNro;
        String mokkiNimi;
        String katuOsoite;
        Double hinta;
        String kuvaus;
        int henkilomaara;
        String varustelu;
        public SimpleStringProperty mokkinimi;
        public SimpleStringProperty mokinhenkilomaara;
        public SimpleStringProperty mokinHinta;
        public SimpleStringProperty mokinAlue;
        public SimpleStringProperty mokinKuvaus;
        public SimpleStringProperty mokinVarustelu;
        public SimpleStringProperty mokinOsoite;


        public Mokki(int mokkiId, int alueId, int postiNro, String mokkiNimi, String katuOsoite, double hinta, String kuvaus, int henkilomaara, String varustelu) {
            this.mokkiId = mokkiId;
            this.alueId = alueId;
            this.postiNro = postiNro;
            this.mokkiNimi = mokkiNimi;
            this.katuOsoite = katuOsoite;
            this.hinta = hinta;
            this.kuvaus = kuvaus;
            this.henkilomaara = henkilomaara;
            this.varustelu = varustelu;
        }

        public void setSimpleStringProperty(String nimi, int hmaara, double mokinHinta, String alue, String kuvaus, String varustelu, String osoite) {
            this.mokkinimi = new SimpleStringProperty(nimi);
            this.mokinhenkilomaara = new SimpleStringProperty(String.valueOf(hmaara));
            this.mokinHinta = new SimpleStringProperty(String.valueOf(mokinHinta));
            this.mokinAlue = new SimpleStringProperty(alue);
            this.mokinKuvaus = new SimpleStringProperty(kuvaus);
            this.mokinVarustelu = new SimpleStringProperty(varustelu);
            this.mokinOsoite = new SimpleStringProperty(osoite);
        }

        public StringProperty getNimi() {
            return mokkinimi;
        }

        public StringProperty getHenkilo() {
            return mokinhenkilomaara;
        }

        public StringProperty getMokinHinta() {
            return mokinHinta;
        }

        public StringProperty getAlue() {
            return mokinAlue;
        }

        public StringProperty getMokinKuvaus() {
            return mokinKuvaus;
        }

        public StringProperty getMokinVarustelu() {
            return mokinVarustelu;
        }

        public StringProperty getMokinOsoite() {
            return mokinOsoite;
        }

        public int getMokkiId() {
            return mokkiId;
        }

        public int getAlueId() {
            return alueId;
        }

        public int getPostiNro() {
            return postiNro;
        }

        public String getMokkiNimi() {
            return mokkiNimi;
        }

        public String getKatuOsoite() {
            return katuOsoite;
        }

        public Double getHinta() {
            return hinta;
        }

        public String getKuvaus() {
            return kuvaus;
        }

        public int getHenkilomaara() {
            return henkilomaara;
        }

        public String getVarustelu() {
            return varustelu;
        }
    }


    public static Mokki fetchMokki(int mokkiId) {
        Mokki mokki = null;
        String sql = "SELECT * FROM mokki WHERE mokki_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, mokkiId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int alueId = rs.getInt("alue_id");
                int postiNro = rs.getInt("postinro");
                String mokkiNimi = rs.getString("mokkinimi");
                String katuOsoite = rs.getString("katuosoite");
                double hinta = rs.getDouble("hinta");
                String kuvaus = rs.getString("kuvaus");
                int henkilomaara = rs.getInt("henkilomaara");
                String varustelu = rs.getString("varustelu");


                mokki = new Mokki(mokkiId, alueId, postiNro, mokkiNimi, katuOsoite, hinta, kuvaus, henkilomaara, varustelu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mokki;
    }

    public static int fetchMokkiId(String mokinNimi) {
        String sql = "SELECT mokki_id FROM mokki WHERE mokkinimi = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, mokinNimi);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("mokki_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static ObservableList<Mokki> fetchMokkiAll(int alueId, double hinta, int henkilomaara) {
        String sql = "SELECT * FROM mokki WHERE alue_id = ? AND hinta <= ? AND henkilomaara >= ?";
        ObservableList<Mokki> mokkiList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, alueId);
            statement.setDouble(2, hinta);
            statement.setInt(3, henkilomaara);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int mokkiId = rs.getInt("mokki_id");
                int mokkialueId = rs.getInt("alue_id");
                int postiNro = rs.getInt("postinro");
                String mokkiNimi = rs.getString("mokkinimi");
                String katuOsoite = rs.getString("katuosoite");
                double mokkihinta = rs.getDouble("hinta");
                String kuvaus = rs.getString("kuvaus");
                int mokkihenkilomaara = rs.getInt("henkilomaara");
                String varustelu = rs.getString("varustelu");


                Mokki mokki = new Mokki(mokkiId, mokkialueId, postiNro, mokkiNimi, katuOsoite, mokkihinta, kuvaus, mokkihenkilomaara, varustelu);
                mokkiList.add(mokki);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mokkiList;
    }


    public static String fetchAlueNimi(int alueId) {
        String sql = "SELECT nimi FROM alue WHERE alue_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, alueId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("nimi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int fetchAlueID(String alue) {
        String sql = "SELECT alue_id FROM alue WHERE nimi = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, alue);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("alue_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ListView<String> haeLaskut() throws SQLException {
        ObservableList<String> laskuList = FXCollections.observableArrayList();

        String query = "SELECT L.*, A.Etunimi, A.Sukunimi " +
                "FROM Lasku L " +
                "INNER JOIN Varaus V ON L.Varaus_id = V.Varaus_id " +
                "INNER JOIN Asiakas A ON V.Asiakas_id = A.Asiakas_id";

        ResultSet resultSet = statement.executeQuery(query);
        try {
            while (resultSet.next()) {
                int laskuId = resultSet.getInt("Lasku_id");
                int varausId = resultSet.getInt("Varaus_id");
                double summa = resultSet.getDouble("Summa");
                double alv = resultSet.getDouble("Alv");
                int maksettu = resultSet.getInt("Maksettu");
                String etunimi = resultSet.getString("Etunimi");
                String sukunimi = resultSet.getString("Sukunimi");

                String maksettuStr = (maksettu == 1) ? "kyllä" : "ei";
                laskuList.add("Lasku ID: " + laskuId + ", Varaus ID: " + varausId +
                        ", Summa: " + summa + ", Alv: " + alv +
                        ", Asiakas: " + etunimi + " " + sukunimi +
                        ", Maksettu: " + maksettuStr);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        ListView<String> laskuListView = new ListView<>(laskuList);
        return laskuListView;
    }

    public void merkitseLaskuMaksetuksi(int laskuId) throws SQLException {
        String query = "UPDATE Lasku SET Maksettu = 1 WHERE Lasku_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, laskuId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}