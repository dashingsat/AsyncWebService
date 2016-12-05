package com.satya;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Created by dashingsat on 12/4/16.
 */
@RestController
public class AsyncController implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TaskService taskService ;


    private ApplicationContext context ;

   @Autowired
    AsyncController(TaskService taskService){
        this.taskService = taskService ;
    }

    @RequestMapping("/myAsyncResource/")
          public DeferredResult<World> getResult(){

          DeferredResult<World> res = new DeferredResult<>();

        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

          CompletableFuture.supplyAsync((Supplier<Object>) taskService::execute,taskExecutor)
                  .thenApplyAsync(world -> res.setResult((World) world),taskExecutor) ;

          logger.info("Thread released");

          return res ;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.context = applicationContext ;
    }
}
