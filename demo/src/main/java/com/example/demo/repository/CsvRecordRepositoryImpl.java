package com.example.demo.repository;

import com.example.demo.model.CsvRecord;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvRecordRepositoryImpl implements CsvRecordRepository{

    private List<CsvRecord> records;

    public CsvRecordRepositoryImpl(){
        this.records = new ArrayList<>();
    }

    @Override
    public void loadRecordsFromCsv(MultipartFile file) {
        List<CsvRecord> records = new ArrayList<>();

        //String filePath = "src/main/java/com/example/demo/file/text.csv";
        final String separator = ",";

        try(InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            bufferedReader.readLine(); //skip the line, which is for the headers for the columns

            while((line = bufferedReader.readLine()) != null){
                String[] splitLine = line.split(separator);

                CsvRecord csvRecord = new CsvRecord(Long.parseLong(splitLine[0]), Long.parseLong(splitLine[1]),
                        LocalDate.parse(splitLine[2]), (splitLine[3].equals("NULL")) ? LocalDate.now() : LocalDate.parse(splitLine[3]));

                records.add(csvRecord);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.records = records;
    }

    @Override
    public List<CsvRecord> getAllRecords(){
        return this.records;
    }
}
