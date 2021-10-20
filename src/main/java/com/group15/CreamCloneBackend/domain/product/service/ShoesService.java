package com.group15.CreamCloneBackend.domain.product.service;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.dto.MainDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesDto;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoesService {

    private final ShoesRepository shoesRepository;

    public Shoes showShoes(Long productId){

        return shoesRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 상품입니다.")
            );
        }


    public ShoesDto showDto(Long productId){
        Shoes shoes = shoesRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("반환이 없습니다")
        );

        ShoesDto shoesDto = new ShoesDto(shoes.getBrand(), shoes.getModelName(), shoes.getImage()
                , shoes.getBookmarkCnt(), shoes.getModelNumber(), shoes.getReleaseDate() , shoes.getPriceOriginal());

        return shoesDto;

    }
//    public Shoes createTest() {
//
//        Shoes shoes = new Shoes("123","123","123");
//        shoesRepository.save(shoes);
//
//        return null;
//    }
    /**
     * 메인페이지에 북마크 true , false 여부 체크
     * userDto에서
     * likedShoesList 체크 ? 유저정보를 확인해서 북마크 반환
     *
     * 전체신발 리스트에서 신발 하나의 리스트를 가져와서 해당 신발의 북마크여부 확인 후 boolean 형과 비교..?     */

    public List<MainDto> getList() {
        List<MainDto> dtos = new ArrayList<MainDto>();
        List<Shoes> showMain = shoesRepository.findAll();
        for (Shoes shoe : showMain) {
            dtos.add(new MainDto(shoe));
        }
        return dtos;
    }
}

