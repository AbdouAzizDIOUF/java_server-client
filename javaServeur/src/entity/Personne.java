package entity;

import java.io.Serializable;

/**
 * @author abdou
 */
public class Personne implements Serializable {

    private int id;
    private String nom;
    private String prenom;
    private String num_nci;
    private String num_sec;

    public Personne(int id, String nom, String prenom, String num_nci, String num_sec) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.num_nci = num_nci;
        this.num_sec = num_sec;
    }

    public Personne(String nom, String prenom, String num_nci, String num_sec) {
        this.nom = nom;
        this.prenom = prenom;
        this.num_nci = num_nci;
        this.num_sec = num_sec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNum_nci() {
        return num_nci;
    }

    public void setNum_nci(String num_nci) {
        this.num_nci = num_nci;
    }

    public String getNum_sec() {
        return num_sec;
    }

    public void setNum_sec(String num_sec) {
        this.num_sec = num_sec;
    }
}
