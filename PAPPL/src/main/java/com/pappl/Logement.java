/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pappl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author viann
 */
public class Logement {
    
    /**Numéro du logement (A11,B53...)*/
    private String numero_id;
    /**Genre des locataires*/
    private String genre;
    /**Nombre de places dans le logement*/
    private int places;
    /**Logement PMR*/
    private boolean pmr;
    /**Eleves du logement*/
    private ArrayList<Eleve> locataires;
    
    public Logement(){
        numero_id=" ";
        genre="";
        places=0;
        pmr=false;
        locataires=new ArrayList<Eleve>();
    }
    
    public Logement(ArrayList<String> mots){
        locataires=new ArrayList<Eleve>();
        genre="";
        //Récupération de l'id du logement
        numero_id=mots.get(3).substring(0, 5);
        //Nombre de places du logement
        places=1;
        if(mots.get(0)!=""){
            places=parseInt(mots.get(0));
        }
        //Ajout d'un locataire s'il y a lieu
        if (mots.get(5)!="" && mots.get(6)!=""){
            String gen="";
            if (mots.get(4).equals("Mr")){
                gen="M.";
            }if (mots.get(4).equals("Mme")){
                gen="Mme";
            }
            Eleve el=new Eleve(mots.get(5),mots.get(6),gen);
            locataires.add(el);
            genre=gen;
        }
        pmr=false;
        if (numero_id.equals("C 001")||numero_id.equals("C 002")||numero_id.equals("C 003")||numero_id.equals("C 004")||numero_id.equals("C 005")){
            pmr=true;
        }
        
    }
    
    public void maj(ArrayList<String> mots){
        String gen="";
            if (mots.get(4).equals("Mr")){
                gen="M.";
            }if (mots.get(4).equals("Mme")){
                gen="Mme";
            }
        Eleve el=new Eleve(mots.get(5),mots.get(6),gen);
        locataires.add(el);
    }
    
    public void affiche(){
        System.out.println("\nLogement: "+numero_id+", Nb places: "+places);
        System.out.println("Genre des locataires: "+genre);
        System.out.println("PMR: "+pmr);
        int i=0;
        for (Eleve loc:locataires){
            loc.afficheSimple();
            i++;
        }
        System.out.println("IL RESTE "+(places-i)+" PLACES DISPONIBLES");
    }
    
    public String getNumero_id() {
        return numero_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setNumero_id(String numero_id) {
        this.numero_id = numero_id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public void setPmr(boolean pmr) {
        this.pmr = pmr;
    }

    public void setLocataires(ArrayList<Eleve> habitants) {
        this.locataires = locataires;
    }

    public int getPlaces() {
        return places;
    }

    public boolean isPmr() {
        return pmr;
    }

    public ArrayList<Eleve> getLocataires() {
        return locataires;
    }
    
    
}
