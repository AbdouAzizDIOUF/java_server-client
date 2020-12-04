package entity;

import java.io.Serializable;

/**
 * @author abdou
 */
public class Produit implements Serializable {
    private int id;
    private String nomProd;
    private String typeProd;
    private String codeProd;
    private boolean estDisponible;
    private int prix;
    private int stock;

    public Produit() {
    }

    public Produit(int id, int prix, String nomProd, String type_produit, String code_prod) {
        this.id = id;
        this.nomProd = nomProd;
        this.typeProd = type_produit;
        this.codeProd = code_prod;
        this.prix = prix;
    }

    public Produit(int prix, String nomProd, String type_produit, String code_prod) {
        this.prix = prix;
        this.nomProd = nomProd;
        this.typeProd = type_produit;
        this.codeProd = code_prod;
    }

    public Produit(int id,int prix, String nomProd, String type_produit, String code_prod, int stock) {
        this.id = id;
        this.prix = prix;
        this.nomProd = nomProd;
        this.typeProd = type_produit;
        this.codeProd = code_prod;
        this.stock = stock;
    }

    public Produit(int id, String nomProd, String typeProd, String codeProd, boolean estDisponible, int prix, int stock) {
        this.id = id;
        this.nomProd = nomProd;
        this.typeProd = typeProd;
        this.codeProd = codeProd;
        this.estDisponible = estDisponible;
        this.prix = prix;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getTypeProd() {
        return typeProd;
    }

    public void setType_produit(String typeProd) {
        this.typeProd = typeProd;
    }

    public String getCodeProd() {
        return codeProd;
    }

    public void setCodeProd(String code_prod) {
        this.codeProd = code_prod;
    }

    public void setTypeProd(String typeProd) {
        this.typeProd = typeProd;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void detailAchat(){
        System.out.println("" +
                "id  => "+this.id+
                "\nnom produit    => "+this.nomProd+
                "\ntypeProd   => "+this.typeProd+
                "\ncode produit   => "+this.codeProd+
                "\nprix produit   => "+this.prix
        );
    }
}
