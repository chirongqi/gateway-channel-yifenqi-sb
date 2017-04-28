package com.namibank.df.gateway.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网关工具
 * 
 * @author CliveYuan
 * @date Mar 21, 2017 4:46:00 PM
 *
 */
public class GatewayUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(GatewayUtils.class);
    
    private GatewayUtils() {}
    
    /**
     * 简单脱敏 <br>
     * phone:      188****0168 <br>
     * bankCardNo: 621***********3350 <br>
     * idcardNo:   441**********7033 <br>
     * @param text
     * @return
     */
    public static String desensitize(String text) {
        if (StringUtils.isBlank(text) || text.length() == 1) {
            return StringUtils.EMPTY;
        }
        int startLen = 3;
        int endLen = 4;
        if (text.length() <= 3) {
            startLen = 0;
            endLen = 1;
        }
        return StringUtils.left(text, startLen).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(text, endLen),
                StringUtils.length(text), "*"), "****"));
    }

    /**
     * 获取默认对账时间(前一天)
     * 格式yyyyMMdd
     * @return
     */
    public static String getDefaultReconcDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, - 1);
        Date date = calendar.getTime();
        String setDate = df.format(date);
        return setDate;
    }
    
    /**
     * map脱敏
     * 
     * @param dataMap
     * @param keys 要脱敏的key
     */
    public static void desensitizeMap(Map<String, String> dataMap, String...keys) {
        if (dataMap == null || dataMap.isEmpty()) {
            return;
        }
        for (String key : keys) {
            String value = dataMap.get(key);
            if (StringUtils.isNotBlank(value)) {
                dataMap.put(key, GatewayUtils.desensitize(value));
            }
        }
    }
    
    /**
     * 是否发送请求 <br>
     * 代付通道 22:40 - 23:20 不处理订单
     * 所以这个时间段不要往通道送
     * 
     * @return
     */
    public static boolean canSend() {
        try {
            String limitStart = "22:40:00";
            String limitEnd = "23:20:00";
            
            String defaultPattern = "yyyy-MM-dd HH:mm:ss";
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = sdf.format(now);
            String limitBeginDateStr = todayDate.concat(" " + limitStart);
            String limitEndDateStr = todayDate.concat(" " + limitEnd);
            Date limitBeginDate = DateUtils.parseDate(limitBeginDateStr, defaultPattern);
            Date limitEndDate = DateUtils.parseDate(limitEndDateStr, defaultPattern);
            if (now.compareTo(limitBeginDate) > 0 && now.compareTo(limitEndDate) < 0) {
                logger.info("[canSend]>>>>>>>>>>>>>>>> now={}, limitB={}, limitE={}, result=false", now, limitBeginDate, limitEndDate);
                return false;
            } else {
                logger.info("[canSend]>>>>>>>>>>>>>>>> now={}, limitB={}, limitE={}, result=true", now, limitBeginDate, limitEndDate);
                return true;
            }
        } catch (ParseException e) {
            logger.error("canSend Exception, result=true", e);
            return true;
        }
    }
    
    /**
     * 获取默认对账时间 格式:yyyyMMdd
     * 
     * @return
     */
    public static String getDefaultSettleDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, - 1);
        Date date = calendar.getTime();
        String setDate = df.format(date);
        return setDate;
    }
    
}
