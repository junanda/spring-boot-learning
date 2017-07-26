DELETE FROM peserta_pelatihan;
DELETE FROM sesi;

INSERT INTO sesi (id, id_materi, mulai, sampai) VALUES ("aa", "aa6", "2015-01-01", "2015-01-05");
INSERT INTO sesi (id, id_materi, mulai, sampai) VALUES ("ab", "aa6", "2015-01-08", "2015-01-12");
INSERT INTO sesi (id, id_materi, mulai, sampai) VALUES ("ac", "aa7", "2015-01-01", "2015-01-05");

INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("aa", "aa1");
INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("aa", "aa2");
INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("aa", "aa3");

INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("ab", "aa2");
INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("ab", "aa3");

INSERT INTO peserta_pelatihan (id_sesi, id_peserta) VALUES ("ac", "aa2")