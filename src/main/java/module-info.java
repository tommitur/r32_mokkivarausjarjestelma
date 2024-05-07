module Varausjarjestelma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ohjtuotanto.varausjarjestelma to javafx.fxml;
    exports ohjtuotanto.varausjarjestelma;
}