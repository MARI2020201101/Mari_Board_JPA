package org.mariworld.boardjpa.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mariworld.boardjpa.dto.GuestbookDTO;
import org.mariworld.boardjpa.dto.PageRequestDTO;
import org.mariworld.boardjpa.dto.PageResultDTO;
import org.mariworld.boardjpa.entity.Guestbook;
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
        Page<Guestbook> result =guestbookRepository.findAll(pageRequest);
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
    }
