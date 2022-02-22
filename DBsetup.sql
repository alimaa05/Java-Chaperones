--CREATE DATABASE chaperones;

DROP TABLE bookings;
DROP TABLE activities;
DROP TABLE venues;
DROP TABLE guides;
DROP TABLE users;

CREATE TABLE venues (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    area VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE guides (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phoneNumber VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phoneNumber VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    guide_id INT REFERENCES guides(id),
    venue_id INT REFERENCES venues(id),
    name VARCHAR(255),
    description TEXT,
    date DATE,
    time TIME,
    duration VARCHAR(255),
    price DECIMAL(5,2),
    capacity INT,
    cancelled BOOLEAN DEFAULT false
);

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    activity_id INT REFERENCES activities(id)
);

-- Create Guides
INSERT INTO guides (name, phoneNumber, email) VALUES ('Robyn', '01356 189056', 'robyn@gmail.com');
INSERT INTO guides (name, phoneNumber, email) VALUES ('Andrew', '01345 123456', 'andrew@gmail.com');
INSERT INTO guides (name, phoneNumber, email) VALUES ('Alimaa', '01789 389202', 'alimaa@gmail.com');
INSERT INTO guides (name, phoneNumber, email) VALUES ('Adib', '03789 892345', 'adib@gmail.com');

-- Create Venues
INSERT INTO venues (name, area, address) VALUES ('London Dungeons', 'Southbank', 'The Queen''s Walk, London SE1 7PB');
INSERT INTO venues (name, area, address) VALUES ('London Eye', 'River Thames', 'Riverside Building, County Hall, London SE1 7PB');
INSERT INTO venues (name, area, address) VALUES ('Sea Life London', 'River Thames', 'Riverside Building, County Hall, Westminster Bridge Rd London SE1 7PB');
INSERT INTO venues (name, area, address) VALUES ('Altab Ali Park', 'Whitechapel Road', 'London'); -- jack the ripper
INSERT INTO venues (name, area, address) VALUES ('Madame Tussauds', 'Baker Street', 'Marylebone Rd, London NW1 5LR');
INSERT INTO venues (name, area, address) VALUES ('Tate Modern', 'River Thames', 'Bankside, London SE1 9TG');
INSERT INTO venues (name, area, address) VALUES ('Tate Britain', 'Pimlico','Millbank, London SW1P 4RG');
INSERT INTO venues (name, area, address) VALUES ('Natural History Museum', 'South Kensington', 'Cromwell Rd, South Kensington, London SW7 5BD');
INSERT INTO venues (name, area, address) VALUES ('Warner Bros. Studio Tour London', 'Southeast England','Studio Tour Dr, Leavesden, Watford WD25 7LR');
INSERT INTO venues (name, area, address) VALUES ('Royal Botanic Gardens, Kew','Richmond upon Thames','Kew Gardens, Richmond, Surrey TW9 3AB');
INSERT INTO venues (name, area, address) VALUES ('Hidden City Escape Rooms', 'London', 'Dingley Place, EC1V 8BR');

-- Sea Life
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (2, 3, 'VIP Penguin Experience',
'Get close to our Gentoo penguins. Help out our handlers with their daily routines.',
'2022-05-06', '12:15:00', '1hr', 13.50, 10);
-- Altab Ali Park - Jack the ripper
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (1, 4, 'Jack the Ripper, Interactive Guided Walking Tour',
'Hit the streets of East End London on an interactive Jack the Ripper walking tour. Head back to 1888 with your expert Ripperologist guide as you try to solve the still unsolved murders on this immersive small-group experience.',
'2022-05-13', '20:00:00', '1-2hrs', 7.50, 20);
-- Hidden City Escape Rooms
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (3, 11, 'Moriarty''s Game',
'A journey into the world of a criminal mastermind. Professor James Moriarty invites you to celebrate the finest minds in London by solving his mysterious challenge, which he has personally prepared. Succeed, and he promises to make you an offer you can''t refuse...',
'2022-03-12', '10:30:00', '3-4hrs', 25.00, 8);
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (3, 11, 'The Enchanted Mirror',
'A fairytale quest inspired by the world of Snow White. Mirror, mirror on the wall… Embark on a fairytale quest to find the Enchanted Mirror and prove yourself the wisest of them all. But beware the tricks of a deceitful Queen, and the fate of those who fail...',
'2022-03-12', '19:30:00', '3-4hrs', 25.00, 8);
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (3, 11, 'The Hunt For The Cheshire Cat',
'The Cheshire Cat is in London, and he wants you to find him. Solve his clues on an adventure into the strange, surreal and absurd - and uncover a dark conspiracy at the heart of Wonderland…',
'2022-03-13', '19:30:00', '3-4hrs', 25.00, 8);
-- Royal Botanic Gardens, Kew
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (4, 10, 'Members welcome tour',
'Exclusively for members: Kick-start your Kew membership with a free guided tour and barista coffee.',
'2022-04-22', '13:00:00', '1-2hrs', 00.00, 20);
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (4, 10, 'Tai chi at Kew',
'Exercise your mind and body in our enchanting Mediterranean Garden.',
'2022-03-25', '10:00:00', '1hr', 40.00, 10);
-- Harry potter tour
INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity) VALUES (1, 9, 'Harry Potter Studio Tour',
'Walk in the footsteps of Harry Potter and explore the wonders of the Wizarding World.',
'2022-12-15', '9:30:00', '7-8hrs', 89.00, 50);

-- Create Users
INSERT INTO users (name, phoneNumber, email) VALUES ('Valeria', '02338 187389', 'valeria@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Colin', '01246 876984', 'colin@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Nelson', '02347 989785', 'amigoscode@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Ben', '01849 089421', 'ben@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Iain', '02244 148985', 'iain@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Marc', '02883 987732', 'marc@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Tim', '01445 658560', 'tim@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Cate', '01886 874483', 'cate@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Suad', '02876 876580', 'suad@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Will', '02443 848795', 'will@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Sarina', '02378 995823', 'sarina@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Alex', '02498 398505', 'alex@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Cristian', '02049 084322', 'cristain@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Suraya', '02487 899824', 'suraya@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Abdi', '02234 823480', 'abdi@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Aoife', '02845 873435', 'aoife@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Connie', '02345 643565', 'connie@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Darshil', '02908 498231', 'darshil@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Hajr', '02304 247895', 'hajr@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Jake', '02487 292873', 'jake@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Marcy', '02487 593467', 'marcy@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Michael', '02345 435465', 'michael@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Michelle', '02597 837353', 'michelle@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Nasir', '02459 839534', 'nasir@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Nayan', '02573 858933', 'nayan@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Rachel', '02587 398537', 'rachel@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Rosa', '02858 798531', 'rosa@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Shirhan', '02874 853244', 'shirhan@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Wendy', '02487 958754', 'wendy@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Yang', '02487 486752', 'yang@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Aaron', '02787 895353', 'aaron@gmail.com');
INSERT INTO users (name, phoneNumber, email) VALUES ('Suraaj', '02387 595367', 'suraaj@gmail.com');
