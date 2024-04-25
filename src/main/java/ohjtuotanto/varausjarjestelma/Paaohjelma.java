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
        primaryStage.setTitle("Mökkivarausjärjestelmä");
        primaryStage.setScene(paavalikko);
        primaryStage.show();

    }
}