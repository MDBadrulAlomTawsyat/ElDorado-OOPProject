package com.cse2214.eldorado;
//Badrul

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.Formatter;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MongoDB {
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    MongoCollection<Document> collectionbus;
    MongoCollection<Document> collectionedb;
    MongoCollection<Document> collectionadmin;
    FindIterable<Document> iterdoc, iterdocbus, iterdocedb,iterdocadmin;
    public MongoDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        client = MongoClients.create("mongodb+srv://ElDorado1:MAcmw0ldFbNTvLdU@eldorado1.wuakt.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        db = client.getDatabase("User");
        collection = db.getCollection("Database");
        collectionbus = db.getCollection("BUSDB");
        collectionedb = db.getCollection("EmergencyContactDatabase");
        collectionadmin = db.getCollection("Admin");
        iterdoc = collection.find(); // is a list of iterable document
        iterdocbus = collectionbus.find();
        iterdocedb = collectionedb.find();
        iterdocadmin = collectionadmin.find();
    }
    void mongoDB() {
        for (Document doc : iterdoc){
            System.out.print("\t\t\t\t\t\t|\t");
            System.out.format("%10s%25s",doc.get("User Number") + "\t|\t" , doc.get("Name")+"\t     |\n");
        }
    }
    void mongoDBBus() {
        for (Document doc : iterdocbus)
            System.out.println(doc.get("From_City") + "\t|\t" + doc.get("Fare"));
    }
    void mongoDBBusFinder(String From,String To) {
        int sum=1;
        for(Document doc : iterdocbus){
            if (doc.get("To").equals(To)){
                System.out.print("\n\t\t\t\t|");
                System.out.format("%5s%10s%10s%20s%6s%4s%4s", sum+"\t|", From, "-"+doc.get("To") + "\t|", doc.get("Bus") + "\t|",doc.get("Time")+ "\t|" , doc.get("Fare") + "\t|\t",doc.get("Fare")+"\t|");
                int Fare=1;//doc.get("Fare");
                sum++;
                new Transportation().fareGenerator(Fare);
            }
        }
    }
    void insertMongoDB(int User_Number, String... args) {
        Document doc = new Document("User Number", User_Number)
                .append("Name", args[0])
                .append("gender", args[1])
                .append("Passport Number", args[2])
                .append("Phone Number", args[3])
                .append("Email", args[4])
                .append("Card Number", args[5])
                .append("Visa ID", args[6])
                .append("City", args[7])
                .append("Country", args[8])
                .append("User_Name", args[9])
                .append("Passeord", args[10]); // 0-10
        collection.insertOne(doc);
    }
    void updateMongoDB(String Email, String password) {
        Document doc = collection.find(Filters.eq("Email", Email)).first();
        if (doc == null){
            System.out.println(Email+"Takrim Nai");
            return;
        }
        doc.replace("Passeword", password);
        collection.updateOne(Filters.eq("Email",Email),new Document("$set",doc));
    }
    public boolean passwordAuth(String Email, String password) {
        Document doc = collection.find(Filters.eq("Email", Email)).first();
        if (doc == null)
            return false;
        if (doc.get("Passeord").equals(password))
            return true;
        return false;
    }
    public boolean adminpasswordAuth(String user, String password) {
        Document doc = collectionadmin.find(Filters.eq("User", user)).first();
        if (doc == null)
            return false;
        if (doc.get("Pass").equals(password))
            return true;
        return false;
    }
    
    void mongoDBEmergencyDB() {
        for (Document doc : iterdocedb) {
            System.out.print("\t\t\t   |");
            System.out.format("%25s%20s%20s", doc.get("Country") + "\t|\t", doc.get("Category") + "\t|\t",
                    doc.get("Mobile") + "\t|\n");
        }
    }
    void mongoDBEmergencyDBFiler(String Country) {
        for (Document doc : iterdocedb){
            if (doc.get("Country").equals(Country)){
                System.out.print("\t\t\t   |");
                System.out.format("%25s%20s%20s", doc.get("Country") + "\t|\t", doc.get("Category") + "\t|\t",doc.get("Mobile") + "\t|\n");
            }
        }
    }

}

