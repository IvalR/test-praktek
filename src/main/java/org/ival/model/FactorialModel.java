package org.ival.model;

import javax.persistence.*;

@Entity
@Table(name = "factorial")
public class FactorialModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "n")
    public Integer n;

    @Column(name = "factorial")
    public Integer factorial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getFactorial() {
        return factorial;
    }

    public void setFactorial(Integer factorial) {
        this.factorial = factorial;
    }


}
