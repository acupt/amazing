package com.acupt.service;

import com.acupt.dao.BiubiuDAO;
import com.acupt.entity.Biubiu;
import com.acupt.service.domain.ServiceResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liujie on 2017/8/14.
 */
@Service
public class BiubiuService {

    private static long maxId;

    @Resource
    private BiubiuDAO biubiuDAO;

    public ServiceResult<Biubiu> insert(Biubiu biubiu) {
        biubiuDAO.saveAndFlush(biubiu);
        maxId = biubiu.getId();
        return ServiceResult.newSuccess(biubiu);
    }

    public ServiceResult<Biubiu> getById(long id) {
        return ServiceResult.newSuccess(biubiuDAO.findOne(id));
    }

    public ServiceResult<String> biu() {
        if (maxId == 0) {
            maxId = biubiuDAO.count();
        }
        long id = (long) (Math.random() * (double) maxId + 1);
        Biubiu biubiu = biubiuDAO.findOne(id);
        if (biubiu != null) {
            return ServiceResult.newSuccess(biubiu.getContent());
        }
        return new ServiceResult<String>(false).setMsg("you biu nothing, try?");
    }

    public long getMaxId() {
        return maxId;
    }
}
