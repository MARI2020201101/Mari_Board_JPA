package org.mariworld.boardjpa.service;

import org.mariworld.boardjpa.dto.GuestbookDTO;
import org.mariworld.boardjpa.dto.PageRequestDTO;
import org.mariworld.boardjpa.dto.PageResultDTO;
import org.mariworld.boardjpa.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
    PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO pageRequestDTO);
    GuestbookDTO read(Long gno);
    void remove(Long gno);
    void modify(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook guestbook = Guestbook.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return guestbook;
    }
    default GuestbookDTO entityToDto(Guestbook guestbook){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .writer(guestbook.getWriter())
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
                .build();
        return dto;
    }
}
