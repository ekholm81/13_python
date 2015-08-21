import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by mq on 2014-12-20.
 */
public class Datahandler {

    int[] binom13={13,78,286};
    int[] binom8={8,28,56};

    Utills utills;
    public Datahandler(){
        utills=new Utills();
    }

    public void calcRows(GameData gd, double slider,boolean inv){
        for(int i=0;i<gd.games.length;i++){
            for(int j=0;j<3;j++){
               // gd.value[i][j]=gd.crossed[i][j]*(gd.wodds[i][j]);
                gd.wvalue[i][j]=(gd.crossed[i][j]*(gd.wodds[i][j]-(slider))/100.00);
            }
        }
        if(gd.numMatch==13){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) for(int i=0;i<3;i++) for(int j=0;j<3;j++) for(int k=0;k<3;k++) for(int l=0;l<3;l++) for(int m=0;m<3;m++){
                gd.rowVal[counter]=new MyPair();
                if(inv)gd.rowVal[counter].append(counter,-1* gd.wvalue[0][a]*gd.wvalue[1][b]*gd.wvalue[2][c]*gd.wvalue[3][d]*gd.wvalue[4][e]*gd.wvalue[5][f]*gd.wvalue[6][g]*gd.wvalue[7][h]*gd.wvalue[8][i]*gd.wvalue[9][j]*gd.wvalue[10][k]*gd.wvalue[11][l]*gd.wvalue[12][m]);
                else gd.rowVal[counter].append(counter, gd.wvalue[0][a] * gd.wvalue[1][b]*gd.wvalue[2][c]*gd.wvalue[3][d]*gd.wvalue[4][e]*gd.wvalue[5][f]*gd.wvalue[6][g]*gd.wvalue[7][h]*gd.wvalue[8][i]*gd.wvalue[9][j]*gd.wvalue[10][k]*gd.wvalue[11][l]*gd.wvalue[12][m]);
                counter++;
            }
            Arrays.sort(gd.rowVal);
        }
        if(gd.numMatch==8){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) {
                gd.rowVal[counter]=new MyPair();
                gd.rowVal[counter].append(counter, gd.wvalue[0][a] * gd.wvalue[1][b] * gd.wvalue[2][c] * gd.wvalue[3][d] * gd.wvalue[4][e] * gd.wvalue[5][f] * gd.wvalue[6][g] * gd.wvalue[7][h]);
                counter++;

            }
            Arrays.sort(gd.rowVal);
        }

    }

    private double get_val(GameData gd,String s){
        double ch=1.0;
        double numrows=1.0;
        for(int j=0;j<gd.wvalue.length;j++){
            int sign=s.charAt(j)-48;
            ch=ch*(1.00/gd.wodds[j][sign]);
            numrows=(numrows*gd.crossed[j][sign])/100;
        }
       // if (numrows<1.00/1000000)numrows=1.00/1000000;
        return (1.00/numrows)*ch;
    }

   private void setTecken(GameData gd, int antalrader){

        gd.tecken=new int[gd.wvalue.length][3];
        double chance=0;
        double rowVal=0;
        try{

            PrintWriter writer = new PrintWriter("o.txt", "UTF-8");
            PrintWriter iwriter = new PrintWriter("i.txt", "UTF-8");
           // writer.print("Stryktipset");
           // writer.print("\r\n");
            for(int i=0;i<antalrader;i++){
                double rowchance=100;

                StringBuilder SB=new StringBuilder();
                int num = gd.rowVal[i].key();
                int r = -1;
                while (true){
                    r=num %3;
                    SB.append(r);
                    num=num/3;
                    if(num==0)break;
                }
                for(int j=0;j<gd.wvalue.length;j++){
                    int p = (int)Math.pow(3, j);
                    if(gd.rowVal[i].key()<p)SB.append(0);
                }
                SB.reverse();

                writer.print("E,");
                for(int j=0;j<gd.wvalue.length;j++){
                    char c=SB.charAt(j);
                    if(c=='0')writer.print('1');
                    if(c=='1')writer.print('X');
                    if(c=='2')writer.print('2');
                    if(!(j+1==gd.value.length))writer.print(",");
                }
                writer.print("\r\n");
              //  if(i+1!=antalrader)writer.print("\n");

                rowVal+=get_val(gd,SB.toString());

                for(int j=0;j<gd.wvalue.length;j++){
                    gd.tecken[j][Character.getNumericValue(SB.charAt(j))]++;
                    rowchance=rowchance*1.00/gd.wodds[j][Character.getNumericValue(SB.charAt(j))];

                }

                chance+=rowchance;
            }
            for(int i=antalrader;i<Math.pow(3,gd.wvalue.length);i++){
                StringBuilder SB=new StringBuilder();
                int num = gd.rowVal[i].key();
                int r = -1;
                while (true){
                    r=num %3;
                    SB.append(r);
                    num=num/3;
                    if(num==0)break;
                }
                for(int j=0;j<gd.wvalue.length;j++){
                    int p = (int)Math.pow(3, j);
                    if(gd.rowVal[i].key()<p)SB.append(0);
                }
                SB.reverse();
                for(int j=0;j<gd.wvalue.length;j++){
                    char c=SB.charAt(j);
                    if(c=='0')iwriter.print('0');
                    if(c=='1')iwriter.print('1');
                    if(c=='2')iwriter.print('2');
                }

                if (i+1!=Math.pow(3,gd.wvalue.length))iwriter.print("\n");
            }
            gd.expval =utills.round(rowVal*gd.utdelning, 2);
            gd.sannolikhet13=  utills.round(chance,2);
            gd.sannolikhet12=utills.round(gd.sannolikhet13+getC(1,chance,gd.games.length),2);
            gd.sannolikhet11=utills.round(gd.sannolikhet12+getC(2,chance,gd.games.length),2);
            gd.sannolikhet10=utills.round(gd.sannolikhet11+getC(3,chance,gd.games.length),2);
            for(int i=0;i<gd.wvalue.length;i++){
                for(int j=0;j<3;j++){
                    gd.dtecken[i][j]=(int)utills.round((double)(gd.tecken[i][j])*100/(double)antalrader,0);
                }
            }
            writer.close();
            iwriter.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }



    public void beast(String antalRaderS, GameData gd, double slider,boolean inv){
        int antalRader=Integer.parseInt(antalRaderS);
        calcRows(gd, slider,inv);
        setTecken(gd,antalRader);
    }

    public double getC(int fel,double a,int n){
        a=Math.pow(a/100.00,1.00/13.00);
        int b=0;
        if (n==8)b=binom8[fel-1];
        if (n==13)b=binom13[fel-1];
        return 100*Math.pow(a,n-fel)*Math.pow(1.00-a,fel)*b;
    }

}
