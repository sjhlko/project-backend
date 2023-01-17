package com.itstime.allpasstival.controller;

import com.itstime.allpasstival.domain.dto.Response;
import com.itstime.allpasstival.domain.dto.festival.*;
import com.itstime.allpasstival.service.FestivalService;
import com.itstime.allpasstival.service.LikedPostService;
import com.itstime.allpasstival.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/festivals")
@RequiredArgsConstructor
@Slf4j
public class FestivalApiController {

    private final FestivalService festivalService ;
    private final LikedPostService likedPostService;
    private final ValidateService validateService;

    //글 작성처리
    @PostMapping("")
    public Response<FestivalSaveResponseDto> saveFestival(@RequestBody FestivalSaveRequestDto requestDto){
        FestivalSaveResponseDto saveResponse = festivalService.save(requestDto);
        return Response.success(saveResponse);
    }

    //게시글 수정기능
    @PutMapping("/{id}")
    public Response<FestivalUpdateResponseDto> modifyfestival(@PathVariable Integer id, @RequestBody  FestivalUpdateRequestDto updateRequest){
        FestivalUpdateResponseDto festivalModifyResponse = festivalService.modifyfestival(id, updateRequest);
        return Response.success(festivalModifyResponse);

    }

    //축제글 세부 조회.
    @GetMapping(value="/{id}")
    public Response<FestivalDetailResponse> viewDetails(@PathVariable Integer id, Authentication authentication){
        FestivalDetailResponse festivalDetailResponse = festivalService.viewDetail(id);
        festivalService.updateRecentlyViewedFestival(id, authentication);
        return Response.success(festivalDetailResponse);
    }

    //삭제기능
    @DeleteMapping("/{id}")
    public Response<FestivalDeleteResponse> deletePost(@PathVariable Integer id){
        FestivalDeleteResponse festivalDeleteResponse = festivalService.deleteFestival(id);
        return Response.success(festivalDeleteResponse);

    }

    //찜한 축제 수정
    @PostMapping("/{id}/reserves")
    public Response<FestivalReserveResponse> reserveFestival(@PathVariable Integer id, Authentication authentication){
        FestivalReserveResponse festivalReserveResponse = festivalService.updateReservedFestival(id, authentication.getName());
        return Response.success(festivalReserveResponse);
    }

    //리스트
    @GetMapping("/list")
    public Response<Page<FestivalDetailResponse>> festivallist(@PageableDefault(size = 20, sort ="startDate",
            direction = Sort.Direction.DESC)Pageable pageable){
        Page<FestivalDetailResponse> list = festivalService.festivalList(pageable);
        return Response.success(list);
    }

    //좋아요
    @GetMapping("/{id}/likes")
    public Response<Long> CntLike(@PathVariable Integer id){
        return Response.success(festivalService.cntLike(id));
    }

    //좋아요 등록&취소
    @PostMapping("/{id}/likes")
    public Response<FestivalLikedResponse> AddLike(@PathVariable Integer id, Authentication authentication){
        FestivalLikedResponse likeUpdateResponse = festivalService.upDate(id, authentication.getName());
        return Response.success(likeUpdateResponse);
    }

    //월별 일정관리



}
