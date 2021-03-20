package EvolutionarySteering;

import Helpers.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;

public class Environment {
    private PropertyChangeSupport notifier;
    public ArrayList<Vector2D> food;
    public ArrayList<Vector2D> poison;
    private SimulationSettings ss;

    public Environment(SimulationSettings ss) {
        notifier = new PropertyChangeSupport(this);
        this.ss = ss;
        Random r = new Random();
        food = new ArrayList<>();
        poison = new ArrayList<>();
        for (int i = 0; i < ss.initial_food; i++) {
            food.add(new Vector2D(r.nextInt(1150) + 50, r.nextInt(850) + 50));
        }
        if (ss.spawn_poison) {
            for (int i = 0; i < ss.initial_poison; i++) {
                poison.add(new Vector2D(r.nextInt(1150) + 50, r.nextInt(850) + 50));
            }
        }
    }

    void reset() {
        food.clear();
        poison.clear();
        Random r = new Random();
        for (int i = 0; i < ss.initial_food; i++) {
            food.add(new Vector2D(r.nextInt(1150) + 50, r.nextInt(850) + 50));
        }
        if (ss.spawn_poison) {
            for (int i = 0; i < ss.initial_poison; i++) {
                poison.add(new Vector2D(r.nextInt(1150) + 50, r.nextInt(850) + 50));
            }
        }
    }

    public void addFood(Vector2D location) {
        food.add(location);
    }

    public void addPoison (Vector2D location) {
        poison.add(location);
    }

    public void consumeFood(Vector2D location) {
        food.remove(location);
    }

    public void consumePoison(Vector2D location) {
        poison.remove(location);
    }

    public void addObserver(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    public void update() {
        notifier.firePropertyChange("aa", "", "A");
    }
}
