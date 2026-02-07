module com.traffic.symulacjaruchu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.traffic.symulacjaruchu to javafx.fxml;
    exports com.traffic.symulacjaruchu;
    exports com.traffic.symulacjaruchu.ui;
}