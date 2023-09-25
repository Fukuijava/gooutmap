CREATE TABLE IF NOT EXISTS golist(
    golist_id varchar(8) primary key,
    pref varchar(16),
    city varchar(16),
    genre varchar(16),
    move_means varchar(8)
);
CREATE TABLE IF NOT EXISTS my_home(
    my_home_id varchar(8) primary key,
    coordinate varchar(128)
);