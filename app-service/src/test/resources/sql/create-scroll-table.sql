CREATE TABLE SCROLL (
scroll_id BIGINT IDENTITY,
description VARCHAR(255) UNIQUE,
durability BIGINT,
mana_cost BIGINT,
creation_date DATE,
mage_id BIGINT
);