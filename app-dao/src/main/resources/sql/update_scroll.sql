UPDATE SCROLL SET description = :description, durability = :durability,
                                mana_cost = :mana_cost, creation_date = :creation_date, mage_id = :mage_id
                                WHERE scroll_id = :scroll_id