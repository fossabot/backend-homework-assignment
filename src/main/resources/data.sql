INSERT INTO issuer (id, name) VALUES
  (1, 'Dominic Quinn'),
  (2, 'John Doe'),
  (3, 'Theresa Lawrence');

INSERT INTO tender (description, closed_for_offers, issuer_id) VALUES
  ('description for tender 1', false, 1),
  ('description for tender 2', false, 1),
  ('description for tender 3', false, 3);

INSERT INTO bidder (id, name) VALUES
  (1, 'William Black'),
  (2, 'Caroline May'),
  (3, 'Austin Fraser'),
  (4, 'Sue Manning'),
  (5, 'Jasmine Gill'),
  (6, 'William Buckland');


INSERT INTO offer (amount, accepted, tender_id, bidder_id) VALUES
  (300, false, 1, 1),
  (400, false, 1, 2),
  (400, false, 2, 3),
  (200, false, 2, 4),
  (500, true, 2, 5),
  (200, false, 3, 6),
  (400, false, 1, 1),
  (500, false, 2, 1);