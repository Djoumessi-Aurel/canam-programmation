package utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public class FocusComponentListener extends MouseAdapter {
    private final JComponent textField;

    public FocusComponentListener(JComponent textField) {
        this.textField = textField;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        textField.requestFocusInWindow();
    }
}