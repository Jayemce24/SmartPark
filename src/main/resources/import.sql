INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('11111111-1111-1111-1111-111111111111','Intramuros',50,3);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('22222222-2222-2222-2222-222222222222','Makati CBD',100,5);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('33333333-3333-3333-3333-333333333333','Quezon City',80,4);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('44444444-4444-4444-4444-444444444444','Binondo',60,6);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('55555555-5555-5555-5555-555555555555','Malate',40,2);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('66666666-6666-6666-6666-666666666666','Pasay',70,5);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('77777777-7777-7777-7777-777777777777','Ortigas',90,7);
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES ('88888888-8888-8888-8888-888888888888','Manila Port Area',30,1);

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAN-001','CAR','Alice','11111111-1111-1111-1111-111111111111');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAN-002','TRUCK','Bob','11111111-1111-1111-1111-111111111111');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAN-003','MOTORCYCLE','Charlie','11111111-1111-1111-1111-111111111111');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAK-101','CAR','David','22222222-2222-2222-2222-222222222222');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAK-102','CAR','Evelyn','22222222-2222-2222-2222-222222222222');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAK-103','TRUCK','Frank','22222222-2222-2222-2222-222222222222');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MAK-104','MOTORCYCLE','Grace','22222222-2222-2222-2222-222222222222');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('QC-201','CAR','Henry','33333333-3333-3333-3333-333333333333');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('QC-202','TRUCK','Irene','33333333-3333-3333-3333-333333333333');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('QC-203','MOTORCYCLE','Jack','33333333-3333-3333-3333-333333333333');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('QC-204','CAR','Kevin','33333333-3333-3333-3333-333333333333');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('BIN-301','CAR','Liam','44444444-4444-4444-4444-444444444444');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('BIN-302','TRUCK','Mia','44444444-4444-4444-4444-444444444444');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('BIN-303','MOTORCYCLE','Noah','44444444-4444-4444-4444-444444444444');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MLT-401','CAR','Olivia','55555555-5555-5555-5555-555555555555');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('MLT-402','MOTORCYCLE','Paul','55555555-5555-5555-5555-555555555555');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('PSY-501','CAR','Quinn','66666666-6666-6666-6666-666666666666');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('PSY-502','TRUCK','Rachel','66666666-6666-6666-6666-666666666666');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('PSY-503','MOTORCYCLE','Sam','66666666-6666-6666-6666-666666666666');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('ORT-601','CAR','Tom','77777777-7777-7777-7777-777777777777');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('ORT-602','TRUCK','Uma','77777777-7777-7777-7777-777777777777');
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('ORT-603','CAR','Victor','77777777-7777-7777-7777-777777777777');

INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES ('PORT-701','CAR','Wendy','88888888-8888-8888-8888-888888888888');
