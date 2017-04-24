CREATE DATABASE IF NOT EXISTS fog;
USE fog;

drop table IF EXISTS User_Has_UserInfo, Orders_Has_Carport, ProductionLine, Carport_Has_Inventory, Employee_Warehouse, Warehouse_Has_Inventory, Warehouse, Inventory, Cart, CreditCard, InvoiceHistory, Invoice, Orders, Address, Employee, User_Roles, Roles, UserSession, User, UserInfo, Carport;

create table UserInfo(
	UserInfo_ID int(7) AUTO_INCREMENT,
    Name varchar(15) NOT NULL,
    LastName varchar(15) NOT NULL,
    Gender enum('Male', 'Female'),
    Email varchar(30) UNIQUE NOT NULL,
    PRIMARY KEY(UserInfo_ID)
);

create table Carport(
	Carport_ID int(7),
    Title varchar(50),
    UUID varchar(30) UNIQUE,
    PRIMARY KEY(Carport_ID)
);

create table User(
	User_ID int(7) AUTO_INCREMENT NOT NULL,
	Username varchar(15) UNIQUE NOT NULL,
	User_Pass varchar(15) NOT NULL,
    Timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Visits_AMT int(10) default 0,
	PRIMARY KEY(User_ID)
);

create table User_Has_UserInfo(
	UserInfo_ID int(7),
    User_ID int(7), 
    FOREIGN KEY(UserInfo_ID) REFERENCES UserInfo(UserInfo_ID),
    FOREIGN KEY(User_ID) REFERENCES User(User_ID)
);

create table UserSession(
	Session_ID int(7) AUTO_INCREMENT,
    Clicks int(15),
    Timestamp timestamp,
    IP varchar(15) NOT NULL,
    User_ID int(7),
    PRIMARY KEY(Session_ID),
    FOREIGN KEY(User_ID) REFERENCES User(User_ID)
);

create table Roles(
	Role_ID int(7) AUTO_INCREMENT,
    Role_Name varchar(15) UNIQUE NOT NULL,
    PRIMARY KEY(Role_ID)
);

create table User_Roles(
	User_ID int(7),
    Role_ID int(7),
    FOREIGN KEY(User_ID) REFERENCES User(User_ID),
    FOREIGN KEY(Role_ID) REFERENCES Roles(Role_ID)
);

create table Employee(
	EMPNO int(7),
    Hire_Date date,
    Work_DEPT varchar(15) NOT NULL,
    PRIMARY KEY(EMPNO)
);

create table Address(	
	Address_ID int(7) AUTO_INCREMENT,
    Street varchar(20) NOT NULL,
    Zip int(7),
    City varchar(15) NOT NULL,
    State varchar(15) NOT NULL,
    Country varchar(15) NOT NULL,
    PRIMARY KEY(Address_ID)
);

create table Orders(
	Order_ID int(7) AUTO_INCREMENT,
    Order_Date date,
    Total_Price int(10),
    User_ID int(7),
    Status enum('Pending', 'Cancelled', 'Confirmed'),
	PRIMARY KEY(Order_ID),
	FOREIGN KEY(User_ID) REFERENCES User(User_ID)
);

create table Invoice(
	Invoice_ID int(7) AUTO_INCREMENT,
    Invoice_Date date,
    Order_ID int(7),
    PRIMARY KEY(Invoice_ID),
    FOREIGN KEY(Order_ID) REFERENCES Orders(Order_ID)
);

create table InvoiceHistory(
    InvoiceHistory_ID int(7) AUTO_INCREMENT,
	Invoice_ID int(7),
    Paid_Date date,
    Comments varchar(100) NOT NULL,
    PRIMARY KEY(InvoiceHistory_ID),
    FOREIGN KEY(Invoice_ID) REFERENCES Invoice(Invoice_ID)
);

create table CreditCard(
	CreditcardNO int(15),
    Address_ID int(7),
    Owner_Name varchar(30) NOT NULL,
    CCNo int(7),
    Expiration date,
    PRIMARY KEY(CreditcardNO),
    FOREIGN KEY(Address_ID) REFERENCES Address(Address_ID)
);

create table Cart(
	Cart_ID int(7) AUTO_INCREMENT,
    Session_ID int(7),
    PRIMARY KEY(Cart_ID),
    FOREIGN KEY(Session_ID) REFERENCES UserSession(Session_ID)
);

create table Inventory(
	Inventory_ID int(7) AUTO_INCREMENT,
    Inventory_Title varchar(50) NOT NULL,
    Length int(10),
    Price int(7),
    PRIMARY KEY(Inventory_ID)
);

create table Warehouse(
	Warehouse_ID int(7) AUTO_INCREMENT,
    Warehouse_Title varchar(15) NOT NULL,
    Address_ID  int(7),
    RoofType enum('Flat', 'Standard'),
    PRIMARY KEY(Warehouse_ID),
    FOREIGN KEY(Address_ID) REFERENCES Address(Address_ID)
);

create table Warehouse_Has_Inventory(
	Warehouse_ID int(7),
    Inventory_ID int(7),
    Inventory_AMT int(7),
    FOREIGN KEY(Warehouse_ID) REFERENCES Warehouse(Warehouse_ID),
    FOREIGN KEY(Inventory_ID) REFERENCES Inventory(Inventory_ID)
);

create table Orders_Has_Carport(
	Carport_ID int(7),
    Order_ID int(7),
    FOREIGN KEY(Carport_ID) REFERENCES Carport(Carport_ID),
    FOREIGN KEY(Order_ID) REFERENCES Orders(Order_ID)
);
create table Carport_Has_Inventory(
	Carport_ID int(7),
    Inventory_ID int(7),
    Inventory_AMT int(7),
    FOREIGN KEY(Carport_ID) REFERENCES Carport(Carport_ID),
    FOREIGN KEY(Inventory_ID) REFERENCES Inventory(Inventory_ID)
);

create table Employee_Warehouse(
	Warehouse_ID int(7),
    EMPNO int(7),
    FOREIGN KEY(Warehouse_ID) REFERENCES Warehouse(Warehouse_ID),
    FOREIGN KEY(EMPNO) REFERENCES Employee(EMPNO)
);

create table ProductionLine(
	ProductionLine_ID int(7),
    EMPNO int(7),
    Order_ID int(7),
    Completed date,
    Date date,
    PRIMARY KEY(ProductionLine_ID),
    FOREIGN KEY(EMPNO) REFERENCES Employee(EMPNO)
);

insert into Roles values
  (1,'Admin');
insert into Roles values
  (2,'Employee');
insert into Roles values
  (3,'Customer');
  
insert into Inventory values
  (1, '25x200 mm. trykimp. Brædt', 360, 50);
insert into Inventory values
  (2, '25x200 mm. trykimp. Brædt', 540, 50);
insert into Inventory values
  (3, '25x125 mm. trykimp. Brædt', 360, 50);
insert into Inventory values
  (4, '25x125 mm. trykimp. Brædt', 540, 50);
insert into Inventory values
  (5, '38x73 mm. Lægte ubh.', 420, 50);
insert into Inventory values
  (6, '45x95 mm. Reglar ub.', 270, 50);
insert into Inventory values
  (7, '45x95 mm. Reglar ub.', 240, 50);
insert into Inventory values
  (8, '45x195 mm. spærtræ ubh.', 600, 50);
insert into Inventory values
  (9, '45x195 mm. spærtræ ubh.', 480, 50);
insert into Inventory values
  (10, '97x97 mm. trykimp. Stolpe', 300, 50);
insert into Inventory values
  (11, '19x100 mm. trykimp. Brædt', 210, 50);
insert into Inventory values
  (12, '19x100mm trykimp. Brædt', 540, 50);
insert into Inventory values
  (13, '19x100mm trykimp. Brædt', 360, 50);
insert into Inventory values
  (14, 'Plastmo Ecolite blåtonet', 600, 50);
insert into Inventory values
  (15, 'Plastmo Ecolite blåtonet', 360, 50);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (16, 'Plastmo bundskruer 200 stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (17, 'hulbånd 1x20mm. 10 mtr.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (18, 'universal 190mm højre', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (19, 'universal 190mm venstre', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (20, '4,5x60 mm. skruer 200 stk', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (21, '4,0x50mm. beslagskruer 250 stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (22, 'bræddebolt 10x120mm.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (23, 'firkantskiver 40x40x11mm.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (24, '4,5x70mm. Skruer 400stk', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (25, '4,5x50mm. Skruer 300stk', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (26, 'stalddørsgreb 50x75', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (27, 't hængsel 390 mm.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (28, 'vinkelbeslag 35', 20);
  
insert into Inventory values
  (29, '25x150 mm. trykimp. Bræt', 480, 50);
insert into Inventory values
  (30, '25x150 mm. trykimp. Bræt', 600, 50);
insert into Inventory values
  (31, '25x150 mm. trykimp Bræt', 540, 50);
insert into Inventory (Inventory_ID, Inventory_Title, Price) values
  (32, 'fædigskåret (byg-selv spær)', 50);
insert into Inventory values
  (33, '45x95 Reglar ubh.', 240, 50);
insert into Inventory values
  (34, '45x95 Reglar ubh', 360, 50);
insert into Inventory values
  (35, '19x100 mm. trykimp. Bræt', 480, 50);
insert into Inventory values
  (36, '19x100 mm. trykimp. Bræt', 240, 50);
insert into Inventory values
  (37, '25x50 mm. trykimp. Bræt', 540, 50);
insert into Inventory values
  (38, '38x73 mm. taglægte T1', 540, 50);
insert into Inventory values
  (39, '38x73 mm. taglægte T1', 420, 50);
  
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (40, 'B & C Dobbelt -s sort', 30); 
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (41, 'B & C Rygsten sort', 30);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (42, 'B & C Toplægte holder', 30);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (43, 'B & C rygstensbeslag.', 30);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (44, 'B & C tagstens bindere & nakkekroge', 30);
  
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (45, 'Staldørsgreb 50x75', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (46, 'vinkelbeslag', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (47, '4,5 x 60 mm. Skruer 200stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (48, '5,0 x 40 mm. beslagskruer  250stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (49, '5,0 x 100 mm. skruer 100 stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (50, 'firkantskiver 40x40x11mm', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (51, '4,5 x 70 mm. Skruer 200stk.', 20);
insert into Inventory(Inventory_ID, Inventory_Title, Price) values
  (52, '4,5 x 50mm. Skruer 350stk.', 20);
  
insert into Carport (Carport_ID, Title) values
  (1, 'Standard Cow Shed');
insert into Carport (Carport_ID, Title) values
  (2, 'Enterprise Edition Shed');
  
insert into Carport_Has_Inventory values
  (1, 1, 4);
insert into Carport_Has_Inventory values
  (1, 2, 4);
insert into Carport_Has_Inventory values
  (1, 3, 2);
insert into Carport_Has_Inventory values
  (1, 4, 4);
insert into Carport_Has_Inventory values
  (1, 5, 1);
insert into Carport_Has_Inventory values
  (1, 6, 12);
insert into Carport_Has_Inventory values
  (1, 7, 4);
insert into Carport_Has_Inventory values
  (1, 8, 17);
insert into Carport_Has_Inventory values
  (1, 9, 1);
insert into Carport_Has_Inventory values
  (1, 10, 11);
insert into Carport_Has_Inventory values
  (1, 11, 200);
insert into Carport_Has_Inventory values
  (1, 12, 4);
insert into Carport_Has_Inventory values
  (1, 13, 2);
insert into Carport_Has_Inventory values
  (1, 14, 6);
insert into Carport_Has_Inventory values
  (1, 15, 6);
insert into Carport_Has_Inventory values
  (1, 16, 3);
insert into Carport_Has_Inventory values
  (1, 17, 2);
insert into Carport_Has_Inventory values
  (1, 18, 15);
insert into Carport_Has_Inventory values
  (1, 19, 15);
insert into Carport_Has_Inventory values
  (1, 20, 1);
insert into Carport_Has_Inventory values
  (1, 21, 3);
insert into Carport_Has_Inventory values
  (1,22, 18);
insert into Carport_Has_Inventory values
  (1, 23, 12);
insert into Carport_Has_Inventory values
  (1, 24, 2);
insert into Carport_Has_Inventory values
  (1, 25, 2);
insert into Carport_Has_Inventory values
  (1, 26, 1);
insert into Carport_Has_Inventory values
  (1, 27, 2);
insert into Carport_Has_Inventory values
  (1, 28, 32);

insert into carport_has_inventory values
  (2, 29, 2);
insert into carport_has_inventory values
  (2, 30, 2);
insert into carport_has_inventory values
  (2, 31, 1);
insert into carport_has_inventory values
  (2, 32, 1);
insert into carport_has_inventory values
  (2, 10, 9);
insert into carport_has_inventory values
  (2, 9, 3);
insert into carport_has_inventory values
  (2, 33, 4);
insert into carport_has_inventory values
  (2, 34, 6);
insert into carport_has_inventory values
  (2, 35, 2);
insert into carport_has_inventory values
  (2, 36, 38);
insert into carport_has_inventory values
  (2, 11, 148);
insert into carport_has_inventory values
  (2, 37, 3);
insert into carport_has_inventory values
  (2, 38, 1);
insert into carport_has_inventory values
  (2, 39, 23);

insert into carport_has_inventory values
  (2, 40, 300);
insert into carport_has_inventory values
  (2, 41, 21);
insert into carport_has_inventory values
  (2, 42, 8);
insert into carport_has_inventory values
  (2, 43, 21);
insert into carport_has_inventory values
  (2, 44, 2);
  
insert into carport_has_inventory values
  (2, 18, 8);
insert into carport_has_inventory values
  (2, 19, 8);
insert into carport_has_inventory values
  (2, 45, 1);
insert into carport_has_inventory values
  (2, 27, 2);
insert into carport_has_inventory values
  (2, 46, 20);
insert into carport_has_inventory values
  (2, 47, 1);
insert into carport_has_inventory values
  (2, 48, 1);
insert into carport_has_inventory values
  (2, 49, 2);
insert into carport_has_inventory values
  (2, 22, 20);
insert into carport_has_inventory values
  (2, 50, 20);
insert into carport_has_inventory values
  (2, 51, 3);
insert into carport_has_inventory values
  (2, 52, 2);
  
insert into User(Username, User_Pass) values
  ('Peter', 'Peter');
  
insert into User_Roles values
  (1, 3);
  
insert into UserInfo values
  (1, 'Peter', 'Peterson', 'Male', 'Petey@gmail.com');

insert into User_Has_UserInfo values
  (1, 1);
  

  
  
  
  commit;