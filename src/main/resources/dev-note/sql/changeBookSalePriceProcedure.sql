use alicebookshop;
drop procedure if exists changeSalePrice;
delimiter //
create procedure changeSalePrice(in bookId int, in salePercent int)
begin

update book
set sale_price = cover_price * (100 - salePercent) / 100 
where id = bookId;

end//
delimiter ;