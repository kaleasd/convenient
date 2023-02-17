package com.gyh.internalcommon.dto.servicesms.request;

import com.gyh.internalcommon.dto.servicesms.SmsTemplateDto;

import java.util.Arrays;
import java.util.List;

public class SmsSendRequest {
    private String[] receivers;
    private List<SmsTemplateDto> data;

    public String toString() {
        return "SmsSendRequest [reveivers=" + Arrays.toString(this.receivers) + ", data=" + this.data + "]";
    }

    public SmsSendRequest() {
    }

    public String[] getReceivers() {
        return this.receivers;
    }

    public List<SmsTemplateDto> getData() {
        return this.data;
    }

    public void setReceivers(final String[] receivers) {
        this.receivers = receivers;
    }

    public void setData(final List<SmsTemplateDto> data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SmsSendRequest)) {
            return false;
        } else {
            SmsSendRequest other = (SmsSendRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!Arrays.deepEquals(this.getReceivers(), other.getReceivers())) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SmsSendRequest;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + Arrays.deepHashCode(this.getReceivers());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

}
