/**
 * Created by mq on 2014-12-20.
 */
public class Utills {

    public double[] getwodds(double[] i){
        double c= 1.00/(1/i[0]+1.00/i[1]+1.00/i[2]);
        i[0]=round(i[0]/c,2);
        i[1]=round(i[1]/c,2);
        i[2]=round(i[2]/c,2);
        return i;
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
    public double getNotProb(double[] i, boolean[]b){
        return 1.00-getProb(i,b);
    }

}
