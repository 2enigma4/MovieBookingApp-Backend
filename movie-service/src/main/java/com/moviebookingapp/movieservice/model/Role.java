package com.moviebookingapp.movieservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Role {
	

    private Integer id;
    private String name;
 
    @Override
    public String toString() {
        return this.name;
    }

    public Role(String name) {
        this.name = name;
    }
     
    public Role(Integer id) {
        this.id = id;
    }
     
}