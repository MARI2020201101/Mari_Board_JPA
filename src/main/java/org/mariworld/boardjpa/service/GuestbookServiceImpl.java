package org.mariworld.boardjpa.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mariworld.boardjpa.dto.GuestbookDTO;
import org.mariworld.boardjpa.dto.PageRequestDTO;
import org.mariworld.boardjpa.dto.PageResultDTO;
import org.mariworld.boardjpa.entity.Guestbook;
import org.mariworld.boardjpa.entity.QGuestbook;
import org.mariworld.boardjpa.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook guestbook = dtoToEntity(dto);
        guestbookRepository.save(guestbook);
        return guestbook.getGno();
    }

    public PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO pageRequestDTO){
        PageRequest pageRequest =pageRequestDTO.getPageable(Sort.by("gno").descending());

        //조건검색
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Guestbook> result =guestbookRepository.findAll(booleanBuilder, pageRequest);
        Function<Guestbook,GuestbookDTO> fn = i->entityToDto(i);
        //바로 stream으로 List전달해도 OK
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result =guestbookRepository.findById(gno);

    /*    if(result.isPresent()){
            return entityToDto(result.get());
        }else return null;*/
        return result.isPresent()? entityToDto(result.get()):null;
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = guestbookRepository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            guestbookRepository.save(entity);
            }
        }

    private BooleanBuilder getSearch(PageRequestDTO dto){
        //기본 조건 : gno>0
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.gno.goe(0L);
        booleanBuilder.and(expression);
        String type = dto.getType();

        //검색조건 없는 경우
        if(type==null||type.trim().length()==0){
            return booleanBuilder;
        }

        //검색조건 있는 경우
        String keyword = dto.getKeyword();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(dto.getType().contains("t"))
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        if(dto.getType().contains("c"))
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        if(dto.getType().contains("w"))
            conditionBuilder.or(qGuestbook.writer.contains(keyword));

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
    }
