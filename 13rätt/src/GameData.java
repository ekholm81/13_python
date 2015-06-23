/**
 * Created by mq on 2014-12-20.
 */
public class GameData {
    public String[] games;
    public double[][] odds;
    public double[][] wodds;
    public double[][] crossed;
    public double[][] value;
    public double[][] wvalue;
    public boolean[][] rad;
    public double[][] beastData;
    public MyPair[] rowVal;
    public int[][] tecken;
    public int[][] dtecken;
    public double sannolikhet13;
    public double sannolikhet12;
    public double sannolikhet11;
    public double sannolikhet10;
    public double edge;
    public double utdelning;
    public String spelstopp;
    public String omsättning;
    public int numMatch;
    public GameData(int numMatches){
        numMatch=numMatches;
        int numRows;
        if (numMatches==13)numRows=1594323;
        else numRows=6561;
        beastData=new double[numMatches][3];
        rowVal =new MyPair[numRows];
        games=new String[numMatches];
        odds=new double[numMatches][3];
        crossed=new double[numMatches][3];
        wodds=new double[numMatches][3];
        value=new double[numMatches][3];
        wvalue=new double[numMatches][3];
        rad=new boolean[numMatches][3];
        tecken=new int[numMatches][3];
        dtecken=new int[numMatches][3];
        spelstopp=new String("");
        omsättning=new String("");
        sannolikhet13 =-1;
        sannolikhet12 =-1;
        sannolikhet11 =-1;
        sannolikhet10 =-1;
        edge=-1;
    }

}
