package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

// GoodOp를 조회할경우, Good의 대한 정보까지 같이 포함하여 Return 할 필요는 없다고 판단하였고,
// JsonIgnore라는 Annotation 을 이용하여 리턴할경우 Good에대한 정보는 리턴하지 않도록 하였음.

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
