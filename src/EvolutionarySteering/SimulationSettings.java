package EvolutionarySteering;

import java.io.Serializable;

public class SimulationSettings implements Serializable {
    public boolean spawn_poison = true;
    public boolean spawn_predators = true;

    public int initial_animals = 100;
    public int initial_predators = 30;
    public int initial_food = 80;
    public int initial_poison = 40;

    public double food_spawn_rate = 0.15;
    public double poison_spawn_rate = 0.05;
    public int max_poison = 60;

    public double predator_max_force = 1.5;
    public double predator_max_speed = 2;
    public double animal_max_force = 1.5;
    public double animal_max_speed = 2;

    public double predator_hplr = 0.001;
    public double animal_hplr = 0.001;

    public int animal_max_perception = 150;
    public int animal_min_perception = 20;
    public int predator_max_perception = 150;
    public int predator_min_perception = 20;

    public double animal_max_health = 4;
    public double predator_max_health = 4;

    public double animal_max_attraction = 5;
    public double animal_min_attraction = -5;
    public double predator_max_attraction = 5;
    public double predator_min_attraction = -5;

    public double animal_rr = 0.001;
    public double predator_rr = 0.0005;
    public double animal_mutation_rate = 0.05;
    public double predator_mutation_rate = 0.05;

    public double animal_food_gain = 1.0;
    public double animal_poison_gain = -1.5;
    public double predator_food_gain = 2.0;
    public double predator_poison_gain = -1.0;

    public int log_interval = 100;
}
