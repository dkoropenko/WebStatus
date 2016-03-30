package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Koropenkods on 21.03.16.
 */
public class MainWindow extends JFrame {

    JTree mainContainer;

    public MainWindow(){
        setTitle("LanTester");
        setSize(300,400);
        setIconImage(Constants.ICON.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initData();

        setContentPane(mainContainer);
    }

    private void initData(){
        DefaultMutableTreeNode organisation = new DefaultMutableTreeNode("DCK");
        DefaultMutableTreeNode ip1 = new DefaultMutableTreeNode("192.168.5.121");
        DefaultMutableTreeNode ip2 = new DefaultMutableTreeNode("192.168.5.122");
        DefaultMutableTreeNode ip3 = new DefaultMutableTreeNode("192.168.5.123");

        organisation.add(ip1);
        organisation.add(ip2);
        organisation.add(ip3);

        mainContainer = new JTree(organisation);

    }
}
