package com.crud.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.crud.model.MahasiswaModel;

public interface MahasiswaService {

    public List<MahasiswaModel> getAllMahasiswa();

    public void saveMahasiswa(MahasiswaModel mahasiswa);

    public MahasiswaModel getMahasiswaById(int id) throws Exception;

    public void deleteMahasiswa(int id);

    public Page<MahasiswaModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
