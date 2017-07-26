package com.patihullah.junanda.pelatihan.dao;

import com.patihullah.junanda.pelatihan.entity.Materi;
import com.patihullah.junanda.pelatihan.entity.Peserta;
import com.patihullah.junanda.pelatihan.entity.Sesi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/data/peserta.sql", "/data/materi.sql", "/data/sesi.sql"}
)
public class SesiDaoTest {

    @Autowired
    private SesiDao sd;

    @Qualifier("dataSource")
    @Autowired
    private DataSource ds;

    @Test
    public void testCariByMateri(){
        Materi m = new Materi();
        m.setId("aa6");

        PageRequest page = new PageRequest(0,5);

        Page<Sesi> hasilQuery = sd.findByMateri(m, page);
        Assert.assertEquals(2L, hasilQuery.getTotalElements());

        Sesi s = hasilQuery.getContent().get(0);
        Assert.assertNotNull(s);
        Assert.assertEquals("Java Fundamental", s.getMateri().getNama());
    }

    @Test
    public void testCariBerdasarkanTanggalMulaiDanKodeMateri() throws Exception{
        PageRequest page = new PageRequest(0,5);

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formater.parse("2015-01-01");
        Date sampai = formater.parse("2015-01-03");

        Page<Sesi> hasil = sd.cariBerdasarkanTanggalMulaiDanKodeMateri(
                sejak, sampai, "JF-002", page
        );
        Assert.assertEquals(1L, hasil.getTotalElements());
        Assert.assertFalse(hasil.getContent().isEmpty());

        Sesi s = hasil.getContent().get(0);
        Assert.assertEquals("Java Web", s.getMateri().getNama());
    }

    //Contoh save Many to Many
    @Test
    public void testSaveSesi() throws Exception{
        Peserta p1 = new Peserta();
        p1.setId("aa1");

        Peserta p2 = new Peserta();
        p2.setId("aa2");

        Materi m = new Materi();
        m.setId("aa8");

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formater.parse("2017-02-01");
        Date akhir = formater.parse("2017-02-05");

        Sesi s = new Sesi();
        s.setMateri(m);
        s.setMulai(sejak);
        s.setSampai(akhir);
        s.getDaftarPeserta().add(p1);
        s.getDaftarPeserta().add(p2);

        sd.save(s);
        String idSesiBaru = s.getId();
        Assert.assertNotNull(idSesiBaru);
        System.out.println("ID Baru : "+s.getId());

        String sql = "select count(*) from sesi where id_materi = 'aa8'";
        String sqlManyToMany = "select count(*) from peserta_pelatihan where id_sesi=?";

        try(Connection c = ds.getConnection()){
            //Cek tabel sesi
            ResultSet rs = c.createStatement().executeQuery(sql);
            Assert.assertTrue(rs.next());
            Assert.assertEquals(1L, rs.getLong(1));

            //cek tabel relasi many to many
            PreparedStatement ps = c.prepareStatement(sqlManyToMany);
            ps.setString(1, idSesiBaru);
            ResultSet rs2 = ps.executeQuery();

            Assert.assertTrue(rs2.next());
            Assert.assertEquals(2L, rs2.getLong(1));
        }

    }
}
