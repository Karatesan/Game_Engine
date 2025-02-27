package pl.karatesan.gfx;


import javafx.scene.Node;
import pl.karatesan.shapes.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private List<GameObject<?>> objects = new ArrayList<>();
    private GameObject<?> map;


    public void updateObjects(double deltaTime) {
        objects.forEach(o -> o.update(deltaTime, map));
    }

    public List<GameObject<?>> getObjects() {
        return objects;
    }

    public void setObjects(List<GameObject<?>> objects) {
        this.objects = objects;
    }

    public GameObject getMap() {
        return map;
    }

    public void setMap(GameObject map) {
        this.map = map;
    }

    public void addObject(GameObject<?> object){
        objects.add(object);
    }
}
