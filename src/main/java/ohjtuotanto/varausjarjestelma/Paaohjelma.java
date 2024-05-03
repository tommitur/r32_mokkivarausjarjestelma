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

        Pane asettelu = new Pane();

        HBox suodattimet = new HBox(25);
        suodattimet.setPadding(new Insets(15,10,10,15));

        VBox hinta = new VBox();

        HBox slider = new HBox(5);

        ObservableList<String>alueidenlista = FXCollections.observableArrayList();
        alueidenlista = komennot.valitseKaikkiAlueet();
        ComboBox alueet = new ComboBox(FXCollections.observableArrayList(alueidenlista));
        alueet.setPromptText("Paikkakunta");

        Text hinta0 = new Text("0€");
        Text rahanArvo = new Text("0€");
        Text hinta1000 = new Text("1000€");

        Slider hinnansaato = new Slider(0,1000,0);
        hinnansaato.setOrientation(Orientation.HORIZONTAL);
        hinnansaato.setBlockIncrement(50);
        //hinnansaato.setShowTickLabels(true);
        hinnansaato.setShowTickMarks(true);
        hinnansaato.setMajorTickUnit(250);

        hinnansaato.valueProperty().addListener((o, oldValue, newValue) -> {
            int newHinta = newValue.intValue();
            rahanArvo.setText("0-" + newHinta + "€");
        });

        Button hae = new Button("Hae");
        hae.setMinWidth(50);

        hinta.getChildren().addAll(hinnansaato, rahanArvo);
        hinta.setAlignment(Pos.TOP_CENTER);

        slider.getChildren().addAll(hinta0, hinta, hinta1000);

        suodattimet.getChildren().addAll(alueet, slider, hae);
        asettelu.getChildren().add(suodattimet);

        Scene paavalikko = new Scene(asettelu, 600, 400);

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