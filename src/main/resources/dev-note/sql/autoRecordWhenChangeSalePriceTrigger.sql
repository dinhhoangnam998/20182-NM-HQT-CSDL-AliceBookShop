use alicebookshop;
drop trigger if exists before_update_book_price;
delimiter //

create trigger before_update_book_price 
before update on book
for each row

begin
  if (old.sale_price != new.sale_price) then
    insert into book_price_audit (book_id, old_price, new_price, change_date)
    value (old.id, old.sale_price, new.sale_price, now());
  end if;
end //

delimiter ;