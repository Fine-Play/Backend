//package com.fineplay.fineplaybackend.mypage.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "User_Stats_oldversion")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserStats {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name="User_ID", nullable = false)
//    private Long userId;
//
//    private String position;
//    private String selectedStat;
//
//    // Common Stats
//    private int SPD;
//    private int PAS;
//    private int PAC;
//
//    // Position Stats (FW)
//    private int SHO;
//    private int DRV;
//
//    // Position Stats (MF)
//    @Column(name = "`DEC`")
//    private int DEC;
//
//    private int DRI;
//
//    // Position Stats (DF)
//    private int TAC;
//    private int BLD;
//
//    // Special Stats
//    private int CRO;
//    private int HED;
//    private int FST;
//    private int ACT;
//    private int OFF;
//    private int TEC;
//    private int COP;
//
//    private String OVR;
//}
