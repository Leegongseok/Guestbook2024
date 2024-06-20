package kr.ac.kopo.guestbook2024.service;

import com.querydsl.core.BooleanBuilder;
import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.dto.PageRequestDTO;
import kr.ac.kopo.guestbook2024.dto.PageResultDTO;
import kr.ac.kopo.guestbook2024.entity.Guestbook;
import org.springframework.data.querydsl.QPageRequest;

public interface GuestbookService {
    //글 등록 기능
    Long register(GuestbookDTO dto);
    //한페이지에 보여질 글목록(GuestbookDto 객체)이 저장된 list정보를 갖고있는 pageResultDTo 객체참조값을 반환하는 기능
    PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO requestDTO);

    GuestbookDTO read(Long gno);
    //글 제목과 내용 수정하는 기능
    void modify(GuestbookDTO dto);
    //글 삭제하는기능
    void remove(Long gno);
    //검색기기능
    BooleanBuilder getSearch(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){

        Guestbook entity =Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return  entity;
    }

    default GuestbookDTO entityToDTo(Guestbook entity){
        GuestbookDTO dto=GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
