package com.acupt.service.impl;

import com.acupt.dao.BiubiuDAO;
import com.acupt.domain.Biubiu;
import com.acupt.service.BiubiuService;
import com.acupt.service.domain.ServiceResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liujie on 2017/8/14.
 */
@Service
public class BiubiuServiceImpl implements BiubiuService {

    private static long maxId;

    @Resource
    private BiubiuDAO biubiuDAO;

    @Override
    public ServiceResult<Biubiu> insert(Biubiu biubiu) {
        try {
            biubiuDAO.saveAndFlush(biubiu);
            maxId = biubiu.getId();
            return ServiceResult.newSuccess(biubiu);
        } catch (Exception e) {
            return ServiceResult.newFailed(e);
        }
    }

    @Override
    public ServiceResult<Biubiu> getById(long id) {
        try {
            return ServiceResult.newSuccess(biubiuDAO.findOne(id));
        } catch (Exception e) {
            return ServiceResult.newFailed(e);
        }
    }

    @Override
    public ServiceResult<String> biu() {
        if (maxId == 0) {
            try {
                maxId = biubiuDAO.count();
            } catch (Exception e) {
                return ServiceResult.newFailed(e);
            }
        }
        long id = (long) (Math.random() * (double) maxId + 1);
        Biubiu biubiu = biubiuDAO.findOne(id);
        if (biubiu != null) {
            return ServiceResult.newSuccess(biubiu.getContent());
        }
        return new ServiceResult<String>(false).setMsg("you biu nothing, try?");
    }
}
