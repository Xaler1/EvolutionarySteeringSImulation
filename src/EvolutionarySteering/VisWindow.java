package EvolutionarySteering;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VisWindow extends JFrame {

    private VisSettings vs;

    public VisWindow(VisSettings vs) {
        this.vs = vs;

        Font standard_font = new Font("Arial", Font.PLAIN, 20);
        Font larger_font = new Font("Arial", Font.BOLD, 24);
        Border standard_border = new LineBorder(Color.BLACK, 2);

        /******************************************************************************/
        JLabel animal_l = new JLabel("Animal attractions:");
        animal_l.setAutoscrolls(true);
        animal_l.setFont(larger_font);
        JCheckBox animal_food_l = new JCheckBox("Show food attraction\n(green)");
        animal_food_l.setFont(standard_font);
        animal_food_l.setSelected(vs.show_animal_food_l);
        animal_food_l.addActionListener(e -> {
            vs.show_animal_food_l = animal_food_l.isSelected();
        });
        JCheckBox animal_poison_l = new JCheckBox("Show poison attraction\n(red)");
        animal_poison_l.setFont(standard_font);
        animal_poison_l.setSelected(vs.show_animal_poison_l);
        animal_poison_l.addActionListener(e -> {
            vs.show_animal_poison_l = animal_poison_l.isSelected();
        });
        JCheckBox animal_animal_l = new JCheckBox("Show animal attraction\n(light-gray)");
        animal_animal_l.setFont(standard_font);
        animal_animal_l.setSelected(vs.show_animal_animal_l);
        animal_animal_l.addActionListener(e -> {
            vs.show_animal_animal_l = animal_animal_l.isSelected();
        });
        JCheckBox animal_predator_l = new JCheckBox("Show predator attraction\n(magenta)");
        animal_predator_l.setFont(standard_font);
        animal_predator_l.setSelected(vs.show_animal_predator_l);
        animal_predator_l.addActionListener(e -> {
            vs.show_animal_predator_l = animal_predator_l.isSelected();
        });
        JPanel animal_l_pane = new JPanel();
        animal_l_pane.add(animal_l);
        animal_l_pane.add(animal_food_l);
        animal_l_pane.add(animal_poison_l);
        animal_l_pane.add(animal_animal_l);
        animal_l_pane.add(animal_predator_l);
        animal_l_pane.setLayout(new GridLayout(6, 1));
        animal_l_pane.setBorder(standard_border);
        /******************************************************************************/

        /******************************************************************************/
        JLabel animal_p = new JLabel("Animal perceptions:");
        animal_p.setFont(larger_font);
        JCheckBox animal_food_p = new JCheckBox("Show food perception\n(green)");
        animal_food_p.setFont(standard_font);
        animal_food_p.setSelected(vs.show_animal_food_p);
        animal_food_p.addActionListener(e -> {
            vs.show_animal_food_p = animal_food_p.isSelected();
        });
        JCheckBox animal_poison_p = new JCheckBox("Show poison perception\n(red)");
        animal_poison_p.setFont(standard_font);
        animal_poison_p.setSelected(vs.show_animal_poison_p);
        animal_poison_p.addActionListener(e -> {
            vs.show_animal_poison_p = animal_poison_p.isSelected();
        });
        JCheckBox animal_animal_p = new JCheckBox("Show animal perception\n(light-gray)");
        animal_animal_p.setFont(standard_font);
        animal_animal_p.setSelected(vs.show_animal_animal_p);
        animal_animal_p.addActionListener(e -> {
            vs.show_animal_animal_p = animal_animal_p.isSelected();
        });
        JCheckBox animal_predator_p = new JCheckBox("Show predator perception\n(magenta)");
        animal_predator_p.setFont(standard_font);
        animal_predator_p.setSelected(vs.show_animal_predator_p);
        animal_predator_p.addActionListener(e -> {
            vs.show_animal_predator_p = animal_predator_p.isSelected();
        });
        JPanel animal_p_pane = new JPanel();
        animal_p_pane.add(animal_p);
        animal_p_pane.add(animal_food_p);
        animal_p_pane.add(animal_poison_p);
        animal_p_pane.add(animal_animal_p);
        animal_p_pane.add(animal_predator_p);
        animal_p_pane.setLayout(new GridLayout(6, 1));
        animal_p_pane.setBorder(standard_border);
        /******************************************************************************/


        /******************************************************************************/
        JLabel predator_l = new JLabel("Predator attractions:");
        predator_l.setFont(larger_font);
        JCheckBox predator_food_l = new JCheckBox("Show animal attraction\n(green)");
        predator_food_l.setFont(standard_font);
        predator_food_l.setSelected(vs.show_predator_animal_l);
        predator_food_l.addActionListener(e -> {
            vs.show_predator_animal_l = predator_food_l.isSelected();
        });
        JCheckBox predator_poison_l = new JCheckBox("Show poison attraction\n(red)");
        predator_poison_l.setFont(standard_font);
        predator_poison_l.setSelected(vs.show_predator_poison_l);
        predator_poison_l.addActionListener(e -> {
            vs.show_predator_poison_l = predator_poison_l.isSelected();
        });
        JCheckBox predator_predator_l = new JCheckBox("Show predator attraction\n(magenta)");
        predator_predator_l.setFont(standard_font);
        predator_predator_l.setSelected(vs.show_predator_predator_l);
        predator_predator_l.addActionListener(e -> {
            vs.show_predator_predator_l = predator_predator_l.isSelected();
        });
        JPanel predator_l_pane = new JPanel();
        predator_l_pane.add(predator_l);
        predator_l_pane.add(predator_food_l);
        predator_l_pane.add(predator_poison_l);
        predator_l_pane.add(predator_predator_l);
        predator_l_pane.setLayout(new GridLayout(5, 1));
        predator_l_pane.setBorder(standard_border);
        /******************************************************************************/

        /******************************************************************************/
        JLabel predator_p = new JLabel("Predator perceptions:");
        predator_p.setFont(larger_font);
        JCheckBox predator_food_p = new JCheckBox("Show animal perception\n(green)");
        predator_food_p.setFont(standard_font);
        predator_food_p.setSelected(vs.show_predator_animal_p);
        predator_food_p.addActionListener(e -> {
            vs.show_predator_animal_p = predator_food_p.isSelected();
        });
        JCheckBox predator_poison_p = new JCheckBox("Show poison perception\n(red)");
        predator_poison_p.setFont(standard_font);
        predator_poison_p.setSelected(vs.show_predator_poison_p);
        predator_poison_p.addActionListener(e -> {
            vs.show_predator_poison_p = predator_poison_p.isSelected();
        });
        JCheckBox predator_predator_p = new JCheckBox("Show predator perception\n(magenta)");
        predator_predator_p.setFont(standard_font);
        predator_predator_p.setSelected(vs.show_predator_predator_p);
        predator_predator_p.addActionListener(e -> {
            vs.show_predator_predator_p = predator_predator_p.isSelected();
        });
        JPanel predator_p_pane = new JPanel();
        predator_p_pane.add(predator_p);
        predator_p_pane.add(predator_food_p);
        predator_p_pane.add(predator_poison_p);
        predator_p_pane.add(predator_predator_p);
        predator_p_pane.setLayout(new GridLayout(5, 1));
        predator_p_pane.setBorder(standard_border);
        /******************************************************************************/

        JPanel overall = new JPanel();
        overall.add(animal_l_pane);
        overall.add(animal_p_pane);
        overall.add(predator_l_pane);
        overall.add(predator_p_pane);
        overall.setLayout(new GridLayout(2, 2));

        add(overall);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(750, 350);
        setVisible(false);
        setTitle("Visualisation settings");
    }

}
