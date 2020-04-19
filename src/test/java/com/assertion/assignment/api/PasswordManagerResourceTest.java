package com.assertion.assignment.api;

import com.assertion.assignment.entity.WebsitePO;
import com.assertion.assignment.response.WebsiteVO;
import com.assertion.assignment.service.PasswordManagerService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class PasswordManagerResourceTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PasswordManagerService passwordManagerService;

    @Test
    void getAllRecords() throws Exception {
        List<WebsiteVO> websiteVOList = new ArrayList<>();
        WebsiteVO websiteVO = new WebsiteVO();
        websiteVO.setId(1L);
        websiteVO.setWebsiteName("Subham");
        websiteVO.setPassword("pass123_");
        websiteVO.setCreatedTime(new Date());
        websiteVO.setModifiedTime(new Date());

        WebsiteVO websiteVO1 = new WebsiteVO();
        websiteVO1.setId(2L);
        websiteVO1.setWebsiteName("New Web");
        websiteVO1.setPassword("pass1234_");
        websiteVO1.setCreatedTime(new Date());
        websiteVO1.setModifiedTime(new Date());

        websiteVOList.add(websiteVO);
        websiteVOList.add(websiteVO1);

        Mockito.when(passwordManagerService.findAll()).thenReturn(websiteVOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/websites").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andDo(MockMvcResultHandlers.print());
    }

}