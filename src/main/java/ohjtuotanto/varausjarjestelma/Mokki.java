package ohjtuotanto.varausjarjestelma;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mokki {
    private final SimpleStringProperty nimi;
    private final SimpleIntegerProperty hinta;
    private final SimpleIntegerProperty vieraidenLkm;
    private final SimpleIntegerProperty alue_id;
    private final SimpleStringProperty alue;

    public Mokki(String nimi, int hinta, int vieraidenLkm, int alue_id, String alue) {
        this.nimi = new SimpleStringProperty(nimi);
        this.hinta = new SimpleIntegerProperty(hinta);
        this.vieraidenLkm = new SimpleIntegerProperty(vieraidenLkm);
        this.alue_id = new SimpleIntegerProperty(alue_id);
        this.alue = new SimpleStringProperty(alue);
    }

    public String getNimi() {
        return nimi.get();
    }

    public SimpleStringProperty nimiProperty() {
        return nimi;
    }

    public int getHinta() {
        return hinta.get();
    }

    public SimpleIntegerProperty hintaProperty() {
        return hinta;
    }

    public int getVieraidenLkm() {
        return vieraidenLkm.get();
    }

    public SimpleIntegerProperty vieraidenLkmProperty() {
        return vieraidenLkm;
    }

    public int getAlue_id() {
        return alue_id.get();
    }

    public SimpleIntegerProperty alueIdProperty() {
        return alue_id;
    }

    public String getAlue() {
        return alue.get();
    }

    public SimpleStringProperty alueProperty() {
        return alue;
    }


}