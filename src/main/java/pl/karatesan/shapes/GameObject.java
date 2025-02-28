package pl.karatesan.shapes;

import javafx.scene.Node;

public interface GameObject<T extends Node>  {

    void update(double deltaTime, GameObject<BallFx> map);
    T getNode();
}
