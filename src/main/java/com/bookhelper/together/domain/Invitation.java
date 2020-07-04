package com.bookhelper.together.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Invitation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;
    //@OneToOne(mappedBy = "invitation", fetch = FetchType.LAZY)
    private String code;
    private boolean available = true;


}
