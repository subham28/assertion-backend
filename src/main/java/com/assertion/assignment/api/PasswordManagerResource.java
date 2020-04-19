package com.assertion.assignment.api;


import com.assertion.assignment.constants.ApplicationConstants;
import com.assertion.assignment.exception.PasswordGenerationException;
import com.assertion.assignment.response.RequestVO;
import com.assertion.assignment.response.ResponseVO;
import com.assertion.assignment.response.WebsiteVO;
import com.assertion.assignment.service.PasswordManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PasswordManagerResource
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerResource.class);
    @Autowired
    PasswordManagerService passwordManagerService;

    @GetMapping("/websites")
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<List<WebsiteVO>> getAllWebsites(){
        return new ResponseEntity<>(passwordManagerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/generate/{name}/{length}")
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<Object> generatePassword(@PathVariable("name") String websiteName,@PathVariable("length") int length)
    {
        LOGGER.info("Received parameters name : {}, Length : {}",websiteName,length);
        if(length > 0) {
            String secretString = passwordManagerService.getGeneratedPassword(length);
            ResponseVO responseVO = new ResponseVO();
            responseVO.setWebsiteName(websiteName);
            responseVO.setPassString(secretString);
            return new ResponseEntity<>(responseVO, HttpStatus.OK);
        }else {
            throw new PasswordGenerationException(ApplicationConstants.REQ_ERROR_CODE,ApplicationConstants.INVALID_LENGTH);
        }
    }

    @PostMapping("/save")
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<Object> savePassword(@RequestBody List<RequestVO> requestVO){
        LOGGER.info("Request obj : {}",requestVO.toString());
        List<Long> savedsIdList = passwordManagerService.savePassword(requestVO);
        LOGGER.info("Saved ids : {}",savedsIdList.toString());
        return new ResponseEntity<>(savedsIdList,HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteWebsite/{id}")
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<Object> deleteWebsite(@PathVariable("id") List<Long> idList){
        LOGGER.info("Request List : {}",idList.toString());
        boolean flag = passwordManagerService.deleteWebsitebyId(idList);
        if(flag){
            return new ResponseEntity<>(ApplicationConstants.DELETE_SUCCESS,HttpStatus.CREATED);
        }else{
            throw new PasswordGenerationException(ApplicationConstants.DELETE_CODE,ApplicationConstants.DELETE_DESC);
        }
    }

    @PutMapping("/update")
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<Object> updateWebsiteRecord(@RequestBody List<RequestVO> requestVOS){
        LOGGER.info("Request value : {}",requestVOS.size());
        requestVOS.forEach(requestVO -> {
            byte[] arr = Base64.getDecoder().decode(requestVO.getPassString());
            LOGGER.info("Decoded String : {}", new String(arr));
        });
        if(!requestVOS.isEmpty()){
            List<Long> updatedIds = passwordManagerService.updateWebsitesById(requestVOS);
            LOGGER.info("Updated ids : {}",updatedIds.toString());
            return new ResponseEntity<>(ApplicationConstants.UPDATE_SUCCESS,HttpStatus.CREATED);
        }else{
            throw new PasswordGenerationException(ApplicationConstants.INVALID_REQUEST,ApplicationConstants.INVALID_REQUEST_DESC);
        }
    }
}
