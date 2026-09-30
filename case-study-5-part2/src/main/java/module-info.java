module se233.casestudy5part2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens se233.casestudy5part2 to javafx.fxml;
    opens se233.casestudy5part2.model;

    exports se233.casestudy5part2;
}