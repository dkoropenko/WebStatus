package DB;

import gui.Constants;
import hosts.SimpleHost;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;

/**
 * Created by Koropenkods on 21.03.16.
 */
public class Database {

    Connection connect = null;
    Statement sql = null;

    public Database() throws SQLException {
        initDriver();
        connection();
        createTables();

    }

    private void initDriver(){
        try {
            Class.forName(Constants.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void connection() throws SQLException {
        connect = DriverManager.getConnection(Constants.URL);
        sql = connect.createStatement();
    }
    private void createTables() throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("CREATE TABLE IF NOT EXISTS hosts (ID INTEGER PRIMARY KEY AUTOINCREMENT, ip CHAR(15), " +
                "port INTEGER, type CHAR(3), comment CHAR(30), root CHAR(10));");

        sql.execute(result.toString());
    }

    public void addData(String ip, int port, String type, String comment, String root) throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("INSERT INTO hosts (ip, port, type, comment, root) VALUES (");
        result.append("\""+ ip +"\", ");
        result.append(""+ port +", ");
        result.append("\""+ type +"\", ");
        result.append("\""+ comment +"\", ");
        result.append("\""+ root +"\");");

        sql.execute(result.toString());
    }
    public void rmData(String ip, String root) throws SQLException {
        sql.execute("DELETE FROM hosts WHERE ip = \""+ ip +"\" AND root = \""+ root +"\";");
    }
    public void updateData (String value, String dest, String ip ) throws SQLException{
        sql.execute("UPDATE hosts SET "+ dest +" = \""+ value +"\" where ip = \""+ ip +"\";");
    }
    public void updateData (int value, String dest, String ip ) throws SQLException{
        sql.execute("UPDATE hosts SET "+ dest +" = \""+ value +"\" where ip = \""+ ip +"\";");
    }
    public LinkedList<SimpleHost> getData(String root) throws SQLException, UnknownHostException {
        LinkedList<SimpleHost> result = new LinkedList();

        ResultSet rset = sql.executeQuery("SELECT ip, port, type, comment FROM hosts WHERE root = \""+ root +"\";");


        ArrayList<String> dataIp = new ArrayList();
        ArrayList<Integer> dataPort = new ArrayList();
        ArrayList<String> dataType = new ArrayList();
        ArrayList<String> dataComment = new ArrayList();

        while (rset.next()){
            dataIp.add(rset.getString(1));
            dataPort.add(rset.getInt(2));
            dataType.add(rset.getString(3));
            dataComment.add(rset.getString(4));
        }

        for (int i = 0; i < dataIp.size(); i++) {
            result.add(new SimpleHost(dataIp.get(i),dataPort.get(i),dataType.get(i),dataComment.get(i)));
        }

        return result;
    }
    public LinkedHashSet<String> getRoots() throws SQLException {
        LinkedHashSet<String> result = new LinkedHashSet<>();
        ResultSet org = sql.executeQuery("SELECT root FROM hosts;");
        while (org.next())
            result.add(org.getString(1));
        return result;
    }
    public void clearData() throws SQLException{
        sql.execute("DELETE FROM hosts;");
    }


    public static void main(String[] args) {
        try {
            Database test = new Database();


            Iterator iter = test.getRoots().iterator();
            String root;
            Thread thread;

            ArrayList<String> result = new ArrayList<>();

            while (iter.hasNext()){
                root = iter.next().toString();
                LinkedList<SimpleHost> out = test.getData(root);
                for (int i = 0; i < out.size(); i++) {
                    thread = new Thread(out.get(i));
                    thread.start();

                    while (thread.isAlive()){
                        System.out.println("А мы ждем данные");
                        Thread.sleep(1000);
                    }

                    result.add(out.get(i).toString() +" . "+ root);
                }
            }

            Iterator outResult = result.iterator();

            while (outResult.hasNext()){
                System.out.println(outResult.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
