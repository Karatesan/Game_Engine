package pl.karatesan.gfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.Duration;

public class Graphics extends Application {

    private int windowWidth;
    private int windowHeight;
    private int targetFps = 60;
    private long targetFrameTimeNano = Duration.ofSeconds(1).toNanos() / targetFps;
    private DoubleProperty fpsProperty;
    private Pane root;
    private String windowName;
    private ObjectManager objectManager;
    private long previousTimeStamp = -1;

    public Graphics() {
        this.fpsProperty = new SimpleDoubleProperty(0.0);
        root = new Pane();
        windowWidth = 800;
        windowHeight = 600;
    }

    @Override
    public void init() throws Exception {
        super.init();
        objectManager = new ObjectManager();
        BallsInitializer initializer = new BallsInitializer();
        initializer.initialize(objectManager, this);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Label fpsMeter = new Label("");
        fpsMeter.textProperty().bind(fpsProperty.asString("%.2f"));
        fpsMeter.setTextFill(Color.WHITE);

        VBox fpsMeterBox = new VBox(fpsMeter);
        fpsMeterBox.setAlignment(Pos.BASELINE_LEFT);

        root.getChildren().add(fpsMeterBox);
        root.getChildren().add(objectManager.getMap().getNode());

        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        objectManager.getObjects().forEach(o -> root.getChildren().add(o.getNode()));
        Scene scene = new Scene(root, windowWidth, windowHeight);
        scene.setFill(Color.BLACK);
        stage.setTitle(windowName);
        stage.setScene(scene);
        stage.show();

        startGameLoop();
    }

    public static void startEngine() {
        launch();
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (previousTimeStamp < 0) {
                    previousTimeStamp = now;
                    return;
                }
                long deltaTime = now - previousTimeStamp;
                previousTimeStamp = now;

                System.out.println(deltaTime / 1000000.0);
                // Game logic (runs on game thread)
                objectManager.updateObjects(deltaTime);
                double fps = 1 / (deltaTime / 1_000_000_000.0);
                fpsProperty.set(fps);
            }
        };
        gameLoop.start();
    }

    void fixFrameTime(long startFrameTime) {
        long frameTimeNano = System.nanoTime() - startFrameTime;

        if (frameTimeNano < targetFrameTimeNano) {
            long frameTimeMills = frameTimeNano / Duration.ofMillis(1).toNanos();
            if (frameTimeMills > 0) {
                long sleepTimeMs = (targetFrameTimeNano / Duration.ofMillis(1).toNanos()) - frameTimeMills;
                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (System.nanoTime() - startFrameTime < targetFrameTimeNano) {
                //Thread.yield();
            }
        }
    }

    public void setWindowWidth(int w) {
        windowWidth = w;
    }

    public void setWindowHeight(int h) {
        windowHeight = h;
    }

    public void setWindowName(String name) {
        windowName = name;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getTargetFps() {
        return targetFps;
    }

    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
    }

    public long getTargetFrameTimeNano() {
        return targetFrameTimeNano;
    }

    public void setTargetFrameTimeNano(long targetFrameTimeNano) {
        this.targetFrameTimeNano = targetFrameTimeNano;
    }

    public String getWindowName() {
        return windowName;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

//    public void setObjectManager(ObjectManager objectManager) {
//        this.objectManager = objectManager;
//        staticObjectManager = objectManager;
//    }
}
