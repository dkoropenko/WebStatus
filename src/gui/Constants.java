package gui;

import javax.swing.*;

/**
 * Created by Koropenkods on 21.03.16.
 */
public interface Constants {
    //GUI
    ImageIcon ICON = new ImageIcon("src/gui/icons/icon.png");

    //DataBase
    String DRIVER = "org.sqlite.JDBC";
    String URL = "jdbc:sqlite:ipDB.db";
}
