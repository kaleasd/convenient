package com.gyh.internalcommon.util;

import lombok.Data;

/**
 * @author gyh
 * */
@Data
public class JwtInfo {
    private String subject;
    private Long issueDate;
}
