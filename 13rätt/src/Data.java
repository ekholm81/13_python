import com.sun.org.apache.xpath.internal.SourceTree;
import java.io.PrintWriter;
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
    GameData TT;
    Utills utills;
    public boolean access=false;
    public Data(){
        utills=new Utills();
    }

    public void collectStrykData(boolean offline){
        genHtml(gameTypes.STRYKTIPSET,offline);
    }

    public void     collectEUData(boolean offline){genHtml(gameTypes.EUROPATIPSET, offline);}

    public void     collectTTData(boolean offline){genHtml(gameTypes.TOPPTIPSET,offline);}

    public void     collectPPData(boolean offline){genHtml(gameTypes.POWERPLAY,offline);}

    private String cleanString(String s){
        StringBuilder SB=new StringBuilder();
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)=='}' || s.charAt(i)==',')continue;
            else {
                SB.append(s.charAt(i));
            }
        }return SB.toString();
    }


    private void genHtml(gameTypes type,boolean offline){
        String gameType;
        if(type==gameTypes.STRYKTIPSET){gameType="stryktipset";}
        else if(type==gameTypes.EUROPATIPSET)gameType="europatipset";
        else if(type==gameTypes.TOPPTIPSET)gameType="topptipset";
        else if(type==gameTypes.POWERPLAY)gameType="powerplay";
        else gameType="";
        try{
            if(!offline){
                Process pp = Runtime.getRuntime().exec("rm "+gameType);
                pp.waitFor();
                Process p = Runtime.getRuntime().exec("wget https://www.svenskaspel.se/"+gameType+"/spela -O "+gameType);
                p.waitFor();
            }
        }
        catch (Exception e){e.printStackTrace();}
        int n=0;
        if(type==gameTypes.EUROPATIPSET || type==gameTypes.STRYKTIPSET)n=13;
        if (type==gameTypes.POWERPLAY)n=8;
        if (type==gameTypes.TOPPTIPSET)n=8;
        GameData gd=new GameData(n);
        try{
	    PrintWriter writer = new PrintWriter("matriser.txt", "UTF-8");
            BufferedReader br = new BufferedReader(new FileReader(gameType));
            String line = null;
            if(type==gameTypes.STRYKTIPSET)gd.utdelning=0.65;
            if(type==gameTypes.EUROPATIPSET)gd.utdelning=0.65;
            if(type==gameTypes.POWERPLAY)gd.utdelning=0.7;
            if(type==gameTypes.TOPPTIPSET)gd.utdelning=0.7;
            int i=0;
            while ((line = br.readLine()) != null)
            {
                i++;
                if(line.length()>22){
                    if(line.substring(0,22).equals("_svs.tipset.data.draws"))break;
                }
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
            for (i = -1; (i = line.indexOf("\"odds\":", i + 1)) != -1; ) {

                c++;
                int inc=0;
                if(c>n)break;
                /*if(c==1){
                    gd.odds[c-1][0]=1.44;
                    gd.odds[c-1][1]=3.50;
                    gd.odds[c-1][2]=11.70;
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
                gd.wodds[c-1]=utills.getwodds(gd.odds[c - 1].clone());
                gd.oddsC[c-1]=utills.round(100*utills.getOddsC(gd.odds[c - 1].clone()),2);
		writer.println("["+gd.wodds[c-1][0]+","+gd.wodds[c-1][1]+","+gd.wodds[c-1][2]+"],");

            }
             c=0;
            for (i = -1; (i = line.indexOf("svenskaFolket\":{\"one\":", i + 1)) != -1; ) {
                c++;
                if(c>n)break;
                int inc=0;
                String o1=cleanString(line.substring(i+22,i+24));
                gd.crossed[c-1][0]=Double.parseDouble(o1);
                if(gd.crossed[c-1][0]<10.00)inc--;
                String o2= cleanString(line.substring(i + 29 + inc,i+31+inc));
                gd.crossed[c-1][1]=Double.parseDouble(o2);
                if(gd.crossed[c-1][1]<10.00)inc--;
                String o3=cleanString(line.substring(i+38+inc,i+40+inc));
                gd.crossed[c-1][2]=Double.parseDouble(o3);
                writer.println("["+gd.crossed[c-1][0]+","+gd.crossed[c-1][1]+","+gd.crossed[c-1][2]+"],");

            }

            i=line.indexOf("\"currentNetSale\":\"");
            StringBuilder OSB=new StringBuilder();
            for(int j=18;j<30;j++){
                if(line.charAt(i+j)=='\"')break;
                OSB.append(line.charAt(i + j));
            }
            gd.omsättning=OSB.toString();


	    writer.close();
            for(i=0;i<n;i++){
                gd.value[i][0]=utills.round(gd.crossed[i][0]*gd.wodds[i][0],2);
                gd.value[i][1]=utills.round(gd.crossed[i][1]*gd.wodds[i][1],2);
                gd.value[i][2]=utills.round(gd.crossed[i][2]*gd.wodds[i][2],2);
            }
            i = line.indexOf(", stänger ", i + 1);
            gd.spelstopp=line.substring(i + 10, i + 26);
            if(gd.spelstopp.substring(0,6).equals("2015-1"))access=true;
            if(type==gameTypes.STRYKTIPSET)Stryk=gd;
            if(type==gameTypes.EUROPATIPSET)EU=gd;
            if(type==gameTypes.POWERPLAY)PP=gd;
            if(type==gameTypes.TOPPTIPSET)TT=gd;
        }catch (Exception e){

        }

    }
}
