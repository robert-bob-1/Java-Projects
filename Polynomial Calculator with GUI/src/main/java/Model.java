package main.java;

public class Model {

    Model() {
    }

    public String add(String firstPolynomial, String secondPolynomial){

        Polinom first=new Polinom(firstPolynomial);
        Polinom second=new Polinom(secondPolynomial);
        first.add(second);
        if (first.monoame.size()==0)
            return "0";
        return first.toString();
    }

    public String sub(String firstPolynomial, String secondPolynomial){

        Polinom first=new Polinom(firstPolynomial);
        Polinom second=new Polinom(secondPolynomial);
        first.sub(second);
        if (first.monoame.size()==0)
            return "0";
        return first.toString();
    }
    public String mul(String firstPolynomial, String secondPolynomial){

        Polinom first=new Polinom(firstPolynomial);
        Polinom second=new Polinom(secondPolynomial);
        first.multiply(second);
        if (first.monoame.size()==0)
            return "0";
        return first.toString();
    }

    public String der(String firstPolynomial){
        Polinom first=new Polinom(firstPolynomial);
        first.derive();
        if (first.monoame.size()==0)
            return "0";
        return first.toString();
    }

    public String integrate(String firstPolynomial){
        Polinom first=new Polinom(firstPolynomial);
        first.integrate();
        if (first.monoame.size()==0)
            return "0";
        return first.toString();
    }

    public static void main(String[]args){
        Model model=new Model();

    }
}
