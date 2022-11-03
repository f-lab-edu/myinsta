create table account_(
	id_account bigint not null auto_increment primary key,
    nick_name varchar(48), -- 16글자
    email varchar(150), -- 구글 계정 30자까지 가능 + 이메일 부분(20)
    password_ varchar(48),
    created_date timestamp,
    updated_date timestamp
);
