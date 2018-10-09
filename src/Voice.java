import java.io.Serializable;

/**
 * Created by d on 2018/10/9.
 */
public class Voice implements Serializable{

    private String PRONOUNCER;

    private String VOWEL;

    private Double DURATION;

    private Double K1;

    private Double K2;

    private Double K3;

    private Double K4;

    private Double START1;

    private Double START2;

    private Double START3;

    private Double START4;

    private Double DIFFERENCE1;

    private Double DIFFERENCE2;

    private Double DIFFERENCE3;

    private Double DIFFERENCE4;

    public Double getSTART4() {
        return START4;
    }

    public void setSTART4(Double START4) {
        this.START4 = START4;
    }

    public String getPRONOUNCER() {
        return PRONOUNCER;
    }

    public void setPRONOUNCER(String PRONOUNCER) {
        this.PRONOUNCER = PRONOUNCER;
    }

    public String getVOWEL() {
        return VOWEL;
    }

    public void setVOWEL(String VOWEL) {
        this.VOWEL = VOWEL;
    }

    public Double getDURATION() {
        return DURATION;
    }

    public void setDURATION(Double DURATION) {
        this.DURATION = DURATION;
    }

    public Double getK1() {
        return K1;
    }

    public void setK1(Double k1) {
        K1 = k1;
    }

    public Double getK2() {
        return K2;
    }

    public void setK2(Double k2) {
        K2 = k2;
    }

    public Double getK3() {
        return K3;
    }

    public void setK3(Double k3) {
        K3 = k3;
    }

    public Double getK4() {
        return K4;
    }

    public void setK4(Double k4) {
        K4 = k4;
    }

    public Double getSTART1() {
        return START1;
    }

    public void setSTART1(Double START1) {
        this.START1 = START1;
    }

    public Double getSTART2() {
        return START2;
    }

    public void setSTART2(Double START2) {
        this.START2 = START2;
    }

    public Double getSTART3() {
        return START3;
    }

    public void setSTART3(Double START3) {
        this.START3 = START3;
    }

    public Double getDIFFERENCE1() {
        return DIFFERENCE1;
    }

    public void setDIFFERENCE1(Double DIFFERENCE1) {
        this.DIFFERENCE1 = DIFFERENCE1;
    }

    public Double getDIFFERENCE2() {
        return DIFFERENCE2;
    }

    public void setDIFFERENCE2(Double DIFFERENCE2) {
        this.DIFFERENCE2 = DIFFERENCE2;
    }

    public Double getDIFFERENCE3() {
        return DIFFERENCE3;
    }

    public void setDIFFERENCE3(Double DIFFERENCE3) {
        this.DIFFERENCE3 = DIFFERENCE3;
    }

    public Double getDIFFERENCE4() {
        return DIFFERENCE4;
    }

    public void setDIFFERENCE4(Double DIFFERENCE4) {
        this.DIFFERENCE4 = DIFFERENCE4;
    }
}
