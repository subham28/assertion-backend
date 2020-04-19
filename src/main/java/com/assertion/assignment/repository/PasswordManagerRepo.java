package com.assertion.assignment.repository;

import com.assertion.assignment.entity.WebsitePO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordManagerRepo extends PagingAndSortingRepository<WebsitePO,Long>
{
    @Modifying
    void deleteByIdIn(List<Long> idList);
}
