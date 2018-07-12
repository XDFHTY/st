package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlashmatchInfo {

    private double tLinesX;
    private double tLinesY;
    private double tLineeX;
    private double tLineeY;
    private double tLineeIX;
    private double tLineeIY;
    private double tLineeA;
    private List<String> intensiveLable;
}
