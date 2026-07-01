module com.example.advanceprogramming {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advanceprogramming to javafx.fxml;
    exports com.example.advanceprogramming;
}