package test.java;
import java.util.ArrayList;
import static kotlin.test.AssertionsKt.assertTrue;
import main.java.*;
import org.testng.annotations.Test;

public class OperationTest {
    @Test
    public void addTest1(){
        Polinom p1=new Polinom("x+1");
        Polinom p2=new Polinom("x+2");
        p1.add(p2);
        Polinom p3=new Polinom("2x+3");
        assertTrue(Polinom.equals(p1,p3), "x+1 + x+2 = 2x+3");
    }
    @Test
    public void addTest2(){
        Polinom p1=new Polinom("1");
        Polinom p2=new Polinom("1x20+2x+2");
        p1.add(p2);
        Polinom p3=new Polinom("x20+2x+3");
        assertTrue(Polinom.equals(p1,p3), "1 + 1x20+2x+2 = x20+2x+3");
    }
    @Test
    public void addTest3(){
        Polinom p1=new Polinom("x5-2x4");
        Polinom p2=new Polinom("x+2");
        p1.add(p2);
        Polinom p3=new Polinom("x5-2x4+x+2");
        assertTrue(Polinom.equals(p1,p3), "x5-2x4 + x+2 = x5-2x4+x+2");
    }
    @Test
    public void addTest4(){
        Polinom p1=new Polinom("1x20+2x+2");
        Polinom p2=new Polinom("1");
        p1.add(p2);
        Polinom p3=new Polinom("x20+2x+3");
        assertTrue(Polinom.equals(p1,p3), "1x20+2x+2 + 1 = x20+2x+3");
    }
    @Test
    public void subTest1(){
        Polinom p1=new Polinom("x+1");
        Polinom p2=new Polinom("x+2");
        p1.sub(p2);
        Polinom p3=new Polinom("-1");
        assertTrue(Polinom.equals(p1,p3), "x+1 - x+2 = -1");
    }
    @Test
    public void subTest2(){
        Polinom p1=new Polinom("1");
        Polinom p2=new Polinom("1x20+2x+2");
        p1.sub(p2);
        Polinom p3=new Polinom("-1x20-2x-1");
        assertTrue(Polinom.equals(p1,p3), "1 - 1x20+2x+2 = -x20-2x-1");
    }
    @Test
    public void subTest3(){
        Polinom p1=new Polinom("x5-2x4");
        Polinom p2=new Polinom("x+2");
        p1.sub(p2);
        Polinom p3=new Polinom("x5-2x4-x-2");
        assertTrue(Polinom.equals(p1,p3), "x5-2x4 - x+2 = x5-2x4-x-2");
    }
    @Test
    public void subTest4(){
        Polinom p1=new Polinom("1x20+2x+2");
        Polinom p2=new Polinom("1");
        p1.sub(p2);
        Polinom p3=new Polinom("x20+2x+1");
        assertTrue(Polinom.equals(p1,p3), "1x20+2x+2 - 1 = x20+2x+1");
    }
    @Test
    public void mulTest1(){
        Polinom p1=new Polinom("x+1");
        Polinom p2=new Polinom("x+2");
        p1.multiply(p2);
        Polinom p3=new Polinom("x2+3x+2");
        assertTrue(Polinom.equals(p1,p3), "x+1 * x+2 = x2+3x+2");
    }
    @Test
    public void mulTest2(){
        Polinom p1=new Polinom("1");
        Polinom p2=new Polinom("1x20+2x+2");
        p1.multiply(p2);
        Polinom p3=new Polinom("1x20   +2x+2");
        assertTrue(Polinom.equals(p1,p3), "1 * 1x20+2x+2 = 1x20+2x+2");
    }
    @Test
    public void mulTest3(){
        Polinom p1=new Polinom("x-2");
        Polinom p2=new Polinom("x+2");
        p1.multiply(p2);
        Polinom p3=new Polinom("x2   -4");
        assertTrue(Polinom.equals(p1,p3), "x-2 * x+2 = x2-4");
    }
    @Test
    public void mulTest4(){
        Polinom p1=new Polinom("1x20+2x+2");
        Polinom p2=new Polinom("-1");
        p1.multiply(p2);
        Polinom p3=new Polinom("-x20-2x-2");
        assertTrue(Polinom.equals(p1,p3), "1x20+2x+2 * -1 = -x20-2x-2");
    }
    @Test
    public void derTest1(){
        Polinom p1=new Polinom("x+1");
        p1.derive();
        Polinom p3=new Polinom("1");
        assertTrue(Polinom.equals(p1,p3), "x+1 derived = 1");
    }
    @Test
    public void derTest2(){
        Polinom p1=new Polinom("1");
        p1.derive();
        Polinom p3=new Polinom("0");
        assertTrue(Polinom.equals(p1,p3), "1 derived = 0");
    }
    @Test
    public void derTest3(){
        Polinom p1=new Polinom("-2x3-2x");
        p1.derive();
        Polinom p3=new Polinom("-6x2-2");
        assertTrue(Polinom.equals(p1,p3), "-2x3-2x derived = -6x2-2");
    }
    @Test
    public void derTest4(){
        Polinom p1=new Polinom("10x2-2x-3");
        p1.derive();
        Polinom p3=new Polinom("20x-2");
        assertTrue(Polinom.equals(p1,p3), "10x2-2x-3 derived = 20x-2");
    }
    @Test
    public void intTest1(){
        Polinom p1=new Polinom("x+1");
        p1.integrate();
        Polinom p3=new Polinom("x^2+x");
        assertTrue(Polinom.equals(p1,p3), "x+1 integrated = x^2+x");
    }
    @Test
    public void intTest2(){
        Polinom p1=new Polinom("-1");
        p1.integrate();
        Polinom p3=new Polinom("-x");
        assertTrue(Polinom.equals(p1,p3), "-1 integrated = -x");
    }
    @Test
    public void intTest3(){
        Polinom p1=new Polinom("-2x3-2x");
        p1.integrate();
        ArrayList<Monom> result=new ArrayList<Monom>();
        result.add(new Monom(4,-2.0/3));
        result.add(new Monom(2,-2));
        Polinom p3=new Polinom();p3.monoame=result;
        assertTrue(Polinom.equals(p1,p3), "-2x3-2x integrated = -2/3x^4-2x^2");
    }
}
