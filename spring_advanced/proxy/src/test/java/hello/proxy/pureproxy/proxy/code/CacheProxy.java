package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target; //실제 객체 - 프록시입장에서 호출해야하는 타겟 - 원래는 직접 호출하던애
    private String cacheValue; //캐시 데이터 저장

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if(cacheValue == null){ //캐시가 있으면 그거사용
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
