package com.example.advanceprogramming;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DragAndDrop extends Application {
    double mouse_x, mouse_y;
    boolean isEntered;
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello Drag and Drop");

        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);
        scene.setFill(Color.WHITE);

        FigureGroup source = new FigureGroup(80, 50, 40, Color.LIGHTBLUE, "SOURCE");
        FigureGroup target = new FigureGroup(320, 50, 50, Color.RED, "DROP TARGET");
        isEntered = false;

        source.getFigure().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse_x = event.getScreenX();
                mouse_y = event.getScreenY();
            }
        });

        source.getFigure().setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                source.getFigure().setMouseTransparent(true);
                source.getFigure().toFront();
                source.getFigure().startFullDrag();
            }
        });

        source.getFigure().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getScreenX() - mouse_x;
                double deltaY = event.getScreenY() - mouse_y;
                source.setPosition(deltaX, deltaY);
                mouse_x = event.getScreenX();
                mouse_y = event.getScreenY();
            }
        });

        source.getFigure().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isEntered) {
                    source.setBackOrigin();
                    source.getFigure().setMouseTransparent(false);
                } else {
                    target.getFigure().getChildren().add(source.getFigure());
                }
            }
        });

        target.getFigure().setOnMouseDragEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isEntered = true;
            }
        });

        target.getFigure().setOnMouseDragExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isEntered = false;
            }
        });

        root.getChildren().addAll(source.getFigure(), target.getFigure());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}