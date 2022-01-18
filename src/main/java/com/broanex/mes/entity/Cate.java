package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "indexNo")
@Getter
@Setter
@Table(name = "mes_cate")
public class Cate {
    @Id
    @Column(name = "index_no")
    private Long indexNo;

    @Column(name = "catename")
    private String cateName;

    @Column(name = "catecode")
    private String cateCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upccate")
    private Cate parentCate;

    @OneToMany(mappedBy = "parentCate")
    private List<Cate> childCateList = new ArrayList<>();

    public void addChildCate(Cate child) {
        this.childCateList.add(child);
        child.setParentCate(this);
    }
}
