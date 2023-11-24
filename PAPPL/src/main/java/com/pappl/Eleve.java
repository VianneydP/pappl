/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pappl;

import static java.lang.Integer.parseInt;
import java.sql.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author viann
 */
public class Eleve {
    
    /**Nom de l'élève*/
    private String nom;
    /**Prénom de l'élève*/
    private String prenom;
    /**Genre de l'élève*/
    private String genre;
    /**Date de naissance*/
    private Date naissance;
    /**Boursier ou non*/
    private boolean boursier;
    
    /**Pays d'habitation*/
    private String pays;
    /**Ville d'habitation*/
    private String ville;
    /**Code postal*/
    private String cpostal;
    
    /**Adresse e-mail*/
    private String mail;
    /**Numéro de téléphone*/
    private int num_tel;
    
    
    public Eleve(){
        nom="";
        prenom="";
        genre="";
        naissance=new Date(0,1,1);
        boursier=false;
        pays="";
        ville="";
        mail="";
        num_tel=0;
    }
    public Eleve(String nom, String prenom,String genre){     //Pour récupérer infos de SCEI
        this();
        this.nom=nom;
        this.prenom=prenom;
        this.genre=genre;
    }
    
    public Eleve(ArrayList<String> mots){
        this();
        //Codage du genre
        if (mots.get(0).equals("M.")){
            genre="M.";
        } if (mots.get(0).equals("Mme")){
            genre="Mme";
        }
        nom=mots.get(1);
        prenom=mots.get(2);
        //Reconstruction date de naissance
        StringTokenizer tok=new StringTokenizer(mots.get(3),"/");
        ArrayList<String> naiss=new ArrayList<>();
        while (tok.hasMoreTokens()){
            naiss.add(tok.nextToken());
        }
        this.naissance=new Date(parseInt(naiss.get(2))-1900,parseInt(naiss.get(1)),parseInt(naiss.get(0)));
        this.cpostal=mots.get(4);
        this.ville=mots.get(5);
        this.pays=mots.get(6);
    }
    
    public void affiche(){
        System.out.println("\n"+genre+" "+prenom+" "+nom);
        System.out.println(naissance);
        System.out.println("Habite à: "+ville+" ("+pays+")");
        System.out.println("Mail: "+mail);
        System.out.println("Tel: "+num_tel);
        System.out.println("Boursier: "+boursier);
    }

    public void afficheSimple(){
        System.out.println(" "+genre+" "+prenom+" "+nom);
    }
}
