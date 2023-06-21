package com.example.demo.controller;

import com.example.demo.model.CsvRecord;
import com.example.demo.model.PairOfEmployees;
import com.example.demo.service.PairOfEmployeesWorking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping
public class CsvController {
    private final PairOfEmployeesWorking pairOfEmployeesWorking;

    @Autowired
    public CsvController(PairOfEmployeesWorking pairOfEmployeesWorking) {
        this.pairOfEmployeesWorking = pairOfEmployeesWorking;
    }

    @GetMapping
    public String showUploadForm(){
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("error", "No file uploaded");
            return "upload-form";
        }

        try{
            pairOfEmployeesWorking.loadRecordsFromCsv(file);
            return "redirect:/records";
        } catch (Exception e){
            model.addAttribute("error", "Failed to process the file");
            return "upload-form";
        }
    }

    @GetMapping("/records")
    public String showRecords(Model model){
        List<CsvRecord> records = pairOfEmployeesWorking.getAllRecords();
        PairOfEmployees longestPairOfEmployees = pairOfEmployeesWorking.findLongestPair(records);

        model.addAttribute("records", records);
        model.addAttribute("longestPair", longestPairOfEmployees);

        return "records";
    }
}
