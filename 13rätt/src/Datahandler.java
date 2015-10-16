import java.io.PrintWriter;
import java.util.ArrayList;
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

    public void calcRows(GameData gd, double slider){
        for(int i=0;i<gd.games.length;i++){
            for(int j=0;j<3;j++){
                gd.wvalue[i][j]=(gd.crossed[i][j]*(gd.wodds[i][j]-(slider))/100.00);
            }
        }
        if(gd.numMatch==13){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) for(int i=0;i<3;i++) for(int j=0;j<3;j++) for(int k=0;k<3;k++) for(int l=0;l<3;l++) for(int m=0;m<3;m++){
                gd.rowVal[counter]=new Triplet();
                double intoms=Double.parseDouble(gd.omsättning.replaceAll(" ",""));
                double intutd=intoms*gd.utdelning;
                double chans=1.00/gd.wodds[0][a]*1.00/gd.wodds[0][b]*1.00/gd.wodds[0][c]*1.00/gd.wodds[0][d]*1.00/gd.wodds[0][e]*1.00/gd.wodds[0][f]*1.00/gd.wodds[0][g]*1.00/gd.wodds[0][h]*1.00/gd.wodds[0][i]*1.00/gd.wodds[0][j]*1.00/gd.wodds[0][k]*1.00/gd.wodds[0][l]*1.00/gd.wodds[0][m];
                double cross=gd.utdelning/(gd.crossed[0][a]/100.00*gd.crossed[0][b]/100.00*gd.crossed[0][c]/100.00*gd.crossed[0][d]/100.00*gd.crossed[0][e]/100.00*gd.crossed[0][f]/100.00*gd.crossed[0][g]/100.00*gd.crossed[0][h]/100.00*gd.crossed[0][i]/100.00*gd.crossed[0][j]/100.00*gd.crossed[0][k]/100.00*gd.crossed[0][l]/100.00*gd.crossed[0][m]/100.00);
               // if (cross>intutd) System.out.println(a+" "+b+" "+c+" "+d+" "+e+" "+f+" "+g+" "+h+" "+i+" "+j+" "+k+" "+l+" "+m);
                gd.rowVal[counter].append(counter, gd.wvalue[0][a] * gd.wvalue[1][b]*gd.wvalue[2][c]*gd.wvalue[3][d]*gd.wvalue[4][e]*gd.wvalue[5][f]*gd.wvalue[6][g]*gd.wvalue[7][h]*gd.wvalue[8][i]*gd.wvalue[9][j]*gd.wvalue[10][k]*gd.wvalue[11][l]*gd.wvalue[12][m],cross,chans);
                counter++;
            }
            Arrays.sort(gd.rowVal);
        }
        if(gd.numMatch==8){
            int counter=0;
            for(int a=0;a<3;a++) for(int b=0;b<3;b++) for(int c=0;c<3;c++) for(int d=0;d<3;d++) for(int e=0;e<3;e++) for(int f=0;f<3;f++) for(int g=0;g<3;g++) for(int h=0;h<3;h++) {
                gd.rowVal[counter]=new Triplet();
                double intoms=Double.parseDouble(gd.omsättning.replaceAll(" ",""));
                double intutd=intoms*gd.utdelning;
                double chans=1.00/gd.wodds[0][a]*1.00/gd.wodds[1][b]*1.00/gd.wodds[2][c]*1.00/gd.wodds[3][d]*1.00/gd.wodds[4][e]*1.00/gd.wodds[5][f]*1.00/gd.wodds[6][g]*1.00/gd.wodds[7][h];
                double cross=gd.utdelning/(gd.crossed[0][a]/100.00*gd.crossed[1][b]/100.00*gd.crossed[2][c]/100.00*gd.crossed[3][d]/100.00*gd.crossed[4][e]/100.00*gd.crossed[5][f]/100.00*gd.crossed[6][g]/100.00*gd.crossed[7][h]/100.00);
               // if (cross>intutd) System.out.println(a+" "+b+" "+c+" "+d+" "+e+" "+f+" "+g+" "+h);
                gd.rowVal[counter].append(counter, gd.wvalue[0][a] * gd.wvalue[1][b] * gd.wvalue[2][c] * gd.wvalue[3][d] * gd.wvalue[4][e] * gd.wvalue[5][f] * gd.wvalue[6][g] * gd.wvalue[7][h],cross,chans);
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
            gd.utdarray=new int[50000];
            gd.utdchans=new double[50000];
            gd.rowValArray=new int[20];
            int index=0;
            StringBuilder SB;
            for(int i=0;i<antalrader;i++){
                while(true) {
                    boolean valid=true;
                    SB = new StringBuilder();
                    int num = gd.rowVal[i+index].key();
                    int r = -1;
                    while (true) {
                        r = num % 3;
                        SB.append(r);
                        num = num / 3;
                        if (num == 0) break;
                    }
                    for (int j = 0; j < gd.wvalue.length; j++) {
                        int p = (int) Math.pow(3, j);
                        if (gd.rowVal[i+index].key() < p) SB.append(0);
                    }
                    SB.reverse();
                    for (int j = 0; j < gd.wvalue.length; j++) {
                        if (gd.spikar[j] != 0) {
                            if ((gd.spikar[j] == 1 && SB.charAt(j) != '0')||(gd.spikar[j] == 2 && SB.charAt(j) != '1')||(gd.spikar[j] == 3 && SB.charAt(j) != '2')) {
                                index++;
                                valid=false;
                                break;
                            }
                        }
                        if(gd.dodgers[j]!=0){
                            if(gd.dodgers[j]==1 && SB.charAt(j)=='0'|| gd.dodgers[j]==2 && SB.charAt(j)=='1'|| gd.dodgers[j]==3 && SB.charAt(j)=='2'){
                                index++;
                                valid=false;
                                break;
                            }
                        }
                    }
                    if(valid)break;
                    else if(gd.wvalue.length!=13){
                        for(int j=0;j<gd.wvalue.length;j++){
                            char c=SB.charAt(j);
                            if(c=='0')iwriter.print('0');
                            if(c=='1')iwriter.print('1');
                            if(c=='2')iwriter.print('2');
                        } iwriter.print("\n");
                    }
                }
                int a=(int)(10.00/(get_val(gd,SB.toString())*gd.utdelning)+0.5);
                if(a>19)a=19;
                //System.out.println(a+"   :   "+10.00/get_val(gd,SB.toString()));
                gd.rowValArray[a]++;
                double rowchance=100;
                if(gd.wodds.length==8){
                    gd.utdarray[(int)(gd.rowVal[i+index].cross()/750.00)]++;
                    gd.utdchans[(int)(gd.rowVal[i+index].cross()/750.00)]+=gd.rowVal[i+index].chance()*100.00;
                }
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
            if(gd.wvalue.length!=13) {
                for (int i = antalrader; i < Math.pow(3, gd.wvalue.length); i++) {
                    SB = new StringBuilder();
                    int num = gd.rowVal[i].key();
                    int r = -1;
                    while (true) {
                        r = num % 3;
                        SB.append(r);
                        num = num / 3;
                        if (num == 0) break;
                    }
                    for (int j = 0; j < gd.wvalue.length; j++) {
                        int p = (int) Math.pow(3, j);
                        if (gd.rowVal[i].key() < p) SB.append(0);
                    }
                    SB.reverse();
                    for (int j = 0; j < gd.wvalue.length; j++) {
                        char c = SB.charAt(j);
                        if (c == '0') iwriter.print('0');
                        if (c == '1') iwriter.print('1');
                        if (c == '2') iwriter.print('2');
                    }

                    if (i + 1 != Math.pow(3, gd.wvalue.length)) iwriter.print("\n");
                }
            }
            gd.expval =utills.round(rowVal*gd.utdelning, 2);
            gd.sannolikhet13=  utills.round(chance,2);
            gd.sannolikhet12=utills.round(gd.sannolikhet13+getC(1,chance,gd.games.length),2);
            gd.sannolikhet11=utills.round(gd.sannolikhet12+getC(2,chance,gd.games.length),2);
            gd.sannolikhet10 = utills.round(gd.sannolikhet11 + getC(3, chance, gd.games.length),2);
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



    public void beast(String antalRaderS, GameData gd, double slider){
        int antalRader=Integer.parseInt(antalRaderS);
        calcRows(gd, slider);
        setTecken(gd,antalRader);
    }

    public double[] getRowstats(int[] n, GameData gd){
        double[] res={gd.utdelning,100};
        boolean zeroodds=false;
        for (int i=0; i<n.length;i++){
            res[0]=res[0]/(gd.crossed[i][n[i]]/100.00);
            if(gd.wodds[i][n[i]]==0) zeroodds=true;
            res[1]=res[1]*(1.00/gd.wodds[i][n[i]]);
        }
        if(zeroodds)res[1]=0;
        return res;
    }

    public boolean sign(int a,int b){
        if(a==0)return (b==6||b==4||b==3||b==0);
        if(a==1)return (b==6||b==5||b==3||b==1);
        if(a==2)return (b==6||b==5||b==4||b==2);
        System.out.println("Error in sign function");
        return false;
    }

    public boolean s_in_m(int[] s, int[] m){
        for (int i=0;i<s.length;i++){
            if(!(sign(s[i],m[i])))return false;
        }
        return true;
    }

    public double[] getMRowstats(int[] m, GameData gd){
        ArrayList<int[]> al=new ArrayList<int[]>();
        if(m.length==8) {
            for (int a = 0; a < 3; a++)
                for (int b = 0; b < 3; b++)
                    for (int c = 0; c < 3; c++)
                        for (int d = 0; d < 3; d++)
                            for (int e = 0; e < 3; e++)
                                for (int f = 0; f < 3; f++)
                                    for (int g = 0; g < 3; g++)
                                        for (int h = 0; h < 3; h++) {
                                            if (s_in_m(new int[]{a, b, c, d, e, f, g, h}, m))
                                                al.add(new int[]{a, b, c, d, e, f, g, h});
                                        }
        }
        else {
            for (int a = 0; a < 3; a++)
                for (int b = 0; b < 3; b++)
                    for (int c = 0; c < 3; c++)
                        for (int d = 0; d < 3; d++)
                            for (int e = 0; e < 3; e++)
                                for (int f = 0; f < 3; f++)
                                    for (int g = 0; g < 3; g++)
                                        for (int h = 0; h < 3; h++)
                                            for (int i = 0; i < 3; i++)
                                                for (int j = 0; j < 3; j++)
                                                    for (int k = 0; k < 3; k++)
                                                        for (int l = 0; l < 3; l++)
                                                            for (int mm = 0; mm < 3; mm++)
                                                            {
                                                                  if (s_in_m(new int[]{a, b, c, d, e, f, g, h,i,j,k,l,mm}, m))
                                                                  al.add(new int[]{a, b, c, d, e, f, g, h,i,j,k,l,mm});
                                        }
        }
        double[] res={0,0,0};
        for (int i=0;i<al.size();i++){
            double utd=gd.utdelning;
            double chance=100.00;
            for(int j=0;j<m.length;j++){
                utd=utd/(gd.crossed[j][al.get(i)[j]]/100.00);
                chance=chance*(1.00/gd.wodds[j][al.get(i)[j]]);
            }
            res[0]+=utd*chance/100.00;
            res[1]+=chance;

        }
        res[2]=al.size();
        return res;
    }

    public double getC(int fel,double a,int n){
        a=Math.pow(a/100.00,1.00/13.00);
        int b=0;
        if (n==8)b=binom8[fel-1];
        if (n==13)b=binom13[fel-1];
        return 100*Math.pow(a,n-fel)*Math.pow(1.00-a,fel)*b;
    }

}
