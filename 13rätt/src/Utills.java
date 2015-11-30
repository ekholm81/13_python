/**
 * Created by mq on 2014-12-20.
 */
public class Utills {

    public double[] stripodds(String s){
        double[] res=new double[4];
        int index=0;
        for(int i=0;i<3;i++){
            StringBuilder tmp=new StringBuilder();
            while (true){
                if(index==s.length()){
                    res[i]=Double.parseDouble(tmp.toString());
                    index++;
                    break;
                }
                if(s.charAt(index)=='-'){
                    res[i]=Double.parseDouble(tmp.toString());
                    index++;
                    break;
                }
                tmp.append(s.charAt(index));
                index++;
            }
        }
        res[3]=getOddsC(res);
        return getwodds(res);
    }

    public double[] getwodds(double[] i){
        double c= 1.00/(1/i[0]+1.00/i[1]+1.00/i[2]);
        i[0]=round(i[0]/c,2);
        i[1]=round(i[1]/c,2);
        i[2]=round(i[2]/c,2);
        return i;
    }

    public double getOddsC(double[] i){
        return 1.00/(1/i[0]+1.00/i[1]+1.00/i[2]);
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public int getBestSign(double[] i){
        if(i[0]<=i[1] && i[0]<=i[2])return 0;
        if(i[1]<=i[0] && i[1]<=i[2])return 1;
        if(i[2]<=i[0] && i[2]<=i[1])return 2;
        return -1;
    }
    public int getworstSign(double[] i){
        if(i[0]>=i[1] && i[0]>=i[2])return 0;
        if(i[1]>=i[0] && i[1]>=i[2])return 1;
        if(i[2]>=i[0] && i[2]>=i[1])return 2;
        return -1;
    }
    public int getmiddleBestSign(double[] i){
        boolean[]booleans=new boolean[3];
        booleans[getBestSign(i)]=true;
        booleans[getworstSign(i)]=true;
        for (int j=0;j<3;j++)if(!booleans[j])return j;
        return -1;
    }
    public double getProb(double[] i, boolean[]b){
        double res=0;
        for (int j=0;j<3;j++){
            if(b[j])res+=1.00/i[j];
        }
        return res;
    }

    public String fs(String s, int n){
        StringBuilder SB=new StringBuilder();
        if(s.length()>=n)return s;
        else{
            SB.append(s);
            while (SB.length()<n)SB.append(' ');
        }
        return SB.toString();
    }

    public double getNotProb(double[] i, boolean[]b){
        return 1.00-getProb(i,b);
    }

    public GameData mergeData(GameData n, GameData o){
        if(o==null)return n;
        for(int i=0;i<n.crossed.length;i++){
            for(int j=0;j<3;j++){
                if(n.wodds[i][j]<o.wodds[i][j])n.oddsCh[i][j]=1;
                else if(n.wodds[i][j]>o.wodds[i][j])n.oddsCh[i][j]=2;
                else n.oddsCh[i][j]=0;

                if(n.crossed[i][j]<o.crossed[i][j])n.crossedCh[i][j]=1;
                else if(n.crossed[i][j]>o.crossed[i][j])n.crossedCh[i][j]=2;
                else n.crossedCh[i][j]=0;

                if(n.value[i][j]<o.value[i][j])n.valueCh[i][j]=1;
                else if(n.value[i][j]>o.value[i][j])n.valueCh[i][j]=2;
                else n.valueCh[i][j]=0;
            }
        }return n;
    }

}
