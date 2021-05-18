package org.mariworld.boardjpa.service;

import org.junit.jupiter.api.Test;
import org.mariworld.boardjpa.dto.GuestbookDTO;
import org.mariworld.boardjpa.dto.PageRequestDTO;
import org.mariworld.boardjpa.dto.PageResultDTO;
import org.mariworld.boardjpa.entity.Guestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    GuestbookService guestbookService;

    @Test
    public void registerTest(){
        GuestbookDTO guestbookDTO
                = GuestbookDTO.builder()
                                .title("서비스 테스트")
                                .content("서비스 테스트 내용")
                                .writer("작성자1")
                                .build();
        guestbookService.register(guestbookDTO);
    }
    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder().size(10).page(2).build();

        guestbookService.getList(pageRequestDTO)
                .getDtoList()
                .forEach(System.out::println);

    }
    @Test
    public void getListTest2(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder().page(11).size(10).build();
        PageResultDTO<GuestbookDTO,Guestbook> result =
                guestbookService.getList(pageRequestDTO);
        System.out.println(result.getPageList());
        System.out.println(result.getEnd());
        System.out.println(result.getSize());
        System.out.println(result.getStart());
        System.out.println(result.getTempEnd());
        System.out.println(result.getTotalPage());
        System.out.println(result.isNext());
        System.out.println(result.isPrev());
    }
}
