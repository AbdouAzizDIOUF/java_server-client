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

        String username, estOkay;
        String password, password2;
        //boolean isOkay;
        String isNotOkay;
        System.out.println("*****************************Veillez-vous identifier***************************");
        do {
            System.out.print("username: ");username = sc.nextLine().toLowerCase();
            System.out.print("Password: "); password = sc.nextLine().toLowerCase();

            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);

            estOkay = (String) objectInputStream.readObject();

            if (!estOkay.equals("Okay")){
                System.out.println("                             identifients Invalides!                  ");
                System.out.println("************************ Veillez saisir les bons identifients **********************");
            }
        }while (!estOkay.equals("Okay"));


        System.out.println("************************               Identification reussie             **********************");

        while (true){
            String option;
            System.out.println("*********************************************************************************************************************************************************************");
            System.out.println("******************************************                    Choisir une operation a effectuer            **********************************************************");
            do {
                System.out.println("                      1 - Effectuer une vente");
                System.out.println("                      2 - Effectuer une vente avec reduction '25%' Numero Carte vitale Valide ");
                System.out.println("                      3 - Rechercher Un Produit Avec son code");
                System.out.println("                      4 - Afficher la liste des produits vendus");
                System.out.println("                      5 - Afficher le Bilan de la vente "+ new Date());
                System.out.println("                      0 - Fermer La connexion");
                System.out.println("*********************************************************************************************************************************************************************");
                System.out.println("*********************************************************************************************************************************************************************");
                System.out.print("Donner votre choix : ");
                option = sc.next();
                switch (option) {
                    case "1":
                        objectOutputStream.writeObject(option);
                        Actions.ventdre(objectOutputStream, objectInputStream);
                        break;
                    case "2":
                        objectOutputStream.writeObject(option);
                        Actions.ventdreByReduc(objectOutputStream, objectInputStream);
                        break;
                    case "3":
                        objectOutputStream.writeObject(option);
                        Actions.findProductByCode(objectOutputStream, objectInputStream);
                        break;
                    case "4":
                        objectOutputStream.writeObject(option);
                        Actions.bilanVente(objectOutputStream, objectInputStream);
                        break;
                    case "5":
                        objectOutputStream.writeObject(option);
                        Actions.bilanVenteJournalier(objectOutputStream, objectInputStream);
                        break;
                    case "0": System.exit(-1);
                }
                if (!ValideateExpression.isInt(option)){
                    System.out.println("**********  Option non prise en compte! Veillez choisir une opion valide   ************");
                }
            }while (!ValideateExpression.isInt(option));
        }
    }


}
