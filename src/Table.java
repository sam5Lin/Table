import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Pattern;


public class Table extends JFrame {


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Table frame = new Table();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private int rowIndex = 10;
    private int colIndex = 26;

    private int size = rowIndex * (colIndex + 1);
    //定义按钮数组
    private JButton jButton[] = new JButton[colIndex + 1];
    private String[][] tableValues = new String[rowIndex][colIndex];
    private int[] updown;   //记录每一列的表头当前是降序还是升序
    private MyTextField[][] arrays;

    public Table(){
        //设置窗格属性
        this.setTitle("演示网格布局管理器");
        this.setSize(1600, 800);
        this.setLocation(20, 20);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);  //Resizable:可调整大小的

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(rowIndex + 1,colIndex + 1));
        updown = new int[colIndex + 1];
        arrays = new MyTextField[rowIndex][colIndex];
        try {
            tableValues = (String[][]) readObjectFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton b = new JButton();
        b.enable(false);
        jPanel.add(new JButton());

        for(int i = 1; i <= colIndex; i++){
            char c = (char)('A' + i - 1);
            jButton[i] = new JButton(String.valueOf(c));
            int buttonColumn = i;
            int column = buttonColumn - 1;
            jButton[buttonColumn].addMouseListener(new MouseAdapter(){

                public void mouseClicked(MouseEvent mouseEvent) {
                    if(updown[column] == 0){
                        updown[column] = 1;
                        for(int i = 1; i < rowIndex; i++) {
                            boolean flag = false;
                            for(int j = i + 1;j < rowIndex ;j++){
                                String data1 = tableValues[i][column];
                                String data2 = tableValues[j][column];

                                if (data1 != null && data1.length() != 0 && data2 != null  && data2.length() != 0 ){
                                    if(isInteger(data1) && isInteger(data2)){ //如果都是数字
                                        double num1 = Double.valueOf(data1);
                                        double num2 = Double.valueOf(data2);
                                        if(num1 > num2){
                                            tableValues[i][column] = replace(String.valueOf(num2));
                                            tableValues[j][column] = replace(String.valueOf(num1));
                                            flag = true;
                                            for(int index = 0; index < colIndex ; index++){
                                                if(index != column){
                                                    data1 = tableValues[i][index];
                                                    data2 = tableValues[j][index];

                                                    tableValues[i][index] = data2;
                                                    tableValues[j][index] = data1;
                                                }
                                            }
                                        }
                                    }
                                    else if(!isInteger(data1) && isInteger(data2)){ //第一个字符串，第二个数字
                                        double num2 = Double.valueOf(data2);
                                        System.out.println(data1 + " " + num2);
                                        tableValues[i][column] = replace(String.valueOf(num2));
                                        tableValues[j][column] = data1;
                                        flag = true;
                                        for(int index = 0; index < colIndex ; index++){
                                            if(index != column){
                                                data1 = tableValues[i][index];
                                                data2 = tableValues[j][index];

                                                tableValues[i][index] = data2;
                                                tableValues[j][index] = data1;

                                            }
                                        }
                                    }
                                    else if(!isInteger(data1) && !isInteger(data2)){  //两个都不是数字，按照字符串来比较
                                        System.out.println(data1 + " " + data2);
                                        System.out.println(data1.compareTo(data2));
                                        if(data1.compareTo(data2) > 0){
                                            tableValues[i][column] = data2;
                                            tableValues[j][column] = data1;
                                            flag = true;
                                            for(int index = 0; index < colIndex ; index++){
                                                if(index != column){
                                                    data1 = tableValues[i][index];
                                                    data2 = tableValues[j][index];


                                                    tableValues[i][index] = data2;
                                                    tableValues[j][index] = data1;
                                                }
                                            }
                                        }
                                    }
                                }
                                else if((data1 == null || data1.length() == 0) && (data2 != null && data2.length() != 0)){   //空格无论是降序还是升序都是最后面

                                    tableValues[i][column] = data2;
                                    tableValues[j][column] = data1;
                                    flag = true;
                                    for(int index = 0; index < colIndex ; index++){
                                        if(index != column){

                                            data1 = tableValues[i][index];
                                            data2 = tableValues[j][index];

                                            tableValues[i][index] = data2;
                                            tableValues[j][index] = data1;

                                        }
                                    }
                                }
                            }
                            if(!flag) break;   //如果当前已经是正确的顺序，直接跳出循环
                        }
                    }
                    else {
                        updown[column] = 0;
                        for(int i = 1; i < rowIndex; i++) {
                            boolean flag = false;
                            for(int j = i + 1; j < rowIndex; j++){
                                String data1 = tableValues[i][column];
                                String data2 = tableValues[j][column];

                                if (data1 != null && data1.length() != 0 && data2 != null  && data2.length() != 0 ){
                                    if(isInteger(data1) && isInteger(data2)){ //如果都是数字
                                        double num1 = Double.valueOf(data1);
                                        double num2 = Double.valueOf(data2);
                                        if(num1 < num2){

                                            tableValues[i][column] = replace(String.valueOf(num2));
                                            tableValues[j][column] = replace(String.valueOf(num1));

                                            flag = true;
                                            for(int index = 0; index < colIndex ; index++){
                                                if(index != column){
                                                    data1 = tableValues[i][index];
                                                    data2 = tableValues[j][index];

                                                    tableValues[i][index] = data2;
                                                    tableValues[j][index] = data1;

                                                    System.out.println(data1);
                                                    System.out.println(data2);
                                                }
                                            }
                                        }
                                    }
                                    else if(isInteger(data1) && !isInteger(data2)){ //第一个数字，第二个字符串
                                        double num1 = Double.valueOf(data1);
                                        System.out.println(data1 + " " + num1);
                                        tableValues[i][column] = data2;
                                        tableValues[j][column] = replace(String.valueOf(num1));

                                        flag = true;
                                        for(int index = 0; index < colIndex ; index++){
                                            if(index != column){
                                                data1 = tableValues[i][index];
                                                data2 = tableValues[j][index];

                                                tableValues[i][index] = data2;
                                                tableValues[j][index] = data1;

                                            }
                                        }
                                    }
                                    else if(!isInteger(data1) && !isInteger(data2)){  //两个都不是数字，按照字符串来比较
                                        System.out.println(data1 + " " + data2);
                                        System.out.println(data1.compareTo(data2));
                                        flag = true;
                                        if(data1.compareTo(data2) < 0){
                                            tableValues[i][column] = data2;
                                            tableValues[j][column] = data1;

                                            System.out.println(colIndex);
                                            for(int index = 0; index < colIndex ; index++){
                                                if(index != column){
                                                    data1 = tableValues[i][index];
                                                    data2 = tableValues[j][index];

                                                    tableValues[i][index] = data2;
                                                    tableValues[j][index] = data1;


                                                }
                                            }
                                        }
                                    }
                                }
                                else if((data1 == null || data1.length() == 0) && (data2 != null && data2.length() != 0)){  //空格排在最后面

                                    tableValues[i][column] = data2;
                                    tableValues[j][column] = data1;
                                    flag = true;
                                    for(int index = 0; index < colIndex ; index++){
                                        if(index != column){
                                            data1 = tableValues[i][index];
                                            data2 = tableValues[j][index];

                                            tableValues[i][index] = data2;
                                            tableValues[j][index] = data1;

                                        }
                                    }
                                }
                            }
                            if(!flag) break; //如果当前已经是正确的顺序，直接跳出循环
                        }
                    }

                    //更新界面
                    Fresh();

                }


            });

            jPanel.add(jButton[i]);
        }


        for(int i = 0; i < size; i++){
            int row = i / (colIndex + 1);
            int col = i % (colIndex + 1);
            System.out.println(row + " " + col);
            MyTextField myTextField;
            if(col == 0){
                myTextField = new MyTextField(row,col);
                myTextField.setValue(String.valueOf(row + 1));
                myTextField.getjTextField().setSelectedTextColor(Color.RED);
                myTextField.getjTextField().enable(false);
            }else{

                arrays[row][col - 1] = new MyTextField(row, col);
                myTextField = arrays[row][col - 1];

                //改变选中的颜色
                myTextField.getjTextField().setSelectedTextColor(Color.RED);

                //改变点击时的颜色
                myTextField.getjTextField().setCaretColor(Color.RED);

                //改变字体颜色
               // myTextField.getjTextField().setForeground(Color.red);
                if(row == 2 && col == 3){
                    myTextField.getjTextField().setForeground(Color.red);
                }
                char c = (char)('A' + col - 1);
                if(!" ".equals(tableValues[row][col - 1]) && tableValues[row][col - 1] != null && tableValues[row][col - 1].length() >= 1){
                    myTextField.setValue(tableValues[row][col - 1] + " "  + String.valueOf(c));
                }
                else{
                    myTextField.setValue(tableValues[row][col - 1]);
                }
                myTextField.getjTextField().getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {

                        tableValues[row][col - 1] = myTextField.getjTextField().getText();

                        if(tableValues[row][col - 1].length() != 0 && !" ".equals(tableValues[row][col - 1])){
                            for(int i = 0; i < col - 1; i++){
                                if(tableValues[row][i] == null){
                                    tableValues[row][i] = "";
                                }
                            }
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {

                        tableValues[row][col - 1] = myTextField.getjTextField().getText();
                        if(tableValues[row][col - 1].length() != 0 && !" ".equals(tableValues[row][col - 1])){
                            for(int i = 0; i < col - 1; i++){
                                if(tableValues[row][i] == null){
                                    tableValues[row][i] = "";
                                }
                            }
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent documentEvent) {

                        tableValues[row][col - 1] = myTextField.getjTextField().getText();
                        if(tableValues[row][col - 1].length() != 0 && !" ".equals(tableValues[row][col - 1])){
                            for(int i = 0; i < col - 1; i++){
                                if(tableValues[row][i] == null){
                                    tableValues[row][i] = "";
                                }
                            }
                        }
                    }
                });


                myTextField.getjTextField().addMouseListener(new MouseAdapter() {     //监听鼠标右键
                    public void mouseClicked(MouseEvent evt) {
                        mouseRightButtonClick(evt, row, col - 1);  //弹出菜单栏
                    }
                });


            }


            jPanel.add(myTextField.getjTextField());
        }


        getContentPane().add(jPanel, BorderLayout.CENTER); //将ToolBar加入面板
        initToolBar();
    }


    //初始化ToolBar
    public void initToolBar(){
        final JToolBar toolBar = new JToolBar("工具栏");// 创建工具栏对象
        toolBar.setFloatable(false);// 设置为不允许拖动

        String path = new File("").getAbsolutePath() + "/src/image/" + "save" + ".png"; //获取图片路径
        ImageIcon icon = new ImageIcon(path);

        final JButton saveButton = new JButton(icon);
        saveButton.setBounds(0,0, 20, 20);
        Image temp = icon.getImage().getScaledInstance(saveButton.getWidth(), saveButton.getHeight(), icon.getImage().SCALE_DEFAULT);
        icon = new ImageIcon(temp);

        saveButton.setIcon(icon);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    writeObjectToFile(tableValues);//写文件
                    JOptionPane.showMessageDialog(getContentPane(), "保存成功", "Excel",JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        toolBar.add(saveButton);
        toolBar.addSeparator();// 添加默认大小的分隔符

        getContentPane().add(toolBar, BorderLayout.NORTH); //将ToolBar加入面板
    }


    //刷新界面
    public void Fresh(){
        for(int i = 0; i < rowIndex; i++ ){
            for(int j = 0; j < colIndex ; j++){
                System.out.println(tableValues[i][j]);
                arrays[i][j].getjTextField().setText(tableValues[i][j]);
            }
        }
    }



    //数字去多余0
    public static String replace(String s){
        if(null != s && s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }



    //判断是否是数字
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }

    //鼠标右键
    private void mouseRightButtonClick(MouseEvent evt, int row, int col) {

        if (evt.getButton() == MouseEvent.BUTTON3) {
            //获得鼠标位置

            if (row == -1 || col == -1) {
                return;
            }


            //弹出菜单
            if(col != -1){
                new MyPopupMenu().show(getContentPane(), evt.getXOnScreen() , evt.getYOnScreen(), row, col, rowIndex, colIndex, tableValues, arrays);
            }



        }


    }


    //写文件
    public static void writeObjectToFile(Object obj) throws IOException {
        String filePath2 = new File("").getAbsolutePath() + "/src/file/" + "data" + ".dat";
        File file = new File(filePath2);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    //读文件
    public static Object readObjectFromFile() throws IOException {
        Object temp=null;
        String filePath2 = new File("").getAbsolutePath() + "/src/file/" + "data" + ".dat";
        File file = new File(filePath2);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
