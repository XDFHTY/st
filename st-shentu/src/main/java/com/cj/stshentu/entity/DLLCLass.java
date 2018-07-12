package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DLLCLass {

    private List<Parameter> finalBeam;
    private List<SlashmatchInfo> slashmatchInfo;
    private List<SlashmatchInfo> matchInfoJudge;

}
