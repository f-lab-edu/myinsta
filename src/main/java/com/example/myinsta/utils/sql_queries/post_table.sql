create table post(
	id_post bigint auto_increment not null primary key,
    id_account bigint not null,
    foreign key(id_account) references account_(id_account),
    title varchar(150), -- 글 제목 50자 
    created_date timestamp,
    updated_date timestamp
);