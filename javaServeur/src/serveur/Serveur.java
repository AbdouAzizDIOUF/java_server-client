package serveur;

import dao.AccesBD;
import entity.Achat;
import entity.Personne;
import entity.Produit;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Serveur extends Thread{
    private int nbrClient=0;



    @Override
    public void run(){
        try {
            ServerSocket serversocket  = new ServerSocket(8888);
            System.out.println("Demarrage du serveur ..............");
            while (true) {
                Socket socket = serversocket.accept();
                new Service(socket, nbrClient).start();
                ++nbrClient;
                System.out.println("le numero client est : "+nbrClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class Service extends Thread
    {
        private Socket sock;
        private int numeroClient;
        private AccesBD bdd;

        public Service(Socket sock, int numeroClient){
            this.sock = sock;
            this.numeroClient = numeroClient;
            bdd = new AccesBD();
        }

        @Override
        public void run()
        {
            boolean isOkay = false;
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(sock.getOutputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
                String IP = sock.getRemoteSocketAddress().toString();
                System.out.println("Connexion du client IP : "+IP);
                do {
                    String username = (String) objectInputStream.readObject();
                    System.out.println("username : "+username);
                    String password = (String) objectInputStream.readObject();
                    System.out.println("password : "+password);
                    isOkay = bdd.authentifier(username, password);

                    if (isOkay){
                        objectOutputStream.writeObject("Okay");
                    }else {
                        objectOutputStream.writeObject("notOkay");
                    }
                    objectOutputStream.flush();
                }while (!isOkay);
                Produit p;
                String option;
                while (true) {
                    option = (String) objectInputStream.readObject();
                    System.out.println(option);
                    switch (option)
                    {
                        case "1":
                            vente(objectOutputStream, objectInputStream);
                            break;
                        case "2":
                            venteWichNumCarteVitale(objectOutputStream, objectInputStream);
                            break;
                        case "3":
                            findProductByCode(objectOutputStream, objectInputStream);
                            break;
                        case "4":
                            bilanVente(objectOutputStream, objectInputStream);
                            break;
                        case "5":
                            bilanVentejournalier(objectOutputStream, objectInputStream);
                            break;
                    }

                }
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }



        }


        /**
         *
         * @param objectOutputStream
         * @param objectInputStream
         * @throws SQLException
         * @throws IOException
         * @throws ClassNotFoundException
         */
        void vente(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws SQLException, IOException, ClassNotFoundException {
            Produit p;
            int status;
            do {
                String codeProduct = (String) objectInputStream.readObject();
                System.out.println(codeProduct);
                p = bdd.findProductByCode(codeProduct);
                objectOutputStream.writeObject(p);
                if (p!=null) {
                    status=20;
                }else {
                    status = 1;
                }
                System.out.println(status);
            }while (p==null);
            Achat achat = (Achat) objectInputStream.readObject();
            System.out.println("idPod: "+achat.getProduit() +"\n"+
                    "prixTotale: "+achat.getPrixTotal()+"\n"+
                    "quantity: "+achat.getQuantity());

            String result = bdd.saveAchat(achat.getProduit(), achat.getPrixTotal(), achat.getQuantity());
            if ("Okay".equals(result)){
                System.out.println(result);
                objectOutputStream.writeObject(result);
            }
        }


        /**
         *
         * @param objectOutputStream
         * @param objectInputStream
         * @throws SQLException
         * @throws IOException
         * @throws ClassNotFoundException
         */
        void venteWichNumCarteVitale(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws SQLException, IOException, ClassNotFoundException
        {
            Produit p;
            Personne personne;
            int status;
            do {
                String codeProduct = (String) objectInputStream.readObject();
                System.out.println(codeProduct);
                p = bdd.findProductByCode(codeProduct);
                objectOutputStream.writeObject(p);
                if (p!=null) {
                    status=20;
                }else {
                    status = 1;
                }
                System.out.println(status);
            }while (p==null);
            do {
                String numSec = (String) objectInputStream.readObject();
                System.out.println(numSec);
                personne = (Personne) bdd.verifCarteVitale(numSec);
                objectOutputStream.writeObject(personne);
            }while (personne==null);
            Achat achat = (Achat) objectInputStream.readObject();
            System.out.println("idPod: "+achat.getProduit() +"\n"+
                    "prixTotale: "+achat.getPrixTotal()+"\n"+
                    "quantity: "+achat.getQuantity());

            String result = bdd.saveAchat(achat.getProduit(), achat.getPrixTotal(), achat.getQuantity());
            if ("Okay".equals(result)){
                System.out.println(result);
                objectOutputStream.writeObject(result);
            }
        }

        /**
         *
         * @param objectOutputStream
         * @param objectInputStream
         */
        void findProductByCode(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            Produit produit;
            int status;
            do {
                String codeProduct = (String) objectInputStream.readObject();
                System.out.println(codeProduct);
                produit = bdd.findProductByCode(codeProduct);
                objectOutputStream.writeObject(produit);
            }while (produit==null);
        }


        /**
         *
         * @param objectOutputStream
         * @param objectInputStream
         * @throws SQLException
         * @throws IOException
         */
        void bilanVente(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws SQLException, IOException {
            objectOutputStream.writeObject(bdd.bilanProductVendus());
        }

        /**
         *
         * @param objectOutputStream
         * @param objectInputStream
         * @throws SQLException
         * @throws IOException
         */
        void bilanVentejournalier(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws SQLException, IOException {
            objectOutputStream.writeObject(bdd.bilanJournalierProductVendus());
        }

    }

}
