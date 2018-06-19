package ua.edu.ratos.service;

import org.springframework.cache.annotation.Cacheable;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.domain.model.ResultMock;
import ua.edu.ratos.domain.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultDAO;

    @TrackTime
    public List<ResultMock> findAll() {
        return resultDAO.findAll();
    }


    @TrackTime
    @Cacheable("result")
    public ResultMock findOne(long id) {
        simulateSlowService();
        return resultDAO.findOne(id);
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }


}
