package se233.casestudy4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import se233.casestudy4.Launcher;
import se233.casestudy4.model.GameCharacter;
import se233.casestudy4.model.Keys;

import java.util.List;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private Image gameStageImg;
    private GameCharacter gameCharacter;
    private List<GameCharacter> gameCharacters;
    private Keys keys;

    public GameStage() {
        keys = new Keys();
        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        gameCharacter = new GameCharacter(30, 30,0,0, KeyCode.A,KeyCode.D,KeyCode.W);
        GameCharacter secondGameCharacter = new GameCharacter(300, 30,
            KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, 5, 15,
            "assets/rockman.png", 10, 5, 2, 0, 0, 541, 514);
        gameCharacters = List.of(gameCharacter, secondGameCharacter);
        getChildren().addAll(backgroundImg, gameCharacter, secondGameCharacter);
    }
    public GameCharacter getGameCharacter() {
        return gameCharacter;
    }
    public List<GameCharacter> getGameCharacters() {
        return gameCharacters;
    }
    public Keys getKeys() {
        return keys;
    }
}