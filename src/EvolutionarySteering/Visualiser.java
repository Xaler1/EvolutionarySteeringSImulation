package EvolutionarySteering;

import Helpers.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Visualiser extends JPanel implements PropertyChangeListener {
    private Graphics2D painter;
    private Environment environment;
    private Population population;
    private SimulationSettings ss;
    private EvolutionarySteeringMain parent;

    public Visualiser(Environment environment, Population population, SimulationSettings ss, EvolutionarySteeringMain parent) {
        this.environment = environment;
        this.population = population;
        this.ss = ss;
    }

    Color getHealthColor(double health, double max_health) {
        try {
            health /= max_health;
            return new Color((int) (200 - 130 * health), (int) (250 * health), (int) (77 * health));
        } catch (Exception ex) {
            System.out.println(health);
            return Color.WHITE;
        }
    }

    Color getPredatorColor(double health, double max_health) {
        health /= max_health;
        try {
            return new Color((int) (85 + 94 * health), (int) (250 * health), (int) (102 + 148 * health));
        } catch (Exception ex) {
            System.out.println(health);
            return Color.WHITE;
        }
    }

    /**
     * This paints all the shapes in the drawing model onto the canvas, as well as adding a selection box, if a shape
     * is currently selected.
     * @param graphics
     */
    @Override
    public void paintComponent(Graphics graphics) {
        try {
            super.paintComponent(graphics);
            painter = (Graphics2D) graphics;
            painter.setColor(Color.BLACK);
            painter.setStroke(new BasicStroke(2));
            Shape background = new Rectangle2D.Double(0, 0, 1250, 950);
            painter.draw(background);
            painter.fill(background);
            painter.setColor(Color.red);
            painter.draw(background);

            painter.setColor(Color.green);
            for (Vector2D food : environment.food) {
                Shape food_shape = new Ellipse2D.Double(food.x - 5, food.y - 5, 10, 10);
                painter.draw(food_shape);
                painter.fill(food_shape);
            }

            painter.setColor(Color.red);
            for (Vector2D poison : environment.poison) {
                Shape food_shape = new Ellipse2D.Double(poison.x - 5, poison.y - 5, 10, 10);
                painter.draw(food_shape);
                painter.fill(food_shape);
            }

            for (Animal animal : population.animals) {
                try {
                    painter.setColor(getHealthColor(animal.health, animal.dna[2]));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.printf("%f / %f\n", animal.health, animal.dna[2]);
                }
                painter.rotate(animal.rotation, animal.location.x, animal.location.y);
                painter.draw(animal.body);
                painter.fill(animal.body);
                painter.rotate(-1 * animal.rotation, animal.location.x, animal.location.y);

                painter.setStroke(new BasicStroke(3));
                if (ss.show_forces) {
                    painter.setColor(Color.red);
                    painter.draw(animal.poison_line);
                    painter.setColor(Color.green);
                    painter.draw(animal.food_line);
                    painter.setColor(Color.cyan);
                    painter.draw(animal.resultant_line);
                }

                if (ss.show_perceptions) {
                    painter.setColor(Color.red);
                    double r = animal.dna[4];
                    Shape poison_vision = new Ellipse2D.Double(animal.location.x - r, animal.location.y - r, r * 2, r * 2);
                    painter.draw(poison_vision);
                    painter.setColor(Color.green);
                    r = animal.dna[3];
                    Shape food_vision = new Ellipse2D.Double(animal.location.x - r, animal.location.y - r, r * 2, r * 2);
                    painter.draw(food_vision);
                }
                painter.setStroke(new BasicStroke(2));
            }

            for (Predator predator : population.predators) {
                painter.setColor(getPredatorColor(predator.health, predator.dna[6]));
                painter.rotate(predator.rotation, predator.location.x, predator.location.y);
                painter.draw(predator.body);
                painter.fill(predator.body);
                painter.rotate(-1 * predator.rotation, predator.location.x, predator.location.y);
            }

            painter.setColor(Color.BLACK);
            painter.drawString(String.format("Average animal time alive: %.3f\n ", population.average_time_alive), 1250, 20);
            painter.drawString(String.format("Average animal food attraction: %.4f", population.average_attraction), 1250, 40);
            painter.drawString(String.format("Average animal poison attraction: %.4f", population.average_repulsion), 1250, 60);
            painter.drawString(String.format("Average animal-animal attraction: %.4f", population.average_animal_attraction), 1250, 80);
            painter.drawString(String.format("Average animal-predator attraction: %.4f", population.average_predator_attraction), 1250, 100);
            painter.drawString(String.format("Average animal max health: %.2f", population.average_max_health), 1250, 120);
            painter.drawString(String.format("Average animal food perception: %.0f", population.average_food_perception), 1250, 140);
            painter.drawString(String.format("Average animal poison perception: %.0f", population.average_poison_perception), 1250, 160);
            painter.drawString(String.format("Average animal-animal perception: %.0f", population.average_animal_perception), 1250, 180);
            painter.drawString(String.format("Average animal-predator perception: %.4f", population.average_predator_perception), 1250, 200);
            painter.drawString(String.format("Animal population: %d", population.animals.size()), 1250, 220);
            painter.drawString(String.format("Average animal children: %.1f", population.average_children), 1250, 240);

            painter.drawString(String.format("Average predator time alive: %.3f\n ", population.predator_time_alive), 1250, 280);
            painter.drawString(String.format("Average predator-animal attraction: %.4f", population.predator_attraction), 1250, 300);
            painter.drawString(String.format("Average predator poison attraction: %.4f", population.predator_repulsion), 1250, 320);
            painter.drawString(String.format("Average predator-predator attraction: %.4f", population.predator_predator_attraction), 1250, 340);
            painter.drawString(String.format("Average predator max health: %.2f", population.predator_max_health), 1250, 360);
            painter.drawString(String.format("Average predator-animal perception: %.0f", population.predator_animal_perception), 1250, 380);
            painter.drawString(String.format("Average predator poison perception: %.0f", population.predator_poison_perception), 1250, 400);
            painter.drawString(String.format("Average predator-predator perception: %.0f", population.predator_predator_perception), 1250, 420);
            painter.drawString(String.format("Predator population: %d", population.predators.size()), 1250, 440);
            painter.drawString(String.format("Average predator children: %.1f", population.predator_children), 1250, 460);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Too many objects on screen, the renderer can't keep up!", "Render warning!", JOptionPane.WARNING_MESSAGE);
            parent.paused = true;
        }
    }

    /**
     * This detects a property change event and repaints the canvas.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        removeAll();
        repaint();
    }
}
