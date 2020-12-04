package entity;

import java.io.Serializable;

public class BilanVente implements Serializable {
    private String nom;
    private String typeProduit;
    private int nbVente;
    private float totalPrice;
    private int totalQuantite;


    public BilanVente(String nom, String typeProduit, int nbVente, float totalPrice, int totalQuantite) {
        this.nom = nom;
        this.typeProduit = typeProduit;
        this.nbVente = nbVente;
        this.totalPrice = totalPrice;
        this.totalQuantite = totalQuantite;
    }

    public BilanVente() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public int getNbVente() {
        return nbVente;
    }

    public void setNbVente(int nbVente) {
        this.nbVente = nbVente;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantite() {
        return totalQuantite;
    }

    public void setTotalQuantite(int totalQuantite) {
        this.totalQuantite = totalQuantite;
    }

    @Override
    public String toString() {
        return "BilanVente{" +
                "nom='" + nom + '\'' +
                ", typeProduit='" + typeProduit + '\'' +
                ", nbVente=" + nbVente +
                ", totalPrice=" + totalPrice +
                "€, totalQuantite=" + totalQuantite +
                '}';
    }
}
