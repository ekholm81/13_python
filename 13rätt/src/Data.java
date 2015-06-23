import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Created by mq on 2014-12-20.
 */
public class Data {
    GameData Stryk;
    GameData EU;
    GameData PP;
    Utills utills;
    public Data(){
        utills=new Utills();
    }

    public void collectStrykData(){
        genHtml(gameTypes.STRYKTIPSET);
    }

    public void collectEUData(){
        genHtml(gameTypes.EUROPATIPSET);

    }

    private String cleanString(String s){
        StringBuilder SB=new StringBuilder();
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)=='}' || s.charAt(i)==',')continue;
            else {
                SB.append(s.charAt(i));
            }
        }return SB.toString();
    }

    public void collectPPData(){
        genHtml(gameTypes.POWERPLAY);
    }

    private void genHtml(gameTypes type){
        String gameType;
        if(type==gameTypes.STRYKTIPSET){gameType="stryktipset";}
        else if(type==gameTypes.EUROPATIPSET)gameType="europatipset";
        else if(type==gameTypes.POWERPLAY)gameType="topptipset";
        else gameType="";
        try{
            Process pp = Runtime.getRuntime().exec("rm "+gameType);
            pp.waitFor();
            Process p = Runtime.getRuntime().exec("wget https://www.svenskaspel.se/"+gameType+"/spela -O "+gameType);
            p.waitFor();
        }
        catch (Exception e){e.printStackTrace();}
        try{
            int n=0;
            if(type==gameTypes.EUROPATIPSET || type==gameTypes.STRYKTIPSET)n=13;
            if (type==gameTypes.POWERPLAY)n=8;
            BufferedReader br = new BufferedReader(new FileReader(gameType));
            String line = null;
            GameData gd=new GameData(n);
            if(type==gameTypes.STRYKTIPSET)gd.utdelning=0.65;
            if(type==gameTypes.EUROPATIPSET)gd.utdelning=0.65;
            if(type==gameTypes.POWERPLAY)gd.utdelning=0.7;
            int i=0;
            while ((line = br.readLine()) != null)
            {
                i++;
                if(i==266)break;
            }
            int c=0;
            for (i = -1; (i = line.indexOf("eventDescription\":\"", i + 1)) != -1; ) {
                c++;
                if(c>n)break;
                String g=(line.substring(i+19,i+60));
                for (int j=0;j<100;j++){
                    if(g.charAt(j)=='\"'){
                        gd.games[c-1]=g.substring(0,j);
                        break;
                    }
                }
            }
             c=0;
            System.out.println("\"odds\":");
            for (i = -1; (i = line.indexOf("\"odds\":", i + 1)) != -1; ) {

                c++;
                int inc=0;
                if(c>n)break;
               /* if(c==7){
                    gd.odds[c-1][0]=1.66;
                    gd.odds[c-1][1]=4.00;
                    gd.odds[c-1][2]=4.75;
                    gd.wodds[c-1]=utills.getwodds(gd.odds[c-1].clone());
                    continue;
                }if(c==8){
                    gd.odds[c-1][0]=4.75;
                    gd.odds[c-1][1]=3.60;
                    gd.odds[c-1][2]=1.72;
                    gd.wodds[c-1]=utills.getwodds(gd.odds[c-1].clone());
                    continue;
                }*/
                if (line.charAt(i+7)=='n'){
                    gd.odds[c-1][0]=0;
                    gd.odds[c-1][1]=0;
                    gd.odds[c-1][2]=0;
                    gd.wodds[c-1]=utills.getwodds(gd.odds[c-1].clone());

                    continue;
                }
                String o1=(line.substring(i+15,i+19)).replaceAll(",",".");
                gd.odds[c-1][0]=Double.parseDouble(o1);
                if(gd.odds[c-1][0]>10.00)inc++;
                String o2=(line.substring(i+26+inc,i+30+inc)).replaceAll(",",".");
                gd.odds[c-1][1]=Double.parseDouble(o2);
                if(gd.odds[c-1][1]>10.00)inc++;
                String o3=(line.substring(i+39+inc,i+43+inc)).replaceAll(",",".");
                gd.odds[c-1][2]=Double.parseDouble(o3);
                gd.wodds[c-1]=utills.getwodds(gd.odds[c-1].clone());

            }
             c=0;
            for (i = -1; (i = line.indexOf("svenskaFolket\":{\"one\":", i + 1)) != -1; ) {
                c++;
                if(c>n)break;
                int inc=0;
                String o1=cleanString(line.substring(i+22,i+24));
                System.out.println(("o1: "+Double.parseDouble(o1)));
                gd.crossed[c-1][0]=Double.parseDouble(o1);
                if(gd.crossed[c-1][0]<10.00)inc--;
                String o2= cleanString(line.substring(i + 29 + inc,i+31+inc));
                System.out.println(("o2: "+Double.parseDouble(o2)));

                gd.crossed[c-1][1]=Double.parseDouble(o2);
                if(gd.crossed[c-1][1]<10.00)inc--;
                String o3=cleanString(line.substring(i+38+inc,i+40+inc));
                System.out.println(("o3: "+Double.parseDouble(o3)));

                gd.crossed[c-1][2]=Double.parseDouble(o3);
                for(int ii=0;ii<3;ii++)System.out.print(ii+" : "+gd.crossed[c - 1][ii] + " ");
                System.out.println("");

            }

            i=line.indexOf("\"currentNetSale\":\"");
            StringBuilder OSB=new StringBuilder();
            for(int j=18;j<30;j++){
                if(line.charAt(i+j)=='\"')break;
                OSB.append(line.charAt(i + j));
            }
            gd.omsättning=OSB.toString();



            for(i=0;i<n;i++){
                gd.value[i][0]=utills.round(gd.crossed[i][0]*gd.wodds[i][0],2);
                gd.value[i][1]=utills.round(gd.crossed[i][1]*gd.wodds[i][1],2);
                gd.value[i][2]=utills.round(gd.crossed[i][2]*gd.wodds[i][2],2);
            }
            i = line.indexOf(", stänger ", i + 1);
            gd.spelstopp=line.substring(i + 10, i + 26);


            if(type==gameTypes.STRYKTIPSET)Stryk=gd;
            if(type==gameTypes.EUROPATIPSET)EU=gd;
            if(type==gameTypes.POWERPLAY)PP=gd;
        }catch (Exception e){}

    }
}
