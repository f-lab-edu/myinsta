create table post_image(
	id_post_image bigint auto_increment not null primary key,
    id_post bigint not null,
    constraint fk_post_image_post foreign key(id_post) references post(id_post),
    image_path varchar(4069), -- 파일 경로 linux system 최대 4069 바이트 지
    created_date timestamp,
    updated_date timestamp
);