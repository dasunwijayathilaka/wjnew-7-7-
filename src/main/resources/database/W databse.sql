-- Create database
CREATE DATABASE WijesingheGrocery;


-- Use the database
USE WijesingheGrocery;


-- Create table for Users
CREATE TABLE Users (
    U_ID VARCHAR(50) PRIMARY KEY,
    U_name VARCHAR(50) NOT NULL,
    U_Password VARCHAR(100) NOT NULL,
    Role VARCHAR(50) NOT NULL,
    U_Email VARCHAR(100) UNIQUE,
    U_Address VARCHAR(255),
    U_Contact_Number VARCHAR(20)
);



-- Insert sample values into Users table
INSERT INTO Users (U_ID, U_name, U_Password, Role, U_Email, U_Address, U_Contact_Number) 
VALUES 
    ('U001','JohnDoe', 'password123', 'Customer', 'john.doe@example.com', '123 Main St, Anytown', '123-456-7890'),
    ('U002','JaneSmith', 'password456', 'Admin', 'jane.smith@example.com', '456 Elm St, Anytown', '987-654-3210');
    ('U003','AliceJones', 'qwerty', 'Customer', 'alice.jones@example.com', '789 Oak St, Anytown', NULL),
    ('U004','BobBrown', 'browny', 'Customer', 'bob.brown@example.com', NULL, NULL);




-- Create table for Customers
CREATE TABLE Customers (
    C_ID  VARCHAR(50) PRIMARY KEY ,
    C_Name VARCHAR(50) NOT NULL,
    C_Address VARCHAR(55),
    C_Email VARCHAR(50) UNIQUE,
    C_Contact_Number VARCHAR(20),
    L_Points INT(30)
);


-- Insert sample values into Customers table
INSERT INTO Customers ( C_ID ,C_Name, C_Address, C_Email, C_Contact_Number, L_Points) 
VALUES 
    ('C001','John Doe', '123 Main St, Anytown', 'john.doe@example.com', '123-456-7890', 50),
    ('C002','Jane Smith', '456 Elm St, Anytown', 'jane.smith@example.com', '987-654-3210', 100);
    ('C003''Alice Jones', '789 Oak St, Anytown', 'alice.jones@example.com', NULL, 25),
    ('C004''Bob Brown', NULL, 'bob.brown@example.com', NULL, 75);



-- Create table for Payments
CREATE TABLE Payments (
    P_ID VARCHAR(50) PRIMARY KEY ,
    P_Type VARCHAR(50) NOT NULL,
    P_Amount DECIMAL(10, 2) NOT NULL,
    Transaction_Date DATE NOT NULL,
    Transaction_Time TIME NOT NULL,
    Card_Type VARCHAR(50),
    Billing_Address VARCHAR(255)
);


-- Insert sample values into Payments table
INSERT INTO Payments ( P_ID , P_Type, P_Amount, Transaction_Date, Transaction_Time, Card_Type, Billing_Address) 
VALUES 
    ('P001','Credit Card', 50.00, '2024-04-09', '12:30:00', 'Visa', '123 Main St, Anytown'),
    ('P002','Cash', 75.50, '2024-04-09', '14:15:00', NULL, NULL);
   
 ('Debit Card', 100.25, '2024-04-09', '16:45:00', 'Mastercard', '456 Elm St, Anytown'),
    ('Credit Card', 30.75, '2024-04-09', '18:20:00', 'American Express', '789 Oak St, Anytown');



-- Create table for Discounts
CREATE TABLE Discounts (
    D_ID VARCHAR(50) PRIMARY KEY ,
    D_Name VARCHAR(100) NOT NULL,
    D_Amount DECIMAL(10, 2) NOT NULL,
    V_Period VARCHAR(50),
    Grocery_Categories VARCHAR(255),
    D_Start_Date DATE NOT NULL,
    D_End_Date DATE NOT NULL
);


-- Insert sample values into Discounts table
INSERT INTO Discounts ( D_ID , D_Name,  D_Amount, V_Period, Grocery_Categories, D_Start_Date, D_End_Date) 
VALUES 
    ('D001','Spring Sale',  10.00, 'Weekly', 'Fruits, Vegetables', '2024-04-01', '2024-04-07'),
    ('D002','Weekend Special', 25.00, 'One-time', 'All', '2024-04-06', '2024-04-07');
  
  ('Back to School',  15.00, 'Monthly', 'School Supplies', '2024-08-01', '2024-08-31'),
    ('Holiday Discount',  50.00, 'One-time', 'All', '2024-12-24', '2024-12-25');





-- Create table for Stores
CREATE TABLE Stores (
    St_ID VARCHAR(50) PRIMARY KEY ,
    St_Name VARCHAR(100) NOT NULL,
    Floor_Plan VARCHAR(255),
    Description TEXT,
    Location VARCHAR(100),
    Qty_On_Hand INT(50)
);



-- Insert sample values into Stores table
INSERT INTO Stores ( St_ID, St_Name, Floor_Plan, Description, Location, Qty_On_Hand) 
VALUES 
    ('St001','SuperMart', 'FloorPlan_SuperMart.jpg', 'Large supermarket offering a wide range of products.', '123 Main St, Anytown', 500),
    ('St002','FreshGro', 'FloorPlan_FreshGro.png', 'Specializes in fresh produce and organic groceries.', '456 Elm St, Anytown', 300);
    

('ConvenienceCorner', NULL, 'Small convenience store offering everyday essentials.', '789 Oak St, Anytown', 150),
    ('Gourmet Delights', 'FloorPlan_GourmetDelights.pdf', 'Upscale store offering gourmet foods and specialty items.', '321 Pine St, Anytown', 200);






-- Create table for Suppliers
CREATE TABLE Suppliers (
    S_ID VARCHAR(10) PRIMARY KEY ,
    S_Name VARCHAR(100) NOT NULL,
    S_Address VARCHAR(255),
    S_Email VARCHAR(100) UNIQUE,
    S_Contact_Number VARCHAR(20),
    Delivery_Schedule VARCHAR(100)
);


-- Insert sample values into Suppliers table
INSERT INTO Suppliers ( S_ID, S_Name, S_Address, S_Email, S_Contact_Number, Delivery_Schedule) 
VALUES 
    ('S001','Fresh Produce Co.', '123 Market St, Anytown', 'info@freshproduce.com', '123-456-7890', 'Monday, Wednesday, Friday'),
    ('S002','Dairy Delights Ltd.', '456 Dairy Ln, Anytown', 'info@dairydelights.com', NULL, 'Tuesday, Thursday');
   
 ('Bakery Supplies Inc.', '789 Baker St, Anytown', 'info@bakerysupplies.com', '987-654-3210', 'Monday to Friday'),
    ('Meat Masters LLC', '321 Butcher Ave, Anytown', 'info@meatmasters.com', '555-555-5555', 'Monday, Wednesday, Friday');






-- Create table for Orders


CREATE TABLE Orders (
     O_ID VARCHAR(10) PRIMARY KEY ,
     U_ID VARCHAR(50) ,
	C_ID  VARCHAR(50) ,
 	D_ID VARCHAR(50),
   	P_ID VARCHAR(50),
    	L_Points_Earned INT(30),
  	O_Time TIME NOT NULL,
   	 O_Date DATE NOT NULL,
   	 Total_Amount DECIMAL(10, 2),
   	 Status VARCHAR(50),
    
FOREIGN KEY (U_ID) REFERENCES Users(U_ID),
    FOREIGN KEY (C_ID) REFERENCES Customers(C_ID),
    FOREIGN KEY (D_ID) REFERENCES Discounts(D_ID),
    FOREIGN KEY (P_ID) REFERENCES Payments(P_ID)
);







-- Create table for Items


CREATE TABLE Items (
    I_ID VARCHAR(50) PRIMARY KEY ,
    I_Name VARCHAR(100) NOT NULL,
    Brands VARCHAR(100),
    Qty INT (50),
    Description TEXT,
    Weight DECIMAL(10, 2),
    St_ID VARCHAR(50),
    FOREIGN KEY (St_ID) REFERENCES Stores(St_ID)
);

===========================================================================================================


-- Create database
CREATE DATABASE WijesingheGrocery;


-- Use the database
USE WijesingheGrocery;


-- Create table for User
CREATE TABLE User (
    U_ID VARCHAR(50) PRIMARY KEY,
    U_name VARCHAR(50) NOT NULL,
    U_Password VARCHAR(100) NOT NULL,
    Role VARCHAR(50) NOT NULL,
    U_Email VARCHAR(100) UNIQUE,
    U_Address VARCHAR(255),
    U_Contact_Number VARCHAR(20)
);

INSERT INTO User (U_ID, U_name, U_Password, Role, U_Email, U_Address, U_Contact_Number)
VALUES
('U001', 'JohnDoe', 'password123', 'Customer', 'john@example.com', '123 Main St, City, Country', '+1234567890'),
('U002', 'JaneSmith', 'securepass', 'Admin', 'jane@example.com', '456 Elm St, City, Country', '+1987654321'),
('U003', 'AliceJones', 'pass1234', 'Customer', 'alice@example.com', '789 Oak St, City, Country', '+1357924680');
INSERT INTO User (U_ID, U_name, U_Password, Role, U_Email, U_Address, U_Contact_Number)
VALUES
('1', 'AliceJones', '1', 'Customer', 'cdd@example.com', '789 Oak St, City, Country', '+1357924680');











-- Create table for Customer
CREATE TABLE Customer (
    C_ID VARCHAR(5) PRIMARY KEY,
    C_Name VARCHAR(50) NOT NULL,
    C_Address VARCHAR(100),
    C_Email VARCHAR(50) UNIQUE,
    C_Contact_Number VARCHAR(15),
    L_Points INT
);

-- Insert values into Customer table
INSERT INTO Customer (C_ID, C_Name, C_Address, C_Email, C_Contact_Number, L_Points)
VALUES
('C001', 'Kasun Silva', '123 Main St', 'kasun@gmail.com', '0777890678', 100),
('C002', 'Nimal Perera', '456 Elm St', 'nimal@gmail.com', '0789456123', 50),
('C003', 'Samantha Jayawardena', '789 Oak St', 'samantha@gmail.com', '0763214567', 75),
('C004', 'Dilini Fernando', '101 Pine St', 'dilini@gmail.com', '0757891234', 120),
('C005', 'Roshan Bandara', '202 Oak St', 'roshan@gmail.com', '0712345678', 80),
('C006', 'Nadun Rajapakse', '303 Maple St', 'nadun@gmail.com', '0723456789', 110),
('C007', 'Chamari Kumari', '404 Cedar St', 'chamari@gmail.com', '0765432198', 90),
('C008', 'Sujeewa Perera', '505 Birch St', 'sujeewa@gmail.com', '0776543219', 70),
('C009', 'Tharaka Jayasuriya', '606 Elm St', 'tharaka@gmail.com', '0789123456', 100),
('C010', 'Sachin Gunawardena', '707 Pine St', 'sachin@gmail.com', '0712345678', 85);







-- Create table for Payment
CREATE TABLE Payment (
    P_ID VARCHAR(50) PRIMARY KEY ,
    P_Type VARCHAR(50) NOT NULL,
    P_Amount DECIMAL(10, 2) NOT NULL,
    Transaction_Date DATE NOT NULL,
    Transaction_Time TIME NOT NULL,
    Card_Type VARCHAR(50)
);


INSERT INTO Payment (P_ID, P_Type, P_Amount, Transaction_Date, Transaction_Time, Card_Type)
VALUES
('P001', 'Credit Card', 50.00, '2024-05-07', '15:30:00', 'Visa'),
('P002', 'Cash', 75.25, '2024-05-06', '14:45:00', 'Visa'),
('P003', 'Debit Card', 120.50, '2024-05-05', '12:15:00', 'Mastercard'),
('P004', 'Credit Card', 100.75, '2024-05-04', '11:30:00', 'Cash'),
('P005', 'Cash', 80.25, '2024-05-03', '10:45:00', 'Cash'),
('P006', 'Debit Card', 60.50, '2024-05-02', '09:15:00', 'Visa'),
('P007', 'Credit Card', 90.75, '2024-05-01', '08:30:00', 'Mastercard'),
('P008', 'Cash', 70.25, '2024-04-30', '07:45:00', 'Cash'),
('P009', 'Debit Card', 110.50, '2024-04-29', '06:15:00', 'Cash'),
('P010', 'Credit Card', 95.75, '2024-04-28', '05:30:00', 'Visa');













-- Create table for Discount
CREATE TABLE Discount (
    D_ID VARCHAR(50) PRIMARY KEY ,
    D_Name VARCHAR(100) NOT NULL,
    D_Amount DECIMAL(10, 2) NOT NULL,
    V_Period VARCHAR(50),
    Grocery_Categories VARCHAR(255),
    D_Start_Date DATE NOT NULL,
    D_End_Date DATE NOT NULL
);




INSERT INTO Discount (D_ID, D_Name, D_Amount, V_Period, Grocery_Categories, D_Start_Date, D_End_Date)
VALUES
('D001', 'Summer Sale', 10.00, '3 months', 'Grocery', '2024-06-01', '2024-08-31'),
('D002', 'Back to School', 15.00, '1 month', 'School Supplies', '2024-08-01', '2024-08-31'),
('D003', 'Holiday Special', 20.00, '2 weeks', 'Holiday Items', '2024-12-15', '2024-12-31'),
('D004', 'New Year Clearance', 30.00, '1 week', 'Clearance', '2025-01-01', '2025-01-07'),
('D005', 'Spring Discount', 25.00, '1 month', 'Spring Products', '2025-03-01', '2025-03-31'),
('D006', 'Easter Sale', 10.00, '2 weeks', 'Easter Items', '2025-04-01', '2025-04-14');












-- Create table for Store
CREATE TABLE Store (
    St_ID VARCHAR(50) PRIMARY KEY ,
    St_Name VARCHAR(100) NOT NULL,
    Floor_Plan VARCHAR(255),
    Description TEXT,
    Location VARCHAR(100),
    Qty_On_Hand INT(50)
);



INSERT INTO Store (St_ID, St_Name, Floor_Plan, Description, Location, Qty_On_Hand)
VALUES
('ST001', 'Main Street Grocery', 'Ground Floor', 'Local grocery store with a wide variety of products.', 'Main Street', 1000),
('ST002', 'City Market', 'First Floor', 'Large supermarket offering groceries, household items, and more.', 'City Center', 1500),
('ST003', 'Fresh Foods', 'Second Floor', 'Specializes in fresh produce, meats, and dairy products.', 'Downtown Plaza', 800),
('ST004', 'Discount Mart', 'Basement', 'Discount store providing affordable products.', 'South Side, City', 1200),
('ST005', 'Gourmet Grocer', 'Ground Floor', 'Upscale grocery store featuring gourmet and specialty items.', 'North End, City', 600);
















-- Create table for Supplier
CREATE TABLE Supplier (
    S_ID VARCHAR(10) PRIMARY KEY ,
    S_Name VARCHAR(100) NOT NULL,
    S_Address VARCHAR(255),
    S_Email VARCHAR(100) UNIQUE,
    S_Contact_Number VARCHAR(20),
    Delivery_Schedule VARCHAR(100)
);





INSERT INTO Supplier (S_ID, S_Name, S_Address, S_Email, S_Contact_Number, Delivery_Schedule)
VALUES
('S001', 'Somasiri Stores', '73 Main Street', 'somasiri@gmail.com', '+9471890678', 'Weekly'),
('S002', 'Bandara Brothers', '456 Orchard Street', 'bandaras@example.com', '+9412345678', 'Bi-weekly'),
('S003', 'Perera Enterprises', '789 Gourmet Lane', 'pererae@example.com', '+9477654321', 'Monthly'),
('S004', 'Fernando Farms', '101 Organic Avenue', 'fernas@example.com', '+9475123456', 'Bi-monthly'),
('S005', 'Silva Suppliers', '202 Sunrise Street', 'silsup@example.com', '+9471122334', 'Weekly'),
('S006', 'Fernando Foods', '303 Green Avenue, City', 'fernaoods@example.com', '+9471654321', 'Monthly'),
('S007', 'De Silva Distributors', '404 Farm Lane', 'desilvrs@example.com', '+9472890345', 'Weekly'),
('S008', 'Jayawardena Produce', '505 Quality Street', 'jayawarduce@example.com', '+9476234567', 'Bi-weekly'),
('S009', 'Gunawardena Groceries', '606 Garden Road', 'gunawardenaes@example.com', '+9477890123', 'Monthly'),
('S010', 'Fernando Farmstead', '707 Nature Lane', 'fernandofard@example.com', '+9471456789', 'Bi-monthly');










-- Create table for Orders


CREATE TABLE Orders (
     O_ID VARCHAR(10) PRIMARY KEY,
     U_ID VARCHAR(50),
     C_ID VARCHAR(50),
     D_ID VARCHAR(50),
     P_ID VARCHAR(50),
     L_Points_Earned INT,
     O_Time TIME NOT NULL,
     O_Date DATE NOT NULL,
     Total_Amount DECIMAL(10, 2),
     Status VARCHAR(50),
     FOREIGN KEY (U_ID) REFERENCES User(U_ID) ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY (C_ID) REFERENCES Customer(C_ID) ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY (D_ID) REFERENCES Discount(D_ID) ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY (P_ID) REFERENCES Payment(P_ID) ON UPDATE CASCADE ON DELETE CASCADE
);







INSERT INTO Orders (O_ID, U_ID, C_ID, D_ID, P_ID, L_Points_Earned, O_Time, O_Date, Total_Amount, Status)
VALUES
        ('ORD001', 'U001', 'C001', 'D001', 'P001', 10, '10:00:00', '2024-05-01', 90.00, 'Delivered'),
        ('ORD002', 'U001', 'C002', 'D002', 'P002', 5, '11:00:00', '2024-05-02', 120.25, 'Processing'),
        ('ORD003', 'U001', 'C003', 'D003', 'P003', 15, '12:00:00', '2024-05-03', 150.50, 'Delivered'),
        ('ORD004', 'U001', 'C004', 'D004', 'P004', 20, '13:00:00', '2024-05-04', 180.75, 'Cancelled'),
        ('ORD005', 'U001', 'C005', 'D005', 'P005', 25, '14:00:00', '2024-05-05', 210.25, 'Delivered'),
        ('ORD006', 'U001', 'C006', 'D006', 'P006', 30, '15:00:00', '2024-05-06', 240.50, 'Processing');












-- Create table for Item


CREATE TABLE Item (
    I_ID VARCHAR(50) PRIMARY KEY,
    I_Name VARCHAR(100) NOT NULL,
    Brands VARCHAR(100),
    Qty INT,
    Description TEXT,
    Weight DECIMAL(10, 2),
    St_ID VARCHAR(50),
    Unit_price DECIMAL(10, 2),
    FOREIGN KEY (St_ID) REFERENCES Store(St_ID) ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO Item (I_ID, I_Name, Brands, Qty, Description, Weight, St_ID, Unit_price)
VALUES
('I001', 'Apple', 'Farm Fresh', 100, 'Fresh apples from local farms.', 0.5, 'ST001', 1.00),
('I002', 'Banana', 'Tropical Delight', 150, 'Ripe bananas for a healthy snack.', 0.3, 'ST002', 0.75),
('I003', 'Milk', 'Dairy Farms', 200, 'Fresh milk from grass-fed cows.', 1.0, 'ST003', 2.50),
('I004', 'Bread', 'Bakery Fresh', 120, 'Soft and fluffy bread for sandwiches.', 0.4, 'ST004', 1.50),
('I005', 'Eggs', 'Happy Hens', 180, 'Organic eggs from free-range chickens.', 0.6, 'ST005', 3.00);













-- Create table for Return & Exchange
CREATE TABLE Return_Exchange (
    R_ID  VARCHAR(50) PRIMARY KEY,
    O_ID VARCHAR(10),
    R_Date DATE,
    Refund_Amount DECIMAL(10, 2),
    R_Reason TEXT,
    Exchange_Request BOOLEAN,
    R_Status VARCHAR(50),
    FOREIGN KEY (O_ID) REFERENCES Orders(O_ID) ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO Return_Exchange (R_ID, R_Date,  Refund_Amount, R_Reason, Exchange_Request, R_Status)
VALUES
('RET001', '2024-05-01',  20.00, 'Wrong item received.', FALSE, 'Processed'),
('RET002', '2024-05-02',  15.25, 'Item damaged during shipping.', TRUE, 'Processing'),
('RET003', '2024-05-03',  30.50, 'Changed mind about purchase.', FALSE, 'Pending'),
('RET004', '2024-05-04',  25.75, 'Item not as described.', TRUE, 'Received'),
('RET005', '2024-05-05',  40.25, 'Received duplicate item.', FALSE, 'Processing');
('RET006', '2024-05-06',  35.50, 'Item no longer needed.', TRUE, 'Processed'),
('RET007', '2024-05-07',  50.75, 'Received expired item.', FALSE, 'Pending'),
('RET008', '2024-05-08',  45.25, 'Item doesn''t fit.', TRUE, 'Received'),
('RET009', '2024-05-09',  60.50, 'Changed color preference.', FALSE, 'Processing'),
('RET010', '2024-05-10',  55.75, 'Item arrived late.', TRUE, 'Processed');










-- Create table for Order detail
CREATE TABLE Order_Detail (
    I_ID VARCHAR(50),
    O_ID VARCHAR(10),
    Total_Price DECIMAL(10, 2),
    Status VARCHAR(50),
    FOREIGN KEY (I_ID) REFERENCES Item(I_ID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (O_ID) REFERENCES Orders(O_ID) ON UPDATE CASCADE ON DELETE CASCADE
);




INSERT INTO Order_Detail (I_ID, O_ID, Total_Price, Status)
VALUES
('I001', 'ORD001', 10.00, 'Delivered'),
('I002', 'ORD002', 15.25, 'Processing'),
('I003', 'ORD003', 30.50, 'Delivered'),
('I004', 'ORD004', 25.75, 'Cancelled'),
('I005', 'ORD005', 40.25, 'Delivered');












-- Create table for Supply_Store

CREATE TABLE Supply_Store (
 S_ID VARCHAR(10),
St_ID VARCHAR(50),
 Status VARCHAR(50),
Date DATE NOT NULL,
Time TIME NOT NULL,
    FOREIGN KEY (S_ID) REFERENCES Supplier(S_ID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (St_ID) REFERENCES Store(St_ID) ON UPDATE CASCADE ON DELETE CASCADE


);




INSERT INTO Supply_Store (S_ID, St_ID, Status, Date, Time)
VALUES
('S001', 'ST001', 'Delivered', '2024-05-01', '10:00:00'),
('S002', 'ST002', 'Processing', '2024-05-02', '11:00:00'),
('S003', 'ST003', 'Delivered', '2024-05-03', '12:00:00'),
('S004', 'ST004', 'Cancelled', '2024-05-04', '13:00:00'),
('S005', 'ST005', 'Delivered', '2024-05-05', '14:00:00');









