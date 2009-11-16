DROP TABLE usersitecredential if exists;

CREATE TABLE usersitecredential
(
  uid character varying(20) NOT NULL,
  sitekey character varying(50) NOT NULL,
  siteuser character varying(256),
  sitepassword character varying(20),
  CONSTRAINT vaultkey PRIMARY KEY (uid, sitekey)
);
