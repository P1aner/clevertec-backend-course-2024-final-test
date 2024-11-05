DELETE FROM comment;
ALTER sequence comment_id_seq restart WITH 1;
DELETE FROM news;
ALTER sequence news_id_seq restart WITH 1;
