package com.gyh.internalcommon.dto.serviceverificationcode.response;

import java.util.Objects;

public class VerifyCodeResponse {
    private String code;

    public VerifyCodeResponse(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof VerifyCodeResponse)) {
            return false;
        } else {
            VerifyCodeResponse other = (VerifyCodeResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = this.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }
                return true;
            }
        }
    }

    private boolean canEqual(final Object other) {
        return other instanceof VerifyCodeResponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "VerifyCodeResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
