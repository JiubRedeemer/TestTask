CREATE TABLE BANK
(
    IDBANK VARCHAR(255) NOT NULL PRIMARY KEY,
    NAME   VARCHAR(255)
);

CREATE TABLE CLIENT
(
    IDCLIENT     VARCHAR(255) NOT NULL PRIMARY KEY,
    FIO          VARCHAR(255),
    EMAIL        VARCHAR(255),
    NAME         VARCHAR(255),
    PASSPORT     VARCHAR(255),
    PATRONYMIC   VARCHAR(255),
    PHONE        VARCHAR(255),
    SURNAME      VARCHAR(255),
    IDBANKCLIENT VARCHAR(255),
    CONSTRAINT FK1X066RMXVR7MXE8ME6HPOWVNW FOREIGN KEY (IDBANKCLIENT) REFERENCES BANK (IDBANK)
);

CREATE TABLE CLIENTCREDIT
(
    IDCLIENTCREDIT VARCHAR(255) NOT NULL PRIMARY KEY,
    CLIENTFULLNAME VARCHAR(255),
    CREDITNAME     VARCHAR(255),
    CREDITSUM      BIGINT,
    PERCENT        DOUBLE,
    START          DATE,
    TIME           BIGINT,
    ID\u0421LIENT VARCHAR (255),
    IDCREDIT       VARCHAR(255),
    IDCLIENT       VARCHAR(255),
    CONSTRAINT FKGDBSMJI0PH7MI67OVJ2Y3TD6S FOREIGN KEY (ID\u0421LIENT) REFERENCES CLIENT (IDCLIENT),
    CONSTRAINT FKC96R4JQVW63ODRHQTIMAKXNM4 FOREIGN KEY (IDCLIENT) REFERENCES CLIENT (IDCLIENT)
);

CREATE TABLE PUBLIC.CREDIT
(
    IDCREDIT     VARCHAR(255) NOT NULL PRIMARY KEY,
    LIMIT        BIGINT,
    NAME         VARCHAR(255),
    PERCENT      DOUBLE,
    IDBANKCREDIT VARCHAR(255),
    CONSTRAINT FKIQJTJYR9CJLEUO84LG90TEOD2 FOREIGN KEY (IDBANKCREDIT) REFERENCES BANK (IDBANK)
);
