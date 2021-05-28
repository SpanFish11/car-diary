ALTER TABLE m_guarantee
    ADD COLUMN mileage integer;

UPDATE m_guarantee
SET mileage = 150000
WHERE extended = true;

UPDATE m_guarantee
SET mileage = 100000
WHERE extended = false;