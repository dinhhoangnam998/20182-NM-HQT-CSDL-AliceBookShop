use alicebookshop;
drop trigger if exists after_insert_book_input;
delimiter //

create trigger after_insert_book_input 
after insert on book_input
for each row
begin

  update book set remain_quantity = remain_quantity + new.quantity
  where id = new.book_id;

end //

delimiter ;