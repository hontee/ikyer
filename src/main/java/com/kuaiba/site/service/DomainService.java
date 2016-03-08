package com.kuaiba.site.service;

import java.util.List;

import com.kuaiba.site.db.entity.Domain;
import com.kuaiba.site.db.entity.DomainExample;
import com.kuaiba.site.front.vo.DomainVO;
import com.kuaiba.site.service.utils.Pager;

public interface DomainService extends Pager<Domain, DomainExample> {

	int countByExample(DomainExample example);

    void deleteByExample(DomainExample example);

    void deleteByPrimaryKey(Long id);

    void add(DomainVO vo);

    List<Domain> findByExample(DomainExample example);

    Domain findByPrimaryKey(Long id);
    
    List<Domain> findByCollect(DomainExample example);

    void updateByExample(Domain record, DomainExample example);

    void updateByPrimaryKey(Long id, DomainVO vo);
    
}
