import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyTextField  {
    private JTextField jTextField;
    private String value;
    private int x;
    private int y;

    public MyTextField(int x, int y){
        this.x = x;
        this.y = y;
        jTextField = new JTextField();
        jTextField.setHorizontalAlignment(JLabel.CENTER);

    }

    public JTextField getjTextField(){
        return jTextField;
    }


    public void setValue(String value) {
        this.value = value;
        jTextField.setText(value);
    }









}
