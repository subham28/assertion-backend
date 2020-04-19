package com.assertion.assignment.service;

import com.assertion.assignment.response.RequestVO;
import com.assertion.assignment.response.WebsiteVO;

import java.util.List;

public interface PasswordManagerService
{
    List<WebsiteVO> findAll();

    String getGeneratedPassword(int length);

    List<Long> savePassword(List<RequestVO> requestVO);

    Boolean deleteWebsitebyId(List<Long> idList);

    List<Long> updateWebsitesById(List<RequestVO> requestVOS);
}
