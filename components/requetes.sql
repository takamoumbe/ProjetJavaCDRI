CREATE TABLE "user" (
	"idUser"	INTEGER NOT NULL,
	"nomUser"	TEXT,
	"login"	TEXT,
	"password"	TEXT,
	"contact"	TEXT,
	"typeUser"	TEXT,
	"etatUser"	INTEGER,
	"statutUser"	TEXT,
	"createAt"	TEXT,
	"updateAt"	TEXT,
	"deleteAt"	TEXT,
	PRIMARY KEY("idUser" AUTOINCREMENT)
);

CREATE TABLE "client" (
	"idClient"	INTEGER NOT NULL,
	"nomClient"	TEXT,
	"contactClient"	TEXT,
	"idUser"	INTEGER,
	PRIMARY KEY("idClient" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "fournisseur" (
	"idFournisseur"	INTEGER NOT NULL,
	"nomFournisseur"	TEXT,
	"addressFournisseur"	TEXT,
	"villeFournisseur"	TEXT,
	"paysFournisseur"	TEXT,
	"telFournisseur"	TEXT,
	"etatFournisseur"	INTEGER DEFAULT 0,
	"idUser"	INTEGER,
	PRIMARY KEY("idFournisseur" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES "user"("idUser") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "magasin" (
	"idMagasin"	INTEGER NOT NULL,
	"numMagasin"	TEXT,
	"nomMagasin"	TEXT,
	"idUser"	INTEGER,
	PRIMARY KEY("idMagasin" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "categorie" (
	"idCategorie"	INTEGER NOT NULL,
	"nomCategorie"	TEXT,
	"descriptionCategoerie"	TEXT,
	"idUser"	INTEGER,
	PRIMARY KEY("idCategorie" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE "article" (
	"idArticle"	INTEGER NOT NULL,
	"codeArticle"	TEXT,
	"nomArticle" TEXT,
	"designationArticle"	TEXT,
	"quantiteArticle"	INTEGER,
	"prixArticle"	INTEGER,
	"emplacement"	TEXT,
	"createAt"	TEXT,
	"updateAt"	TEXT,
	"deleteAt"	TEXT,
	"nbrePaquet"	INTEGER,
	"etatArticle"	INTEGER DEFAULT 0,
	"idUser"	INTEGER,

	PRIMARY KEY("idArticle" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE "article_fournisseur" (
	"idArticle"	INTEGER NOT NULL,
	"idFournisseur"	INTEGER NOT NULL,

	FOREIGN KEY("idArticle") REFERENCES article(idArticle) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idFournisseur") REFERENCES fournisseur(idFournisseur) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE "entree" (
	"idEntree"	INTEGER NOT NULL,
	"quantiteEntrer"	INTEGER,
	"dateEntree"	TEXT,
	"prixEntrer"	INTEGER,
	"idArticle"	INTEGER,
	"idUser"	INTEGER,
	PRIMARY KEY("idEntree" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idArticle") REFERENCES article(idArticle) ON UPDATE CASCADE ON DELETE CASCADE

	);

CREATE TABLE "sortie" (
	"idSortie"	INTEGER NOT NULL,
	"quantiteSortie"	INTEGER,
	"dateSortie"	TEXT,
	"prixSortir"	INTEGER,
	"idArticle"	INTEGER,
	"idUser"	INTEGER,
	PRIMARY KEY("idSortie" AUTOINCREMENT),
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idArticle") REFERENCES article(idArticle) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "articleMagasin" (
	"idArticle"	INTEGER,
	"idMagasin"	INTEGER,
	"idUser"	INTEGER,
	FOREIGN KEY("idUser") REFERENCES user(idUser) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idMagasin") REFERENCES magasin(idMagasin) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idArticle") REFERENCES article(idArticle) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "commande" (
	"idCommande"	INTEGER NOT NULL,
	"num_commande"  TEXT,
	"dateCommande"	TEXT,
	"etatCommande"	INTEGER,
	"quantiteComande"	INTEGER,
	"prix_commande"	INTEGER,
	"idClient"	INTEGER,
	"idArticle"	INTEGER,
	PRIMARY KEY("idCommande" AUTOINCREMENT),
	FOREIGN KEY("idClient") REFERENCES client(idClient) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("idArticle") REFERENCES article(idArticle) ON UPDATE CASCADE ON DELETE CASCADE
);