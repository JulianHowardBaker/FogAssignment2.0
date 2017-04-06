CREATE DATABASE IF NOT EXISTS fog;
USE fog;

drop table IF EXISTS ProductionLine, Order_Has_Inventory, Cart_Has_Inventory, Employee_Warehouse, Warehouse_Has_Inventory, Warehouse, Inventory, Cart, CreditCard, InvoiceHistory, Invoice, Orders, Address, Employee, User_Roles, Roles, UserSession, User, UserInfo;

create table UserInfo(
	UserInfo_ID int(7) AUTO_INCREMENT,
    Name varchar(15) NOT NULL,
    LastName varchar(15) NOT NULL,
    Gender enum('Male', 'Female'),
    Email varchar(30) UNIQUE NOT NULL,
    PRIMARY KEY(UserInfo_ID)
);

create table User(
	User_ID int(7) AUTO_INCREMENT,
	Username varchar(15) UNIQUE NOT NULL,
	User_Pass varchar(15) NOT NULL,
    Timestamp timestamp NOT NULL,
	Visits_AMT int(10),
    UserInfo_ID int(7),
	PRIMARY KEY(User_ID),
    FOREIGN KEY(UserInfo_ID) REFERENCES UserInfo(UserInfo_ID)
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
    UserInfo_ID int(7),
    PRIMARY KEY(EMPNO),
    FOREIGN KEY(UserInfo_ID) REFERENCES UserInfo(UserInfo_ID)
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
    Inventory_Title varchar(15) NOT NULL,
    Price int(7),
    PRIMARY KEY(Inventory_ID)
);

create table Warehouse(
	Warehouse_ID int(7) AUTO_INCREMENT,
    Warehouse_Title varchar(15) NOT NULL,
    Address_ID  int(7),
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

create table Cart_Has_Inventory(
	Cart_ID int(7),
    Inventory_ID int(7),
    Inventory_AMT int(7),
    FOREIGN KEY(Cart_ID) REFERENCES Cart(Cart_ID),
    FOREIGN KEY(Inventory_ID) REFERENCES Inventory(Inventory_ID)
);

create table Order_Has_Inventory(
	Order_ID int(7),
    Inventory_ID int(7),
    Inventory_AMT int(7),
    FOREIGN KEY(Order_ID) REFERENCES Orders(Order_ID),
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
  
  commit;