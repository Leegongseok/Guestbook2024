package kr.ac.kopo.guestbook2024.service;

import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.dto.PageRequestDTO;
import kr.ac.kopo.guestbook2024.dto.PageResultDTO;
import kr.ac.kopo.guestbook2024.entity.Guestbook;
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
        Page<Guestbook> result =repository.findAll(pageable);
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


}
