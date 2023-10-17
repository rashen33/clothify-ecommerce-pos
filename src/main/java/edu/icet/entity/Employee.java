package edu.icet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String empID;
    private String empTitle;
    private String empName;
    private String empNic;
    private String empDob;
    private String empAddress;
    private String empContact;
    private String empBankAcNo;
    private String empBranch;
}
