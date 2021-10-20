package com.group15.CreamCloneBackend.domain.product.service;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.dto.ProductDto;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ShoesRepository shoesRepository;

    public void setup(ProductDto productDto){
        Shoes shoes = new Shoes(productDto);
        Shoes shoes1 = shoesRepository.save(shoes);
        System.out.println(shoes1);

    }
}
