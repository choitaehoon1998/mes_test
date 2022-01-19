package com.broanex.mes.entity;

import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.Enum.useStatus.UseStatusConvertor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// useOp가 가질수 있는 값이 Y,N 이라고만 판단하였고, 그렇다면 Enum 을 사용하는것이 이점을 가진다고 판단하였음,

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "indexNo")
@Getter
@Setter
@Table(name = "mes_goods")
public class Good {
    @Id
    @Column(name = "index_no")
    private Long indexNo;

    @Column(name = "gname")
    private String gName;

    @Column(name = "account")
    private Long account;

    @Column(name = "useop")
    @Convert(converter = UseStatusConvertor.class)
    private UseStatus useOp;

    @Column(name = "regidate")
    private LocalDateTime regidate;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<GoodOp> goodOpList = new ArrayList<>();

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<GoodCate> goodCateList = new ArrayList<>();

}
