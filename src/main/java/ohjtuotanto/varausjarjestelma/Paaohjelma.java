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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.SQLException;


public class Paaohjelma extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {

        SqlKomennot komennot = new SqlKomennot();

        BorderPane asettelu = new BorderPane();

        HBox kaikille = new HBox(30);
        kaikille.setPadding(new Insets(15,10,15,10));
        kaikille.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        //kaikille.setStyle("-fx-background-color: gray");

        HBox alueelle = new HBox(5);
        HBox sliderille = new HBox(5);
        VBox hinnalle = new VBox();

        Text paikkakunta = new Text("Paikkakunta:");

        ObservableList<String>alueidenlista = FXCollections.observableArrayList();
        alueidenlista = komennot.valitseKaikkiAlueet();
        ComboBox alueet = new ComboBox(FXCollections.observableArrayList(alueidenlista));
        alueet.setPromptText("Valitse");

        Text hinta0 = new Text("hinta/yö 0€");
        Text rahanArvo = new Text("0€");
        Text hinta1000 = new Text("1000€");

        Slider hinnansaato = new Slider(0,1000,0);
        hinnansaato.setOrientation(Orientation.HORIZONTAL);
        hinnansaato.setBlockIncrement(100);
        hinnansaato.setShowTickMarks(true);
        hinnansaato.setMajorTickUnit(250);

        final double raja = 100.0;

        hinnansaato.setOnMouseDragged(event -> {
            double newValue = Math.round(hinnansaato.getValue() / raja) * raja; // Pyöristetään sadan välein
            hinnansaato.setValue(newValue);
        });

        hinnansaato.valueProperty().addListener((o, oldValue, newValue) -> {
            int newHinta = newValue.intValue();
            rahanArvo.setText("0-" + newHinta + "€");
        });


        ComboBox<Integer> vieraat = new ComboBox<>();
        vieraat.setPromptText("vieraiden lkm");
        vieraat.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        ));

        Button hae = new Button("Hae");
        hae.setMinWidth(50);

        alueelle.getChildren().addAll(paikkakunta, alueet);

        hinnalle.getChildren().addAll(hinnansaato, rahanArvo);
        hinnalle.setAlignment(Pos.CENTER);

        sliderille.getChildren().addAll(hinta0, hinnalle, hinta1000);

        kaikille.getChildren().addAll(alueelle, sliderille, vieraat, hae);
        asettelu.setTop(kaikille);

        Scene paavalikko = new Scene(asettelu, 700, 400);





        //-------------------------------------------------------------

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
        primaryStage.setTitle("Mökkivarausjärjestelmä");
        primaryStage.setScene(paavalikko);
        primaryStage.show();

    }
}