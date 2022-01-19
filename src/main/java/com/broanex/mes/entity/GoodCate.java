package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

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
