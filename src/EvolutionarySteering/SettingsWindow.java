package EvolutionarySteering;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

public class SettingsWindow extends JFrame {

    SimulationSettings ss;

    private final JTextField food_num_t;
    private final JTextField poison_num_t;
    private final JTextField animal_num_t;
    private final JTextField predator_num_t;

    private final JTextField food_rate_t;
    private final JTextField poison_rate_t;
    private final JTextField poison_max_t;

    private final JTextField animal_max_force_t;
    private final JTextField animal_max_speed_t;
    private final JTextField predator_max_force_t;
    private final JTextField predator_max_speed_t;

    private final JTextField animal_min_attraction_t;
    private final JTextField animal_max_attraction_t;
    private final JTextField animal_min_perception_t;
    private final JTextField animal_max_perception_t;
    private final JTextField animal_max_health_t;

    private final JTextField predator_min_attraction_t;
    private final JTextField predator_max_attraction_t;
    private final JTextField predator_min_perception_t;
    private final JTextField predator_max_perception_t;
    private final JTextField predator_max_health_t;

    private final JTextField animal_rr_t;
    private final JTextField animal_mutation_t;
    private final JTextField predator_rr_t;
    private final JTextField predator_mutation_t;

    private final JTextField animal_hplr_t;
    private final JTextField animal_food_t;
    private final JTextField animal_poison_t;
    private final JTextField predator_hplr_t;
    private final JTextField predator_food_t;
    private final JTextField predator_poison_t;

    SettingsWindow (SimulationSettings ss) {
        this.ss = ss;
        NumberFormat int_format = NumberFormat.getIntegerInstance();
        Border sub_pane_border = new LineBorder(Color.BLACK, 2);
        Font larger_font = new Font("Arial", Font.BOLD, 22);

        /******************************************************************/
        JLabel to_spawn_l = new JLabel("What to spawn:");
        Font standard_font = to_spawn_l.getFont().deriveFont(18.0f);
        to_spawn_l.setFont(larger_font);
        JCheckBox spawn_predators_c = new JCheckBox("Spawn predators");
        spawn_predators_c.setFont(standard_font);
        spawn_predators_c.setSelected(ss.spawn_predators);
        spawn_predators_c.addActionListener(actionEvent -> {
            ss.spawn_predators = spawn_predators_c.isSelected();
        });
        JCheckBox spawn_poison_c = new JCheckBox("Spawn poison");
        spawn_poison_c.setFont(standard_font);
        spawn_poison_c.setSelected(ss.spawn_poison);
        spawn_poison_c.addActionListener(actionEvent -> {
            ss.spawn_poison = spawn_poison_c.isSelected();
        });
        JPanel to_spawn_pane = new JPanel();
        to_spawn_pane.add(to_spawn_l);
        to_spawn_pane.add(spawn_predators_c);
        to_spawn_pane.add(spawn_poison_c);
        to_spawn_pane.setLayout(new GridLayout(3, 1));
        to_spawn_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel initial_spawn_l = new JLabel("Initial parameters:");
        initial_spawn_l.setFont(larger_font);
        JLabel food_num_l = new JLabel("Initial amount of food");
        food_num_l.setFont(standard_font);
        food_num_t =  new JTextField(3);
        food_num_t.setFont(standard_font);
        food_num_t.setText(String.valueOf(ss.initial_food));

        JLabel poison_num_l = new JLabel("Initial amount of poison");
        poison_num_l.setFont(standard_font);
        poison_num_t = new JTextField(3);
        poison_num_t.setFont(standard_font);
        poison_num_t.setText(String.valueOf(ss.initial_poison));

        JLabel animal_num_l = new JLabel("Initial number of animals");
        animal_num_l.setFont(standard_font);
        animal_num_t = new JTextField(3);
        animal_num_t.setFont(standard_font);
        animal_num_t.setText(String.valueOf(ss.initial_animals));

        JLabel predator_num_l = new JLabel("Initial number of predators");
        predator_num_l.setFont(standard_font);
        predator_num_t = new JTextField(3);
        predator_num_t.setFont(standard_font);
        predator_num_t.setText(String.valueOf(ss.initial_predators));

        JPanel initial_spawn_pane = new JPanel();
        initial_spawn_pane.add(initial_spawn_l);
        initial_spawn_pane.add(food_num_l);
        initial_spawn_pane.add(food_num_t);
        initial_spawn_pane.add(poison_num_l);
        initial_spawn_pane.add(poison_num_t);
        initial_spawn_pane.add(animal_num_l);
        initial_spawn_pane.add(animal_num_t);
        initial_spawn_pane.add(predator_num_l);
        initial_spawn_pane.add(predator_num_t);
        initial_spawn_pane.setLayout(new GridLayout(9, 1));
        initial_spawn_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel spawn_rates_l = new JLabel("Spawn rates:");
        spawn_rates_l.setFont(larger_font);
        JLabel food_rate_l = new JLabel("Food spawn rate");
        food_rate_l.setFont(standard_font);
        food_rate_t = new JTextField(6);
        food_rate_t.setFont(standard_font);
        food_rate_t.setText(String.valueOf(ss.food_spawn_rate));

        JLabel poison_rate_l = new JLabel("Poison spawn rate");
        poison_rate_l.setFont(standard_font);
        poison_rate_t = new JTextField(6);
        poison_rate_t.setFont(standard_font);
        poison_rate_t.setText(String.valueOf(ss.poison_spawn_rate));

        JLabel poison_max_l = new JLabel("Maximum poison");
        poison_max_l.setFont(standard_font);
        poison_max_t = new JTextField(3);
        poison_max_t.setFont(standard_font);
        poison_max_t.setText(String.valueOf(ss.max_poison));

        JPanel spawn_rates_pane = new JPanel();
        spawn_rates_pane.add(spawn_rates_l);
        spawn_rates_pane.add(food_rate_l);
        spawn_rates_pane.add(food_rate_t);
        spawn_rates_pane.add(poison_rate_l);
        spawn_rates_pane.add(poison_rate_t);
        spawn_rates_pane.add(poison_max_l);
        spawn_rates_pane.add(poison_max_t);
        spawn_rates_pane.setLayout(new GridLayout(7, 1));
        spawn_rates_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel physics_limits_l = new JLabel("Physics limits:");
        physics_limits_l.setFont(larger_font);
        JLabel animal_max_force_l = new JLabel("Animal max force");
        animal_max_force_l.setFont(standard_font);
        animal_max_force_t = new JTextField(6);
        animal_max_force_t.setFont(standard_font);
        animal_max_force_t.setText(String.valueOf(ss.animal_max_force));

        JLabel animal_max_speed_l = new JLabel("Animal max speed");
        animal_max_speed_l.setFont(standard_font);
        animal_max_speed_t = new JTextField(6);
        animal_max_speed_t.setFont(standard_font);
        animal_max_speed_t.setText(String.valueOf(ss.animal_max_speed));

        JLabel predator_max_force_l = new JLabel("Predator max force");
        predator_max_force_l.setFont(standard_font);
        predator_max_force_t = new JTextField(6);
        predator_max_force_t.setFont(standard_font);
        predator_max_force_t.setText(String.valueOf(ss.predator_max_force));

        JLabel predator_max_speed_l = new JLabel("Predator max speed");
        predator_max_speed_l.setFont(standard_font);
        predator_max_speed_t = new JTextField(6);
        predator_max_speed_t.setFont(standard_font);
        predator_max_speed_t.setText(String.valueOf(ss.predator_max_speed));

        JPanel physics_limits_pane = new JPanel();
        physics_limits_pane.add(physics_limits_l);
        physics_limits_pane.add(animal_max_force_l);
        physics_limits_pane.add(animal_max_force_t);
        physics_limits_pane.add(animal_max_speed_l);
        physics_limits_pane.add(animal_max_speed_t);
        physics_limits_pane.add(predator_max_force_l);
        physics_limits_pane.add(predator_max_force_t);
        physics_limits_pane.add(predator_max_speed_l);
        physics_limits_pane.add(predator_max_speed_t);
        physics_limits_pane.setLayout(new GridLayout(9, 1));
        physics_limits_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel animal_evolution_l = new JLabel("Animal evolution bounds:");
        animal_evolution_l.setFont(larger_font);
        JLabel animal_min_attraction_l = new JLabel("Minimum attraction");
        animal_min_attraction_l.setFont(standard_font);
        animal_min_attraction_t = new JTextField(6);
        animal_min_attraction_t.setFont(standard_font);
        animal_min_attraction_t.setText(String.valueOf(ss.animal_min_attraction));

        JLabel animal_max_attraction_l = new JLabel("Maximum attraction");
        animal_max_attraction_l.setFont(standard_font);
        animal_max_attraction_t = new JTextField(6);
        animal_max_attraction_t.setFont(standard_font);
        animal_max_attraction_t.setText(String.valueOf(ss.animal_max_attraction));

        JLabel animal_min_perception_l = new JLabel("Minimum perception");
        animal_min_perception_l.setFont(standard_font);
        animal_min_perception_t = new JTextField(3);
        animal_min_perception_t.setFont(standard_font);
        animal_min_perception_t.setText(String.valueOf(ss.animal_min_perception));

        JLabel animal_max_perception_l = new JLabel("Maximum perception");
        animal_max_perception_l.setFont(standard_font);
        animal_max_perception_t = new JTextField(3);
        animal_max_perception_t.setFont(standard_font);
        animal_max_perception_t.setText(String.valueOf(ss.animal_max_perception));

        JLabel animal_max_health_l = new JLabel("Maximum health");
        animal_max_health_l.setFont(standard_font);
        animal_max_health_t = new JTextField(6);
        animal_max_health_t.setFont(standard_font);
        animal_max_health_t.setText(String.valueOf(ss.animal_max_health));

        JPanel animal_evolution_pane = new JPanel();
        animal_evolution_pane.add(animal_evolution_l);
        animal_evolution_pane.add(animal_min_attraction_l);
        animal_evolution_pane.add(animal_min_attraction_t);
        animal_evolution_pane.add(animal_max_attraction_l);
        animal_evolution_pane.add(animal_max_attraction_t);
        animal_evolution_pane.add(animal_min_perception_l);
        animal_evolution_pane.add(animal_min_perception_t);
        animal_evolution_pane.add(animal_max_perception_l);
        animal_evolution_pane.add(animal_max_perception_t);
        animal_evolution_pane.add(animal_max_health_l);
        animal_evolution_pane.add(animal_max_health_t);
        animal_evolution_pane.setLayout(new GridLayout(11, 1));
        animal_evolution_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel predator_evolution_l = new JLabel("Predator evolution bounds:");
        predator_evolution_l.setFont(larger_font);
        JLabel predator_min_attraction_l = new JLabel("Minimum desire");
        predator_min_attraction_l.setFont(standard_font);
        predator_min_attraction_t = new JTextField(6);
        predator_min_attraction_t.setFont(standard_font);
        predator_min_attraction_t.setText(String.valueOf(ss.predator_min_attraction));

        JLabel predator_max_attraction_l = new JLabel("Maximum desire");
        predator_max_attraction_l.setFont(standard_font);
        predator_max_attraction_t = new JTextField(6);
        predator_max_attraction_t.setFont(standard_font);
        predator_max_attraction_t.setText(String.valueOf(ss.predator_max_attraction));

        JLabel predator_min_perception_l = new JLabel("Minimum perception");
        predator_min_perception_l.setFont(standard_font);
        predator_min_perception_t = new JTextField(3);
        predator_min_perception_t.setFont(standard_font);
        predator_min_perception_t.setText(String.valueOf(ss.predator_min_perception));

        JLabel predator_max_perception_l = new JLabel("Maximum perception");
        predator_max_perception_l.setFont(standard_font);
        predator_max_perception_t = new JTextField(3);
        predator_max_perception_t.setFont(standard_font);
        predator_max_perception_t.setText(String.valueOf(ss.animal_max_perception));

        JLabel predator_max_health_l = new JLabel("Maximum health");
        predator_max_health_l.setFont(standard_font);
        predator_max_health_t = new JTextField(6);
        predator_max_health_t.setFont(standard_font);
        predator_max_health_t.setText(String.valueOf(ss.predator_max_health));

        JPanel predator_evolution_pane = new JPanel();
        predator_evolution_pane.add(predator_evolution_l);
        predator_evolution_pane.add(predator_min_attraction_l);
        predator_evolution_pane.add(predator_min_attraction_t);
        predator_evolution_pane.add(predator_max_attraction_l);
        predator_evolution_pane.add(predator_max_attraction_t);
        predator_evolution_pane.add(predator_min_perception_l);
        predator_evolution_pane.add(predator_min_perception_t);
        predator_evolution_pane.add(predator_max_perception_l);
        predator_evolution_pane.add(predator_max_perception_t);
        predator_evolution_pane.add(predator_max_health_l);
        predator_evolution_pane.add(predator_max_health_t);
        predator_evolution_pane.setLayout(new GridLayout(11, 1));
        predator_evolution_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel reproduction_l = new JLabel("Reproduction settings:");
        reproduction_l.setFont(larger_font);
        JLabel animal_rr_l = new JLabel("Animal reproduction rate");
        animal_rr_l.setFont(standard_font);
        animal_rr_t = new JTextField(6);
        animal_rr_t.setFont(standard_font);
        animal_rr_t.setText(String.valueOf(ss.animal_rr));

        JLabel animal_mutation_l = new JLabel("Animal mutation rate");
        animal_mutation_l.setFont(standard_font);
        animal_mutation_t = new JTextField(6);
        animal_mutation_t.setFont(standard_font);
        animal_mutation_t.setText(String.valueOf(ss.animal_mutation_rate));

        JLabel predator_rr_l = new JLabel("Predator reproduction rate");
        predator_rr_l.setFont(standard_font);
        animal_rr_l.setFont(standard_font);
        predator_rr_t = new JTextField(6);
        predator_rr_t.setFont(standard_font);
        predator_rr_t.setText(String.valueOf(ss.predator_rr));

        JLabel predator_mutation_l = new JLabel("Predator mutation rate");
        predator_mutation_l.setFont(standard_font);
        predator_mutation_t = new JTextField(6);
        predator_mutation_t.setFont(standard_font);
        predator_mutation_t.setText(String.valueOf(ss.animal_mutation_rate));

        JPanel reproduction_pane = new JPanel();
        reproduction_pane.add(reproduction_l);
        reproduction_pane.add(animal_rr_l);
        reproduction_pane.add(animal_rr_t);
        reproduction_pane.add(animal_mutation_l);
        reproduction_pane.add(animal_mutation_t);
        reproduction_pane.add(predator_rr_l);
        reproduction_pane.add(predator_rr_t);
        reproduction_pane.add(predator_mutation_l);
        reproduction_pane.add(predator_mutation_t);
        reproduction_pane.setLayout(new GridLayout(9, 1));
        reproduction_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel animal_health_l = new JLabel("Animal health:");
        animal_health_l.setFont(larger_font);
        JLabel animal_hplr_l = new JLabel("Health loss rate");
        animal_hplr_l.setFont(standard_font);
        animal_hplr_t = new JTextField(6);
        animal_hplr_t.setFont(standard_font);
        animal_hplr_t.setText(String.valueOf(ss.animal_hplr));

        JLabel animal_food_l = new JLabel("Food health gain");
        animal_food_l.setFont(standard_font);
        animal_food_t = new JTextField(6);
        animal_food_t.setFont(standard_font);
        animal_food_t.setText(String.valueOf(ss.animal_food_gain));

        JLabel animal_poison_l = new JLabel("Poison health gain");
        animal_poison_l.setFont(standard_font);
        animal_poison_t = new JTextField(6);
        animal_poison_t.setFont(standard_font);
        animal_poison_t.setText(String.valueOf(ss.animal_poison_gain));

        JPanel animal_health_pane = new JPanel();
        animal_health_pane.add(animal_health_l);
        animal_health_pane.add(animal_hplr_l);
        animal_health_pane.add(animal_hplr_t);
        animal_health_pane.add(animal_food_l);
        animal_health_pane.add(animal_food_t);
        animal_health_pane.add(animal_poison_l);
        animal_health_pane.add(animal_poison_t);
        animal_health_pane.setLayout(new GridLayout(7, 1));
        animal_health_pane.setBorder(sub_pane_border);
        /******************************************************************/

        /******************************************************************/
        JLabel predator_health_l = new JLabel("Predator health:");
        predator_health_l.setFont(larger_font);
        JLabel predator_hplr_l = new JLabel("Health loss rate");
        predator_hplr_l.setFont(standard_font);
        predator_hplr_t = new JTextField(6);
        predator_hplr_t.setFont(standard_font);
        predator_hplr_t.setText(String.valueOf(ss.predator_hplr));

        JLabel predator_food_l = new JLabel("Food health gain");
        predator_food_l.setFont(standard_font);
        predator_food_t = new JTextField(6);
        predator_food_t.setFont(standard_font);
        predator_food_t.setText(String.valueOf(ss.predator_food_gain));

        JLabel predator_poison_l = new JLabel("Poison health gain");
        predator_poison_l.setFont(standard_font);
        predator_poison_t = new JTextField(6);
        predator_poison_t.setFont(standard_font);
        predator_poison_t.setText(String.valueOf(ss.predator_poison_gain));

        JPanel predator_health_pane = new JPanel();
        predator_health_pane.add(predator_health_l);
        predator_health_pane.add(predator_hplr_l);
        predator_health_pane.add(predator_hplr_t);
        predator_health_pane.add(predator_food_l);
        predator_health_pane.add(predator_food_t);
        predator_health_pane.add(predator_poison_l);
        predator_health_pane.add(predator_poison_t);
        predator_health_pane.setLayout(new GridLayout(7, 1));
        predator_health_pane.setBorder(sub_pane_border);
        /******************************************************************/

        JPanel overall_pane = new JPanel();
        overall_pane.add(to_spawn_pane);
        overall_pane.add(spawn_rates_pane);
        overall_pane.add(initial_spawn_pane);
        overall_pane.add(physics_limits_pane);
        overall_pane.add(animal_evolution_pane);
        overall_pane.add(predator_evolution_pane);
        overall_pane.add(reproduction_pane);
        overall_pane.add(animal_health_pane);
        overall_pane.add(predator_health_pane);
        overall_pane.setLayout(new GridLayout(3, 3));

        add(overall_pane);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                updateSettings();
            }
        });
        setSize(900, 800);
        setVisible(false);
        setTitle("Simulation settings");
    }

    private void updateSettings() {
        try {
            ss.initial_food = Integer.parseInt(food_num_t.getText().strip());
        } catch (NumberFormatException ex) {
            food_num_t.setText(String.valueOf(ss.initial_food));
        }
        try {
            ss.initial_poison = Integer.parseInt(poison_num_t.getText().strip());
        } catch (NumberFormatException ex) {
            poison_num_t.setText(String.valueOf(ss.initial_poison));
        }
        try {
            ss.initial_animals = Integer.parseInt(animal_num_t.getText().strip());
        } catch (NumberFormatException ex) {
            animal_num_t.setText(String.valueOf(ss.initial_animals));
        }
        try {
            ss.initial_predators = Integer.parseInt(predator_num_t.getText().strip());
        } catch (NumberFormatException ex) {
            predator_num_t.setText(String.valueOf(ss.initial_predators));
        }


        try {
            ss.food_spawn_rate = Double.parseDouble(food_rate_t.getText().strip());
        } catch (NumberFormatException ex) {
            food_rate_t.setText(String.valueOf(ss.food_spawn_rate));
        }
        try {
            ss.poison_spawn_rate = Double.parseDouble(poison_rate_t.getText().strip());
        } catch (NumberFormatException ex) {
            poison_rate_t.setText(String.valueOf(ss.poison_spawn_rate));
        }
        try {
            ss.max_poison = Integer.parseInt(poison_max_t.getText().strip());
        } catch (NumberFormatException ex) {
            poison_max_t.setText(String.valueOf(ss.max_poison));
        }


        try {
            ss.animal_max_force = Double.parseDouble(animal_max_force_t.getText().strip());
        } catch (Exception ex) {
            animal_max_force_t.setText(String.valueOf(ss.animal_max_force));
        }
        try {
            ss.animal_max_speed = Double.parseDouble(animal_max_speed_t.getText().strip());
        } catch (Exception ex) {
            animal_max_speed_t.setText(String.valueOf(ss.animal_max_speed));
        }
        try {
            ss.predator_max_force = Double.parseDouble(predator_max_force_t.getText().strip());
        } catch (Exception ex) {
            predator_max_force_t.setText(String.valueOf(ss.predator_max_force));
        }
        try {
            ss.predator_max_speed = Double.parseDouble(predator_max_speed_t.getText().strip());
        } catch (Exception ex) {
            predator_max_speed_t.setText(String.valueOf(ss.predator_max_speed));
        }


        try {
            ss.animal_min_attraction = Double.parseDouble(animal_min_attraction_t.getText().strip());
        } catch (Exception ex) {
            animal_min_attraction_t.setText(String.valueOf(ss.animal_min_attraction));
        }
        try {
            ss.animal_max_attraction = Double.parseDouble(animal_max_attraction_t.getText().strip());
        } catch (Exception ex) {
            animal_max_attraction_t.setText(String.valueOf(ss.animal_max_attraction));
        }
        try {
            ss.animal_min_perception = Integer.parseInt(animal_min_perception_t.getText().strip());
        } catch (Exception ex) {
            animal_min_perception_t.setText(String.valueOf(ss.animal_min_perception));
        }
        try {
            ss.animal_max_perception = Integer.parseInt(animal_max_perception_t.getText().strip());
        } catch (Exception ex) {
            animal_max_perception_t.setText(String.valueOf(ss.animal_max_perception));
        }
        try {
            ss.animal_max_health = Double.parseDouble(animal_max_health_t.getText().strip());
        } catch (Exception ex) {
            animal_max_health_t.setText(String.valueOf(ss.animal_max_health));
        }


        try {
            ss.predator_min_attraction = Double.parseDouble(predator_min_attraction_t.getText().strip());
        } catch (Exception ex) {
            predator_min_attraction_t.setText(String.valueOf(ss.predator_min_attraction));
        }
        try {
            ss.predator_max_attraction = Double.parseDouble(predator_max_attraction_t.getText().strip());
        } catch (Exception ex) {
            predator_max_attraction_t.setText(String.valueOf(ss.predator_max_attraction));
        }
        try {
            ss.predator_min_perception = Integer.parseInt(predator_min_perception_t.getText().strip());
        } catch (Exception ex) {
            predator_min_perception_t.setText(String.valueOf(ss.predator_min_perception));
        }
        try {
            ss.predator_max_perception = Integer.parseInt(predator_max_perception_t.getText().strip());
        } catch (Exception ex) {
            predator_max_perception_t.setText(String.valueOf(ss.predator_max_perception));
        }
        try {
            ss.predator_max_health = Double.parseDouble(predator_max_health_t.getText().strip());
        } catch (Exception ex) {
            predator_max_health_t.setText(String.valueOf(ss.predator_max_health));
        }


        try {
            ss.animal_rr = Double.parseDouble(animal_rr_t.getText().strip());
        } catch (Exception ex) {
            animal_rr_t.setText(String.valueOf(ss.animal_rr));
        }
        try {
            ss.animal_mutation_rate = Double.parseDouble(animal_mutation_t.getText().strip());
        } catch (Exception ex) {
            animal_mutation_t.setText(String.valueOf(ss.animal_mutation_rate));
        }
        try {
            ss.predator_rr = Double.parseDouble(predator_rr_t.getText().strip());
        } catch (Exception ex) {
            predator_rr_t.setText(String.valueOf(ss.predator_rr));
        }
        try {
            ss.predator_mutation_rate = Double.parseDouble(predator_mutation_t.getText().strip());
        } catch (Exception ex) {
            predator_mutation_t.setText(String.valueOf(ss.predator_mutation_rate));
        }


        try {
            ss.animal_hplr = Double.parseDouble(animal_hplr_t.getText().strip());
        } catch (NumberFormatException ex) {
            animal_hplr_t.setText(String.valueOf(ss.animal_hplr));
        }
        try {
            ss.animal_food_gain = Double.parseDouble(animal_food_t.getText().strip());
        } catch (NumberFormatException ex) {
            animal_food_t.setText(String.valueOf(ss.animal_food_gain));
        }
        try {
            ss.animal_poison_gain = Double.parseDouble(animal_poison_t.getText().strip());
        } catch (NumberFormatException ex) {
            animal_poison_t.setText(String.valueOf(ss.animal_poison_gain));
        }


        try {
            ss.predator_hplr = Double.parseDouble(predator_hplr_t.getText().strip());
        } catch (NumberFormatException ex) {
            predator_hplr_t.setText(String.valueOf(ss.predator_hplr));
        }
        try {
            ss.predator_food_gain = Double.parseDouble(predator_food_t.getText().strip());
        } catch (NumberFormatException ex) {
            predator_food_t.setText(String.valueOf(ss.predator_food_gain));
        }
        try {
            ss.predator_poison_gain = Double.parseDouble(predator_poison_t.getText().strip());
        } catch (NumberFormatException ex) {
            predator_poison_t.setText(String.valueOf(ss.predator_poison_gain));
        }
    }
}
