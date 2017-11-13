import cn.asens.util.StringUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.util.Random;

import static cn.asens.util.EmailUtils.sendEmail;

/**
 * @author Asens
 * create 2017-10-26 1:13
 **/

public class TestS {
    @Test
    public void mm(){
        Random random=new Random();
        System.out.println(random.nextInt(899999)+100000);
    }


}
