module se233.casestudy4{
    requires javafx.fxml;
    requires  javafx.controls;
    requires org.apache.logging.log4j;

    opens se233.casestudy4  to javafx.fxml;
    exports se233.casestudy4;
}