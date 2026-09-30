module se233.chapter6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.logging;

    opens se233.casestudy6 to javafx.fxml;
    opens se233.casestudy6.model;

    exports se233.casestudy6;
}