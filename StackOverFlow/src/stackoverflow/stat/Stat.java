package stackoverflow.stat;
public class Stat {

    private long ctf;
    private long df;
    private double idf;
    private double ictf;

    public Stat(long ctf, long df, double idf, double ictf) {
        this.ctf = ctf;
        this.df = df;
        this.idf = idf;
        this.ictf = ictf;
    }

    public long getCtf() {
        return ctf;
    }

    public long getDf() {
        return df;
    }

    public double getIdf() {
        return idf;
    }

    public double getIctf() {
        return ictf;
    }
}