use alicebookshop;
drop procedure if exists getTotalOrderCharge;

delimiter //
create procedure getTotalOrderCharge(in orderId int, out totalCharge int)
begin
  declare finish, totalLine int default 0;

  declare orderLine cursor for
  select total_line
  from orders join order_line on orders.id = order_line.order_id
  where orders.id = orderId;

  declare continue handler for not found set finish = 1;

  set totalCharge = 0;

  open orderLine;
    my_label: loop

      fetch orderLine into totalLine;

      if finish = 1 then
        leave my_label;
      end if;

      set totalCharge = totalCharge + totalLine;
      
    end loop my_label;
  close orderLine;

end//
delimiter ;