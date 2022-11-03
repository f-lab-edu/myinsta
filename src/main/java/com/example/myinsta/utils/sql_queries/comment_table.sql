create table comment_(
	id_comment bigint auto_increment not null primary key,
    id_account bigint not null,
    id_post bigint not null,
    constraint fk_comment_post foreign key(id_post) references post(id_post),
    constraint fk_comment_account foreign key(id_account) references account_(id_account),
    contents varchar(1500), -- 글내용 500자 
    created_date timestamp,
    updated_date timestamp
);