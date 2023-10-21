package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.model.MahasiswaModel;
import com.crud.service.MahasiswaService;

@Controller
public class MahasiswaController {
    @Autowired
    private MahasiswaService mhs;

    @GetMapping("/getAllData")
    public String viewHomePage(Model model) {
        return this.findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/showMahasiswaAddForm")
    public String showAddMahasiswaForm(Model model) {
        MahasiswaModel mhs = new MahasiswaModel();
        model.addAttribute("mahasiswa", mhs);
        return "addMahasiswaForm";
    }

    @PostMapping("/addMahasiswa")
    public String saveMahasiswa(@ModelAttribute("mahasiswa") MahasiswaModel mhs) {
        this.mhs.saveMahasiswa(mhs);
        return "redirect:/getAllData";
    }

    @GetMapping("/updateForm/{id}")
    public String updateDataMahasiswa(@PathVariable(value = "id") int id, Model model) throws Exception {
        MahasiswaModel mhsModel = mhs.getMahasiswaById(id);
        model.addAttribute("mahasiswa", mhsModel);
        return "updateMahasiswa";
    }

    @GetMapping("/deleteForm/{id}")
    public String deleteDataMahasiswa(@PathVariable(value = "id") int id) {
        this.mhs.deleteMahasiswa(id);
        return "redirect:/getAllData";
    }

    @GetMapping("/page/{pageNum}")
    public String findPaginated(@PathVariable(value = "pageNum") int pageNum,
            @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 5;
        Page<MahasiswaModel> page = mhs.findPaginated(pageNum, pageSize, sortField, sortDir);
        List<MahasiswaModel> listMahasiswa = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("listMahasiswa", listMahasiswa);

        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "index";
    }

}
