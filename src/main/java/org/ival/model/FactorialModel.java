package org.ival.model;

import javax.persistence.*;

@Entity
public class FactorialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Integer n;

    public Integer factorial;

}
