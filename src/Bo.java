import java.io.Serializable;

/**
 * Created by d on 2018/9/27.
 */
public class Bo implements Serializable{

    private String Item;

    private String Subject;

    private String Vowel;

    private String Node;

    private double F1;

    private double F2;

    private double F3;

    private double F4;

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getVowel() {
        return Vowel;
    }

    public void setVowel(String vowel) {
        Vowel = vowel;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }

    public double getF1() {
        return F1;
    }

    public void setF1(double f1) {
        F1 = f1;
    }

    public double getF2() {
        return F2;
    }

    public void setF2(double f2) {
        F2 = f2;
    }

    public double getF3() {
        return F3;
    }

    public void setF3(double f3) {
        F3 = f3;
    }

    public double getF4() {
        return F4;
    }

    public void setF4(double f4) {
        F4 = f4;
    }

    public void setF4(Integer f4) {
        F4 = f4;
    }
}
