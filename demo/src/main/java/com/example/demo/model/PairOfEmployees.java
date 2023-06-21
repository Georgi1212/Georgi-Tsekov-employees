package com.example.demo.model;

public class PairOfEmployees {
    private long employeeId1;
    private long employeeId2;
    private long totalDuration;

    public PairOfEmployees(Long employeeId1, Long employeeId2, Long totalDuration) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
        this.totalDuration = totalDuration;
    }

    public long getEmployeeId1() {
        return employeeId1;
    }

    public void setEmployeeId1(long employeeId1) {
        this.employeeId1 = employeeId1;
    }

    public long getEmployeeId2() {
        return employeeId2;
    }

    public void setEmployeeId2(long employeeId2) {
        this.employeeId2 = employeeId2;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void addOverlap(Long overlap){
        this.totalDuration += overlap;
    }
}
