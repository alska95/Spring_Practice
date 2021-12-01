package com.example.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {


    public void save(String itemId){
        //저장 로직
        if(itemId.equals("ex")){ // 예외터트리는 로직
            throw new IllegalStateException("예외 발생!");
        }

        sleep(1000);
    }

    private void sleep(int mills) {

        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
