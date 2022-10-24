package com.example.ncc.meta_model;

import com.example.ncc.entity.Branch;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Branch.class)
public abstract class Branch_ {
    public static volatile SingularAttribute<Branch, Integer> id;
    public static volatile SingularAttribute<Branch, String> name;

    public static final String ID = "id";
    public static final String NAME = "name";
}
