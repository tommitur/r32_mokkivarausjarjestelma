package ohjtuotanto.varausjarjestelma;

public class Mokit {

    int alue_id, henkilomaara;
    String mokkiNimi;
    Double hinta;
    int mokkiId;
    int postiNro;
    String katuOsoite;
    String kuvaus;
    String varustelu;

    public Mokit(int alue_id, int henkilomaara, String mokkiNimi, Double hinta, int mokkiId,
                 int postiNro, String katuOsoite, String kuvaus, String varustelu) {
        this.alue_id = alue_id;
        this.henkilomaara = henkilomaara;
        this.mokkiNimi = mokkiNimi;
        this.hinta = hinta;
        this.mokkiId = mokkiId;
        this.postiNro = postiNro;
        this.katuOsoite = katuOsoite;
        this.kuvaus = kuvaus;
        this.varustelu = varustelu;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    public void setMokkiNimi(String mokkiNimi) {
        this.mokkiNimi = mokkiNimi;
    }

    public void setHinta(Double hinta) {
        this.hinta = hinta;
    }

    public void setMokkiId(int mokkiId) {
        this.mokkiId = mokkiId;
    }

    public void setPostiNro(int postiNro) {
        this.postiNro = postiNro;
    }

    public void setKatuOsoite(String katuOsoite) {
        this.katuOsoite = katuOsoite;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    public int getAlue_id() {
        return alue_id;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public String getMokkiNimi() {
        return mokkiNimi;
    }

    public Double getHinta() {
        return hinta;
    }

    public int getMokkiId() {
        return mokkiId;
    }

    public int getPostiNro() {
        return postiNro;
    }

    public String getKatuOsoite() {
        return katuOsoite;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public String getVarustelu() {
        return varustelu;
    }
}