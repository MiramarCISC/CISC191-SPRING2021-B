package edu.sdccd.cisc191.b;

import java.io.Serializable;

public class ServerCloseRequest implements Serializable {
    private int i;

    //protected ServerCloseRequest() {}

    //public ServerCloseRequest() {i = 0;}

    @Override
    public String toString(){
        return "Thanks for playing!";
    }
}
