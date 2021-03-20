package EvolutionarySteering;

import Helpers.Randomiser;
import Helpers.Vector2D;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Population {
    ArrayList<Animal> animals;
    ArrayList<Predator> predators;
    Deque<Double> animal_times;
    Deque<Integer> animal_childs;
    Deque<Double> predator_times;
    Deque<Integer> predator_childs;

    Randomiser r;
    Environment environment;
    SimulationSettings ss;

    double average_time_alive = 0;
    double average_max_health = 0;
    double average_attraction = 0;
    double average_repulsion = 0;
    double average_predator_attraction = 0;
    double average_animal_attraction = 0;
    double average_food_perception = 0;
    double average_poison_perception = 0;
    double average_animal_perception = 0;
    double average_predator_perception = 0;
    double average_children = 0;

    double predator_time_alive = 0;
    double predator_max_health = 0;
    double predator_attraction = 0;
    double predator_repulsion = 0;
    double predator_predator_attraction = 0;
    double predator_animal_perception = 0;
    double predator_poison_perception = 0;
    double predator_predator_perception = 0;
    double predator_children = 0;

    FileWriter log_writer;
    int counter;

    public Population (Environment environment, SimulationSettings ss) {
        animals = new ArrayList<>();
        predators = new ArrayList<>();
        animal_times = new ArrayDeque<>();
        animal_childs = new ArrayDeque<>();
        predator_times = new ArrayDeque<>();
        predator_childs = new ArrayDeque<>();

        this.environment = environment;
        this.ss = ss;
        counter = 0;

        try {
            log_writer = new FileWriter("evolution_log.txt");
        } catch (IOException ex) { }
        r = new Randomiser();
        for (int i = 0; i < ss.initial_animals; i++) {
            animals.add(new Animal(new Vector2D(r.getInt(50, 1200), r.getInt(50, 850)), ss.animal_max_force, ss.animal_max_speed, environment, this));
        }
        if (ss.spawn_predators) {
            for (int i = 0; i < ss.initial_predators; i++) {
                predators.add(new Predator(new Vector2D(r.getInt(50, 1200), r.getInt(50, 850)), ss.predator_max_force, ss.animal_max_speed, environment, this));
            }
        }
    }

    void reset () {
        animals.clear();
        predators.clear();
        animal_times.clear();
        animal_childs.clear();
        environment.reset();
        counter = 0;
        environment.reset();
        for (int i = 0; i < ss.initial_animals; i++) {
            animals.add(new Animal(new Vector2D(r.getInt(50, 1200), r.getInt(50, 850)), ss.animal_max_force, ss.animal_max_speed, environment, this));
        }
        if (ss.spawn_predators) {
            for (int i = 0; i < ss.initial_predators; i++) {
                predators.add(new Predator(new Vector2D(r.getInt(50, 1200), r.getInt(50, 850)), ss.predator_max_force, ss.animal_max_speed, environment, this));
            }
        }
    }

    public void advance() {
        advancePredators();

        advanceAnimals();

        spawnFoodPoison();
        if (counter % ss.log_interval == 0) {
            try {
                log_writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f,%f,%d\n",
                        average_time_alive,
                        average_attraction,
                        average_repulsion,
                        average_animal_attraction,
                        average_max_health,
                        average_food_perception,
                        average_poison_perception,
                        average_animal_perception,
                        average_children,
                        animals.size()));
                log_writer.flush();
            } catch (IOException ex) {
            }
        }
        counter++;
    }

    public void advancePredators() {
        predator_time_alive = 0;
        predator_max_health = 0;
        predator_attraction = 0;
        predator_repulsion = 0;
        predator_predator_attraction = 0;
        predator_animal_perception = 0;
        predator_poison_perception = 0;
        predator_predator_perception = 0;
        predator_children = 0;

        for (int i = predators.size() - 1; i >= 0; i--) {
            Predator predator = predators.get(i);
            predator.advance();
            if (predator.dead) {
                predators.remove(predator);
                predator_times.add(predator.fitness);
                if (predator_times.size() > 30) {
                    predator_times.removeFirst();
                }
                predator_childs.add(predator.children);
                if (predator_childs.size() > 30) {
                    predator_childs.removeFirst();
                }
            } else if (r.getDouble(0, 1) < ss.predator_rr) {
                predators.add(predator.cloneMe());
            }
            predator_max_health += predator.dna[6] / (predators.size() - 1);
            predator_attraction += predator.dna[0] / (predators.size() - 1);
            predator_repulsion += predator.dna[1] / (predators.size() - 1);
            predator_predator_attraction += predator.dna[2] / (predators.size() - 1);
            predator_animal_perception += predator.dna[3] / (predators.size() - 1);
            predator_poison_perception += predator.dna[4] / (predators.size() - 1);
            predator_predator_perception += predator.dna[5] / (predators.size() - 1);
        }

        for (Double i : predator_times) {
            predator_time_alive += i * 100 / (predator_times.size());
        }
        for (Integer i : predator_childs) {
            predator_children += (float)i  / (predator_childs.size());
        }
    }

    public void advanceAnimals() {
        average_time_alive = 0;
        average_max_health = 0;
        average_attraction = 0;
        average_repulsion = 0;
        average_animal_attraction = 0;
        average_predator_attraction = 0;
        average_food_perception = 0;
        average_poison_perception = 0;
        average_animal_perception = 0;
        average_predator_perception = 0;
        average_children = 0;

        for (int i = animals.size() - 1; i >= 0; i--) {
            Animal animal = animals.get(i);
            try {
                animal.advance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (animal.dead) {
                animals.remove(animal);
                Vector2D location = animal.location;
                if (location.x < 30 || location.x > 1220 || location.y < 30 || location.y > 870) {
                    //environment.addFood(new Vector2D(625, 425));
                } else {
                    //environment.addFood(animal.location);
                }
                animal_times.addLast(animal.fitness);
                animal_childs.addLast(animal.children);
                if (animal_times.size() > 30) {
                    animal_times.removeFirst();
                }
                if (animal_childs.size() > 30) {
                    animal_childs.removeFirst();
                }
            } else {
                if (r.getDouble(0, 1) < ss.animal_rr) {
                    animals.add(animal.cloneMe());
                }
            }
            average_attraction += animal.dna[0] / animals.size();
            average_poison_perception += animal.dna[4] / animals.size();
            average_repulsion += animal.dna[1] / animals.size();
            average_predator_attraction += animal.dna[7] / animals.size();
            average_max_health += animal.dna[2] / animals.size();
            average_food_perception += animal.dna[3] / animals.size();
            average_animal_attraction += animal.dna[5] / animals.size();
            average_animal_perception += animal.dna[6] / animals.size();
            average_predator_perception += animal.dna[8] / animals.size();
        }
        for (Double i : animal_times) {
            average_time_alive += i * 100 / animal_times.size();
        }
        for (Integer i : animal_childs) {
            average_children += (float)i / animal_childs.size();
        }
    }

    void spawnFoodPoison() {
        double chance = r.getDouble(0, 1);
        if (chance < ss.food_spawn_rate) {
            environment.addFood(new Vector2D(r.getInt(50, 1200), r.getInt(50, 900)));
        } else if (ss.spawn_poison && chance > (1 - ss.poison_spawn_rate) && environment.poison.size() < ss.max_poison) {
            environment.addPoison(new Vector2D(r.getInt(50, 1200), r.getInt(50, 900)));
        }
    }
}
