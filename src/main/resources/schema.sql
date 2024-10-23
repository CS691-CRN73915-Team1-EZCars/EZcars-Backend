ALTER TABLE vehicles 
ADD FULLTEXT(make, model, transmission, fuel_type, details);