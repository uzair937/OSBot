import org.osbot.rs07.api.model.Entity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CowGUI {
    private final JDialog mainDialog;
    private final JComboBox<Cow> cowBox;

    private boolean started;

    public CowGUI() {
        mainDialog = new JDialog();
        mainDialog.setTitle("Uzair's Cow Killer");
        mainDialog.setModal(true);
        mainDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainDialog.getContentPane().add(mainPanel);

        JPanel cowSelector = new JPanel();
        cowSelector.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel select = new JLabel("Select Cow Types:");
        cowSelector.add(select);

        cowBox = new JComboBox<>(Cow.values());
        cowSelector.add(cowBox);

        mainPanel.add(cowBox);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            started = true;
            close();
        });
        mainPanel.add(startButton);

        mainDialog.pack();
    }

    public boolean isStarted() {
        return started;
    }

    public Cow getSelectedCow() {
        return (Cow) cowBox.getSelectedItem();
    }

    public void open() {
        mainDialog.setVisible(true);
    }

    public void close() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }
}

enum Cow {
    COW,
    COW_CALF;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}