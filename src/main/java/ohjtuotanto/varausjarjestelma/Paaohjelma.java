package ohjtuotanto.varausjarjestelma;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Paaohjelma extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    public ObservableList<String> listaAlueista;
    public ObservableList<String> mokinAlueet;
    public ObservableList<String> mokit;
    public ObservableList<String> listaAlueistaPalveluille;
    public ObservableList<String> palvelutlista;
    ObservableList<Integer> asiakkaanID;
    public ComboBox alueMuokkauscb;
    public int palvelunIDmuokkaukseen;
    public String asiakkaanIDmuokkaukseen;
    public String mokinNimimuokkaukseen;

    @Override
    public void start(Stage primaryStage) throws SQLException {

        SqlKomennot komennot = new SqlKomennot();

        BorderPane asettelu = new BorderPane();

        HBox kaikille = new HBox(50);
        kaikille.setPadding(new Insets(15, 10, 15, 10));
        kaikille.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        HBox alueelle = new HBox(5);
        HBox sliderille = new HBox(5);
        VBox hinnalle = new VBox();

        Text paikkakunta = new Text("Paikkakunta:");

        ObservableList<String> alueidenlista = FXCollections.observableArrayList();
        alueidenlista = komennot.valitseKaikkiAlueet();
        ComboBox alueet = new ComboBox(FXCollections.observableArrayList(alueidenlista));
        alueet.setPromptText("Valitse");

        Text hinta0 = new Text("hinta/yö 0€");
        Text rahanArvo = new Text("0€");
        Text hinta1000 = new Text("1000€");

        Slider hinnansaato = new Slider(0, 1000, 0);
        hinnansaato.setOrientation(Orientation.HORIZONTAL);
        hinnansaato.setBlockIncrement(100);
        hinnansaato.setShowTickMarks(true);
        hinnansaato.setMajorTickUnit(250);

        final double raja = 100.0; //finaali

        hinnansaato.setOnMouseDragged(event -> {
            double newValue = Math.round(hinnansaato.getValue() / raja) * raja; // Pyöristetään sadan välein
            hinnansaato.setValue(newValue);
        });

        hinnansaato.valueProperty().addListener((o, oldValue, newValue) -> {
            double newHinta = newValue.intValue();
            rahanArvo.setText("0-" + newHinta + "€");
        });


        ComboBox<Integer> vieraat = new ComboBox<>();
        vieraat.setPromptText("vieraiden lkm");
        vieraat.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        ));

        Button haebt = new Button("Hae");
        haebt.setMinWidth(50);

        Button muokkaa = new Button("Muokkaa tietoja");
        muokkaa.setMinWidth(50);

        VBox varaus = new VBox(5);
        varaus.setPadding(new Insets(10,10,10,10));
        Button varaa = new Button("Varaa");
        varaa.setMinWidth(50);

        alueelle.getChildren().addAll(paikkakunta, alueet);

        hinnalle.getChildren().addAll(hinnansaato, rahanArvo);
        hinnalle.setAlignment(Pos.CENTER);

        varaus.getChildren().add(varaa);
        varaus.setAlignment(Pos.BOTTOM_CENTER);

        sliderille.getChildren().addAll(hinta0, hinnalle, hinta1000);

        kaikille.getChildren().addAll(alueelle, sliderille, vieraat, haebt, muokkaa);
        asettelu.setBottom(varaus);
        asettelu.setTop(kaikille);

        Scene paavalikko = new Scene(asettelu, 900, 500);

        TextField kayttajatunnustf = new TextField();
        TextField salasanatf = new TextField();

        Button kirjaudu = new Button("Kirjaudu");
        kayttajatunnustf.setMaxWidth(100);
        salasanatf.setMaxWidth(100);

        VBox kirjautumisetvbox = new VBox(15);
        kirjautumisetvbox.getChildren().addAll(kayttajatunnustf, salasanatf, kirjaudu);
        kirjautumisetvbox.setAlignment(Pos.CENTER);


        haebt.setOnAction(e -> {
            System.out.println(SqlKomennot.fetchMokkiAll(5, 2, 1));
        });


        kirjaudu.setOnAction(e -> {
            if (kayttajatunnustf.getText().equals("testi") && salasanatf.getText().equals("123")) {
                primaryStage.setScene(paavalikko);
            } else {
                System.out.println("Salasana väärin");
            }
        });

        Scene kirjautuminen = new Scene(kirjautumisetvbox, 500, 500);

        Button lisaaAlue = new Button("Lisää uusi alue");
        Button lisaaMokki = new Button("Lisää uusi mökki");
        Button lisaaPalvelu = new Button("Lisää uusi palvelu");
        Button lisaaAsiakas = new Button("Lisää uusi asiakas");

        Button takaisinAlue = new Button("Takaisin");
        Button takaisinMokki = new Button("Takaisin");
        Button takaisinPalvelu = new Button("Takaisin");
        Button takaisinAsiakas = new Button("Takaisin");

        Button muokkaaAlue = new Button("Muokkaa/poista\n      alueita");
        Button muokkaaMokki = new Button("Muokkaa/poista\n     mökkejä");
        Button muokkaaPalvelu = new Button("Muokkaa/poista\n     palveluita");
        Button muokkaaAsiakas = new Button("Muokkaa/poista\n    asiakastietoja");

        lisaaAsiakas.setStyle("-fx-border-color: Green");
        lisaaMokki.setStyle("-fx-border-color: Green");
        lisaaPalvelu.setStyle("-fx-border-color: Green");
        lisaaAlue.setStyle("-fx-border-color: Green");

        muokkaaAlue.setStyle("-fx-border-color: Blue");
        muokkaaMokki.setStyle("-fx-border-color: Blue");
        muokkaaAsiakas.setStyle("-fx-border-color: Blue");
        muokkaaPalvelu.setStyle("-fx-border-color: Blue");

        lisaaAlue.setPrefSize(140, 100);
        lisaaMokki.setPrefSize(140, 100);
        lisaaPalvelu.setPrefSize(140, 100);
        lisaaAsiakas.setPrefSize(140, 100);
        muokkaaAlue.setPrefSize(140, 100);
        muokkaaMokki.setPrefSize(140, 100);
        muokkaaPalvelu.setPrefSize(140, 100);
        muokkaaAsiakas.setPrefSize(140, 100);

        GridPane kaikkiMuokattavat = new GridPane(15, 15);

        kaikkiMuokattavat.add(lisaaAlue, 0, 0);
        kaikkiMuokattavat.add(lisaaMokki, 1, 0);
        kaikkiMuokattavat.add(lisaaPalvelu, 2, 0);
        kaikkiMuokattavat.add(lisaaAsiakas, 3, 0);

        kaikkiMuokattavat.add(muokkaaAlue, 0, 1);
        kaikkiMuokattavat.add(muokkaaMokki, 1, 1);
        kaikkiMuokattavat.add(muokkaaPalvelu, 2, 1);
        kaikkiMuokattavat.add(muokkaaAsiakas, 3, 1);

        // Alueen lisäys
        Label alueennimilb = new Label("Alueen nimi");
        TextField alueennimitf = new TextField();
        Button lisaaAluebt = new Button("Lisää");
        GridPane aluidentiedotGP = new GridPane(15, 15);
        BorderPane alueBP = new BorderPane();

        //Alueen muokkaus
        Button muokkaabt = new Button("Muokkaa");
        muokkaabt.setVisible(false);
        Button poistabt = new Button("Poista");
        poistabt.setVisible(false);
        listaAlueista = komennot.valitseKaikkiAlueet();
        alueMuokkauscb = new ComboBox(FXCollections.observableArrayList(listaAlueista));
        alueMuokkauscb.setMinWidth(100);
        alueMuokkauscb.setVisible(false);
        HBox alueHBox = new HBox(150);
        alueHBox.setPadding(new Insets(5, 5, 5, 5));
        alueHBox.getChildren().addAll(takaisinAlue, alueMuokkauscb);
        HBox alueButtonit = new HBox(15);
        alueButtonit.getChildren().addAll(lisaaAluebt, muokkaabt, poistabt);
        Label aluemuokkausohje = new Label("Valitse alue ylhäältä\nja voit joko muokata sen nimeä\ntai poistaa sen");
        aluemuokkausohje.setVisible(false);

        aluidentiedotGP.add(alueennimilb, 0, 0);
        aluidentiedotGP.add(alueennimitf, 1, 0);
        aluidentiedotGP.add(alueButtonit, 1, 1);
        aluidentiedotGP.add(aluemuokkausohje, 1, 2);
        alueBP.setCenter(aluidentiedotGP);
        alueBP.setTop(alueHBox);

        Button lisaaPalvelubt = new Button("Lisää");
        Label palvelunNimilb = new Label("Palvelun nimi");
        Label palvelunKuvauslb = new Label("Palvelun kuvaus");
        Label palvelunHintalb = new Label("Palvelun hinta");
        Label palvelunAlvlb = new Label("Palvelun alv");
        Label palvelunAlueenlb = new Label("Palvelun alueen nimi");
        Label palvelunIDlb = new Label("Palvelun id");
        TextField palvelunnimitf = new TextField();
        TextArea palvelunkuvaustf = new TextArea();
        TextField palvelunhintatf = new TextField();
        TextField palvelunAlvtf = new TextField();
        TextField palvelunIDtf = new TextField();
        listaAlueistaPalveluille = komennot.valitseKaikkiAlueet();
        ComboBox palvelunAlueencb = new ComboBox(FXCollections.observableArrayList(listaAlueistaPalveluille));
        palvelunAlueencb.setPrefSize(100, 10);
        palvelunkuvaustf.setPrefSize(100, 80);
        numeronTarkistus(palvelunIDtf);
        palvelunkuvaustf.setWrapText(true);
        numeronTarkistus(palvelunhintatf);
        numeronTarkistus(palvelunAlvtf);
        BorderPane palveluBP = new BorderPane();

        //Palvelun muokkaamis scenen honmia
        palvelutlista = komennot.valitseKaikkiPalvelut();
        ComboBox muokkaaPalveluitacb = new ComboBox(FXCollections.observableArrayList(palvelutlista));
        muokkaaPalveluitacb.setVisible(false);
        muokkaaPalveluitacb.setMinWidth(100);
        HBox palvelutHBox = new HBox(15);
        Button palveluMuokkaabt = new Button("Muokkaa");
        palveluMuokkaabt.setVisible(false);
        Button palveluPoistabt = new Button("Poista");
        palveluPoistabt.setVisible(false);
        Label palvelunmuokkausohje = new Label("Valitse palvelu ylhäältä\nja voit muokata sen tietoja\ntai poistaa sen");
        palvelunmuokkausohje.setVisible(false);
        palvelutHBox.getChildren().addAll(lisaaPalvelubt, palveluMuokkaabt, palveluPoistabt);

        GridPane palveluidentiedotGP = new GridPane(15, 15);
        palveluidentiedotGP.add(palvelunNimilb, 0, 0);
        palveluidentiedotGP.add(palvelunnimitf, 1, 0);
        palveluidentiedotGP.add(palvelunKuvauslb, 0, 1);
        palveluidentiedotGP.add(palvelunkuvaustf, 1, 1);
        palveluidentiedotGP.add(palvelunHintalb, 0, 2);
        palveluidentiedotGP.add(palvelunhintatf, 1, 2);
        palveluidentiedotGP.add(palvelunAlvlb, 0, 3);
        palveluidentiedotGP.add(palvelunAlvtf, 1, 3);
        palveluidentiedotGP.add(palvelunIDlb, 0, 4);
        palveluidentiedotGP.add(palvelunIDtf, 1, 4);
        palveluidentiedotGP.add(palvelunAlueenlb, 0, 5);
        palveluidentiedotGP.add(palvelunAlueencb, 1, 5);
        palveluidentiedotGP.add(palvelutHBox, 1, 6);
        palveluidentiedotGP.add(muokkaaPalveluitacb, 2, 0);
        palveluidentiedotGP.add(palvelunmuokkausohje, 2, 1);
        palveluBP.setCenter(palveluidentiedotGP);
        palveluBP.setTop(takaisinPalvelu);


        Button lisaaAsiakasbt = new Button("Lisää");
        Label asiakaanNimilb = new Label("Etunimi");
        Label asiakaanSukunimilb = new Label("Sukunimi ");
        Label asiakaanOsoitelb = new Label("Osoite");
        Label asiakaanPostinumerolb = new Label("Postinumero");
        Label asiakaanSahkopostilb = new Label("Sähköposti");
        Label asiakaanPuhelinnrolb = new Label("Puhelinnumero");
        Label asiakkaanPostitoimipaikkalb = new Label("Postitoimipaikka");
        TextField asiakaanNimitf = new TextField();
        TextField asiakaanSukunimitf = new TextField();
        TextField asiakaanOsoitetf = new TextField();
        TextField asiakaanPostinumerotf = new TextField();
        TextField asiakaanSahkopostitf = new TextField();
        TextField asiakaanPuhelinnrotf = new TextField();
        TextField asiakkaanPostitoimipaikkatf = new TextField();
        numeronTarkistus(asiakaanPostinumerotf);
        postiNroTarkistus(asiakaanPostinumerotf);
        puhulinNroTarkistus(asiakaanPuhelinnrotf);
        BorderPane asiakasBP = new BorderPane();

        //Asiakkaan muokkaus
        asiakkaanID = komennot.valitseKaikkiAsiakkaat();
        ComboBox asiakkaanMuokkauscb = new ComboBox(FXCollections.observableArrayList(asiakkaanID));
        asiakkaanMuokkauscb.setVisible(false);
        asiakkaanMuokkauscb.setMinWidth(100);
        HBox asiakasHBox = new HBox(15);
        Button asiakasMuokkaabt = new Button("Muokkaa");
        asiakasMuokkaabt.setVisible(false);
        Button asiakasPoistabt = new Button("Poista");
        asiakasPoistabt.setVisible(false);
        asiakasHBox.getChildren().addAll(lisaaAsiakasbt, asiakasMuokkaabt, asiakasPoistabt);
        Label asiakkaanmuokkausohje = new Label("Valitse asiakas ylhäältä\nja voit muokata heidän tietoja\ntai poistaa heidät");
        asiakkaanmuokkausohje.setVisible(false);

        GridPane asiakaantiedotGP = new GridPane(15, 15);
        asiakaantiedotGP.add(asiakaanNimilb, 0, 0);
        asiakaantiedotGP.add(asiakaanNimitf, 1, 0);
        asiakaantiedotGP.add(asiakaanSukunimilb, 0, 1);
        asiakaantiedotGP.add(asiakaanSukunimitf, 1, 1);
        asiakaantiedotGP.add(asiakaanOsoitelb, 0, 2);
        asiakaantiedotGP.add(asiakaanOsoitetf, 1, 2);
        asiakaantiedotGP.add(asiakaanPostinumerolb, 0, 3);
        asiakaantiedotGP.add(asiakaanPostinumerotf, 1, 3);
        asiakaantiedotGP.add(asiakkaanPostitoimipaikkalb, 0, 4);
        asiakaantiedotGP.add(asiakkaanPostitoimipaikkatf, 1, 4);
        asiakaantiedotGP.add(asiakaanSahkopostilb, 0, 5);
        asiakaantiedotGP.add(asiakaanSahkopostitf, 1, 5);
        asiakaantiedotGP.add(asiakaanPuhelinnrolb, 0, 6);
        asiakaantiedotGP.add(asiakaanPuhelinnrotf, 1, 6);
        asiakaantiedotGP.add(asiakasHBox, 1, 7);
        asiakaantiedotGP.add(asiakkaanMuokkauscb, 2, 0);
        asiakaantiedotGP.add(asiakkaanmuokkausohje, 2, 1);
        asiakasBP.setCenter(asiakaantiedotGP);
        asiakasBP.setTop(takaisinAsiakas);

        Button lisaaMokkibt = new Button("Lisää");
        Label mokinNimilb = new Label("Mökin nimi");
        Label mokinOsoitelb = new Label("Mökin osoite ");
        Label mokinHintalb = new Label("Mökin päivävuokran hinta");
        Label mokinKuvaslb = new Label("Mökin kuvaus");
        Label mokinHenkilomaaralb = new Label("Mökin henkilömäärä");
        Label mokinVaruselulb = new Label("Mökinvarustelu");
        Label mokinPostinrolb = new Label("Mökin postinumero");
        Label mokinAluelb = new Label("Mökin alue");
        mokinAlueet = komennot.valitseKaikkiAlueet();
        ComboBox mokinalueetcb = new ComboBox(FXCollections.observableArrayList(mokinAlueet));
        TextField mokinNimitf = new TextField();
        TextField mokinOsoitetf = new TextField();
        TextField mokinHintatf = new TextField();
        TextArea mokinKuvaustf = new TextArea();
        TextField mokinHenkilomaaratf = new TextField();
        TextArea mokinVaruselutf = new TextArea();
        TextField mokinPostinrotf = new TextField();
        postiNroTarkistus(mokinPostinrotf);
        numeronTarkistus(mokinHintatf);
        numeronTarkistus(mokinHenkilomaaratf);
        mokinKuvaustf.setPrefSize(100, 80);
        mokinVaruselutf.setPrefSize(100, 80);
        mokinKuvaustf.setWrapText(true);
        mokinVaruselutf.setWrapText(true);
        BorderPane mokkiBP = new BorderPane();

        //Mökkien muokkaus scene honma jutu
        mokit = komennot.valitseKaikkiMokit();
        ComboBox mokkienMuokkauscb = new ComboBox(FXCollections.observableArrayList(mokit));
        mokkienMuokkauscb.setMinWidth(100);
        mokkienMuokkauscb.setVisible(false);
        Button mokkiMuokkaabt = new Button("Muokkaa");
        Button mokkiPoistabt = new Button("Poista");
        HBox mokkiHBox = new HBox(15);
        mokkiHBox.getChildren().addAll(lisaaMokkibt, mokkiMuokkaabt, mokkiPoistabt);
        mokkiMuokkaabt.setVisible(false);
        mokkiPoistabt.setVisible(false);
        Label mokkienmuokkausohje = new Label("Valitse mökki ylhäältä\nja voit muokata sen tietoja\ntai poistaa sen");
        mokkienmuokkausohje.setVisible(false);


        GridPane mokintiedotGP = new GridPane(15, 15);
        mokintiedotGP.add(mokinNimilb, 0, 0);
        mokintiedotGP.add(mokinNimitf, 1, 0);
        mokintiedotGP.add(mokinOsoitelb, 0, 1);
        mokintiedotGP.add(mokinOsoitetf, 1, 1);
        mokintiedotGP.add(mokinHintalb, 0, 2);
        mokintiedotGP.add(mokinHintatf, 1, 2);
        mokintiedotGP.add(mokinKuvaslb, 0, 3);
        mokintiedotGP.add(mokinKuvaustf, 1, 3);
        mokintiedotGP.add(mokinHenkilomaaralb, 0, 4);
        mokintiedotGP.add(mokinHenkilomaaratf, 1, 4);
        mokintiedotGP.add(mokinVaruselulb, 0, 5);
        mokintiedotGP.add(mokinVaruselutf, 1, 5);
        mokintiedotGP.add(mokinPostinrolb, 0, 6);
        mokintiedotGP.add(mokinPostinrotf, 1, 6);
        mokintiedotGP.add(mokinAluelb, 0, 7);
        mokintiedotGP.add(mokinalueetcb, 1, 7);
        mokintiedotGP.add(mokkiHBox, 1, 8);
        mokintiedotGP.add(mokkienMuokkauscb, 2, 0);
        mokintiedotGP.add(mokkienmuokkausohje, 2, 1);
        mokkiBP.setCenter(mokintiedotGP);
        mokkiBP.setTop(takaisinMokki);


        Scene mokinLisausValikko = new Scene(mokkiBP, 550, 600);
        mokintiedotGP.setAlignment(Pos.CENTER);

        Scene asiakaanLisausValikko = new Scene(asiakasBP, 500, 500);
        asiakaantiedotGP.setAlignment(Pos.CENTER);

        Scene alueenLisausValikko = new Scene(alueBP, 500, 500);
        aluidentiedotGP.setAlignment(Pos.CENTER);

        Scene palveluidenLisausValikko = new Scene(palveluBP, 550, 550);
        palveluidentiedotGP.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane(kaikkiMuokattavat);
        kaikkiMuokattavat.setAlignment(Pos.CENTER);

        Scene muokkaausvalikko = new Scene(pane, 640, 400);

        primaryStage.setTitle("Mökkivarausjärjestelmä");
        primaryStage.setScene(paavalikko);
        primaryStage.show();

        //Alkuvalikon lisäysnapit
        muokkaa.setOnAction(e -> {
            primaryStage.setScene(muokkaausvalikko);
        });

        lisaaAlue.setOnAction(e -> {
            primaryStage.setScene(alueenLisausValikko);
        });
        lisaaMokki.setOnAction(e -> {
            primaryStage.setScene(mokinLisausValikko);
            try {
                mokinAlueet = komennot.valitseKaikkiAlueet();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            mokinalueetcb.setItems(FXCollections.observableArrayList(mokinAlueet));
        });
        lisaaPalvelu.setOnAction(e -> {
            try {
                listaAlueistaPalveluille = komennot.valitseKaikkiAlueet();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            palvelunAlueencb.setItems(FXCollections.observableArrayList(listaAlueistaPalveluille));
            primaryStage.setScene(palveluidenLisausValikko);
        });
        lisaaAsiakas.setOnAction(e -> {
            primaryStage.setScene(asiakaanLisausValikko);
        });

        //Alkuvalikon muokkausnapit
        muokkaaAlue.setOnAction(e -> {
            try {
                listaAlueista = komennot.valitseKaikkiAlueet();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            alueMuokkauscb.setItems(FXCollections.observableArrayList(listaAlueista));
            primaryStage.setScene(alueenLisausValikko);
            alueMuokkauscb.setVisible(true);
            lisaaAluebt.setVisible(false);
            muokkaabt.setVisible(true);
            poistabt.setVisible(true);
            aluemuokkausohje.setVisible(true);
        });
        muokkaaMokki.setOnAction(e -> {
            try {
                mokit = komennot.valitseKaikkiMokit();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            mokkienMuokkauscb.setItems(FXCollections.observableArrayList(mokit));
            primaryStage.setScene(mokinLisausValikko);
            mokkienMuokkauscb.setVisible(true);
            lisaaMokkibt.setVisible(false);
            mokkiMuokkaabt.setVisible(true);
            mokkiPoistabt.setVisible(true);
            mokkienmuokkausohje.setVisible(true);
        });
        muokkaaPalvelu.setOnAction(e -> {
            try {
                palvelutlista = komennot.valitseKaikkiPalvelut();
                listaAlueistaPalveluille = komennot.valitseKaikkiAlueet();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            muokkaaPalveluitacb.setItems(FXCollections.observableArrayList(palvelutlista));
            palvelunAlueencb.setItems(FXCollections.observableArrayList(listaAlueistaPalveluille));
            primaryStage.setScene(palveluidenLisausValikko);
            muokkaaPalveluitacb.setVisible(true);
            lisaaPalvelubt.setVisible(false);
            palveluMuokkaabt.setVisible(true);
            palveluPoistabt.setVisible(true);
            palvelunmuokkausohje.setVisible(true);
        });
        muokkaaAsiakas.setOnAction(e -> {
            try {
                asiakkaanID = komennot.valitseKaikkiAsiakkaat();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            asiakkaanMuokkauscb.setItems(FXCollections.observableArrayList(asiakkaanID));
            primaryStage.setScene(asiakaanLisausValikko);
            asiakkaanMuokkauscb.setVisible(true);
            lisaaAsiakasbt.setVisible(false);
            asiakasMuokkaabt.setVisible(true);
            asiakasPoistabt.setVisible(true);
            asiakkaanmuokkausohje.setVisible(true);
        });

        //Takaisin napit
        takaisinMokki.setOnAction(e -> {
            primaryStage.setScene(muokkaausvalikko);
            mokinNimitf.clear();
            mokinOsoitetf.clear();
            mokinHintatf.clear();
            mokinKuvaustf.clear();
            mokinHenkilomaaratf.clear();
            mokinVaruselutf.clear();
            mokinPostinrotf.clear();
            mokinalueetcb.setValue(null);
            mokkienMuokkauscb.setValue(null);
            if (mokkienMuokkauscb.isVisible()) {
                mokkienMuokkauscb.setVisible(false);
                lisaaMokkibt.setVisible(true);
                mokkiMuokkaabt.setVisible(false);
                mokkiPoistabt.setVisible(false);
                mokkienmuokkausohje.setVisible(false);
            }
        });
        takaisinAlue.setOnAction(e -> {
            primaryStage.setScene(muokkaausvalikko);
            alueMuokkauscb.setValue(null);
            alueennimitf.clear();
            if (alueMuokkauscb.isVisible()) {
                alueMuokkauscb.setVisible(false);
                lisaaAluebt.setVisible(true);
                muokkaabt.setVisible(false);
                poistabt.setVisible(false);
                aluemuokkausohje.setVisible(false);
            }
        });
        takaisinAsiakas.setOnAction(e -> {
            primaryStage.setScene(muokkaausvalikko);
            asiakaanNimitf.clear();
            asiakaanSukunimitf.clear();
            asiakaanOsoitetf.clear();
            asiakaanPostinumerotf.clear();
            asiakkaanPostitoimipaikkatf.clear();
            asiakaanSahkopostitf.clear();
            asiakaanPuhelinnrotf.clear();
            asiakkaanMuokkauscb.setValue(null);
            if (asiakkaanMuokkauscb.isVisible()) {
                asiakkaanMuokkauscb.setVisible(false);
                lisaaAsiakasbt.setVisible(true);
                asiakasMuokkaabt.setVisible(false);
                asiakasPoistabt.setVisible(false);
                asiakkaanmuokkausohje.setVisible(false);
            }
        });
        takaisinPalvelu.setOnAction(e -> {
            primaryStage.setScene(muokkaausvalikko);
            palvelunnimitf.clear();
            palvelunkuvaustf.clear();
            palvelunhintatf.clear();
            palvelunAlvtf.clear();
            palvelunIDtf.clear();
            muokkaaPalveluitacb.setValue(null);
            palvelunAlueencb.setValue(null);
            if (muokkaaPalveluitacb.isVisible()) {
                muokkaaPalveluitacb.setVisible(false);
                lisaaPalvelubt.setVisible(true);
                palveluMuokkaabt.setVisible(false);
                palveluPoistabt.setVisible(false);
                palvelunmuokkausohje.setVisible(false);
            }
        });

        alueMuokkauscb.setOnAction(e -> {
            Object selectedItem = alueMuokkauscb.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String data = selectedItem.toString();
                alueennimitf.setText(data);
            }
        });

        muokkaabt.setOnAction(e -> {
            try {
                if (!alueennimitf.getText().isEmpty()) {
                    komennot.updateQuery("update alue set nimi ='" + alueennimitf.getText() + "' where nimi = '" +
                            alueMuokkauscb.getValue() + "'");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.setScene(muokkaausvalikko);
            alueennimitf.clear();
            alueMuokkauscb.setValue(null);
            alueMuokkauscb.setVisible(false);
            lisaaAluebt.setVisible(true);
            muokkaabt.setVisible(false);
            poistabt.setVisible(false);
            aluemuokkausohje.setVisible(false);
        });

        poistabt.setOnAction(e -> {
            try {
                if(!komennot.valitseKaikkiAlueet().contains("Ei aluetta")){
                    System.out.println("Täällä");
                    komennot.updateQuery("insert into alue (nimi) values ('Ei aluetta')");
                    String eiAluettaid = String.valueOf(komennot.haeAlueenID("Ei aluetta"));
                    eiAluettaid = eiAluettaid.replaceAll("[\\[\\](){}]", "");

                    ObservableList<String> alueettomatMokit = komennot.haeAlueenmokit(String.valueOf(alueMuokkauscb.getValue()));
                    for(int i = 0; i < alueettomatMokit.size(); i++){
                        komennot.updateQuery("update mokki set alue_id = '" + eiAluettaid + "' where mokkinimi = '" + alueettomatMokit.get(i) + "'");
                    }

                    ObservableList<String> alueettomatPalvelut = komennot.haeAlueenpalvelut(String.valueOf(alueMuokkauscb.getValue()));
                    for(int i = 0; i < alueettomatPalvelut.size(); i++){
                        komennot.updateQuery("update palvelu set alue_id = '" + eiAluettaid + "' where nimi = '" + alueettomatPalvelut.get(i) + "'");
                    }
                }else{
                    String eiAluettaid = String.valueOf(komennot.haeAlueenID("Ei aluetta"));
                    eiAluettaid = eiAluettaid.replaceAll("[\\[\\](){}]", "");

                    ObservableList<String> alueettomatMokit = komennot.haeAlueenmokit(String.valueOf(alueMuokkauscb.getValue()));
                    for(int i = 0; i < alueettomatMokit.size(); i++){
                        komennot.updateQuery("update mokki set alue_id = '" + eiAluettaid + "' where mokkinimi = '" + alueettomatMokit.get(i) + "'");
                    }

                    ObservableList<String> alueettomatPalvelut = komennot.haeAlueenpalvelut(String.valueOf(alueMuokkauscb.getValue()));
                    for(int i = 0; i < alueettomatPalvelut.size(); i++){
                        komennot.updateQuery("update palvelu set alue_id = '" + eiAluettaid + "' where nimi = '" + alueettomatPalvelut.get(i) + "'");
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            try {
                if (!alueennimitf.getText().isEmpty()) {
                    komennot.updateQuery("delete from alue where nimi = '" +
                            alueMuokkauscb.getValue() + "'");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            primaryStage.setScene(muokkaausvalikko);
            alueennimitf.clear();
            alueMuokkauscb.setValue(null);
            alueMuokkauscb.setVisible(false);
            lisaaAluebt.setVisible(true);
            muokkaabt.setVisible(false);
            poistabt.setVisible(false);
            aluemuokkausohje.setVisible(false);
        });

        muokkaaPalveluitacb.setOnAction(e -> {
            Object selectedItem = muokkaaPalveluitacb.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String data = selectedItem.toString();
                int id = SqlKomennot.fetchPalveluId(data);
                palvelunIDmuokkaukseen = id;
                SqlKomennot.Palvelu palvelu = SqlKomennot.fetchPalvelu(id);
                palvelunnimitf.setText(palvelu.nimi);
                palvelunkuvaustf.setText(palvelu.kuvaus);
                palvelunhintatf.setText(palvelu.hinta.toString());
                palvelunAlvtf.setText(palvelu.alv.toString());
                palvelunIDtf.setText(String.valueOf(palvelu.palveluId));
                String alueenimi = SqlKomennot.fetchAlueNimi(palvelu.alueId);
                palvelunAlueencb.setValue(alueenimi);

            }
        });

        palveluMuokkaabt.setOnAction(e ->{
            try{
                if (palvelunnimitf.getText().isEmpty() || palvelunkuvaustf.getText().isEmpty() || palvelunhintatf.getText().isEmpty() ||
                        palvelunAlvtf.getText().isEmpty() || palvelunIDtf.getText().isEmpty() || palvelunAlueencb.getValue() == null) {
                    //Tietoja puuttuu
                } else {
                    String alueid = String.valueOf(komennot.haeAlueenID(String.valueOf(palvelunAlueencb.getValue())));
                    alueid = alueid.replaceAll("[\\[\\](){}]", "");
                    komennot.updateQuery("update palvelu set palvelu_id = '" + palvelunIDtf.getText() + "', alue_id = '" + alueid + "', nimi = '" + palvelunnimitf.getText() +
                            "', kuvaus = '" + palvelunkuvaustf.getText() + "', hinta = '" + palvelunhintatf.getText() + "', alv = '" + palvelunAlvtf.getText() + "' where palvelu_id = '" + palvelunIDmuokkaukseen + "'");
                    palvelunnimitf.clear();
                    palvelunkuvaustf.clear();
                    palvelunhintatf.clear();
                    palvelunAlvtf.clear();
                    palvelunIDtf.clear();
                    palvelunAlueencb.setValue(null);
                    primaryStage.setScene(muokkaausvalikko);
                }
                muokkaaPalveluitacb.setValue(null);
                muokkaaPalveluitacb.setVisible(false);
                lisaaPalvelubt.setVisible(true);
                palveluMuokkaabt.setVisible(false);
                palveluPoistabt.setVisible(false);
                palvelunmuokkausohje.setVisible(false);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });


        asiakkaanMuokkauscb.setOnAction(e -> {
            Object selectedItem = asiakkaanMuokkauscb.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String data = selectedItem.toString();
                asiakkaanIDmuokkaukseen = data;
                SqlKomennot.Asiakas asiakas = SqlKomennot.fetchAsiakas(Integer.parseInt(data));
                if (asiakas != null) {
                    asiakaanNimitf.setText(asiakas.etunimi);
                    asiakaanSukunimitf.setText(asiakas.sukunimi);
                    asiakaanOsoitetf.setText(asiakas.lahiosoite);
                    asiakaanPostinumerotf.setText(String.valueOf(asiakas.postiNro));
                    asiakkaanPostitoimipaikkatf.setText(SqlKomennot.fetchAsiakaanPosti(asiakaanPostinumerotf.getText()));
                    asiakaanSahkopostitf.setText(asiakas.email);
                    asiakaanPuhelinnrotf.setText(String.valueOf(asiakas.puhelinumero));
                }

            }
        });

        asiakasMuokkaabt.setOnAction(e ->{
            try{
                if (asiakaanNimitf.getText().isEmpty() || asiakaanSukunimitf.getText().isEmpty() || asiakaanOsoitetf.getText().isEmpty() ||
                        asiakaanPostinumerotf.getText().isEmpty() || asiakkaanPostitoimipaikkatf.getText().isEmpty() || asiakaanSahkopostitf.getText().isEmpty() || asiakaanPuhelinnrotf.getText().isEmpty()) {
                    //Tietoja puuttuu
                } else {
                    if (komennot.haePostriNrot().contains(Integer.valueOf(asiakaanPostinumerotf.getText()))) {
                        komennot.updateQuery("update asiakas set postinro = '" + asiakaanPostinumerotf.getText()
                                + "', etunimi = '" + asiakaanNimitf.getText() + "', sukunimi = '" + asiakaanSukunimitf.getText()
                                + "', lahiosoite = '" + asiakaanOsoitetf.getText() + "', email = '" + asiakaanSahkopostitf.getText()
                                + "', puhelinnro = '" + asiakaanPuhelinnrotf.getText() + "' where asiakas_id = '" + asiakkaanIDmuokkaukseen + "'");
                        asiakaanNimitf.clear();
                        asiakaanSukunimitf.clear();
                        asiakaanOsoitetf.clear();
                        asiakaanPostinumerotf.clear();
                        asiakkaanPostitoimipaikkatf.clear();
                        asiakaanSahkopostitf.clear();
                        asiakaanPuhelinnrotf.clear();
                        asiakkaanMuokkauscb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    } else {
                        komennot.updateQuery("insert into posti (postinro, toimipaikka) values ('" + Integer.valueOf(asiakaanPostinumerotf.getText()) + "','" + asiakkaanPostitoimipaikkatf.getText() + "')");
                        komennot.updateQuery("update asiakas set postinro = '" + asiakaanPostinumerotf.getText()
                                + "', etunimi = '" + asiakaanNimitf.getText() + "', sukunimi = '" + asiakaanSukunimitf.getText()
                                + "', lahiosoite = '" + asiakaanOsoitetf.getText() + "', email = '" + asiakaanSahkopostitf.getText()
                                + "', puhelinnro = '" + asiakaanPuhelinnrotf.getText() + "' where asiakas_id = '" + asiakkaanIDmuokkaukseen + "'");
                        asiakaanNimitf.clear();
                        asiakaanSukunimitf.clear();
                        asiakaanOsoitetf.clear();
                        asiakaanPostinumerotf.clear();
                        asiakkaanPostitoimipaikkatf.clear();
                        asiakaanSahkopostitf.clear();
                        asiakaanPuhelinnrotf.clear();
                        asiakkaanMuokkauscb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    }
                }
                asiakkaanMuokkauscb.setVisible(false);
                lisaaAsiakasbt.setVisible(true);
                asiakasMuokkaabt.setVisible(false);
                asiakasPoistabt.setVisible(false);
                asiakkaanmuokkausohje.setVisible(false);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });


        mokkienMuokkauscb.setOnAction(e -> {
            Object selectedItem = mokkienMuokkauscb.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String data = selectedItem.toString();
                mokinNimimuokkaukseen = data;
                int id = SqlKomennot.fetchMokkiId(data);
                SqlKomennot.Mokki mokki = SqlKomennot.fetchMokki(id);
                if (mokki != null) {
                    mokinNimitf.setText(mokki.mokkiNimi);
                    mokinOsoitetf.setText(mokki.katuOsoite);
                    mokinHintatf.setText(mokki.hinta.toString());
                    mokinKuvaustf.setText(mokki.kuvaus);
                    mokinHenkilomaaratf.setText(String.valueOf(mokki.henkilomaara));
                    mokinVaruselutf.setText(mokki.varustelu);
                    mokinPostinrotf.setText(String.valueOf(mokki.postiNro));
                    String alueennimi = SqlKomennot.fetchAlueNimi(mokki.alueId);
                    mokinalueetcb.setValue(alueennimi);
                } else {
                    System.out.println("Täällä");
                }
            }
        });

        mokkiMuokkaabt.setOnAction(e -> {
            try {
                if (mokinNimitf.getText().isEmpty() || mokinOsoitetf.getText().isEmpty() || mokinHintatf.getText().isEmpty() || mokinKuvaustf.getText().isEmpty() ||
                        mokinHenkilomaaratf.getText().isEmpty() || mokinVaruselutf.getText().isEmpty() || mokinPostinrotf.getText().isEmpty() || mokinalueetcb.getValue() == null) {
                    //tietoja puuttuu, vois vaikka virhetekstin pistää

                } else {
                    String id = String.valueOf(komennot.haeAlueenID(String.valueOf(mokinalueetcb.getValue())));
                    id = id.replaceAll("[\\[\\](){}]", "");
                    if (komennot.haePostriNrot().contains(Integer.valueOf(mokinPostinrotf.getText()))) {
                        komennot.updateQuery("update mokki set alue_id = '" + id
                                + "', postinro = '" + mokinPostinrotf.getText() + "', mokkinimi = '" + mokinNimitf.getText()
                                + "', katuosoite = '" + mokinOsoitetf.getText() + "', hinta = '" + mokinHintatf.getText()
                                + "', kuvaus = '" + mokinKuvaustf.getText() + "', henkilomaara = '" + mokinHenkilomaaratf.getText()
                                + "', varustelu = '" + mokinVaruselutf.getText() + "' where mokkinimi = '" + mokinNimimuokkaukseen + "'");
                        mokinNimitf.clear();
                        mokinOsoitetf.clear();
                        mokinHintatf.clear();
                        mokinKuvaustf.clear();
                        mokinHenkilomaaratf.clear();
                        mokinVaruselutf.clear();
                        mokinPostinrotf.clear();
                        mokinalueetcb.setValue(null);
                        mokkienMuokkauscb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    } else {
                        komennot.updateQuery("insert into posti (postinro, toimipaikka) values ('" + Integer.valueOf(mokinPostinrotf.getText()) + "','" + mokinalueetcb.getValue() + "')");

                        komennot.updateQuery("update mokki set alue_id = '" + id
                                + "', postinro = '" + mokinPostinrotf.getText() + "', mokkinimi = '" + mokinNimitf.getText()
                                + "', katuosoite = '" + mokinOsoitetf.getText() + "', hinta = '" + mokinHintatf.getText()
                                + "', kuvaus = '" + mokinKuvaustf.getText() + "', henkilomaara = '" + mokinHenkilomaaratf.getText()
                                + "', varustelu = '" + mokinVaruselutf.getText() + "' where mokkinimi = '" + mokinNimimuokkaukseen + "'");
                        mokinNimitf.clear();
                        mokinOsoitetf.clear();
                        mokinHintatf.clear();
                        mokinKuvaustf.clear();
                        mokinHenkilomaaratf.clear();
                        mokinVaruselutf.clear();
                        mokinPostinrotf.clear();
                        mokinalueetcb.setValue(null);
                        mokkienMuokkauscb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    }
                }
                mokkienMuokkauscb.setVisible(false);
                lisaaMokkibt.setVisible(true);
                mokkiMuokkaabt.setVisible(false);
                mokkiPoistabt.setVisible(false);
                mokkienmuokkausohje.setVisible(false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        lisaaAluebt.setOnAction(e -> {
            try {
                if (!alueennimitf.getText().isEmpty()) {
                    komennot.updateQuery("insert into alue (nimi) values ('" + alueennimitf.getText() + "')");
                    listaAlueista = komennot.valitseKaikkiAlueet();
                    alueMuokkauscb.setItems(FXCollections.observableArrayList(listaAlueista));
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.setScene(muokkaausvalikko);
            alueennimitf.clear();
        });

        lisaaMokkibt.setOnAction(e -> {
            try {
                if (mokinNimitf.getText().isEmpty() || mokinOsoitetf.getText().isEmpty() || mokinHintatf.getText().isEmpty() || mokinKuvaustf.getText().isEmpty() ||
                        mokinHenkilomaaratf.getText().isEmpty() || mokinVaruselutf.getText().isEmpty() || mokinPostinrotf.getText().isEmpty() || mokinalueetcb.getValue() == null) {
                    //tietoja puuttuu, vois vaikka virhetekstin pistää

                } else {
                    String id = String.valueOf(komennot.haeAlueenID(String.valueOf(mokinalueetcb.getValue())));
                    id = id.replaceAll("[\\[\\](){}]", "");
                    if (komennot.haePostriNrot().contains(Integer.valueOf(mokinPostinrotf.getText()))) {
                        komennot.updateQuery("insert into mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) " +
                                "values ('" + id + "','" + mokinPostinrotf.getText() + "','" + mokinNimitf.getText() + "','" + mokinOsoitetf.getText() + "','"
                                + mokinHintatf.getText() + "','" + mokinKuvaustf.getText() + "','" + mokinHenkilomaaratf.getText() + "','" + mokinVaruselutf.getText() + "')");
                        mokinNimitf.clear();
                        mokinOsoitetf.clear();
                        mokinHintatf.clear();
                        mokinKuvaustf.clear();
                        mokinHenkilomaaratf.clear();
                        mokinVaruselutf.clear();
                        mokinPostinrotf.clear();
                        mokinalueetcb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    } else {
                        komennot.updateQuery("insert into posti (postinro, toimipaikka) values ('" + Integer.valueOf(mokinPostinrotf.getText()) + "','" + mokinalueetcb.getValue() + "')");
                        komennot.updateQuery("insert into mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) " +
                                "values ('" + id + "','" + mokinPostinrotf.getText() + "','" + mokinNimitf.getText() + "','" + mokinOsoitetf.getText() + "','"
                                + mokinHintatf.getText() + "','" + mokinKuvaustf.getText() + "','" + mokinHenkilomaaratf.getText() + "','" + mokinVaruselutf.getText() + "')");
                        mokinNimitf.clear();
                        mokinOsoitetf.clear();
                        mokinHintatf.clear();
                        mokinKuvaustf.clear();
                        mokinHenkilomaaratf.clear();
                        mokinVaruselutf.clear();
                        mokinPostinrotf.clear();
                        mokinalueetcb.setValue(null);
                        primaryStage.setScene(muokkaausvalikko);
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        lisaaPalvelubt.setOnAction(e -> {
            try {
                if (palvelunnimitf.getText().isEmpty() || palvelunkuvaustf.getText().isEmpty() || palvelunhintatf.getText().isEmpty() ||
                        palvelunAlvtf.getText().isEmpty() || palvelunIDtf.getText().isEmpty() || palvelunAlueencb.getValue() == null) {
                    //Tietoja puuttuu
                } else {
                    String alueid = String.valueOf(komennot.haeAlueenID(String.valueOf(palvelunAlueencb.getValue())));
                    alueid = alueid.replaceAll("[\\[\\](){}]", "");
                    komennot.updateQuery("insert into palvelu (palvelu_id, alue_id, nimi, kuvaus, hinta, alv) values ('" + palvelunIDtf.getText()
                            + "','" + alueid + "','" + palvelunnimitf.getText() + "','" + palvelunkuvaustf.getText() + "','" + palvelunhintatf.getText() + "','" + palvelunAlvtf.getText() + "')");
                    palvelunnimitf.clear();
                    palvelunkuvaustf.clear();
                    palvelunhintatf.clear();
                    palvelunAlvtf.clear();
                    palvelunIDtf.clear();
                    palvelunAlueencb.setValue(null);
                    primaryStage.setScene(muokkaausvalikko);
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        lisaaAsiakasbt.setOnAction(e -> {
            try {
                if (asiakaanNimitf.getText().isEmpty() || asiakaanSukunimitf.getText().isEmpty() || asiakaanOsoitetf.getText().isEmpty() ||
                        asiakaanPostinumerotf.getText().isEmpty() || asiakkaanPostitoimipaikkatf.getText().isEmpty() || asiakaanSahkopostitf.getText().isEmpty() || asiakaanPuhelinnrotf.getText().isEmpty()) {
                    //Tietoja puuttuu
                } else {
                    if (komennot.haePostriNrot().contains(Integer.valueOf(asiakaanPostinumerotf.getText()))) {
                        komennot.updateQuery("insert into asiakas (postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) values ('" + asiakaanPostinumerotf.getText()
                                + "','" + asiakaanNimitf.getText() + "','" + asiakaanSukunimitf.getText() + "','" + asiakaanOsoitetf.getText() + "','" +
                                asiakaanSahkopostitf.getText() + "','" + asiakaanPuhelinnrotf.getText() + "')");
                        asiakaanNimitf.clear();
                        asiakaanSukunimitf.clear();
                        asiakaanOsoitetf.clear();
                        asiakaanPostinumerotf.clear();
                        asiakkaanPostitoimipaikkatf.clear();
                        asiakaanSahkopostitf.clear();
                        asiakaanPuhelinnrotf.clear();
                        primaryStage.setScene(muokkaausvalikko);
                    } else {
                        komennot.updateQuery("insert into posti (postinro, toimipaikka) values ('" + Integer.valueOf(asiakaanPostinumerotf.getText()) + "','" + asiakkaanPostitoimipaikkatf.getText() + "')");
                        komennot.updateQuery("insert into asiakas (postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) values ('" + asiakaanPostinumerotf.getText()
                                + "','" + asiakaanNimitf.getText() + "','" + asiakaanSukunimitf.getText() + "','" + asiakaanOsoitetf.getText() + "','" +
                                asiakaanSahkopostitf.getText() + "','" + asiakaanPuhelinnrotf.getText() + "')");
                        asiakaanNimitf.clear();
                        asiakaanSukunimitf.clear();
                        asiakaanOsoitetf.clear();
                        asiakaanPostinumerotf.clear();
                        asiakkaanPostitoimipaikkatf.clear();
                        asiakaanSahkopostitf.clear();
                        asiakaanPuhelinnrotf.clear();
                        primaryStage.setScene(muokkaausvalikko);
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void numeronTarkistus(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*.?\\d+")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }

    private void puhulinNroTarkistus(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[0-9]+") && newValue.length() == 11) {
                textField.setText(oldValue);
            }
        });
    }


    private void postiNroTarkistus(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[0-9]+") && newValue.length() == 6) {
                textField.setText(oldValue);
            }
        });
    }
}