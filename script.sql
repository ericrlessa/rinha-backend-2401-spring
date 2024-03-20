create sequence Transaction_SEQ start with 1 increment by 50;

create UNLOGGED table Transaction (
    id bigint not null,
    value integer,
    client_id bigint not null,
    executed_on timestamp(6),
    description varchar(255),
    type varchar(255),
    primary key (id)
);

create UNLOGGED table Client (
    id bigint not null,
    limit_value bigint,
    account_balance bigint,
    primary key (id)
);

CREATE INDEX ix_transaction_idclient ON Transaction
(
    client_id
);

  insert into Client (limit_value, account_balance, id) values (100000, 0, 1);
  insert into Client (limit_value, account_balance, id) values (80000, 0, 2);
  insert into Client (limit_value, account_balance, id) values (1000000, 0, 3);
  insert into Client (limit_value, account_balance, id) values (10000000, 0, 4);
  insert into Client (limit_value, account_balance, id) values (500000, 0, 5);