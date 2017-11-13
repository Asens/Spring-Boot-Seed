package cn.asens.util;

import cn.asens.controller.LoginAct;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Asens
 * create 2017-10-31 6:27
 **/

public class EmailUtils {
    private static Logger log= LogManager.getLogger(EmailUtils.class);

    /**
     * @param emailTo email to
     * @param code active code
     * @return boolean result
     */
    public static boolean sendEmail(String emailTo,String code){
        //use your own style to send email
        return false;
    }
}
