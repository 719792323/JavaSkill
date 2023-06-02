PRAGMA foreign_keys = false;

DROP TABLE IF EXISTS "entry";
CREATE TABLE "entry" (
                         "index" integer NOT NULL,
                         "term" integer NOT NULL,
                         "kind" integer NOT NULL,
                         "command" blob,
                         PRIMARY KEY ("index", "term", "kind")
);


DROP TABLE IF EXISTS "meta";
CREATE TABLE "meta" (
                        "key" text NOT NULL,
                        "value" text,
                        PRIMARY KEY ("key")
);

PRAGMA foreign_keys = true;