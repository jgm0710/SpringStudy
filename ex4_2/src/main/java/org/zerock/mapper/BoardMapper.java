package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper { // BoardMapper.xml에 이 인터페이스에 대한 처리가 담겨있음.

	// @Select("select * from system.tbl_board where bno >0") //@Select 어노테이션이 아닌
	// mapper.xml파일을 이용한 매핑
	public List<BoardVO> getList(); // select * from tbl_board where bno>0
									// List<>를 통해서 객체를 배열의 형태로 반환
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	/* 위 메소드 내용
	 * select A.* from ( select @rownum:=@rownum+1 rn,a.* from tbl_board a,
	 * (select @rownum:=0) b where @rownum<#{pageNum}*#{amount} order by bno desc )
	 * A where rn>(#{pageNum}-1)*#{amount}
	 */

	public void insert(BoardVO board); // create

	public void insertSelectKey(BoardVO board); // create

	public BoardVO read(Long bno);// select * from system.tbl_board where bno = #{bno}

	public int delete(Long bno); // delete from system.tbl_board where bno=#{bno}
									// delete는 int return하면 몇개의 데이터가 삭제되었는지 반환
									// bno에 지정한 번호에 데이터가 없을경우 0을 반환

	public int update(BoardVO board); /*
										 * update system.tbl_board set title=#{title}, content=#{content},
										 * writer=#{writer}, updateDate=sysdate where bno=#{bno}
										 * 
										 */
										// update 처리 --delete와 마찬가지로 int return으로 몇개의 데이터가 수정되었는지를 반환
	
	public int getTotalCount(Criteria cri);
	
	//MyBatis의 sql을 처리하기 위해서는 기본적으로 하나의 파라미터 타입을 사용하기 때문에 
	//2개 이상의 데이터를 전달하려면 @Param 어노테이션을 이용해서 처리
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}