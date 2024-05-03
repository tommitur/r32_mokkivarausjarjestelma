package ohjtuotanto.varausjarjestelma;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Paaohjelma extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {

        SqlKomennot komennot = new SqlKomennot();

        HBox valinnoille = new HBox(10);
        valinnoille.setPadding(new Insets(10,10,10,10));

        Label paikkakunta = new Label();
        paikkakunta.setText("Paikkakunta: ");

        ObservableList<String>alueidenlista = FXCollections.observableArrayList();
        alueidenlista = komennot.valitseKaikkiAlueet();
        ComboBox alueet = new ComboBox(FXCollections.observableArrayList(alueidenlista));

        Label hinta = new Label();
        hinta.setText("hinta €");

        Slider hinnansaato = new Slider(0,1000,0);
        hinnansaato.setOrientation(Orientation.HORIZONTAL);
        hinnansaato.setShowTickLabels(true);
        hinnansaato.setShowTickMarks(true);
        hinnansaato.setMajorTickUnit(50);
        hinnansaato.setBlockIncrement(50);

        Button hae = new Button("Hae");

        valinnoille.getChildren().addAll(paikkakunta, alueet, hinta, hinnansaato, hae);
        valinnoille.setAlignment(Pos.TOP_LEFT);




        Scene paavalikko = new Scene(valinnoille, 600, 400);

        TextField kayttajatunnustf = new TextField();
        TextField salasanatf = new TextField();

        Button kirjaudu = new Button("Kirjaudu");
        kayttajatunnustf.setMaxWidth(100);
        salasanatf.setMaxWidth(100);

        VBox kirjautumisetvbox  = new VBox(15);
        kirjautumisetvbox.getChildren().addAll(kayttajatunnustf,salasanatf,kirjaudu);
        kirjautumisetvbox.setAlignment(Pos.CENTER);




        kirjaudu.setOnAction(e->{
            if (kayttajatunnustf.getText().equals("testi") && salasanatf.getText().equals("123")){
                primaryStage.setScene(paavalikko);
            }
            else{
                System.out.println("Salasana väärin");
            }
        });

        Scene kirjautuminen = new Scene(kirjautumisetvbox,500,500);

        Button lisaaAlue = new Button("Lisää uusi alue");
        Button lisaaMokki = new Button("Lisää uusi mökki");
        Button lisaaPalvelu = new Button("Lisää uusi palvelu");
        Button lisaaAsiakas = new Button("Lisää uusi asiakas");

        Button muokkaaAlue = new Button("Muokkaa aluetta");
        Button muokkaaMokki = new Button("Muokkaa mökkiä");
        Button muokkaaPalvelu = new Button("Muokkaa palvelua");
        Button muokkaaAsiakas = new Button("Muokkaa asiakastietoa");

        Button poistaAlue = new Button("Poista alue");
        Button poistaMokki = new Button("Poista mökki");
        Button poistaPalvelu = new Button("Poista palvelu");
        Button poistaAsiakas = new Button("Poista asiakastieto");

        lisaaAsiakas.setStyle("-fx-border-color: Green");
        lisaaMokki.setStyle("-fx-border-color: Green");
        lisaaPalvelu.setStyle("-fx-border-color: Green");
        lisaaAlue.setStyle("-fx-border-color: Green");

        muokkaaAlue.setStyle("-fx-border-color: Blue");
        muokkaaMokki.setStyle("-fx-border-color: Blue");
        muokkaaAsiakas.setStyle("-fx-border-color: Blue");
        muokkaaPalvelu.setStyle("-fx-border-color: Blue");

        poistaAlue.setStyle("-fx-border-color: Red");
        poistaPalvelu.setStyle("-fx-border-color: Red");
        poistaAsiakas.setStyle("-fx-border-color: Red");
        poistaMokki.setStyle("-fx-border-color: Red");

        lisaaAlue.setPrefSize(140,100);
        lisaaMokki.setPrefSize(140,100);
        lisaaPalvelu.setPrefSize(140,100);
        lisaaAsiakas.setPrefSize(140,100);
        muokkaaAlue.setPrefSize(140,100);
        muokkaaMokki.setPrefSize(140,100);
        muokkaaPalvelu.setPrefSize(140,100);
        muokkaaAsiakas.setPrefSize(140,100);
        poistaAlue.setPrefSize(140,100);
        poistaMokki.setPrefSize(140,100);
        poistaPalvelu.setPrefSize(140,100);;
        poistaAsiakas.setPrefSize(140,100);


        GridPane kaikkiMuokattavat = new GridPane(15,15);

        kaikkiMuokattavat.add(lisaaAlue,0,0);
        kaikkiMuokattavat.add(lisaaMokki,1,0);
        kaikkiMuokattavat.add(lisaaPalvelu,2,0);
        kaikkiMuokattavat.add(lisaaAsiakas,3,0);

        kaikkiMuokattavat.add(muokkaaAlue,0,1);
        kaikkiMuokattavat.add(muokkaaMokki,1,1);
        kaikkiMuokattavat.add(muokkaaPalvelu,2,1);
        kaikkiMuokattavat.add(muokkaaAsiakas,3,1);

        kaikkiMuokattavat.add(poistaAlue,0,2);
        kaikkiMuokattavat.add(poistaMokki,1,2);
        kaikkiMuokattavat.add(poistaPalvelu,2,2);
        kaikkiMuokattavat.add(poistaAsiakas,3,2);

        Label alueennimilb = new Label("Alueen nimi");
        TextField alueennimitf = new TextField();
        Button lisaaAluebt = new Button("Lisää");
        GridPane aluidentiedotGP = new GridPane(15,15);

        aluidentiedotGP.add(alueennimilb,0,0);
        aluidentiedotGP.add(alueennimitf,1,0);
        aluidentiedotGP.add(lisaaAluebt,1,1);

        Button lisaaPalvelubt = new Button("Lisää");
        Label palvelunNimilb = new Label("Palvelun nimi");
        Label palvelunKuvauslb = new Label("Palvelun kuvaus");
        Label palvelunHintalb = new Label("Palvelun hinta");
        Label palvelunAlvlb = new Label("Palvelun alv");
        Label palvelunAlueenlb = new Label("Palvelun alueen nimi");
        TextField palvelunnimitf = new TextField();
        TextArea palvelunkuvaustf = new TextArea();
        TextField palvelunhintatf = new TextField();
        TextField palvelunAlvtf = new TextField();
        ComboBox palvelunAlueencb = new ComboBox();
        palvelunAlueencb.setPrefSize(100,10);
        palvelunkuvaustf.setPrefSize(100,80);
        palvelunkuvaustf.setWrapText(true);
        integerinTarkistus(palvelunhintatf);
        integerinTarkistus(palvelunAlvtf);

        GridPane palveluidentiedotGP = new GridPane(15,15);
        palveluidentiedotGP.add(palvelunNimilb,0,0);
        palveluidentiedotGP.add(palvelunnimitf,1,0);
        palveluidentiedotGP.add(palvelunKuvauslb,0,1);
        palveluidentiedotGP.add(palvelunkuvaustf,1,1);
        palveluidentiedotGP.add(palvelunHintalb,0,2);
        palveluidentiedotGP.add(palvelunhintatf,1,2);
        palveluidentiedotGP.add(palvelunAlvlb,0,3);
        palveluidentiedotGP.add(palvelunAlvtf,1,3);
        palveluidentiedotGP.add(palvelunAlueenlb,0,4);
        palveluidentiedotGP.add(palvelunAlueencb,1,4);
        palveluidentiedotGP.add(lisaaPalvelubt,1,5);


        Button lisaaAsiakasbt = new Button("Lisää");
        Label asiakaanNimilb = new Label("Etunimi");
        Label asiakaanSukunimilb = new Label("Sukunimi ");
        Label asiakaanOsoitelb = new Label("Osoite");
        Label asiakaanPostinumerolb = new Label("Postinumero");
        Label asiakaanSahkopostilb = new Label("Sähköposti");
        Label asiakaanPuhelinnrolb = new Label("Puhelinnumero");
        TextField asiakaanNimitf = new TextField();
        TextField asiakaanSukunimitf = new TextField();
        TextField asiakaanOsoitetf = new TextField();
        TextField asiakaanPostinumerotf = new TextField();
        TextField asiakaanSahkopostitf = new TextField();
        TextField asiakaanPuhelinnrotf = new TextField();
        integerinTarkistus(asiakaanPostinumerotf);
        postiNroTarkistus(asiakaanPostinumerotf);
        integerinTarkistus(asiakaanPuhelinnrotf);

        GridPane asiakaantiedotGP = new GridPane(15,15);
        asiakaantiedotGP.add(asiakaanNimilb,0,0);
        asiakaantiedotGP.add(asiakaanNimitf,1,0);
        asiakaantiedotGP.add(asiakaanSukunimilb,0,1);
        asiakaantiedotGP.add(asiakaanSukunimitf,1,1);
        asiakaantiedotGP.add(asiakaanOsoitelb,0,2);
        asiakaantiedotGP.add(asiakaanOsoitetf,1,2);
        asiakaantiedotGP.add(asiakaanPostinumerolb,0,3);
        asiakaantiedotGP.add(asiakaanPostinumerotf,1,3);
        asiakaantiedotGP.add(asiakaanSahkopostilb,0,4);
        asiakaantiedotGP.add(asiakaanSahkopostitf,1,4);
        asiakaantiedotGP.add(asiakaanPuhelinnrolb,0,5);
        asiakaantiedotGP.add(asiakaanPuhelinnrotf,1,5);
        asiakaantiedotGP.add(lisaaAsiakasbt,0,6);

        Button lisaaMokkibt = new Button("Lisää");
        Label mokinNimilb = new Label("Mökin nimi");
        Label mokinOsoitelb = new Label("Mökin osoite ");
        Label mokinHintalb = new Label("Mökin päivävuokran hinta");
        Label mokinKuvaslb = new Label("Mökin kuvaus");
        Label mokinHenkilomaaralb = new Label("Mökin henkilömäärä");
        Label mokinVaruselulb = new Label("Mökinvarustelu");
        TextField mokinNimitf = new TextField();
        TextField mokinOsoitetf = new TextField();
        TextField mokinHintatf = new TextField();
        TextArea mokinKuvaustf = new TextArea();
        TextField mokinHenkilomaaratf = new TextField();
        TextArea mokinVaruselutf = new TextArea();
        integerinTarkistus(mokinHintatf);
        integerinTarkistus(mokinHenkilomaaratf);
        mokinKuvaustf.setPrefSize(100,80);
        mokinVaruselutf.setPrefSize(100,80);
        mokinKuvaustf.setWrapText(true);
        mokinVaruselutf.setWrapText(true);




        GridPane mokintiedotGP = new GridPane(15,15);
        mokintiedotGP.add(mokinNimilb,0,0);
        mokintiedotGP.add(mokinNimitf,1,0);
        mokintiedotGP.add(mokinOsoitelb,0,1);
        mokintiedotGP.add(mokinOsoitetf,1,1);
        mokintiedotGP.add(mokinHintalb,0,2);
        mokintiedotGP.add(mokinHintatf,1,2);
        mokintiedotGP.add(mokinKuvaslb,0,3);
        mokintiedotGP.add(mokinKuvaustf,1,3);
        mokintiedotGP.add(mokinHenkilomaaralb,0,4);
        mokintiedotGP.add(mokinHenkilomaaratf,1,4);
        mokintiedotGP.add(mokinVaruselulb,0,5);
        mokintiedotGP.add(mokinVaruselutf,1,5);
        mokintiedotGP.add(lisaaMokkibt,1,6);



        Scene mokinLisausValikko = new Scene(mokintiedotGP,500,500);
        mokintiedotGP.setAlignment(Pos.CENTER);

        Scene asiakaanLisausValikko = new Scene(asiakaantiedotGP,500,500);
        asiakaantiedotGP.setAlignment(Pos.CENTER);

        Scene alueenLisausValikko = new Scene(aluidentiedotGP,500,500);
        aluidentiedotGP.setAlignment(Pos.CENTER);

        Scene palveluidenLisausValikko = new Scene(palveluidentiedotGP,500,500);
        palveluidentiedotGP.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane(kaikkiMuokattavat);
        kaikkiMuokattavat.setAlignment(Pos.CENTER);

        Scene muokkaausvalikko = new Scene(pane,640,400);


        primaryStage.setTitle("Mökkivarausjärjestelmä");
        primaryStage.setScene(paavalikko);
        primaryStage.show();

    }
    private void integerinTarkistus (TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    private void postiNroTarkistus(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}")) {
                textField.setText(oldValue);
            }
        });
    }

}