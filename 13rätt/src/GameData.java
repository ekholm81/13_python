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
    // 0 no change,  1 increase, 2 decrease
    public int[][] oddsCh;
    public int[][] crossedCh;
    public int[][] valueCh;

    public double[][] beastData;
    public Triplet[] rowVal;
    public int[][] tecken;
    public int[][] dtecken;
    public boolean[] modOdds;
    public double[] oddsC;
    public double expval;
    public double sannolikhet12;
    public double sannolikhet13;
    public double sannolikhet11;
    public double sannolikhet10;
    public double edge;
    public double utdelning;
    public String spelstopp;
    public String omsättning;
    public int numMatch;
    public int[] utdarray;
    public double[] utdchans;
    //spikarray, 1:hemmalalg, 2, kryss, 3, bortalag!!!!!!!
    public int[] spikar;
    public int[] dodgers;
    public int[] rowValArray;
    public GameData(int numMatches){

        numMatch=numMatches;
        int numRows;
        if (numMatches==13)numRows=1594323;
        else numRows=6561;
        oddsC=new double[numMatches];
        spikar=new int[numMatches];
        dodgers=new int[numMatches];
        rowValArray=new int[20];
        beastData=new double[numMatches][3];
        rowVal =new Triplet[numRows];
        games=new String[numMatches];
        odds=new double[numMatches][3];
        crossed=new double[numMatches][3];
        wodds=new double[numMatches][3];
        modOdds=new boolean[numMatches];
        value=new double[numMatches][3];
        wvalue=new double[numMatches][3];
        rad=new boolean[numMatches][3];
        tecken=new int[numMatches][3];
        dtecken=new int[numMatches][3];
        oddsCh=new int[numMatches][3];
        crossedCh=new int[numMatches][3];
        valueCh=new int[numMatches][3];
        spelstopp=new String("");
        omsättning=new String("");
        sannolikhet13 =0;
        sannolikhet12 =0;
        expval =0;
        sannolikhet11 =0;
        sannolikhet10 =0;
        edge=-1;
    }

}
