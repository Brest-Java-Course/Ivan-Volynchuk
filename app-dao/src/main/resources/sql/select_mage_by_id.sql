SELECT MAGE.mage_id, MAGE.mage_name, COUNT(SCROLL.mage_id)
                                            AS scroll_amount, AVG(SCROLL.mana_cost) AS average_manacost
FROM MAGE LEFT OUTER JOIN SCROLL
ON MAGE.mage_id=SCROLL.mage_id
WHERE MAGE.mage_id=:mage_id
GROUP BY MAGE.mage_id