package com.example.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * "127.0.0.1:8080" 같은거 컨버터로 이쁘게 바꿔보자!
 * */
@Getter
@EqualsAndHashCode
public class IpPort {


    private String ip;
    private int port;


    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


}
