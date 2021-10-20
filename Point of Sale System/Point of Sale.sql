DROP DATABASE IF EXISTS point_of_sale;
CREATE DATABASE IF NOT EXISTS point_of_sale;
USE point_of_sale;

DROP TABLE IF EXISTS category;
CREATE TABLE category
(
    id           int(10) primary key auto_increment,
    categoryName varchar(45),
    description  varchar(150)
);

ALTER TABLE category
    DISABLE KEYS;

INSERT INTO category (categoryName, description)
VALUES ('Canned Goods', 'Canned Goods'),
       ('Condiments', 'Condiments'),
       ('Beverage', 'Beverage'),
       ('RTR', 'RTR');


DROP TABLE IF EXISTS company;
CREATE TABLE company
(
    id        int(10) primary key auto_increment,
    name      varchar(250),
    address   varchar(250),
    phoneNo   varchar(45),
    email     varchar(100),
    website   varchar(100),
    TINNumber varchar(100),
    HInvoice  int(10)
);
ALTER TABLE company
    DISABLE KEYS;

INSERT INTO company(id, name, address, phoneNo, email, website, TINNumber, HInvoice)
VALUES (1, 'CyberVineyard SS', 'Kidapawan City, Philippines', '12312312', 'cybernick75@gmail.com', 'www.cvss.com',
        '2131-232-3232', 0);
ALTER TABLE company
    ENABLE KEYS;

DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id           int(10) primary key auto_increment,
    productCode  varchar(45),
    description  varchar(200),
    barcode      varchar(50),
    unitPrice    double,
    stocksOnHand double,
    reorderLevel int(10),
    categoryNo   int,
    FOREIGN KEY (categoryNo) REFERENCES category (id)
);

ALTER TABLE product
    DISABLE KEYS;

INSERT INTO product (id, productCode, description, barcode, unitPrice, stocksOnHand, reorderLevel, categoryNo)
VALUES (1, '748485801575', '555 CARNE NORTE BISTEK 48 X 150.00G.', '748485801575', 1440, 97, 50, 1),
       (2, '748485801629', '555 CARNE NORTE CHUNKY 48 X 150.00G.', '748485801629', 1500, 149, 50, 1),
       (3, '748485800264', '555 CARNE NORTE CORNED BEEF 48 X 150.00G.', '748485800264', 2000, 149, 50, 1),
       (4, '748485801162', '555 CARNE NORTE CORNED BEEF 48 X 175.00G.', '748485801162', 1600, 147, 50, 1),
       (5, '4801958705001', 'AJINOMOTO AJI-SHIO PEPPER 12 X 24 X 18.00G.', '4801958705001', 1000, 198, 50, 2),
       (6, '4808680023809', 'BEST FOOD MAYONNAISE PLAIN 12 X 470.00ML.', '4808680023809', 2000, 200, 50, 2),
       (7, '4801032415048', 'CALI GREEN PINEAPPLE CANNED JUICES 24 X 330.00ML.', '4801032415048', 1700, 197, 50, 3),
       (8, '4809013996081', 'CUCINA FILIPINA COCO 12 X 750ML', '4809013996081', 1500, 200, 50, 2),
       (9, '4801668500224', 'DATU PUTI SOY SAUCE PLAIN 12 X 1000.00ML.', '4801668500224', 1600, 200, 50, 2),
       (10, '480000900034', 'DOLE PINEAPPLE JUICE 24 X 240ML.', '480000900034', 1800, 199, 50, 3),
       (11, '48006020253413', 'EIGHT O\'CLOCK ICED TEA LEMON POWDER JUICE 24 X 400.00G.', '48006020253413', 1200, 245,
        50, 3);

INSERT INTO product (id, productCode, description, barcode, unitPrice, stocksOnHand, reorderLevel, categoryNo)
VALUES (12, '4803925071140', 'GATORADE BLUE BOLT 24 X 400.00ML.', '4803925071140', 1400, 200, 50, 3),
       (13, '000004806519060001', ' DOMINQart            ', '000004806519060001', 40, 40, 10, 1),
       (14, '000004800053411046', ' S-Bkr SB Oil500ml    ', '000004800053411046', 68.8, 40, 10, 4),
       (15, '000004800017925305', '@101ckle rlish.405g   ', '000004800017925305', 53.1, 40, 10, 4),
       (16, '000004806507550934', '@101er G.100ML        ', '000004806507550934', 48.4, 40, 10, 4),
       (17, '000004806501703503', '@101pion todo 155g    ', '000004806501703503', 9.8, 40, 10, 4),
       (18, '000004800818680809', '@101quik 12\'s         ', '000004800818680809', 46.25, 40, 10, 4),
       (19, '000004806507550040', '@101rubbing al.       ', '000004806507550040', 23.45, 40, 10, 4),
       (20, '000004800053411046', '1 GT Lmn G 500ml      ', '000004800053411046', 19, 40, 10, 1),
       (21, '000004804888901840', '1 GT Lmn I 355ml      ', '000004800053411046', 15, 40, 10, 1),
       (22, '000004800053411046', '1 RD Strwb 355ml      ', '000004804888901765', 15, 40, 10, 1);

INSERT INTO product (id, productCode, description, barcode, unitPrice, stocksOnHand, reorderLevel, categoryNo)
VALUES (23, '000004804888903264', '1 W T Apl355ml        ', '000004804888903264', 15, 40, 10, 4),
       (24, '000004804888903271', '1 W T Apl500ml        ', '000004804888903271', 20, 40, 10, 1),
       (25, '000004804888903288', '1 W T Citrus 355      ', '000004804888903288', 15, 40, 10, 4),
       (26, '000004804888901345', '1 W T Pch P500ml      ', '000004804888901345', 19, 40, 10, 4),
       (27, '000004800011191201', '123 M Klr 10 C        ', '000004800011191201', 14, 40, 10, 4),
       (28, '000006001108001085', '2Oceans S75cl         ', '000006001108001085', 321.5, 40, 10, 4),
       (29, '000006001497600647', '2Oceans SA 75cl       ', '000006001497600647', 321.5, 40, 10, 4),
       (30, '000000748485801957', '357 CB G 150g         ', '000000748485801957', 16.5, 40, 10, 4),
       (31, '000004800488011279', '4250', '000004800488011279', 42.5, 40, 10, 4),
       (32, '000000731126412025', '4X cleanser 350g      ', '000000731126412025', 22.75, 40, 10, 4),
       (33, '000000748485801872', '555 B-Loaf 50g        ', '000000748485801872', 13, 39, 10, 4),
       (34, '000000748485801629', '555 Chnky C-N150      ', '000000748485801629', 21.65, 40, 10, 4);

INSERT INTO product (id, productCode, description, barcode, unitPrice, stocksOnHand, reorderLevel, categoryNo)
VALUES (35, '000000748485800721', '555 C-N 100g          ', '000000748485800721', 11.9, 40, 10, 4),
       (36, '000000748485800264', '555 C-N 150g          ', '000000748485800264', 19.95, 40, 10, 4),
       (37, '000000748485801162', '555 C-N 175g          ', '000000748485801162', 23, 40, 10, 4),
       (38, '000000748485800424', '555 CN 200g           ', '000000748485800424', 25.9, 40, 10, 4),
       (39, '000000748485801919', '555 CN 260g           ', '000000748485801919', 33.6, 40, 10, 4),
       (40, '000000748485801575', '555 CN Bistek 150g    ', '000000748485801575', 22, 40, 10, 4),
       (41, '000000748485801636', '555 CN Chunky190      ', '000000748485801636', 27.4, 40, 10, 4),
       (42, '000000748485801520', '555 C-N CN Ptta100    ', '000000748485801520', 11.4, 40, 10, 4),
       (43, '000000748485801698', '555 CN GT 100g        ', '000000748485801698', 11.9, 40, 10, 4),
       (44, '000000748485801704', '555 CN GT 150g        ', '000000748485801704', 19.95, 40, 10, 4),
       (45, '000000748485801179', '555 C-N HT 175g       ', '000000748485801179', 23, 40, 10, 4);

INSERT INTO product (id, productCode, description, barcode, unitPrice, stocksOnHand, reorderLevel, categoryNo)
VALUES (46, '000000748485800905', '555 C-N HT&S100g      ', '000000748485800905', 15.9, 40, 10, 4),
       (47, '000000748485700854', '555 CorTun150g        ', '000000748485700854', 20.7, 40, 10, 4),
       (48, '000000748485700847', '555 CorTun150g        ', '000000748485700847', 20.7, 40, 10, 4),
       (49, '000000748485200668', '555 Frd.Sar155g       ', '000000748485200668', 15.4, 40, 10, 4),
       (50, '000000748485200675', '555 FS W/T 155g       ', '000000748485200675', 15.85, 40, 10, 4),
       (51, '000000748485300016', '555 M 155g            ', '000000748485300016', 11.8, 40, 10, 4),
       (52, '000000748485300047', '555 Mack TS 425g      ', '000000748485300047', 37.4, 40, 10, 4),
       (53, '000000748485300139', '555 MacSS200g         ', '000000748485300139', 24.95, 40, 10, 4),
       (54, '000000748485300023', '555 Mckrel N.O 425g   ', '000000748485300023', 37.4, 40, 10, 4),
       (55, '000000748485801889', '555 MLoaf 150g        ', '000000748485801889', 13.4, 40, 10, 4),
       (56, '000000748485200033', '555 N.Oil 155g        ', '000000748485200033', 12.75, 40, 10, 4);
ALTER TABLE `product`
    ENABLE KEYS;

DROP TABLE IF EXISTS staff;
CREATE TABLE staff
(
    id           int(10) primary key auto_increment,
    firstname    varchar(50),
    lastname     varchar(50),
    mobile       varchar(20),
    email        varchar(50),
    country      varchar(50),
    addressLine1 varchar(100),
    addressLine2 varchar(100),
    city         varchar(50),
    postalCode   varchar(4),
    username     varchar(45),
    role         varchar(45),
    password     varchar(45),
    companyID    int,
    FOREIGN KEY (companyID) REFERENCES company (id)
);

ALTER TABLE staff
    DISABLE KEYS;
INSERT INTO staff(id, firstname, lastname, mobile, email, country, addressLine1, addressLine2, city, postalCode,
                  username, role, password, companyID)
VALUES (1, 'System', 'Admin', '0120742310', 'admin@gmail.com', 'South Africa', '20 Oak Street', '', 'Johannesburg',
        '0877', 'admin', 'Admin', 'admin', 1),
       (2, 'Rojas', 'Nick', '0846890823', 'nick@gmail.com', 'South Africa', '1234 Diagonal Street', '', 'Johannesburg',
        '0888', 'nick', 'Cashier', '1234', 1),
       (3, 'Gerald', 'Greg', '0769890823', 'greg@gmail.com', 'South Africa', '34 Main Street', '', 'Johannesburg',
        '1088', 'greg', 'Cashier', '1234', 1);
ALTER TABLE `staff`
    ENABLE KEYS;

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(
    id          int(10) primary key auto_increment,
    date        date,
    time        time,
    subTotal    double,
    vatAmount   double,
    totalAmount double,
    staffID     int(11),
    status      int(10),
    FOREIGN KEY (staffID) REFERENCES staff (id) on DELETE set null
);

DROP TABLE IF EXISTS transaction_details;
CREATE TABLE transaction_details
(
    id        int(10) primary key auto_increment,
    invoiceNo int,
    productNo int(10),
    itemPrice double,
    quantity  double,
    discount  double,
    FOREIGN KEY (invoiceNo) REFERENCES transactions (id),
    FOREIGN KEY (productNo) REFERENCES product (id)
);

DROP TABLE IF EXISTS payment;
CREATE TABLE payment
(
    id        int(10) primary key auto_increment,
    invoiceNo int(10),
    cash      double,
    `change`  double,
    FOREIGN KEY (invoiceNo) REFERENCES transactions (id)
);

DROP TABLE IF EXISTS stock;
CREATE TABLE stock
(
    id        int(10) primary key auto_increment,
    productNo int(10),
    quantity  double,
    dateIn    date,
    FOREIGN KEY (productNo) REFERENCES product (id)
);

ALTER TABLE stock
    ENABLE KEYS;
INSERT INTO stock (id, productNo, quantity, dateIn)
VALUES (1, 1, 100, '03/19/2021'),
       (2, 2, 150, '03/19/2021'),
       (3, 3, 150, '03/19/2021'),
       (4, 4, 150, '03/19/2021'),
       (5, 5, 200, '03/19/2021'),
       (6, 6, 200, '03/19/2021'),
       (7, 7, 200, '03/19/2021'),
       (8, 8, 200, '03/19/2021'),
       (9, 9, 200, '03/19/2021'),
       (10, 10, 200, '03/19/2021'),
       (11, 11, 250, '03/19/2021'),
       (12, 12, 200, '03/19/2021'),
       (13, 13, 40, '04/06/2021'),
       (14, 14, 40, '04/06/2021'),
       (15, 15, 40, '04/06/2021'),
       (16, 16, 40, '04/06/2021'),
       (17, 17, 40, '04/06/2021'),
       (18, 18, 40, '04/06/2021'),
       (19, 19, 40, '04/06/2021'),
       (20, 20, 40, '04/06/2021');

INSERT INTO stock (id, productNo, quantity, dateIn)
VALUES (21, 21, 40, '04/06/2021'),
       (22, 22, 40, '04/06/2021'),
       (23, 23, 40, '04/06/2021'),
       (24, 24, 40, '04/06/2021'),
       (25, 25, 40, '04/06/2021'),
       (26, 26, 40, '04/06/2021'),
       (27, 27, 40, '04/06/2021'),
       (28, 28, 40, '04/06/2021'),
       (29, 29, 40, '04/06/2021'),
       (30, 30, 40, '04/06/2021'),
       (31, 31, 40, '04/06/2021'),
       (32, 32, 40, '04/06/2021'),
       (33, 33, 40, '04/06/2021'),
       (34, 34, 40, '04/06/2021'),
       (35, 35, 40, '04/06/2021'),
       (36, 36, 40, '04/06/2021'),
       (37, 37, 40, '04/06/2021');

INSERT INTO stock (id, productNo, quantity, dateIn)
VALUES (38, 38, 40, '04/06/2021'),
       (39, 39, 40, '04/06/2021'),
       (40, 40, 40, '04/06/2021'),
       (41, 41, 40, '04/06/2021'),
       (42, 42, 40, '04/06/2021'),
       (43, 43, 40, '04/06/2021'),
       (44, 44, 40, '04/06/2021'),
       (45, 45, 40, '04/06/2021'),
       (46, 46, 40, '04/06/2021'),
       (47, 47, 40, '04/06/2021'),
       (48, 48, 40, '04/06/2021'),
       (49, 49, 40, '04/06/2021'),
       (50, 50, 40, '04/06/2021'),
       (51, 51, 40, '04/06/2021'),
       (52, 52, 40, '04/06/2021'),
       (53, 53, 40, '04/06/2021'),
       (54, 54, 40, '04/06/2021'),
       (55, 55, 40, '04/06/2021'),
       (56, 56, 40, '04/06/2021');
ALTER TABLE stock
    ENABLE KEYS;

DROP TABLE IF EXISTS vat_setting;
CREATE TABLE vat_setting
(
    id         int(10) primary key auto_increment,
    vatPercent double
);

ALTER TABLE vat_setting
    DISABLE KEYS;
INSERT INTO vat_setting (id, vatPercent)
VALUES (1, 15);
ALTER TABLE vat_setting
    ENABLE KEYS;