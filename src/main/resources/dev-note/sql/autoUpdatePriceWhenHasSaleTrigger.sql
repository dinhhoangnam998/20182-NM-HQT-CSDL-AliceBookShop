use alicebookshop;
drop trigger if exists after_insert_book_sale;
drop trigger if exists after_update_book_sale;

delimiter //
create trigger after_insert_book_sale 
after insert on book_sale 
for each row
begin
	call changeSalePrice(new.book_id, new.percent);
end//

create trigger after_update_book_sale 
after update on book_sale 
for each row
begin
	call changeSalePrice(new.book_id, new.percent);
end//
delimiter ;