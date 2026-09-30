module se233.chapter5part1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens se233.chapter5part1 to javafx.fxml;
    opens se233.chapter5part1.model;

    exports se233.chapter5part1;
}