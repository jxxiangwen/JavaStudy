package cn.edu.shu.second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jxxiangwen on 2015/10/9 0009.
 * @version 1.0
 * @author jxxiangwen
 */
public class ImageView {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new ImageViewFrame();
                frame.setTitle("ImageView");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

//显示图片标签
class ImageViewFrame extends JFrame{
    private JLabel label;
    private JFileChooser chooser;
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 400;

    public ImageViewFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        //使用标签展示图片
        label = new JLabel();
        add(label);

        //文件选择
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        //设置菜单
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //显示文件选择框
                int result = chooser.showOpenDialog(null);

                //如果文件选择，设置为标签图标
                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(filePath));

                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}