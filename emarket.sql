
--
-- Base de données: emarket
--
-- --------------------------------------------------------

drop table categories_produits;
drop table clients;
drop table fournisseurs;
drop table produits;
drop table commandes;
drop table users;
--
-- Structure de la table categories_produits
--

CREATE TABLE categories_produits (
  id_categ integer PRIMARY KEY,
  nom_categ varchar(50) NOT NULL
);

-- Structure de la table clients

CREATE TABLE clients (
  Id_c integer PRIMARY KEY,
  prenom_c varchar(100) NOT NULL,
  nom_c varchar(100) NOT NULL,
  ville_c varchar(100) NOT NULL,
  date_naissance date NOT NULL
) ;


--
-- Structure de la table fournisseurs
--
CREATE TABLE fournisseurs (
  id_f integer PRIMARY KEY,
  nom_f varchar(255) NOT NULL,
  prenom_f varchar(255) NOT NULL,
  ville_f  varchar(255) NOT NULL,
  societe varchar(255) NOT NULL
);

-- Structure de la table produits
--

CREATE TABLE produits (
  id_p integer PRIMARY KEY,
  libelle varchar(100) NOT NULL,
  prix float NOT NULL,
  quatite_stock integer NOT NULL,
  id_categ integer references categories_produits (id_categ) NOT NULL,
  id_fournisseur integer references fournisseurs (id_f) NOT NULL 
) ;

-- Structure de la table commandes
--

CREATE TABLE commandes (
  Id_commande integer PRIMARY KEY,
  num_commande integer  NOT NULL,
  id_p integer references produits(id_p) NOT NULL,
  id_c integer references clients (id_c) NOT NULL,
  qte_achete integer  NOT NULL,
  prix_total float NOT NULL,
  date date NOT NULL,
  ncli 	integer references client(ncli)
);

-- Structure de la table users
--

CREATE TABLE users (
  id_user serial PRIMARY KEY,
  nom_user varchar(50) NOT NULL,
  prenom_user varchar(50) NOT NULL,
  email_user varchar(50) NOT NULL,
  password_user varchar(50) NOT NULL
);
--
-- Contraintegeres pour la table commandes
--
ALTER TABLE commandes
  ADD CONSTRAINT commandes_ibfk_1 FOREIGN KEY (id_p) REFERENCES produits (id_p) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT commandes_ibfk_2 FOREIGN KEY (id_c) REFERENCES clients (Id_c) ON DELETE CASCADE ON UPDATE CASCADE;


--
-- Contraintegeres pour la table produits
--
ALTER TABLE produits
  ADD CONSTRAINT produits_ibfk_1 FOREIGN KEY (id_fournisseur) REFERENCES fournisseurs (id_fournisseur) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT produits_ibfk_2 FOREIGN KEY (id_categ) REFERENCES categories_produits (id_categ) ON DELETE CASCADE ON UPDATE CASCADE;

