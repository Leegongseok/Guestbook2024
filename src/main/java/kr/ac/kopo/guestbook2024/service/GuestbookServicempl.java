package kr.ac.kopo.guestbook2024.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.dto.PageRequestDTO;
import kr.ac.kopo.guestbook2024.dto.PageResultDTO;
import kr.ac.kopo.guestbook2024.entity.Guestbook;
import kr.ac.kopo.guestbook2024.entity.QGuestbook;
import kr.ac.kopo.guestbook2024.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GuestbookServicempl implements GuestbookService{
    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook entity=dtoToEntity(dto);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable =requestDTO.getPageable(Sort.by("gno").descending());
        //검색기능추가
        BooleanBuilder booleanBuilder=getSearch(requestDTO);/*where절에 조건식을 위한*/
        Page<Guestbook> result =repository.findAll(booleanBuilder,pageable);/*조건식이 포함된 select문이 실행*/
        Function<Guestbook,GuestbookDTO> fn =(entity) ->entityToDTo(entity);
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result=repository.findById(gno);
        return result.isPresent()?entityToDTo(result.get()) : null;
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result =repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity=result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity); //글의 제목과 내용을 업데이트하는 문장이 실행된다
        }
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type=requestDTO.getType();
        String keyword=requestDTO.getKeyword();

        BooleanBuilder booleanBuilder=new BooleanBuilder();
        QGuestbook qGuestbook= QGuestbook.guestbook;
        BooleanExpression booleanExpression=qGuestbook.gno.gt(0L);

        booleanBuilder.and(booleanExpression);

        // 화면에서 검색조건을 선택하지않은경우 검색타입 및 검색어
        if(type==null || keyword.trim().length()==0 || type.trim().length()==0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder=new BooleanBuilder();
        if(type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
