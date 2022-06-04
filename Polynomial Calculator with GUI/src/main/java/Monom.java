package main.java;

public class Monom implements Comparable<Monom>{
    public int power;
    public double constant;

    public Monom(int power, double constant) {
        this.power = power;
        this.constant = constant;
    }

    public Monom(String monom){
        int isNegative=1,xIndex=-1;

        if(monom.contains("-")){ isNegative=-1; }
        monom=monom.replaceAll("[+-]","");

        if(monom.contains("x")){ xIndex=monom.indexOf("x"); }
        if(xIndex==0 && xIndex==monom.length()-1){
            this.constant=isNegative;
            this.power=1;
        } else if(xIndex==0 ){
            this.constant=isNegative;
            this.power=Integer.parseInt(monom.substring(1,monom.length()));
        } else if(xIndex==monom.length()-1){
            this.constant=isNegative*Integer.parseInt(monom.substring(0,monom.length()-1));
            this.power=1;
        } else if(xIndex==-1){
            this.constant=isNegative*Integer.parseInt(monom);
            this.power=0;
        } else{
            this.constant=isNegative*Integer.parseInt(monom.substring(0,xIndex));
            this.power=Integer.parseInt(monom.substring(xIndex+1,monom.length()));
        }
    }

    public Monom() {
        this.power=0;
        this.constant=0;
    }

    @Override
    public int compareTo(Monom b){
        if (this.power>b.power)
            return -1;
        if(this.power<b.power)
            return 1;
        return 0;

    }

    public Monom add(Monom a){
        return new Monom(this.power,this.constant+a.constant);
    }
    public Monom sub(Monom a){
        return new Monom(this.power, this.constant-a.constant);
    }
}
