package com.patihullah.junanda.pelatihan.dao;

import com.patihullah.junanda.pelatihan.entity.Peserta;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/data/peserta.sql"
)
public class PesertaDaoTest {

    @Autowired
    private PesertaDao pd;

    @Qualifier("dataSource")
    @Autowired
    private DataSource ds;

    // dijalankan setelah test
    @After
    public void hapusData() throws Exception {
        String sql = "delete from peserta where email='peserta0001@gmail.com'";

        try(Connection c = ds.getConnection()) {
            c.createStatement().executeUpdate(sql);
        }
    }

    @Test
    public void testInsert() throws SQLException{
        /**
        * membuat tester untuk interface peserta yang telah dibuat
        * */
        Peserta p = new Peserta();

        p.setNama("Peserta 0001");
        p.setEmail("peserta0001@gmail.com");
        p.setTanggalLahir(new Date());

        pd.save(p);

        String sql = "select count(*) as jumlah from peserta where email='peserta0001@gmail.com'";

        Connection c = ds.getConnection();

        ResultSet rs = c.createStatement().executeQuery(sql);
        Assert.assertTrue(rs.next());

        Long jumlahRow = rs.getLong("jumlah");
        Assert.assertEquals(1L, jumlahRow.longValue());

        c.close();
    }

    @Test
    public void testHitung(){
        Long jumlah = pd.count();
        Assert.assertEquals(3L, jumlah.longValue());
    }

    @Test
    public void testCariById(){
        Peserta p = pd.findOne("aa");
        Assert.assertNotNull(p);
        Assert.assertEquals("Peserta test 001", p.getNama());
        Assert.assertEquals("peserta.test.001@gmail.com", p.getEmail());

        Peserta px = pd.findOne("xx");
        Assert.assertNull(px);
    }
}
