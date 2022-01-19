package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// GoodCate에서 Cate의 primary key 가 아닌 키를 fk 로 사용하기 때문에, 삭제시의 문제를 방지하기위하여 Serializable 상속
// TODO 1. Entity는 Setter를 가지면 안됨. (SETTER -> BUILDER )
//      2. Entity를 너무 남용해서 사용했음, DTO를 만들어서 Entity가 꼭필요한 상황이 아니라면(repository에서 리턴할경우)
//         DTO로 최대한 해결하도록 .

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "indexNo")
@Getter
@Setter
@Table(name = "mes_cate")
public class Cate implements Serializable {
    @Id
    @Column(name = "index_no")
    private Long indexNo;

    @Column(name = "catename")
    private String cateName;

    @Column(name = "catecode")
    private String catecode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upccate")
    private Cate parentCate;

    @OneToMany(mappedBy = "parentCate", cascade = CascadeType.ALL)
    private List<Cate> childCateList = new ArrayList<>();
}
