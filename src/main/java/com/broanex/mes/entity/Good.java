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

    @OneToMany(mappedBy= "good" ,cascade = CascadeType.ALL)
    private List<GoodCate> goodCateList = new ArrayList<>();

}
