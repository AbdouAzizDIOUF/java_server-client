package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author abdou
 */
public class Achat implements Serializable {

    private int id;
    private int quantity;
    private Date dateAchat;
    private float prixTotal;
    private int produit;
    private Collection<Produit> produits;

    public Achat(int quantity, float prixTotal) {
        this.quantity = quantity;
        this.dateAchat = new Date();
        this.prixTotal = prixTotal;

    }
    public Achat(int produit, int quantity, float prixTotal) {
        this.quantity = quantity;
        this.prixTotal = prixTotal;
        this.produit = produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getProduit() {
        return produit;
    }

    public void setProduit(int produit) {
        this.produit = produit;
    }

    public Collection<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Collection<Produit> produits) {
        this.produits = produits;
    }
}
