package org.dromara.dynamictp.common.properties;

import java.util.List;

import org.dromara.dynamictp.common.entity.DtpExecutorProps;

import lombok.Data;

@Data
public class DtpProperties {

    private DtpProperties() {}

    /**
     * If enabled DynamicTp.
     */
    private boolean enabled = true;

    /**
     * ThreadPoolExecutor configs.
     */
    private List<DtpExecutorProps> executors;


    public static DtpProperties getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        private static final DtpProperties INSTANCE = new DtpProperties();
    }
    
}
