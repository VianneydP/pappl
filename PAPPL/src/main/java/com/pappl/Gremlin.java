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
public class Gremlin {
    private ArrayList<Logement> logements;
    private ArrayList<Eleve> eleves;

    public Gremlin() {
        this.logements = new ArrayList<>();
        this.eleves = new ArrayList<>();
    }
    
    public void recupDonneesEleves(String nomFichier) throws IOException{
        try{
            BufferedReader fichier=new BufferedReader(new FileReader(nomFichier));
            String ligne=fichier.readLine();
            ligne=fichier.readLine();
            System.out.println(ligne);
            while (ligne!=null){
                //Défilement et lecture de chaque ligne une à une
                StringTokenizer tok=new StringTokenizer(ligne,";");
                ArrayList<String> mots=new ArrayList<>();
                while (tok.hasMoreTokens()){
                    mots.add(tok.nextToken());
                }
                Eleve el=new Eleve(mots);
                eleves.add(el);
                ligne=fichier.readLine();
            }
        }catch (FileNotFoundException ex){
            System.out.println("FICHIER NON TROUVE !");
            System.out.println(ex);
        }
    }

    public void recupDonneesLogements(String nomFichier) throws IOException{
        try{
            BufferedReader fichier=new BufferedReader(new FileReader(nomFichier));
            String ligne=fichier.readLine();
            ligne=fichier.readLine();
            while (ligne!=null){
                //Défilement et lecture de chaque ligne une à une
                //Processus d'extraction des information d'une ligne CSV
                //Pourquoi pas StringTokenizer ? Parce qu'il se débarrasse des éléments vides
                // => problèmes d'indexation des informations... Essaie ! Tu verras
                ArrayList<String> lineValues=new ArrayList<>();
                int i=0;
                String elem="";
                while (i<ligne.length()){
                    if (ligne.substring(i, i+1).equals(";")){
                        lineValues.add(elem);
                        elem="";
                    }else{
                        if(ligne.substring(i,i+1).equals(",")){
                            elem+=".";
                        }else{
                            elem+=ligne.substring(i,i+1);
                        }
                    }
                    i++;
                }
                if (lineValues.get(1)!=("")){
                    //Création logement
                    Logement log=new Logement(lineValues);
                    logements.add(log);
                }else{
                    if(lineValues.get(5)!=""){
                        for(Logement log:logements){
                            if(log.getNumero_id().equals(lineValues.get(3).substring(0, 5))){
                                log.maj(lineValues);
                            }
                        }
                    }
                }
                System.out.println(lineValues);
                ligne=fichier.readLine();
            }
        }catch (FileNotFoundException ex){
            System.out.println("FICHIER NON TROUVE !");
            System.out.println(ex);
        }
    }

    public void afficheLogements(){
        for (Logement log:logements){
            log.affiche();
        }
    }

    public ArrayList<Logement> getLogements() {
        return logements;
    }

    public ArrayList<Eleve> getEleves() {
        return eleves;
    }

    public void setLogements(ArrayList<Logement> logements) {
        this.logements = logements;
    }

    public void setEleves(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
    }
    
    
}
