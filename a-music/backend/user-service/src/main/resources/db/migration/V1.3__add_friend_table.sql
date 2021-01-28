create table if not exists friends (
    id serial not null primary key,
    friend_id bigint references users(id) on delete cascade,
    user_id bigint references users(id) on delete cascade,
    status text,
    check (friend_id != user_id)
);
