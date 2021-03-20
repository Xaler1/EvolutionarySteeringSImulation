package EvolutionarySteering;

import Helpers.Vector2D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class EvolutionarySteeringMain extends JFrame {

    Population population;
    Environment environment;
    Visualiser visualiser;
    SimulationSettings ss;

    int i;
    int target = 0;
    boolean paused = false;

    private final JButton pause_btn;
    private SettingsWindow settings_window;
    private final JFileChooser file_chooser;
    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Evolution settings", "es");

    EvolutionarySteeringMain () {
        ss = new SimulationSettings();
        environment = new Environment(ss);
        population = new Population(environment, ss);

        settings_window = new SettingsWindow(ss);
        JLayeredPane pane = new JLayeredPane();
        add(pane);

        pause_btn = new JButton("Pause");
        pause_btn.addActionListener(e -> {
            if (paused) {
                paused = false;
                pause_btn.setText("Pause");
            } else {
                paused = true;
                pause_btn.setText("Resume");
            }
        });
        Font font = pause_btn.getFont();
        pause_btn.setBounds(1300, 500, 200, 50);
        pause_btn.setFont(font.deriveFont(24.0f));

        JButton skip_btn = new JButton("Skip 1000 steps");
        skip_btn.addActionListener(e -> { target = i + 1000; });
        skip_btn.setBounds(1300, 560, 200, 50);
        skip_btn.setFont(font.deriveFont(20.0f));

        JButton settings_btn = new JButton("Settings");
        settings_btn.addActionListener(e -> {
            paused = true;
            pause_btn.setText("Resume");
            settings_window.setVisible(true);
        });
        settings_btn.setBounds(1300, 620, 200, 50);
        settings_btn.setFont(font.deriveFont(24.0f));

        JButton restart_btn = new JButton("Restart");
        restart_btn.addActionListener(e -> {
            population.reset();
            environment.reset();
            paused = false;
            pause_btn.setText("Pause");
        });
        restart_btn.setBounds(1300, 680, 200, 50);
        restart_btn.setFont(font.deriveFont(24.0f));

        JButton save_btn = new JButton("Save preset");
        save_btn.addActionListener(e -> {
            savePreset();
        });
        save_btn.setBounds(1300, 740, 200, 50);
        save_btn.setFont(font.deriveFont(22.0f));

        JButton load_btn = new JButton("Load preset");
        load_btn.addActionListener(e -> {
            loadPreset();
        });
        load_btn.setBounds(1300, 800, 200, 50);
        load_btn.setFont(font.deriveFont(22.0f));

        file_chooser = new JFileChooser();
        file_chooser.setFileFilter(filter);
        visualiser = new Visualiser(environment, population, ss, this);
        visualiser.setBounds(0, 0, 1500, 950);
        environment.addObserver(visualiser);
        environment.update();

        pane.add(settings_btn, 2, 0);
        pane.add(pause_btn, 2, 0);
        pane.add(skip_btn, 2, 0);
        pane.add(restart_btn, 2, 0);
        pane.add(save_btn, 2, 0);
        pane.add(load_btn, 2, 0);
        pane.add(visualiser, 1, 0);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { }

        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int) (1550), (int) (1000));
        setVisible(true);
        setTitle("Evolutionary Steering");
    }

    public void runSim() {
        SwingWorker simmer = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                    if (!paused) {
                        /*
                        if (population.animals.size() == 0) {
                            System.out.println("The predators have won!");
                            population.reset();
                            paused = true;
                            pause_btn.setText("Start new");
                            i = 0;
                            target = 0;
                        } else if (population.predators.size() == 0) {
                            System.out.println("The animals have won!");
                            population.reset();
                            paused = true;
                            pause_btn.setText("Start new");
                            i = 0;
                            target = 0;
                        }
                         */
                        population.advance();
                        if (i >= target) {
                            environment.update();
                            Thread.sleep(20);
                        }
                        i++;
                        if (i < 0) {
                            break;
                        }
                    }
                    Thread.sleep(1);
                }
                return null;
            }
        };
        simmer.execute();
    }

    private void savePreset() {
        paused = true;
        if (file_chooser.showSaveDialog(this) == 0) {
            String file_name = file_chooser.getSelectedFile().toString();
            if (!file_name.matches(".*(\\.es)")) {
                file_name = file_name + ".es";
            }
            try {
                FileOutputStream file_out = new FileOutputStream(file_name);
                ObjectOutputStream object_out = new ObjectOutputStream(file_out);
                object_out.writeObject(ss);
                object_out.close();
                file_out.close();
                JOptionPane.showMessageDialog(this, "Preset saved.", "Save success", JOptionPane.PLAIN_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving preset.", "Save error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (pause_btn.getText().equals("Pause")) {
            paused = false;
        }
    }

    private void loadPreset() {
        paused = true;
        if (file_chooser.showOpenDialog(this) == 0) {
            String file_name = file_chooser.getSelectedFile().toString();
            if (!file_name.matches(".*(\\.es)")) {
                file_name = file_name + ".es";
            }
            try {
                FileInputStream file_in = new FileInputStream(file_name);
                ObjectInputStream object_in = new ObjectInputStream(file_in);
                ss = (SimulationSettings) object_in.readObject();
                settings_window = new SettingsWindow(ss);
                JOptionPane.showMessageDialog(this, "Preset loaded.", "Load success", JOptionPane.PLAIN_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found.", "Load error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading file.", "Load error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (pause_btn.getText().equals("Pause")) {
            paused = false;
        }
    }

    public static void main(String[] args) {
        EvolutionarySteeringMain m = new EvolutionarySteeringMain();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
        m.runSim();
    }

}
