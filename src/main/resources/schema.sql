CREATE TABLE IF NOT EXISTS golist(
    id varchar(8) primary key,
    pref varchar(16),
    city varchar(16),
    genre varchar(16),
    move_means varchar(8)
);

CREATE TABLE IF NOT EXISTS maplist(
    id varchar(8) primary key,
    golist_id varchar(8),
    latitude varchar(64),
    longitude varchar(64),
     CONSTRAINT fk_golist_id
        FOREIGN KEY (id)
        REFERENCES golist (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
);