package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Polinom{
    public ArrayList<Monom> monoame;
    public Polinom(){
        this.monoame=new ArrayList<Monom>();
    }
    public Polinom(String input){
        this.monoame=new ArrayList<Monom>();
        //stergem caracterele whitespace steluta si power si consideram stanga x-ului ca si constanta
        //   si dreapta x-ului ca putere
        input=input.replaceAll("([\s\\*\\^])","");
        input=input.replaceAll("([a-zA-Z])","x");
        Pattern pattern=Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher=pattern.matcher(input);
        int prevPower=-1, prevIndex=0,currPower=prevPower;
        while (matcher.find()) {
            this.monoame.add(new Monom(matcher.group(1)));
        }
        Collections.sort(monoame);
    }

    public void add(Polinom b){
        ArrayList<Monom> result=new ArrayList<>();int last=0;
        int size=this.monoame.size(), sizeb=b.monoame.size(), i=0,j=0;

        while(i<size && j<sizeb){
            Monom m1=monoame.get(i); Monom m2=b.monoame.get(j);
            if(m1.power>m2.power) {
                result.add(m1);
                i++;
            } else if(m2.power>m1.power) {
                result.add(m2);
                j++;
            } else {
                result.add(new Monom(m1.power,m1.constant+m2.constant));
                i++; j++;
            }
        }
        while( i < size ){ result.add(monoame.get(i)); i++; }
        while( j < sizeb ){ result.add(b.monoame.get(j)); j++; }

        this.monoame=result;
    }

    public void sub(Polinom b){
        ArrayList<Monom> result=new ArrayList<>();
        int size=this.monoame.size(), sizeb=b.monoame.size(), i=0,j=0;

        while(i<size && j<sizeb){
            Monom m1=monoame.get(i); Monom m2=b.monoame.get(j);
            if(m1.power>m2.power) {
                result.add(m1);
                i++;
            } else if(m2.power>m1.power) {
                result.add(new Monom(m2.power,-m2.constant));
                j++;
            } else {
                result.add(new Monom(m1.power,m1.constant-m2.constant));
                i++; j++;
            }
        }
        while( i < size ){ result.add(monoame.get(i)); i++; }
        while( j < sizeb ){ result.add(new Monom(b.monoame.get(j).power,b.monoame.get(j).constant*(-1))); j++; }


        this.monoame=result;
        this.clean();
    }

    public void multiply(Polinom b){
        this.clean();b.clean();
        Polinom result=new Polinom();
        for(Monom m2:b.monoame){
            Polinom temp=new Polinom();
            for(Monom m1:monoame){
                temp.monoame.add(new Monom(m1.power+m2.power,m1.constant*m2.constant));
            }
            result.add(temp);
        }
        Collections.sort(result.monoame);
        this.monoame=result.monoame;
    }

    public void derive(){
        Polinom result= new Polinom();
        for(Monom m:this.monoame){
            if(m.power>0)
                result.monoame.add(new Monom(m.power-1,m.constant*m.power));
        }
        if(result.monoame.size()==0)
            result.monoame.add(new Monom(0,0));
        this.monoame=result.monoame;
    }

    public void integrate(){
        Polinom result= new Polinom();
        for(Monom m:this.monoame){
            if(m.power>0)
                result.monoame.add(new Monom(m.power+1,m.constant/m.power));
            else result.monoame.add(new Monom(m.power+1,m.constant));
        }
        this.monoame=result.monoame;
    }

    public void clean(){
        int size=this.monoame.size();
        int i=0;
        while(i<size){
            if(monoame.get(i).constant==0) {
                monoame.remove(i--);
                size = this.monoame.size();
            }
            i++;
        }
    }

    @Override
    public java.lang.String toString() {
        StringBuilder string= new StringBuilder();
        this.clean();

        for(Monom m:monoame){
            string.append((m.constant>0 ? "+":"-")+  abs(m.constant)+"*x^"+m.power+" ");
        }

        return string.toString();
    }
    public void print(){
        for(Monom m:this.monoame){
            System.out.print( (m.constant>0 ? "+":"-")+  abs(m.constant)+"*x^"+m.power+" ");
        }
        System.out.println();
    }
    public static boolean equals(Polinom polinom, Polinom polinom1) {
        if(polinom1.monoame.size()!=polinom.monoame.size())
            return false;
        for(int i=0;i< polinom.monoame.size();i++){
            if(polinom1.monoame.get(i).constant!=polinom.monoame.get(i).constant ||
                    polinom1.monoame.get(i).power!=polinom.monoame.get(i).power){
                return false;
            }
        }
        return true;
    }


}
