package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

// Cate 를 참조할때 pk를 참조하는것이 아니라, 일반키를 사용하기때문에,
// JoinColumn의 name 과 referencedColumnName이라는 속성을 이용하여 명시적으로 컬럼명을 설정하였음.
// TODO 1. Entity는 Setter를 가지면 안됨. (SETTER -> BUILDER )
//      2. Entity를 너무 남용해서 사용했음, DTO를 만들어서 Entity가 꼭필요한 상황이 아니라면(repository에서 리턴할경우)
//         DTO로 최대한 해결하도록 .
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "indexNo")
@Getter
@Setter
@Builder
@Table(name = "mes_goods_cate")
public class GoodCate {
    @Id
    @Column(name = "index_no")
    private Long indexNo;

    @ManyToOne
    @JoinColumn(name = "goods_idx")
    private Good good;

    @ManyToOne
    @JoinColumn(name = "catecode",
            referencedColumnName = "catecode")
    private Cate cate;
}
