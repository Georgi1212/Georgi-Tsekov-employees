package com.example.demo.repository;

import com.example.demo.model.CsvRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvRecordRepository {

    List<CsvRecord> getAllRecords();
    void loadRecordsFromCsv(MultipartFile file);
}
