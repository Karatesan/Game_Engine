package pl.karatesan.gfx;

import javafx.application.Application;
import javafx.application.Platform;
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
    private double deltaTime;
    private boolean showFps = false;
    private Pane root;
    private String windowName;
    private ObjectManager objectManager;
    private static ObjectManager staticObjectManager; // Temporary static storage

    public Graphics() {
        this.fpsProperty = new SimpleDoubleProperty(0.0);;
        root = new Pane();
        windowWidth = 800;
        windowHeight = 600;
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (objectManager == null) {
            objectManager = staticObjectManager;
        }

        Label fpsMeter = new Label("");
        fpsMeter.textProperty().bind(fpsProperty.asString("%.2f"));
        fpsMeter.setTextFill(Color.WHITE);

        VBox fpsMeterBox = new VBox(fpsMeter);
        fpsMeterBox.setAlignment(Pos.BASELINE_LEFT);
        if (showFps)
            root.getChildren().add(fpsMeterBox);
        root.getChildren().add(objectManager.getMap().getNode());
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        objectManager.getObjects().forEach(o -> root.getChildren().add(o.getNode()));
        Scene scene = new Scene(root, windowWidth, windowHeight);
        scene.setFill(Color.BLACK);
        stage.setTitle(windowName);
        stage.setScene(scene);
        stage.show();

        Thread gameThread = new Thread(() -> {
            while (true) {
                long startFrameTime = System.nanoTime();
                if(objectManager.getObjects().get(0) == root.getChildren().get(0)){
                    System.out.println("dasdasda");
                }
                // Game logic (runs on game thread)
                objectManager.updateObjects(deltaTime);


                fixFrameTime(startFrameTime);

                deltaTime = System.nanoTime() - startFrameTime;
                double fps = 1000.0 / (deltaTime / 1_000_000.0);
                Platform.runLater(() -> fpsProperty.setValue(fps));
            }
        });
        gameThread.setDaemon(true); // Stops thread when app closes
        gameThread.start();
    }

    public void startEngine(){
        launch();
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

    public void showFps(boolean show){
        this.showFps = show;
    }

    public void setWindowWidth(int w){
        windowWidth = w;
    }

    public void setWindowHeight(int h){
        windowHeight = h;
    }

    public void setWindowName(String name){
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

    public void setShowFps(boolean showFps) {
        this.showFps = showFps;
    }

    public String getWindowName() {
        return windowName;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
        staticObjectManager = objectManager;
    }
}
