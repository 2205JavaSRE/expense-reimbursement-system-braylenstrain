CREATE TABLE employees(
	id serial PRIMARY KEY,
	first_name text NOT NULL,
	last_name text NOT NULL,
	username text NOT NULL UNIQUE CHECK(LENGTH(username) > 0),
	password text  NOT NULL CHECK(LENGTH(password) > 0),
	finance_manager boolean NOT NULL
);

CREATE TYPE category AS ENUM ('LODGING', 'TRAVEL', 'FOOD', 'OTHER');
CREATE TYPE status AS ENUM ('PENDING', 'APPROVED', 'DENIED');
CREATE TABLE reimbursements_log(
	id serial PRIMARY KEY,
	time_of_action timestamp DEFAULT current_timestamp,
	action_details text NOT NULL,
	employee_id int NOT NULL,
	employee_first_name text NOT NULL,
	employee_last_name text NOT NULL,
	reimbursement_category category NOT NULL,
	amount numeric NOT NULL CHECK(amount > 0),
	reimbursement_status status NOT NULL
);