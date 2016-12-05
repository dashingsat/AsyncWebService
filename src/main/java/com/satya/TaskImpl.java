package com.satya;

import org.springframework.stereotype.Service;

/**
 * Created by dashingsat on 12/4/16.
 */
@Service
public class TaskImpl implements TaskService {

    @Override
      public World execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new World("satya");
    }
}
