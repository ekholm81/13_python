

public class Triplet implements Comparable<Triplet>
{
    private  int key;
    private  Double value;
    private Double cross;
    private Double chance;
    public Triplet()
    {
        key   = -1;
        value = -1.0;
        cross=-1.0;
        chance=-1.0;
    }

    public void append(int aKey, Double aValue,Double aCross, Double aChance){
        key   = aKey;
        value = aValue;
        cross = aCross;
        chance = aChance;
    }

    public int key()   { return key; }
    public double value() { return value; }
    public double cross() { return cross; }
    public double chance() { return chance; }

    @Override
    public int compareTo(Triplet triplet) {
        if(this.value()>triplet.value())return 1;
        if(this.value()<triplet.value())return -1;
        return 0;
    }
}