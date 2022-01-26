package com.broanex.mes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "indexNo")
@Builder
@Getter
@Setter
@Table(name = "mes_goods_img")
public class GoodImage {
	@Id
	@Column(name = "index_no")
	private Long indexNo;

	@Column(name = "filename")
	private String filename;

	@Column(name = "orders")
	private Long order;

	@ManyToOne
	@JoinColumn(name = "goods_idx")
	@JsonIgnore
	private Good good;

}
