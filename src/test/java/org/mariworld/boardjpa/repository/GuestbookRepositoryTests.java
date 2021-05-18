package org.mariworld.boardjpa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.jupiter.api.Test;
import org.mariworld.boardjpa.entity.Guestbook;
import org.mariworld.boardjpa.entity.QGuestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Guestbook guestbook =
                    Guestbook.builder()
                            .title("제목 "+i)
                            .content("내용 "+i)
                            .writer("작성자" + i)
                            .build();
            guestbookRepository.save(guestbook);
                }
        );
    }
    @Test
    public void updateTest(){
        Guestbook guestbook = guestbookRepository.findById(300L).get();
        guestbook.changeTitle("수정 테스트");
        guestbook.changeContent("수정 내용");
        guestbookRepository.save(guestbook);
    }
    @Test
    public void selectTest1(){
        PageRequest page = PageRequest.of(1,10);

        //1) Q도메인 객체 public static으로 생성
        QGuestbook qGuestbook = QGuestbook.guestbook;
        //2) where 조건절을 만들 booleanBuilder생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //3) 조건문에 넣을 쿼리 만들기
        String query = "1";
        BooleanExpression expression = qGuestbook.title.contains(query);
        booleanBuilder.and(expression);
        //4) 쿼리 날리기
        guestbookRepository.findAll(booleanBuilder,page).get().forEach(System.out::println);
    }
    @Test
    public void selectTest2(){
        PageRequest page =PageRequest.of(0,10);
        //1) Q도메인 객체가져오기
        QGuestbook qGuestbook = QGuestbook.guestbook;
        //2) BooleanBuilder생성
        BooleanBuilder builder = new BooleanBuilder();
        //3) 쿼리 만들기 : 제목 or 내용 / and gno>30
        BooleanExpression title = qGuestbook.title.contains("1");
        BooleanExpression content = qGuestbook.content.contains("2");
        BooleanExpression exall = title.or(content);

        BooleanExpression gno = qGuestbook.gno.gt(30L);
        builder.and(exall);
        builder.and(gno);
        //4) repository로 쿼리 날리기
        guestbookRepository.findAll(builder,page).get().forEach(System.out::println);

    }
}
