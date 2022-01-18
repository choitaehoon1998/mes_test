package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "mes_goods_op")
public class GoodOp {

    @Id
    @Column(name = "index_no")
    private Long indexNo;

    @ManyToOne
    @JoinColumn(name = "goods_idx")
    @JsonIgnore
    private Good good;

    @Column(name = "opname")
    private String opname;

    @Column(name = "opaccount")
    private Long opaccount;

}
