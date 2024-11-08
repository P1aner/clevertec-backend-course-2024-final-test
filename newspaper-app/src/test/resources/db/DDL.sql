
CREATE TABLE news
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    local_date_time timestamp(6) without time zone,
    username character varying(255),
    text character varying(2048),
    title character varying(255),
    CONSTRAINT news_pkey PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    local_date_time timestamp(6) without time zone,
    news_id bigint NOT NULL,
    text character varying(255),
    username character varying(255),
    CONSTRAINT comment_pkey PRIMARY KEY (id),
    CONSTRAINT fk_comment_news FOREIGN KEY (news_id)
        REFERENCES public.news (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)