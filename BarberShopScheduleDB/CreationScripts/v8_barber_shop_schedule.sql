ALTER TABLE barberShop
ADD COLUMN deviceToken text;

ALTER TABLE client
ADD COLUMN deviceToken text;

ALTER TABLE appointment
ADD COLUMN pendingConfirmation int DEFAULT 0;

