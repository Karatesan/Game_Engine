package pl.karatesan;

import javafx.scene.paint.Color;
import pl.karatesan.gfx.BallsInitializer;
import pl.karatesan.gfx.GameInitializer;
import pl.karatesan.gfx.Graphics;
import pl.karatesan.gfx.ObjectManager;
import pl.karatesan.shapes.BallFx;

public class Main {

    public static void main(String[] args) {
        Graphics.setInitializer(new BallsInitializer());
        Graphics.startEngine();
    }
}