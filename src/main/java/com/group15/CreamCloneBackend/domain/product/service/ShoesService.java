package com.group15.CreamCloneBackend.domain.product.service;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.dto.MainDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesDto;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import com.group15.CreamCloneBackend.domain.user.repository.UserShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShoesService {

    private final ShoesRepository shoesRepository;
    private final UserShoesRepository userShoesRepository;

    public Shoes showShoes(Long Id){

        return shoesRepository.findById(Id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 상품입니다.")
            );
        }


    public ShoesDto showDto(Long Id){
        Shoes shoes = shoesRepository.findById(Id).orElseThrow(
                () -> new IllegalArgumentException("반환이 없습니다")
        );

        ShoesDto shoesDto = new ShoesDto(shoes);
        shoesDto.setStatusCode(200L);
        shoesDto.setMsg("상세페이지 로딩 성공");
        return shoesDto;

    }



    public List<MainDto> getList() {
        List<MainDto> dtos = new ArrayList<MainDto>();
        List<Shoes> showMain = shoesRepository.findAll();

        for (Shoes shoe : showMain) {
            dtos.add(new MainDto(shoe));
        }
        return dtos;
    }

    public List<MainDto> getUserLoginedList(User user) {
        List<MainDto> originList = this.getList();
        return setPriceAndLike(user, originList);
    }

    public List<MainDto> setPriceAndLike(User user, List<MainDto> productList) {
        List<Shoes> collect = shoesRepository.findAll().stream()
                .filter(s -> s.getInTradeList().size() > 0).collect(Collectors.toList());
        List<UserShoes> likedShoesList = userShoesRepository.findAllByUser(user);
        Map<Long, MainDto> productListMap = productList.stream().collect(Collectors.toMap(
                i1 -> i1.getId(),
                i2 -> i2
        ));


        if (!collect.isEmpty()) {
            for (Shoes shoes : collect) {
                MainDto mainDto = productListMap.get(shoes.getId());
                Long price = shoes.getInTradeList().get(0).getPrice();
                mainDto.setPrice(price.toString());
                productListMap.put(shoes.getId(), mainDto);
            }
        }

        if (!likedShoesList.isEmpty()) {
            for (UserShoes userShoes : likedShoesList) {
                MainDto mainDto = productListMap.get(userShoes.getShoes().getId());
                mainDto.setBookMark(true);
                productListMap.put(userShoes.getShoes().getId(), mainDto);
            }
        }
       return new ArrayList<MainDto>(productListMap.values());

    }

}

