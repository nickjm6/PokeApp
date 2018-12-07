package com.example.nickjm6.pokeapp;

import java.util.ArrayList;

public class Pokemon {
    private String name;
    private String type1;
    private String type2;
    private ArrayList<String> weaknesses;
    private ArrayList<String> resistances;
    private ArrayList<String> superweaknesses;
    private ArrayList<String> superresistances;
    private ArrayList<String> unaffected;
    private int nationalNumber;
    private String evolvesInto;
    private String evolvesFrom;

    public Pokemon(){
        weaknesses = new ArrayList<String>();
        resistances = new ArrayList<String>();
        superweaknesses = new ArrayList<String>();
        superresistances = new ArrayList<String>();
        unaffected = new ArrayList<String>();
    }

    public String getName(){
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getType1(){
        return type1;
    }

    public void setType1(String newType1){
        type1 = newType1;
    }

    public String getType2(){
        return type2;
    }

    public void setType2(String newType2){
        type2 = newType2;
    }

    public void addWeakness(String weakness){
        weaknesses.add(weakness);
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public void addResistance(String resistance){
        resistances.add(resistance);
    }

    public ArrayList<String> getResistances() {
        return resistances;
    }

    public void addSuperResistance(String superrResistance){
        superresistances.add(superrResistance);
    }

    public ArrayList<String> getSuperresistances() {
        return superresistances;
    }

    public void addSuperWeakness(String superWeakness){
        superweaknesses.add(superWeakness);
    }

    public ArrayList<String> getSuperweaknesses() {
        return superweaknesses;
    }

    public void addUnaffected(String type){
        unaffected.add(type);
    }

    public ArrayList<String> getUnaffected() {
        return unaffected;
    }

    public void setNationalNumber(int number){
        nationalNumber = number;
    }

    public int getNationalNumber() {
        return nationalNumber;
    }

    public void setEvolvesInto(String evolution){
        evolvesInto = evolution;
    }

    public String getEvolvesInto() {
        return evolvesInto;
    }

    public void setEvolvesFrom(String evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }

    @Override
    public String toString() {
        String res = "#" + nationalNumber + " - " + name + ":\n";
        res += "Types: [" + type1;
        if(type2 != null)
            res += ", " + type2 + "]\n";
        else
            res += "]\n";
        if(weaknesses.size() > 0){
            res += "Weak against: [ ";
            for(String weakness : weaknesses)
                res += weakness + " ";
            res += "]\n";
        }
        if(superweaknesses.size() > 0){
            res += "Super weak against: [ ";
            for(String weakness : superweaknesses)
                res += weakness + " ";
            res += "]\n";
        }
        if(resistances.size() > 0){
            res += "Resistant to: [ ";
            for(String resistance : resistances)
                res += resistance + " ";
            res += "]\n";
        }
        if(superresistances.size() > 0){
            res += "Super resistant to: [ ";
            for(String resistance : superresistances)
                res += resistance + " ";
            res += "]\n";
        }
        if(unaffected.size() > 0){
            res += "Unaffected by: [ ";
            for(String type : unaffected)
                res += type + " ";
            res += "]\n";
        }
        if(evolvesInto != null)
            res += "Evolves into: " + evolvesInto + "\n";
        if(evolvesFrom != null)
            res += "Evolves from: " + evolvesFrom;
        return res;
    }
}
