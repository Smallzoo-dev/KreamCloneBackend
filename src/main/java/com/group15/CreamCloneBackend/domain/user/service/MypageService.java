package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.dto.OrderListDto;

public interface MypageService {
    OrderListDto getBuyAndSellList(User user);
}
