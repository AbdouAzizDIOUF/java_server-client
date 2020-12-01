import entity.Achat;
import entity.Produit;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * @author abdou
 */
public class Principale extends JFrame{

    static Scanner sc = new Scanner(System.in);



    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        Socket socket;

        socket = new Socket("localhost",8888);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());;
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        String username, username2;
        String password, password2;
        //boolean isOkay;
        String isNotOkay;
        System.out.println("*****************************Veillez-vous identifier***************************");
        do {
            System.out.print("username: ");username = sc.next().toLowerCase();
            objectOutputStream.writeObject(username);

            System.out.print("Password: "); password = sc.next().toLowerCase();
            objectOutputStream.writeObject(password);

            username2 = (String) objectInputStream.readObject();
            password2 = (String) objectInputStream.readObject();

            if (!username2.equals("admin") || !password2.equals("admin")){
                System.out.println("                             identifients Invalides!                  ");
                System.out.println("************************ Veillez saisir les bons identifients **********************");
            }
        }while (!username2.equals("admin") || !password2.equals("admin"));


        System.out.println("************************               Identification reussie             **********************");

        while (true){
            String option;
            System.out.println("*********************************************************************************************************************************************************************");
            System.out.println("******************************************                    Choisir une operation a effectuer            **********************************************************");
            do {
                System.out.println("                      1 - Effectuer une vente");
                System.out.println("                      2 - Effectuer une vente avec reduction '25%' Numero Carte vitale Valide ");
                System.out.println("                      3 - Rechercher Un Produit Avec son code");
                System.out.println("                      4 - Afficher la liste des produits vendus "+ new Date());
                System.out.println("                      5 - Afficher le Bilan de la vente "+ new Date());
                System.out.println("                      0 - Fermer La connexion");
                System.out.println("*********************************************************************************************************************************************************************");
                System.out.println("*********************************************************************************************************************************************************************");
                System.out.print("Donner votre choix : ");
                option = sc.next();
                switch (option){
                    case "1":
                        ventdre(objectOutputStream, objectInputStream);
                        break;
                    case "2":
                        System.out.println("Vous avez choisi d'effectuer une recherche");
                        break;
                    case "0": System.exit(-1);
                }
                if (!ValideateExpression.isInt(option)){
                    System.out.println("**********  Option non prise en compte! Veillez choisir une opion valide   ************");
                }
            }while (!ValideateExpression.isInt(option));
        }
    }


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
                    status = 0;
                }else {
                    status = 1;
                }
            }while (status == 0);
            System.out.println("*****************************************************************");
            System.out.println("     ID :"+p.getId());
            System.out.println("     Nom Produit :"+p.getNomProd());
            System.out.println("     Code Produit :"+p.getCodeProd());
            System.out.println("     Type Produit :"+p.getTypeProd().toUpperCase());
            System.out.println("     Prix Produit :"+p.getPrix()+" €");
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



}
