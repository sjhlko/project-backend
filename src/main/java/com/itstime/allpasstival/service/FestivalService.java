package com.itstime.allpasstival.service;


import com.itstime.allpasstival.domain.dto.festival.FestivalDetailResponseDto;
import com.itstime.allpasstival.domain.dto.festival.FestivalReserveResponse;
import com.itstime.allpasstival.domain.dto.festival.FestivalSaveRequestDto;
import com.itstime.allpasstival.domain.dto.festival.FestivalUpdateRequestDto;
import com.itstime.allpasstival.domain.entity.Festival;
import com.itstime.allpasstival.domain.entity.ReservedFestival;
import com.itstime.allpasstival.domain.entity.User;
import com.itstime.allpasstival.repository.FestivalRepository;
import com.itstime.allpasstival.repository.ReservedFestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final ValidateService validateService;
    private final ReservedFestivalRepository reservedFestivalRepository;




    //리스트 조회
    public static Page<Festival> fesList(Pageable pageable){

        return null;
    }

    public static FestivalUpdateRequestDto findByid(Integer id) {

        return FestivalUpdateRequestDto.builder().build();
    }


    //글 작성
    public Integer save(FestivalSaveRequestDto requestDto) {
        return festivalRepository.save(requestDto.toEntity()).getFestivalId();
    }


    //리스트에서 게시글 세부조회. 게시글의 id를 받아와서 반환
    public FestivalDetailResponseDto viewDetail(Integer id){
        Festival festival = festivalRepository.findById(id).get();
        return FestivalDetailResponseDto.builder().
                holdingVenue(festival.getHoldingVenue()).
                hostInst(festival.getHostInst()).
                telNum(festival.getTelNum()).
                festivalName(festival.getFestivalName()).
                hostOrg(festival.getHostOrg()).
                etc(festival.getEtc()).
                view(festival.getView()).
                finishDate(festival.getFinishDate()).
                startDate(festival.getStartDate()).
                homepAddr(festival.getHomepAddr()).
                streetAddr(festival.getStreetAddr()).
                author(festival.getAuthor()).
                build();
    }

    //검색기능
    ///public Page<Festival> festivalSearch(String keyWord, Pageable pageable){
       /// return festivalRepository
    //.findByKeyWordContaining(keyWord,pageable);
    ///}


    //게시글 삭제하는거
    //게시글 아이디 받아서 삭제
    public void Delete(Integer id){

        festivalRepository.deleteById((id));
    }

    public FestivalReserveResponse updateReservedFestival(Integer festivalId, String userId){
        User user = validateService.validateUser(userId);
        Festival festival = validateService.validateFestival(festivalId);
        Optional<ReservedFestival> reservedFestival = reservedFestivalRepository.findByFestivalAndUser(festival,user);
        if(reservedFestival.isPresent()){
            validateService.validatePermission(reservedFestival.get().getUser(),user);
            reservedFestivalRepository.delete(reservedFestival.get());
            return FestivalReserveResponse.of(festival, "찜을 삭제했습니다.");
        }
        reservedFestivalRepository.save(ReservedFestival.of(festival,user));
        return FestivalReserveResponse.of(festival,"찜을 추가했습니다.");

    }

}
