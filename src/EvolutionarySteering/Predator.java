package EvolutionarySteering;

import Helpers.Randomiser;
import Helpers.Vector2D;

import java.awt.*;

import static java.lang.Math.min;

public class Predator {
    private Vector2D velocity;
    public Vector2D location;
    public double health;
    private double max_speed;
    private double max_force;

    public Shape body;
    public double rotation;

    private Environment environment;
    private Population population;
    private SimulationSettings ss;
    private Randomiser r;

    public double[] dna;
    public double fitness;
    public boolean dead;
    public int children;

    public Predator(Vector2D initial_location, double max_force, double max_speed, Environment environment, Population population) {
        this.velocity = new Vector2D(0, 0);
        this.location = initial_location;
        this.environment = environment;
        this.population = population;
        this.ss = population.ss;
        children = 0;

        r = new Randomiser();
        dna = new double[7];
        dna[0] = r.getDouble(ss.predator_min_attraction, ss.predator_max_attraction);
        dna[1] = r.getDouble(ss.predator_min_attraction, ss.predator_max_attraction);
        dna[2] = r.getDouble(ss.predator_min_attraction, ss.predator_max_attraction);
        dna[3] = r.getDouble(ss.predator_min_perception, ss.predator_max_perception);
        dna[4] = r.getDouble(ss.predator_min_perception, ss.predator_max_perception);
        dna[5] = r.getDouble(ss.predator_min_perception, ss.predator_max_perception);
        dna[6] = r.getDouble(0, ss.predator_max_health);
        health = min(1.0, dna[6]);
        this.max_speed = max_speed * (ss.predator_max_health - dna[6]) / (ss.predator_max_health / 2);
        this.max_force = max_force * (ss.predator_max_health - dna[6]) / (ss.predator_max_health / 2);
        fitness = 0;
        dead = false;
        updateShape();
    }

    public void advance() {
        double min_dist = 1000000;
        Animal closest_animal = null;
        for (Animal animal : population.animals) {
            if (animal.location.distanceTo(location) < min_dist && animal.location.distanceTo(location) < dna[3]) {
                closest_animal = animal;
                min_dist = animal.location.distanceTo(location);
            }
        }

        min_dist = 1000000;
        Vector2D closest_poison = null;
        for (Vector2D poison : environment.poison) {
            if (poison.distanceTo(location) < min_dist && poison.distanceTo(location) < dna[4]) {
                closest_poison = poison;
                min_dist = poison.distanceTo(location);
            }
        }

        min_dist = 1000000;
        Vector2D closest_predator = null;
        for (Predator predator : population.predators) {
            if (predator.location.distanceTo(location) < min_dist && predator.location.distanceTo(location) < dna[5]) {
                closest_predator = predator.location;
                min_dist = predator.location.distanceTo(location);
            }
        }

        Vector2D total_force = new Vector2D(0, 0);
        if (closest_animal != null) {
            Vector2D animal_force = closest_animal.location.sub(location).setMag(max_speed).sub(velocity).mul(dna[0]);
            total_force = total_force.add(animal_force);
        }

        if (closest_poison != null) {
            Vector2D poison_force = closest_poison.sub(location).setMag(max_speed).sub(velocity).mul(dna[1]);
            total_force = total_force.add(poison_force);
        }

        if (closest_predator != null) {
            Vector2D predator_force = closest_predator.sub(location).setMag(max_speed).sub(velocity).mul(dna[2]);
            total_force = total_force.add(predator_force);
        }

        if (location.x < 30 || location.x > 1220 || location.y < 30 || location.y > 920) {
            Vector2D center_force = (new Vector2D(625, 450)).sub(location).setMag(max_speed).sub(velocity).mul(10);
            total_force = total_force.add(center_force);
        }

        total_force = total_force.limit(max_force);

        velocity = velocity.add(total_force);
        velocity = velocity.limit(max_speed);
        location = location.add(velocity);

        if (closest_animal != null && closest_animal.location.distanceTo(location) < 10) {
            closest_animal.dead = true;
            health += ss.predator_food_gain;
            if (health > dna[6]) {
                health = dna[6];
            }
        }

        if (closest_poison != null && closest_poison.distanceTo(location) < 10) {
            environment.consumePoison(closest_poison);
            health += ss.predator_poison_gain;
        }

        updateLife();
        updateShape();
    }

    private void updateLife () {
        fitness += 0.0001;
        health -= ss.predator_hplr;
        if (health <= 0) {
            dead = true;
        }
    }

    private void updateShape() {
        body = new Polygon(new int[] {(int)location.x, (int)location.x - 5, (int)location.x + 5},
                new int[] {(int)location.y - 10, (int)location.y + 10, (int)location.y + 10},  3);
        if (velocity.length() == 0) {
            rotation = 0;
        } else if (velocity.y < 0) {
            rotation = Math.atan(velocity.x / velocity.y*-1);
        } else {
            rotation = Math.PI + Math.atan(velocity.x / velocity.y*-1);
        }
    }

    Predator cloneMe() {
        children++;
        Predator child = new Predator(location, ss.predator_max_force, ss.predator_max_speed, environment, population);
        child.dna = dna.clone();
        child.mutate();
        child.health = min(health, child.dna[6]);
        child.velocity = velocity.mul(-1);
        return child;
    }

    public void mutate() {
        for (int i = 0; i < 3; i++) {
            if (r.getDouble(0, 1) < ss.predator_mutation_rate) {
                dna[i] = r.getDouble(ss.predator_min_attraction, ss.predator_max_attraction);
            }
        }
        for (int i = 3; i < 6; i++) {
            if (r.getDouble(0, 1) < ss.predator_mutation_rate) {
                dna[i] = r.getDouble(ss.predator_min_perception, ss.predator_max_perception);
            }
        }
        for (int i = 6; i < 7; i++) {
            if (r.getDouble(0, 1) < ss.predator_mutation_rate) {
                dna[i] = r.getDouble(0, ss.predator_max_health);
            }
        }
        max_speed = max_speed * (ss.predator_max_health - dna[6]) / (ss.predator_max_health / 2);
        max_force = max_force * (ss.predator_max_health - dna[6]) / (ss.predator_max_health / 2);
    }
}
