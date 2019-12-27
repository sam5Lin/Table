import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/*
自定义一个弹出菜单
 */
public class MyPopupMenu extends JPopupMenu {
	private JPopupMenu m_popupMenu = new JPopupMenu();
	private ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();

    MyPopupMenu(){
        menuItemList.add(new JMenuItem("  删除行  "));
        menuItemList.add(new JMenuItem("  删除列  "));
        menuItemList.forEach(m_popupMenu::add);
    }

	public void show(Component invoker, int x, int y, int row, int col,int rowIndex, int colIndex, String[][] tableValues, MyTextField[][] arrays) {

        /*
        监听删除行的动作
         */
        menuItemList.get(0).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {


                for(int i = row ; i < rowIndex - 1; i++){
                    for(int j = 0; j < colIndex ; j++){
                        String data = tableValues[i + 1][j];
                        tableValues[i][j] = data;
                    }
                }


                System.out.println(rowIndex + " " + colIndex);
                for(int i = 0; i < colIndex ; i++){
                    tableValues[rowIndex - 1][i] = "";
                }

                for(int i = 0; i < rowIndex; i++ ){
                    for(int j = 0; j < colIndex ; j++){
                        arrays[i][j].getjTextField().setText(tableValues[i][j]);
                    }
                }

            }
        });

        
        /*
        监听删除列的动作
         */
        menuItemList.get(1).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {


                System.out.println(rowIndex + " " + colIndex);
                for(int i = col; i < colIndex - 1; i++){
                    for(int j = 0; j < rowIndex; j++){
                        String data = tableValues[j][i + 1];
                        tableValues[j][i] = data;
                    }
                }

                for(int i = 0; i < rowIndex; i++){
                    tableValues[i][colIndex - 1] = "";
                }

                for(int i = 0; i < rowIndex; i++ ){
                    for(int j = 0; j < colIndex ; j++){
                        System.out.println(tableValues[i][j]);
                        arrays[i][j].getjTextField().setText(tableValues[i][j]);
                    }
                }

            }
        });
        
        m_popupMenu.show(invoker, x, y);

	}

}
