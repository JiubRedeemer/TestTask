package com.haulmont.testtask.createTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTables {
    public static void create() {

        Connection con = null;
        Statement stmt = null;
        int result = 0;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("DROP TABLE CLIENT;DROP TABLE Bank ");
//
            result = stmt.executeUpdate("    CREATE TABLE Bank (\n" +
                    "        idBank VARCHAR(255) NOT NULL,\n" +
                    "        name VARCHAR(255) NOT NULL,\n" +
                    "        CONSTRAINT idBank PRIMARY KEY (idBank)\n" +
                    "        );");
            result = stmt.executeUpdate("CREATE TABLE Client (\n" +
                    "        idClient VARCHAR(255) NOT NULL," +
                    "        idBank VARCHAR(255),\n" +
                    "        name VARCHAR(255) NOT NULL,\n" +
                    "        surname VARCHAR(255) NOT NULL,\n" +
                    "        patronymic VARCHAR(255) NOT NULL,\n" +
                    "        phone VARCHAR(255) NOT NULL,\n" +
                    "        email VARCHAR(255) NOT NULL,\n" +
                    "        passport VARCHAR(255) NOT NULL,\n" +
                    "        CONSTRAINT pk_idClient PRIMARY KEY (idClient),\n" +
                    "        CONSTRAINT fk_idBank FOREIGN KEY (idBank) REFERENCES Bank(idBank)" +
                    "        );");
            result = stmt.executeUpdate("CREATE TABLE Credit (\n" +
                    "        idCredit VARCHAR(255) NOT NULL,\n" +
                    "        idBank VARCHAR(255),        " +
                    "        name VARCHAR(255) NOT NULL,\n" +
                    "        limit BIGINT NOT NULL,\n" +
                    "        percent FLOAT NOT NULL,\n" +
                    "        CONSTRAINT pk_idCredit PRIMARY KEY (idCredit),\n" +
                    "        CONSTRAINT fk_idCreditBank FOREIGN KEY (idBank) REFERENCES Bank(idBank)" +
                    "        );");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println("Table created successfully");
    }
}


//    CREATE TABLE 'Bank' (
//        'id' VARCHAR(255) NOT NULL,
//        'name' VARCHAR(255) NOT NULL,
//        PRIMARY KEY ('id')
//        );

//    CREATE TABLE 'Client' (
//        'id' VARCHAR(255) NOT NULL,
//        'name' VARCHAR(255) NOT NULL,
//        'surname' VARCHAR(255) NOT NULL,
//        'patronymic' VARCHAR(255) NOT NULL,
//        'phone' VARCHAR(255) NOT NULL,
//        'email' VARCHAR(255) NOT NULL,
//        'passport' VARCHAR(255) NOT NULL,
//        'idBank' VARCHAR(255) NOT NULL,
//        PRIMARY KEY ('id')
//
//        );


/*   CREATE TABLE 'ClientInBank' (
        'idBank' VARCHAR(255) NOT NULL,
        'idClient' VARCHAR(255) NOT NULL,
        PRIMARY KEY ('idBank'),
         FOREIGN KEY('idClient') REFERENCES Client('idClient') ON DELETE CASCADE
        );*/

//    CREATE TABLE 'Credit' (
//        'id' VARCHAR(255) NOT NULL,
//        'name' VARCHAR(255) NOT NULL,
//        'limit' BIGINT NOT NULL,
//        'percent' FLOAT NOT NULL,
//        PRIMARY KEY ('id'),
//        'idBank' VARCHAR(255) NOT NULL
//        );

/*    CREATE TABLE 'CreditsInBank' (
       'idBank' VARCHAR(255) NOT NULL,
        'idCredit' VARCHAR(255) NOT NULL,
         FOREIGN KEY('idCredit') REFERENCES Credit('idCredit') ON DELETE CASCADE
         FOREIGN KEY('idBank') REFERENCES Bank('idBank') ON DELETE CASCADE

        );*/

//    CREATE TABLE 'CreditInfo' (
//        'id' VARCHAR(255) NOT NULL,
//        'idCredit' VARCHAR(255) NOT NULL,
//        'idClient' VARCHAR(255) NOT NULL,
//        'idPayments' VARCHAR(255) NOT NULL,
//        PRIMARY KEY ('id'),
//         FOREIGN KEY('idCredit') REFERENCES Credit('idCredit') ON DELETE CASCADE,
//         FOREIGN KEY('idClient') REFERENCES Client('idClient) ON DELETE CASCADE,
//         FOREIGN KEY('idPayments') REFERENCES Payments('idPayments') ON DELETE CASCADE

//        );

//    CREATE TABLE 'Payments' (
//        'id' VARCHAR(255) NOT NULL,
//        'date' VARCHAR(255) NOT NULL,
//        'sum' BIGINT NOT NULL,
//        'sumPayBody' BIGINT NOT NULL,
//        'sumPayPercents' BIGINT NOT NULL,
//        'balanceOwed' BIGINT NOT NULL,
//        PRIMARY KEY ('id')
//        );

//result = stmt.executeUpdate("CREATE TABLE client(\n" +
//        "    id VARCHAR(255) not null primary key,\n" +
//        "    name VARCHAR(255),\n" +
//        "    secname VARCHAR(255),\n" +
//        "    patronymic VARCHAR(255),\n" +
//        "    phone_number VARCHAR(255),\n" +
//        "    email VARCHAR(255),\n" +
//        "    pasport VARCHAR(255)\n" +
//        "    )");