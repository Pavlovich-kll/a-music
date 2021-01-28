CREATE OR REPLACE FUNCTION notification_function()
RETURNS TRIGGER
AS $$
BEGIN
FOR i IN 1..(SELECT COUNT(1) FROM notification_metadata) LOOP
	INSERT INTO notification(user_id, notification_metadata_id) VALUES (NEW.id, i);
END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER notification_trigger
    AFTER INSERT ON users
    FOR EACH ROW
    EXECUTE PROCEDURE notification_function();
