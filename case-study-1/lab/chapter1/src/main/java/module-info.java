module se233.chapter1 {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens se233.chapter1 to javafx.fxml;
    exports se233.chapter1;
    exports se233.chapter1.controller;
    exports se233.chapter1.model.character;
    exports se233.chapter1.model.item;
    exports se233.chapter1.view;
}