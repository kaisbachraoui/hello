create extension if not exists "uuid-ossp";

create table user_accounts (
    id uuid primary key default uuid_generate_v4(),
    username varchar(255) not null unique,
    password_hash varchar(255) not null
);

create table user_roles (
    user_id uuid not null references user_accounts(id) on delete cascade,
    role varchar(100) not null,
    primary key (user_id, role)
);

create table risks (
    risk_id varchar(100) primary key,
    domain varchar(255) not null,
    title varchar(255) not null,
    description text,
    typical_finding_examples text,
    annex_a_mapping text
);

create table projects (
    id uuid primary key default uuid_generate_v4(),
    name varchar(255) not null,
    description text,
    business_owner varchar(255) not null,
    technical_owner varchar(255) not null,
    start_date date,
    go_live_date date,
    status varchar(100) not null
);

create table findings (
    id uuid primary key default uuid_generate_v4(),
    project_id uuid not null references projects(id) on delete cascade,
    risk_id varchar(100) not null references risks(risk_id),
    title varchar(255) not null,
    description text,
    severity varchar(50) not null,
    likelihood varchar(50) not null,
    impact varchar(50) not null,
    risk_level varchar(50) not null,
    status varchar(50) not null,
    owner varchar(255) not null,
    due_date date,
    created_at timestamp with time zone not null
);

create table finding_control_tags (
    finding_id uuid not null references findings(id) on delete cascade,
    control_tag varchar(100) not null
);

create table finding_comments (
    id uuid primary key default uuid_generate_v4(),
    finding_id uuid not null references findings(id) on delete cascade,
    author varchar(255) not null,
    comment text not null,
    created_at timestamp with time zone not null
);

create table finding_history (
    id uuid primary key default uuid_generate_v4(),
    finding_id uuid not null references findings(id) on delete cascade,
    actor varchar(255) not null,
    from_status varchar(50),
    to_status varchar(50),
    action varchar(100) not null,
    details text,
    created_at timestamp with time zone not null
);

create table evidence (
    id uuid primary key default uuid_generate_v4(),
    finding_id uuid not null references findings(id) on delete cascade,
    filename varchar(255) not null,
    content_type varchar(255) not null,
    size bigint not null,
    storage_path text not null,
    url text,
    created_at timestamp with time zone not null
);

insert into user_accounts (id, username, password_hash)
values ('00000000-0000-0000-0000-000000000001', 'admin', '$2a$10$7QJQFqV7Yb9Q9rG1xJzO/ODrEJ1o1t4bm/F/6HC8K3opgDdGztJzK');

insert into user_roles (user_id, role)
values ('00000000-0000-0000-0000-000000000001', 'ADMIN');

insert into risks (risk_id, domain, title, description, typical_finding_examples, annex_a_mapping)
values
    ('RISK-APP-001', 'Application Security', 'Insecure Authentication', 'Weak authentication controls.', 'Hard-coded credentials', 'A.9.4'),
    ('RISK-APP-002', 'Application Security', 'Injection Flaws', 'Injection vulnerabilities.', 'SQL injection', 'A.14.2');

insert into projects (id, name, description, business_owner, technical_owner, start_date, go_live_date, status)
values
    ('00000000-0000-0000-0000-000000000010', 'Payments Platform', 'Modernize payment gateway.', 'Alex Morgan', 'Riley Chen', '2024-01-01', '2024-10-01', 'In Progress');

insert into findings (id, project_id, risk_id, title, description, severity, likelihood, impact, risk_level, status, owner, due_date, created_at)
values
    ('00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000010', 'RISK-APP-002', 'SQL injection in search', 'Unsanitized inputs.', 'HIGH', 'LIKELY', 'HIGH', 'HIGH', 'DRAFT', 'Jordan Lee', '2024-12-31', now());
