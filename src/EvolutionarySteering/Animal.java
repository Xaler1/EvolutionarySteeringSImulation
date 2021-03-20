package EvolutionarySteering;

import Helpers.Randomiser;
import Helpers.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.Math.*;

public class Animal {
    private Vector2D velocity;
    public Vector2D location;
    public double health;
    private double max_speed;
    private double max_force;

    public Shape body;
    public double rotation;
    public Shape food_line;
    public Shape poison_line;
    public Shape resultant_line;

    private Environment environment;
    private Population population;
    private Randomiser r;

    public double[] dna;
    public double fitness;
    public boolean dead;
    public int children;

    private SimulationSettings ss;

    public Animal (Vector2D initial_location, double max_force, double max_speed, Environment environment, Population population) {
        this.velocity = new Vector2D(0, 0);
        this.location = initial_location;
        this.environment = environment;
        this.population = population;
        this.ss = population.ss;
        children = 0;

        food_line = new Line2D.Double(0, 0, 0, 0);
        poison_line = new Line2D.Double(0, 0, 0, 0);
        resultant_line = new Line2D.Double(0, 0, 0, 0);

        r = new Randomiser();
        dna = new double[9];
        dna[0] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
        dna[1] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
        dna[2] = r.getDouble(0, ss.animal_max_health);
        dna[3] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
        dna[4] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
        dna[5] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
        dna[6] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
        dna[7] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
        dna[8] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
        health = min(1.0, dna[2]);
        this.max_speed = max_speed * (ss.animal_max_health - dna[2]) / (ss.animal_max_health / 2);
        this.max_force = max_force * (ss.animal_max_health - dna[2]) / (ss.animal_max_health / 2);
        fitness = 0;
        dead = false;
        updateShape();
    }

    public void advance() {
         double min_dist = 10000000;
         Vector2D closest_food = null;
         for (Vector2D food : environment.food) {
             if (location.distanceTo(food) < min_dist && location.distanceTo(food) < dna[3]) {
                 closest_food = food;
                 min_dist = location.distanceTo(food);
             }
         }

         min_dist = 1000000;
         Vector2D closest_poison = null;
         for (Vector2D poison : environment.poison) {
             if (location.distanceTo(poison) < min_dist && location.distanceTo(poison) < dna[4]) {
                 closest_poison = poison;
                 min_dist = location.distanceTo(poison);
             }
         }

         min_dist = 1000000;
         Vector2D closest_animal = null;
         for (Animal animal : population.animals) {
             if (location.distanceTo(animal.location) < min_dist && location.distanceTo(animal.location) < dna[6]) {
                 closest_animal = animal.location;
                 min_dist = location.distanceTo(animal.location);
             }
         }

         min_dist = 1000000;
         Vector2D closest_predator = null;
         for (Predator predator : population.predators) {
             if (location.distanceTo(predator.location) < min_dist && location.distanceTo(predator.location) < dna[8]) {
                 closest_predator = predator.location;
                 min_dist = location.distanceTo(predator.location);
             }
         }

         Vector2D total_force = new Vector2D(0, 0);
         if (closest_food != null) {
             Vector2D food_force = closest_food.sub(location).setMag(max_speed).sub(velocity).mul(dna[0]);
             total_force = total_force.add(food_force);
             food_force.mul(10);
             food_line = new Line2D.Double(location.x, location.y, location.x + food_force.x, location.y + food_force.y);
         } else {
             food_line = new Line2D.Double(0, 0, 0, 0);
         }

        if (closest_poison != null) {
            Vector2D poison_force = closest_poison.sub(location).setMag(max_speed).sub(velocity).mul(dna[1]);
            total_force = total_force.add(poison_force);
            poison_force.mul(10);
            poison_line = new Line2D.Double(location.x, location.y, location.x + poison_force.x, location.y + poison_force.y);
        } else {
            poison_line = new Line2D.Double(0, 0, 0, 0);
        }

        if (closest_animal != null) {
            Vector2D animal_force = closest_animal.sub(location).setMag(max_speed).sub(velocity).mul(dna[5]);
            total_force = total_force.add(animal_force);
        }

        if (closest_predator != null) {
            Vector2D predator_force = closest_predator.sub(location).setMag(max_speed).sub(velocity).mul(dna[7]);
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

         total_force = total_force.mul(80);
         resultant_line = new Line2D.Double(location.x, location.y, location.x + total_force.x, location.y + total_force.y);

         if (closest_food != null) {
             if (location.distanceTo(closest_food) < 10) {
                 environment.consumeFood(closest_food);
                 health += ss.animal_food_gain;
                 if (health > dna[2]) {
                     health = dna[2];
                 }
             }
         }

         if (closest_poison != null) {
             if (location.distanceTo(closest_poison) < 10) {
                 environment.consumePoison( closest_poison);
                 health += ss.animal_poison_gain;
             }
         }

         updateLife();
         updateShape();
    }

    void updateLife () {
        fitness += 0.0001;
        health -= ss.animal_hplr;
        if (health <= 0) {
            dead = true;
        }
    }

    void updateShape() {
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

    Animal cloneMe() {
        children++;
        Animal child = new Animal(location, population.ss.animal_max_force, population.ss.animal_max_speed, environment, population);
        child.dna = dna.clone();
        child.mutate();
        child.health = min(health, child.dna[2]);
        child.velocity = velocity.mul(-1);
        return child;
    }

    void mutate() {
        for (int i = 0; i < 2; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
            }
        }
        for (int i = 2; i < 3; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(0, ss.animal_max_health);
            }
        }
        for (int i = 3; i < 5; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
            }
        }
        for (int i = 5; i < 6; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
            }
        }
        for (int i = 6; i < 7; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
            }
        }
        for (int i = 7; i < 8; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_attraction, ss.animal_max_attraction);
            }
        }
        for (int i = 8; i < 9; i++) {
            if (r.getDouble(0, 1) < population.ss.animal_mutation_rate) {
                dna[i] = r.getDouble(ss.animal_min_perception, ss.animal_max_perception);
            }
        }
        max_speed = max_speed * (ss.animal_max_health - dna[2]) / (ss.animal_max_health / 2);
        max_force = max_force * (ss.animal_max_health - dna[2]) / (ss.animal_max_health / 2);
    }
}
