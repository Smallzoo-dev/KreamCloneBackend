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
    public Shoes createTest() {

        Shoes shoes = new Shoes("123","123","123");
        shoesRepository.save(shoes);

        return null;
    }

//    public MainDto getList() {
//        List<MainDto> dtos = new ArrayList<MainDto>();
//        for(int i = 0; dtos; i++){
//            MainDto dto = new MainDto();
//            dto.getBrand();
//        }
//        return shoesRepository.findAll();
//    }
}

