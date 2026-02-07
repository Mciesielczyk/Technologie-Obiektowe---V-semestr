package Observers;

import javax.swing.*;

public class GuiObserver implements Observer {

    private JTextArea textArea;

    public GuiObserver(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void update(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
