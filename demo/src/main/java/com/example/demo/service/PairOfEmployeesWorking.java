package com.example.demo.service;

import com.example.demo.model.CsvRecord;
import com.example.demo.model.PairOfEmployees;
import com.example.demo.repository.CsvRecordRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PairOfEmployeesWorking {
    private final CsvRecordRepositoryImpl csvRecordRepository;

    @Autowired
    public PairOfEmployeesWorking(CsvRecordRepositoryImpl csvRecordRepository) {
        this.csvRecordRepository = csvRecordRepository;
    }

    private boolean hasOverlap(CsvRecord first, CsvRecord second){
        return ((first.getDateFrom().isBefore(second.getDateTo()) || first.getDateFrom().isEqual(second.getDateTo())) &&
                (second.getDateFrom().isBefore(first.getDateTo()) || second.getDateFrom().isEqual(first.getDateTo())));
    }

    private Long calculateDaysOverlap(CsvRecord first, CsvRecord second){
        LocalDate startDateFirst = first.getDateFrom();
        LocalDate endDateFirst = first.getDateTo();
        LocalDate startDateSecond = second.getDateFrom();
        LocalDate endDateSecond = second.getDateTo();

        LocalDate startDateOverlap = startDateFirst.isAfter(startDateSecond) ? startDateFirst : startDateSecond;
        LocalDate endDateOverLap = endDateFirst.isBefore(endDateSecond) ? endDateFirst : endDateSecond;

        return Math.abs(ChronoUnit.DAYS.between(startDateOverlap, endDateOverLap));
    }

    private String getPairKey(Long firstId, Long secondId){
        return (firstId < secondId) ? (firstId + " - " + secondId) : (secondId + " - " + firstId);
    }

    public List<CsvRecord> getAllRecords(){
        return csvRecordRepository.getAllRecords();
    }

    public PairOfEmployees findLongestPair(List<CsvRecord> records){
        PairOfEmployees longestPairOfEmployees = null;
        Map<String, PairOfEmployees> allPairsOfWorkers = new HashMap<>();

        for(int i = 0; i < records.size() - 1; ++i){
            for(int j = i + 1; j < records.size(); ++j){
                CsvRecord first = records.get(i);
                CsvRecord second = records.get(j);

                if(first.getEmployeeId() == second.getEmployeeId()){
                    continue;
                }

                if((first.getProjectId() == second.getProjectId()) &&
                    hasOverlap(first, second)){

                    Long periodOverlap = calculateDaysOverlap(first, second);

                    String pairKey = getPairKey(first.getEmployeeId(), second.getEmployeeId());
                    PairOfEmployees pairOfEmployees = allPairsOfWorkers.get(pairKey);

                    if(periodOverlap > 0){
                        if(pairOfEmployees == null){
                            pairOfEmployees = new PairOfEmployees(first.getEmployeeId(), second.getEmployeeId(), periodOverlap);
                            allPairsOfWorkers.put(pairKey, pairOfEmployees);

                        } else{
                            pairOfEmployees.addOverlap(periodOverlap);
                        }
                    }

                    if(longestPairOfEmployees == null || longestPairOfEmployees.getTotalDuration() < periodOverlap){
                        longestPairOfEmployees = pairOfEmployees;
                    }
                }
            }
        }

        return longestPairOfEmployees;
    }

    public void loadRecordsFromCsv(MultipartFile file){
        csvRecordRepository.loadRecordsFromCsv(file);
    }

}
