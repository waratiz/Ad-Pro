package se233.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.controller.draw.DrawGraphTask;
import se233.chapter2.model.Currency;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CurrencyPane extends BorderPane {
    private Currency currency;
    private Button watch;
    private Button delete;
    private Button Unwatch;

    public CurrencyPane(Currency currency) {
        this.watch = new Button("Watch");
        this.Unwatch = new Button("Unwatch");
        this.delete = new Button("Delete");
        this.watch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode());
            }
        });
        this.Unwatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { AllEventHandlers.onUnwatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode());
            }
        });
        this.setPadding(new Insets(0));
        this.setPrefSize(640, 300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (ExecutionException e) {
            System.out.println("Encountered an execution exception.");
        } catch (InterruptedException e) {
            System.out.println("Encountered an interupted exception.");
        }
    }

    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<Pane> infoTask = new GenInfoPaneTask(currency);
        Callable<Pane> topAreaTask = new GenTopAreaTask();
        Callable<VBox> graphTask = new DrawGraphTask(currency);

        Future<Pane> infoFuture = executor.submit(infoTask);
        Future<Pane> topAreaFuture = executor.submit(topAreaTask);
        Future<VBox> graphFuture = executor.submit(graphTask);

        Pane currencyInfo = infoFuture.get();
        Pane topArea = topAreaFuture.get();
        VBox currencyGraph = graphFuture.get();

        this.setTop(topArea);
        this.setLeft(currencyInfo);
        this.setCenter(currencyGraph);

        executor.shutdown();
    }

    private class GenInfoPaneTask implements Callable<Pane> {
        private Currency currency;

        public GenInfoPaneTask(Currency currency) {
            this.currency = currency;
        }

        @Override
        public Pane call() {
            VBox currencyInfoPane = new VBox(10);
            currencyInfoPane.setPadding(new Insets(5, 25, 5, 25));
            currencyInfoPane.setAlignment(Pos.CENTER);

            Label exchangeString = new Label("");
            Label watchString = new Label("");
            exchangeString.setStyle("-fx-font-size: 20;");
            watchString.setStyle("-fx-font-size: 14;");

            if (this.currency != null) {
                if (this.currency.getCurrency() != null) {
                    exchangeString.setText(String.format("%s: %.4f", this.currency.getShortCode(), this.currency.getCurrency().getRate()));
                } else {
                    exchangeString.setText(String.format("%s: N/A", this.currency.getShortCode()));
                }

                if (this.currency.getWatch()) {
                    watchString.setText(String.format("(Watch @%.4f)", this.currency.getWatchRate()));
                }
            }

            currencyInfoPane.getChildren().addAll(exchangeString, watchString);
            return currencyInfoPane;
        }
    }

    private class GenTopAreaTask implements Callable<Pane> {
        @Override
        public Pane call() {
            HBox topArea = new HBox(10);
            topArea.setPadding(new Insets(5));
            topArea.getChildren().addAll(watch, Unwatch, delete);
            topArea.setAlignment(Pos.CENTER_RIGHT);
            return topArea;
        }
    }
}