package com.assertion.assignment.service.impl;

import com.assertion.assignment.constants.ApplicationConstants;
import com.assertion.assignment.entity.WebsitePO;
import com.assertion.assignment.exception.PasswordGenerationException;
import com.assertion.assignment.repository.PasswordManagerRepo;
import com.assertion.assignment.response.RequestVO;
import com.assertion.assignment.response.WebsiteVO;
import com.assertion.assignment.service.PasswordManagerService;
import com.assertion.assignment.util.PasswordGenerationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerServiceImpl.class);

    @Autowired
    private PasswordGenerationUtil passwordGenerationUtil;

    @Autowired
    private PasswordManagerRepo passwordManagerRepo;

    @Override
    public List<WebsiteVO> findAll() {
        try {
            List<WebsitePO> websitePOS = (List<WebsitePO>) passwordManagerRepo.findAll();
            List<WebsiteVO> websiteVOList = new ArrayList<>();
            if(!websitePOS.isEmpty()) {
                websitePOS.forEach(websitePO -> {
                    WebsiteVO websiteVO = new WebsiteVO();
                    BeanUtils.copyProperties(websitePO, websiteVO);
                    websiteVOList.add(websiteVO);
                });
                return websiteVOList;
            }
            return websiteVOList;
        }catch (Exception ex){
            if(ex instanceof PasswordGenerationException){
                throw ex;
            }else{
                LOGGER.info("Exception while fetching data {}",ex.getMessage());
                throw new PasswordGenerationException(ApplicationConstants.DATA_FETCH_ERROR_CODE,ApplicationConstants.DATA_FETCH_ERROR_DESC);
            }
        }
    }

    @Override
    public String getGeneratedPassword(int length)
    {
        StringBuilder concatenatedString = new StringBuilder().append(ApplicationConstants.LOWER_CASE)
                .append(ApplicationConstants.UPPER_CASE)
                .append(ApplicationConstants.DIGITS)
                .append(ApplicationConstants.SPECIAL_CHARACTERS);

        String randomString = passwordGenerationUtil.generateRandomString(concatenatedString.toString());
        StringBuilder passString = new StringBuilder();
        for(int i=0;i<=length;i++){
            int randInt = passwordGenerationUtil.getRandomInteger(randomString.length());
            passString.append(randomString.charAt(randInt));
        }
        String encodedString = Base64.getEncoder().encodeToString(passString.toString().getBytes());
        LOGGER.info("Generated String : {}",passString.toString());
        LOGGER.info("Encoded String : {}",encodedString);
        return encodedString;
    }

    /**
     * @param requestVO
     * @return list of ids
     */
    @Override
    @Transactional
    public List<Long> savePassword(List<RequestVO> requestVO)
    {
        List<WebsitePO> websitePOList = new ArrayList<>();
        List<Long> savedIdList;
        if(!requestVO.isEmpty()){
            requestVO.forEach(req -> {
                WebsitePO websitePO = new WebsitePO();
                websitePO.setWebsiteName(req.getWebsiteName());
                websitePO.setPassword(req.getPassString());
                Calendar calobj = Calendar.getInstance();
                websitePO.setCreatedTime(calobj.getTime());
                websitePOList.add(websitePO);
            });
        }
        LOGGER.info("Size of list {}",websitePOList.size());
        try{
            List<WebsitePO> websitePOS = (List<WebsitePO>) passwordManagerRepo.saveAll(websitePOList);
            savedIdList = websitePOS.stream().map(WebsitePO::getId).collect(Collectors.toList());
        }catch (Exception ex){
            LOGGER.info("Exception message : {}",ex.getMessage());
            throw new PasswordGenerationException(ApplicationConstants.SAVE_ERROR_CODE,ApplicationConstants.SAVE_ERROR_DESC);
        }
        return savedIdList;
    }

    /**
     * @param idList
     * @return flag to indicate successful delete
     */
    @Override
    @Transactional
    public Boolean deleteWebsitebyId(List<Long> idList)
    {
        if(!idList.isEmpty()){
            Boolean deleteFlag;
            try{
                passwordManagerRepo.deleteByIdIn(idList);
                deleteFlag = true;
                return deleteFlag;
            }catch (Exception ex){
                LOGGER.info("Exception message : {}",ex.getMessage());
                throw new PasswordGenerationException(ApplicationConstants.DELETE_CODE,ApplicationConstants.DELETE_DESC);
            }
        }else{
            throw new PasswordGenerationException(ApplicationConstants.INVALID_REQUEST,ApplicationConstants.INVALID_REQUEST_DESC);
        }
    }

    /**
     * @param requestVOS
     * @return list of updated ids
     */
    @Override
    @Transactional
    public List<Long> updateWebsitesById(List<RequestVO> requestVOS)
    {
        List<Long> idList;
        try{
            List<WebsitePO> listToSave = new ArrayList<>();
            List<Long> updatedIds = new ArrayList<>();
            idList = requestVOS.stream().map(RequestVO::getId).collect(Collectors.toList());
            List<WebsitePO> websitePOList = (List<WebsitePO>) passwordManagerRepo.findAllById(idList);
            if(!websitePOList.isEmpty()){
                for (WebsitePO websitePO : websitePOList) {
                    for (RequestVO requestVO : requestVOS) {
                        WebsitePO po = new WebsitePO();
                        if(requestVO.getId().equals(websitePO.getId())){
                            Calendar calobj = Calendar.getInstance();
                            po.setId(requestVO.getId());
                            po.setCreatedTime(websitePO.getCreatedTime());
                            if(requestVO.getPassString() != null){
                                po.setPassword(requestVO.getPassString());
                                po.setModifiedTime(calobj.getTime());
                            }else{
                                po.setPassword(websitePO.getPassword());
                            }
                            if(requestVO.getWebsiteName() != null){
                                po.setWebsiteName(requestVO.getWebsiteName());
                                po.setModifiedTime(calobj.getTime());
                            }else{
                                po.setWebsiteName(websitePO.getWebsiteName());
                            }
                            listToSave.add(po);
                        }
                    }
                }
                if(!listToSave.isEmpty()){
                    List<WebsitePO> updatedList = (List<WebsitePO>) passwordManagerRepo.saveAll(listToSave);
                    updatedIds = updatedList.stream().map(WebsitePO::getId).collect(Collectors.toList());
                }
                return updatedIds;
            }else {
                throw new PasswordGenerationException(ApplicationConstants.NO_RECORD_ERROR_CODE,ApplicationConstants.NO_RECORD_ERROR_DESC);
            }
        }catch (Exception ex){
            LOGGER.info("Exception message : {}",ex.getMessage());
            if(ex instanceof PasswordGenerationException)
                throw ex;
            throw new PasswordGenerationException(ApplicationConstants.DATA_FETCH_ERROR_CODE,ApplicationConstants.DATA_FETCH_ERROR_DESC);
        }
    }
}
