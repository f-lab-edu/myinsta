create table comment_image(
	id_comment_image bigint auto_increment not null primary key,
    id_comment bigint not null,
    constraint fk_comment_image_comment foreign key(id_comment) references comment_(id_comment),
    image_path varchar(4069), -- 파일 경로 linux system 최대 4069 바이트 지원
    created_date timestamp,
    updated_date timestamp
);