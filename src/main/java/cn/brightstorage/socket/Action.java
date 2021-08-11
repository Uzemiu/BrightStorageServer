package cn.brightstorage.socket;

import lombok.Data;

import java.util.List;

@Data
public class Action {

    private int action;

    private List<Object> data;
}
