insert into category(id, name, tax_rate_percent) VALUES
(1, 'books', 0),
(2, 'food', 0),
(3, 'medical', 0),
(4, 'other', 10);

insert into product(id, name, category_id) VALUES
(1, 'book', 1),
(2, 'music CD', 4),
(3, 'chocolate bar', 2),
(4, 'box of chocolates', 2),
(5, 'bottle of perfume', 4),
(6, 'packet of headache pills', 3);