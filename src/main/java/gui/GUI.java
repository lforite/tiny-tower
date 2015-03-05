package gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * User: louis.forite
 * Date: 24/02/15
 * Time: 21:36
 */
public class GUI extends JFrame {

    private Map<String, JButton> mapActionToButton = new HashMap<>();

    private JTextField floorNumberTextField;

    public GUI() {
        super("JPrompt Demo");

        JPanel panel = new JPanel();

        mapActionToButton.put("restock", new JButton("restock button"));
        mapActionToButton.put("buildRecreationStore", new JButton("build recreation store"));
        mapActionToButton.put("buildServiceStore", new JButton("build service store"));
        mapActionToButton.put("buildFoodStore", new JButton("build food store"));
        mapActionToButton.put("buildCreativeStore", new JButton("build creative store"));
        mapActionToButton.put("buildRetailStore", new JButton("build retail store"));

        for (Map.Entry<String, JButton> entry : mapActionToButton.entrySet()) {
            entry.getValue().setActionCommand(entry.getKey());
            panel.add(entry.getValue());
        }

        floorNumberTextField = new JTextField(3);
        panel.add(floorNumberTextField);

        add(panel);

        setBounds(100, 100, 200, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void suscribeButtonsToActionListener(ActionListener actionListener) {
        for (JButton button : mapActionToButton.values()) {
            button.addActionListener(actionListener);
        }
    }

    public String getFloorNumber() {
        return floorNumberTextField.getText();
    }
}
