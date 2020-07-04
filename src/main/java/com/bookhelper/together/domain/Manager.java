package com.bookhelper.together.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Manager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    private String name;
    private String phoneNumber;


/*
    @OneToMany(mappedBy = "manager")
    private List<Room> rooms = new ArrayList<>();
*/


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;



}
