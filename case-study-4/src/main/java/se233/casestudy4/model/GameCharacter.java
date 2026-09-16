package se233.casestudy4.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.casestudy4.Launcher;
import se233.casestudy4.view.GameStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image gameCharacterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    int yVelocity = 0;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    int xAcceleration = 1;
    int yAcceleration = 1;
    private final int xMaxVelocity;
    private final int yMaxVelocity;


    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey,
                         KeyCode rightKey, KeyCode upKey) {
        this(x, y, offsetX, offsetY, leftKey, rightKey, upKey, 7, 17);
    }

    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey,
                         KeyCode rightKey, KeyCode upKey, int xMaxVelocity, int yMaxVelocity) {
        this(x, y, leftKey, rightKey, upKey, xMaxVelocity, yMaxVelocity,
            "assets/MarioSheet.png", 4, 4, 1, offsetX, offsetY, 16, 32);
        }

        public GameCharacter(int x, int y, KeyCode leftKey, KeyCode rightKey, KeyCode upKey,
                 int xMaxVelocity, int yMaxVelocity, String spriteAsset,
                 int frameCount, int columns, int rows, int offsetX, int offsetY,
                 int frameWidth, int frameHeight) {
        this.x = x;
        this.y = y;
        this.xMaxVelocity = xMaxVelocity;
        this.yMaxVelocity = yMaxVelocity;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.gameCharacterImg = new Image(Launcher.class.getResourceAsStream(spriteAsset));
        this.imageView = new AnimatedSprite(gameCharacterImg, frameCount, columns, rows,
            offsetX, offsetY, frameWidth, frameHeight);
        this.imageView.setFitWidth((int) Math.round((double) CHARACTER_HEIGHT * frameWidth / frameHeight));
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }

    public void moveX() {
        setTranslateX(x);
        if (isMoveLeft) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x - xVelocity;
        }
        if (isMoveRight) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x + xVelocity;
        }
    }

    public void moveY() {
        setTranslateY(y);
        if(isFalling) {
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity+yAcceleration;
            y = y + yVelocity;
        } else if(isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity-yAcceleration;
            y = y - yVelocity;
        }
    }

    public void checkReachGameWall() {
        if(x <= 0) {
            logger.debug("Character collided with the left game boundary at x={}", x);
            x = 0;
        } else if( x+getWidth() >= GameStage.WIDTH) {
            logger.debug("Character collided with the right game boundary at x={}", x);
            x = GameStage.WIDTH-(int)getWidth();
        }
    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }
    public void checkReachHighest () {
        if(isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor() {
        if(isFalling && y >= GameStage.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}",x,y,xVelocity,yVelocity);
    }

    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight() {
        setScaleX(1);
        isMoveLeft = false;
        isMoveRight = true;
    }

    public void stop() {
        isMoveLeft = false;
        isMoveRight = false;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }
}