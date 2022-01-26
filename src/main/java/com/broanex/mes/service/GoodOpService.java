package com.broanex.mes.service;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 추가 정보를 관리하는 Service 역활을 한다.
 * 관련 DB 테이블 :  mes_goods_op
 * */

import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.repository.GoodOpRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. isExist R:[Boolean] P:[GoodOp]                     : 파라미터로 전달받은 goodOp가 DB에 실제로 존재여부 확인하는 메서드.
 * 2. deleteAllByList R:[List<GoodOp>] P:[List<GoodOp>]  : 파라미터로 전달받은 goodOp 리스트 전부 삭제함 , 삭제되지 않은 리스트는 다시 리턴.
 * 3. updateGoodOp R:[없음] P:[GoodOp]                    : 파라미터로 전달받은 goodOp를 업데이트함
 */

@Service
public class GoodOpService {
	private final GoodOpRepository goodOpRepository;

	public GoodOpService(GoodOpRepository goodOpRepository) {
		this.goodOpRepository = goodOpRepository;
	}

	private boolean isExist(GoodOp goodOp) {
		if (goodOp.getIndexNo() != null && goodOpRepository.existsById(goodOp.getIndexNo())) {
			return true;
		}
		return false;
	}

	public List<GoodOp> deleteAllByList(List<GoodOp> goodopList) {
		List<GoodOp> undeletedGoodOp = new ArrayList<>();
		for (GoodOp goodOp : goodopList) {
			if (isExist(goodOp)) {
				goodOpRepository.deleteById(goodOp.getIndexNo());
			} else {
				undeletedGoodOp.add(goodOp);
			}
		}
		return undeletedGoodOp;
	}

	public void updateGoodOp(GoodOp goodOp) {
		if (isExist(goodOp)) {
			goodOpRepository.save(goodOp);
		}
	}
}
