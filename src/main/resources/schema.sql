CREATE TABLE IF NOT EXISTS golist(
    golist_id varchar(8) primary key,
    pref varchar(16),
    city varchar(16),
    genre varchar(16),
    move_means varchar(8)
);

CREATE TABLE IF NOT EXISTS maplist(
    maplist_id varchar(8) primary key,
    golist_id varchar(8) not null,
    latitude varchar(64),
    longitude varchar(64),
    FOREIGN KEY fk_golist(golist_id)
    REFERENCES golist(golist_id)
);