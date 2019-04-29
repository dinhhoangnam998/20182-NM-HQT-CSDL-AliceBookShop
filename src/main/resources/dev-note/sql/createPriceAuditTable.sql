use alicebookshop;
create table book_price_audit (
	id int auto_increment primary key,
    book_id int not null,
    old_price int not null,
    new_price int not null,
    change_date datetime default null
);