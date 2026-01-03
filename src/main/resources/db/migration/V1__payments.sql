create table payments (
  id uuid primary key,
  customer_id varchar(64),
  from_account varchar(64),
  to_account varchar(64),
  amount_minor bigint,
  currency varchar(3),
  status varchar(32),
  created_at timestamptz
);
