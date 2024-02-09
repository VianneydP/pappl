/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pappl;

import java.io.IOException;
import java.sql.Date;

/**
 *
 * @author viann
 */
public class PAPPL {

    public static void main(String[] args) throws IOException {
        Gremlin gre=new Gremlin();
        gre.recupDonneesLogements("C:\\Users\\elsaa\\Documents\\Centrale\\INFOSI\\PGROU\\GIT\\pappl\\Fichiers_csv\\Tableurs_REZ_test.csv");
        gre.afficheLogements();
    }
}

/*for (int i=0;i<7;i++){
            gre.getEleves().get(i).affiche();
        }*/