package com.patihullah.junanda.pelatihan.dao;

import com.patihullah.junanda.pelatihan.entity.Materi;
import com.patihullah.junanda.pelatihan.entity.Sesi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface SesiDao extends PagingAndSortingRepository<Sesi, String> {
    //menggunakan spring data jpa query method
    //page salah satu fitur dari hibernate agar bisa menampilin data dengan paging

    public Page<Sesi> findByMateri(Materi m, Pageable page);

    //menggunakan query sendiri
    @Query("select x from Sesi x where x.mulai >= :m " +
            "and x.mulai < :s " +
            "and x.materi.kode = :k " +
            "order by x.mulai desc ")
    public Page<Sesi> cariBerdasarkanTanggalMulaiDanKodeMateri(
            @Param("m") Date mulai,
            @Param("s") Date sampai,
            @Param("k") String kode,
            Pageable page);
}
