import entity.Achat;
import entity.BilanVente;
import entity.Personne;
import entity.Produit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author abdou
 */
public class Actions {

    static Scanner sc = new Scanner(System.in);

    /**
     * Cette fonction realise la vente d'un produit
     *
     * @param objectOutputStream
     * @param objectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void ventdre(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int prixTotale = 0;
        String qte;
        String numSecSoc;
        Produit p = null;
        int status = 1;
        String codeProd;
        System.out.print("Veiller saisir le code du produit : ");
        do {
            do {
                codeProd = sc.nextLine();
                if (!ValideateExpression.isValideCodeProd(codeProd)){
                    System.out.println("Veiller respecter le format de chaine");
                    System.out.print("Veiller saisir le code du produit : ");
                }
            }while (!ValideateExpression.isValideCodeProd(codeProd));
            objectOutputStream.writeObject(codeProd);
            p = (Produit) objectInputStream.readObject();
            if (p==null){
                System.out.println("Code produit n'existe pas");
                System.out.print("Veiller saisir le code du produit valide : ");
            }
        }while (p==null);
        System.out.println("*****************************************************************");
        System.out.println("     ID :"+p.getId());
        System.out.println("     Nom Produit :"+p.getNomProd());
        System.out.println("     Code Produit :"+p.getCodeProd());
        System.out.println("     Type Produit :"+p.getTypeProd().toUpperCase());
        System.out.println("     Prix Produit :"+p.getPrix()+" €");
        System.out.println("     Stock Produit :"+p.getStock());
        System.out.println("******************************************************************");
        System.out.print("Quantite acheter (01 - 25) : ");
        do {
            qte = sc.nextLine();
            if (!ValideateExpression.isValideDecimal(qte)){
                System.out.println("Attention ! vous ne pouvez pas depasser la quantite totale comprise entre [01 - 25]");
                System.out.print("Quantite acheter (01 - 25) : ");
            }
        }while (!ValideateExpression.isValideDecimal(qte));
        int quantite = Integer.parseInt(qte);
        prixTotale = quantite*p.getPrix();
        Achat achat = new Achat(p.getId(), quantite, prixTotale);
        objectOutputStream.writeObject(achat);
        String res = (String) objectInputStream.readObject();
        System.out.println("Enregistrement reussi");
    }

    /**
     * Cette fonction realise la vente d'un produit pour les personnes qui en dispose d'une numero de securite sociale
     * en fin de beneficier d'une reduction de 25% de tous achat
     *
     * @param objectOutputStream
     * @param objectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void ventdreByReduc(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        float prixTotale = 0;
        String qte;
        Personne personne = null;
        Produit p = null;
        int status = 1;
        String codeProd;
        String numSec;
        System.out.print("Veiller saisir le code du produit : ");
        do {
            do {
                codeProd = sc.nextLine();
                if (!ValideateExpression.isValideCodeProd(codeProd)){
                    System.out.println("Veiller respecter le format de chaine");
                    System.out.print("Veiller saisir le code du produit : ");
                }
            } while (!ValideateExpression.isValideCodeProd(codeProd));
            objectOutputStream.writeObject(codeProd);
            p = (Produit) objectInputStream.readObject();
            if (p==null){
                System.out.println("Code produit n'existe pas");
                status = 0;
            }else {
                status = 1;
            }
        } while (status == 0);
        System.out.print("Donner le numero de securite sociale ou CNI du client : ");
        do{
            do {
                numSec = sc.nextLine();
                if (!ValideateExpression.isValideNumSecOrNumCni(numSec)){
                    System.out.println("Veiller respecter le format de chaine");
                    System.out.print("Veiller saisir le numero (CNI/SEC SOCIALE) : ");
                }
            } while (!ValideateExpression.isValideNumSecOrNumCni(numSec));
            objectOutputStream.writeObject(numSec);
            personne = (Personne) objectInputStream.readObject();
            if (personne==null){
                System.out.println("Desole le client ne pourra pas beneficier de reduction 25%, car numero de security sociale inexistant");
            }
        } while (personne==null);
        System.out.println("*****************************************************************");
        System.out.println("     ID :"+p.getId());
        System.out.println("     Nom Produit :"+p.getNomProd());
        System.out.println("     Code Produit :"+p.getCodeProd());
        System.out.println("     Type Produit :"+p.getTypeProd().toUpperCase());
        System.out.println("     Prix Produit :"+p.getPrix()+" €");
        System.out.println("     Stock Produit :"+p.getStock());
        System.out.println("******************************************************************");
        System.out.print("Quantite acheter (01 - 25) : ");
        do {
            qte = sc.nextLine();
            if (!ValideateExpression.isValideDecimal(qte)){
                System.out.println("Attention ! vous ne pouvez pas depasser la quantite totale comprise entre [01 - 25]");
                System.out.print("Quantite acheter (01 - 25) : ");
            }
        }while (!ValideateExpression.isValideDecimal(qte));
        int quantite = Integer.parseInt(qte);
        prixTotale = (float) (quantite*p.getPrix()*0.25);
        Achat achat = new Achat(p.getId(), quantite, prixTotale);
        objectOutputStream.writeObject(achat);
        String res = (String) objectInputStream.readObject();
        System.out.println("Enregistrement reussi");
    }

    /**
     *
     *
     * @param objectOutputStream
     * @param objectInputStream
     */
    static void findProductByCode(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Produit p;
        String codeProd;
        System.out.print("Veiller saisir le code du produit : ");
        do {
            do {
                codeProd = sc.nextLine();
                if (!ValideateExpression.isValideCodeProd(codeProd)){
                    System.out.println("Veiller respecter le format de chaine");
                    System.out.print("Veiller saisir le code du produit : ");
                }
            }while (!ValideateExpression.isValideCodeProd(codeProd));
            objectOutputStream.writeObject(codeProd);
            p = (Produit) objectInputStream.readObject();
            if (p==null) {
                System.out.println("Code produit n'existe pas");
                System.out.print("Veiller saisir un code produit valide : ");
            }
        }while (p==null);
        System.out.println("*****************************************************************");
        System.out.println("     ID :"+p.getId());
        System.out.println("     Nom Produit :"+p.getNomProd());
        System.out.println("     Code Produit :"+p.getCodeProd());
        System.out.println("     Type Produit :"+p.getTypeProd().toUpperCase());
        System.out.println("     Prix Produit :"+p.getPrix()+" €");
        System.out.println("     Stock Produit :"+p.getStock());
        System.out.println("******************************************************************");

    }


    /**
     *
     *
     * @param objectOutputStream
     * @param objectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void bilanVente(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ArrayList bilanVentes = (ArrayList<BilanVente>) objectInputStream.readObject();
        bilanVentes.forEach(bilanVente->{
            System.out.println(bilanVente.toString());
            System.out.println("");
            System.out.println("*****************************************************************");
        });
    }

    /**
     *
     *
     * @param objectOutputStream
     * @param objectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void bilanVenteJournalier(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ArrayList bilanVentes = (ArrayList<BilanVente>) objectInputStream.readObject();
        bilanVentes.forEach(bilanVente->{
            System.out.println(bilanVente.toString());
            System.out.println("");
            System.out.println("*****************************************************************");
        });
    }
}
