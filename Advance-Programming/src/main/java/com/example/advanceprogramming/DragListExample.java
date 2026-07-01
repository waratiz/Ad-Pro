package com.example.advanceprogramming;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragListExample extends Application {

    @Override
    public void start(Stage stage) {
        Label sourceListLbl = new Label("Source List: ");
        Label targetListLbl = new Label("Target List: ");

        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Java", "Python", "Ruby", "R", "Go", "C");

        DragListModel sourceView = new DragListModel();
        DragListModel targetView = new DragListModel();
        sourceView.lV.getItems().addAll(list);

        GridPane pane = new GridPane();
        pane.addRow(1, sourceListLbl, targetListLbl);
        pane.addRow(2, sourceView.lV, targetView.lV);

        VBox root = new VBox();
        root.getChildren().add(pane);

        stage.setScene(new Scene(root));
        stage.setTitle("Drag and Drop Items in List");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}