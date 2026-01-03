create table payments (
  id uuid primary key,
  kind varchar(32) not null,
  customer_id varchar(64) not null,
  from_account varchar(64),
  to_account varchar(64),
  amount_minor bigint not null,
  currency varchar(3) not null,
  reference varchar(64) not null,
  status varchar(32) not null,
  hold_id uuid,
  created_at timestamptz
);

create index idx_payments_customer on payments(customer_id);
