SELECT MAGE.mage_id, MAGE.mage_name, MAGE.mage_level, MAGE.mage_exp, COUNT(SCROLL.mage_id)
                                            AS scroll_amount, AVG(SCROLL.mana_cost) AS average_manacost
FROM MAGE LEFT OUTER JOIN SCROLL
ON MAGE.mage_id=SCROLL.mage_id
GROUP BY MAGE.mage_id LIMIT :amt OFFSET :n_from