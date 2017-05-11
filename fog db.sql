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
	Carport_ID int(7) AUTO_INCREMENT NOT NULL,
    Title varchar(50),
    UUID varchar(50) UNIQUE,
    Height int(10),
    Width int(10),
    Length int(10),
    CPType varchar(30),
    RoofType varchar(30),    
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
    Length int(15),
    Depth int(15),
    Width float(15),
    Material varchar(30),
    Type varchar(30),
    Box_AMT int(10),
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
  
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 240, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 300, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 360, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 420, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 480, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 540, 100, 100, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 600, 100, 100, 'Brædt', 'TrykImp', 100);
    
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 240, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 300, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 360, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 420, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 480, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 540, 45, 195, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 600, 45, 195, 'Brædt', 'TrykImp', 100);
    
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 240, 38, 73, 'Brædt', 'TrykImp', 100);  
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 300, 38, 73, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 360, 38, 73, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 420, 38, 73, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 480, 38, 73, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 540, 38, 73, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 600, 38, 73, 'Brædt', 'TrykImp', 100);

insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 240, 25, 150, 'Brædt', 'TrykImp', 100);   
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 300, 25, 150, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 360, 25, 150, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 420, 25, 150, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 480, 25, 150, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 540, 25, 150, 'Brædt', 'TrykImp', 100);
insert into Inventory(Inventory_Title, Length, Depth, Width, Material, Type, Price) values
	('TrykImp Brædt', 600, 25, 150, 'Brædt', 'TrykImp', 100);
 
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 240, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 300, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 360, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 420, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 480, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 540, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
	('Plastmo Ecolite Blåtonet', 60, 109, 'Plastmo', 'Ecolite Blåtonet', 100);
    
 insert into Inventory(Inventory_Title, Material, Type, Price) values
    ('Vinkelbeslag 35', 'Vinkelbeslag', '35', 10);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Price) values
    ('Bræddebolt', 120, 10, 'Bræddebolt', 'Bræddebolt', 10);
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Box_AMT, Price) values
    ('Skruer', 70, 4.5, 'Skruer', 'Skruer', 300, 10);  
 insert into Inventory(Inventory_Title, Length, Width, Material, Type, Box_AMT, Price) values
    ('Skruer', 60, 4.5, 'Skruer', 'Skruer', 200, 8); 
    
insert into Inventory(Inventory_Title, Length, Material, Type, Price) values
    ('Aluminium Cover', 100, 'Cover', 'Aluminium', 25); 
    
insert into User(Username, User_Pass) values
  ('Peter', 'Peter');
  
insert into User_Roles values
  (1, 3);
  
insert into UserInfo values
  (1, 'Peter', 'Peterson', 'Male', 'Petey@gmail.com');

insert into User_Has_UserInfo values
  (1, 1);
  

  
  
  
  commit;