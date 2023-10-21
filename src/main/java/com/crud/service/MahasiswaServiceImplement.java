package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.crud.model.MahasiswaModel;
import com.crud.repository.MahasiswaRepository;

@Service
public class MahasiswaServiceImplement implements MahasiswaService {
    @Autowired
    private MahasiswaRepository repoMhs;

    @Override
    public List<MahasiswaModel> getAllMahasiswa() {
        return (List<MahasiswaModel>) repoMhs.findAll();
    }

    @Override
    public void saveMahasiswa(MahasiswaModel mahasiswa) {
        this.repoMhs.save(mahasiswa);
    }

    @Override
    public MahasiswaModel getMahasiswaById(int id) {
        Optional<MahasiswaModel> optional = repoMhs.findById(id);
        MahasiswaModel mhs = null;
        if (optional.isPresent()) {
            mhs = optional.get();
        } else {
            throw new RuntimeException("Mahasiswa tidak dengan id " + id + " tidak ditemukan");
        }
        return mhs;
    }

    @Override
    public void deleteMahasiswa(int id) {
        this.repoMhs.deleteById(id);
    }

    @Override
    public Page<MahasiswaModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repoMhs.findAll(pageable);
    }

}
