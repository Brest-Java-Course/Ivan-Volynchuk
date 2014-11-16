CREATE TABLE SCROLL (
scroll_id BIGINT IDENTITY,
description VARCHAR(255),
durability BIGINT,
mana_cost BIGINT,
creation_date DATE
);

CREATE TABLE MAGE (
mage_id BIGINT IDENTITY,
mage_name VARCHAR(255),
scroll_amount BIGINT,
average_manacost BIGINT,
);